package liquibase.structure;

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
