package liquibase.item;

import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.core.Schema;

import java.util.List;

public class SchemaTestItemReferenceSupplier extends TestItemReferenceSupplier {

    @Override
    public int getPriority(Class type, Scope scope) {
        if (Schema.class.isAssignableFrom(type)) {
            return PRIORITY_SPECIALIZED;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public List<String> getItemNames(Class type, ItemNameStrategy nameStrategy, Scope scope) {
        throw new UnexpectedLiquibaseException("Look up schemas with conn.getAllSchemas()");
    }
}
