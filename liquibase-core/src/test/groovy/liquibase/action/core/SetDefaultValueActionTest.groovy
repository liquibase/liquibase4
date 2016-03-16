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
import liquibase.util.TestUtil
import spock.lang.Unroll

class SetDefaultValueActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can drop default values on complex names"() {
        expect:
        testAction([
                column_asTable  : action.column,
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
                    TestUtil.createAllPermutationsWithoutNulls(SetDefaultValueAction, [
                            column        : TestUtil.createAllPermutations(ColumnReference, [
                                    name     : getItemNames(Column, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                                    container: getItemReferences(Table, it.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope)
                            ]),
                            columnDataType: [DataType.parse("int")],
                            defaultValue  : [null],
                    ])
            ], new ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "can set default values on complex names"() {
        expect:
        testAction([
                column_asTable  : action.column,
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
                    TestUtil.createAllPermutationsWithoutNulls(SetDefaultValueAction, [
                            column        : TestUtil.createAllPermutations(ColumnReference, [
                                    name     : getItemNames(Column, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                                    container: getItemReferences(Table, it.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope)
                            ]),
                            columnDataType: [DataType.parse("int")],
                            defaultValue  : [50],
                    ])
            ], new ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "check createAllActionPermutations"() {
        expect:
        testAction([
                column_asTable          : action.column,
                dataType_asTable        : action.columnDataType,
                defaultValue_asTable    : action.defaultValue,
                defaultValueType_asTable: action.defaultValue == null ? null : action.defaultValue.class.name,
        ], action, conn, scope, {
            plan, results ->

        })

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope),
            ], new ValidActionFilter(scope))
        }
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return CollectionUtil.permutationsWithoutNulls([
                TestUtil.createAllPermutations(SetDefaultValueAction, [
                        column: TestUtil.createAllPermutations(ColumnReference, [
                                name     : [standardCaseItemName("test_column", Column, scope)],
                                container: standardCaseReferences(RelationReference, "test_table", connectionSupplier.allSchemas, scope),
                        ]),
                ]),

                [ //typeAndValues
                  [new DataType(DataType.StandardType.INTEGER), null],
                  [new DataType(DataType.StandardType.INTEGER), 10],
                  [new DataType(DataType.StandardType.INTEGER), "10"],
                  [new DataType(DataType.StandardType.VARCHAR, 50), null],
                  [new DataType(DataType.StandardType.VARCHAR, 50), "test value"],
                ]
        ]).collect({
            def action = it[0].clone()
            def typeAndValue = it[1]
            if (typeAndValue != null) {
                action.columnDataType = typeAndValue[0]
                action.defaultValue = typeAndValue[1]
            }
            return action;
        })
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope);

        def tableRef = ((SetDefaultValueAction) action).column.relation
        snapshot.add(new Table(tableRef.name, tableRef.schema))

        def testColumn = new Column((((SetDefaultValueAction) action).column).name, tableRef, ((SetDefaultValueAction) action).columnDataType, false)
        if (((SetDefaultValueAction) action).defaultValue == null) { //set something to drop

            def standardType = ((SetDefaultValueAction) action).columnDataType.standardType
            if (standardType.valueType.equals(String)) {
                testColumn.defaultValue = "original default value"
            } else {
                testColumn.defaultValue = 42
            }
        }
        snapshot.add(testColumn);

        snapshot.add(new Column(standardCaseItemName("other_col", Column, scope), tableRef, DataType.parse("int"), true));

        return snapshot
    }
}
