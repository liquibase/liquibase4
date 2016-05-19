package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ExecuteShellCommandAction extends AbstractAction {

    public String executable;
    public List<String> args = new ArrayList<>();
    public List<String> osFilters = new ArrayList<>();

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(ExecuteShellCommandAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{"executeCommand"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                ParsedNode os = actionNode.getChild("os", false);
                if (os != null && os.getValue() != null) {
                    actionNode.addChild("osFilters").setValue(StringUtil.splitAndTrim(os.getValue(null, String.class), ","));
                    os.remove();
                }
                ParsedNode argsNode = actionNode.getChild("args", true);
                actionNode.moveChildren("arg", argsNode);

                for (ParsedNode arg : argsNode.getChildren()) {
                    ParsedNode valueNode = arg.getChild("value", false);
                    if (valueNode != null) {
                        arg.setValue(valueNode.getValue(null, Object.class));
                        valueNode.remove();
                    }
                }
            }
        };
    }
}
