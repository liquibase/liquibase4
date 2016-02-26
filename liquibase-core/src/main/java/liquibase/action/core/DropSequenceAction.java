package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.SequenceReference;

/**
 * Action to drop an existing sequence.
 */
public class DropSequenceAction extends AbstractAction {

    public SequenceReference sequence;

    public DropSequenceAction(SequenceReference sequence) {
        this.sequence = sequence;
    }

    public DropSequenceAction() {


    }
}
