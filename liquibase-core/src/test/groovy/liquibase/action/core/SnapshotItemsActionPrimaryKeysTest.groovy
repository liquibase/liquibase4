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
import liquibase.item.core.PrimaryKey
import liquibase.item.core.PrimaryKeyReference
import liquibase.item.core.Relation
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.util.CollectionUtil
import spock.lang.Unroll

class SnapshotItemsActionPrimaryKeysTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can find complex pk names without a table"() {
        expect:
        testAction([
                pk_asTable: action.relatedTo*.toString()
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundPk = results.asObject(PrimaryKey)

            def foundPkRef = foundPk.toReference()
            assert foundPkRef.equals(pkRef, true)
            assert foundPk.columns.size() > 0
        })

        where:
        [conn, scope, action] << okIfEmpty("No permutations that support named primary keys", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            if (!((TestDetails) getTestDetails(scope)).testNamedPrimaryKeys()) {
                return []
            }

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemNames(PrimaryKey, ItemNameStrategy.COMPLEX_NAMES, scope).collect({ return new SnapshotItemsAction(new PrimaryKeyReference(it)) }),
            ], new ValidActionFilter(scope))
        })
    }

    @Unroll("#featureName: #action on #conn")
    def "can find complex pk names with a table"() {
        when:
        def pkRef = action.relatedTo.iterator().next()

        then:
        testAction([
                pk_asTable: pkRef
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundPk = results.asObject(PrimaryKey)

            def foundPkRef = foundPk.toReference()
            assert foundPkRef.equals(pkRef, true)
            assert foundPk.columns.size() > 0
        })

        where:
        [conn, scope, action] << okIfEmpty("No permutations that support named primary keys", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            if (!((TestDetails) getTestDetails(scope)).testNamedPrimaryKeys()) {
                return []
            }

            def tableName = standardCaseItemName("known_table", Table, scope.getDatabase())

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    CollectionUtil.permutationsWithoutNulls([
                            it.allSchemas,
                            getItemNames(PrimaryKey, ItemNameStrategy.COMPLEX_NAMES, scope)
                    ]).collect({
                        def ref = new PrimaryKeyReference(it[1], new RelationReference(Table, tableName, it[0]))

                        return new SnapshotItemsAction(ref)
                    })
            ], new ValidActionFilter(scope))
        })
    }

    @Unroll("#featureName: #pkRef on #conn")
    def "can find by PrimaryKeyReference with a table name but null primary key name"() {
        expect:
        def action = new SnapshotItemsAction(pkRef)

        testAction([
                pk_asTable: pkRef
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundPk = results.asObject(PrimaryKey)

            def foundPkRef = foundPk.toReference()
            assert foundPkRef.equals(pkRef, true)
            assert foundPk.columns.size() > 0
        })

        where:
        [conn, scope, pkRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemReferences(Table, it.getAllSchemas(), ItemNameStrategy.COMPLEX_NAMES, scope).collect({ new PrimaryKeyReference(null, it) }),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all primaryKeys in a fully qualified complex table name"() {
        expect:
        def action = new SnapshotItemsAction(PrimaryKey, tableName)

        testAction([
                table_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;


            def foundPk = results.asObject(PrimaryKey)
            assert foundPk.relation.equals(tableName, true)
            assert foundPk.columns.size() > 0
        })


        where:
        [conn, scope, tableName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemReferences(Table,it.getAllSchemas(),  ItemNameStrategy.COMPLEX_NAMES, scope),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all primaryKeys related to a table with a null name"() {
        expect:
        def action = new SnapshotItemsAction(PrimaryKey, tableName)

        testAction([
                table_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0;


            for (def foundPk : results.asList(PrimaryKey)) {
                assert foundPk.relation.equals(tableName, true)
                assert foundPk.columns.size() > 0
            }
        })


        where:
        [conn, scope, tableName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return okIfEmpty("May not support snapshotting PKs without a table name", CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.getAllSchemas().collect({ return new RelationReference(Table, null, it) }),
            ], new ValidActionFilter(scope)))
        }
    }

    @Unroll("#featureName: #schemaName on #conn")
    def "can find all primaryKeys related to a schema"() {
        expect:
        def action = new SnapshotItemsAction(PrimaryKey, schemaName)

        testAction([
                schema_asTable: schemaName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0;


            for (def foundPk : results.asList(PrimaryKey)) {
                assert foundPk.relation.container.equals(schemaName, true)
                assert foundPk.columns.size() > 0
            }
        })


        where:
        [conn, scope, schemaName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return okIfEmpty("May not support snapshotting PKs without a table name", CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.getAllSchemas(),
            ], new ValidActionFilter(scope)))
        }
    }

    @Unroll("#featureName: #schemaName on #conn")
    def "Finds multi-column PKs correctly"() {
        expect:

        def table = new Table(standardCaseItemName("table_name", Table, scope.database), schemaName)
        def column1 = new Column(standardCaseItemName("col1", Column, scope.database), table.toReference(), DataType.parse("int"), false)
        def column2 = new Column(standardCaseItemName("col2", Column, scope.database), table.toReference(), DataType.parse("int"), false)
        def column3 = new Column(standardCaseItemName("col3", Column, scope.database), table.toReference(), DataType.parse("int"), false)

        def pk = new PrimaryKey(null, table.toReference(), column1.name, column3.name)
        def snapshot = new Snapshot(scope)
        snapshot.addAll([table, column1, column2, column3, pk])

        def action = new SnapshotItemsAction(pk.toReference())

        testAction([
                schema_asTable: schemaName
        ], snapshot, action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1; //found more than one object

            PrimaryKey foundPk = results.asObject(PrimaryKey)
            assert foundPk.relation.equals(table.toReference(), true)
            assert foundPk.columns.size() == 2

            assert foundPk.columns[0].name == column1.name
            assert foundPk.columns[1].name == column3.name

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

        def columnName = standardCaseItemName("id", Column, scope.database)
        //Crate the expected PK/table combo
        for (ItemReference relatedTo : ((SnapshotItemsAction) action).relatedTo) {
            if (relatedTo.instanceOf(PrimaryKey)) {

                def table = relatedTo.container ?: new RelationReference(Table, standardCaseItemName("test_table", Table, scope.database))

                snapshot.add(new Table(table.name, table.container))
                snapshot.add(new Column(columnName, table, DataType.parse("int"), false))
                snapshot.add(new PrimaryKey(relatedTo.name, table, columnName))
            } else if (relatedTo.instanceOf(Relation)) {
                if (relatedTo.name == null) {
                    continue
                }
                snapshot.add(new Table(relatedTo.name, relatedTo.container))
                snapshot.add(new Column(columnName, relatedTo, DataType.parse("int"), false))
                snapshot.add(new PrimaryKey(null, relatedTo, columnName))
            }
        }

        boolean testNamedPKs = ((TestDetails) getTestDetails(scope)).testNamedPrimaryKeys();

        //create some additional tables
        for (int i = 0; i < 5; i++) {
            i = i + 1;
            for (ItemReference schema : connectionSupplier.getAllSchemas()) {
                def table = new RelationReference(Table, standardCaseItemName("table_$i", Table, scope.database), schema)
                snapshot.add(new Table(table.name, table.container))
                snapshot.add(new Column(columnName, table, DataType.parse("int"), false))

                def pkName = null;
                if (testNamedPKs) {
                    pkName = standardCaseItemName("pk_test_" + i, PrimaryKey, scope.database)
                }

                snapshot.add(new PrimaryKey(pkName, table, columnName))
            }
        }

        return snapshot
    }

    public static class TestDetails extends AbstractActionTest.TestDetails {
        public boolean testNamedPrimaryKeys() {
            return true;
        }
    }

}
