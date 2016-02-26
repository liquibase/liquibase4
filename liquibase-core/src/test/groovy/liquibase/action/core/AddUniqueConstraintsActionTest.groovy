package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.snapshot.Snapshot
import liquibase.item.ItemNameStrategy
import liquibase.item.core.Column
import liquibase.item.core.IndexReference
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.core.UniqueConstraint
import liquibase.item.datatype.DataType
import liquibase.util.CollectionUtil
import spock.lang.Unroll

public class AddUniqueConstraintsActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "Can apply single column with standard settings but complex constraint names"() {
        expect:
        testAction([
                table_asTable: action.uniqueConstraints*.relation,
                uq_asTable   : action.uniqueConstraints*.name,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllPermutationsWithoutNulls(AddUniqueConstraintsAction, [
                            uniqueConstraints: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(UniqueConstraint, [
                                    name    : getItemNames(UniqueConstraint, ItemNameStrategy.COMPLEX_NAMES, scope),
                                    relation: it.allSchemas.collect({
                                        return new RelationReference(Table.class, standardCaseItemName("table_name", Table, scope.database), it)
                                    }),
                                    columns : [[standardCaseItemName("column_name", Column.class, scope.database)]],
                            ]))
                    ])
            ])
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "Can apply single column with standard settings but complex table names"() {
        expect:
        testAction([
                table_asTable: action.uniqueConstraints.relation,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllPermutationsWithoutNulls(AddUniqueConstraintsAction, [
                            uniqueConstraints: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(UniqueConstraint, [
                                    name    : [standardCaseItemName("uq_name", UniqueConstraint.class, scope.database)],
                                    relation: getItemReferences(Table, it.getAllSchemas(), ItemNameStrategy.COMPLEX_NAMES, scope),
                                    columns : [[standardCaseItemName("column_name", Column.class, scope.database)]],
                            ]))
                    ])
            ])
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "Can apply single column with standard settings but complex column names"() {
        expect:
        testAction([
                table_asTable : action.uniqueConstraints*.relation,
                column_asTable: action.uniqueConstraints.columns,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllPermutationsWithoutNulls(AddUniqueConstraintsAction, [
                            uniqueConstraints: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(UniqueConstraint, [
                                    name    : [standardCaseItemName("uq_name", UniqueConstraint.class, scope.database)],
                                    relation: it.allSchemas.collect({
                                        return new RelationReference(Table.class, standardCaseItemName("table_name", Table, scope.database), it)
                                    }),
                                    columns : CollectionUtil.toSingletonLists(getItemNames(Column, ItemNameStrategy.COMPLEX_NAMES, scope)),
                            ]))
                    ])
            ])
        }
    }

    @Unroll("#featureName: add #action on #conn")
    def "Can apply multi-column with standard settings"() {
        expect:
        testAction([
                table_asTable : action.uniqueConstraints*.relation,
                column_asTable: action.uniqueConstraints*.columns
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllPermutationsWithoutNulls(AddUniqueConstraintsAction, [
                            uniqueConstraints: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(UniqueConstraint, [
                                    name    : [standardCaseItemName("uq_name", UniqueConstraint.class, scope.database)],
                                    relation: it.allSchemas.collect({
                                        return new RelationReference(Table, standardCaseItemName("table_name", Table.class, scope.database), it)
                                    }),
                                    columns : [[standardCaseItemName("col_1", Column.class, scope.database), standardCaseItemName("col_2", Column.class, scope.database)]],
                            ]))
                    ])
            ])
        }
    }

    @Unroll("#featureName: for #schema on #conn")
    def "Can add multiple constraints at once"() {
        when:
        def action = new AddUniqueConstraintsAction()

        action.uniqueConstraints = [
                new UniqueConstraint(null, new RelationReference(Table, standardCaseItemName("test_table_1", Table, scope.database), schema), standardCaseItemName("col_name", Column, scope.database)),
                new UniqueConstraint(null, new RelationReference(Table, standardCaseItemName("test_table_2", Table, scope.database), schema), standardCaseItemName("col_name", Column, scope.database)),
                new UniqueConstraint(null, new RelationReference(Table, standardCaseItemName("test_table_3", Table, scope.database), schema), standardCaseItemName("col_name", Column, scope.database)),
        ]

        then:
        testAction(["schema_asTable": schema], action, conn, scope)

        where:
        [conn, scope, schema] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.getAllSchemas()
            ])
        }
    }

    @Unroll("#featureName: #action.describe() on #conn")
    def "Can apply unique constraint with with various settings"() {
        when:
        def uqDef = action.uniqueConstraints[0]

        then:
        testAction([
                name_asTable             : uqDef.name,
                table_asTable            : uqDef.relation,
                columns_asTable          : uqDef.columns,
                deferrable_asTable       : uqDef.deferrable,
                initiallyDeferred_asTable: uqDef.initiallyDeferred,
                disabled_asTable         : uqDef.disabled,
                backingIndex_asTable     : uqDef.backingIndex,
                tablespace_asTable       : uqDef.tablespace,

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
    def createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        def tableName = standardCaseItemName("test_table", Table, scope.database)

        return createAllPermutations(AddUniqueConstraintsAction, [
                uniqueConstraints: CollectionUtil.toSingletonLists(createAllPermutations(UniqueConstraint, [
                        name             : [null, standardCaseItemName("uq_test", UniqueConstraint, scope.database)],
                        relation         : CollectionUtil.addNull(connectionSupplier.allSchemas.collect({
                            return new RelationReference(Table, tableName, it)
                        })),
                        deferrable       : [null, true, false],
                        initiallyDeferred: [null, true, false],
                        disabled         : [null, true, false],
                        tablespace       : [null, "test_tablespace"],
                        backingIndex     : [null, new IndexReference("idx_test")],
                        columns          : [
                                null,
                                [],
                                [standardCaseItemName("col_name", Column, scope.database)],
                                [standardCaseItemName("col_name1", Column, scope.database), standardCaseItemName("col_name2", Column, scope.database)],
                        ]
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        for (def uq : ((AddUniqueConstraintsAction) action).uniqueConstraints) {
            snapshot.add(new Table(uq.relation.name, uq.relation.container))
            for (def colName : uq.columns) {
                snapshot.add(new Column(colName, uq.relation, DataType.parse("int"), false))
            }
            snapshot.add(new Column(standardCaseItemName("non_uq_col", Column, scope.database), uq.relation, DataType.parse("int"), true))
        }

        return snapshot

    }
}
