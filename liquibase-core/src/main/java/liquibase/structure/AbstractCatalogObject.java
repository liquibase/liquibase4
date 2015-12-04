package liquibase.structure;

public abstract class AbstractCatalogObject extends AbstractObject {

    public ObjectReference catalog;

    public AbstractCatalogObject() {
    }

    public AbstractCatalogObject(String name) {
        super(name);
    }

    public AbstractCatalogObject(ObjectReference reference) {
        super(reference.name);
        this.catalog = reference.container;
    }

    public AbstractCatalogObject(ObjectReference catalog, String name) {
        super(name);
        this.catalog = catalog;
    }

    @Override
    public ObjectReference toReference() {
        return new ObjectReference(getClass(), catalog, name);
    }


}
