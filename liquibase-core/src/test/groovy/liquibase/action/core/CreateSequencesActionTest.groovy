package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Sequence
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class CreateSequencesActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can create from createAllActionPermutations"() {
        expect:
        testAction([
                name_asTable      : action.sequences*.name,
                schema_asTable    : action.sequences*.schema,
                startValue_asTable: action.sequences*.startValue,
                minValue_asTable  : action.sequences*.minValue,
                maxValue_asTable  : action.sequences*.maxValue,
                cycle_asTable     : action.sequences*.cycle,
                ordered_asTable   : action.sequences*.ordered,
                cacheSize_asTable : action.sequences*.cacheSize,
        ], action, conn, scope)

        where:
        [conn, scope, action] << ignoreIfEmpty("May not support sequences", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            if (!scope.database.supports(Sequence, scope)) {
                return []
            }

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new ValidActionFilter(scope))
        })
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return TestUtil.createAllPermutations(CreateSequencesAction, [
                sequences: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(Sequence, [
                        name      : [standardCaseItemName("seq_test", Sequence, scope)],
                        schema    : connectionSupplier.allSchemas,
                        startValue: [BigInteger.ONE, new BigInteger(131)],
                        minValue  : [BigInteger.ONE, new BigInteger(5123)],
                        maxValue  : [new BigInteger(10), new BigInteger(1262136)],
                        cycle     : [true, false],
                        ordered   : [true, false],
                        cacheSize : [new BigInteger(1262136)],
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        return new Snapshot(scope)
    }
}
