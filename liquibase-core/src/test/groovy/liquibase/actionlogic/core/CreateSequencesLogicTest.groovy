package liquibase.actionlogic.core;

import liquibase.actionlogic.AbstractLogicTest;

public class CreateSequencesLogicTest extends AbstractLogicTest {
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
