package liquibase.action.core;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

import java.util.List;

public class CustomClassAction extends AbstractAction {

    public Class customClass;
    public List<CustomClassParameter> parameters;

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(CustomClassAction.class) {

                    @Override
                    protected String[] getAliases() {
                        return new String[] {"customChange"};
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        actionNode.renameChildren("class", "customClass");
                        ParsedNode parameters = actionNode.getChild("parameters", true);
                        actionNode.moveChildren("param", parameters);
                    }
                }
        };
    }

    public static class CustomClassParameter extends AbstractExtensibleObject {
        public String name;
        public Object value;
    }



}
