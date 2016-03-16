package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ActionExecutor
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.TestItemSupplier
import liquibase.item.core.Column
import liquibase.item.core.ColumnReference
import liquibase.item.core.ForeignKey
import liquibase.item.core.PrimaryKey
import liquibase.item.core.RelationReference
import liquibase.item.core.RowData
import liquibase.item.core.Table
import liquibase.item.core.View
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Specification
import spock.lang.Unroll

class AddLookupTableActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can run from createAllActionPermutations"() {
        expect:
        testAction([
                existingColumn_asTable   : action.existingColumn,
                newColumn_asTable        : action.newColumn,
                newColumnDataType_asTable: action.newColumnDataType,
                foreignKeyName_asTable   : action.foreignKeyName,
                primaryKeyName_asTable   : action.primaryKeyName,
        ], action, conn, scope, {
            plan, results ->
                assert ((Scope) scope).getSingleton(ActionExecutor).query(new SelectDataAction(action.newColumn.relation, new SelectDataAction.SelectedColumn(null, "*", null, true)), scope).size() > 0
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
        return TestUtil.createAllPermutations(AddLookupTableAction, [
                existingColumn   : connectionSupplier.allSchemas.collect {
                    return new ColumnReference(standardCaseItemName("col1", Column, scope), new RelationReference(Table, standardCaseItemName("existing_table", Table, scope), it))
                },
                newColumn        : connectionSupplier.allSchemas.collect {
                    return new ColumnReference(standardCaseItemName("id", Column, scope), new RelationReference(Table, standardCaseItemName("new_table", Table, scope), it))
                },
                newColumnDataType: [new DataType(DataType.StandardType.INTEGER)],
                foreignKeyName   : [standardCaseItemName("fk_name", ForeignKey, scope)],
                primaryKeyName   : [standardCaseItemName("pk_name", ForeignKey, scope)],
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def otherColumnName = standardCaseItemName("other_col", Column, scope)

        Snapshot snapshot = new Snapshot(scope)

        snapshot.add(new Table(((AddLookupTableAction) action).existingColumn.relation.name, ((AddLookupTableAction) action).existingColumn.relation.schema))
        snapshot.add(new Column(((AddLookupTableAction) action).existingColumn.name, ((AddLookupTableAction) action).existingColumn.relation, ((AddLookupTableAction) action).newColumnDataType ?: new DataType(DataType.StandardType.INTEGER), true))
        snapshot.add(new Column(otherColumnName, ((AddLookupTableAction) action).existingColumn.relation, ((AddLookupTableAction) action).newColumnDataType ?: new DataType(DataType.StandardType.INTEGER), true))


        snapshot.add(new RowData(((AddLookupTableAction) action).existingColumn.relation)
                .add(((AddLookupTableAction) action).existingColumn.name, 1, new DataType(DataType.StandardType.INTEGER))
                .add(otherColumnName, 100, new DataType(DataType.StandardType.INTEGER))
        )
        snapshot.add(new RowData(((AddLookupTableAction) action).existingColumn.relation)
                .add(((AddLookupTableAction) action).existingColumn.name, 2, new DataType(DataType.StandardType.INTEGER))
                .add(otherColumnName, 200, new DataType(DataType.StandardType.INTEGER))
        )
        snapshot.add(new RowData(((AddLookupTableAction) action).existingColumn.relation) //duplicate row to check distinct logic
                .add(((AddLookupTableAction) action).existingColumn.name, 2, new DataType(DataType.StandardType.INTEGER))
                .add(otherColumnName, 200, new DataType(DataType.StandardType.INTEGER))
        )
    }
}
