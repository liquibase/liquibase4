package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.TestItemSupplier
import liquibase.item.core.Column
import liquibase.item.core.ColumnReference
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.StringClauses
import liquibase.util.TestUtil
import spock.lang.Unroll

class DropDefaultValueActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can drop default values on complex names"() {
        expect:
        testAction([
                column_asTable       : action.column,
                dataType_asTable: action.columnDataType
        ], action, conn, scope, {
            plan, results ->

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
        return TestUtil.createAllPermutations(DropDefaultValueAction, [
                column        : TestUtil.createAllPermutations(ColumnReference, [
                        name     : getItemNames(Column, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                        container: getItemReferences(Table, connectionSupplier.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope)
                ]),
                columnDataType: [DataType.parse("int")]
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope);

        def tableRef = ((DropDefaultValueAction) action).column.relation
        snapshot.add(new Table(tableRef.name, tableRef.schema))
        snapshot.add(new Column((((DropDefaultValueAction) action).column).name, tableRef, DataType.parse("int"), false).set("defaultValue", 42));
        snapshot.add(new Column(standardCaseItemName("other_col", Column, scope), tableRef, DataType.parse("int"), true));

        return snapshot
    }
}
