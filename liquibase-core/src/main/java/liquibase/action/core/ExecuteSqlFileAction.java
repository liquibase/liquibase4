package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

public class ExecuteSqlFileAction extends AbstractAction {

    public String path;
    public String encoding;
    public String endDelimiter;
    public String dbms;
    public Boolean splitStatements;
    public Boolean stripComments;


    public ExecuteSqlFileAction() {
    }

    public ExecuteSqlFileAction(String path) {
        this.path = path;
    }

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(ExecuteSqlFileAction.class) {

                    @Override
                    protected String[] getAliases() {
                        return new String[] { "sqlFile" };
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {

                    }
                }
        };
    }
}
