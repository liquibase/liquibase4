package liquibase.changelog.filter;

import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;

import java.util.List;

/**
 * Convenience base class for {@link ChangeSetFilter}s that rely on knowing what was ran in the past.
 */
public abstract class AbstractRanChangeSetFilter extends AbstractChangeSetFilter {
    public final List<RanChangeSet> ranChangeSets;

    public AbstractRanChangeSetFilter(List<RanChangeSet> ranChangeSets) {
        this.ranChangeSets = ranChangeSets;
    }

    protected RanChangeSet getRanChangeSet(ChangeSet changeSet) {
        for (RanChangeSet ranChangeSet : ranChangeSets) {
            if (ranChangeSet.matches(changeSet)) {
                return ranChangeSet;
            }
        }
        return null;
    }

    /**
     * Default implementation finds the corresponding RanChangeSet and calls {@link #allow(ChangeSet, RanChangeSet, Scope)}
     */
    @Override
    public Result allow(ChangeSet changeSet, Scope scope) {
        return allow(changeSet, getRanChangeSet(changeSet), scope);
    }

    /**
     * Override with the logic on whether a changeSet should be allowed or not given the corresponding {@link RanChangeSet}.
     * Called by {@link #allow(ChangeSet, Scope)}.
     */
    protected abstract Result allow(ChangeSet changeSet, RanChangeSet ranChangeSet, Scope scope);
}
