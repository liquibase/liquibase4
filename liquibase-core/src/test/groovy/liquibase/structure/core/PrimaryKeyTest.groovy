package liquibase.structure.core

import liquibase.structure.ObjectReference
import spock.lang.Specification
import spock.lang.Unroll

class PrimaryKeyTest extends Specification {

    @Unroll
    def "toString test"() {
        expect:
        pk.toString() == expected

        where:
        pk | expected
        new PrimaryKey() | "PrimaryKey"
        new PrimaryKey("pk_name") | "PrimaryKey pk_name"
        new PrimaryKey(new ObjectReference(Table, "table_name"),  null) | "null on table_name()"
        new PrimaryKey(new ObjectReference(Table, "schema_name", "table_name"),  null) | "null on schema_name.table_name()"
        new PrimaryKey(new ObjectReference(Table, "cat_name", "schema_name", "table_name"),  null) | "null on cat_name.schema_name.table_name()"
        new PrimaryKey("pk_name", new ObjectReference(Table, "schema_name", "table_name")) | "pk_name on schema_name.table_name()"
        new PrimaryKey("pk_name", new ObjectReference(Table, "schema_name", "table_name"), "col1") | "pk_name on schema_name.table_name(col1)"
        new PrimaryKey("pk_name", new ObjectReference(Table, "schema_name", "table_name"), "col1", "col2") | "pk_name on schema_name.table_name(col1,col2)"
    }
}
