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
import liquibase.item.core.View
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class InsertDataFromQueryActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can run from createAllActionPermutations"() {
        expect:
        testAction([
                destination_asTable: action.destination,
                destinationColumns_asTable: action.destinationColumns,
                query_asTable      : action.query
        ], action, conn, scope, {
            plan, results ->
                assert ((Scope) scope).getSingleton(ActionExecutor).query(new SelectDataAction(action.destination, new SelectDataAction.SelectedColumn(null, "*", null, true)), scope).size() > 0
        })

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new AbstractActionTest.ValidActionFilter(scope))
        }
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return TestUtil.createAllPermutations(InsertDataFromQueryAction, [
                destination: TestUtil.createAllPermutations(RelationReference, [
                        name     : getItemNames(View, scope),
                        container: connectionSupplier.getAllSchemas()
                ]).each {
                    it.type = Table
                },
                destinationColumns: [
                        [standardCaseItemName("col1", Column, scope)],
                        [standardCaseItemName("col1", Column, scope), standardCaseItemName("col2", Column, scope)]
                ],
                query      : [new SelectDataAction()].plus(
                        connectionSupplier.allSchemas.collect {
                            return new SelectDataAction(new RelationReference(Table, standardCaseItemName("data_table", Table, scope), it))
                        }
                )
        ]).each {
            if (it.query && it.query.relation != null) {
                it.query = it.query.clone()
                if (it.destinationColumns == null || it.destinationColumns.size() == 0) {
                    it.query.addColumn(new SelectDataAction.SelectedColumn(null, "*", null, true))
                } else {
                    for (def col : it.destinationColumns) {
                        it.query.addColumn(new SelectDataAction.SelectedColumn(null, col, null, false))
                    }
                }
            }
        }
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)

        def column1Name = standardCaseItemName("col1", Column, scope)
        def column2Name = standardCaseItemName("col2", Column, scope)

        snapshot.add(new Table(((InsertDataFromQueryAction) action).destination.name, ((InsertDataFromQueryAction) action).destination.schema))
        snapshot.add(new Column(column1Name, ((InsertDataFromQueryAction) action).destination, new DataType(DataType.StandardType.INTEGER), true))
        snapshot.add(new Column(column2Name, ((InsertDataFromQueryAction) action).destination, new DataType(DataType.StandardType.INTEGER), true))

        def sourceTableRef = ((InsertDataFromQueryAction) action).query.relation
        snapshot.add(new Table(sourceTableRef.name, sourceTableRef.schema))
        snapshot.add(new Column(column1Name, sourceTableRef, new DataType(DataType.StandardType.INTEGER), true))
        snapshot.add(new Column(column2Name, sourceTableRef, new DataType(DataType.StandardType.INTEGER), true))

        snapshot.add(new RowData(sourceTableRef)
                .add(column1Name, 1, new DataType(DataType.StandardType.INTEGER))
                .add(column2Name, 100, new DataType(DataType.StandardType.INTEGER))
        )
        snapshot.add(new RowData(sourceTableRef)
                .add(column1Name, 2, new DataType(DataType.StandardType.INTEGER))
                .add(column2Name, 200, new DataType(DataType.StandardType.INTEGER))
        )
    }
}


