package liquibase.item.core;

import liquibase.util.StringClauses;

public class View extends Relation {

    public StringClauses definition;
    public Boolean completeDefinition;

    public View() {
    }

    public View(String name) {
        super(name);
    }

    public View(String name, SchemaReference schema) {
        this(name, schema, null);
    }

    public View(String name, SchemaReference schema, StringClauses definition) {
        super(name, schema);
        this.definition = definition;
    }
}
