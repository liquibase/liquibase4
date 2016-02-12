package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.core.UniqueConstraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Adds unique constraints to a database.
 * {@link liquibase.actionlogic.ActionLogic} implementations that handle this should handle the case when there are constraints added to multiple tables.
 */
public class AddUniqueConstraintsAction extends AbstractAction {

    public List<UniqueConstraint> uniqueConstraints = new ArrayList<>();

    public AddUniqueConstraintsAction() {

    }

    public AddUniqueConstraintsAction(UniqueConstraint... uniqueConstraints) {
        if (uniqueConstraints != null) {
            this.uniqueConstraints.addAll(Arrays.asList(uniqueConstraints));
        }
    }
}
