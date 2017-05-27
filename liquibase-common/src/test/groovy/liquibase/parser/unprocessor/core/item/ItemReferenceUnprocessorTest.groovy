package liquibase.parser.unprocessor.core.item

import liquibase.item.core.Schema
import liquibase.item.core.Table
import liquibase.item.core.View
import spock.lang.Specification
import spock.lang.Unroll

class ItemReferenceUnprocessorTest extends Specification {

    @Unroll
    def "toNodeName"() {
        expect:
        new ItemReferenceUnprocessor().toNodeName(nodeName, type == null ? null : type.name, baseReferenceName) == expected

        where:
        nodeName       | type   | baseReferenceName | expected
        "anythingHere" | null   | null              | "anythingHere"
        "relation"     | Table  | null              | "tableName"
        "container"    | Table  | null              | "tableName"
        "table"        | Table  | null              | "tableName"
        "baseTable"    | Table  | null              | "baseTableName"
        "baseTable"    | View   | null              | "baseTableName"
        "schema"       | Schema | null              | "schemaName"
        "schema"       | Schema | "tableName"       | "schemaName"
        "schema"       | Schema | "baseTableName"   | "baseTableSchemaName"
    }
}
