package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ActionExecutor
import liquibase.database.ConnectionSupplier
import liquibase.database.DatabaseConnection
import liquibase.database.DatabaseConnectionFactory
import liquibase.database.OfflineConnection
import liquibase.database.core.GenericDatabase
import liquibase.resource.JUnitResourceAccessor
import liquibase.snapshot.Snapshot
import liquibase.item.core.Column
import liquibase.item.core.ColumnReference
import liquibase.item.core.PrimaryKey
import liquibase.item.core.PrimaryKeyReference
import liquibase.item.core.RelationReference
import liquibase.item.core.Schema
import liquibase.item.core.SchemaReference
import liquibase.item.core.Table
import liquibase.item.core.UniqueConstraint
import liquibase.item.core.UniqueConstraintReference
import liquibase.item.datatype.DataType
import spock.lang.Unroll

public class SnapshotItemsActionOfflineTest extends AbstractActionTest {

    @Unroll("#featureName: #type related to #relatedTo")
    def "can snapshot objects from a snapshot"() {
        when:
        def action = new SnapshotItemsAction(type, relatedTo)

        def scope = JUnitScope.instance

        def connectionParameters = new DatabaseConnection.ConnectionParameters();
        connectionParameters.url = "offline:generic";
        connectionParameters.set(OfflineConnection.OfflineConnectionParameters.snapshot.name(), createSnapshot(action, null, scope))
        def connection = scope.getSingleton(DatabaseConnectionFactory).connect(connectionParameters, scope)

        scope = scope.child(Scope.Attr.database.name(), new GenericDatabase(connection, scope));

        then:
        new TreeSet<>(scope.getSingleton(ActionExecutor).query(action, scope).asList(type)*.toReference()*.toString()).join(",") == expected

        where:
        type       | relatedTo                                                     | expected
        Table      | new SchemaReference("schema1")                                | "schema1.TABLE_UPPER,schema1.table1,schema1.table2"
        Table      | new SchemaReference(null)                                     | "schema1.TABLE_UPPER,schema1.table1,schema1.table2,schema2.table2" //all the schemas
        Table      | new RelationReference(Table, "schema1", "table1")             | "schema1.table1"
        Table      | new RelationReference(Table, "schema1", "TABLE_UPPER")        | "schema1.TABLE_UPPER"
        Table      | new RelationReference(Table, "schema1", "table_upper")        | "" //is case sensitive
        Table      | new RelationReference(Table, "schema1", (String) null)        | "schema1.TABLE_UPPER,schema1.table1,schema1.table2" //all the tables in schema1
        Table      | new RelationReference(Table, "table2")                        | "schema1.table2,schema2.table2" //all the table2's across schemas
        Table      | new ColumnReference("schema1", "table2", "col1")              | "schema1.table2" //the table for a column

        Column     | new RelationReference(Table, "schema1", "table1")             | "schema1.table1.col1,schema1.table1.col2"
        Column     | new RelationReference(Table, "schema1", (String) null)        | "schema1.TABLE_UPPER.COLUMN_UPPER,schema1.TABLE_UPPER.col1,schema1.TABLE_UPPER.col2,schema1.table1.col1,schema1.table1.col2,schema1.table2.col1,schema1.table2.col2" //null table name so all tables in schema1
        Column     | new RelationReference(Table, "fake", "fake")                  | ""
        Column     | new RelationReference(Table, "schema1", "fake")               | ""
        Column     | new SchemaReference("schema1")                                | "schema1.TABLE_UPPER.COLUMN_UPPER,schema1.TABLE_UPPER.col1,schema1.TABLE_UPPER.col2,schema1.table1.col1,schema1.table1.col2,schema1.table2.col1,schema1.table2.col2"
        Column     | new SchemaReference(null)                                     | "schema1.TABLE_UPPER.COLUMN_UPPER,schema1.TABLE_UPPER.col1,schema1.TABLE_UPPER.col2,schema1.table1.col1,schema1.table1.col2,schema1.table2.col1,schema1.table2.col2,schema2.table2.col1,schema2.table2.col2" //all schemas

        PrimaryKey | new RelationReference(Table, "schema1", "table2")             | "pk_table2 on schema1.table2"
        PrimaryKey | new RelationReference(Table, "schema1", "table1")             | "" //no pk
        PrimaryKey | new RelationReference(Table, "schema1", (String) null)        | "PK_UPPER on schema1.TABLE_UPPER,pk_table2 on schema1.table2"
        PrimaryKey | new PrimaryKeyReference("schema1", "table2", "pk_table2")     | "pk_table2 on schema1.table2"
        PrimaryKey | new PrimaryKeyReference("schema1", "TABLE_UPPER", "PK_UPPER") | "PK_UPPER on schema1.TABLE_UPPER"
        PrimaryKey | new PrimaryKeyReference("schema1", "table_upper", "PK_UPPER") | "" //case sensitivity check
        PrimaryKey | new PrimaryKeyReference("schema1", "TABLE_UPPER", "pk_upper") | "" //case sensitivity check
        PrimaryKey | new SchemaReference("schema1")                                | "PK_UPPER on schema1.TABLE_UPPER,pk_table2 on schema1.table2"
        PrimaryKey | new SchemaReference("schema2")                                | ""

        UniqueConstraint | new RelationReference(Table, "schema1", "table2")             | "uq_table2 on schema1.table2"
        UniqueConstraint | new RelationReference(Table, "schema1", "table1")             | "" //no uq
        UniqueConstraint | new RelationReference(Table, "schema1", (String) null)        | "UQ_UPPER on schema1.TABLE_UPPER,uq_table2 on schema1.table2"
        UniqueConstraint | new UniqueConstraintReference("schema1", "table2", "uq_table2")     | "uq_table2 on schema1.table2"
        UniqueConstraint | new UniqueConstraintReference("schema1", "TABLE_UPPER", "UQ_UPPER") | "UQ_UPPER on schema1.TABLE_UPPER"
        UniqueConstraint | new UniqueConstraintReference("schema1", "table_upper", "UQ_UPPER") | "" //case sensitivity check
        UniqueConstraint | new UniqueConstraintReference("schema1", "TABLE_UPPER", "uq_upper") | "" //case sensitivity check
        UniqueConstraint | new SchemaReference("schema1")                                | "UQ_UPPER on schema1.TABLE_UPPER,uq_table2 on schema1.table2"
        UniqueConstraint | new SchemaReference("schema2")                                | ""
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)

