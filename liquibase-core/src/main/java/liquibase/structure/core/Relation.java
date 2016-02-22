package liquibase.structure.core;

import liquibase.structure.AbstractSchemaObject;
import liquibase.structure.ObjectReference;

/**
 * A container of columns. Usually a table or view.
 */
public abstract class Relation extends AbstractSchemaObject {

    public String remarks;

    public Relation() {
    }

    public Relation(String name) {
        super(name);
    }

    public Relation(ObjectReference reference) {
        super(reference);
    }

    public Relation(ObjectReference container, String name) {
        super(container, name);
    }
}
