package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.RelationReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Drop existing table(s)
 */
public class DropTablesAction extends AbstractAction {
    public List<RelationReference> tables = new ArrayList<>();
    public Boolean cascadeConstraints;

    public DropTablesAction() {
    }

    public DropTablesAction(RelationReference... tables) {
        if (tables != null) {
            this.tables.addAll(Arrays.asList(tables));
        }
    }
}