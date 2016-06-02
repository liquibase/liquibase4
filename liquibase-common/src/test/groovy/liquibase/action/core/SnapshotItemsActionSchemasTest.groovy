package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ObjectBasedQueryResult
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.core.Catalog
import liquibase.item.core.CatalogReference
import liquibase.item.core.Schema
import liquibase.item.core.SchemaReference
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class SnapshotItemsActionSchemasTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can find each schema"() {
        expect:
        testAction([
                schema_asTable: action.relatedTo
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundSchema = results.asObject(Schema)

            assert foundSchema.toReference().equals(action.relatedTo[0], true)
        })

        where:
        [conn, scope, action] << ignoreIfEmpty("May not support schemas", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            if (!scope.database.supports(Schema, scope)) {
                return [];
            }
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new ValidActionFilter(scope))
        })
    }

    @Unroll("#featureName: #action on #conn")
    def "can find all schemas by specifying null name"() {
        expect:
        testAction([:], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() >= 1;
            assert !results.asList(Schema).contains(null)
        })

        where:
        [conn, scope, action] << ignoreIfEmpty("May not support schemas", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            if (!scope.database.supports(Schema, scope)) {
                return [];
            }
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(SnapshotItemsAction, [
                            typeToSnapshot: [Schema],
                            relatedTo     : CollectionUtil.toSingletonSets([new SchemaReference(null)])
                    ])
            ], new ValidActionFilter(scope))
        })
    }

    @Unroll("#featureName: #action on #conn")
    def "can find all schemas by specifying null catalog name"() {
        expect:
        testAction([:], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() >= 1;
        })

        where:
        [conn, scope, action] << ignoreIfEmpty("May not support schemas and/or schemas", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            if (!scope.database.supports(Catalog, scope)) {
                return [];
            }
            if (!scope.database.supports(Schema, scope)) {
                return [];
            }
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(SnapshotItemsAction, [
                            typeToSnapshot: [Schema],
                            relatedTo     : CollectionUtil.toSingletonSets([new CatalogReference(null)])
                    ])
            ], new ValidActionFilter(scope))
        })
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return TestUtil.createAllPermutationsWithoutNulls(SnapshotItemsAction, [
                typeToSnapshot: [Schema],
                relatedTo     : CollectionUtil.toSingletonSets(connectionSupplier.getAllSchemas())
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)
        return snapshot
    }
}
