package liquibase.item.core;

public class Table extends Relation {

    public String tablespace;

    public Table() {
    }

    public Table(String... name) {
        super(name);
    }

    public Table(String name, SchemaReference schema) {
        super(name, schema);
    }
}
