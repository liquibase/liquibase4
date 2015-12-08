package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
