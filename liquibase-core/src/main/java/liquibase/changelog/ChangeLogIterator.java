package liquibase.changelog;

import liquibase.Scope;
import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.exception.LiquibaseException;

import java.util.*;

/**
 * Used to apply logic to all {@link ChangeSet}s in a {@link ChangeLog}, after filtering it as needed.
 */
public class ChangeLogIterator {

    private ChangeLog changeLog;

    public ChangeLogIterator(ChangeLog changeLog) {
        this.changeLog = changeLog;
    }

    public void run(ChangeSetVisitor visitor, Scope scope) throws LiquibaseException {
        List<ChangeSet> changeSetList = new ArrayList<ChangeSet>(changeLog.getChangeSets());

        for (ChangeSet changeSet : changeSetList) {
            visitor.visit(changeSet, scope);
        }
    }
}
