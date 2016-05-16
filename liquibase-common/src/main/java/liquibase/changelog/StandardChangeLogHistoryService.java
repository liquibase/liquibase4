package liquibase.changelog;

import liquibase.ContextExpression;
import liquibase.Labels;
import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.DeleteDataAction;
import liquibase.action.core.InsertDataAction;
import liquibase.action.core.SelectDataAction;
import liquibase.actionlogic.ActionExecutor;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.database.Database;
import liquibase.database.JdbcConnection;
import liquibase.diff.output.changelog.ActionGeneratorFactory;
import liquibase.exception.LiquibaseException;
import liquibase.item.core.Column;
import liquibase.item.core.RelationReference;
import liquibase.item.core.RowData;
import liquibase.item.core.Table;
import liquibase.item.datatype.DataType;
import liquibase.item.datatype.DataTypeLogic;
import liquibase.item.datatype.DataTypeLogicFactory;
import liquibase.snapshot.Snapshot;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * {@link ChangeLogHistoryService} implementation that stores history in a database table.
 */
public class StandardChangeLogHistoryService extends AbstractChangeLogHistoryService {

    public RelationReference changeLogTable;
    public String changeLogTablespace;
    public boolean upperCaseObjects;

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
        if (this.changeLogTable == null) {
            this.changeLogTable = createChangeLogTableReference(scope);
        }

        this.upperCaseObjects = scope.getDatabase().getIdentifierCaseHandling(Table.class, false, scope) != Database.IdentifierCaseHandling.LOWERCASE;

        Snapshot wantedVersion = createWantedSnapshot(scope);
        Snapshot existingVersion = getExistingSnapshot(scope);

