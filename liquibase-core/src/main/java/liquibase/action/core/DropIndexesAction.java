package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.IndexReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Drops existing indexes.
 */
public class DropIndexesAction extends AbstractAction {

    public List<IndexReference> indexes = new ArrayList<>();

    public DropIndexesAction() {
    }

    public DropIndexesAction(IndexReference... indexes) {
        if (indexes != null) {
            this.indexes.addAll(Arrays.asList(indexes));
        }
    }
}
