package liquibase.item.datatype.core

import liquibase.JUnitScope
import liquibase.database.core.MockDatabase
import liquibase.item.datatype.DataType
import liquibase.item.datatype.DataTypeLogic
import spock.lang.Specification
import spock.lang.Unroll

class DefaultDataTypeLogicTest extends Specification {

    @Unroll
    def "toSql for values"() {
        expect:
        new DefaultDataTypeLogic().toSql(object, dataType, JUnitScope.getInstance(new MockDatabase())) == expected

        where:
        object        | dataType                      | expected
        null          | DataType.parse("varchar(10)") | "NULL"
        "abc"         | DataType.parse("varchar(10)") | "'abc'"
        "3"           | DataType.parse("varchar(10)") | "'3'"
        "test 'this'" | DataType.parse("varchar(10)") | "'test ''this'''"
    }
}
