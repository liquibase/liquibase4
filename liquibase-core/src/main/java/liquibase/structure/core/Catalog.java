package liquibase.structure.core;

import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.ObjectReference;

public class Catalog extends AbstractDatabaseObject {

    public Boolean isDefault;

    public Catalog() {
    }

    public Catalog(String name) {
        super(name);
    }

    public Catalog(ObjectReference nameAndContainer) {
        super(nameAndContainer);
    }

    public Catalog(ObjectReference container, String name) {
        super(container, name);
    }
}
