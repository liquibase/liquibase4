package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Column
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.StringClauses
import liquibase.util.TestUtil
import spock.lang.Unroll

class UpdateDataActionTest extends AbstractActionTest {

//    relation;
//    columns
//    where

    @Unroll("#featureName: #action on #conn")
    def "can update with complex names"() {
        expect:
        testAction([
                relation_asTable: action.relation,
                columns_asTable : action.columns,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(UpdateDataAction, [
                            relation: getItemReferences(Table, it.allSchemas, scope),
                            columns : CollectionUtil.toSingletonLists(getItemNames(Column, scope).collect({
                                return new UpdateDataAction.UpdatedColumn(it, 42)
                            }))
                    ])
            ], new ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "can update from createAllActionPermutations"() {
        expect:
        testAction([
                relation_asTable: action.relation,
                columns_asTable : action.columns,
                where_asTable   : action.where,
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
        def tableName = standardCaseItemName("test_table", Table, scope)
        def column1Name = standardCaseItemName("column1", Column, scope)
        def column2Name = standardCaseItemName("column2", Column, scope)


        return TestUtil.createAllPermutations(UpdateDataAction, [
                relation: connectionSupplier.allSchemas.collect({ return new RelationReference(Table, tableName, it) }),
                columns : [
                        [new UpdateDataAction.UpdatedColumn(column1Name, 1)],
                        [new UpdateDataAction.UpdatedColumn(column1Name, 1), new UpdateDataAction.UpdatedColumn(column2Name, 2)],
                        [new UpdateDataAction.UpdatedColumn(column1Name, null)],
                        [new UpdateDataAction.UpdatedColumn(null, 1)],
                        [new UpdateDataAction.UpdatedColumn(null, null)],
                ],
                where   : [
                        new StringClauses().append("1=1")
                ]

        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)

        snapshot.add(new Table(action.relation.name, action.relation.schema))
        for (UpdateDataAction.UpdatedColumn column : action.columns) {
            snapshot.add(new Column(column.name, action.relation, new DataType(DataType.StandardType.INTEGER), true))
        }
        return snapshot
    }
}