        ActionExecutor actionExecutor = scope.getSingleton(ActionExecutor.class);
        List<? extends Action> actions = scope.getSingleton(ActionGeneratorFactory.class).fix(existingVersion, wantedVersion, scope);
        for (Action action : actions) {
            actionExecutor.execute(action, scope);
        }
    }

    protected RelationReference createChangeLogTableReference(Scope scope) {
        String changeLogTableName = "databasechangelog";
        if (this.upperCaseObjects) {
            changeLogTableName = changeLogTableName.toUpperCase();
        }

        return new RelationReference(Table.class, changeLogTableName);
    }


    protected Snapshot getExistingSnapshot(Scope scope) throws liquibase.exception.ActionPerformException {
        Snapshot existingSnapshot = new Snapshot(scope);
        existingSnapshot.add(scope.getSingleton(SnapshotFactory.class).snapshot(this.changeLogTable, scope));
        existingSnapshot.addAll(scope.getSingleton(SnapshotFactory.class).snapshotAll(Column.class, this.changeLogTable, scope));
        return existingSnapshot;
    }

    @Override
    public List<RanChangeSet> getRanChangeSets(Scope scope) throws LiquibaseException {
        RowBasedQueryResult result = (RowBasedQueryResult) scope.getSingleton(ActionExecutor.class).query(new SelectDataAction(changeLogTable, new SelectDataAction.SelectedColumn(null, "*", null, true)), scope);

        List<RanChangeSet> returnList = new ArrayList<>();
        for (RowBasedQueryResult.Row row : result.getRows()) {
            RanChangeSet ranChangeSet = toRanChangeSet(row, scope);
            if (ranChangeSet != null) {
                returnList.add(ranChangeSet);
            }
        }

        return returnList;

    }

    protected RanChangeSet toRanChangeSet(RowBasedQueryResult.Row row, Scope scope) {
        RanChangeSet ranChangeSet = new RanChangeSet();
        ranChangeSet.id = row.get(correctCase("id"), String.class);
        ranChangeSet.author = row.get(correctCase("author"), String.class);
        ranChangeSet.path = row.get(correctCase("filename"), String.class);
        ranChangeSet.dateExecuted = row.get(correctCase("dateexecuted"), Date.class);
        ranChangeSet.deploymentId = row.get(correctCase("deployment_id"), String.class);

        String contexts = StringUtil.trimToNull(row.get(correctCase("contexts"), String.class));
        if (contexts != null) {
            ranChangeSet.contextExpression = new ContextExpression(contexts);
        }

        String labels = StringUtil.trimToNull(row.get(correctCase("labels"), String.class));
        if (labels != null) {
            ranChangeSet.labels = new Labels(labels);
        }

        return ranChangeSet;
    }

    @Override
    public void removeFromHistory(String id, String author, String logicalPath, Scope scope) throws LiquibaseException {
        if (id == null || author == null || logicalPath == null) {
            throw new IllegalArgumentException("removeFromHistory requires id, author, and logicalPath");
        }

        DeleteDataAction action = new DeleteDataAction();
        action.relation = this.changeLogTable;

        DataType stringType = DataType.forType(String.class);
        DataTypeLogic stringDataTypeLogic = scope.getSingleton(DataTypeLogicFactory.class).getDataTypeLogic(stringType, scope);

        action.where.append(correctCase("id") + "=" + stringDataTypeLogic.toSql(id, stringType, scope));
        action.where.append(correctCase("author") + "=" + stringDataTypeLogic.toSql(author, stringType, scope));
        action.where.append(correctCase("filename") + "=" + stringDataTypeLogic.toSql(logicalPath, stringType, scope));

        scope.getSingleton(ActionExecutor.class).execute(action, scope);
    }

    @Override
    public void setExecType(ChangeSet changeSet, ChangeSet.ExecType execType, Scope scope) throws LiquibaseException {
        Logger log = LoggerFactory.getLogger(getClass());
        if (execType == ChangeSet.ExecType.FAILED) {
            log.info("Not marking failed changeSet as ran");
            return;
        }
        if (execType == ChangeSet.ExecType.SKIPPED) {
            log.info("Not marking skipped changeSet as ran");
            return;
        }

        Map<String, Object> data = new LinkedHashMap<>();

        data.put(correctCase("id"), changeSet.id);
        data.put(correctCase("author"), changeSet.author);
        data.put(correctCase("filename"), changeSet.getPath());

        data.put(correctCase("dateexecuted"), scope.getDatabase().getCurrentDateTimeFunction(scope));
        data.put(correctCase("md5sum"), "TODO");
        data.put(correctCase("exectype"), execType.value);
        data.put(correctCase("dateexecuted"), scope.getDatabase().getCurrentDateTimeFunction(scope));
        data.put(correctCase("orderexecuted"), 1); //todo
        data.put(correctCase("description"), "TODO"); //todo
        data.put(correctCase("comments"), "TODO"); //todo
        data.put(correctCase("contexts"), "TODO"); //todo
        data.put(correctCase("labels"), "TODO"); //todo
        data.put(correctCase("liquibase"), "TODO"); //todo
        data.put(correctCase("deployment_id"), "TODO"); //todo


        scope.getSingleton(ActionExecutor.class).execute(new InsertDataAction(new RowData(((StandardChangeLogHistoryService) scope.get(Scope.Attr.changeLogHistoryService, ChangeLogHistoryService.class)).changeLogTable, data)), scope);
    }

    @Override
    public void close() throws Exception {

    }

    protected String correctCase(String name) {
        if (upperCaseObjects) {
            return name.toUpperCase();
        } else {
            return name;
        }
    }

    protected Snapshot createWantedSnapshot(Scope scope) {
        Database database = scope.getDatabase();
        Table wantedVersionTable = new Table(this.changeLogTable.name, this.changeLogTable.getSchema());
        wantedVersionTable.tablespace = changeLogTablespace;
        Collection<Column> wantedVersionColumns = new ArrayList<>();

        wantedVersionColumns.add(new Column("id", this.changeLogTable, new DataType(getCharType(scope), getColumnSize("id", scope)), false));
        wantedVersionColumns.add(new Column("author", this.changeLogTable, new DataType(getCharType(scope), getColumnSize("author", scope)), false));
        wantedVersionColumns.add(new Column("filename", this.changeLogTable, new DataType(getCharType(scope), getColumnSize("filename", scope)), false));
        wantedVersionColumns.add(new Column("dateexecuted", this.changeLogTable, new DataType(getDateTimeType(scope)), false));
        wantedVersionColumns.add(new Column("orderexecuted", this.changeLogTable, new DataType(DataType.StandardType.INTEGER), false));
        wantedVersionColumns.add(new Column("exectype", this.changeLogTable, new DataType(getCharType(scope), getColumnSize("exectype", scope)), false));

        wantedVersionColumns.add(new Column("md5sum", this.changeLogTable, new DataType(getCharType(scope), getColumnSize("md5sum", scope)), true));
        wantedVersionColumns.add(new Column("description", this.changeLogTable, new DataType(getCharType(scope), getColumnSize("description", scope)), true));
        wantedVersionColumns.add(new Column("comments", this.changeLogTable, new DataType(getCharType(scope), getColumnSize("comments", scope)), true));
        wantedVersionColumns.add(new Column("tag", this.changeLogTable, new DataType(getCharType(scope), getColumnSize("tag", scope)), true));
        wantedVersionColumns.add(new Column("liquibase", this.changeLogTable, new DataType(getCharType(scope), getColumnSize("liquibase", scope)), true));
        wantedVersionColumns.add(new Column("labels", this.changeLogTable, new DataType(getCharType(scope), getColumnSize("labels", scope)), true));
        wantedVersionColumns.add(new Column("contexts", this.changeLogTable, new DataType(getCharType(scope), getColumnSize("contexts", scope)), true));
        wantedVersionColumns.add(new Column("deployment_id", this.changeLogTable, new DataType(getCharType(scope), getColumnSize("deployment_id", scope)), true));

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

    private DataType.StandardType getDateTimeType(Scope scope) {
        return DataType.StandardType.TIMESTAMP;
    }

    protected DataType.StandardType getCharType(Scope scope) {
        return DataType.StandardType.VARCHAR;
    }

    protected Integer getColumnSize(String columnName, Scope scope) {
        switch (columnName) {
            case "exectype":
                return 10;
            case "md5sum":
                return 35;
            case "liquibase":
                return 20;
            case "deployment_id":
                return 10;
            default:
                return 255;
        }
    }
}
