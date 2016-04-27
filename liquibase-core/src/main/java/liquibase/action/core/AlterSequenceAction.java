package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.SequenceReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.CollectionUtil;

import java.math.BigInteger;

/**
 * Alters an existing sequence. If a value is null, do not change the existing settings.
 */
public class AlterSequenceAction extends AbstractAction {

    public SequenceReference sequence;

    public BigInteger restartWith;
    public BigInteger incrementBy;

    /**
     * Minimum value. Pass 0 for no minimum
     */
    public BigInteger minValue;

    /**
     * Maximum value. Pass -1 for no maximum
     */
    public BigInteger maxValue;

    /**
     * Number of values to cache. Pass -1 for NO CACHE
     */
    public BigInteger cacheSize;
    public Boolean ordered;
    public Boolean cycle;

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(AlterSequenceAction.class) {

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        actionNode.renameChildren("startValue", "restartWith");
                        convertToSequenceReferenceNode("catalogName", "schemaName", "sequenceName", actionNode);
                    }
                }
        };
    }
}
