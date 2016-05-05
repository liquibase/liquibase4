package liquibase.item.datatype.core

import liquibase.JUnitScope
import liquibase.database.core.MockDatabase
import liquibase.item.datatype.DataType
import spock.lang.Specification
import spock.lang.Unroll

class NumberDataTypeLogicTest extends Specification {

    @Unroll
    def "toSql for values"() {
        expect:
        new NumberDataTypeLogic().toSql(object, dataType, JUnitScope.getInstance(new MockDatabase())) == expected

        where:
        object        | dataType                      | expected
        null   | new DataType(DataType.StandardType.INTEGER) | "NULL"
        "3"    | new DataType(DataType.StandardType.INTEGER) | "3"
        "3.5"  | new DataType(DataType.StandardType.FLOAT)   | "3.5"
        "34"   | new DataType(DataType.StandardType.BIGINT)  | "34"
    }
}
