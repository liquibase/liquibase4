package liquibase.snapshot

import liquibase.JUnitScope
import liquibase.item.core.Column
import liquibase.item.core.ColumnReference
import liquibase.item.core.RelationReference
import liquibase.item.core.SchemaReference
import liquibase.item.core.Table
import spock.lang.Specification
import spock.lang.Unroll

class SnapshotTest extends Specification {

    @Unroll
    def "getAll finds objects by full name correctly"() {
        when:
        def snapshot = new Snapshot(JUnitScope.instance).addAll([
                new Table("table-a-1", new SchemaReference("cat1", "schema-a")),
                new Table("table-a-2", new SchemaReference("cat1", "schema-a")),
                new Table("table-b-1", new SchemaReference("cat1", "schema-b")),
                new Column("col-a-1-x", new RelationReference(Table.class, "cat1", "schema-a", "table-a-1")),
                new Column("col2", new RelationReference(Table.class, "cat1", "schema-a", "table-a-1")),
                new Column("col-a-2-x", new RelationReference(Table.class, "cat1", "schema-a", "table-a-2")),
                new Column("col2", new RelationReference(Table.class, "cat1", "schema-a", "table-a-2")),
                new Column("col-b-1-x", new RelationReference(Table.class, "cat1", "schema-b", "table-b-1")),
        ])

        then:
        snapshot.getAll(type, name)*.toReference()*.toString() == expected

        where:
        type   | name                                                              | expected
        Column | new ColumnReference("cat1", "schema-a", "table-a-1", "col-a-1-x") | ["cat1.schema-a.table-a-1.col-a-1-x"]
        Column | new ColumnReference("cat1", "schema-a", "table-a-2", "col-a-2-x") | ["cat1.schema-a.table-a-2.col-a-2-x"]
        Column | new ColumnReference("cat1", "schema-a", "table-b-1", "col-a-1-x") | []
    }
}
