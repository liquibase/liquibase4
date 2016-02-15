package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.snapshot.Snapshot
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.ObjectReference
import liquibase.structure.core.Catalog
import liquibase.structure.core.Column
import liquibase.structure.core.Table
import liquibase.util.CollectionUtil
import org.junit.Assume
import spock.lang.Unroll

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.containsInAnyOrder

class SnapshotObjectsActionTablesTest extends AbstractActionTest {

    @Unroll("#featureName: #tableRef on #conn")
    def "can snapshot fully qualified table"() {
        expect:
        def action = new SnapshotObjectsAction(Table, tableRef)

        testAction([tableName_asTable: tableRef], action, conn, scope, { plan, result ->
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
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope)
            ])
        }
    }

    @Unroll("#featureName: #schemaRef on #conn")
    def "can snapshot all tables in schema"() {
        expect:
        def action = new SnapshotObjectsAction(Table, schemaRef)

        testAction([schemaName_asTable: schemaRef], action, conn, scope, { plan, result ->
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
        def action = new SnapshotObjectsAction(Table, new ObjectReference(Table, schemaRef, null))

        testAction([schemaName_asTable: schemaRef], action, conn, scope, { plan, result ->
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
        def action = new SnapshotObjectsAction(Table, new ObjectReference(Table, new ObjectReference(new ObjectReference(catalogRef, null), null)))

        testAction([catalogName_asTable: catalogRef], action, conn, scope, {
            plan, result ->
                def expected = result.asList(Table).grep({
                    it.schema.catalog == catalogRef
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
        action | expected
        new SnapshotObjectsAction(new ObjectReference(Table, "schema_name", "table_name")) | "snapshotObjects(relatedTo=[schema_name.table_name], typeToSnapshot=liquibase.structure.core.Table)"
    }


    @Override
    def createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return null;
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        for (ObjectReference tableName : getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope)) {
            snapshot.add(new Table(tableName))
            snapshot.add(new Column(tableName, standardCaseObjectName("id", Column, scope.getDatabase()), "int"))
        }
        return snapshot
    }
}
