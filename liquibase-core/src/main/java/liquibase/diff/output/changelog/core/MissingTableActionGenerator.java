package liquibase.diff.output.changelog.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.CreateTableAction;
import liquibase.database.DatabaseFactory;
import liquibase.diff.output.changelog.MissingObjectActionGenerator;
import liquibase.exception.ActionPerformException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.snapshot.Snapshot;
import liquibase.snapshot.SnapshotFactory;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Table;

import java.util.Arrays;
import java.util.List;

public class MissingTableActionGenerator implements MissingObjectActionGenerator {

    @Override
    public int getPriority(Class<? extends LiquibaseObject> objectType, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        if (Table.class.isAssignableFrom(objectType)) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public Class<? extends LiquibaseObject>[] runAfterTypes() {
        return null;
    }

    @Override
    public Class<? extends LiquibaseObject>[] runBeforeTypes() {
        return null;
    }

    @Override
    public List<? extends Action> fixMissing(LiquibaseObject missingObject, Snapshot referenceSnapshot, Snapshot targetSnapshot, Scope scope) {
        try {
            Table missingTable = (Table) missingObject;

            Scope referenceOfflineDatabaseScope = scope.child(Scope.Attr.database, scope.getSingleton(DatabaseFactory.class).fromSnapshot(referenceSnapshot, scope));

            SnapshotFactory snapshotFactory = scope.getSingleton(SnapshotFactory.class);

            PrimaryKey primaryKey  = snapshotFactory.snapshot(PrimaryKey.class, missingTable.toReference(), referenceOfflineDatabaseScope);

//        if (control.diffResult.getReferenceSnapshot().getDatabase().isLiquibaseTable(missingTable.getSchema().toCatalogAndSchema(), missingTable.getName())) {
//            continue;
//        }

            CreateTableAction action = createCreateTableChange();
            action.table = (Table) missingTable.clone();
            action.primaryKey = primaryKey;

            for (Column column : snapshotFactory.snapshotAll(Column.class, missingTable.toReference(), referenceOfflineDatabaseScope)) {
                action.columns.add(column);
    //            columnDefinition.column = column.getName();
    //
    //            LiquibaseDataType ldt = DataTypeFactory.getInstance().from(column.type, targetScope.getDatabase());
    //            DatabaseDataType ddt = ldt.toDatabaseDataType(referenceScope.getDatabase());
    //            columnDefinition.columnType = ddt.toString();
    //
    //            if (column.isAutoIncrement()) {
    //                columnDefinition.autoIncrementInformation = new AutoIncrementDefinition();
    //            }
    //
    //            // In MySQL, the primary key must be specified at creation for an autoincrement column
    //            if (column.isAutoIncrement() && primaryKey != null && primaryKey.getColumnNamesAsList().contains(column.getName())) {
    //                columnDefinition.isPrimaryKey = true;
    //                action.primaryKeyTablespace = primaryKey.getTablespace();
    ////todo:         action refactoring MySQL sets some primary key names as PRIMARY which is invalid
    ////                if (comparisonDatabase instanceof MySQLDatabase && "PRIMARY".equals(primaryKey.getName())) {
    ////                    constraintsConfig.setPrimaryKeyName(null);
    ////                } else  {
    //                    action.primaryKeyName = primaryKey.getSimpleName();
    ////                }
    //                control.setAlreadyHandledMissing(primaryKey);
    //                control.setAlreadyHandledMissing(primaryKey.getBackingIndex());
    //            } else if (column.nullable != null && !column.nullable) {
    //                columnDefinition.nullable = false;
    //            }
    //
    //            setDefaultValue(columnDefinition, column, referenceScope, targetScope);
    //
    //            if (column.remarks != null) {
    //                columnDefinition.remarks = column.remarks;
    //            }
    //
    //            action.addColumn(column);
    //            control.setAlreadyHandledMissing(column);
            }

            return Arrays.asList((Action) action);
        } catch (ActionPerformException e) {
            throw new UnexpectedLiquibaseException(e);
        }


    }

    public static void setDefaultValue(Column column, Scope referenceScope, Scope targetScope) {
//        LiquibaseDataType dataType = DataTypeFactory.getInstance().from(column.type, targetScope.getDatabase());

        Object defaultValue = column.defaultValue;
//todo: action refactoring        if (defaultValue == null) {
//            // do nothing
//        } else if (column.isAutoIncrement()) {
//            // do nothing
//        } else if (defaultValue instanceof Date) {
//            columnDefinition.setDefaultValueDate((Date) defaultValue);
//        } else if (defaultValue instanceof Boolean) {
//            columnDefinition.setDefaultValueBoolean(((Boolean) defaultValue));
//        } else if (defaultValue instanceof Number) {
//            columnDefinition.setDefaultValueNumeric(((Number) defaultValue));
//        } else if (defaultValue instanceof DatabaseFunction) {

//            DatabaseFunction function = (DatabaseFunction) defaultValue;
//            if ("current".equals(function.getValue())) {
//              if (database instanceof InformixDatabase) {
//                if (dataType instanceof DateTimeType) {
//                  if (dataType.getAdditionalInformation() == null || dataType.getAdditionalInformation().length() == 0) {
//                    if (dataType.getParameters() != null && dataType.getParameters().length > 0) {
//
//                      String parameter = String.valueOf(dataType.getParameters()[0]);
//
//                      if ("4365".equals(parameter)) {
//                        function = new DatabaseFunction("current year to fraction(3)");
//                      }
//
//                      if ("3594".equals(parameter)) {
//                        function = new DatabaseFunction("current year to second");
//                      }
//
//                      if ("3080".equals(parameter)) {
//                        function = new DatabaseFunction("current year to minute");
//                      }
//
//                      if ("2052".equals(parameter)) {
//                        function = new DatabaseFunction("current year to day");
//                      }
//                    }
//                  }
//                }
//              }
//            }

//            columnDefinition.setDefaultValueComputed(function);
//        } else {
//            columnDefinition.set(ColumnDefinition.Attr.defaultValue, defaultValue.toString());
//        }
    }

    protected CreateTableAction createCreateTableChange() {
        return new CreateTableAction();
    }
}
