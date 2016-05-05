package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ActionExecutor
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

class MergeColumnsActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can run from createAllActionPermutations"() {
        testAction([
                relation_asTable       : action.relation,
                column1Name_asTable    : action.column1Name,
                joinString_asTable     : action.joinString,
                column2Name_asTable    : action.column2Name,
                finalColumnName_asTable: action.finalColumnName,
                finalColumnName_asTable: action.finalColumnName,
        ], action, conn, scope, {
            plan, results ->
                def rs = ((Scope) scope).getSingleton(ActionExecutor).query(
                        new SelectDataAction(action.relation, new SelectDataAction.SelectedColumn(null, "*", null, true))
                                .addOrder(new SelectDataAction.OrderedByColumn(action.finalColumnName))
                        , scope).rows

                assert rs.size() == 2

                def joinString = action.joinString
                if (joinString == null) {
                    joinString = " ";
                }

                assert rs[0].toString().contains("=Front 1"+ joinString +"Back 1]")
                assert rs[1].toString().contains("=Front 2"+ joinString +"Back 2]")
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
        return TestUtil.createAllPermutations(MergeColumnsAction, [
                relation       : standardCaseReferences(RelationReference, "test_table", connectionSupplier.allSchemas, scope),
                column1Name    : [standardCaseItemName("col_1", Column, scope), "CRAZY!@#\\\$%^&*()_+{}[]'\"Col1"],
                joinString     : ["->", "'"],
                column2Name    : [standardCaseItemName("col_2", Column, scope), "CRAZY!@#\\\$%^&*()_+{}[]'\"Col2"],
                finalColumnName: [standardCaseItemName("final_col", Column, scope), "CRAZY!@#\\\$%^&*()_+{}[]'\"ColFinal"],
                finalColumnType: [new DataType(DataType.StandardType.VARCHAR, 40)],
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)

        MergeColumnsAction mergeAction = (MergeColumnsAction) action

        snapshot.add(new Table(mergeAction.relation.name, mergeAction.relation.schema))

        def dataType = new DataType(DataType.StandardType.VARCHAR, 20)
        snapshot.add(new Column(mergeAction.column1Name, mergeAction.relation, dataType, true))
        snapshot.add(new Column(mergeAction.column2Name, mergeAction.relation, dataType, true))

        snapshot.add(new RowData(mergeAction.relation)
                .add(mergeAction.column1Name, "Front 1", dataType)
                .add(mergeAction.column2Name, "Back 1", dataType))
        snapshot.add(new RowData(mergeAction.relation)
                .add(mergeAction.column1Name, "Front 2", dataType)
                .add(mergeAction.column2Name, "Back 2", dataType)
        )
        return snapshot;
    }
}
