package liquibase.parser.preprocessor.core.changelog;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.exception.ParseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.AbstractParsedNodePreprocessor;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.util.StringUtil;

/**
 * Convenience base class for {@link ParsedNodePreprocessor} needed by {@link Action} classes.
 */
public abstract class AbstractActionPreprocessor extends AbstractParsedNodePreprocessor {

    public final String standardNodeName;

    public AbstractActionPreprocessor(Class<? extends Action> actionType) {
        try {
            standardNodeName = actionType.newInstance().getName();
        } catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    /**
     * Return other node names that need to be translated to {@link #standardNodeName}.
     * Default implementation returns null.
     *
     * @see StandardActionPreprocessor for aliasing that is done automatically for all actions.
     */
    protected String[] getAliases() {
        return null;
    }

    /**
     * Default implementation renames nodes that match values from {@link #getAliases()}
     * and calls {@link #processActionNode(ParsedNode)} for each node that ends up matching {@link #standardNodeName}
     */
    @Override
    public void process(ParsedNode node, Scope scope) throws ParseException {
        for (ParsedNode changeSet : node.getChildren("changeSet", true)) {
            String[] aliases = getAliases();
            if (aliases != null) {
                for (String alias : aliases) {
                    for (ParsedNode wrongName : changeSet.getChildren(alias, true)) {
                        wrongName.name = standardNodeName;
                    }
                }
            }

            for (ParsedNode action : changeSet.getChildren(standardNodeName, true)) {
                processActionNode(action);
            }
        }
    }

    /**
     * Usually easier to override this method with action-specific preprocessing logic than override the entire {@link #process(ParsedNode, Scope)} method.
     */
    protected void processActionNode(ParsedNode actionNode) throws ParseException {

    }
}
