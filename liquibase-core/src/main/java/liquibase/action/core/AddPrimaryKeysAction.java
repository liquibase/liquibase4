package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.core.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Adds primary keys to a database. {@link liquibase.actionlogic.ActionLogic} implementations that handle this should handle the case when there are keys added to multiple tables.
 */
public class AddPrimaryKeysAction extends AbstractAction {

    public List<PrimaryKey> primaryKeys = new ArrayList<>();

    public AddPrimaryKeysAction() {
    }

    public AddPrimaryKeysAction(PrimaryKey... primaryKeys) {
        if (primaryKeys != null) {
            this.primaryKeys.addAll(Arrays.asList(primaryKeys));
        }
    }

}
