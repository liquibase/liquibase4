package liquibase.item.core;

import liquibase.item.AbstractDatabaseObject;

/**
 * A database catalog.
 * Catalogs are the container for {@link Schema}s, and are not supported by all databases. Because they hold Schemas, if a database does not support schemas, it does not support Catalogs.
 * For database that do support them, they can be called "catalog" or "databases" or "instances". Whatever holds schemas in a database are called "Catalogs" within Liquibase.
 */
public class Catalog extends AbstractDatabaseObject<CatalogReference> {

    /**
     * True if this is the default catalog in the database.
     */
    public Boolean isDefault;

    public Catalog() {
    }

    public Catalog(String name) {
        super(name);
    }

    @Override
    public CatalogReference toReference() {
        return new CatalogReference(name);
    }

}
