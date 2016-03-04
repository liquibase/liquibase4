package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ObjectBasedQueryResult
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.database.Database
import liquibase.item.TestItemSupplier
import liquibase.item.core.Column
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.core.View
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.StringClauses
import liquibase.util.TestUtil
import spock.lang.Unroll

class SnapshotItemsActionViewsTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can find complex view names"() {
        expect:
        testAction([
                view_asTable: action.relatedTo
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundView = results.asObject(View)

            assert foundView.toReference().equals(action.relatedTo[0], true)
        })

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(SnapshotItemsAction, [
                            typeToSnapshot: [View],
                            relatedTo     : CollectionUtil.toSingletonSets(getItemReferences(View, it.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope))
                    ])
            ], new ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "can find all views using a null name "() {
        expect:
        testAction([
                view_asTable: action.relatedTo
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0; //there will be multiple

            results.asList(View).each {
                assert it.toReference().container.equals(action.relatedTo[0].container, true)
            }
        })

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(SnapshotItemsAction, [
                            typeToSnapshot: [View],
                            relatedTo     : CollectionUtil.toSingletonSets(it.getAllSchemas().collect({
                                return new RelationReference(View, null, it)
                            }))
                    ])
            ], new ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "can find all views using schema reference"() {
        expect:
        testAction([
                view_asTable: action.relatedTo
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0; //there will be multiple

            results.asList(View).each {
                assert it.toReference().container.equals(action.relatedTo[0], true)
            }
        })

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(SnapshotItemsAction, [
                            typeToSnapshot: [View],
                            relatedTo     : CollectionUtil.toSingletonSets(it.getAllSchemas())
                    ])
            ], new ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #createAction.views on #conn")
    def "can find views with various options"() {
        when:
        def viewDefinition = createAction.views[0]

        then:
        testAction([
                schema_asTable            : viewDefinition.schema,
                name_asTable              : viewDefinition.name,
                definition_asTable        : viewDefinition.definition,
                completeDefinition_asTable: viewDefinition.completeDefinition,
        ], new SnapshotItemsAction(viewDefinition.toReference()), conn, scope, {
            plan, results ->
                assert results instanceof ObjectBasedQueryResult
                assert results.size() == 1;

                def foundView = results.asObject(View)

                assert foundView.toReference().equals(viewDefinition.toReference(), true)
                assert foundView.definition.toString().toLowerCase().startsWith("select")
        })

        where:
        [conn, scope, createAction] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    new CreateViewsActionTest().createAllActionPermutations(it, scope).findAll({
                        return it.views != null && it.views.size() > 0 && it.views[0].schema != null
                    })
            ], new ValidActionFilter(scope))
        }
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return null
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)

        def tablesCreatedInSchemas = new HashSet<String>()

        def tableName = standardCaseItemName("test_table", Table, scope)

        for (
                def ref : getItemReferences(View, connectionSupplier.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope)) {
            if (tablesCreatedInSchemas.add(ref.schema.toString())) {
                def table = new Table(tableName, ref.schema)
                snapshot.add(table)
                snapshot.add(new Column(standardCaseItemName("id", Column, scope), table.toReference(), new DataType(DataType.StandardType.INTEGER), false))
            }
            snapshot.add(new View(ref.name, ref.container, new StringClauses().append("select * from " + (ref.container == null ? ":" : (ref.container.toString() + ".")) + tableName)))
        }
        return snapshot
    }
}
