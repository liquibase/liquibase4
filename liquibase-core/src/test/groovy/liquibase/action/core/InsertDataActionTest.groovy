package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Column
import liquibase.item.core.RelationReference
import liquibase.item.core.RowData
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class InsertDataActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can insert with just numbers but complex names"() {
        expect:
        testAction([
                relation_asTable: action.data*.relation,
                columns_asTable : action.data*.getColumns(),
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(InsertDataAction, [
                            data: CollectionUtil.toSingletonLists(TestUtil.createAllPermutationsWithoutNulls(RowData, [
                                    relation: getItemReferences(Table, it.allSchemas, scope),
                                    data    : CollectionUtil.toSingletonLists(getItemNames(Column, scope).collect({
                                        return new RowData.Cell(it, 42)
                                    }))
                            ]))
                    ])
            ], new ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "can add columns with various options"() {
        expect:
        testAction([
                relation_asTable: action.data*.relation,
                columns_asTable : action.data*.getColumns(),
                values_asTable  : action.data*.getValues(),
                types_asTable  : action.data*.getTypes(),
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new ValidActionFilter(scope))
        }
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        def tableRef = new RelationReference(Table, standardCaseItemName("test_table", Table, scope))
        def column1Name = standardCaseItemName("column1", Column, scope)
        def column2Name = standardCaseItemName("column2", Column, scope)


        return TestUtil.createAllPermutations(InsertDataAction, [
                data: [
                        [],
                        [new RowData(tableRef).add(column1Name, 42, new DataType(DataType.StandardType.INTEGER))], //insert an integer
                        [new RowData(tableRef).add(column1Name, 123142, new DataType(DataType.StandardType.BIGINT))], //insert a bigint
                        [new RowData(tableRef).add(column1Name, 12.5, new DataType(DataType.StandardType.FLOAT))], //insert a float
                        [new RowData(tableRef).add(column1Name, "test string", new DataType(DataType.StandardType.VARCHAR, 50))], //insert a varchar

                        [new RowData(tableRef).add(column1Name, null, new DataType(DataType.StandardType.INTEGER))], //insert a null int
                        [new RowData(tableRef).add(column1Name, null, new DataType(DataType.StandardType.VARCHAR, 50))], //insert a null varchar

                        [new RowData(tableRef)  //insert two columns: both varchar
                            .add(column1Name, "test string 1", new DataType(DataType.StandardType.VARCHAR, 50))
                             .add(column2Name, "test string 2", new DataType(DataType.StandardType.VARCHAR, 50))
                        ],
                        [new RowData(tableRef)  //insert two columns: one varchar, one int
                                 .add(column1Name, "test string 1", new DataType(DataType.StandardType.VARCHAR, 50))
                                 .add(column2Name, 2362, new DataType(DataType.StandardType.INTEGER))
                        ],
                ]
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)
        def seenTables = new HashSet<RelationReference>()
        def seenColumns = new HashMap<RelationReference, Set<String>>()
        for (RowData row : ((InsertDataAction) action).data) {
            if (seenTables.add(row.relation)) {
                snapshot.add(new Table(row.relation.name, row.relation.schema))
                seenColumns.put(row.relation, new HashSet<String>())
            }
            for (def cell : row.data) {
                if (seenColumns.get(row.relation).add(cell.columnName)) {
                    def dataType = cell.type ?: new DataType(DataType.StandardType.INTEGER);
                    snapshot.add(new Column(cell.columnName, row.relation, dataType, true))
                }
            }
        }
        return snapshot
    }
}
