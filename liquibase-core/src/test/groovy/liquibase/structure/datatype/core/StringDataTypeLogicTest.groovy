package liquibase.structure.datatype.core

import liquibase.JUnitScope
import liquibase.database.core.MockDatabase
import liquibase.structure.datatype.DataType
import spock.lang.Specification
import spock.lang.Unroll

class StringDataTypeLogicTest extends Specification {

    @Unroll
    def "toSql for values"() {
        expect:
        new StringDataTypeLogic().toSql(object, dataType, JUnitScope.getInstance(new MockDatabase())) == expected

        where:
        object        | dataType                      | expected
        null          | DataType.parse("varchar(10)") | "NULL"
        "abc"         | DataType.parse("varchar(10)") | "'abc'"
        "3"           | DataType.parse("varchar(10)") | "'3'"
        "test 'this'" | DataType.parse("varchar(10)") | "'test ''this'''"
    }
}
