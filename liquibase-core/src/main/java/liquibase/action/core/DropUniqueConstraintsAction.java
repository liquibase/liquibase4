package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.PrimaryKeyReference;
import liquibase.item.core.UniqueConstraintReference;

import java.util.ArrayList;
import java.util.List;

public class DropUniqueConstraintsAction extends AbstractAction {

    public List<UniqueConstraintReference> constraints = new ArrayList<>();
}
