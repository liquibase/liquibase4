package liquibase.changelog;

import liquibase.Scope;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.exception.LiquibaseException;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Used to apply logic to all {@link ChangeSet}s in a {@link ChangeLog}, after filtering it as needed.
 */
public class ChangeLogIterator {

    private ChangeLog changeLog;
    private List<ChangeSetFilter> changeSetFilters = new ArrayList<>();

    public ChangeLogIterator(ChangeLog changeLog, ChangeSetFilter... changeSetFilters) {
        this.changeLog = changeLog;
        if (changeSetFilters != null) {
            this.changeSetFilters.addAll(Arrays.asList(changeSetFilters));
        }
    }

    public void run(ChangeSetVisitor visitor, Scope scope) throws LiquibaseException {
        List<ChangeSet> changeSetList = new ArrayList<ChangeSet>(changeLog.getChangeSets());

        for (ChangeSet changeSet : changeSetList) {
            boolean shouldVisit = true;
            Set<ChangeSetFilter.Result> reasonsAccepted = new HashSet<ChangeSetFilter.Result>();
            Set<ChangeSetFilter.Result> reasonsDenied = new HashSet<ChangeSetFilter.Result>();
            if (changeSetFilters != null) {
                for (ChangeSetFilter filter : changeSetFilters) {
                    ChangeSetFilter.Result result = filter.allow(changeSet, scope);
                    if (result.allow) {
                        reasonsAccepted.add(result);
                    } else {
                        shouldVisit = false;
                        reasonsDenied.add(result);
                        break;
                    }
                }
            }

            if (shouldVisit) {
                LoggerFactory.getLogger(getClass()).debug("Visiting changeSet " + changeSet.getIdentifier() + " with " + visitor.getClass().getName());
                visitor.visit(changeSet, scope);
            }
        }
    }
}
