package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ObjectBasedQueryResult
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.snapshot.Snapshot
import liquibase.item.ItemNameStrategy
import liquibase.item.ItemReference
import liquibase.item.core.Column
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.core.UniqueConstraint
import liquibase.item.core.UniqueConstraintReference
import liquibase.item.datatype.DataType
import liquibase.util.CollectionUtil
import spock.lang.Unroll

class SnapshotItemsActionUniqueConstraintsTest extends AbstractActionTest {

    @Unroll("#featureName: #uqRef on #conn")
    def "can find complex uq names without a table"() {
        expect:
        def action = new SnapshotItemsAction(uqRef)

        testAction([
                uq_asTable: uqRef
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
                    getItemNames(UniqueConstraint, ItemNameStrategy.COMPLEX_NAMES, scope).collect({
                        return new UniqueConstraintReference(it)
                    }),
            ])
        }
    }

    @Unroll("#featureName: #uqRef on #conn")
    def "can find complex uq names with a table"() {
        expect:
        def action = new SnapshotItemsAction(uqRef)

        testAction([
                uq_asTable: uqRef
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
                    getItemNames(UniqueConstraint, ItemNameStrategy.COMPLEX_NAMES, scope).collect({
                        return new UniqueConstraintReference(it, new RelationReference(Table, standardCaseItemName("known_table", Table, scope.getDatabase())))
                    }),
            ])
        }
    }

    @Unroll("#featureName: #uqRef on #conn")
    def "can find by UniqueConstraintReference with a table name but null constraint name"() {
        expect:
        def action = new SnapshotItemsAction(uqRef)

        testAction([
                uq_asTable: uqRef
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
                    getItemReferences(Table, it.getAllSchemas(), ItemNameStrategy.COMPLEX_NAMES, scope).collect({
                        new UniqueConstraintReference(null, it)
                    }),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all uniqueConstraints in a fully qualified complex table name"() {
        expect:
        def action = new SnapshotItemsAction(UniqueConstraint, tableName)

        testAction([
                table_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;


            def foundUq = results.asObject(UniqueConstraint)
            assert foundUq.relation.equals(tableName, true)
            assert foundUq.columns.size() > 0
        })


        where:
        [conn, scope, tableName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemReferences(Table, it.getAllSchemas(), ItemNameStrategy.COMPLEX_NAMES, scope),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all uniqueConstraints related to a table with a null name"() {
        expect:
        def action = new SnapshotItemsAction(UniqueConstraint, tableName)

        testAction([
                table_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0;


            for (def foundUq : results.asList(UniqueConstraint)) {
                assert foundUq.relation.equals(tableName, true)
                assert foundUq.columns.size() > 0
            }
        })


        where:
        [conn, scope, tableName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.getAllSchemas().collect({ return new RelationReference(Table, null, it) }),
            ])
        }
    }

    @Unroll("#featureName: #schemaName on #conn")
    def "can find all uniqueConstraints related to a schema"() {
        expect:
        def action = new SnapshotItemsAction(UniqueConstraint, schemaName)

        testAction([
                schema_asTable: schemaName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0;


            for (def foundUq : results.asList(UniqueConstraint)) {
                assert foundUq.relation.container.equals(schemaName, true)
                assert foundUq.columns.size() > 0
            }
        })


        where:
        [conn, scope, schemaName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.getAllSchemas(),
            ])
        }
    }

    @Unroll("#featureName: #schema on #conn")
    def "Finds multi-column UQs correctly"() {
        expect:

        def table = new Table(standardCaseItemName("table_name", Table, scope.database), schema)
        def column1 = new Column(standardCaseItemName("col1", Column, scope.database), table.toReference(), DataType.parse("int"), true)
        def column2 = new Column(standardCaseItemName("col2", Column, scope.database), table.toReference(), DataType.parse("int"), true)
        def column3 = new Column(standardCaseItemName("col3", Column, scope.database), table.toReference(), DataType.parse("int"), true)

        def uq = new UniqueConstraint(null, table.toReference(), column1.name, column3.name)
        def snapshot = new Snapshot(scope)
        snapshot.addAll([table, column1, column2, column3, uq])

        def action = new SnapshotItemsAction(uq.toReference())

        testAction([
                schema_asTable: schema
        ], snapshot, action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1; //found more than one object

            UniqueConstraint foundUq = results.asObject(UniqueConstraint)
            assert foundUq.relation.equals(table.toReference(), true)
            assert foundUq.columns.size() == 2

            assert foundUq.columns[0] == column1.name
            assert foundUq.columns[1] == column3.name

        })


        where:
        [conn, scope, schema] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
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

        def columnName = standardCaseItemName("id", Column, scope.database)
        //Crate the expected UQ/table combo
        for (ItemReference relatedTo : ((SnapshotItemsAction) action).relatedTo) {
            if (relatedTo instanceof UniqueConstraintReference) {

                def tableName = relatedTo.container
                if (tableName == null || tableName.name == null) {
                    tableName = new RelationReference(Table, standardCaseItemName("test_table", Table, scope.database), ((UniqueConstraintReference) relatedTo).getSchema())
                }

                snapshot.add(new Table(tableName.name, tableName.container))
                snapshot.add(new Column(columnName, tableName, DataType.parse("int"), true))
                snapshot.add(new UniqueConstraint(relatedTo.name, tableName, columnName))
            } else if (relatedTo.instanceOf(Table)) {
                def table = new Table(relatedTo.name, relatedTo.container)
                if (table.name == null) {
                    table.name = standardCaseItemName("test_table", Table, scope.database)
                }
                snapshot.add(table)
                snapshot.add(new Column(columnName, table.toReference(), DataType.parse("int"), true))
                snapshot.add(new UniqueConstraint(null, table.toReference(), columnName))
            }
        }

        //create some additional tables
        for (int i = 0; i < 5; i++) {
            i = i + 1;
            for (ItemReference schema : connectionSupplier.getAllSchemas()) {
                def tableName = new RelationReference(Table, standardCaseItemName("table_$i", Table, scope.database), schema)
                snapshot.add(new Table(tableName.name, tableName.container))
                snapshot.add(new Column(columnName, tableName, DataType.parse("int"), true))
                snapshot.add(new UniqueConstraint(standardCaseItemName("uq_test_" + i, UniqueConstraint, scope.database), tableName, columnName))
            }
        }

        return snapshot
    }
}
