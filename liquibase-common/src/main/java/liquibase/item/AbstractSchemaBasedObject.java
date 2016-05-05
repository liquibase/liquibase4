package liquibase.item;

import liquibase.item.core.CatalogReference;
import liquibase.item.core.SchemaReference;

import java.util.Arrays;

/**
 * Base class for database objects that belong to a {@link liquibase.item.core.Schema}.
 * Does not extend from {@link AbstractCatalogBasedObject} even though they can still be in a catalog, because we don't want both a "schema" and a "catalog" field which may differ.
 * Objects that can be in schemas or catalogs or relations depending on their configuration such as a trigger should extend {@link AbstractDatabaseObject} instead of this class.
 *
 * @see AbstractCatalogBasedObject for objects that belong to a catalog.
 * @see AbstractRelationBasedObject for objects that belong to a table.
 */
public abstract class AbstractSchemaBasedObject<ReferenceType extends AbstractSchemaBasedObject.SchemaBasedObjectReference> extends AbstractDatabaseObject<ReferenceType> {

    public SchemaReference schema;

    public AbstractSchemaBasedObject() {
    }

    public AbstractSchemaBasedObject(String... name) {
        if (name != null && name.length > 0) {
            if (name.length == 1) {
                this.name = name[0];
            } else {
                this.name = name[name.length - 1];
                this.schema = new SchemaReference(Arrays.copyOfRange(name, 0, name.length - 1));
            }
        }
    }

    public AbstractSchemaBasedObject(String name, SchemaReference schema) {
        super(name);
        this.schema = schema;
    }

    /**
     * Returns the {@link #schema}'s catalog, or null if schema is null.
     */
    public final CatalogReference getCatalog() {
        if (schema == null) {
            return null;
        }
        return schema.getCatalog();
    }

    /**
     * Base class for {@link ItemReference} implementations for objects which are part of a {@link liquibase.item.core.Schema}.
     */
    public abstract static class SchemaBasedObjectReference extends DatabaseObjectReference<SchemaReference> {

        public SchemaBasedObjectReference() {
        }

        public SchemaBasedObjectReference(Class<? extends AbstractSchemaBasedObject> type, String name, SchemaReference schema) {
            super(type, name, schema);
        }

        public SchemaBasedObjectReference(Class<? extends AbstractSchemaBasedObject> type, String... names) {
            super(type, names);
        }

        /**
         * Convenience method that calls the {@link #container} "schema"
         */
        public final SchemaReference getSchema() {
            return container;
        }

        @Override
        protected SchemaReference createContainer(String name) {
            return new SchemaReference(name);
        }
    }

}
