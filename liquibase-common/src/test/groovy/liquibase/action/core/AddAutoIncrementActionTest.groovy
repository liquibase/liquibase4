package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.snapshot.Snapshot

import liquibase.item.core.Column
import liquibase.item.core.ColumnReference
import liquibase.item.core.PrimaryKey
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
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
                    TestUtil.createAllPermutationsWithoutNulls(AddAutoIncrementAction, [
                            column  : TestUtil.createAllPermutationsWithoutNulls(ColumnReference, [
                                    name     : getItemNames(Column, scope),
                                    container: getItemReferences(Table, it.getAllSchemas(), scope),
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
                infoObject_asTable : action.autoIncrementDetails != null,
                startWith_asTable  : action.autoIncrementDetails == null ? null : action.autoIncrementDetails.startWith,
                incrementBy_asTable: action.autoIncrementDetails == null ? null : action.autoIncrementDetails.incrementBy
        ], action, conn, scope, { plan, result ->
            if (action.autoIncrementDetails != null && action.autoIncrementDetails.incrementBy != null) {
                //need to check because checkStatus does not get incrementBy metadata
                assert plan.toString().contains(action.autoIncrementDetails.incrementBy.toString()): "IncrementBy value not used"
            }
            if (action.autoIncrementDetails != null && action.autoIncrementDetails.startWith != null) {
                //need to check because checkStatus does not get startWith metadata
                assert plan.toString().contains(action.autoIncrementDetails.startWith.toString()): "StartWith value not used"
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
    protected String getExpectedUnparsedFormat(String format) {
        switch (format) {
            case "xml":
                return """
<?xml version="1.1" encoding="utf-8"?>
<changeLog xmlns="http://www.liquibase.org/xml/ns/changelog" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.liquibase.org/xml/ns/changelog http://www.liquibase.org/xml/ns/changelog/changelog-4.0.xsd">
    <changeSet author="test" id="1">
        <addAutoIncrement type="INTEGER">
            <autoIncrementDetails incrementBy="4221" startWith="4221"/>
            <column name="object_name" tableName="table_name" schemaName="schema_name" catalogName="cat_name"/>
        </addAutoIncrement>
    </changeSet>
</changeLog>
"""

            case "yaml":
                return """
changeLog:
    changeSet:
        id: '1'
        author: test
        addAutoIncrement:
            column:
                catalogName: cat_name
                schemaName: schema_name
                tableName: table_name
                name: object_name
            type: INTEGER
            autoIncrementDetails:
                startWith: 4221
                incrementBy: 4221
"""

            case "json":
                return """
{
  "changeLog": {
    "changeSet": {
      "author": "test",
      "id": "1",
      "addAutoIncrement": {
        "autoIncrementDetails": {
          "incrementBy": 4221,
          "startWith": 4221
        },
        "column": {
          "name": "object_name",
          "tableName": "table_name",
          "schemaName": "schema_name",
          "catalogName": "cat_name"
        },
        "type": "INTEGER"
      }
    }
  }
}
"""
        }
        return "";
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        def tableName = standardCaseItemName("table_name", Table, scope)
        def columnName = standardCaseItemName("column_name", Table, scope)

        TestUtil.createAllPermutations(AddAutoIncrementAction, [
                column              : [null, new ColumnReference(columnName, null), new ColumnReference(tableName, columnName)],
                dataType            : [null, new DataType(DataType.StandardType.INTEGER)],
                autoIncrementDetails: CollectionUtil.addNull(TestUtil.createAllPermutations(Column.AutoIncrementDetails, [
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
