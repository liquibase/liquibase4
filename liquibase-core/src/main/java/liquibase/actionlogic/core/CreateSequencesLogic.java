package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.Action;
import liquibase.action.ActionStatus;
import liquibase.action.ExecuteSqlAction;
import liquibase.action.core.CreateSequencesAction;
import liquibase.actionlogic.AbstractActionLogic;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.actionlogic.ActionResult;
import liquibase.actionlogic.DelegateResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.Item;
import liquibase.item.core.Index;
import liquibase.item.core.RelationReference;
import liquibase.item.core.Sequence;
import liquibase.item.core.Table;
import liquibase.item.datatype.DataTypeLogicFactory;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateSequencesLogic extends AbstractActionLogic<CreateSequencesAction> {

    public enum Clauses {
        dataType, startWith, incrementBy, minValue, maxValue, cacheSize, ordered, cycle,
    }

    @Override
    protected Class<? extends CreateSequencesAction> getSupportedAction() {
        return CreateSequencesAction.class;
    }

    @Override
    protected boolean supportsScope(Scope scope) {
        return super.supportsScope(scope) && scope.getDatabase().supports(Sequence.class, scope);
    }

    @Override
    public ValidationErrors validate(CreateSequencesAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope)
                .checkRequiredFields("sequences", "sequences.name");

        Database database = scope.getDatabase();
        if (!database.supports(Database.Feature.SEQUENCE_ORDERED, scope)) {
            errors.checkUnsupportedFields("sequences.ordered");
        }
        errors.checkField("sequences", new ValidationErrors.FieldCheck<Sequence>() {
            @Override
            public String check(Sequence obj) {
                if (obj.minValue != null && obj.maxValue != null) {
                    if (obj.minValue.compareTo(obj.maxValue) != -1) {
                        return "minValue must be less than maxValue";
                    }
                }
                if (obj.minValue != null && obj.startValue != null) {
                    if (obj.minValue.compareTo(obj.startValue) != -1) {
                        return "minValue must be less than startValue";
                    }
                }
                if (obj.startValue != null && obj.maxValue != null) {
                    if (obj.startValue.compareTo(obj.maxValue) != -1) {
                        return "minValue must be less than startValue";
                    }
                }
                return null;
            }
        });

        return errors;
    }

    @Override
    public ActionResult execute(CreateSequencesAction action, Scope scope) throws ActionPerformException {
        List<Action> actions = new ArrayList<>();

        for (Sequence sequence : action.sequences) {
            if (sequence == null) {
                continue;
            }

            actions.addAll(Collections.singletonList(execute(sequence, action, scope)));
        }

        return new DelegateResult(action, null, actions.toArray(new Action[actions.size()]));
    }

    protected Action execute(Sequence seq, CreateSequencesAction action, Scope scope) {
        StringClauses sql = new StringClauses()
                .append("CREATE SEQUENCE")
                .append(scope.getDatabase().quoteObjectName(seq.toReference(), scope));

        if (seq.dataType != null) {
            sql.append(Clauses.dataType, "AS " + scope.getSingleton(DataTypeLogicFactory.class).getDataTypeLogic(seq.dataType, scope).toSql(seq.dataType, scope));
        }

        if (seq.startValue != null) {
            sql.append(Clauses.startWith, "START WITH " + seq.startValue.toString());
        }
        if (seq.incrementBy != null) {
            sql.append(Clauses.incrementBy, "INCREMENT BY " + seq.incrementBy.toString());
        }
        if (seq.minValue != null) {
            sql.append(Clauses.minValue, "MINVALUE " + seq.minValue.toString());
        }
        if (seq.maxValue != null) {
            sql.append(Clauses.maxValue, "MAXVALUE " + seq.maxValue.toString());
        }

        if (seq.cacheSize != null) {
            if (seq.cacheSize.equals(BigInteger.ZERO)) {
                sql.append(Clauses.cacheSize, "NOCACHE");
            } else {
                sql.append(Clauses.cacheSize, "CACHE " + seq.cacheSize.toString());
            }
        }

        if (seq.ordered != null) {
            if (seq.ordered) {
                sql.append(Clauses.ordered, "ORDER");
            } else {
                sql.append(Clauses.ordered, "NOORDER");
            }
        }
        if (seq.cycle != null) {
            sql.append(Clauses.cycle, (seq.cycle ? "CYCLE" : ""));
        }

        return new ExecuteSqlAction(sql);
    }

    @Override
    public ActionStatus checkStatus(CreateSequencesAction action, Scope scope) {
        ActionStatus result = new ActionStatus();
        try {
            for (Sequence actionSequence : action.sequences) {
                Sequence snapshotSequence = scope.getSingleton(SnapshotFactory.class).snapshot(actionSequence.toReference(), scope);
                if (snapshotSequence == null) {
                    result.assertApplied(false, "Sequence '" + actionSequence.toReference() + "' not found");
                } else {
                    result.assertCorrect(actionSequence, snapshotSequence, Collections.singletonList("startValue"));
                }
            }
            return result;
        } catch (Throwable e) {
            return result.unknown(e);
        }
    }
}
