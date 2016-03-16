package liquibase.item.core;

import liquibase.item.AbstractSchemaBasedObject;

/**
 * A reference to a table or a view.
 */
public class RelationReference<ThisType extends Relation> extends AbstractSchemaBasedObject.SchemaBasedObjectReference {

    public RelationReference() {
        super(Relation.class);
    }

    public RelationReference(Class<ThisType> type, String name, SchemaReference schema) {
        super(type, name, schema);
    }

    public RelationReference(Class<ThisType> type, String... names) {
        super(type, names);
    }
}
