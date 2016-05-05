package liquibase.changelog.filter;

import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;
import liquibase.util.ObjectUtil;

import java.util.List;

/**
 * Only allow objects that should be executed because they have not ran before or the changeSet is "alwaysRun"
 */
public class ShouldRunChangeSetFilter extends AbstractRanChangeSetFilter {

    public ShouldRunChangeSetFilter(List<RanChangeSet> ranChangeSets) {
        super(ranChangeSets);
    }

    @Override
    protected Result allow(ChangeSet changeSet, RanChangeSet ranChangeSet, Scope scope) {
        if (ObjectUtil.defaultIfNull(changeSet.alwaysRun, false)) {
            return new Result(true, "Change set always runs");
        }

        if (ranChangeSet == null) {
            return new Result(true, "Change set has not ran yet");
        } else {
            return new Result(true, "Change set already ran");
        }
    }
}
