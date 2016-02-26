package liquibase.item;

import liquibase.item.core.CatalogReference;
import liquibase.item.core.Relation;
import liquibase.item.core.RelationReference;
import liquibase.item.core.SchemaReference;
import liquibase.util.ObjectUtil;

import java.util.Arrays;

/**
 * Base class for database objects that belong to a {@link liquibase.item.core.Table} or a {@link liquibase.item.core.View}.
 * Does not extend from {@link AbstractSchemaBasedObject} even though they are still in a schema, because we don't want both a "relation" and a "schema" field which may differ.
 * Classes that extend from this should be objects whose identity is tied to a table and if the table doesn't exist the object cannot exist.
 * They can physically live separately, like an {@link liquibase.item.core.Index} but they are tied to a single table.
 * Objects that can be in schemas or catalogs or relations depending on their configuration such as a trigger should extend {@link AbstractDatabaseObject} instead of this class.
 * Also, objects that can be tied equally to multiple tables should extend {@link AbstractDatabaseObject}.
 *
 * @see AbstractCatalogBasedObject for objects that belong to a catalog.
 * @see AbstractSchemaBasedObject for objects that belong to a schema.
 */
public abstract class AbstractRelationBasedObject<ReferenceType extends AbstractRelationBasedObject.RelationBasedObjectReference> extends AbstractDatabaseObject<ReferenceType> {

    public RelationReference relation;

    public AbstractRelationBasedObject() {
    }

    public AbstractRelationBasedObject(Class<? extends Relation> relationType, String... name) {
        if (name != null && name.length > 0) {
            if (name.length == 1) {
                this.name = name[0];
            } else {
                this.name = name[name.length];
                this.relation = new RelationReference(relationType, Arrays.copyOfRange(name, 0, name.length - 1));
            }
        }
    }

    public AbstractRelationBasedObject(String name, RelationReference relation) {
        super(name);
        this.relation = relation;
    }

    /**
     * Returns the {@link #relation}'s schema, or null if relation is null.
     */
    public final SchemaReference getSchema() {
        if (relation == null) {
            return null;
        }
        return relation.getSchema();
    }

    /**
     * Returns the {@link #relation}'s schema's catalog, or null if relation or schema is null.
     */
    public final CatalogReference getCatalog() {
        SchemaReference schema = getSchema();
        if (schema == null) {
            return null;
        }
        return schema.getCatalog();
    }

    /**
     * Base class for {@link ItemReference} implementations for objects which are part of a {@link liquibase.item.core.Relation}.
     */
    public abstract static class RelationBasedObjectReference extends DatabaseObjectReference<RelationReference> {

        public RelationBasedObjectReference() {
        }

        public RelationBasedObjectReference(Class<? extends AbstractRelationBasedObject> type, String name, RelationReference relation) {
            super(type, name, relation);
        }

        public RelationBasedObjectReference(Class<? extends AbstractRelationBasedObject> type, String... names) {
            super(type, names);
        }

        /**
         * Convenience method that calls {@link #container} "relation". Final so that this works the same as get("container")
         */
        public final RelationReference getRelation() {
            return container;
        }

        /**
         * Returns the {@link #container}'s schema, or null if the container is null.
         */
        public final SchemaReference getSchema() {
            if (container == null) {
                return null;
            }
            return container.getSchema();
        }

        /**
         * Returns the {@link #container}'s schema's catalog, or null if either is null.
         */
        public final CatalogReference getCatalog() {
            SchemaReference schema = getSchema();
            if (schema == null) {
                return null;
            }
            return schema.getCatalog();
        }


        @Override
        public String toString() {
            return ObjectUtil.defaultIfEmpty(name, "UNNAMED") + " on " + ObjectUtil.defaultIfEmpty(container, "UNNAMED");
        }

        @Override
        protected RelationReference createContainer(String name) {
            return new RelationReference(Relation.class, name);
        }
    }
}
