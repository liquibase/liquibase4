package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.*
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import spock.lang.Unroll

class SnapshotItemsActionStoredProceduresTest extends AbstractActionTest {

    @Unroll("#featureName: #procedure on #conn")
    def "can find fully qualified complex procedure names"() {
        expect:
        def action = new SnapshotItemsAction(StoredProcedure, procedure)

        testAction([procedure_asTable: procedure], action, conn, scope, {
            plan, result ->
                assert result.asList(StoredProcedure).size() == 1
                assert result.asObject(Object) instanceof StoredProcedure
                assert result.asObject(StoredProcedure).toReference() == procedure
        })

        where:
        [conn, scope, procedure] << ignoreIfEmpty("database may not support sequences", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            if (!it.database.supports(StoredProcedure, scope)) {
                return []
            }

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemReferences(StoredProcedure, it.allSchemas, scope)
            ])
        })
    }

    @Unroll("#featureName: #schemaRef on #conn")
    def "can find all procedures in a schema"() {
        expect:
        def action = new SnapshotItemsAction(StoredProcedure, schemaRef)

        testAction([schema_asTable: schemaRef], action, conn, scope, {
            plan, result ->
                assert result.asList(Column).size() > 0
                result.asList(Object).each {
                    assert it instanceof StoredProcedure;
                    assert it.container.equals(schemaRef, true)
                }
        })

        where:
        [conn, scope, schemaRef] << ignoreIfEmpty("database may not support sequences", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            if (!it.database.supports(StoredProcedure, scope)) {
                return []
            }

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.allSchemas
            ])
        })
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return null;
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)

        CreateStoredProceduresActionTest.TestDetails testDetails = new CreateStoredProceduresActionTest().getTestDetails(scope)

        snapshot.addAll(testDetails.getObjectsForSnapshot(scope))

        for (StoredDatabaseLogicReference procRef : getItemReferences(StoredProcedure, connectionSupplier.getAllSchemas(), scope)) {
            def procedure = new StoredProcedure(procRef.name, procRef.container)
            procedure.body = testDetails.createExampleProcedureBody(procRef, scope)

            snapshot.add(procedure)
        }

        return snapshot
    }

    List<ColumnReference> getColumnNamesWithTables(ConnectionSupplier connectionSupplier, Scope scope) {
        getItemNames(Column, scope).collectMany {
            def colName = it
            return getItemReferences(Table, connectionSupplier.getAllSchemas(), scope).collect {
                return new ColumnReference(colName, it)
            }
        }
    }
}
