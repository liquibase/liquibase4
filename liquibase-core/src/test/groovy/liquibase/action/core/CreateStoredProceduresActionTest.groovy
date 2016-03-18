package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.DatabaseObject
import liquibase.item.core.Column
import liquibase.item.core.RelationReference
import liquibase.item.core.StoredDatabaseLogicReference
import liquibase.item.core.StoredProcedure
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class CreateStoredProceduresActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can create from createAllActionPermutations"() {
        expect:
        testAction([
                name_asTable     : action.procedures*.name,
                container_asTable: action.procedures*.container,
                body_asTable     : action.procedures*.body,
        ], action, conn, scope)

        where:
        [conn, scope, action] << ignoreIfEmpty("database does not support procedures", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            if (!it.database.supports(StoredProcedure, scope)) {
                return []
            }

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new AbstractActionTest.ValidActionFilter(scope))
        })
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        def details = (TestDetails) getTestDetails(scope)
        return TestUtil.createAllPermutations(CreateStoredProceduresAction, [
                procedures: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(StoredProcedure, [
                        name     : [details.getExampleProcedureName(scope)],
                        container: connectionSupplier.allSchemas,
                        body     : ["BODY"],
                ]).each {
                    if (it.name == null) {
                        it.body = null
                    }
                    if (it.body != null) {
                        it.body = details.createExampleProcedureBody(it.toReference(), scope)
                    }
                })
        ])

    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)
        snapshot.addAll(((TestDetails) getTestDetails(scope)).getObjectsForSnapshot(scope))
        return snapshot;
    }

    public static class TestDetails extends AbstractActionTest.TestDetails {

        private CreateStoredProceduresActionTest testObj = new CreateStoredProceduresActionTest();

        protected String getExampleProcedureName(Scope scope) {
            return testObj.standardCaseItemName("test_proc", StoredProcedure, scope)
        }


        public String createExampleProcedureBody(StoredDatabaseLogicReference procedure, Scope scope) {
            def procName = scope.getDatabase().quoteObjectName(procedure, scope)
            def tableName = testObj.standardCaseItemName("test_table", Table, scope)

            return """
CREATE PROCEDURE $procName (OUT param1 int)
BEGIN
    SELECT COUNT(*) INTO param1 FROM $tableName;
END
"""
        }

        public List<DatabaseObject> getObjectsForSnapshot(Scope scope) {
            def tableName = testObj.standardCaseItemName("test_table", Table, scope)

            return [new Table(tableName),
                    new Column(testObj.standardCaseItemName("col1", Column, scope), new RelationReference(Table, tableName), new DataType(DataType.StandardType.INTEGER), true)
            ]

        }
    }
}
