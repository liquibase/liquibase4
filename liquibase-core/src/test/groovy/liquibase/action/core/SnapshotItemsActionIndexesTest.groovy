package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ObjectBasedQueryResult
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.database.Database
import liquibase.item.TestItemSupplier
import liquibase.snapshot.Snapshot

import liquibase.item.ItemReference
import liquibase.item.core.*
import liquibase.item.datatype.DataType
import liquibase.util.CollectionUtil
import liquibase.util.StringUtil
import spock.lang.Unroll

class SnapshotItemsActionIndexesTest extends AbstractActionTest {

    @Unroll("#featureName: #indexRef on #conn")
    def "can find complex indexes names without a table"() {
        expect:
        def action = new SnapshotItemsAction(indexRef)

        testAction([
                index_asTable: indexRef
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundIndex = results.asObject(Index)

            def foundIndexRef = foundIndex.toReference()
            assert foundIndexRef.equals(indexRef, true)
            assert foundIndex.columns.size() > 0
        })

        where:
        [conn, scope, indexRef] << okIfEmpty("May not support snapshotting indexes without a table", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemNames(Index, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope).collect({
                        return new IndexReference(it)
                    }),
            ], new ValidActionFilter(scope))
        })
    }

    @Unroll("#featureName: #indexRef on #conn")
    def "can find complex index names with a table"() {
        expect:
        def action = new SnapshotItemsAction(indexRef)

        testAction([
                index_asTable: indexRef
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundIndex = results.asObject(Index)

            def foundIndexRef = foundIndex.toReference()
            assert foundIndexRef.equals(indexRef, true)
            assert foundIndex.columns.size() > 0
        })

        where:
        [conn, scope, indexRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemNames(Index, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope).collect({
                        return new IndexReference(it, new RelationReference(Table, standardCaseItemName("known_table", Table, scope)))
                    }),
            ])
        }
    }

    @Unroll("#featureName: #indexRef on #conn")
    def "can find by IndexReference with a table name but null index name"() {
        expect:
        def action = new SnapshotItemsAction(indexRef)

        testAction([
                index_asTable: indexRef
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundIndex = results.asObject(Index)

            def foundIndexRef = foundIndex.toReference()
            assert foundIndexRef.equals(indexRef, true)
            assert foundIndex.columns.size() > 0
        })

        where:
        [conn, scope, indexRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemReferences(Table, it.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope).collect({
                        new IndexReference(null, it)
                    }),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all indexes in a fully qualified complex table name"() {
        expect:
        def action = new SnapshotItemsAction(Index, tableName)

        testAction([
                table_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;


            def foundIndex = results.asObject(Index)
            assert foundIndex.relation.equals(tableName, true)
            assert foundIndex.columns.size() > 0
        })


        where:
        [conn, scope, tableName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemReferences(Table, it.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all indexes related to a table with a null name"() {
        expect:
        def action = new SnapshotItemsAction(Index, tableName)

        testAction([
                table_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0;


            for (def foundIndex : results.asList(Index)) {
                assert foundIndex.relation.equals(tableName, true)
                assert foundIndex.columns.size() > 0
            }
        })


        where:
        [conn, scope, tableName] << okIfEmpty("May not support snapshotting indexes without a table name", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.getAllSchemas().collect({ return new RelationReference(Table, null, it) }),
            ], new ValidActionFilter(scope))
        })
    }

    @Unroll("#featureName: #schemaName on #conn")
    def "can find all indexes related to a schema"() {
        expect:
        def action = new SnapshotItemsAction(Index, schemaName)

        testAction([
                schema_asTable: schemaName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0;


            for (def foundIndex : results.asList(Index)) {
                assert foundIndex.relation.container.equals(schemaName, true)
                assert foundIndex.columns.size() > 0
            }
        })


        where:
        [conn, scope, schemaName] << okIfEmpty("May not support snapshotting indexes without a table", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.getAllSchemas(),
            ], new ValidActionFilter(scope))
        })
    }

    @Unroll("#featureName: #schema on #conn")
    def "Finds multi-column indexes correctly"() {
        expect:

        def table = new Table(standardCaseItemName("table_name", Table, scope), schema)
        def column1 = new Column(standardCaseItemName("col1", Column, scope), table.toReference(), DataType.parse("int"), true)
        def column2 = new Column(standardCaseItemName("col2", Column, scope), table.toReference(), DataType.parse("int"), true)
        def column3 = new Column(standardCaseItemName("col3", Column, scope), table.toReference(), DataType.parse("int"), true)

        def index = new Index(standardCaseItemName("idx_table_name", Index, scope), table.toReference(), new Index.IndexedColumn(column1.name), new Index.IndexedColumn(column3.name))
        def snapshot = new Snapshot(scope)
        snapshot.addAll([table, column1, column2, column3, index])

        def action = new SnapshotItemsAction(index.toReference())

        testAction([
                schema_asTable: schema
        ], snapshot, action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1; //found more than one object

            Index foundIndex = results.asObject(Index)
            assert foundIndex.relation.equals(table.toReference(), true)
            assert foundIndex.columns.size() == 2

            assert foundIndex.columns[0].name == column1.name
            assert foundIndex.columns[1].name == column3.name

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

    @Unroll("#featureName: #conn")
    def "Snapshots column direction correctly"() {
        when:
        def schemaName = conn.getAllSchemas()[0]
        def table = new Table(standardCaseItemName("table1", Table, scope), schemaName)
        def columnAsc = new Column(standardCaseItemName("col_asc", Column, scope), table.toReference(), DataType.parse("int"), true)
        def columnDesc = new Column(standardCaseItemName("col_desc", Column, scope), table.toReference(), DataType.parse("int"), true)

        def index = new Index(standardCaseItemName("idx_table1", Index, scope), table.toReference())
        index.columns = [
                new Index.IndexedColumn(columnAsc.name, Index.IndexDirection.ASC),
                new Index.IndexedColumn(columnDesc.name, Index.IndexDirection.DESC)
        ].findAll {
            def database = scope.getDatabase();

            if (it.direction == null) {
                return true;
            }
            switch (it.direction) {
                case Index.IndexDirection.ASC:
                    return database.supports(Database.Feature.INDEXES_ASC, scope) ? true : false;
                case Index.IndexDirection.DESC:
                    return database.supports(Database.Feature.INDEXES_DESC, scope) ? true : false;
            }
        }

        def snapshot = new Snapshot(scope)
        snapshot.addAll([table, columnAsc, columnDesc, index])

        def action = new SnapshotItemsAction(index.toReference())

        then:
        testAction([
                schema_asTable: schemaName
        ], snapshot, action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1; //found more than one object

            Index foundIndex = results.asObject(Index)
            assert foundIndex.relation.equals(table.toReference(), true)
            assert foundIndex.columns.size() == index.columns.size()

            assert foundIndex.columns[0].name == columnAsc.name
            assert foundIndex.columns[0].direction == Index.IndexDirection.ASC

            if (foundIndex.columns.size() > 1) {
                assert foundIndex.columns[1].direction == Index.IndexDirection.DESC
                assert foundIndex.columns[1].name == columnDesc.name
            }
        })

        where:
        [conn, scope] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
            ])
        }
    }

    @Unroll("#featureName: #conn")
    def "Snapshots single-column primary key correctly"() {
        when:
        def schemaName = conn.getAllSchemas()[0]
        def table1 = new Table(standardCaseItemName("table1", Table, scope), schemaName)
        def table1Col1 = new Column(standardCaseItemName("col1", Column, scope), table1.toReference(), DataType.parse("int"), false)
        def table1Col2 = new Column(standardCaseItemName("col2", Column, scope), table1.toReference(), DataType.parse("int"), false)
        def table1Col3 = new Column(standardCaseItemName("col3", Column, scope), table1.toReference(), DataType.parse("int"), false)
        def table1Col4 = new Column(standardCaseItemName("col4", Column, scope), table1.toReference(), DataType.parse("int"), false)

        def table2 = new Table(standardCaseItemName("table2", Table, scope), schemaName)
        def table2Col1 = new Column(standardCaseItemName("col1", Column, scope), table2.toReference(), DataType.parse("int"), false)
        def table2Col2 = new Column(standardCaseItemName("col2", Column, scope), table2.toReference(), DataType.parse("int"), false)
        def table2Col3 = new Column(standardCaseItemName("col3", Column, scope), table2.toReference(), DataType.parse("int"), false)
        def table2Col4 = new Column(standardCaseItemName("col4", Column, scope), table2.toReference(), DataType.parse("int"), false)

        def table1PK = new PrimaryKey(null, table1.toReference(), table1Col1.name)
        def table2PK = new PrimaryKey(null, table2.toReference(), table2Col2.name, table2Col3.name)

        def table1Index1 = new Index(StringUtil.concatConsistentCase(table1.name, "_idx1"), table1.toReference(), new Index.IndexedColumn(table1Col2.name), new Index.IndexedColumn(table1Col3.name))
        def table1Index2 = new Index(StringUtil.concatConsistentCase(table1.name, "_idx2"), table1.toReference(), new Index.IndexedColumn(table1Col3.name))
        def table1Index3 = new Index(StringUtil.concatConsistentCase(table1.name, "_idx3"), table1.toReference(), new Index.IndexedColumn(table1Col4.name))
        def table1Index4 = new Index(StringUtil.concatConsistentCase(table1.name, "_idx4"), table1.toReference(), new Index.IndexedColumn(table1Col1.name), new Index.IndexedColumn(table1Col2.name))

        def table2Index1 = new Index(StringUtil.concatConsistentCase(table2.name, "_idx1"), table2.toReference(), new Index.IndexedColumn(table2Col1.name))
        def table2Index2 = new Index(StringUtil.concatConsistentCase(table2.name, "_idx2"), table2.toReference(), new Index.IndexedColumn(table2Col2.name))
        def table2Index3 = new Index(StringUtil.concatConsistentCase(table2.name, "_idx3"), table2.toReference(), new Index.IndexedColumn(table2Col3.name))
        def table2Index4 = new Index(StringUtil.concatConsistentCase(table2.name, "_idx4"), table2.toReference(), new Index.IndexedColumn(table2Col2.name), new Index.IndexedColumn(table2Col4.name))

        def snapshot = new Snapshot(scope)
        snapshot.addAll([table1, table2,
                         table1PK, table2PK,
                         table1Col1, table1Col2, table1Col3, table1Col4,
                         table2Col1, table2Col2, table2Col3, table2Col4,
                         table1Index1, table1Index2, table1Index3, table1Index4,
                         table2Index1, table2Index2, table2Index3, table2Index4,
        ])

        def action = new SnapshotItemsAction(Index, table1PK.toReference())

        then:
        testAction([
                primaryKey_asTable: table1PK.toReference().toString()
        ], snapshot, action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1; //found more than one object

            Index foundIndex = results.asObject(Index)
            assert foundIndex.relation.equals(table1PK.relation, true)
            assert foundIndex.columns.size() == 1

            assert foundIndex.columns[0].name == table1PK.columns[0].name
        })

        where:
        [conn, scope] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
            ])
        }
    }

    @Unroll("#featureName: #conn")
    def "Snapshots multi-column primary key correctly"() {
        when:
        def schemaName = conn.getAllSchemas()[0]
        def table1 = new Table(standardCaseItemName("table1", Table, scope), schemaName)
        def table1Col1 = new Column(standardCaseItemName("col1", Column, scope), table1.toReference(), DataType.parse("int"), false)
        def table1Col2 = new Column(standardCaseItemName("col2", Column, scope), table1.toReference(), DataType.parse("int"), false)
        def table1Col3 = new Column(standardCaseItemName("col3", Column, scope), table1.toReference(), DataType.parse("int"), false)
        def table1Col4 = new Column(standardCaseItemName("col4", Column, scope), table1.toReference(), DataType.parse("int"), false)

        def table2 = new Table(standardCaseItemName("table2", Table, scope), schemaName)
        def table2Col1 = new Column(standardCaseItemName("col1", Column, scope), table2.toReference(), DataType.parse("int"), false)
        def table2Col2 = new Column(standardCaseItemName("col2", Column, scope), table2.toReference(), DataType.parse("int"), false)
        def table2Col3 = new Column(standardCaseItemName("col3", Column, scope), table2.toReference(), DataType.parse("int"), false)
        def table2Col4 = new Column(standardCaseItemName("col4", Column, scope), table2.toReference(), DataType.parse("int"), false)

        def table1PK = new PrimaryKey(null, table1.toReference(), table1Col1.name)
        def table2PK = new PrimaryKey(null, table2.toReference(), table2Col2.name, table2Col3.name)

        def table1Index1 = new Index(StringUtil.concatConsistentCase(table1.name, "_idx1"), table1.toReference(), new Index.IndexedColumn(table1Col2.name), new Index.IndexedColumn(table1Col3.name))
        def table1Index2 = new Index(StringUtil.concatConsistentCase(table1.name, "_idx2"), table1.toReference(), new Index.IndexedColumn(table1Col3.name))
        def table1Index3 = new Index(StringUtil.concatConsistentCase(table1.name, "_idx3"), table1.toReference(), new Index.IndexedColumn(table1Col4.name))
        def table1Index4 = new Index(StringUtil.concatConsistentCase(table1.name, "_idx4"), table1.toReference(), new Index.IndexedColumn(table1Col1.name), new Index.IndexedColumn(table1Col2.name))

        def table2Index1 = new Index(StringUtil.concatConsistentCase(table2.name, "_idx1"), table2.toReference(), new Index.IndexedColumn(table2Col1.name))
        def table2Index2 = new Index(StringUtil.concatConsistentCase(table2.name, "_idx2"), table2.toReference(), new Index.IndexedColumn(table2Col2.name))
        def table2Index3 = new Index(StringUtil.concatConsistentCase(table2.name, "_idx3"), table2.toReference(), new Index.IndexedColumn(table2Col3.name))
        def table2Index4 = new Index(StringUtil.concatConsistentCase(table2.name, "_idx4"), table2.toReference(), new Index.IndexedColumn(table2Col2.name), new Index.IndexedColumn(table2Col4.name))

        def snapshot = new Snapshot(scope)
        snapshot.addAll([table1, table2,
                         table1PK, table2PK,
                         table1Col1, table1Col2, table1Col3, table1Col4,
                         table2Col1, table2Col2, table2Col3, table2Col4,
                         table1Index1, table1Index2, table1Index3, table1Index4,
                         table2Index1, table2Index2, table2Index3, table2Index4,
        ])

        def action = new SnapshotItemsAction(Index, table2PK.toReference())

        then:
        testAction([
                primaryKey_asTable: table2PK.toReference().toString()
        ], snapshot, action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1; //found more than one object

            Index foundIndex = results.asObject(Index)
            assert foundIndex.relation.equals(table2PK.relation, true)
            assert foundIndex.columns.size() == 2

            assert foundIndex.columns[0].name == table2PK.columns[0].name
            assert foundIndex.columns[1].name == table2PK.columns[1].name
        })

        where:
        [conn, scope] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
            ])
        }
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)

        def columnName = standardCaseItemName("id", Column, scope)
        //Crate the expected index/table combo
        int incNumber = 0;
        for (ItemReference relatedTo : ((SnapshotItemsAction) action).relatedTo) {
            if (relatedTo.instanceOf(Index)) {

                def table = relatedTo.container ?: new RelationReference(Table, standardCaseItemName("test_table", Table, scope))
                if (table.name == null) {
                    table.name = standardCaseItemName("test_table", Table, scope)
                }

                snapshot.add(new Table(table.name, table.container))
                snapshot.add(new Column(columnName, table, DataType.parse("int"), true))

                def indexName = relatedTo.name ?: standardCaseItemName("idx_passed", Index, scope)

                snapshot.add(new Index(indexName, table, new Index.IndexedColumn(columnName)))
            } else if (relatedTo.instanceOf(Relation)) {
                relatedTo = relatedTo.clone()
                if (relatedTo.name == null) {
                    relatedTo.name = standardCaseItemName("generated_table_" + (incNumber++), Table, scope)
                }
                snapshot.add(new Table(relatedTo.name, relatedTo.container))
                snapshot.add(new Column(columnName, relatedTo, DataType.parse("int"), true))
                snapshot.add(new Index(standardCaseItemName("idx_passed_table", Index, scope), relatedTo, new Index.IndexedColumn(columnName)))
            }
        }

        //create some additional tables
        for (int i = 0; i < 5; i++) {
            i = i + 1;
            for (ItemReference schema : connectionSupplier.getAllSchemas()) {
                def table = new Table(standardCaseItemName("table_$i", Table, scope), schema)
                snapshot.add(table)
                snapshot.add(new Column(columnName, table.toReference(), DataType.parse("int"), true))
                snapshot.add(new Index(standardCaseItemName("idx_test_" + i, Index, scope), table.toReference(), new Index.IndexedColumn(columnName)))
            }
        }

        return snapshot
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return null //not used
    }
}
