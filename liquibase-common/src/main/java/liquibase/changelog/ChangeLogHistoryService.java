package liquibase.changelog;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.exception.LiquibaseException;
import liquibase.plugin.Plugin;

import java.util.List;

/**
 * Implementations manage which changeSets have ran and which have not.
 * Normally, the current implementation should be attached to the scope at {@link liquibase.Scope.Attr#changeLogHistoryService}.
 * The "normal" implementation can be looked up via {@link ChangeLogHistoryServiceFactory#getChangeLogHistoryService} but instances can be created and managed manually and attached to the scope as well.
 */
public interface ChangeLogHistoryService extends Plugin, AutoCloseable, ExtensibleObject {

    int getPriority(Scope scope);

    /**
     * Perform whatever initialization is needed to ensure this service is ready for use.
     * May create/update external systems (tables, file etc.) and/or configure properties on itself.
     */
    void init(Scope scope) throws LiquibaseException;

    /**
     * Return a list of what changeSets have been ran.
     */
    List<RanChangeSet> getRanChangeSets(Scope scope) throws LiquibaseException;

    /**
     * Convenience/optimized method to get an individual changeSet by id/author/path.
     */
    RanChangeSet getRanChangeSet(String id, String author, String logicalPath, Scope scope) throws LiquibaseException;

    /**
     * Marks the given changeSet as having the given {@link liquibase.changelog.ChangeSet.ExecType}.
     */
    void setExecType(ChangeSet changeSet, ChangeSet.ExecType execType, Scope scope) throws LiquibaseException;

    /**
     * Removes the given changeSet from the tracked history. It should be like it was never executed.
     * @throws IllegalArgumentException if id, author, or logicalPath are null
     */
    void removeFromHistory(String id, String author, String logicalPath, Scope scope) throws LiquibaseException;

}
