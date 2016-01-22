package liquibase.actionlogic.core

import liquibase.action.core.AddUniqueConstraintsActionTest
import liquibase.actionlogic.AbstractLogicTest
import liquibase.database.core.UnsupportedDatabaseSupplier

class AddUniqueConstraintsLogicTest extends AbstractLogicTest {

    def "validation failures are as expected"() {
        expect:
        collectAllValidationErrors(new AddUniqueConstraintsActionTest(), new UnsupportedDatabaseSupplier()) == """
UniqueConstraint.backingIndex is not supported
UniqueConstraint.columns is required
UniqueConstraint.deferrable is not supported
UniqueConstraint.disabled is not supported
UniqueConstraint.initiallyDeferred is not supported
UniqueConstraint.table is required
UniqueConstraint.tablespace is not supported
""".trim()
    }
}
