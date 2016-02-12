package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.core.*;
import liquibase.actionlogic.*;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.structure.datatype.DataType;
import liquibase.structure.datatype.DataTypeLogicFactory;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.*;
import liquibase.util.CollectionUtil;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;

import java.math.BigInteger;
import java.util.*;

public class AddColumnsLogic extends AbstractActionLogic<AddColumnsAction> {

    public enum Clauses {
        columnName,
        dataType,
        nullable,
        primaryKey,
        autoIncrement,
    }

    @Override
    protected Class<AddColumnsAction> getSupportedAction() {
        return AddColumnsAction.class;
    }

    @Override
    public ValidationErrors validate(final AddColumnsAction action, Scope scope) {
        Database database = scope.getDatabase();
        ValidationErrors errors = super.validate(action, scope)
                .checkRequiredFields("columns", "columns.name", "columns.type",
                        "columns.table", "columns.table.name");

        if (!database.supportsAutoIncrement()) {
            errors.checkUnsupportedFields("columns.autoIncrement");
        }


        errors.checkField("columns", new ValidationErrors.FieldCheck<Column>() {
            @Override
            public String check(Column column) {
                if (column.isAutoIncrement()) {
                    if (action.primaryKey != null && action.primaryKey.columns.size() > 1) {
                        return "cannot add a multi-column primary key and mark a column as auto-increment";
                    }
                    if (column.defaultValue != null) {
                        return "cannot set both a default value and auto-increment";
                    } else if (!isPrimaryKey(column, action)) {
                        return "auto-increment columns must be primary keys";
                    }
                } else if (column.nullable != null && column.nullable && isPrimaryKey(column, action)) {
                    return "primary key columns cannot be nullable";
                }
                return null;
            }
        });

        if (!errors.hasErrors() && CollectionUtil.createIfNull(action.foreignKeys).size() > 0) {
            errors.addAll(scope.getSingleton(ActionExecutor.class).validate(createAddForeignKeysAction(action), scope), "foreignKeys");

            errors.checkField("foreignKeys", new ValidationErrors.FieldCheck<ForeignKey>() {
                @Override
                public String check(ForeignKey fk) {
                    if (fk.updateRule == ForeignKey.ConstraintType.importedKeySetNull) {
                        for (ForeignKey.ForeignKeyColumnCheck check : fk.columnChecks) {
                            for (Column column : action.columns) {
                                if (column.name.equals(check.baseColumn)) {
                                    if (column.nullable != null && !column.nullable) {
                                        return "cannot use update rule 'set null' on a not null column";
                                    } else {
                                        return null;
                                    }
                                }
                            }
                        }
                    }

                    if (fk.deleteRule == ForeignKey.ConstraintType.importedKeySetNull) {
                        for (ForeignKey.ForeignKeyColumnCheck check : fk.columnChecks) {
                            for (Column column : action.columns) {
                                if (column.name.equals(check.baseColumn)) {
                                    if (column.nullable != null && !column.nullable) {
                                        return "cannot use delete rule 'set null' on a not null column";
                                    } else {
                                        return null;
                                    }
                                }
                            }
                        }
                    }
                    return null;
                }
            });
        }

        if (!errors.hasErrors() && CollectionUtil.createIfNull(action.uniqueConstraints).size() > 0) {
            AddUniqueConstraintsAction addUniqueConstraintsAction = createAddUniqueConstraintsAction(action);
            errors.addAll(scope.getSingleton(ActionExecutor.class).validate(addUniqueConstraintsAction, scope), "uniqueConstraints");
        }

        if (!errors.hasErrors() && action.primaryKey != null) {
            AddPrimaryKeysAction addPrimaryKeysAction = createAddPrimaryKeysAction(action);
            errors.addAll(scope.getSingleton(ActionExecutor.class).validate(addPrimaryKeysAction, scope), "primaryKeys", "primaryKey");
        }

        if (!errors.hasErrors()) {
            int autoIncColumns = 0;
            for (Column column : action.columns) {
                if (column.autoIncrementInformation != null) {
                    autoIncColumns++;
                    if (column.type.standardType != null && !(column.type.standardType.valueType.equals(Integer.class) || column.type.standardType.valueType.equals(BigInteger.class))) {
                        errors.addUnsupportedError("a non-integer auto-increment column");
                    }
                    if (column.nullable != null && column.nullable) {
                        errors.addUnsupportedError("a nullable auto-increment column");
                    }
                    if (column.defaultValue != null) {
                        errors.addUnsupportedError("a default value on an auto-increment column");
                    }
                }
            }
            if (autoIncColumns > 1) {
                errors.addError("There can be only one auto-increment column");
            }
        }

        return errors;
    }

