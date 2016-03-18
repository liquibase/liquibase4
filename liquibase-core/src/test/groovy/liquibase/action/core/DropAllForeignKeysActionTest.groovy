package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Column
import liquibase.item.core.ForeignKey
import liquibase.item.core.PrimaryKey
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class DropAllForeignKeysActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can execute everything from createAllActionPermutations"() {
        expect:
        testAction([
                table_asTable: action.table,
        ], action, conn, scope)

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

        return TestUtil.createAllPermutations(DropAllForeignKeysAction, [
                table: getItemReferences(Table, connectionSupplier.allSchemas, scope),
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def dataType = new DataType(DataType.StandardType.INTEGER)

        def snapshot = new Snapshot(scope)

        snapshot.add(new Table(action.table.name, action.table.schema))


        def baseColumn1 = new Column(standardCaseItemName("test_col1", Column, scope), action.table, dataType, true)
        def baseColumn2 = new Column(standardCaseItemName("test_col2", Column, scope), action.table, dataType, true)

        snapshot.add(baseColumn1)
        snapshot.add(baseColumn2)

        def refTable1 = new Table(standardCaseItemName("ref_table_1", Table, scope), action.table.schema)
        def refColumn1 = new Column(standardCaseItemName("id", Column, scope), refTable1.toReference(), dataType, false)

        def refTable2 = new Table(standardCaseItemName("ref_table_2", Table, scope), action.table.schema)
        def refColumn2 = new Column(standardCaseItemName("id", Column, scope), refTable2.toReference(), dataType, false)

        snapshot.add(refTable1)
        snapshot.add(refColumn1)
        snapshot.add(new PrimaryKey(null, refTable1.toReference(), refColumn1.name))

        snapshot.add(refTable2)
        snapshot.add(refColumn2)
        snapshot.add(new PrimaryKey(null, refTable2.toReference(), refColumn2.name))

        snapshot.add(new ForeignKey(standardCaseItemName("fk_1", ForeignKey, scope), action.table, refTable1.toReference(), [baseColumn1.name], [refColumn1.name]))
        snapshot.add(new ForeignKey(standardCaseItemName("fk_2", ForeignKey, scope), action.table, refTable2.toReference(), [baseColumn2.name], [refColumn2.name]))

        return snapshot
    }


}
