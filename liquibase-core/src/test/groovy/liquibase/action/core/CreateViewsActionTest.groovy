package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.TestItemSupplier
import liquibase.item.core.Column
import liquibase.item.core.Index
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.core.View
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.StringClauses
import liquibase.util.TestUtil
import spock.lang.Specification
import spock.lang.Unroll

class CreateViewsActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can add create with complex names"() {
        expect:
        testAction([
                name_asTable      : action.views*.name,
                schema_asTable    : action.views*.schema,
                definition_asTable: action.views*.definition,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(CreateViewsAction, [
                            views: CollectionUtil.toSingletonLists(TestUtil.createAllPermutationsWithoutNulls(View, [
                                    name      : getItemNames(View, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                                    schema    : it.allSchemas,
                                    definition: [new StringClauses()],
                            ]).each {
                                if (it.definition != null) {
                                    it.definition = new StringClauses().append("select * from");
                                    if (it.schema == null) {
                                        it.definition.append("test_table")
                                    } else {
                                        it.definition.append(it.schema.toString() + ".test_table")
                                    }
                                }
                            })
                    ])
            ], new AbstractActionTest.ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "can add columns with all permutations"() {
        expect:
        testAction([
                name_asTable      : action.views*.name,
                schema_asTable    : action.views*.schema,
                definition_asTable: action.views*.definition,
                fullDefinition    : action.views*.completeDefinition,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new AbstractActionTest.ValidActionFilter(scope))
        }
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return TestUtil.createAllPermutations(CreateViewsAction, [
                views: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(View, [
                        name      : getItemNames(View, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                        schema    : connectionSupplier.allSchemas,
                        definition: [new StringClauses()],
                ]).each {
                    if (it.definition != null) {
                        it.definition = new StringClauses().append("select * from");
                        if (it.schema == null) {
                            it.definition.append("test_table")
                        } else {
                            it.definition.append(it.schema.toString() + ".test_table")
                        }
                    }

                })
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)


        for (def view : ((CreateViewsAction) action).views) {
            def table = new Table(standardCaseItemName("test_table", Table, scope), view.schema)
            snapshot.add(table)
            snapshot.add(new Column(standardCaseItemName("id", Column, scope), table.toReference(), new DataType(DataType.StandardType.INTEGER), true))
        }
        return snapshot
    }

}
