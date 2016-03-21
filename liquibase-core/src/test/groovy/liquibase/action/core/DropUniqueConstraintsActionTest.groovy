package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Column
import liquibase.item.core.Table
import liquibase.item.core.UniqueConstraint
import liquibase.item.core.UniqueConstraintReference
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class DropUniqueConstraintsActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can drop from createAllActionPermutations"() {
        expect:
        testAction([
                name_asTable    : action.constraints*.name,
                relation_asTable: action.constraints*.relation,
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
        return TestUtil.createAllPermutations(DropUniqueConstraintsAction, [
                constraints: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(UniqueConstraintReference, [
                        name       : getItemNames(UniqueConstraint, scope),
                        container  : getItemReferences(Table, connectionSupplier.getAllSchemas(), scope),
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        DropUniqueConstraintsAction dropAction = (DropUniqueConstraintsAction) action
        def colName = standardCaseItemName("test_col", Column, scope)

        def snapshot = new Snapshot(scope)

        for (def uq : dropAction.constraints) {
            def table = new Table((uq.relation == null ? standardCaseItemName("test_table", Table, scope) : uq.relation.name), (uq.relation == null ? null : uq.relation.schema))
            snapshot.add(table)
            snapshot.add(new Column(colName, table.toReference(), new DataType(DataType.StandardType.INTEGER), false))

            def uqObject = new UniqueConstraint(uq.name, table.toReference(), colName)
            snapshot.add(uqObject)
        }

        return snapshot
    }

}