        def schema1 = new Schema("schema1")
        def schema2 = new Schema("schema2")

        def table1 = new Table("table1", schema1.toReference())
        def table2 = new Table("table2", schema1.toReference())
        def tableUpper = new Table("TABLE_UPPER", schema1.toReference())

        def table2Schema2 = new Table("table2", schema2.toReference())

        snapshot.add(schema1)
        snapshot.add(schema2)

        snapshot.add(table1)
        snapshot.add(table2)
        snapshot.add(tableUpper)
        snapshot.add(table2Schema2)

        snapshot.add(new Column("col1", table1.toReference(), DataType.parse("int"), true))
        snapshot.add(new Column("col2", table1.toReference(), DataType.parse("int"), true))
        snapshot.add(new Column("col1", table2.toReference(), DataType.parse("int"), true))
        snapshot.add(new Column("col2", table2.toReference(), DataType.parse("int"), true))
        snapshot.add(new Column("col1", tableUpper.toReference(), DataType.parse("int"), true))
        snapshot.add(new Column("col2", tableUpper.toReference(), DataType.parse("int"), true))
        snapshot.add(new Column("col1", table2Schema2.toReference(), DataType.parse("int"), true))
        snapshot.add(new Column("col2", table2Schema2.toReference(), DataType.parse("int"), true))

        snapshot.add(new Column("COLUMN_UPPER", tableUpper.toReference(), DataType.parse("int"), true));

        snapshot.add(new PrimaryKey("pk_table2", table2.toReference(), "col1"))
        snapshot.add(new PrimaryKey("PK_UPPER", tableUpper.toReference(), "col1"))

        snapshot.add(new UniqueConstraint("uq_table2", table2.toReference(), "col1"))
        snapshot.add(new UniqueConstraint("UQ_UPPER", tableUpper.toReference(), "col1"))

        return snapshot;
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return null;
    }
}
