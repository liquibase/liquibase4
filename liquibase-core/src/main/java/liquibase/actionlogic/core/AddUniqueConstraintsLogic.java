package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.core.AddUniqueConstraintsAction;
import liquibase.action.core.AlterTableAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.exception.ValidationErrors;
import liquibase.snapshot.SnapshotFactory;
import liquibase.structure.core.Column;
import liquibase.structure.core.UniqueConstraint;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;
import liquibase.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddUniqueConstraintsLogic extends AbstractActionLogic<AddUniqueConstraintsAction> {

    public enum Clauses {
        constraintName,
        columnNames,
    }

    @Override
    protected Class<AddUniqueConstraintsAction> getSupportedAction() {
        return AddUniqueConstraintsAction.class;
    }

    @Override
    public ValidationErrors validate(AddUniqueConstraintsAction action, Scope scope) {
        ValidationErrors validationErrors = super.validate(action, scope)
                .checkRequiredFields("uniqueConstraints", "uniqueConstraints.table", "uniqueConstraints.columns");

        //normally not supported so ignoring by default
        validationErrors.checkUnsupportedFields("uniqueConstraints.disabled", "uniqueConstraints.backingIndex");

        Database database = scope.getDatabase();
        if (!database.supports(Database.Feature.DEFERRABLE_CONSTRAINTS, scope)) {
            validationErrors.checkUnsupportedFields("uniqueConstraints.initiallyDeferred", "uniqueConstraints.deferrable");
        }
        if (!scope.getDatabase().supports(Database.Feature.TABLESPACES, scope)) {
            validationErrors.checkUnsupportedFields("uniqueConstraints.tablespace");
        }

        for (UniqueConstraint constraint : action.uniqueConstraints) {
            if (constraint == null) {
                continue;
            }

            if (constraint.name != null && constraint.table != null && !supportsSeparateConstraintSchema() && !constraint.table.equals(constraint.table, true)) {
                validationErrors.addUnsupportedError("specifying a different constraint schema");
            }
        }

        return validationErrors;
    }

    public boolean supportsSeparateConstraintSchema() {
        return false;
    }

    @Override
    public ActionStatus checkStatus(AddUniqueConstraintsAction action, Scope scope) {
        ActionStatus result = new ActionStatus();
        try {
            for (UniqueConstraint actionUq : action.uniqueConstraints) {
                List<UniqueConstraint> snapshotUniques = scope.getSingleton(SnapshotFactory.class).snapshotAll(UniqueConstraint.class, actionUq.toReference(), scope);
                UniqueConstraint snapshotUq = null;
                if (snapshotUniques != null) {
                    if (snapshotUniques.size() == 1) {
                        snapshotUq = snapshotUniques.get(0);
                    } else {
                        for (UniqueConstraint constraint : snapshotUniques) {
                            if (StringUtils.join(constraint.columns, ",").equals(StringUtils.join(actionUq.columns, ","))) {
                                snapshotUq = constraint;
                            }
                        }
                    }
                }

                if (snapshotUq == null) {
                    String desc = actionUq.name;
                    if (desc == null) {
                        desc = actionUq.table.toString()+" ("+StringUtils.join(actionUq.columns, ",") +")";
                    }
                    result.assertApplied(false, "Unique Constraint '" + desc + "' not found");
                } else {
                    List<String> ignore = new ArrayList<>();
                    if (actionUq.name == null) {
                        ignore.add("name");
                    }
                    result.assertCorrect(actionUq, snapshotUq, ignore);
                    if (actionUq.columns.size() == snapshotUq.columns.size()) {
                        for (int i=0; i <  actionUq.columns.size(); i++) {
                            result.assertCorrect(actionUq.columns.get(i), snapshotUq.columns.get(i), "Invalid column "+i+". Expected "+actionUq.columns.get(i)+" got "+ snapshotUq.columns.get(i));
                        }
                    } else {
                        result.assertCorrect(false, "columns sizes are different");
                    }
                }
            }
            return result;
        } catch (Throwable e) {
            return result.unknown(e);
        }
    }

    @Override
    public ActionResult execute(AddUniqueConstraintsAction action, Scope scope) throws ActionPerformException {

        List<Action> actions = new ArrayList<>();

        for (UniqueConstraint uq : action.uniqueConstraints) {
            if (uq == null) {
                continue;
            }

            actions.addAll(Arrays.asList(execute(uq, action, scope)));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected Action execute(UniqueConstraint uq, AddUniqueConstraintsAction action, Scope scope) {
        return new AlterTableAction(
                uq.table,
                generateConstraintClause(uq, action, scope)
        );
    }


    public StringClauses generateConstraintClause(UniqueConstraint uniqueConstraint, AddUniqueConstraintsAction action, final Scope scope) {
        final Database database = scope.getDatabase();

        String constrantName = supportsSeparateConstraintSchema() ? database.quoteObjectName(uniqueConstraint.name, UniqueConstraint.class, scope) : database.quoteObjectName(uniqueConstraint.getName(), UniqueConstraint.class, scope);

        StringClauses clauses = new StringClauses()
                .append("ADD")
                .append("CONSTRAINT")
                .append(AddUniqueConstraintsLogic.Clauses.constraintName, constrantName)
                .append("UNIQUE")
                .append(Clauses.columnNames, "(" + StringUtils.join(uniqueConstraint.columns, ", ", new StringUtils.StringUtilsFormatter<String>() {
                    @Override
                    public String toString(String obj) {
                        return database.quoteObjectName(obj, Column.class, scope);
                    }
                }) + ")");

        boolean deferrable = ObjectUtil.defaultIfEmpty(uniqueConstraint.deferrable, false);
        boolean initiallyDeferred = ObjectUtil.defaultIfEmpty(uniqueConstraint.initiallyDeferred, false);
        if (deferrable || initiallyDeferred) {
            if (deferrable) {
                clauses.append("DEFERRABLE");
            }

            if (initiallyDeferred) {
                clauses.append("INITIALLY DEFERRED");
            }
        }

        if (ObjectUtil.defaultIfEmpty(uniqueConstraint.disabled, false)) {
            clauses.append("DISABLED");
        }

        return clauses;
    }
}
