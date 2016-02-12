package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Add indexes to a database. {@link liquibase.actionlogic.ActionLogic} implementations that handle this should handle the case when there are indexes added to multiple tables.
 */
public class CreateIndexesAction extends AbstractAction {

    public List<Index> indexes = new ArrayList<>();

    public CreateIndexesAction() {
    }

    public CreateIndexesAction(Index... indexes) {
        if (indexes != null) {
            this.indexes.addAll(Arrays.asList(indexes));
        }
    }

}
