package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.ForeignKeyReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Drops an existing foreign key.
 */
public class DropForeignKeysAction extends AbstractAction {

    public List<ForeignKeyReference> foreignKeys = new ArrayList<>();

    public DropForeignKeysAction() {
    }

    public DropForeignKeysAction(ForeignKeyReference... foreignKeys) {
        if (foreignKeys != null) {
            this.foreignKeys.addAll(Arrays.asList(foreignKeys));
        }
    }
}
