package liquibase.item;

import liquibase.Scope;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.core.Catalog;

import java.util.List;

/**
 * Keeps catalogs from being used by {@link TestDatabaseObjectSupplier#getNames(Class, NameStrategy, Scope)}
 */
public class TestCatalogSupplier extends TestDatabaseObjectSupplier {

    @Override
    public int getPriority(Class type, Scope scope) {
        if (Catalog.class.isAssignableFrom(type)) {
            return PRIORITY_SPECIALIZED;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public List<String> getNames(Class type, NameStrategy nameStrategy, Scope scope) {
        throw new UnexpectedLiquibaseException("Look up catalogs with ConnectionSupplier.getAllCatalogs()");
    }
}
