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

class AlterSequenceActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can alter from createAllActionPermutations"() {
        expect:
        testAction([
                sequence_asTable   : action.sequence,
                restartWith_asTable: action.restartWith,
                incrementBy_asTable: action.incrementBy,
                minValue_asTable   : action.minValue,
                maxValue_asTable   : action.maxValue,
                ordered_asTable    : action.ordered,
                cycle_asTable      : action.cycle,
                cacheSize_asTable  : action.cacheSize,
        ], action, conn, scope)

        where:
        [conn, scope, action] << ignoreIfEmpty("database does not support sequences", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new AbstractActionTest.ValidActionFilter(scope))
        })
    }

    @Unroll("#featureName: #action on #conn")
    def "can alter from with complex names"() {
        expect:
        testAction([
                sequence_asTable: action.sequence,
        ], action, conn, scope)

        where:
        [conn, scope, action] << ignoreIfEmpty("database does not support sequences", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(AlterSequenceAction, [
                            sequence: getItemReferences(Sequence, it.allSchemas, scope),
                            maxValue: [10],
                    ])
            ], new AbstractActionTest.ValidActionFilter(scope))
        })
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return TestUtil.createAllPermutations(AlterSequenceAction, [
                sequence   : TestUtil.createAllPermutations(SequenceReference, [
                        name     : [standardCaseItemName("test_seq", Sequence, scope)],
                        container: connectionSupplier.allSchemas
                ]),
                restartWith: [4],
                incrementBy: [2],
                minValue   : [0, 2],
                maxValue   : [10, -1],
                ordered    : [true, false],
                cycle      : [true, false],
                cacheSize  : [-1, 8],
        ])

    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)

        def sequence = new Sequence(((AlterSequenceAction) action).sequence.name, ((AlterSequenceAction) action).sequence.container)
        sequence.startValue = new BigInteger(4)
        snapshot.add(sequence)
        return snapshot;
    }
}
