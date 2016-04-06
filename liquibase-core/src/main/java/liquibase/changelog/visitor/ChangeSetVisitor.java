package liquibase.changelog.visitor;

import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.exception.LiquibaseException;

/**
 * Used by {@link liquibase.changelog.ChangeLogIterator} to traverse {@link ChangeSet}s in a {@link liquibase.changelog.ChangeLog}.
 */
public interface ChangeSetVisitor {

    void visit(ChangeSet changeSet, Scope scope) throws LiquibaseException;

}
