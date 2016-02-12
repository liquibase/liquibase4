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
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Table;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddPrimaryKeysLogic extends AbstractActionLogic<AddPrimaryKeysAction> {

    public enum Clauses {
        constraintName, columnNames, tablespace,
    }

    @Override
    protected Class<AddPrimaryKeysAction> getSupportedAction() {
        return AddPrimaryKeysAction.class;
    }

    @Override
    public ValidationErrors validate(AddPrimaryKeysAction action, final Scope scope) {
        final ValidationErrors errors = super.validate(action, scope)
                .checkRequiredFields("primaryKeys", "primaryKeys.columns", "primaryKeys.columns.name", "primaryKeys.table");

        final Database database = scope.getDatabase();

        if (!database.supportsTablespaces()) {
            errors.checkUnsupportedFields("primaryKeys.tablespace");
        }

        errors.checkField("primaryKeys", new ValidationErrors.FieldCheck<PrimaryKey>() {
            @Override
            public String check(PrimaryKey pk) {
                if (pk.clustered != null && pk.clustered) {
                    return "adding a clustered primary key is not supported";
                }
                return null;
            }
        });


        errors.checkField("primaryKeys.columns", new ValidationErrors.FieldCheck<PrimaryKey.PrimaryKeyColumn>() {
            @Override
            public String check(PrimaryKey.PrimaryKeyColumn col) {
                if (col.direction != null && !database.supportsIndexDirection(col.direction)) {
                    return "'" + col.direction + "'";
                }
                return null;
            }
        });

        return errors;
    }

    @Override
    public ActionStatus checkStatus(AddPrimaryKeysAction action, Scope scope) {
        ActionStatus result = new ActionStatus();
        try {
            for (PrimaryKey actionPK : action.primaryKeys) {
                ObjectReference table = actionPK.table;
                if (table.type == null || table.type.equals(LiquibaseObject.class)) {
                    table.type = Table.class;
                }
                PrimaryKey snapshotPK = scope.getSingleton(SnapshotFactory.class).snapshot(PrimaryKey.class, table, scope);
                if (snapshotPK == null) {
                    result.assertApplied(false, "Primary Key '" + actionPK.toReference()+ "' not found");
                } else {
                    List<String> ignoreList = new ArrayList<>();
                    if (actionPK.clustered == null || !actionPK.clustered) {
                        ignoreList.add("clustered");
                    }
                    if (!scope.getDatabase().supportsNamed(PrimaryKey.class)) {
                        ignoreList.add("name");
                    }


                    result.assertCorrect(actionPK, snapshotPK, ignoreList);

                    if (actionPK.columns.size() == snapshotPK.columns.size()) {
                        for (int i=0; i <  actionPK.columns.size(); i++) {
                            result.assertCorrect(actionPK.columns.get(i), snapshotPK.columns.get(i), Arrays.asList("direction"));
                        }
                    } else {
                        result.assertCorrect(false, "columnCheck sizes are different");
                    }
                }
                
                if (result.isApplied()) {
                    boolean checkIndex = false;
                    for (PrimaryKey.PrimaryKeyColumn column : actionPK.columns) {
                        if (column.direction != null) { //need to check the corresponding index
                            checkIndex = true;
                        }
                    }

                    if (checkIndex) {
                        Index index = scope.getSingleton(SnapshotFactory.class).snapshot(Index.class, snapshotPK.toReference(), scope);
                        for (int i=0; i<actionPK.columns.size(); i++) {
                            if (actionPK.columns.get(i).direction != null) {
                                result.assertCorrect(actionPK.columns.get(i).direction.equals(index.columns.get(i).direction), "column "+actionPK.columns.get(i).name+" direction is incorrect. Expected "+actionPK.columns.get(i).direction+", got "+index.columns.get(i).direction);
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
            if (col.direction != null) {
                colDef += " "+col.direction;
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
