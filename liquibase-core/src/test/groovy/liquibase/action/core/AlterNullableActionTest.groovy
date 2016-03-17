package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Column
import liquibase.item.core.RowData
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class AlterNullableActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can run from createAllActionPermutations"() {
        expect:
        testAction([
                nullable_asTable               : action.nullable,
                column_asTable               : action.column,
                constraintName_asTable       : action.constraintName,
                columnDataType_asTable       : action.columnDataType,
                valueForExistingNulls_asTable: action.valueForExistingNulls,
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
        return TestUtil.createAllPermutations(AlterNullableAction, [
                nullable             : [true, false],
                column               : getItemReferences(Column.class, getItemReferences(Table, connectionSupplier.allSchemas, scope), scope),
                constraintName       : ["nn_const_lower", "NN_CONST_UPPER", "crazy!@#\\\$%^&*()_+{}[]'\"_const"],
                columnDataType       : [new DataType(DataType.StandardType.INTEGER)],
                valueForExistingNulls: [3],
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def otherColumnName = standardCaseItemName("other_col", Column, scope)
        def columnRef = ((AlterNullableAction) action).column

        Snapshot snapshot = new Snapshot(scope)

        snapshot.add(new Table(columnRef.relation.name, columnRef.relation.schema))
        snapshot.add(new Column(columnRef.name, columnRef.relation, ((AlterNullableAction) action).columnDataType ?: new DataType(DataType.StandardType.INTEGER), true))
        snapshot.add(new Column(otherColumnName, columnRef.relation, ((AlterNullableAction) action).columnDataType ?: new DataType(DataType.StandardType.INTEGER), true))


        if (((AlterNullableAction) action).valueForExistingNulls != null) {
            snapshot.add(new RowData(columnRef.relation)
                    .add(columnRef.name, 1, new DataType(DataType.StandardType.INTEGER))
                    .add(otherColumnName, 100, new DataType(DataType.StandardType.INTEGER))
            )
            snapshot.add(new RowData(columnRef.relation)
                    .add(columnRef.name, 2, new DataType(DataType.StandardType.INTEGER))
                    .add(otherColumnName, 200, new DataType(DataType.StandardType.INTEGER))
            )
        }

        return snapshot
    }

}
