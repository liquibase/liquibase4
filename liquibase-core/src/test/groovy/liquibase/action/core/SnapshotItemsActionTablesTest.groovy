package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.database.DatabaseConnection
import liquibase.item.TestItemSupplier
import liquibase.snapshot.Snapshot

import liquibase.item.ItemReference
import liquibase.item.core.Catalog
import liquibase.item.core.Column
import liquibase.item.core.RelationReference
import liquibase.item.core.SchemaReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.util.CollectionUtil
import org.junit.Assume
import spock.lang.Unroll

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.containsInAnyOrder

class SnapshotItemsActionTablesTest extends AbstractActionTest {

    @Unroll("#featureName: #tableRef on #conn")
    def "can snapshot fully qualified table"() {
        expect:
        def action = new SnapshotItemsAction(Table, tableRef)

        testAction([table_asTable: tableRef], action, conn, scope, { plan, result ->
            assert result.asList(Table).size() == 1
            assert result.asObject(Object) instanceof Table
            assert result.asObject(Table).getName() == tableRef.name
        })

        where:
        [scope, conn, tableRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [scope],
                    [it],
                    getItemReferences(Table, it.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope)
            ])
        }
    }

    @Unroll("#featureName: #schemaRef on #conn")
    def "can snapshot all tables in schema"() {
        expect:
        def action = new SnapshotItemsAction(Table, schemaRef)

        testAction([schema_asTable: schemaRef], action, conn, scope, { plan, result ->
            def expected = result.asList(Table).grep({
                it.schema == schemaRef
            })
            assertThat result.asList(Table), containsInAnyOrder(expected.toArray())
        })

        where:
        [scope, conn, schemaRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [scope],
                    [it],
                    it.allSchemas
            ])
        }
    }

    @Unroll("#featureName: #schemaRef on #conn")
    def "can snapshot all tables in schema using a null table name reference"() {
        expect:
        def action = new SnapshotItemsAction(Table, new RelationReference(Table, null, schemaRef))

        testAction([schema_asTable: schemaRef], action, conn, scope, { plan, result ->
            def expected = result.asList(Table).grep({
                it.schema == schemaRef
            })
            assertThat result.asList(Table), containsInAnyOrder(expected.toArray())
        })

        where:
        [scope, conn, schemaRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [scope],
                    [it],
                    it.allSchemas
            ])
        }
    }

    @Unroll("#featureName: #catalogRef on #conn")
    def "can snapshot all tables in catalog"() {
        expect:
        def action = new SnapshotItemsAction(Table, new RelationReference(Table, null, new SchemaReference(null, catalogRef)))

        testAction([catalog_asTable: catalogRef], action, conn, scope, {
            plan, result ->
                def expected = result.asList(Table).grep({
                    DatabaseConnection.getCatalog == catalogRef
                })

                assertThat result.asList(Table), containsInAnyOrder(expected.toArray())
        })

        where:
        [conn, scope, catalogRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            Assume.assumeTrue("Database does not support catalogs", it.database.supports(Catalog, scope))

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.allSchemas*.container.grep({ it != null }).unique()
            ])
        }
    }

    @Unroll
    def "describe"() {
        expect:
        action.describe() == expected

        where:
        action                                                                               | expected
        new SnapshotItemsAction(new RelationReference(Table, "schema_name", "table_name")) | "snapshotItems(relatedTo=[schema_name.table_name], typeToSnapshot=liquibase.item.core.Table)"
    }


    @Override
    def createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return null;
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        for (ItemReference table : getItemReferences(Table, connectionSupplier.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope)) {
            snapshot.add(new Table(table.name, table.container))
            snapshot.add(new Column(standardCaseItemName("id", Column, scope), table, DataType.parse("int"), true))
        }
        return snapshot
    }
}
