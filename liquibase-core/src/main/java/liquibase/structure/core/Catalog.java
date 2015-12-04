package liquibase.structure.core;

import liquibase.structure.AbstractObject;
import liquibase.structure.ObjectReference;

public class Catalog extends AbstractObject {

    public Boolean isDefault;

    public Catalog() {
    }

    public Catalog(ObjectReference reference) {
        this.name = reference.name;
    }

    public Catalog(String name) {
        super(name);
    }

}
