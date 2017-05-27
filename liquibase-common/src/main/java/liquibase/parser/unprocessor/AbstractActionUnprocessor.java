package liquibase.parser.unprocessor;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.exception.ParseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.unprocessor.core.changelog.ChangeLogUnprocessor;
import liquibase.parser.unprocessor.core.changelog.ChangeSetUnprocessor;
import liquibase.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractActionUnprocessor extends AbstractParsedNodeUnprocessor {

    private final String standardNodeName;

    public AbstractActionUnprocessor(Class<? extends Action> actionType) {
        try {
            standardNodeName = actionType.newInstance().getName();
        } catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    public Class<? extends ParsedNodeUnprocessor>[] mustBeAfter() {
        return new Class[]{
                ChangeSetUnprocessor.class,
                ChangeLogUnprocessor.class,
        };
    }

    @Override
    public void unprocess(ParsedNode node, String outputPath, Scope scope) throws ParseException {
        if (node.getName().equals("changeLog")) {
            for (ParsedNode changeSet : node.getChildren("changeSet", false)) {
                for (ParsedNode action : changeSet.getChildren(standardNodeName, false)) {
                    unprocessAction(action, scope);
                }
            }
        }
    }

    abstract protected void unprocessAction(ParsedNode actionNode, Scope scope) throws ParseException;

}
