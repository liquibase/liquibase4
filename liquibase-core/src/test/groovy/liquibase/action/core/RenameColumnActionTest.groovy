package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.exception.UnexpectedLiquibaseException
import liquibase.item.DatabaseObjectReference
import liquibase.item.ItemReference
import liquibase.item.TestItemSupplier
import liquibase.item.core.Column
import liquibase.item.core.RelationReference
import liquibase.item.core.SchemaReference
import liquibase.item.core.Table
import liquibase.item.core.View
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.StringClauses
import liquibase.util.TestUtil
import spock.lang.Unroll

class RenameColumnActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can rename from createAllActionPermutations"() {
        testAction([
                relation_asTable: action.relation,
                oldName_asTable: action.oldName,
                newName_asTable: action.newName,
                columnDefinition_asTable: action.columnDefinition
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
        return TestUtil.createAllPermutations(RenameColumnAction, [
                oldName : getItemNames(Column, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                newName : getItemNames(Column, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                relation: getItemReferences(Table, connectionSupplier.allSchemas, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                columnDefinition: [new StringClauses().append("INT NOT NULL")],
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)

        RelationReference relation = action.relation

        snapshot.add(new Table(relation.name, relation.schema))
        snapshot.add(new Column(action.oldName, relation, new DataType(DataType.StandardType.INTEGER), true))

        return snapshot;
    }
}
