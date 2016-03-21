package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.ExecuteShellCommandAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.ExecuteResult;
import liquibase.actionlogic.NoOpResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.util.StreamUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExecuteShellCommandLogic extends AbstractActionLogic<ExecuteShellCommandAction> {

    @Override
    protected Class<? extends ExecuteShellCommandAction> getSupportedAction() {
        return ExecuteShellCommandAction.class;
    }

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return null;
    }

    @Override
    public boolean executeInteractsExternally(ExecuteShellCommandAction action, Scope scope) {
        return true;
    }

    @Override
    public ValidationErrors validate(ExecuteShellCommandAction action, Scope scope) {
        return super.validate(action, scope)
                .checkRequiredFields("executable");
    }

    @Override
    public ActionStatus checkStatus(ExecuteShellCommandAction action, Scope scope) {
        return new ActionStatus().assertCorrect(true, "Cannot check");
    }

    @Override
    public ActionResult execute(ExecuteShellCommandAction action, Scope scope) throws ActionPerformException {
            String currentOS = System.getProperty("os.name");
            if (!osFilterMatches(action, currentOS)) {
                String message = "Not executing on os " + currentOS + " when " + StringUtils.join(action.osFilters, ", ") + " was specified for osFilters";
                LoggerFactory.getLogger(getClass()).info(message);
                return new NoOpResult(action, message);
            }

        List<String> finalCommandArray = createFinalCommandArray(action, scope);
        return new ExecuteResult(action, executeCommand(finalCommandArray, action, scope));
    }

    protected boolean osFilterMatches(ExecuteShellCommandAction action, String currentOS) {
        if (action.osFilters == null || action.osFilters.size() == 0) {
            return true;
        }

        String simpleOs = null;
        if (currentOS.toLowerCase().contains("windows")) {
            simpleOs = "windows";
        } else if (currentOS.toLowerCase().contains("linux")) {
            simpleOs = "linux";
        } else if (currentOS.toLowerCase().contains("mac")) {
            simpleOs = "mac";
        }

        if (simpleOs != null && action.osFilters.contains(simpleOs)) {
            return true;
        }

        return action.osFilters.contains(currentOS);
    }

    protected List<String> createFinalCommandArray(ExecuteShellCommandAction action, Scope scope) {
        List<String> commandArray = new ArrayList<String>();
        commandArray.add(action.executable);
        commandArray.addAll(action.args);
        return commandArray;
    }

    protected String executeCommand(List<String> commandAndArgs, ExecuteShellCommandAction action, Scope scope) throws ActionPerformException {
        ByteArrayOutputStream standardError = new ByteArrayOutputStream();
        ByteArrayOutputStream standardOut = new ByteArrayOutputStream();

        Process p;
        try {
            ProcessBuilder pb = createProcessBuilder(commandAndArgs, action, scope);
            p = pb.start();
        } catch (IOException e) {
            throw new ActionPerformException(e);
        }

        int returnCode = 0;
        try {
            //output both stdout and stderr data from proc to stdout of this process
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), standardError);
            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), standardOut);

            errorGobbler.start();
            outputGobbler.start();

            returnCode = p.waitFor();

            errorGobbler.finish();
            outputGobbler.finish();

        } catch (InterruptedException e) {
            ;
        }

        String stdErrOut = standardError.toString();
        String stdOutOut = standardOut.toString();

        if (stdErrOut.length() > 0) {
            LoggerFactory.getLogger(getClass()).error(stdErrOut);
        }
        LoggerFactory.getLogger(getClass()).debug(stdOutOut);

        if (returnCode != 0) {
            throw new ActionPerformException(StringUtils.join(commandAndArgs, " ") + " returned an code of " + returnCode+"\n"+stdErrOut);
        }

        return stdOutOut;
    }

    protected ProcessBuilder createProcessBuilder(List<String> commandAndArgs, ExecuteShellCommandAction action, Scope scope) {
        ProcessBuilder pb = new ProcessBuilder(commandAndArgs);
        pb.redirectErrorStream(true);
        return pb;
    }

    private class StreamGobbler extends Thread {
        private final OutputStream outputStream;
        private InputStream processStream;

        private StreamGobbler(InputStream processStream, ByteArrayOutputStream outputStream) {
            this.processStream = processStream;
            this.outputStream = outputStream;
        }

        public void run() {
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(processStream);
                while (processStream != null) {
                    if (bufferedInputStream.available() > 0) {
                        StreamUtil.copy(bufferedInputStream, outputStream);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignore) {
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        public void finish() {
            InputStream processStream = this.processStream;
            this.processStream = null;

            try {
                StreamUtil.copy(processStream, outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
