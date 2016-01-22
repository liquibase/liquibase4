package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.core.AddForeignKeysAction;
import liquibase.actionlogic.core.AddForeignKeysLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.exception.ValidationErrors;
import liquibase.structure.core.ForeignKey;
import liquibase.util.StringClauses;

public class AddForeignKeysLogicH2 extends AddForeignKeysLogic {
    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    public ValidationErrors validate(AddForeignKeysAction action, Scope scope) {
        ValidationErrors errors = super.validate(action, scope);

        for (ForeignKey fk : action.foreignKeys) {
            if (fk == null) {
                continue;
            }
            errors.addError(fk.updateRule != null && fk.updateRule == ForeignKey.ConstraintType.importedKeyNoAction, "H2 does not support ON UPDATE NO ACTION");
            errors.addError(fk.deleteRule != null && fk.deleteRule== ForeignKey.ConstraintType.importedKeyNoAction, "H2 does not support ON DELETE NO ACTION");
        }

            return errors;
    }

    @Override
    public StringClauses generateConstraintClause(ForeignKey fk, AddForeignKeysAction action, Scope scope) {
        StringClauses clauses = super.generateConstraintClause(fk, action, scope);
        if (fk.name == null) {
            clauses.remove("CONSTRAINT");
        }
        return clauses;
    }
}
