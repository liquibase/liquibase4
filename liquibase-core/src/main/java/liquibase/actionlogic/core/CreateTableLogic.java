package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.*;
import liquibase.actionlogic.*;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.exception.ValidationErrors;
import liquibase.structure.core.*;
import liquibase.structure.datatype.DataTypeLogicFactory;
import liquibase.util.CollectionUtil;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateTableLogic extends AbstractSqlBuilderLogic<CreateTableAction> {

    public enum Clauses {
        tableName, columnsClause, primaryKey, tablespace, foreignKeyClauses, uniqueConstraintClauses, mainClauses,
    }

    public enum ColumnClauses {
        autoIncrement, nullable, defaultValue
    }

    public enum UniqueConstraintClauses {
        constraintName, columns
    }

    public enum ForeignKeyClauses {
        constraintName, referencesTarget, columns
    }

    @Override
    protected Class<CreateTableAction> getSupportedAction() {
        return CreateTableAction.class;
    }

    @Override
    public ValidationErrors validate(CreateTableAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope)
                .checkRequiredFields(action, "table")
                .checkRequiredFields(action, "columns")

                .checkRequiredFields(action.table, "name")
                .checkUnsupportedFields(action.table, "tablespace")

                .checkRequiredFields(action.columns, "name")
                .checkRequiredFields(action.columns, "type");

        if (!errors.hasErrors() && action.foreignKeys != null) {
            AddForeignKeysAction addForeignKeysAction = createAddForeignKeysAction(action);
            errors.addAll(scope.getSingleton(ActionExecutor.class).validate(addForeignKeysAction, scope));

            for (ForeignKey fk : action.foreignKeys) {
                if (fk != null && fk.updateRule == ForeignKey.ConstraintType.importedKeySetNull) {
                    for (ForeignKey.ForeignKeyColumnCheck check : fk.columnChecks) {
                        for (Column column : action.columns) {
                            if (column.name.equals(check.baseColumn) && ObjectUtil.defaultIfEmpty(column.nullable, false)) {
                                errors.addError("Cannot set foreign key update rule to 'set null' on a not null field");
                            }
                        }
                    }
                }
            }
        }

        if (!errors.hasErrors() && action.uniqueConstraints != null) {
            AddUniqueConstraintsAction addUniqueConstraintsAction = createAddUniqueConstraintsAction(action);
            errors.addAll(scope.getSingleton(ActionExecutor.class).validate(addUniqueConstraintsAction, scope));
        }

        if (!errors.hasErrors() && action.primaryKey != null) {
            AddPrimaryKeysAction addPrimaryKeysAction = createAddPrimaryKeysAction(action);
            errors.addAll(scope.getSingleton(ActionExecutor.class).validate(addPrimaryKeysAction, scope));
        }

        if (!errors.hasErrors()) {
            int autoIncColumns = 0;
            for (Column column : action.columns) {
                if (column.autoIncrementInformation != null) {
                    autoIncColumns++;
                    if (column.type.standardType != null && !(column.type.standardType.valueType.equals(Integer.class) || column.type.standardType.valueType.equals(BigInteger.class))) {
                        errors.addError("Cannot set a non-integer column as auto-increment");
                    }
                    if (column.nullable != null && column.nullable) {
                        errors.addError("Cannot set a nullable column as auto-increment");
                    }
                    if (column.defaultValue != null) {
                        errors.addError("cannot set a default value on an auto-increment column");
                    }
                }
            }
            if (autoIncColumns > 1) {
                errors.addError("There can be only one auto-increment column");
            }
        }
        return errors;
    }

    protected AddPrimaryKeysAction createAddPrimaryKeysAction(CreateTableAction action) {
        return new AddPrimaryKeysAction(action.primaryKey);
    }

    protected AddUniqueConstraintsAction createAddUniqueConstraintsAction(CreateTableAction action) {
        if (action.uniqueConstraints == null) {
            return null;
        }

        AddUniqueConstraintsAction addConstraintsAction = new AddUniqueConstraintsAction(action.uniqueConstraints.toArray(new UniqueConstraint[action.uniqueConstraints.size()]));
        for (UniqueConstraint uq : addConstraintsAction.uniqueConstraints) {
            if (uq == null) {
                continue;
            }
            uq.table = action.table.toReference();
        }

        return addConstraintsAction;
    }

    protected AddForeignKeysAction createAddForeignKeysAction(CreateTableAction action) {
        AddForeignKeysAction addForeignKeysAction = new AddForeignKeysAction(action.foreignKeys.toArray(new ForeignKey[action.foreignKeys.size()]));
        for (ForeignKey fk : addForeignKeysAction.foreignKeys) {
            if (fk == null) {
                continue;
            }
            fk.table = action.table.toReference();
        }
        return addForeignKeysAction;
    }

    @Override
    public ActionStatus checkStatus(CreateTableAction action, Scope scope) {
        ActionStatus result = new ActionStatus();

        try {
            Table snapshotTable = scope.getSingleton(ActionExecutor.class).query(new SnapshotObjectsAction(action.table.toReference()), scope).asObject(Table.class);
            List<Column> snapshotColumns = scope.getSingleton(ActionExecutor.class).query(new SnapshotObjectsAction(Column.class, action.table.toReference()), scope).asList(Column.class);
            PrimaryKey snapshotPK = scope.getSingleton(ActionExecutor.class).query(new SnapshotObjectsAction(PrimaryKey.class, action.table.toReference()), scope).asObject(PrimaryKey.class);

            result.assertCorrect(action.table, snapshotTable);
            result.assertCorrect(action.columns.size(), snapshotColumns.size(), "Column size incorrect");

            boolean hasAutoIncrement = false;
            for (int i=0; i<snapshotColumns.size(); i++) {
                List<String> excludeFields = new ArrayList<>(Arrays.asList("type", "autoIncrementInformation", "nullable", "table"));

                Column actionColumn = action.columns.get(i);
                Column snapshotColumn = snapshotColumns.get(i);

                if (actionColumn.nullable == null && snapshotColumn.isAutoIncrement() || snapshotPK != null && snapshotPK.columns.contains(snapshotColumn.name)) {
                    excludeFields.add("nullable"); //did not specify nullable, and auto-increment and/or PK usually auto-adds it but not always. Cannot check
                    excludeFields.add("defaultValue"); //if auto-increment and/or pk, defaultValue is sometimes unexpected
                }

                result.assertCorrect(actionColumn, snapshotColumn, excludeFields);
                result.assertCorrect(actionColumn.nullable, actionColumn.nullable, "column.nullable is incorrect");
                if (actionColumn.isAutoIncrement()) {
                    result.assertCorrect(snapshotColumn.isAutoIncrement(), "Column is not auto-increment");
                    hasAutoIncrement = true;
                }
                result.assertCorrect(actionColumn.type.standardType == snapshotColumn.type.standardType, "column.type is incorrect");
            }

            if (action.primaryKey == null) {
                if (!hasAutoIncrement) {  //sometimes PKs are created automatically if a column is marked auto-increment
                    result.assertCorrect(snapshotPK == null, "Unexpected primary key created");
                }
            } else {
                AddPrimaryKeysAction addPrimaryKeysAction = createAddPrimaryKeysAction(action);
                result.addAll(scope.getSingleton(ActionExecutor.class).checkStatus(addPrimaryKeysAction, scope));
            }

            if (action.foreignKeys != null && action.foreignKeys.size() > 0) {
                AddForeignKeysAction addForeignKeysAction = createAddForeignKeysAction(action);
                result.addAll(scope.getSingleton(ActionExecutor.class).checkStatus(addForeignKeysAction, scope));
            }

            if (action.uniqueConstraints != null && action.uniqueConstraints.size() > 0) {
                AddUniqueConstraintsAction addUniqueConstraintsAction = createAddUniqueConstraintsAction(action);
                result.addAll(scope.getSingleton(ActionExecutor.class).checkStatus(addUniqueConstraintsAction, scope));
            }

            return result;
        } catch (ActionPerformException e) {
            return result.unknown(e);
        }
    }

    @Override
    public ActionResult execute(CreateTableAction action, Scope scope) throws ActionPerformException {
        return new DelegateResult(action, null, new ExecuteSqlAction(generateSql(action, scope).toString()));
    }

    @Override
    protected StringClauses generateSql(CreateTableAction action, Scope scope) {
        Database database = scope.getDatabase();

        List<Action> additionalActions = new ArrayList<>();

        StringClauses createTable = new StringClauses(" ");
        createTable.append("CREATE TABLE");
        createTable.append(Clauses.tableName, database.escapeObjectName(action.table.toReference()));

        List<Column> columns = CollectionUtil.createIfNull(action.columns);
//        for (Column column : columns) {
//            if (ObjectUtil.defaultIfEmpty(column.isPrimaryKey, false)) {
//                primaryKeyColumnNames.add(column.column.name);
//            }
//        }

        StringClauses tableDefinition = new StringClauses("(", ", ", ")");
        int i = 0;
        for (Column column : columns) {
            StringClauses columnClause = generateColumnSql(column, action, scope, additionalActions);

            tableDefinition.append("column " + i++, columnClause);
        }

//        if (!( (database instanceof SQLiteDatabase) &&
//                isSinglePrimaryKeyColumn &&
//                isPrimaryKeyAutoIncrement) &&
//
//                !((database instanceof InformixDatabase) &&
//                isSinglePrimaryKeyColumn
//                )) {
        // ...skip this code block for sqlite if a single column primary key
        // with an autoincrement constraint exists.
        // This constraint is added after the column type.

        if (action.primaryKey != null) {
            StringClauses primaryKey = new StringClauses(" ");
            if (database.supportsPrimaryKeyNames()) {
                String pkName = action.primaryKey.getName();
                if (pkName == null) {
                    pkName = database.generatePrimaryKeyName(action.table.getName());
                }
                if (pkName != null) {
                    primaryKey.append("CONSTRAINT");
                    primaryKey.append(database.escapeObjectName(pkName, Index.class));
                }
            }
            primaryKey.append("PRIMARY KEY");
            StringClauses columnClauses = new StringClauses("(", ", ", ")");
            for (PrimaryKey.PrimaryKeyColumn col : action.primaryKey.columns) {
                String colDef = scope.getDatabase().escapeObjectName(col.name, Column.class);
                if (col.descending != null) {
                    if (col.descending) {
                        colDef += " DESC";
                    } else {
                        colDef += " ASC";
                    }
                }
                columnClauses.append(colDef);
            }
            primaryKey.append("columns", columnClauses);

            tableDefinition.append(Clauses.primaryKey, primaryKey);
        }
//        }

        StringClauses foreignKeyClauses = new StringClauses(", ");
        int fkNum = 1;
        for (ForeignKey fk : CollectionUtil.createIfNull(action.foreignKeys)) {
            if (fk != null) {
                foreignKeyClauses.append("foreignKey" + (fkNum++), generateForeignKeySql(fk, action, scope));
            }
        }
        tableDefinition.append(Clauses.foreignKeyClauses, foreignKeyClauses);

        StringClauses uniqueConstraintClauses = new StringClauses(", ");
        int uqNum = 1;
        for (UniqueConstraint uq : CollectionUtil.createIfNull(action.uniqueConstraints)) {
            if (uq != null) {
                foreignKeyClauses.append("uniqueConstraint" + (uqNum++), generateUniqueConstraintSql(uq, action, scope));
            }
        }
        tableDefinition.append(Clauses.uniqueConstraintClauses, uniqueConstraintClauses);
//    }


//        String sql = buffer.toString().replaceFirst(",\\s*$", "")+")";
//
//        if (database instanceof MySQLDatabase && mysqlTableOptionStartWith != null){
//        	LoggerFactory.getLogger(getClass()).info("[MySQL] Using last startWith statement ("+mysqlTableOptionStartWith.toString()+") as table option.");
//        	sql += " "+((MySQLDatabase)database).getTableOptionAutoIncrementStartWithClause(mysqlTableOptionStartWith);
//        }


//        if (StringUtils.trimToNull(tablespace) != null && database.supportsTablespaces()) {
//            if (database instanceof MSSQLDatabase) {
//                buffer.append(" ON ").append(tablespace);
//            } else if (database instanceof DB2Database) {
//                buffer.append(" IN ").append(tablespace);
//            } else {
//                buffer.append(" TABLESPACE ").append(tablespace);
//            }
//        }

        createTable.append(Clauses.columnsClause, tableDefinition);

        String tablespace = action.table.tablespace;
        if (tablespace != null && database.supportsTablespaces()) {
            createTable.append(Clauses.tablespace, "TABLESPACE " + tablespace);
        }

        return createTable;
    }

    protected StringClauses generateUniqueConstraintSql(UniqueConstraint uniqueConstraint, CreateTableAction action, Scope scope) {
        Database database = scope.getDatabase();
        StringClauses clauses = new StringClauses();
        String constraintName = uniqueConstraint.getName();

        if (constraintName != null) {
            clauses.append(UniqueConstraintClauses.constraintName, "CONSTRAINT " + database.escapeObjectName(constraintName, Index.class));
        }

        clauses.append("UNIQUE");

        clauses.append(UniqueConstraintClauses.columns, new StringClauses("(", ", ", ")").append(uniqueConstraint.columns, Column.class, scope));

        return clauses;
    }

    protected StringClauses generateForeignKeySql(ForeignKey foreignKey, CreateTableAction action, Scope scope) {
        AddForeignKeysAction addForeignKeysAction = new AddForeignKeysAction(foreignKey);
        AddForeignKeysLogic addKeyLogic = (AddForeignKeysLogic) scope.getSingleton(ActionLogicFactory.class).getActionLogic(addForeignKeysAction, scope);
        return addKeyLogic.generateConstraintClause(foreignKey, addForeignKeysAction, scope).remove("ADD");
    }

    protected StringClauses generateColumnSql(Column column, CreateTableAction action, Scope scope, List<Action> additionalActions) {
        Database database = scope.getDatabase();

        String columnName = column.getName();
        StringClauses columnClause = new StringClauses().append(database.escapeObjectName(columnName, Column.class));
        columnClause.append(column.type.toString());

        Column.AutoIncrementInformation autoIncrementInformation = column.autoIncrementInformation;


//        boolean isPrimaryKeyColumn = ObjectUtil.defaultIfEmpty(column.isPrimaryKey, false);
//        boolean isPrimaryKeyAutoIncrement = isPrimaryKeyColumn && isAutoIncrementColumn;

//            if ((database instanceof SQLiteDatabase) &&
//                    isSinglePrimaryKeyColumn &&
//                    isPrimaryKeyColumn &&
//                    isAutoIncrementColumn) {
//                String pkName = StringUtils.trimToNull(statement.getPrimaryKeyConstraint().getConstraintName());
//                if (pkName == null) {
//                    pkName = database.generatePrimaryKeyName(statement.getTableName());
//                }
//                if (pkName != null) {
//                    buffer.append(" CONSTRAINT ");
//                    buffer.append(database.escapeConstraintName(pkName));
//                }
//                buffer.append(" PRIMARY KEY");
//            }

        Object defaultValue = column.defaultValue;

        // auto-increment columns, there should be no default value
        if (defaultValue != null && autoIncrementInformation == null) {
            String defaultValueString = scope.getSingleton(DataTypeLogicFactory.class).getDataTypeLogic(column.type, scope).toSql(defaultValue, column.type, scope);
            columnClause.append(ColumnClauses.defaultValue, "DEFAULT " + defaultValueString);
        }

        if (autoIncrementInformation != null) {
            // TODO: check if database supports auto increment on non primary key column
            if (database.supportsAutoIncrement()) {
                BigInteger startWith = autoIncrementInformation.startWith;
                BigInteger incrementBy = autoIncrementInformation.incrementBy;
                ActionLogic addAutoIncrementLogic = scope.getSingleton(ActionLogicFactory.class).getActionLogic(new AddAutoIncrementAction(), scope);

                StringClauses autoIncrementClause = ((AddAutoIncrementLogic) addAutoIncrementLogic).generateAutoIncrementClause(new Column.AutoIncrementInformation(startWith, incrementBy));

                columnClause.append(ColumnClauses.autoIncrement, autoIncrementClause);
//                if (!"".equals(autoIncrementClause)) {
//                    columnClause.append(ColumnClauses.autoIncrement, autoIncrementClause);
//                }

//                if( autoIncrementConstraint.getStartWith() != null ){
//                    if (database instanceof MySQLDatabase){
//                        mysqlTableOptionStartWith = autoIncrementConstraint.getStartWith();
//                    }
//                }
            } else {
                LoggerFactory.getLogger(getClass()).warn(database.getShortName() + " does not support autoincrement columns as requested for " + action.table.name);
            }
        }

        boolean nullable = ObjectUtil.defaultIfEmpty(column.nullable, true); //ObjectUtil.defaultIfEmpty(column.isPrimaryKey, false) || ObjectUtil.defaultIfEmpty(column.nullable, false);

        if (nullable) {
            if (database.requiresDefiningColumnsAsNull()) {
                columnClause.append(ColumnClauses.nullable, "NULL");
            }
        } else {
            columnClause.append(ColumnClauses.nullable, "NOT NULL");
        }

        return columnClause;
    }


}
