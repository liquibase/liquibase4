package liquibase.database.core.h2

import liquibase.JUnitScope
import liquibase.structure.ObjectReference
import liquibase.structure.core.Table
import spock.lang.Specification
import spock.lang.Unroll

class H2DatabaseTest extends Specification {

    @Unroll
    def "escapeObjectName"() {
        expect:
        new H2Database().quoteObjectName(ref, JUnitScope.instance) == expected

        where:
        ref                                      | expected
        null                                     | null
        new ObjectReference(Table, "TEST_TABLE") | "\"TEST_TABLE\""
        new ObjectReference(Table, "test_table") | "\"test_table\""
    }
}
