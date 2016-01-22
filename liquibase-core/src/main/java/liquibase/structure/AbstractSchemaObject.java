package liquibase.structure;

import liquibase.structure.core.Schema;

public abstract class AbstractSchemaObject extends AbstractObject {

    public ObjectReference schema;

    public AbstractSchemaObject() {
    }

    public AbstractSchemaObject(String name) {
        super(name);
    }

    public AbstractSchemaObject(ObjectReference reference) {
        super(reference.name);
        this.schema = reference.container;
        if (this.schema != null && (this.schema.type == null || this.schema.type.equals(LiquibaseObject.class))) {
            this.schema.type = Schema.class;
        }
    }

    public AbstractSchemaObject(ObjectReference schema, String name) {
        super(name);
        this.schema = schema;
    }

    @Override
    public ObjectReference toReference() {
        return new ObjectReference(getClass(), schema, name);
    }

}
