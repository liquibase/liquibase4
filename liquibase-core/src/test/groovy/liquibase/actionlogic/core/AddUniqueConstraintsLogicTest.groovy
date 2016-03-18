package liquibase.actionlogic.core

import liquibase.actionlogic.AbstractActionLogicTest

class AddUniqueConstraintsLogicTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return """
AddUniqueConstraintsAction.uniqueConstraints is required
AddUniqueConstraintsAction.uniqueConstraints.backingIndex is not supported
AddUniqueConstraintsAction.uniqueConstraints.columns is required
AddUniqueConstraintsAction.uniqueConstraints.deferrable is not supported
AddUniqueConstraintsAction.uniqueConstraints.disabled is not supported
AddUniqueConstraintsAction.uniqueConstraints.initiallyDeferred is not supported
AddUniqueConstraintsAction.uniqueConstraints.relation is required
AddUniqueConstraintsAction.uniqueConstraints.tablespace is not supported
""".trim()
    }
}
