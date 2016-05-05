package liquibase.changelog;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.exception.LiquibaseException;

import java.util.List;

/**
 * Convenience base class for {@link ChangeLogHistoryService} implementations.
 */
public abstract class AbstractChangeLogHistoryService extends AbstractExtensibleObject implements ChangeLogHistoryService {

    /**
     * Default implementation calls {@link #getRanChangeSets(Scope)} and finds the matching RanChangeSet.
     * Subclasses can override this method to optimize performance.
     */
    @Override
    public RanChangeSet getRanChangeSet(String id, String author, String logicalPath, Scope scope) throws LiquibaseException {
        List<RanChangeSet> ranChangeSets = getRanChangeSets(scope);
        for (RanChangeSet changeSet : ranChangeSets) {
            if (changeSet.matches(id, author, logicalPath)) {
                return changeSet;
            }
        }
        return null;
    }

}
