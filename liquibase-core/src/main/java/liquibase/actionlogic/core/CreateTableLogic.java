package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.*;
import liquibase.actionlogic.*;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.structure.core.*;
import liquibase.structure.datatype.DataTypeLogicFactory;
import liquibase.util.CollectionUtil;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
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
    public ValidationErrors validate(final CreateTableAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope)
                .checkRequiredFields("columns", "table", "table.name")
                .checkUnsupportedFields("table.tablespace");

        if (!errors.hasErrors()) {
            errors.addAll(scope.getSingleton(ActionExecutor.class).validate(createAddColumnsAction(action), scope), null);
        }
        return errors;
    }

    protected AddColumnsAction createAddColumnsAction(CreateTableAction action) {
        AddColumnsAction addColumnsAction = new AddColumnsAction();
        addColumnsAction.columns = action.columns;
        addColumnsAction.primaryKey = action.primaryKey;
        addColumnsAction.uniqueConstraints = action.uniqueConstraints;
        addColumnsAction.foreignKeys = action.foreignKeys;
        return addColumnsAction;
    }

    @Override
    public ActionStatus checkStatus(CreateTableAction action, Scope scope) {
        ActionStatus result = new ActionStatus();

        try {
            Table snapshotTable = scope.getSingleton(ActionExecutor.class).query(new SnapshotObjectsAction(action.table.toReference()), scope).asObject(Table.class);
            if (snapshotTable == null) {
                return result.assertCorrect(false, "Table "+action.table.toReference()+" was not found");
            }
            List<Column> snapshotColumns = scope.getSingleton(ActionExecutor.class).query(new SnapshotObjectsAction(Column.class, action.table.toReference()), scope).asList(Column.class);

            result.assertCorrect(action.table, snapshotTable);
            result.assertCorrect(action.columns.size(), snapshotColumns.size(), "Column size incorrect");



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
        createTable.append(Clauses.tableName, database.quoteObjectName(action.table.toReference(), scope));

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
            if (database.supports(Database.Feature.NAMED_PRIMARY_KEYS, scope)) {
                String pkName = action.primaryKey.getName();
                if (pkName != null) {
                    primaryKey.append("CONSTRAINT");
                    primaryKey.append(database.quoteObjectName(pkName, Index.class, scope));
                }
            }
            primaryKey.append("PRIMARY KEY");
            StringClauses columnClauses = new StringClauses("(", ", ", ")");
            for (PrimaryKey.PrimaryKeyColumn col : action.primaryKey.columns) {
                String colDef = scope.getDatabase().quoteObjectName(col.name, Column.class, scope);
                if (col.direction!= null) {
                    colDef += " "+col.direction;
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
        if (tablespace != null && database.supports(Database.Feature.TABLESPACES, scope)) {
            createTable.append(Clauses.tablespace, "TABLESPACE " + tablespace);
        }

        return createTable;
    }

    protected StringClauses generateUniqueConstraintSql(UniqueConstraint uniqueConstraint, CreateTableAction action, Scope scope) {
        Database database = scope.getDatabase();
        StringClauses clauses = new StringClauses();
        String constraintName = uniqueConstraint.getName();

        if (constraintName != null) {
            clauses.append(UniqueConstraintClauses.constraintName, "CONSTRAINT " + database.quoteObjectName(constraintName, Index.class, scope));
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
        StringClauses columnClause = new StringClauses().append(database.quoteObjectName(columnName, Column.class, scope));
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
            if (database.supports(Database.Feature.AUTO_INCREMENT, scope)) {
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
            if (((AddColumnsLogic) scope.getSingleton(ActionLogicFactory.class).getActionLogic(createAddColumnsAction(action), scope)).requiresDefiningColumnsAsNull()) {
                columnClause.append(ColumnClauses.nullable, "NULL");
            }
        } else {
            columnClause.append(ColumnClauses.nullable, "NOT NULL");
        }

        return columnClause;
    }


}
