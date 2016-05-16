package liquibase;

import liquibase.database.Database;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class DatabaseExpression extends AbstractExpression {

    public DatabaseExpression() {
    }

    public DatabaseExpression(String expressions) {
        super(expressions);
    }

    public DatabaseExpression(Collection<String> expressions) {
        super(expressions);
    }

    public Set<String> getDatabases() {
        return Collections.unmodifiableSet(this.expressions);
    }

    /**
     * Returns true if the passed runtime contexts match this context expression
     */
    public boolean matches(Database runtimeDatabase) {
        if (runtimeDatabase == null) {
            return true;
        }
        if (this.expressions.size() == 0) {
            return true;
        }

        return matches(Collections.singleton(runtimeDatabase.getShortName()));
    }
}