    protected AddPrimaryKeysAction createAddPrimaryKeysAction(AddColumnsAction action) {
        if (action.primaryKey == null) {
            return null;
        }
        return new AddPrimaryKeysAction(action.primaryKey);
    }

    protected AddUniqueConstraintsAction createAddUniqueConstraintsAction(AddColumnsAction action) {
        if (action.uniqueConstraints == null) {
            return null;
        }

        return new AddUniqueConstraintsAction(action.uniqueConstraints.toArray(new UniqueConstraint[action.uniqueConstraints.size()]));
    }

    protected AddForeignKeysAction createAddForeignKeysAction(AddColumnsAction action) {
        return new AddForeignKeysAction(action.foreignKeys.toArray(new ForeignKey[action.foreignKeys.size()]));
    }

    @Override
    public ActionStatus checkStatus(AddColumnsAction action, Scope scope) {
        ActionStatus result = new ActionStatus();

        List<Column> snapshotColumns = new ArrayList<>();
        ActionExecutor executor = scope.getSingleton(ActionExecutor.class);
        try {
            for (Column actionColumn : action.columns) {
                snapshotColumns.add(executor.query(new SnapshotObjectsAction(actionColumn.toReference()), scope).asObject(Column.class));
            }

            PrimaryKey snapshotPK = null;
            if (action.primaryKey != null) {
                snapshotPK = executor.query(new SnapshotObjectsAction(PrimaryKey.class, action.primaryKey.table), scope).asObject(PrimaryKey.class);
            }

            for (int i = 0; i < snapshotColumns.size(); i++) {
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
                }
                result.assertCorrect(actionColumn.type.standardType == snapshotColumn.type.standardType, "column.type is incorrect");
            }

            if (action.primaryKey != null) {
                AddPrimaryKeysAction addPrimaryKeysAction = createAddPrimaryKeysAction(action);
                result.addAll(executor.checkStatus(addPrimaryKeysAction, scope));
            }

            if (action.foreignKeys != null && action.foreignKeys.size() > 0) {
                AddForeignKeysAction addForeignKeysAction = createAddForeignKeysAction(action);
                result.addAll(executor.checkStatus(addForeignKeysAction, scope));
            }

            if (action.uniqueConstraints != null && action.uniqueConstraints.size() > 0) {
                AddUniqueConstraintsAction addUniqueConstraintsAction = createAddUniqueConstraintsAction(action);
                result.addAll(executor.checkStatus(addUniqueConstraintsAction, scope));
            }
        } catch (ActionPerformException e) {
            return result.unknown(e);
        }

