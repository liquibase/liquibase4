package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Index;
import liquibase.structure.core.Sequence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Add sequences to the database
 */
public class CreateSequencesAction extends AbstractAction {

    public List<Sequence> sequences = new ArrayList<>();

    public CreateSequencesAction() {
    }

    public CreateSequencesAction(Sequence... sequences) {
        if (sequences != null) {
            this.sequences.addAll(Arrays.asList(sequences));
        }
    }

}
