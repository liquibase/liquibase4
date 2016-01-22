package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.core.AddPrimaryKeysAction;
import liquibase.action.core.AlterTableAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.exception.ValidationErrors;
import liquibase.snapshot.SnapshotFactory;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddPrimaryKeysLogic extends AbstractActionLogic<AddPrimaryKeysAction> {

    public static enum Clauses {
        constraintName, columnNames, tablespace,
    }

    @Override
    protected Class<AddPrimaryKeysAction> getSupportedAction() {
        return AddPrimaryKeysAction.class;
    }

    @Override
    public ValidationErrors validate(AddPrimaryKeysAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope)
                .checkRequiredFields(action, "primaryKeys", "primaryKeys.columns", "primaryKeys.columns.name", "primaryKeys.table");
        if (!scope.getDatabase().supportsTablespaces()) {
            errors.checkUnsupportedFields(action, "primaryKeys.tablespace");
        }

        for (PrimaryKey primaryKey : action.primaryKeys) {
            if (ObjectUtil.defaultIfEmpty(primaryKey.clustered, false)) {
                errors.addUnsupportedError("adding a clustered primary key", action);
            }
        }

        return errors;
    }

    @Override
    public ActionStatus checkStatus(AddPrimaryKeysAction action, Scope scope) {
        ActionStatus result = new ActionStatus();
        try {
            for (PrimaryKey actionPK : action.primaryKeys) {
                PrimaryKey snapshotPK = scope.getSingleton(SnapshotFactory.class).snapshot(PrimaryKey.class, actionPK.toReference(), scope);
                if (snapshotPK == null) {
                    result.assertApplied(false, "Primary Key '" + actionPK.toReference()+ "' not found");
                } else {
                    List<String> ignoreList = new ArrayList<>();
                    if (actionPK.clustered == null || !actionPK.clustered) {
                        ignoreList.add("clustered");
                    }


                    result.assertCorrect(actionPK, snapshotPK, ignoreList);

                    if (actionPK.columns.size() == snapshotPK.columns.size()) {
                        for (int i=0; i <  actionPK.columns.size(); i++) {
                            result.assertCorrect(actionPK.columns.get(i), snapshotPK.columns.get(i), Arrays.asList("position", "descending"));
                        }
                    } else {
                        result.assertCorrect(false, "columnCheck sizes are different");
                    }
                }
                
                if (result.isApplied()) {
                    boolean checkIndex = false;
                    for (PrimaryKey.PrimaryKeyColumn column : actionPK.columns) {
                        if (column.descending != null) { //need to check the corresponding index
                            checkIndex = true;
                        }
                    }

                    if (checkIndex) {
                        Index index = scope.getSingleton(SnapshotFactory.class).snapshot(Index.class, snapshotPK.toReference(), scope);
                        for (int i=0; i<actionPK.columns.size(); i++) {
                            if (actionPK.columns.get(i).descending != null) {
                                result.assertCorrect(actionPK.columns.get(i).descending.equals(index.columns.get(i).descending), "column "+actionPK.columns.get(i).name+" direction is incorrect. Expected "+actionPK.columns.get(i).descending+", got "+index.columns.get(i).descending);
                            }
                        }
                    }
                }
            }
            return result;
        } catch (Throwable e) {
            return result.unknown(e);
        }
    }

    @Override
    public ActionResult execute(AddPrimaryKeysAction action, Scope scope) throws ActionPerformException {

        List<Action> actions = new ArrayList<>();

        for (PrimaryKey pk : action.primaryKeys) {
            actions.addAll(Arrays.asList(execute(pk, action, scope)));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected Action execute(PrimaryKey pk, AddPrimaryKeysAction action, Scope scope) {
        return new AlterTableAction(
                pk.table,
                generateSql(pk, action, scope)
        );
    }


    protected StringClauses generateSql(PrimaryKey pk, AddPrimaryKeysAction action, Scope scope) {
        Database database = scope.getDatabase();
        StringClauses clauses = new StringClauses(" ");

        clauses.append("ADD");
        clauses.append("CONSTRAINT");
        clauses.append(Clauses.constraintName, database.escapeObjectName(pk.getName(), PrimaryKey.class));
        clauses.append("PRIMARY KEY");

        StringClauses columns = new StringClauses("(", ", ", ")");
        for (PrimaryKey.PrimaryKeyColumn col : pk.columns) {
            String colDef = scope.getDatabase().escapeObjectName(col.name, Column.class);
            if (col.descending != null) {
                if (col.descending) {
                    colDef += " DESC";
                } else {
                    colDef += " ASC";
                }
            }
            columns.append(colDef);
        }

        clauses.append(Clauses.columnNames, columns);

        String tablespace = pk.tablespace;
        if (tablespace != null && database.supportsTablespaces()) {
            clauses.append(Clauses.tablespace, "USING INDEX TABLESPACE " + tablespace);
        }

        return clauses;
    }
}
