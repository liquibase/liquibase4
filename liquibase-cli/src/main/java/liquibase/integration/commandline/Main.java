package liquibase.integration.commandline;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import liquibase.ObjectMetaData;
import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.command.CommandFactory;
import liquibase.command.CommandResult;
import liquibase.command.LiquibaseCommand;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;
import liquibase.util.SystemUtil;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Main main = new Main(args);
        main.execute();
    }


    protected final List<String> globalArgs = new ArrayList<>();
    protected String commandName;
    protected final List<String> commandArgs = new ArrayList<>();

    protected String defaultsFile = "./liquibase.properties";

    public Main(String[] originalArgs) {
        processOriginalArgs(originalArgs);
    }

    /**
     * Splits original arguments into {@link #globalArgs} {@link #commandName} and {@link #commandArgs}.
     * If a --defaultsFile attribute is set, save that to {@link #defaultsFile}
     */
    protected void processOriginalArgs(String[] originalArgs) {
        for (String arg : originalArgs) {
            if (arg.startsWith("--defaultsFile=")) {
                this.defaultsFile = arg.split("=", 2)[1];
            } else if (commandName == null && isCommand(arg)) {
                commandName = arg;
            } else if (commandName == null) {
                globalArgs.add(arg);
            } else {
                commandArgs.add(arg);
            }
        }
    }

    protected boolean isCommand(String arg) {
        if (!arg.startsWith("-")) {
            return true;
        }
        return false;
    }

    protected Options getGlobalOptions(Scope rootScope) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("help").desc("Output this message").hasArg(false).build());
        options.addOption(Option.builder().longOpt("logLevel").desc("Execution log level. Possible values: OFF, ERROR, WARN, INFO, DEBUG").hasArg().build());
        options.addOption(Option.builder().longOpt("defaultsFile").desc("File with default option values. Defaults to ./liquibase.properties").hasArg().build());
        options.addOption(Option.builder().longOpt("classpath").desc("Classpath containing JDBC drivers and/or changelog files").hasArg().build());
        options.addOption(Option.builder().longOpt("includeSystemClasspath").desc("Include system classpath in Liquibase classpath").hasArg().build());
        return options;
    }

    /**
     * Builds the command options based on the metadata in {@link LiquibaseCommand}
     */
    protected Options getCommandOptions(LiquibaseCommand command, Scope rootScope) {
        Options options = new Options();

        for (ObjectMetaData.Attribute attr : command.getObjectMetaData().attributes) {
            options.addOption(Option.builder().longOpt(attr.name).desc(attr.description).required(ObjectUtil.defaultIfNull(attr.required, false)).hasArg().build());
        }

        return options;
    }

    protected ch.qos.logback.classic.Logger configureLogging() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder loggerPattern = new PatternLayoutEncoder();

        loggerPattern.setContext(loggerContext);
        loggerPattern.setPattern("%date %level %logger{10} [%file:%lineNumber] %msg [%mdc]%n");
        loggerPattern.start();

        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setTarget("System.err");
        consoleAppender.setContext(loggerContext);
        consoleAppender.start();

        ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.OFF); //start with logging off until overridden
        rootLogger.addAppender(consoleAppender);

        return rootLogger;
    }

    protected void execute() {
        ch.qos.logback.classic.Logger rootLogger = configureLogging();

        Scope rootScope = new Scope(new ClassLoaderResourceAccessor(Main.class.getClassLoader()), new HashMap<String, Object>());

        Options globalOptions = getGlobalOptions(rootScope);
        Options commandOptions = null;


        Logger log = LoggerFactory.getLogger(getClass());
        try {
            if (commandName == null) {
                if (globalArgs.size() == 1 && globalArgs.get(0).equals("--help")) {
                    printHelp(globalOptions, null, rootScope);
                    return;
                } else {
                    throw new org.apache.commons.cli.ParseException("No command set");
                }
            }

            LiquibaseCommand command = rootScope.getSingleton(CommandFactory.class).getCommand(this.commandName, rootScope);

            if (command == null) {
                throw new org.apache.commons.cli.ParseException("Unknown command: " + commandName);
            }

            commandOptions = getCommandOptions(command, rootScope);

            File defaultsFile = new File(this.defaultsFile);
            if (defaultsFile.exists()) {
                log.debug("Found defaultsFile " + this.defaultsFile + " at " + defaultsFile.getCanonicalPath());
                try (FileInputStream stream = new FileInputStream(defaultsFile)) {
                    Properties props = new Properties();
                    props.load(stream);

                    for (Map.Entry entry : props.entrySet()) {
                        log.debug("Found default property "+entry.getKey()+"="+entry.getValue());
                        if (globalOptions.hasLongOption((String) entry.getKey())) {
                            setUnlessAlreadySet(entry, globalArgs);
                        } else if (commandOptions.hasLongOption((String) entry.getKey())) {
                            setUnlessAlreadySet(entry, commandArgs);
                        } else {
                            log.info("DefaultsFile Property " + entry.getKey() + " in " + this.defaultsFile + " is not an argument for " + commandName);
                        }
                    }
                }
            } else {
                log.debug("Could not find defaultsFile " + this.defaultsFile + " at " + defaultsFile.getCanonicalPath());
            }

            CommandLineParser parser = new DefaultParser();
            CommandLine globalCommandLine = parser.parse(globalOptions, globalArgs.toArray(new String[globalArgs.size()]));
            CommandLine commandCommandLine = parser.parse(commandOptions, commandArgs.toArray(new String[commandArgs.size()]));

            if (globalCommandLine.hasOption("logLevel")) {
                rootLogger.setLevel(Level.valueOf(globalCommandLine.getOptionValue("logLevel")));
            }

            Scope scope = setupScope(globalCommandLine, commandCommandLine, rootScope);

            for (Option option : commandCommandLine.getOptions()) {
                command.set(option.getLongOpt(), option.getValue());
            }

            ValidationErrors errors = command.validate(scope);
            if (errors.hasErrors()) {
                throw new LiquibaseException(errors.toString());
            }

            CommandResult result = command.execute(scope);
            if (result.succeeded) {
                System.out.println(result.message);
            } else {
                throw new LiquibaseException(result.message);
            }

        } catch (Exception e) {
            if (e instanceof org.apache.commons.cli.ParseException) {
                System.out.println("Error parsing Liquibase parameters: "+e.getMessage());
                printHelp(globalOptions, commandOptions, rootScope);
            } else {
                log.error("Error running liquibase", e);
                System.out.println("Error running liquibase: " + e.getMessage());

                Set<String> loggedReasons = new HashSet<>();
                loggedReasons.add(e.getMessage());

                Throwable cause = e.getCause();
                while (cause != null) {
                    if (cause.getMessage() != null && loggedReasons.add(cause.getMessage())) {
                        System.out.println(StringUtil.indent(cause.getMessage()));
                    }
                    cause = cause.getCause();
                }

            }
        }

    }

    protected void setUnlessAlreadySet(Map.Entry entry, List<String> passedArgs) {
        for (String arg : passedArgs) {
            if (arg.startsWith("--" + entry.getKey() + "=")) {
                LoggerFactory.getLogger(getClass()).debug("Not overwriting passed argument --" + entry.getKey());
                return;
            }
        }

        passedArgs.add("--" + entry.getKey() + "=" + entry.getValue());
    }

    /**
     * Creates the scope to execute the command under.
     */
    protected Scope setupScope(CommandLine globalCommandLine, CommandLine commandCommandLine, Scope rootScope) throws Exception {
        Scope scope = rootScope;

        if (globalCommandLine.hasOption("classpath")) {
            ClassLoader classLoader = createClassLoader(globalCommandLine.getOptionValue("classpath"), Boolean.valueOf(globalCommandLine.getOptionValue("includeSystemClasspath", "true")));
            scope = scope.child(Scope.Attr.classLoader, classLoader);
            scope = scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(classLoader, Scope.class.getClassLoader())); //needs to include liquibase's classloader to find xsd files etc.
        }

        return scope;
    }

    protected ClassLoader createClassLoader(String classpathString, boolean includeSystemClasspath) throws Exception {
        final List<URL> urls = new ArrayList<>();
        String[] classpath;
        if (SystemUtil.isWindows()) {
            classpath = classpathString.split(";");
        } else {
            classpath = classpathString.split(":");
        }

        Logger logger = LoggerFactory.getLogger(getClass());
        for (String classpathEntry : classpath) {
            File classPathFile = new File(classpathEntry);
            if (!classPathFile.exists()) {
                throw new LiquibaseException("Classpath entry "+classPathFile.getAbsolutePath() + " does not exist");
            }

            URL newUrl = new File(classpathEntry).toURI().toURL();
            logger.debug("Adding '" + newUrl + "' to classpath");
            urls.add(newUrl);
        }

        if (includeSystemClasspath) {
            return AccessController.doPrivileged(new PrivilegedAction<URLClassLoader>() {
                @Override
                public URLClassLoader run() {
                    return new URLClassLoader(urls.toArray(new URL[urls.size()]), Thread.currentThread().getContextClassLoader());
                }
            });

        } else {
            return AccessController.doPrivileged(new PrivilegedAction<URLClassLoader>() {
                @Override
                public URLClassLoader run() {
                    return new URLClassLoader(urls.toArray(new URL[urls.size()]), null);
                }
            });
        }
    }

    protected void printHelp(Options globalOptions, Options commandOptions, Scope rootScope) {
        if (globalOptions == null) {
            globalOptions = getGlobalOptions(rootScope);
        }

        HelpFormatter formatter = new HelpFormatter();
        formatter.setLongOptSeparator("=");
        formatter.setSyntaxPrefix("");

        System.out.println("liquibase [global options] COMMAND [command options]");
        formatter.printHelp("Global Options:", globalOptions);
        System.out.println();
        System.out.println("Available commands: ");
        System.out.println(StringUtil.indent(StringUtil.join(rootScope.getSingleton(CommandFactory.class).getAllCommandNames(), System.lineSeparator())));
        if (commandName != null) {
            System.out.println();
            if (commandOptions != null && commandOptions.getOptions().size() > 0) {
                formatter.printHelp("Command Options for \""+commandName+"\":", commandOptions);
            } else {
                System.out.println("No command options options for \""+commandName+"\"");
            }
        }
    }

}