        return result;
    }

    protected boolean assertDataTypesCorrect(Column actionColumn, Column snapshotColumn, Scope scope) {
        return actionColumn.type.standardType == snapshotColumn.type.standardType;
    }


    /**
     * Creates action(s) to create the columns and other needed objects.
     * Calls out to {@link #createAddColumnsActions(AddColumnsAction, Scope, List)} etc. passing in the list of actions built up so far in case the list or other actions should be modified for performance reasons.
     */
    @Override
    public ActionResult execute(AddColumnsAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();

        createAddColumnsActions(action, scope, actions);
        createUniqueConstraintActions(action, scope, actions);
        createForeignKeyActions(action, scope, actions);
        createPrimaryKeyActions(action, scope, actions);

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected void createAddColumnsActions(AddColumnsAction action, Scope scope, List<Action> actions) {
        for (Column column : action.columns) {
            actions.add(new AlterTableAction(
                    column.table,
                    getColumnClause(column, action, scope)));
        }
    }

    protected StringClauses getColumnClause(Column column, AddColumnsAction action, Scope scope) {
        StringClauses clauses = new StringClauses(" ");
        Database database = scope.getDatabase();

        ObjectReference columnName = column.toReference();
        DataType columnType = column.type;

        boolean markPrimaryKey = canInlinePrimaryKey(action) && isPrimaryKey(column, action);
        boolean nullable = ObjectUtil.defaultIfEmpty(column.nullable, true); // markPrimaryKey || ObjectUtil.defaultIfEmpty(column.nullable, false);
//        String addAfterColumn = column.addAfterColumn;

        clauses.append("ADD")
                .append(Clauses.columnName, database.escapeObjectName(columnName.name, Column.class))
                .append(Clauses.dataType, scope.getSingleton(DataTypeLogicFactory.class).getDataTypeLogic(column.type, scope).toSql(columnType, scope))
                .append(getDefaultValueClause(column, action, scope));

        if (column.autoIncrementInformation != null) {
            ActionLogic addAutoIncrementLogic = scope.getSingleton(ActionLogicFactory.class).getActionLogic(new AddAutoIncrementAction(), scope);
            if (addAutoIncrementLogic != null && addAutoIncrementLogic instanceof AddAutoIncrementLogic) {
                clauses.append(Clauses.autoIncrement, ((AddAutoIncrementLogic) addAutoIncrementLogic).generateAutoIncrementClause(column.autoIncrementInformation));
            } else {
                throw new UnexpectedLiquibaseException("Cannot use AddAutoIncrementLogic class " + addAutoIncrementLogic + " to build auto increment clauses");
            }
        }

        if (nullable) {
            if (database.requiresDefiningColumnsAsNull()) {
                clauses.append(Clauses.nullable, "NULL");
            }
        } else {
            clauses.append(Clauses.nullable, "NOT NULL");
        }

        clauses.append(Clauses.primaryKey, markPrimaryKey ? "PRIMARY KEY" : null);

//        if (addAfterColumn != null) {
//            clauses.append("AFTER " + database.escapeObjectName(addAfterColumn, Column.class));
//        }

        return clauses;
    }

    protected boolean canInlinePrimaryKey(AddColumnsAction action) {
        return action.primaryKey != null && CollectionUtil.createIfNull(action.primaryKey.columns).size() == 1;
    }

    protected boolean isPrimaryKey(Column column, AddColumnsAction action) {
        return action.primaryKey != null && action.primaryKey.containsColumn(column);
    }

    protected void createPrimaryKeyActions(AddColumnsAction action, Scope scope, List<Action> returnActions) {
        if (action.primaryKey != null && action.primaryKey.columns.size() > 1) {
            returnActions.add(new AddPrimaryKeysAction(action.primaryKey));
        }
    }

    protected void createUniqueConstraintActions(AddColumnsAction action, Scope scope, List<Action> returnActions) {
        List<UniqueConstraint> constraints = CollectionUtil.createIfNull(action.uniqueConstraints);
        for (UniqueConstraint constraint : constraints) {
            returnActions.add(new AddUniqueConstraintsAction(constraint));
        }
    }

    protected void createForeignKeyActions(AddColumnsAction action, Scope scope, List<Action> returnActions) {
        List<ForeignKey> constraints = CollectionUtil.createIfNull(action.foreignKeys);

        for (ForeignKey fkConstraint : constraints) {
            returnActions.add(new AddForeignKeysAction(fkConstraint));
        }
    }

    protected String getDefaultValueClause(Column column, AddColumnsAction action, Scope scope) {
        Object defaultValue = column.defaultValue;
        if (defaultValue != null) {
            return "DEFAULT " + scope.getSingleton(DataTypeLogicFactory.class).getDataTypeLogic(column.type, scope).toSql(defaultValue, column.type, scope);
        }
        return null;
    }
}
