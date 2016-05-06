package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ActionExecutor
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Column
import liquibase.item.core.PrimaryKey
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
                relation_asTable: action.rows*.relation,
                columns_asTable : action.rows*.getColumnNames(),
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(InsertDataAction, [
                            rows: CollectionUtil.toSingletonLists(TestUtil.createAllPermutationsWithoutNulls(RowData, [
                                    relation: getItemReferences(Table, it.allSchemas, scope),
                                    columns : CollectionUtil.toSingletonLists(getItemNames(Column, scope).collect({
                                        return new RowData.ColumnData(it, 42)
                                    }))
                            ])),
                    ])
            ], new ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "merge statement works"() {
        def snapshot = new Snapshot(scope)

        def tableRef = ((InsertDataAction) action).rows[0].relation
        snapshot.add(new Table(tableRef.name, tableRef.schema))

        final def idCol = standardCaseItemName("id", Column, scope)
        final def nameCol = standardCaseItemName("name", Column, scope)

        snapshot.add(new Column(idCol, tableRef, new DataType(DataType.StandardType.INTEGER), false))

        snapshot.add(new Column(nameCol, tableRef, new DataType(DataType.StandardType.VARCHAR, 20), false))
        snapshot.add(new PrimaryKey(null, tableRef, idCol))
        snapshot.add(new RowData(tableRef)
                .add(idCol, 1, new DataType(DataType.StandardType.INTEGER))
                .add(nameCol, "user 1 - orig", new DataType(DataType.StandardType.VARCHAR)))

        snapshot.add(new RowData(tableRef)
                .add(idCol, 2, new DataType(DataType.StandardType.INTEGER))
                .add(nameCol, "user 2 - orig", new DataType(DataType.StandardType.VARCHAR)))

        testAction([
                relation_asTable             : action.rows*.relation,
                columns_asTable              : action.rows*.getColumnNames(),
                columnsForUpdateCheck_asTable: action.columnsForUpdateCheck
        ], snapshot, action, conn, scope, {
            plan, results ->
                def rs = ((Scope) scope).getSingleton(ActionExecutor).query(new SelectDataAction(tableRef, new SelectDataAction.SelectedColumn(null, "*", null, true)).addOrder(new SelectDataAction.OrderedByColumn(idCol)), scope).rows
                assert rs.size() == 3
                assert rs[0].toString().toLowerCase() == "[id=1, name=user 1 - new]"
                assert rs[1].toString().toLowerCase() == "[id=2, name=user 2 - orig]"
                assert rs[2].toString().toLowerCase() == "[id=3, name=user 3 - new]"
        })

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            def idColName = standardCaseItemName("id", Column, scope)
            def nameColName = standardCaseItemName("name", Column, scope)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.allSchemas.collect({
                        def table = new RelationReference(Table, standardCaseItemName("test_table", Table, scope), it);
                        return new InsertDataAction([
                                new RowData(table)
                                        .add(idColName, 1, new DataType(DataType.StandardType.INTEGER))
                                        .add(nameColName, "user 1 - new", new DataType(DataType.StandardType.VARCHAR, 20)),

                                new RowData(table)
                                        .add(idColName, 3, new DataType(DataType.StandardType.INTEGER))
                                        .add(nameColName, "user 3 - new", new DataType(DataType.StandardType.VARCHAR)),
                        ])
                    }).each {
                        it.columnsForUpdateCheck = [idColName]
                    }
            ], new ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "can add columns with various options"() {
        expect:
        testAction([
                relation_asTable             : action.rows*.relation,
                columns_asTable              : action.rows*.getColumnNames(),
                values_asTable               : action.rows*.getValues(),
                types_asTable                : action.rows*.getTypes(),
                columnsForUpdateCheck_asTable: action.columnsForUpdateCheck,
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
                rows                 : [
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
                ],
                columnsForUpdateCheck: [[column1Name]],
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)
        def seenTables = new HashSet<RelationReference>()
        def seenColumns = new HashMap<RelationReference, Set<String>>()
        for (RowData row : ((InsertDataAction) action).rows) {
            if (seenTables.add(row.relation)) {
                snapshot.add(new Table(row.relation.name, row.relation.schema))
                seenColumns.put(row.relation, new HashSet<String>())
            }
            for (def column : row.columns) {
                if (seenColumns.get(row.relation).add(column.name)) {
                    def dataType = column.type ?: new DataType(DataType.StandardType.INTEGER);
                    def mergeCheck = ((InsertDataAction) action).columnsForUpdateCheck.contains(column.name)
                    snapshot.add(new Column(column.name, row.relation, dataType, !mergeCheck))

                    if (mergeCheck) {
                        snapshot.add(new PrimaryKey(null, row.relation, column.name))
                    }
                }
            }
        }
        return snapshot
    }
}
