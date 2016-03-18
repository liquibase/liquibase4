package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.SequenceReference;

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
}
