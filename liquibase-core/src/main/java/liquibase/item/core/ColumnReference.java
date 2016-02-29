package liquibase.item.core;

import liquibase.item.AbstractRelationBasedObject;
import liquibase.util.ObjectUtil;

/**
 * A reference to a column. For a column reference, "container" is the table.
 */
public class ColumnReference extends AbstractRelationBasedObject.RelationBasedObjectReference {

    public ColumnReference() {
        super(Column.class);
    }

    public ColumnReference(String name, RelationReference relation) {
        super(Column.class, name, relation);
    }

    public ColumnReference(String... names) {
        super(Column.class, names);
    }

    @Override
    public String toString() {
        return ObjectUtil.defaultIfNull(container, "UNNAMED")+"."+ObjectUtil.defaultIfNull(name, "UNNAMED");
    }
}
