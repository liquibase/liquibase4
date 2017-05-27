package liquibase.parser.preprocessor.core.changelog;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.exception.ParseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.FunctionCall;
import liquibase.item.function.SequenceCurrentValueFunction;
import liquibase.item.function.SequenceNextValueFunction;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.AbstractParsedNodePreprocessor;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.util.CollectionUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
     * Runs before {@link ChangeSetPreprocessor} if it defines aliases.
     */
    @Override
    public Class<? extends ParsedNodePreprocessor>[] mustBeBefore() {
        if (getAliases() != null) {
            return new Class[]{ChangeSetPreprocessor.class};
        }
        return null;
    }

    /**
     * Return other node names that need to be translated to {@link #standardNodeName}.
     * Default implementation returns null.
     */
    protected String[] getAliases() {
        return null;
    }

    /**
     * Default implementation renames nodes that match values from {@link #getAliases()}
     * and calls {@link #processActionNode(ParsedNode, Scope)} for each node that ends up matching {@link #standardNodeName}.
     * If a node is renamed due to an alias, {@link #processRenamedNode(String, ParsedNode)} is called first.
     */
    @Override
    public void process(ParsedNode node, Scope scope) throws ParseException {
        for (ParsedNode changeSet : node.getChildren("changeSet", true)) {
            String[] aliases = getAliases();
            if (aliases != null) {
                for (String alias : aliases) {
                    for (ParsedNode wrongName : changeSet.getChildren(alias, true)) {
                        String originalName = wrongName.getName();
                        wrongName.rename(standardNodeName);
                        processRenamedNode(originalName, wrongName);
                    }
                }
            }

            for (ParsedNode action : changeSet.getChildren(standardNodeName, true)) {
                processActionNode(action, scope);
            }
        }
    }

    /**
     * Called by {@link #process(ParsedNode, Scope)} when a node is renamed. Allows custom processing depending on what the original node name was.
     */
    protected void processRenamedNode(String originalName, ParsedNode node) throws ParseException {

    }

    /**
     * Usually easier to override this method with action-specific preprocessing logic than override the entire {@link #process(ParsedNode, Scope)} method.
     */
    protected abstract void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException;

}
