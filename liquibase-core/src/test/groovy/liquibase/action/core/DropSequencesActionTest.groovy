package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Sequence
import liquibase.item.core.SequenceReference
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class DropSequencesActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can drop from createAllActionPermutations"() {
        expect:
        testAction([
                sequence_asTable: action.sequences,
        ], action, conn, scope)

        where:
        [conn, scope, action] << okIfEmpty("Sequences may not be supported", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            if (!scope.database.supports(Sequence, scope)) {
                return []
            }

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new AbstractActionTest.ValidActionFilter(scope))
        })
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return TestUtil.createAllPermutations(DropSequencesAction, [
                sequences: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(SequenceReference, [
                        name     : getItemNames(Sequence, scope),
                        container: connectionSupplier.getAllSchemas()
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def dropAction = (DropSequencesAction) action

        def snapshot = new Snapshot(scope)
        for (def sequence : dropAction.sequences) {
            snapshot.add(new Sequence(sequence.name, sequence.schema))
        }

        return snapshot
    }
}

