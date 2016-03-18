package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.StoredDatabaseLogicReference
import liquibase.item.core.StoredProcedure
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class DropStoredProceduresActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can drop from createAllActionPermutations"() {
        expect:
        testAction([
                procedureName_asTable: action.procedures,
        ], action, conn, scope)

        where:
        [conn, scope, action] << ignoreIfEmpty("may not support stored procedures", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            if (!it.database.supports(StoredProcedure, scope)) {
                return []
            }
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new ValidActionFilter(scope))
        })
    }


    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return TestUtil.createAllPermutations(DropStoredProceduresAction, [
                procedures: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(StoredDatabaseLogicReference, [
                        name     : getItemNames(StoredProcedure, scope),
                        container: connectionSupplier.getAllSchemas(),
                ]).each {
                    it.type = StoredProcedure
                })
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        DropStoredProceduresAction dropAction = (DropStoredProceduresAction) action

        CreateStoredProceduresActionTest.TestDetails testDetails = new CreateStoredProceduresActionTest().getTestDetails(scope)

        def snapshot = new Snapshot(scope)

        for (def procReference : dropAction.procedures) {
            snapshot.addAll(testDetails.getObjectsForSnapshot(scope));

            def procedure = new StoredProcedure(procReference.name, procReference.container)
            procedure.body = testDetails.createExampleProcedureBody(procReference, scope)
            snapshot.add(procedure)
        }

        return snapshot
    }

}
