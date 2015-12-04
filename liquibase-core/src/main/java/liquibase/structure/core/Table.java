package liquibase.structure.core;

import liquibase.structure.ObjectReference;

public class Table extends Relation {

    public String tablespace;

    public Table() {
    }

    public Table(String name) {
        super(name);
    }

    public Table(ObjectReference reference) {
        super(reference);
    }

    public Table(ObjectReference schema, String name) {
        super(schema, name);
    }
}
