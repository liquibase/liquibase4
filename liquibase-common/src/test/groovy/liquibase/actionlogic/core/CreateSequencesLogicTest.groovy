package liquibase.actionlogic.core;

import liquibase.actionlogic.AbstractActionLogicTest;

public class CreateSequencesLogicTest extends AbstractActionLogicTest {
    @Override
    public String getExpectedValidationErrors() {
        return """
CreateSequencesAction.sequences is required
CreateSequencesAction.sequences.name is required
CreateSequencesAction.sequences.ordered is not supported
CreateSequencesAction.sequences: minValue must be less than maxValue
CreateSequencesAction.sequences: minValue must be less than startValue
""".trim();
    }
}
