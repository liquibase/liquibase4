package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.snapshot.Snapshot
import liquibase.item.ItemNameStrategy
import liquibase.item.core.Column
import liquibase.item.core.ColumnReference
import liquibase.item.core.PrimaryKey
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.util.CollectionUtil
import spock.lang.Unroll

class AddAutoIncrementActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action")
    def "Can apply standard settings to complex names"() {
        expect:
        testAction([
                column_asTable: action.column,
        ], action, conn, scope)

        where:
        [conn, scope, action] << connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllPermutationsWithoutNulls(AddAutoIncrementAction, [
                            column  : createAllPermutationsWithoutNulls(ColumnReference, [
                                    name     : getItemNames(Column, ItemNameStrategy.COMPLEX_NAMES, scope),
                                    container: getItemReferences(Table, it.getAllSchemas(), ItemNameStrategy.COMPLEX_NAMES, scope),
                            ]),
                            dataType: [new DataType(DataType.StandardType.INTEGER)]
                    ])
            ])
        }

    }

    @Unroll("#featureName: #action.describe() on #conn")
    def "Valid parameter permutations work"() {
        expect:
        testAction([
                column_asTable     : action.column.toString(),
                dataType_asTable   : action.dataType,
                infoObject_asTable : action.autoIncrementInformation != null,
                startWith_asTable  : action.autoIncrementInformation == null ? null : action.autoIncrementInformation.startWith,
                incrementBy_asTable: action.autoIncrementInformation == null ? null : action.autoIncrementInformation.incrementBy
        ], action, conn, scope, { plan, result ->
            if (action.autoIncrementInformation != null && action.autoIncrementInformation.incrementBy != null) { //need to check because checkStatus does not get incrementBy metadata
                assert plan.toString().contains(action.autoIncrementInformation.incrementBy.toString()): "IncrementBy value not used"
            }
            if (action.autoIncrementInformation != null && action.autoIncrementInformation.startWith != null) { //need to check because checkStatus does not get startWith metadata
                assert plan.toString().contains(action.autoIncrementInformation.startWith.toString()): "StartWith value not used"
            }

        })

        where:
        [conn, scope, action] << connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope),
            ], new ValidActionFilter(scope))
        }
    }

    @Override
    def createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        def tableName = standardCaseItemName("table_name", Table, scope.database)
        def columnName = standardCaseItemName("column_name", Table, scope.database)

        createAllPermutations(AddAutoIncrementAction, [
                column                  : [null, new ColumnReference(columnName, null), new ColumnReference(tableName, columnName)],
                dataType                : [null, new DataType(DataType.StandardType.INTEGER)],
                autoIncrementInformation: CollectionUtil.addNull(createAllPermutations(Column.AutoIncrementInformation, [
                        startWith  : [null, 1, 2, 10],
                        incrementBy: [null, 1, 5, 20]
                ]))
        ])
    }

    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        ColumnReference columnName = ((AddAutoIncrementAction) action).column
        Snapshot snapshot = new Snapshot(scope)
        snapshot.add(new Table(columnName.relation.name, columnName.relation.container))
        snapshot.add(new Column("id", columnName.relation, DataType.parse("int"), false))
        snapshot.add(new Column(columnName.name, columnName.relation, DataType.parse("int"), false))

        if (((TestDetails) getTestDetails(scope)).createPrimaryKeyBeforeAutoIncrement()) {
            snapshot.add(new PrimaryKey(null, columnName.container, columnName.name))
        }

        return snapshot
    }

    public static class TestDetails extends AbstractActionTest.TestDetails {
        public boolean createPrimaryKeyBeforeAutoIncrement() {
            return false;
        }
    }
}
