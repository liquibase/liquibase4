package liquibase.item.core;

import liquibase.item.DatabaseObjectReference;

public class CatalogReference extends DatabaseObjectReference<DatabaseObjectReference> {

    public CatalogReference() {
    }

    public CatalogReference(String name) {
        super(Catalog.class, name);
    }
}
