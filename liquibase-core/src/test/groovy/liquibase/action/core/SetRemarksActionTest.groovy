package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Column
import liquibase.item.core.Table
import liquibase.item.core.View
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.StringClauses
import liquibase.util.TestUtil
import spock.lang.Unroll

class SetRemarksActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can set remarks from createAllActionPermutations"() {
        testAction([
                object_asTable      : action.object,
                objectType_asTable    : action.object.type.simpleName,
                remarks_asTable: action.remarks,
        ], action, conn, scope)

        where:
        [conn, scope, action] << ignoreIfEmpty("May not support remarks", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new ValidActionFilter(scope))
        })
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return TestUtil.createAllPermutations(SetRemarksAction, [
                object : getItemReferences(Column.class, getItemReferences(Table.class, connectionSupplier.getAllSchemas(), scope), scope)
                        .plus(getItemReferences(Table, connectionSupplier.allSchemas, scope)
                        .plus(getItemReferences(View, connectionSupplier.allSchemas, scope))),
                remarks: ["Simple remarks", "crazy!@#\\\$%^&*()_+{}[]'\""],
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)
        def object = ((SetRemarksAction) action).object

        if (object.instanceOf(Column)) {
            snapshot.add(new Table(object.container.name, object.container.container))
            snapshot.add(new Column(object.name, object.container, new DataType(DataType.StandardType.INTEGER), true))
        } else if (object.instanceOf(Table)) {
            snapshot.add(new Table(object.name, object.container))
            snapshot.add(new Column(standardCaseItemName("col_name", Column, scope), object, new DataType(DataType.StandardType.INTEGER), true))
        } else if (object.instanceOf(View)) {
            def table = new Table(standardCaseItemName("test_table", Table, scope), object.container)
            snapshot.add(table)
            snapshot.add(new Column(standardCaseItemName("col_name", Column, scope), table.toReference(), new DataType(DataType.StandardType.INTEGER), true))
            snapshot.add(new View(object.name, object.container, new StringClauses().append("select * from "+table.toReference())))
        }

        return snapshot;
    }
}
