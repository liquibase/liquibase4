package liquibase.item.core;

import liquibase.item.AbstractCatalogBasedObject;

public class SchemaReference extends AbstractCatalogBasedObject.CatalogBasedObjectReference {

    public SchemaReference() {
    }

    public SchemaReference(String name, CatalogReference catalog) {
        super(Schema.class, name, catalog);
    }

    public SchemaReference(String... names) {
        super(Schema.class, names);
    }
}
