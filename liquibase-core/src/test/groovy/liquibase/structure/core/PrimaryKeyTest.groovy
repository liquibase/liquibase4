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
        pk                                                                                                 | expected
        new PrimaryKey()                                                                                   | "PrimaryKey{}"
        new PrimaryKey("pk_name")                                                                          | "PrimaryKey{name=pk_name}"
        new PrimaryKey(new ObjectReference(Table, "table_name"), null)                                     | "PrimaryKey{table=table_name}"
        new PrimaryKey(new ObjectReference(Table, "schema_name", "table_name"), null)                      | "PrimaryKey{table=schema_name.table_name}"
        new PrimaryKey(new ObjectReference(Table, "cat_name", "schema_name", "table_name"), null)          | "PrimaryKey{table=cat_name.schema_name.table_name}"
        new PrimaryKey("pk_name", new ObjectReference(Table, "schema_name", "table_name"))                 | "PrimaryKey{name=pk_name, table=schema_name.table_name}"
        new PrimaryKey("pk_name", new ObjectReference(Table, "schema_name", "table_name"), "col1")         | "PrimaryKey{columns=[PrimaryKeyColumn{name=col1}], name=pk_name, table=schema_name.table_name}"
        new PrimaryKey("pk_name", new ObjectReference(Table, "schema_name", "table_name"), "col1", "col2") | "PrimaryKey{columns=[PrimaryKeyColumn{name=col1}, PrimaryKeyColumn{name=col2}], name=pk_name, table=schema_name.table_name}"
    }
}
