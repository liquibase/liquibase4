package liquibase.item.core;

import liquibase.item.AbstractRelationBasedObject;
import liquibase.item.ItemReference;
import liquibase.util.ObjectUtil;

/**
 * {@link #container} lists the table. {@link #indexSchema} can contain the index's qualifier
 */
public class IndexReference extends AbstractRelationBasedObject.RelationBasedObjectReference {

    public SchemaReference indexSchema;

    public IndexReference() {
        super(Index.class);
    }

    public IndexReference(String... names) {
        super(Index.class, names);
    }

    public IndexReference(String indexName, RelationReference relation) {
        super(Index.class, indexName, relation);
    }

    public IndexReference(String indexName, RelationReference relation, SchemaReference indexSchema) {
        this(indexName, relation);
        this.indexSchema = indexSchema;
    }

    @Override
    public String toString() {
        return (indexSchema == null ? "" : indexSchema + ".") + ObjectUtil.defaultIfNull(name, "UNNAMED") + " on " + ObjectUtil.defaultIfNull(container, "UNNAMED");
    }
}
