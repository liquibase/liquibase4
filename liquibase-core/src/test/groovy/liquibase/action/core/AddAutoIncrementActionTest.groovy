package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ActionExecutor
import liquibase.database.ConnectionSupplier
import liquibase.database.core.UnsupportedDatabaseSupplier
import liquibase.snapshot.Snapshot
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.ObjectReference
import liquibase.structure.core.Column
import liquibase.structure.datatype.DataType
import liquibase.structure.core.PrimaryKey
import liquibase.structure.core.Table
import liquibase.util.CollectionUtil
import liquibase.util.StringUtils
import spock.lang.Unroll

class AddAutoIncrementActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action")
    def "Can apply standard settings to complex names"() {
        expect:
        testAction([
                columnName_asTable: action.column,
        ], action, conn, scope)

        where:
        [conn, scope, action] << connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllPermutationsWithoutNulls(AddAutoIncrementAction, [
                            column  : createAllPermutationsWithoutNulls(Column.ColumnReference, [
                                    name     : getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope).each({ it.name }).unique(),
                                    container: getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope),
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
        def tableName = standardCaseObjectName("table_name", Table, scope.database)
        def columnName = standardCaseObjectName("column_name", Table, scope.database)

        createAllPermutations(AddAutoIncrementAction, [
                column                  : [null, new Column.ColumnReference(null, columnName), new Column.ColumnReference(tableName, columnName)],
                dataType                : [null, new DataType(DataType.StandardType.INTEGER)],
                autoIncrementInformation: CollectionUtil.addNull(createAllPermutations(Column.AutoIncrementInformation, [
                        startWith  : [null, 1, 2, 10],
                        incrementBy: [null, 1, 5, 20]
                ]))
        ])
    }

    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Column.ColumnReference columnName = ((AddAutoIncrementAction) action).column
        Snapshot snapshot = new Snapshot(scope)
        snapshot.add(new Table(columnName.relation))
        snapshot.add(new Column(columnName.relation, "id", "int"))
        snapshot.add(new Column(columnName.relation, columnName.name, "int"))

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
