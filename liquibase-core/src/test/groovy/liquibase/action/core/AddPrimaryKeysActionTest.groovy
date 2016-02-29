package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.TestItemSupplier
import liquibase.snapshot.Snapshot

import liquibase.item.core.*
import liquibase.item.datatype.DataType
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

public class AddPrimaryKeysActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "Can apply single column with standard settings but complex PK names"() {
        expect:
        testAction([
                table_asTable: action.primaryKeys*.relation,
                pk_asTable   : action.primaryKeys*.name,
                columns_asTable  : action.primaryKeys*.columns,
        ], action, conn, scope)

        where:
        [conn, scope, action] << okIfEmpty("cannot add named primary keys", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(AddPrimaryKeysAction, [
                            primaryKeys: CollectionUtil.toSingletonLists(TestUtil.createAllPermutationsWithoutNulls(PrimaryKey, [
                                    name    : getItemNames(Column, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                                    relation: it.getAllSchemas().collect({
                                        return new RelationReference(Table, standardCaseItemName("table_name", Table, scope), it)
                                    }),
                                    columns : [[new PrimaryKey.PrimaryKeyColumn(standardCaseItemName("col_name", Column, scope))]]
                            ]))
                    ])
            ], new ValidActionFilter(scope))
        })
    }

    @Unroll("#featureName: #action on #conn")
    def "Can apply single column with standard settings but complex table names"() {
        expect:
        testAction([
                table_asTable: action.primaryKeys*.relation,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(AddPrimaryKeysAction, [
                            primaryKeys: CollectionUtil.toSingletonLists(TestUtil.createAllPermutationsWithoutNulls(PrimaryKey, [
                                    name    : null,
                                    relation: getItemReferences(Table, it.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                                    columns : [[new PrimaryKey.PrimaryKeyColumn(standardCaseItemName("col_name", Column, scope))]]
                            ]))
                    ])
            ])
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "Can apply single column with standard settings but complex column names"() {
        expect:
        testAction([
                column_asTable: action.primaryKeys*.columns*.name
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutationsWithoutNulls(AddPrimaryKeysAction, [
                            primaryKeys: CollectionUtil.toSingletonLists(TestUtil.createAllPermutationsWithoutNulls(PrimaryKey, [
                                    name    : null,
                                    relation:  [new RelationReference(Table, standardCaseItemName("table_name", Table, scope))],
                                    columns : CollectionUtil.toSingletonLists(getItemNames(PrimaryKey, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope).collect({
                                        return new PrimaryKey.PrimaryKeyColumn(it)
                                    })),
                            ]))
                    ])
            ])
        }
    }

    @Unroll("#featureName: for #schema on #conn")
    def "Can add multiple primary keys at once"() {
        when:
        def action = new AddPrimaryKeysAction()

        action.primaryKeys = [
                new PrimaryKey(null, new RelationReference(Table, standardCaseItemName("test_table_1", Table, scope), schema), standardCaseItemName("col_name", Column, scope)),
                new PrimaryKey(null, new RelationReference(Table, standardCaseItemName("test_table_2", Table, scope), schema), standardCaseItemName("col_name", Column, scope)),
                new PrimaryKey(null, new RelationReference(Table, standardCaseItemName("test_table_3", Table, scope), schema), standardCaseItemName("col_name", Column, scope)),
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
    def "Can apply primary key with with various settings"() {
        expect:
        testAction([
                clustered_asTable : action.primaryKeys*.clustered,
                tablespace_asTable: action.primaryKeys*.tablespace,
                table_asTable     : action.primaryKeys*.relation,
                columns_asTable   : action.primaryKeys*.columns*.name,
                direction_asTable : action.primaryKeys*.columns*.direction,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope),
            ], new ValidActionFilter(scope))
        }
    }

    @Override
    def createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        def tableName = standardCaseItemName("test_table", Table, scope)

        return TestUtil.createAllPermutations(AddPrimaryKeysAction, [
                primaryKeys: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(PrimaryKey, [
                        clustered : [true, false],
                        tablespace: ["test_tablespace"],
                        columns   : CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(PrimaryKey.PrimaryKeyColumn, [
                                direction: [Index.IndexDirection.ASC, Index.IndexDirection.DESC],
                                name     : [standardCaseItemName("col_name", Column, scope)]
                        ])),
                        relation  : connectionSupplier.allSchemas.collect({
                            return new RelationReference(Table, tableName, it)
                        }),
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        for (def pk : ((AddPrimaryKeysAction) action).primaryKeys) {
            snapshot.add(new Table(pk.relation.name, pk.relation.container))
            for (def col : pk.columns) {
                if (pk.relation != null && col.name != null) {
                    snapshot.add(new Column(col.name, pk.relation, DataType.parse("int"), false))
                }
            }
            snapshot.add(new Column(standardCaseItemName("non_pk_col", Column, scope), pk.relation, DataType.parse("int"), true))
        }

        return snapshot

    }
}
