package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ObjectBasedQueryResult
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.snapshot.Snapshot
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.ObjectReference
import liquibase.structure.core.Column
import liquibase.structure.core.Schema
import liquibase.structure.core.Table
import liquibase.structure.core.UniqueConstraint
import liquibase.util.CollectionUtil
import spock.lang.Unroll

class SnapshotObjectsActionUniqueConstraintsTest extends AbstractActionTest {

    @Unroll("#featureName: #uqRef on #conn")
    def "can find complex uq names without a table"() {
        expect:
        def action = new SnapshotObjectsAction(uqRef)

        testAction([
                uqName_asTable: uqRef
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundUq = results.asObject(UniqueConstraint)

            def foundUqRef = foundUq.toReference()
            assert foundUqRef.equals(uqRef, true)
            assert foundUq.columns.size() > 0
        })

        where:
        [conn, scope, uqRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(UniqueConstraint, ObjectNameStrategy.COMPLEX_NAMES, scope).unique().collect({ it.container.name = null; return it }),
            ])
        }
    }

    @Unroll("#featureName: #uqRef on #conn")
    def "can find complex uq names with a table"() {
        expect:
        def action = new SnapshotObjectsAction(uqRef)

        testAction([
                uqName_asTable: uqRef
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundUq = results.asObject(UniqueConstraint)

            def foundUqRef = foundUq.toReference()
            assert foundUqRef.equals(uqRef, true)
            assert foundUq.columns.size() > 0
        })

        where:
        [conn, scope, uqRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(UniqueConstraint, ObjectNameStrategy.COMPLEX_NAMES, scope).unique().collect({ it.container.name = standardCaseObjectName("known_table", Table, scope.getDatabase()); return it }),
            ])
        }
    }

    @Unroll("#featureName: #uqRef on #conn")
    def "can find by UniqueConstraintReference with a table name but null constraint name"() {
        expect:
        def action = new SnapshotObjectsAction(uqRef)

        testAction([
                uqName_asTable: uqRef
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundUq = results.asObject(UniqueConstraint)

            def foundUqRef = foundUq.toReference()
            assert foundUqRef.container.equals(uqRef.container, true)
            assert foundUq.columns.size() > 0
        })

        where:
        [conn, scope, uqRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope).collect({ new UniqueConstraint.UniqueConstraintReference(it, null) }),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all uniqueConstraints in a fully qualified complex table name"() {
        expect:
        def action = new SnapshotObjectsAction(UniqueConstraint, tableName)

        testAction([
                tableName_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;


            def foundUq = results.asObject(UniqueConstraint)
            assert foundUq.table.equals(tableName, true)
            assert foundUq.columns.size() > 0
        })


        where:
        [conn, scope, tableName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all uniqueConstraints related to a table with a null name"() {
        expect:
        def action = new SnapshotObjectsAction(UniqueConstraint, tableName)

        testAction([
                tableName_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0;


            for (def foundUq : results.asList(UniqueConstraint)) {
                assert foundUq.table.equals(tableName, true)
                assert foundUq.columns.size() > 0
            }
        })


        where:
        [conn, scope, tableName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Schema, ObjectNameStrategy.COMPLEX_NAMES, scope).collect({ return new ObjectReference(Table, it, null) }),
            ])
        }
    }

    @Unroll("#featureName: #schemaName on #conn")
    def "can find all uniqueConstraints related to a schema"() {
        expect:
        def action = new SnapshotObjectsAction(UniqueConstraint, schemaName)

        testAction([
                schemaName_asTable: schemaName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0;


            for (def foundUq : results.asList(UniqueConstraint)) {
                assert foundUq.table.container.equals(schemaName, true)
                assert foundUq.columns.size() > 0
            }
        })


        where:
        [conn, scope, schemaName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Schema, scope),
            ])
        }
    }

    @Unroll("#featureName: #schemaName on #conn")
    def "Finds multi-column UQs correctly"() {
        expect:

        def table = new Table(schemaName, standardCaseObjectName("table_name", Table, scope.database))
        def column1 = new Column(table.toReference(), standardCaseObjectName("col1", Column, scope.database), "int")
        def column2 = new Column(table.toReference(), standardCaseObjectName("col2", Column, scope.database), "int")
        def column3 = new Column(table.toReference(), standardCaseObjectName("col3", Column, scope.database), "int")

        def uq = new UniqueConstraint(null, table.toReference(), column1.name, column3.name)
        def snapshot = new Snapshot(scope)
        snapshot.addAll([table, column1, column2, column3, uq])

        def action = new SnapshotObjectsAction(uq.toReference())

        testAction([
                schemaName_asTable: schemaName
        ], snapshot, action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1; //found more than one object

            UniqueConstraint foundUq = results.asObject(UniqueConstraint)
            assert foundUq.table.equals(table.toReference(), true)
            assert foundUq.columns.size() == 2

            assert foundUq.columns[0] == column1.name
            assert foundUq.columns[1] == column3.name

        })


        where:
        [conn, scope, schemaName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.allSchemas
            ])
        }
    }


    @Override
    def createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return null;
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)

        def columnName = standardCaseObjectName("id", Column, scope.database)
        //Crate the expected UQ/table combo
        for (ObjectReference relatedTo : ((SnapshotObjectsAction) action).relatedTo) {
            if (relatedTo.instanceOf(UniqueConstraint)) {

                def tableName = relatedTo.container
                if (tableName == null || tableName.name == null) {
                    tableName = new ObjectReference(Table, relatedTo.container.container, standardCaseObjectName("test_table", Table, scope.database))
                }

                snapshot.add(new Table(tableName))
                snapshot.add(new Column(tableName, columnName, "int"))
                snapshot.add(new UniqueConstraint(relatedTo.name, tableName, columnName))
            } else if (relatedTo.instanceOf(Table)) {
                def table = new Table(relatedTo)
                if (table.name == null) {
                    table.name = standardCaseObjectName("test_table", Table, scope.database)
                }
                snapshot.add(table)
                snapshot.add(new Column(table.toReference(), columnName, "int"))
                snapshot.add(new UniqueConstraint(null, table.toReference(), columnName))
            }
        }

        //create some additional tables
        for (int i = 0; i < 5; i++) {
            i = i + 1;
            for (ObjectReference schema : getObjectNames(Schema, scope)) {
                def tableName = new ObjectReference(Table, schema, standardCaseObjectName("table_$i", Table, scope.database))
                snapshot.add(new Table(tableName))
                snapshot.add(new Column(tableName, columnName, "int"))
                snapshot.add(new UniqueConstraint(standardCaseObjectName("uq_test_" + i, UniqueConstraint, scope.database), tableName, columnName))
            }
        }

        return snapshot
    }
}
