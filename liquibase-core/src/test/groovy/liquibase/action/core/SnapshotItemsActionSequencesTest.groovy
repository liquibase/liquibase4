package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.action.ActionStatus
import liquibase.actionlogic.ObjectBasedQueryResult
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.database.Database
import liquibase.item.TestItemSupplier
import liquibase.item.core.*
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class SnapshotItemsActionSequencesTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can find complex sequence names"() {
        expect:
        testAction([
                sequence_asTable: action.relatedTo
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundSeq = results.asObject(Sequence)

            assert foundSeq.toReference().equals(action.relatedTo[0], true)
        })

        where:
        [conn, scope, action] << okIfEmpty("May not support sequences", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            if (!scope.database.supports(Sequence, scope)) {
                return [];
            }
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(SnapshotItemsAction, [
                            typeToSnapshot: [Sequence],
                            relatedTo     : CollectionUtil.toSingletonSets(getItemReferences(Sequence, it.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope))
                    ])
            ], new ValidActionFilter(scope))
        })
    }

    @Unroll("#featureName: #action on #conn")
    def "can find all sequences using a null name "() {
        expect:
        testAction([
                sequence_asTable: action.relatedTo
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0; //there will be multiple

            results.asList(Sequence).each {
                assert it.toReference().container.equals(action.relatedTo[0].container, true)
            }
        })

        where:
        [conn, scope, action] << okIfEmpty("May not support sequences", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            if (!scope.database.supports(Sequence, scope)) {
                return [];
            }
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(SnapshotItemsAction, [
                            typeToSnapshot: [Sequence],
                            relatedTo     : CollectionUtil.toSingletonSets(it.getAllSchemas().collect({
                                return new SequenceReference(null, it)
                            }))
                    ])
            ], new ValidActionFilter(scope))
        })
    }

    @Unroll("#featureName: #action on #conn")
    def "can find all sequences using schema reference"() {
        expect:
        testAction([
                sequence_asTable: action.relatedTo
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0; //there will be multiple

            results.asList(Sequence).each {
                assert it.toReference().container.equals(action.relatedTo[0], true)
            }
        })

        where:
        [conn, scope, action] << okIfEmpty("May not support sequences", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            if (!scope.database.supports(Sequence, scope)) {
                return [];
            }
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(SnapshotItemsAction, [
                            typeToSnapshot: [Sequence],
                            relatedTo     : CollectionUtil.toSingletonSets(it.getAllSchemas())
                    ])
            ], new ValidActionFilter(scope))
        })
    }

    @Unroll("#featureName: #createAction.sequences on #conn")
    def "can find sequences with various options"() {
        when:
        Sequence sequenceDefinition = createAction.sequences[0]
        def snapshot = new Snapshot(scope)
        snapshot.add(sequenceDefinition)

        then:
        testAction([
                schema_asTable    : sequenceDefinition.schema,
                startValue_asTable: sequenceDefinition.startValue,
                minValue_asTable  : sequenceDefinition.minValue,
                maxValue_asTable  : sequenceDefinition.maxValue,
                cycle_asTable     : sequenceDefinition.cycle,
                ordered_asTable   : sequenceDefinition.ordered,
                cacheSize_asTable : sequenceDefinition.cacheSize
        ], snapshot, new SnapshotItemsAction(sequenceDefinition.toReference()), conn, scope, {
            plan, results ->
                assert results instanceof ObjectBasedQueryResult
                assert results.size() == 1;

                def foundSeq = results.asObject(Sequence)

                assert foundSeq.toReference().equals(sequenceDefinition.toReference(), true)

                for (String field : ["cacheSize", "minValue", "maxValue", "cycle", "ordered"]) { //cannot snapshot startValue
                    if (sequenceDefinition.get(field, Object) != null) {
                        assert sequenceDefinition.get(field, Object) == foundSeq.get(field, Object)
                    }
                }

        })

        where:
        [conn, scope, createAction] << okIfEmpty("May not support sequences", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            if (!scope.database.supports(Sequence, scope)) {
                return [];
            }
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    new CreateSequencesActionTest().createAllActionPermutations(it, scope)
            ], new ValidActionFilter(scope))
        })
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return null
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)
        for (
                def ref : getItemReferences(Sequence, connectionSupplier.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope)) {
            snapshot.add(new Sequence(ref.name, ref.container))
        }
        return snapshot
    }
}
