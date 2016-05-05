package liquibase.item;

import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.core.Schema;

import java.util.List;

/**
 * Keeps schemas from being used by {@link TestItemSupplier#getNames(Class, Scope)}
 */
public class TestSchemaSupplier extends TestDatabaseObjectSupplier {

    @Override
    public int getPriority(Class type, Scope scope) {
        if (Schema.class.isAssignableFrom(type)) {
            return PRIORITY_SPECIALIZED;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public List<String> getNames(Class type, Scope scope) {
        throw new UnexpectedLiquibaseException("Look up schemas with ConnectionSupplier.getAllSchemas()");
    }
}
