package liquibase.parser.preprocessor.core.changelog;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.AbstractParsedNodePreprocessor;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.util.StringUtil;

/**
 * Convenience base class for {@link ParsedNodePreprocessor} needed by {@link Action} classes.
 */
public abstract class AbstractActionPreprocessor extends AbstractParsedNodePreprocessor {

    public final Class<? extends Action> actionType;
    public final String standardNodeName;

    public AbstractActionPreprocessor(Class<? extends Action> actionType) {
        this.actionType = actionType;
        standardNodeName = StringUtil.lowerCaseFirst(actionType.getSimpleName());
    }

    /**
     * Runs before {@link ChangeSetPreprocessor} because ChangeSetPreprocessor expects action nodes to end in "Action"
     */
    @Override
    public Class<? extends ParsedNodePreprocessor>[] mustBeBefore() {
        return new Class[]{ChangeSetPreprocessor.class};
    }

    /**
     * Return other node names that need to be translated to {@link #standardNodeName}.
     * Default implementation returns standardNodeName without the "Action" at the end.
     */
    protected String[] getAliases() {
        return new String[] {
                standardNodeName.replaceFirst("Action$", "")
        };
    }

    /**
     * Default implementation renames nodes that match values from {@link #getAliases()}
     * and calls {@link #processActionNode(ParsedNode)} for each node that ends up matching {@link #standardNodeName}
     */
    @Override
    public void process(ParsedNode node, Scope scope) throws ParseException {
        for (ParsedNode changeSet : node.getChildren("changeSet", true)) {
            for (String alias : getAliases()) {
                for (ParsedNode wrongName : changeSet.getChildren(alias, true)) {
                    wrongName.name = standardNodeName;
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
