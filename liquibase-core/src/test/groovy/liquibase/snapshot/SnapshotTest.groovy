package liquibase.snapshot

import liquibase.JUnitScope
import liquibase.structure.ObjectReference
import liquibase.structure.core.Column
import liquibase.structure.core.Schema
import liquibase.structure.core.Table
import spock.lang.Specification
import spock.lang.Unroll

class SnapshotTest extends Specification {

      @Unroll
    def "getAll finds objects by full name correctly"() {
        when:
        def snapshot = new Snapshot(JUnitScope.instance).addAll([
                new Table(new ObjectReference(Schema, "cat1", "schema-a"), "table-a-1"),
                new Table(new ObjectReference(Schema, "cat1", "schema-a"), "table-a-2"),
                new Table(new ObjectReference(Schema, "cat1", "schema-b"), "table-b-1"),
                new Column(new ObjectReference(Schema, "cat1", "schema-a", "table-a-1"), "col-a-1-x"),
                new Column(new ObjectReference(Schema, "cat1", "schema-a", "table-a-1"), "col2"),
                new Column(new ObjectReference(Schema, "cat1", "schema-a", "table-a-2"), "col-a-2-x"),
                new Column(new ObjectReference(Schema, "cat1", "schema-a", "table-a-2"), "col2"),
                new Column(new ObjectReference(Schema, "cat1", "schema-b", "table-b-1"), "col-b-1-x"),
        ])

        then:
        snapshot.getAll(type, name)*.toReference()*.toString() == expected

        where:
        type   | name                                                         | expected
        Column | new Column.ColumnReference("cat1", "schema-a", "table-a-1", "col-a-1-x") | ["cat1.schema-a.table-a-1.col-a-1-x"]
        Column | new Column.ColumnReference("cat1", "schema-a", "table-a-2", "col-a-2-x") | ["cat1.schema-a.table-a-2.col-a-2-x"]
        Column | new Column.ColumnReference("cat1", "schema-a", "table-b-1", "col-a-1-x") | []
    }
}
