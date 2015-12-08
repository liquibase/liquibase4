package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.core.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
