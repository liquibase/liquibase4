package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.core.ForeignKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Add foreign keys to a database. {@link liquibase.actionlogic.ActionLogic} implementations that handle this should handle the case when there are keys added to multiple tables.
 */
public class AddForeignKeysAction extends AbstractAction {

    public List<ForeignKey> foreignKeys = new ArrayList<>();

    public AddForeignKeysAction() {
    }

    public AddForeignKeysAction(ForeignKey... foreignKeys) {
        if (foreignKeys != null) {
            this.foreignKeys.addAll(Arrays.asList(foreignKeys));
        }
    }
}