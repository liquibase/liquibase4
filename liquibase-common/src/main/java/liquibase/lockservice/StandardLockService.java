package liquibase.lockservice;

import liquibase.ExecuteMode;
import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.*;
import liquibase.actionlogic.ActionExecutor;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.actionlogic.UpdateResult;
import liquibase.database.Database;
import liquibase.database.JdbcConnection;
import liquibase.diff.output.changelog.ActionGeneratorFactory;
import liquibase.exception.ActionPerformException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.LockException;
import liquibase.item.core.Column;
import liquibase.item.core.RelationReference;
import liquibase.item.core.RowData;
import liquibase.item.core.Table;
import liquibase.item.datatype.DataType;
import liquibase.item.datatype.DataTypeLogicFactory;
import liquibase.snapshot.Snapshot;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * {@link LockService} implementation that uses a DATABASECHANGELOGLOCK table to track the lock.
 */
public class StandardLockService extends AbstractLockService {


    public String changeLogLockTablespace;

    public RelationReference changeLogLockTable;
    public boolean upperCaseObjects;

    public StandardLockService() {
    }

    @Override
    public int getPriority(Scope scope) {
        if (scope.getDatabase() != null
                && scope.getDatabase().getConnection() != null
                && scope.getDatabase().getConnection() instanceof JdbcConnection) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public void init(Scope scope) throws LiquibaseException {
        this.upperCaseObjects = scope.getDatabase().getIdentifierCaseHandling(Table.class, false, scope) != Database.IdentifierCaseHandling.LOWERCASE;

        if (this.changeLogLockTable == null) {
            this.changeLogLockTable = createChangeLogLockTableReference(scope);
        }

        Snapshot wantedVersion = createWantedSnapshot(scope);
        Snapshot existingVersion = getExistingSnapshot(scope);

        ActionExecutor actionExecutor = scope.getSingleton(ActionExecutor.class);
        List<? extends Action> actions = scope.getSingleton(ActionGeneratorFactory.class).fix(existingVersion, wantedVersion, scope);
        if (actions.size() > 0) {
            actionExecutor.execute(new CommentAction("Creating or upgrading DatabaseChangeLogLock Table"), scope);
        }

        boolean createdTable = false;
        for (Action action : actions) {
            actionExecutor.execute(action, scope);
            if (action instanceof CreateTableAction) {
                createdTable = true;
            }
        }

        if (createdTable) {
            actionExecutor.execute(new InsertDataAction(new RowData(changeLogLockTable)
                    .add(correctCase("id"), 1, new DataType(DataType.StandardType.INTEGER))
                    .add(correctCase("locked"), false, new DataType(DataType.StandardType.BOOLEAN))
            ), scope);
        }

        if (actions.size() > 0) {
            actionExecutor.execute(new CommitAction(), scope);
        }
    }

    protected RelationReference createChangeLogLockTableReference(Scope scope) {
        String changeLogTableName = "databasechangeloglock";
        if (this.upperCaseObjects) {
            changeLogTableName = changeLogTableName.toUpperCase();
        }

        return new RelationReference(Table.class, changeLogTableName);
    }

    protected Snapshot createWantedSnapshot(Scope scope) {
        Database database = scope.getDatabase();
        Table wantedVersionTable = new Table(this.changeLogLockTable.name, this.changeLogLockTable.getSchema());
        wantedVersionTable.tablespace = changeLogLockTablespace;
        Collection<Column> wantedVersionColumns = new ArrayList<>();

        wantedVersionColumns.add(new Column(correctCase("id"), this.changeLogLockTable, new DataType(DataType.StandardType.INTEGER), false));
        wantedVersionColumns.add(new Column(correctCase("locked"), this.changeLogLockTable, new DataType(DataType.StandardType.BOOLEAN), false));
        wantedVersionColumns.add(new Column(correctCase("lockgranted"), this.changeLogLockTable, new DataType(DataType.StandardType.TIMESTAMP), true));
        wantedVersionColumns.add(new Column(correctCase("lockedby"), this.changeLogLockTable, new DataType(DataType.StandardType.VARCHAR, 255), true));

        boolean upperCase = database.getIdentifierCaseHandling(Table.class, false, scope) != Database.IdentifierCaseHandling.LOWERCASE;
        for (Column column : wantedVersionColumns) {
            if (upperCase) {
                column.name = column.name.toUpperCase();
            }
        }
        Snapshot wantedVersionSnapshot = new Snapshot(scope);
        wantedVersionSnapshot.add(wantedVersionTable);
        wantedVersionSnapshot.addAll(wantedVersionColumns);

        return wantedVersionSnapshot;
    }


    protected Snapshot getExistingSnapshot(Scope scope) throws liquibase.exception.ActionPerformException {
        Snapshot existingSnapshot = new Snapshot(scope);
        existingSnapshot.add(scope.getSingleton(SnapshotFactory.class).snapshot(this.changeLogLockTable, scope));
        existingSnapshot.addAll(scope.getSingleton(SnapshotFactory.class).snapshotAll(Column.class, this.changeLogLockTable, scope));
        return existingSnapshot;
    }

    protected String correctCase(String name) {
        if (upperCaseObjects) {
            return name.toUpperCase();
        } else {
            return name;
        }
    }

    protected ChangeLogLock doLock(Scope scope) throws LockException {
        ActionExecutor executor = scope.getSingleton(ActionExecutor.class);

        try {
            DataType booleanType = DataType.forType(Boolean.class);
            String trueValue = scope.getSingleton(DataTypeLogicFactory.class).getDataTypeLogic(booleanType, scope).toSql(false, booleanType, scope);
            UpdateResult updateResult = executor.update(
                    new UpdateDataAction(changeLogLockTable,
                            new StringClauses().append(correctCase("id") + "=1 AND " + correctCase("locked") + "=" + trueValue),
                            new UpdateDataAction.UpdatedColumn(correctCase("locked"), true),
                            new UpdateDataAction.UpdatedColumn(correctCase("lockgranted"), scope.getDatabase().getCurrentDateTimeFunction(scope)),
                            new UpdateDataAction.UpdatedColumn(correctCase("lockedby"), this.myDescription)
                    ), scope);
            if (scope.getExecuteMode() == ExecuteMode.READ_ONLY) {
                executor.execute(new CommitAction(), scope);
                return new ChangeLogLock(1, new Date(), myDescription, true);
            } else {
                if (updateResult.getNumberAffected() < 0) {
                    //todo: handle for mssql
                    throw new LockException("Database did not return a proper row count (Might have NOCOUNT enabled)");
                } else if (updateResult.getNumberAffected() == 0) {
                    LoggerFactory.getLogger(getClass()).debug("Lock row was already updated, reading new owner");
                    return this.acquireLock(scope);
                } else if (updateResult.getNumberAffected() == 1) {
                    executor.execute(new CommitAction(), scope);
                    return acquireLock(scope);
                } else {
                    throw new LockException("Did not update change log lock correctly, said " + updateResult.getNumberAffected() + " rows were changed");
                }
            }
        } catch (ActionPerformException e) {
            try {
                executor.execute(new RollbackAction(), scope);
                throw new LockException(e);
            } catch (ActionPerformException actionException) {
                throw new LockException("Could not roll back after doLock() ActionPerformException", actionException);
            }
        }
    }

    protected ChangeLogLock getCurrentLock(Scope scope) throws LockException {
        if (scope.getExecuteMode() == ExecuteMode.READ_ONLY) {
            LoggerFactory.getLogger(getClass()).debug("Cannot actually lock database due to running in read only mode, so returning that we own the lock");

            return new ChangeLogLock(1, new Date(), myDescription, true);
        } else {
            try {
                ActionExecutor executor = scope.getSingleton(ActionExecutor.class);
                RowBasedQueryResult lockRow = (RowBasedQueryResult) executor.query(new SelectDataAction(changeLogLockTable, new SelectDataAction.SelectedColumn(null, "*", null, true)), scope);
                RowBasedQueryResult.Row row = lockRow.getSingleRow();
                Boolean locked = row.get(correctCase("locked"), Boolean.class);

                if (locked) {
                    Integer id = row.get(correctCase("id"), Integer.class);
                    Date lockgranted = row.get(correctCase("lockgranted"), Date.class);
                    String lockedby = row.get(correctCase("lockedby"), String.class);
                    boolean isOwner = myDescription.equals(lockedby);

                    return new ChangeLogLock(id, lockgranted, lockedby, isOwner);
                } else {
                    return null;
                }
            } catch (ActionPerformException e) {
                throw new LockException(e);
            }
        }
    }

    @Override
    public void forceReleaseLock(Scope scope) throws LockException {
        ActionExecutor executor = scope.getSingleton(ActionExecutor.class);

        try {
            executor.execute(new CommentAction("Release Liquibase Lock"), scope);

            UpdateResult updateResult = executor.update(new UpdateDataAction(changeLogLockTable, new StringClauses().append(correctCase("id") + " = 1"),
                    new UpdateDataAction.UpdatedColumn(correctCase("locked"), false),
                    new UpdateDataAction.UpdatedColumn(correctCase("lockgranted"), null),
                    new UpdateDataAction.UpdatedColumn(correctCase("lockedby"), null)
            ), scope);

            if (scope.getExecuteMode() == ExecuteMode.READ_WRITE) {
                if (updateResult.getNumberAffected() == -1) {
                    //todo: handle for mssql
                    throw new LockException("Database did not return a proper row count (Might have NOCOUNT enabled.)");
                } else if (updateResult.getNumberAffected() != 1) {
                    throw new LockException("Did not update change log lock correctly." + updateResult.getNumberAffected() + " rows were updated instead of the expected 1 row using executor " + executor.getClass().getName() + " there are " + ((RowBasedQueryResult) executor.query(new SelectDataAction(changeLogLockTable, new SelectDataAction.SelectedColumn(null, "*", null, true)), scope)).getSingleRow().getSingleValue(Long.class) + " rows in the table");
                }
            }

            executor.execute(new CommitAction(), scope);
        } catch (Exception e) {
            try {
                executor.execute(new RollbackAction(), scope);
            } catch (ActionPerformException rollbackException) {
                LoggerFactory.getLogger(getClass()).debug("Cannot rollback after releaseLock exception", rollbackException);
            }
            throw new LockException(e);
        } finally {
            LoggerFactory.getLogger(getClass()).info("Successfully released change log lock");
        }


    }
}