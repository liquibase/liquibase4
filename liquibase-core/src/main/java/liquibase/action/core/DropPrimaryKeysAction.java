package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.PrimaryKeyReference;

import java.util.ArrayList;
import java.util.List;

public class DropPrimaryKeysAction extends AbstractAction {

    public List<PrimaryKeyReference> primaryKeys = new ArrayList<>();
}
