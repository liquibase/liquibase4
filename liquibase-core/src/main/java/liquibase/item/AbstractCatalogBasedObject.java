package liquibase.item;

import liquibase.item.core.Catalog;
import liquibase.item.core.CatalogReference;

/**
 * Base class for database objects that belong to a {@link liquibase.item.core.Catalog}.
 * Objects that can be in schemas or catalogs or relations depending on their configuration such as a trigger should extend {@link AbstractDatabaseObject} instead of this class.
 *
 * @see AbstractSchemaBasedObject for objects that belong to a schema.
 * @see AbstractRelationBasedObject for objects that belong to a table.
 */
public abstract class AbstractCatalogBasedObject<ReferenceType extends DatabaseObjectReference> extends AbstractDatabaseObject<ReferenceType> {

    public CatalogReference catalog;

    public AbstractCatalogBasedObject() {
    }

    public AbstractCatalogBasedObject(String name, CatalogReference catalog) {
        super(name);
        this.catalog = catalog;
    }

    /**
     * Base class for {@link ItemReference} implementations for objects which are contained in a {@link liquibase.item.core.Catalog}.
     */
    public abstract static class CatalogBasedObjectReference extends DatabaseObjectReference<CatalogReference> {

        public CatalogBasedObjectReference() {
        }

        public CatalogBasedObjectReference(Class<? extends AbstractCatalogBasedObject> type, String name, CatalogReference catalog) {
            super(type, name, catalog);
        }

        public CatalogBasedObjectReference(Class<? extends AbstractCatalogBasedObject> type, String... names) {
            super(type, names);
        }

        /**
         * Convenience method that calls {@link #container} "catalog" since.
         */
        public final CatalogReference getCatalog() {
            return container;
        }

        @Override
        protected CatalogReference createContainer(String name) {
            return new CatalogReference(name);
        }
    }


}
