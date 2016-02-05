package liquibase.actionlogic.core

import liquibase.action.core.AddUniqueConstraintsActionTest
import liquibase.actionlogic.AbstractLogicTest
import liquibase.database.core.UnsupportedDatabaseSupplier

class AddUniqueConstraintsLogicTest extends AbstractLogicTest {

    def "validation failures are as expected"() {
        expect:
        collectAllValidationErrors(new AddUniqueConstraintsActionTest(), new UnsupportedDatabaseSupplier()) == """
AddUniqueConstraintsAction.uniqueConstraints is required
AddUniqueConstraintsAction.uniqueConstraints.backingIndex is not supported
AddUniqueConstraintsAction.uniqueConstraints.columns is required
AddUniqueConstraintsAction.uniqueConstraints.deferrable is not supported
AddUniqueConstraintsAction.uniqueConstraints.disabled is not supported
AddUniqueConstraintsAction.uniqueConstraints.initiallyDeferred is not supported
AddUniqueConstraintsAction.uniqueConstraints.table is required
AddUniqueConstraintsAction.uniqueConstraints.tablespace is not supported
""".trim()
    }
}
