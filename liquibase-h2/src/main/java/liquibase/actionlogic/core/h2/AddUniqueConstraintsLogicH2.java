package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.core.AddUniqueConstraintsAction;
import liquibase.actionlogic.core.AddUniqueConstraintsLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.structure.core.UniqueConstraint;
import liquibase.util.StringClauses;

public class AddUniqueConstraintsLogicH2 extends AddUniqueConstraintsLogic {
    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    public StringClauses generateConstraintClause(UniqueConstraint uniqueConstraint, AddUniqueConstraintsAction action, Scope scope) {
        StringClauses clauses = super.generateConstraintClause(uniqueConstraint, action, scope);
        if (uniqueConstraint.name == null) {
            clauses.remove("CONSTRAINT");
        }
        return clauses;
    }
}
