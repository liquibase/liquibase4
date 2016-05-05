package liquibase.item.core;

import liquibase.item.AbstractCatalogBasedObject;

/**
 * A database schema.
 * Schemas are the primary container for objects in a database. Not all database support schemas, but if there are containers for tables etc. they are called "schemas" in Liquibase, regardless of what the database calls them.
 */
public class Schema extends AbstractCatalogBasedObject<SchemaReference> {

    public Boolean isDefault;

    public Schema() {
    }

    public Schema(String name) {
        super(name, null);
    }

    public Schema(String schemaName, CatalogReference catalog) {
        super(schemaName, catalog);
    }

    @Override
    public SchemaReference toReference() {
        return new SchemaReference(name, catalog);
    }

}
