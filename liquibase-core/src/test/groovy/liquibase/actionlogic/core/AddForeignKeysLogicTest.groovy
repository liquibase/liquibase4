package liquibase.actionlogic.core

import liquibase.action.core.AddForeignKeysActionTest
import liquibase.actionlogic.AbstractLogicTest
import liquibase.database.core.UnsupportedDatabaseSupplier

class AddForeignKeysLogicTest extends AbstractLogicTest {

    def "validation failures are as expected"() {
        expect:
        collectAllValidationErrors(new AddForeignKeysActionTest(), new UnsupportedDatabaseSupplier()) == """
ForeignKey.columnChecks is required
ForeignKey.deferrable is not supported
ForeignKey.initiallyDeferred is not supported
ForeignKey.table is required
""".trim()
    }
}
