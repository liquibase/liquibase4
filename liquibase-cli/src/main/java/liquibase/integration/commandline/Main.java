package liquibase.integration.commandline;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import liquibase.ObjectMetaData;
import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.command.AbstractLiquibaseCommand;
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

    private final List<String> globalArgs = new ArrayList<>();
    private String commandName;
    private final List<String> commandArgs = new ArrayList<>();

    private String defaultsFile = "./liquibase.properties";

    private LoggerContext loggerContext;
    private PatternLayoutEncoder loggerPattern;
    private ConsoleAppender<ILoggingEvent> consoleAppender;
    private ch.qos.logback.classic.Logger rootLogger;

    public static void main(String[] args) {

        Main main = new Main(args);
        main.execute();

    }

    public Main(String[] originalArgs) {

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

    protected void configureLogging() {
        loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerPattern = new PatternLayoutEncoder();

        loggerPattern.setContext(loggerContext);
        loggerPattern.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
        loggerPattern.start();

        consoleAppender = new ConsoleAppender<>();
        consoleAppender.setTarget("System.err");
        consoleAppender.setContext(loggerContext);
        consoleAppender.start();

//        FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
//        fileAppender.setFile(file);
//        fileAppender.setEncoder(ple);
//        fileAppender.setContext(lc);
//        fileAppender.start();
//

        rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.WARN);
        rootLogger.addAppender(consoleAppender);

//        Logger logger = (Logger) LoggerFactory.getILoggerFactory().setLogger(string);
//        logger.addAppender(fileAppender);
//        logger.setLevel(Level.DEBUG);
//        logger.setAdditive(false); /* set to true if root should log too */

    }

    protected void execute() {

        configureLogging();

        Options globalOptions = null;
        Options commandOptions = null;

        Scope rootScope = new Scope(new ClassLoaderResourceAccessor(Main.class.getClassLoader()), new HashMap<String, Object>());
        Logger log = LoggerFactory.getLogger(getClass());
        try {

            if (commandName == null) {
                if (globalArgs.size() == 1 && globalArgs.get(0).equals("--help")) {
                    printHelp(globalOptions, commandOptions, rootScope);
                    return;

                } else {
                    throw new LiquibaseException("No command sent");
                }
            }

            LiquibaseCommand command = rootScope.getSingleton(CommandFactory.class).getCommand(this.commandName, rootScope);

            if (command == null) {
                throw new LiquibaseException("Unknown command: " + commandName);
            }

            globalOptions = getGlobalOptions(command, rootScope);
            commandOptions = getCommandOptions(command, rootScope);

            File defaultsFile = new File(this.defaultsFile);
            if (defaultsFile.exists()) {
                log.debug("Found defaultsFile " + this.defaultsFile + " at " + defaultsFile.getCanonicalPath());
                try (FileInputStream stream = new FileInputStream(defaultsFile)) {
                    Properties props = new Properties();
                    props.load(stream);

                    for (Map.Entry entry : props.entrySet()) {
                        if (globalOptions.hasLongOption((String) entry.getKey())) {
                            setUnlessAlreadySet(entry, globalArgs);
                        } else if (commandOptions.hasLongOption((String) entry.getKey())) {
                            setUnlessAlreadySet(entry, commandArgs);
                        } else {
                            log.info("Property " + entry.getKey() + " in " + this.defaultsFile + " is not an argument for " + commandName);
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
            log.error("Error running liquibase", e);
            System.out.println("Error running liquibase: " + e.getMessage());
            if (e instanceof org.apache.commons.cli.ParseException) {
                printHelp(globalOptions, commandOptions, rootScope);
            }
        }


//        if (!rootScope.getSingleton(CommandFactory.class).getAllCommandNames().contains(arg.toLowerCase())) {
//            throw new CommandLineException("Unknown command: "+arg);
//        }


    }

    private void setUnlessAlreadySet(Map.Entry entry, List<String> passedArgs) {
        for (String arg : passedArgs) {
            if (arg.startsWith("--" + entry.getKey() + "=")) {
                LoggerFactory.getLogger(getClass()).debug("Not overwriting passed argument --" + entry.getKey());
                return;
            }
        }

        passedArgs.add("--" + entry.getKey() + "=" + entry.getValue());
    }

    protected Scope setupScope(CommandLine globalCommandLine, CommandLine commandCommandLine, Scope rootScope) throws Exception {
        Scope scope = rootScope;
        if (globalCommandLine.hasOption("classpath")) {
            ClassLoader classLoader = createClassLoader(globalCommandLine.getOptionValue("classpath"), Boolean.valueOf(globalCommandLine.getOptionValue("includeSystemClasspath", "true")));
            scope = scope.child(Scope.Attr.classLoader, classLoader);
            scope = scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(classLoader));
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
                throw new LiquibaseException(classPathFile.getAbsolutePath() + " does not exist");
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
            globalOptions = getGlobalOptions(new AbstractLiquibaseCommand() {
                @Override
                public String getName() {
                    return "help";
                }

                @Override
                protected CommandResult run(Scope scope) throws Exception {
                    return new CommandResult(null);
                }

                @Override
                public ValidationErrors validate(Scope scope) {
                    return new ValidationErrors(this);
                }
            }, rootScope);
        }

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("liquibase [global options] COMMAND [command options]"+System.lineSeparator()+"Global options:", globalOptions);
        System.out.println();
        System.out.println("Available commands: ");
        System.out.println(StringUtil.indent(StringUtil.join(rootScope.getSingleton(CommandFactory.class).getAllCommandNames(), System.lineSeparator())));
        System.out.println();
        if (commandName != null) {
            if (commandOptions != null && commandOptions.getOptions().size() > 0) {
                formatter.printHelp(commandName+" options:", commandOptions);
            } else {
                System.out.println("No options for "+commandName);
            }
        }
    }

    protected Options getGlobalOptions(LiquibaseCommand command, Scope rootScope) {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("logLevel").desc("Execution log level. Possible values: OFF, ERROR, WARN, INFO, DEBUG").hasArg().build());
        options.addOption(Option.builder().longOpt("defaultsFile").desc("File with default option values. Defaults to ./liquibase.properties").hasArg().build());
        options.addOption(Option.builder().longOpt("classpath").desc("Classpath containing JDBC drivers and/or changelog files").hasArg().build());
        options.addOption(Option.builder().longOpt("includeSystemClasspath").desc("Include system classpath in Liquibase classpath").hasArg().build());
        return options;
    }

    protected Options getCommandOptions(LiquibaseCommand command, Scope rootScope) {
        Options options = new Options();

        for (ObjectMetaData.Attribute attr : command.getObjectMetaData().attributes) {
            options.addOption(Option.builder().longOpt(attr.name).desc(attr.description).required(ObjectUtil.defaultIfNull(attr.required, false)).hasArg().build());
        }


        return options;
    }


}
