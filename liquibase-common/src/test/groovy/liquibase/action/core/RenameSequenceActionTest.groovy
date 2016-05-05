package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.RelationReference
import liquibase.item.core.SchemaReference
import liquibase.item.core.SequenceReference
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class RenameSequenceActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can rename from createAllActionPermutations"() {
        testAction([
                oldName_asTable: action.oldName,
                newName_asTable: action.newName,
        ], action, conn, scope)

        where:
        [conn, scope, action] << ignoreIfEmpty("may not support sequences", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
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
        return TestUtil.createAllPermutations(RenameSequenceAction, [
                oldName: getItemReferences(liquibase.item.core.Sequence, connectionSupplier.getAllSchemas(), scope),
                newName: getItemReferences(liquibase.item.core.Sequence, connectionSupplier.getAllSchemas(), scope),
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)

        SequenceReference oldName = action.oldName

        snapshot.add(new liquibase.item.core.Sequence(oldName.name, oldName.schema))

        return snapshot;
    }
}
