package liquibase.item.core;

import liquibase.item.AbstractSchemaBasedObject;

/**
 * A container of columns. Usually a table or view.
 */
public abstract class Relation<ReferenceType extends RelationReference> extends AbstractSchemaBasedObject<RelationReference> {

    public Relation() {
    }

    public Relation(String... name) {
        super(name);
    }

    public Relation(String name, SchemaReference schema) {
        super(name, schema);
    }

    @Override
    public RelationReference toReference() {
        return new RelationReference(getClass(), name, schema);
    }

}
