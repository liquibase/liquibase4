package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.CompoundResult
import liquibase.actionlogic.RowBasedQueryResult
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Column
import liquibase.item.core.RelationReference
import liquibase.item.core.RowData
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.StringClauses
import liquibase.util.TestUtil
import spock.lang.Unroll

class SelectDataActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can select with complex table names"() {
        expect:
        testAction([
                relation_asTable: action.relation,
        ], action, conn, scope, {
            plan, results ->
                results = ((CompoundResult) results).flatResults
                assert results.size() == 1
                assert results[0] instanceof RowBasedQueryResult
                assert ((RowBasedQueryResult) results[0]).size() > 0
        })

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(SelectDataAction, [
                            relation: getItemReferences(Table, it.allSchemas, scope),
                            columns : [[new SelectDataAction.SelectedColumn(standardCaseItemName("test_col", Column, scope))]]
                    ])
            ], new ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "can select with complex column names"() {
        expect:
        testAction([
                columns_asTable: action.columns,
        ], action, conn, scope, {
            plan, results ->
                results = ((CompoundResult) results).flatResults
                assert results.size() == 1
                assert results[0] instanceof RowBasedQueryResult
                assert ((RowBasedQueryResult) results[0]).size() > 0
        })

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(SelectDataAction, [
                            relation: [new RelationReference(Table, standardCaseItemName("test_table", Table, scope))],
                            columns : CollectionUtil.toSingletonLists(getItemNames(Column, scope).collect({
                                return new SelectDataAction.SelectedColumn(it)
                            })),
                    ])
            ], new ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "can select with complex join table names"() {
        expect:

        testAction([
                joins_asTable: action.joins,
        ], action, conn, scope, {
            plan, results ->
                assert plan.toString().contains("JOIN")
                results = ((CompoundResult) results).flatResults
                assert results.size() == 1
                assert results[0] instanceof RowBasedQueryResult
                assert ((RowBasedQueryResult) results[0]).size() > 0
        })

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(SelectDataAction, [
                            relation: [new RelationReference(Table, standardCaseItemName("test_table", Table, scope))],
                            columns : [[new SelectDataAction.SelectedColumn(standardCaseItemName("test_col", Column, scope))]],
                            joins   : CollectionUtil.toSingletonLists(getItemReferences(Table, it.allSchemas, scope).collect({
                                return new SelectDataAction.JoinedRelation(it, "t", SelectDataAction.JoinType.inner).addOnClause("test_table.id=t.id")
                            })),
                    ])
            ], new ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "can select with createAllActionPermutations"() {
        expect:

        testAction([
                distinct_asTable : action.distinct,
                columns_asTable      : action.columns,
                order_asTable        : action.order,
                relation_asTable     : action.relation,
                relationAlias_asTable: action.relationAlias,
                joins_asTable        : action.joins,
                where_asTable        : action.where,
        ], action, conn, scope, {
            plan, results ->

                if (action.joins.size() > 0) {
                    assert plan.toString().contains("JOIN")
                }
                results = ((CompoundResult) results).flatResults
                assert results.size() == 1
                assert results[0] instanceof RowBasedQueryResult
                assert ((RowBasedQueryResult) results[0]).size() > 0
        })

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

        def joinRef1 = new RelationReference(Table, standardCaseItemName("join_table1", Table, scope))
        def joinRef2 = new RelationReference(Table, standardCaseItemName("join_table2", Table, scope))


        def tableAlias = standardCaseItemName("t1", Table, scope)
        def joinAlias = standardCaseItemName("j1", Table, scope)
        def join2Alias = standardCaseItemName("j2", Table, scope)

        return TestUtil.createAllPermutations(SelectDataAction,  //no aliases
                [
                        distinct: [true, false],
                        relation    : [tableRef],
                        columns     : [
                                [new SelectDataAction.SelectedColumn(column1Name)], //one column
                                [new SelectDataAction.SelectedColumn(column1Name), new SelectDataAction.SelectedColumn(column2Name)], //two columns
                                [new SelectDataAction.SelectedColumn(null, "*", null, true)], //all columns
                                [new SelectDataAction.SelectedColumn(null, null, "NULL_VAL", true)], //null computed value
                                [new SelectDataAction.SelectedColumn(null, null, "NO_NAME", false)], //null not-computed value
                                [new SelectDataAction.SelectedColumn(null, column1Name, "my_col")], //aliased column
                        ],
                        order       : [
                                [new SelectDataAction.OrderedByColumn(null, column1Name, SelectDataAction.OrderDirection.ASC)],
                                [new SelectDataAction.OrderedByColumn(null, column1Name, SelectDataAction.OrderDirection.DESC)],
                        ],
                        joins       : [
                                [new SelectDataAction.JoinedRelation(joinRef1, null, SelectDataAction.JoinType.inner).addOnClause("1=1")],
                                [new SelectDataAction.JoinedRelation(joinRef1, null, SelectDataAction.JoinType.leftOuter).addOnClause("1=1")],
                                [new SelectDataAction.JoinedRelation(joinRef1, null, SelectDataAction.JoinType.rightOuter).addOnClause("1=1")],
                                [new SelectDataAction.JoinedRelation(joinRef1, null, SelectDataAction.JoinType.inner)], //no ON clause
                                [new SelectDataAction.JoinedRelation(null, null, SelectDataAction.JoinType.inner).addOnClause("1=1")], //no join table name
                        ],
                        where       : [
                                new StringClauses().append("1=1")
                        ]
                ])
                .plus(TestUtil.createAllPermutations(SelectDataAction, //yes aliases
                [
                        relation             : [tableRef],
                        columns              : [
                                [new SelectDataAction.SelectedColumn(tableAlias, column1Name, null)], //qualified column
                                [new SelectDataAction.SelectedColumn(tableAlias, column1Name, "my_col")], //qualified and aliased column
                        ],
                        order                : [
                                [new SelectDataAction.OrderedByColumn(tableAlias, column1Name, SelectDataAction.OrderDirection.ASC)],
                                [new SelectDataAction.OrderedByColumn(tableAlias, column1Name, SelectDataAction.OrderDirection.DESC)],
                        ],
                        relationAlias: [tableAlias],
                        joins                : [
                                [new SelectDataAction.JoinedRelation(joinRef1, joinAlias, SelectDataAction.JoinType.inner).addOnClause("1=1")],
                                [new SelectDataAction.JoinedRelation(joinRef1, joinAlias, SelectDataAction.JoinType.leftOuter).addOnClause("1=1")],
                                [new SelectDataAction.JoinedRelation(joinRef1, joinAlias, SelectDataAction.JoinType.rightOuter).addOnClause("1=1")],
                                [
                                        new SelectDataAction.JoinedRelation(joinRef1, joinAlias, SelectDataAction.JoinType.leftOuter).addOnClause("1=1"),
                                        new SelectDataAction.JoinedRelation(joinRef2, join2Alias, SelectDataAction.JoinType.rightOuter).addOnClause("1=1"),
                                ],
                        ],
                        where                : [
                                new StringClauses().append("1=1")
                        ]
                ]
        ).findAll { it.relationAlias != null })
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)

        def dataType = new DataType(DataType.StandardType.INTEGER)
        def idColumnName = standardCaseItemName("id", Column, scope)

        def selectAction = (SelectDataAction) action
        snapshot.add(new Table(selectAction.relation.name, selectAction.relation.schema))
        RowData row1 = new RowData(selectAction.relation)
        def seenColumns = new HashSet<String>()
        for (SelectDataAction.SelectedColumn column : selectAction.columns) {
            def columnName = column.name
            if (column.virtual) {
                columnName = standardCaseItemName("col_x", Column.class, scope)
            }
            snapshot.add(new Column(columnName, selectAction.relation, dataType, true))
            row1.add(columnName, 12, dataType)
            seenColumns.add(columnName)
        }

        snapshot.add(new Column(idColumnName, selectAction.relation, dataType, true))
        row1.add(idColumnName, 111, dataType)
        seenColumns.add(idColumnName)

        snapshot.add(row1)

        for (def join : selectAction.joins) {
            snapshot.add(new Table(join.relation.name, join.relation.schema))
            RowData joinRow = new RowData(join.relation)

            snapshot.add(new Column(idColumnName, join.relation, dataType, true))
            joinRow.add(idColumnName, 111, dataType)

            snapshot.add(joinRow)
        }

        for (SelectDataAction.OrderedByColumn orderCol : action.order) {
            if (seenColumns.add(orderCol.name)) {
                snapshot.add(new Column(orderCol.name, selectAction.relation, dataType, true))
            }
        }


        return snapshot
    }

}
