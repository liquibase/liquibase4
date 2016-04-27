package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

/**
 * Outputs a message. The target can be any string, but the default logic knows values from {@link liquibase.action.core.OutputMessageAction.MessageTarget}
 */
public class OutputMessageAction extends AbstractAction {

    public enum MessageTarget {
        /**
         * Outputs directly to stdout
         */
        STDOUT,

        /**
         * Outputs directly to stderr
         */
        STDERR,

        /**
         * Outputs logger at log level debug
         */
        DEBUG,

        /**
         * Outputs logger at log level info
         */
        INFO,

        /**
         * Outputs logger at log level warn
         */
        WARN,

        /**
         * Outputs logger at log level error
         */
        ERROR
    }

    public String message;

    /**
     * Where to write the message to. Should be checked case insensitively
     */
    public String target;

    public OutputMessageAction() {
    }

    public OutputMessageAction(MessageTarget target, String message) {
        this.target = target.name();
        this.message = message;
    }

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[]{
                new AbstractActionPreprocessor(OutputMessageAction.class) {

                    @Override
                    protected String[] getAliases() {
                        return new String[]{"output"};
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {

                    }
                }
        };
    }
}
