package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ObjectBasedQueryResult
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.database.Database
import liquibase.snapshot.Snapshot
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.ObjectReference
import liquibase.structure.core.Column
import liquibase.structure.core.Index
import liquibase.structure.core.PrimaryKey
import liquibase.structure.core.Schema
import liquibase.structure.core.Table
import liquibase.structure.datatype.DataType
import liquibase.util.CollectionUtil
import spock.lang.Unroll

class SnapshotObjectsActionIndexesTest extends AbstractActionTest {

    @Unroll("#featureName: #indexRef on #conn")
    def "can find complex indexes names without a table"() {
        expect:
        def action = new SnapshotObjectsAction(indexRef)

        testAction([
                indexName_asTable: indexRef
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
            return okIfEmpty("May not support snapshotting indexes without a table", CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Index, ObjectNameStrategy.COMPLEX_NAMES, scope).unique().collect({ it.container.name = null; return it }),
            ], new ValidActionFilter(scope)))
        }
    }

    @Unroll("#featureName: #indexRef on #conn")
    def "can find complex index names with a table"() {
        expect:
        def action = new SnapshotObjectsAction(indexRef)

        testAction([
                indexName_asTable: indexRef
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
                    getObjectNames(Index, ObjectNameStrategy.COMPLEX_NAMES, scope).unique().collect({ it.container.name = standardCaseObjectName("known_table", Table, scope.getDatabase()); return it }),
            ])
        }
    }

    @Unroll("#featureName: #indexRef on #conn")
    def "can find by IndexReference with a table name but null index name"() {
        expect:
        def action = new SnapshotObjectsAction(indexRef)

        testAction([
                indexName_asTable: indexRef
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
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope).collect({ new Index.IndexReference(it, null) }),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all indexes in a fully qualified complex table name"() {
        expect:
        def action = new SnapshotObjectsAction(Index, tableName)

        testAction([
                tableName_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;


            def foundIndex = results.asObject(Index)
            assert foundIndex.table.equals(tableName, true)
            assert foundIndex.columns.size() > 0
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
    def "can find all indexes related to a table with a null name"() {
        expect:
        def action = new SnapshotObjectsAction(Index, tableName)

        testAction([
                tableName_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0;


            for (def foundIndex : results.asList(Index)) {
                assert foundIndex.table.equals(tableName, true)
                assert foundIndex.columns.size() > 0
            }
        })


        where:
        [conn, scope, tableName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return okIfEmpty("May not support snapshotting indexes without a table name", CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Schema, ObjectNameStrategy.COMPLEX_NAMES, scope).collect({ return new ObjectReference(Table, it, null) }),
            ], new ValidActionFilter(scope)))
        }
    }

    @Unroll("#featureName: #schemaName on #conn")
    def "can find all indexes related to a schema"() {
        expect:
        def action = new SnapshotObjectsAction(Index, schemaName)

        testAction([
                schemaName_asTable: schemaName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0;


            for (def foundIndex : results.asList(Index)) {
                assert foundIndex.table.container.equals(schemaName, true)
                assert foundIndex.columns.size() > 0
            }
        })


        where:
        [conn, scope, schemaName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return okIfEmpty("May not support snapshotting indexes without a table", CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Schema, scope),
            ], new ValidActionFilter(scope)))
        }
    }

    @Unroll("#featureName: #schemaName on #conn")
    def "Finds multi-column indexes correctly"() {
        expect:

        def table = new Table(schemaName, standardCaseObjectName("table_name", Table, scope.database))
        def column1 = new Column(table.toReference(), standardCaseObjectName("col1", Column, scope.database), "int")
        def column2 = new Column(table.toReference(), standardCaseObjectName("col2", Column, scope.database), "int")
        def column3 = new Column(table.toReference(), standardCaseObjectName("col3", Column, scope.database), "int")

        def index = new Index(standardCaseObjectName("idx_table_name", Index, scope.database), table.toReference(), new Index.IndexedColumn(column1.name), new Index.IndexedColumn(column3.name))
        def snapshot = new Snapshot(scope)
        snapshot.addAll([table, column1, column2, column3, index])

        def action = new SnapshotObjectsAction(index.toReference())

        testAction([
                schemaName_asTable: schemaName
        ], snapshot, action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1; //found more than one object

            Index foundIndex = results.asObject(Index)
            assert foundIndex.table.equals(table.toReference(), true)
            assert foundIndex.columns.size() == 2

            assert foundIndex.columns[0].name == column1.name
            assert foundIndex.columns[1].name == column3.name

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

    @Unroll("#featureName: #conn")
    def "Snapshots column direction correctly"() {
        when:
        def schemaName = getObjectNames(Schema, scope)[0]
        def table = new Table(schemaName, standardCaseObjectName("table1", Table, scope.database))
        def columnAsc = new Column(table.toReference(), standardCaseObjectName("col_asc", Column, scope.database), "int")
        def columnDesc = new Column(table.toReference(), standardCaseObjectName("col_desc", Column, scope.database), "int")

        def index = new Index(standardCaseObjectName("idx_table1", Index, scope.database), table.toReference())
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

        def action = new SnapshotObjectsAction(index.toReference())

        then:
        testAction([
                schemaName_asTable: schemaName
        ], snapshot, action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1; //found more than one object

            Index foundIndex = results.asObject(Index)
            assert foundIndex.table.equals(table.toReference(), true)
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
        def schemaName = getObjectNames(Schema, scope)[0]
        def table1 = new Table(schemaName, standardCaseObjectName("table1", Table, scope.database))
        def table1Col1 = new Column(table1.toReference(), standardCaseObjectName("col1", Column, scope.database), DataType.parse("int"), false)
        def table1Col2 = new Column(table1.toReference(), standardCaseObjectName("col2", Column, scope.database), DataType.parse("int"), false)
        def table1Col3 = new Column(table1.toReference(), standardCaseObjectName("col3", Column, scope.database), DataType.parse("int"), false)
        def table1Col4 = new Column(table1.toReference(), standardCaseObjectName("col4", Column, scope.database), DataType.parse("int"), false)

        def table2 = new Table(schemaName, standardCaseObjectName("table2", Table, scope.database))
        def table2Col1 = new Column(table2.toReference(), standardCaseObjectName("col1", Column, scope.database), DataType.parse("int"), false)
        def table2Col2 = new Column(table2.toReference(), standardCaseObjectName("col2", Column, scope.database), DataType.parse("int"), false)
        def table2Col3 = new Column(table2.toReference(), standardCaseObjectName("col3", Column, scope.database), DataType.parse("int"), false)
        def table2Col4 = new Column(table2.toReference(), standardCaseObjectName("col4", Column, scope.database), DataType.parse("int"), false)

        def table1PK = new PrimaryKey(null, table1.toReference(), table1Col1.name)
        def table2PK = new PrimaryKey(null, table2.toReference(), table2Col2.name, table2Col3.name)

        def table1Index1 = new Index(concatConsistantCaseObjectName(table1.name, "_idx1"), table1.toReference(), new Index.IndexedColumn(table1Col2.name), new Index.IndexedColumn(table1Col3.name))
        def table1Index2 = new Index(concatConsistantCaseObjectName(table1.name, "_idx2"), table1.toReference(), new Index.IndexedColumn(table1Col3.name))
        def table1Index3 = new Index(concatConsistantCaseObjectName(table1.name, "_idx3"), table1.toReference(), new Index.IndexedColumn(table1Col4.name))
        def table1Index4 = new Index(concatConsistantCaseObjectName(table1.name, "_idx4"), table1.toReference(), new Index.IndexedColumn(table1Col1.name), new Index.IndexedColumn(table1Col2.name))

        def table2Index1 = new Index(concatConsistantCaseObjectName(table2.name, "_idx1"), table2.toReference(), new Index.IndexedColumn(table2Col1.name))
        def table2Index2 = new Index(concatConsistantCaseObjectName(table2.name, "_idx2"), table2.toReference(), new Index.IndexedColumn(table2Col2.name))
        def table2Index3 = new Index(concatConsistantCaseObjectName(table2.name, "_idx3"), table2.toReference(), new Index.IndexedColumn(table2Col3.name))
        def table2Index4 = new Index(concatConsistantCaseObjectName(table2.name, "_idx4"), table2.toReference(), new Index.IndexedColumn(table2Col2.name), new Index.IndexedColumn(table2Col4.name))

        def snapshot = new Snapshot(scope)
        snapshot.addAll([table1, table2,
                         table1PK, table2PK,
                         table1Col1, table1Col2, table1Col3, table1Col4,
                         table2Col1, table2Col2, table2Col3, table2Col4,
                         table1Index1, table1Index2, table1Index3, table1Index4,
                         table2Index1, table2Index2, table2Index3, table2Index4,
        ])

        def action = new SnapshotObjectsAction(Index, table1PK.toReference())

        then:
        testAction([
                primaryKey_asTable: table1PK.toReference().toString()
        ], snapshot, action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1; //found more than one object

            Index foundIndex = results.asObject(Index)
            assert foundIndex.table.equals(table1PK.table, true)
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
        def schemaName = getObjectNames(Schema, scope)[0]
        def table1 = new Table(schemaName, standardCaseObjectName("table1", Table, scope.database))
        def table1Col1 = new Column(table1.toReference(), standardCaseObjectName("col1", Column, scope.database), DataType.parse("int"), false)
        def table1Col2 = new Column(table1.toReference(), standardCaseObjectName("col2", Column, scope.database), DataType.parse("int"), false)
        def table1Col3 = new Column(table1.toReference(), standardCaseObjectName("col3", Column, scope.database), DataType.parse("int"), false)
        def table1Col4 = new Column(table1.toReference(), standardCaseObjectName("col4", Column, scope.database), DataType.parse("int"), false)

        def table2 = new Table(schemaName, standardCaseObjectName("table2", Table, scope.database))
        def table2Col1 = new Column(table2.toReference(), standardCaseObjectName("col1", Column, scope.database), DataType.parse("int"), false)
        def table2Col2 = new Column(table2.toReference(), standardCaseObjectName("col2", Column, scope.database), DataType.parse("int"), false)
        def table2Col3 = new Column(table2.toReference(), standardCaseObjectName("col3", Column, scope.database), DataType.parse("int"), false)
        def table2Col4 = new Column(table2.toReference(), standardCaseObjectName("col4", Column, scope.database), DataType.parse("int"), false)

        def table1PK = new PrimaryKey(null, table1.toReference(), table1Col1.name)
        def table2PK = new PrimaryKey(null, table2.toReference(), table2Col2.name, table2Col3.name)

        def table1Index1 = new Index(concatConsistantCaseObjectName(table1.name, "_idx1"), table1.toReference(), new Index.IndexedColumn(table1Col2.name), new Index.IndexedColumn(table1Col3.name))
        def table1Index2 = new Index(concatConsistantCaseObjectName(table1.name, "_idx2"), table1.toReference(), new Index.IndexedColumn(table1Col3.name))
        def table1Index3 = new Index(concatConsistantCaseObjectName(table1.name, "_idx3"), table1.toReference(), new Index.IndexedColumn(table1Col4.name))
        def table1Index4 = new Index(concatConsistantCaseObjectName(table1.name, "_idx4"), table1.toReference(), new Index.IndexedColumn(table1Col1.name), new Index.IndexedColumn(table1Col2.name))

        def table2Index1 = new Index(concatConsistantCaseObjectName(table2.name, "_idx1"), table2.toReference(), new Index.IndexedColumn(table2Col1.name))
        def table2Index2 = new Index(concatConsistantCaseObjectName(table2.name, "_idx2"), table2.toReference(), new Index.IndexedColumn(table2Col2.name))
        def table2Index3 = new Index(concatConsistantCaseObjectName(table2.name, "_idx3"), table2.toReference(), new Index.IndexedColumn(table2Col3.name))
        def table2Index4 = new Index(concatConsistantCaseObjectName(table2.name, "_idx4"), table2.toReference(), new Index.IndexedColumn(table2Col2.name), new Index.IndexedColumn(table2Col4.name))

        def snapshot = new Snapshot(scope)
        snapshot.addAll([table1, table2,
                         table1PK, table2PK,
                         table1Col1, table1Col2, table1Col3, table1Col4,
                         table2Col1, table2Col2, table2Col3, table2Col4,
                         table1Index1, table1Index2, table1Index3, table1Index4,
                         table2Index1, table2Index2, table2Index3, table2Index4,
        ])

        def action = new SnapshotObjectsAction(Index, table2PK.toReference())

        then:
        testAction([
                primaryKey_asTable: table2PK.toReference().toString()
        ], snapshot, action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1; //found more than one object

            Index foundIndex = results.asObject(Index)
            assert foundIndex.table.equals(table2PK.table, true)
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

        def columnName = standardCaseObjectName("id", Column, scope.database)
        //Crate the expected index/table combo
        int incNumber = 0;
        for (ObjectReference relatedTo : ((SnapshotObjectsAction) action).relatedTo) {
            if (relatedTo.instanceOf(Index)) {

                def tableName = relatedTo.container ?: new ObjectReference(Table, standardCaseObjectName("test_table", Table, scope.database))
                if (tableName.name == null) {
                    tableName.name = standardCaseObjectName("test_table", Table, scope.database)
                }

                snapshot.add(new Table(tableName))
                snapshot.add(new Column(tableName, columnName, "int"))

                def indexName = relatedTo.name ?: standardCaseObjectName("idx_passed", Index, scope.database)

                snapshot.add(new Index(indexName, tableName, new Index.IndexedColumn(columnName)))
            } else if (relatedTo.instanceOf(Table)) {
                relatedTo = relatedTo.clone()
                if (relatedTo.name == null) {
                    relatedTo.name = standardCaseObjectName("generated_table_" + (incNumber++), Table, scope.database)
                }
                snapshot.add(new Table(relatedTo))
                snapshot.add(new Column(relatedTo, columnName, "int"))
                snapshot.add(new Index(standardCaseObjectName("idx_passed_table", Index, scope.database), relatedTo, new Index.IndexedColumn(columnName)))
            }
        }

        //create some additional tables
        for (int i = 0; i < 5; i++) {
            i = i + 1;
            for (ObjectReference schema : getObjectNames(Schema, scope)) {
                def tableName = new ObjectReference(Table, schema, standardCaseObjectName("table_$i", Table, scope.database))
                snapshot.add(new Table(tableName))
                snapshot.add(new Column(tableName, columnName, "int"))
                snapshot.add(new Index(standardCaseObjectName("idx_test_" + i, Index, scope.database), tableName, new Index.IndexedColumn(columnName)))
            }
        }

        return snapshot
    }

    @Override
    def createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return null //not used
    }
}
