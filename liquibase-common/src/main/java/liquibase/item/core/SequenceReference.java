package liquibase.item.core;

import liquibase.item.AbstractSchemaBasedObject;

public class SequenceReference extends AbstractSchemaBasedObject.SchemaBasedObjectReference {

    public SequenceReference() {
        super(Sequence.class);
    }

    public SequenceReference(String name, SchemaReference schema) {
        super(Sequence.class, name, schema);
    }

    public SequenceReference(String... names) {
        super(Sequence.class, names);
    }

}
