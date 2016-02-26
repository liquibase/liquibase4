package liquibase.item.core

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
        new PrimaryKey(null, new RelationReference(Table, "table_name"))                                     | "PrimaryKey{relation=table_name}"
        new PrimaryKey(null, new RelationReference(Table, "schema_name", "table_name"))                      | "PrimaryKey{relation=schema_name.table_name}"
        new PrimaryKey(null, new RelationReference(Table, "cat_name", "schema_name", "table_name"))          | "PrimaryKey{relation=cat_name.schema_name.table_name}"
        new PrimaryKey("pk_name", new RelationReference(Table, "schema_name", "table_name"))                 | "PrimaryKey{name=pk_name, relation=schema_name.table_name}"
        new PrimaryKey("pk_name", new RelationReference(Table, "schema_name", "table_name"), "col1")         | "PrimaryKey{columns=[PrimaryKeyColumn{name=col1}], name=pk_name, relation=schema_name.table_name}"
        new PrimaryKey("pk_name", new RelationReference(Table, "schema_name", "table_name"), "col1", "col2") | "PrimaryKey{columns=[PrimaryKeyColumn{name=col1}, PrimaryKeyColumn{name=col2}], name=pk_name, relation=schema_name.table_name}"
    }
}
