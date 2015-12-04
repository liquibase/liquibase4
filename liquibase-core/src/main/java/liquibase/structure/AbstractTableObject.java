package liquibase.structure;

public abstract class AbstractTableObject extends AbstractObject {

    public ObjectReference table;

    public AbstractTableObject() {
    }

    public AbstractTableObject(String name) {
        super(name);
    }

    public AbstractTableObject(ObjectReference reference) {
        super(reference.name);
        this.table = reference.container;
    }

    public AbstractTableObject(ObjectReference table, String name) {
        super(name);
        this.table = table;
    }

    @Override
    public ObjectReference toReference() {
        return new ObjectReference(getClass(), table, name);
    }

}
