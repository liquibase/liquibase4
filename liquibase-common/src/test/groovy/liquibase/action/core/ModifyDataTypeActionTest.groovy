package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ActionExecutor
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Column
import liquibase.item.core.ColumnReference
import liquibase.item.core.RelationReference
import liquibase.item.core.RowData
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Specification
import spock.lang.Unroll

class ModifyDataTypeActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can run from createAllActionPermutations"() {
        testAction([
                column_asTable     : action.column,
                newDataType_asTable: action.newDataType,
        ], action, conn, scope)

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
        return TestUtil.createAllPermutations(ModifyDataTypeAction, [
                column     : TestUtil.createAllPermutations(ColumnReference, [
                        name     : getItemNames(Column, scope),
                        container: getItemReferences(Table, connectionSupplier.allSchemas, scope)

                ]),
                newDataType: [new DataType(DataType.StandardType.BIGINT)]
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)

        ModifyDataTypeAction modifyAction = (ModifyDataTypeAction) action

        snapshot.add(new Table(modifyAction.column.relation.name, modifyAction.column.relation.schema))

        snapshot.add(new Column(modifyAction.column.name, modifyAction.column.relation, new DataType(DataType.StandardType.INTEGER), true))
        snapshot.add(new Column(standardCaseItemName("other_col", Column, scope), modifyAction.column.relation, new DataType(DataType.StandardType.INTEGER), true))

        return snapshot;
    }

}
