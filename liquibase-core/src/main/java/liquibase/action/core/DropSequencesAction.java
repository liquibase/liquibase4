package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.SequenceReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Action to drop existing sequence(s).
 */
public class DropSequencesAction extends AbstractAction {

    public List<SequenceReference> sequences = new ArrayList<>();

    public DropSequencesAction() {

    }

    public DropSequencesAction(SequenceReference... sequences) {
        if (sequences != null) {
            this.sequences.addAll(Arrays.asList(sequences));
        }
    }

}
