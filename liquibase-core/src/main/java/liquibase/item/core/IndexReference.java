package liquibase.item.core;

import liquibase.item.AbstractRelationBasedObject;
import liquibase.item.ItemReference;

/**
 * {@link #container} lists the table. {@link #indexContainer} can contain the index's qualifier
 */
public class IndexReference extends AbstractRelationBasedObject.RelationBasedObjectReference {

    public ItemReference indexContainer;

    public IndexReference() {
        super(Index.class);
    }

    public IndexReference(String... names) {
        super(Index.class, names);
    }

    public IndexReference(String indexName, RelationReference relation) {
        super(Index.class, indexName, relation);
    }

    public IndexReference(String indexName, RelationReference relation, ItemReference indexContainer) {
        this(indexName, relation);
        this.indexContainer = indexContainer;
    }
}
