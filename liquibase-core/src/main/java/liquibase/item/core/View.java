package liquibase.item.core;

public class View extends Relation {

    public Boolean containsFullDefinition;
    public String definition;

    public View() {
    }

    public View(String name) {
        super(name);
    }

    public View(String name, SchemaReference schema) {
        super(name, schema);
    }
}
