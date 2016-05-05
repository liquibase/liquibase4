package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.action.ActionStatus;
import liquibase.action.core.AlterSequenceAction;
import liquibase.actionlogic.AbstractSqlBuilderLogic;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.item.core.Sequence;
import liquibase.snapshot.SnapshotFactory;
import liquibase.util.StringClauses;

import java.math.BigInteger;

public class AlterSequenceLogic extends AbstractSqlBuilderLogic<AlterSequenceAction> {

    public enum Clauses {
        maxValue, minValue, restartWith, cycle, cache, incrementBy
    }

    @Override
    protected Class<? extends AlterSequenceAction> getSupportedAction() {
        return AlterSequenceAction.class;
    }

    @Override
    protected boolean supportsScope(Scope scope) {
        return scope.getDatabase().supports(Sequence.class, scope);
    }

    @Override
    public ValidationErrors validate(final AlterSequenceAction action, final Scope scope) {
        ValidationErrors errors = super.validate(action, scope)
                .checkRequiredFields("sequence", "sequence.name");
        if (!scope.getDatabase().supports(Database.Feature.SEQUENCE_ORDERED, scope)) {
            errors.checkUnsupportedFields("ordered");
        }
        errors.check(new ValidationErrors.ErrorCheck() {
            @Override
            public String check() {
                if (hasSomethingToAlter(action, scope)) {
                    return null;
                } else {
                    return "no alterations to make";
                }
            }
        });

        return errors;
    }

    /**
     * Return true if there is something being altered by this sequence. By default it checks for any non-null attribute not named "sequence".
     * Used by {@link #validate(AlterSequenceAction, Scope)}
     */
    protected boolean hasSomethingToAlter(AlterSequenceAction action, Scope scope) {
        for (String attr : action.getAttributes()) {
            if (attr.equals("sequence")) {
                continue;
            }
            if (action.has(attr)) {
                return true;

            }
        }
        return false;
    }

    @Override
    public ActionStatus checkStatus(AlterSequenceAction action, Scope scope) {
        try {
            Sequence snapshotSequence = scope.getSingleton(SnapshotFactory.class).snapshot(action.sequence, scope);
            if (snapshotSequence == null) {
                return new ActionStatus().unknown("Cannot find sequence " + action.sequence);
            }
            ActionStatus status = new ActionStatus();

            if (action.incrementBy != null) {
                status.assertPropertyCorrect(action, snapshotSequence, "incrementBy");
            }
            if (action.cycle != null) {
                status.assertPropertyCorrect(action, snapshotSequence, "cycle");
            }
            if (action.minValue != null && action.minValue.compareTo(BigInteger.ZERO) > 0) {
                status.assertPropertyCorrect(action, snapshotSequence, "minValue");
            }
            if (action.maxValue != null && action.maxValue.compareTo(BigInteger.ZERO) > 0) {
                status.assertPropertyCorrect(action, snapshotSequence, "maxValue");
            }
            if (action.ordered != null) {
                status.assertPropertyCorrect(action, snapshotSequence, "ordered");
            }
            if (action.cacheSize != null && action.cacheSize.compareTo(BigInteger.ZERO) > 0) {
                status.assertPropertyCorrect(action, snapshotSequence, "cacheSize");
            }
            status.assertCorrect(true, "Sometimes nothing is checked");

            return status;
        } catch (ActionPerformException e) {
            return new ActionStatus().unknown(e);
        }
    }

    @Override
    protected StringClauses generateSql(AlterSequenceAction action, Scope scope) throws ActionPerformException {
        StringClauses sql = new StringClauses()
                .append("ALTER")
                .append("SEQUENCE")
                .append(scope.getDatabase().quoteObjectName(action.sequence, scope));

        if (action.restartWith != null) {
            sql.append(Clauses.restartWith, "RESTART WITH " + action.restartWith);
        }

        if (action.incrementBy != null) {
            sql.append(Clauses.incrementBy, "INCREMENT BY " + action.incrementBy);
        }

        if (action.maxValue != null) {
            if (action.maxValue.compareTo(BigInteger.ZERO) < 0) {
                sql.append(Clauses.maxValue, "NO MAXVALUE");
            } else {
                sql.append(Clauses.maxValue, "MAXVALUE " + action.maxValue);
            }
        }

        if (action.minValue != null) {
            if (action.minValue.compareTo(BigInteger.ZERO) < 0) {
                sql.append(Clauses.minValue, "NO MINVALUE");
            } else {
                sql.append(Clauses.minValue, "MINVALUE " + action.minValue);
            }
        }

        if (action.cycle != null) {
            if (action.cycle) {
                sql.append(Clauses.cycle, "CYCLE");
            } else {
                sql.append(Clauses.cycle, "NO CYCLE");
            }
        }

        if (action.cacheSize != null) {
            if (action.cacheSize.compareTo(BigInteger.ZERO) < 0) {
                sql.append(Clauses.cache, "NO CACHE");
            } else {
                sql.append(Clauses.cache, "CACHE " + action.cacheSize);
            }
        }
        return sql;
    }
}
