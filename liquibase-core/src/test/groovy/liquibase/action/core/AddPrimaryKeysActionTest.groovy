package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.snapshot.Snapshot
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.ObjectReference
import liquibase.structure.core.*
import liquibase.structure.datatype.DataType
import liquibase.util.CollectionUtil
import spock.lang.Unroll

public class AddPrimaryKeysActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "Can apply single column with standard settings but complex PK names"() {
        expect:
        testAction([
                tableName_asTable: action.primaryKeys*.table,
                pkName_asTable    : action.primaryKeys*.name,
                columns_asTable    : action.primaryKeys*.columns,
        ], action, conn, scope)

        where:
        [conn, scope, action] << okIfEmpty("cannot add named primary keys", JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllPermutationsWithoutNulls(AddPrimaryKeysAction, [
                            primaryKeys: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(PrimaryKey, [
                                    name   : getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique(),
                                    table  : [standardCaseObjectName("table_name", Table, scope.getDatabase())],
                                    columns: [[new PrimaryKey.PrimaryKeyColumn(standardCaseObjectName("col_name", Column, scope.getDatabase()))]]
                            ]))
                    ])
            ], new ValidActionFilter(scope))
        })
    }

    @Unroll("#featureName: #action on #conn")
    def "Can apply single column with standard settings but complex table names"() {
        expect:
        testAction([
                tableName_asTable : action.primaryKeys*.table,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllPermutationsWithoutNulls(AddPrimaryKeysAction, [
                            primaryKeys: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(PrimaryKey, [
                                    name   : null,
                                    table  : getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope).unique(),
                                    columns: [[new PrimaryKey.PrimaryKeyColumn(standardCaseObjectName("col_name", Column, scope.getDatabase()))]]
                            ]))
                    ])
            ])
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "Can apply single column with standard settings but complex column names"() {
        expect:
        testAction([
                columnName_asTable: action.primaryKeys*.columns*.name
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllPermutationsWithoutNulls(AddPrimaryKeysAction, [
                            primaryKeys: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(PrimaryKey, [
                                    name   : null,
                                    table  : [standardCaseObjectName("table_name", Table, scope.getDatabase())],
                                    columns: CollectionUtil.toSingletonLists(getObjectNames(PrimaryKey, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique().collect({ return new PrimaryKey.PrimaryKeyColumn(it) })),
                            ]))
                    ])
            ])
        }
    }

    @Unroll("#featureName: for #schemaName on #conn")
    def "Can add multiple primary keys at once"() {
        when:
        def action = new AddPrimaryKeysAction()

        action.primaryKeys = [
                new PrimaryKey(null, new ObjectReference(Table, schemaName, standardCaseObjectName("test_table_1", Table, scope.database)), standardCaseObjectName("col_name", Column, scope.database)),
                new PrimaryKey(null, new ObjectReference(Table, schemaName, standardCaseObjectName("test_table_2", Table, scope.database)), standardCaseObjectName("col_name", Column, scope.database)),
                new PrimaryKey(null, new ObjectReference(Table, schemaName, standardCaseObjectName("test_table_3", Table, scope.database)), standardCaseObjectName("col_name", Column, scope.database)),
        ]

        then:
        testAction(["schemaName_asTable": schemaName], action, conn, scope)

        where:
        [conn, scope, schemaName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Schema, scope)
            ])
        }
    }

    @Unroll("#featureName: #action.describe() on #conn")
    def "Can apply primary key with with various settings"() {
        expect:
        testAction([
                clustered_asTable : action.primaryKeys*.clustered,
                tablespace_asTable: action.primaryKeys*.tablespace,
                table_asTable     : action.primaryKeys*.table,
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
        def tableName = standardCaseObjectName("test_table", Table, scope.database)

        return createAllPermutations(AddPrimaryKeysAction, [
                primaryKeys: CollectionUtil.toSingletonLists(createAllPermutations(PrimaryKey, [
                        clustered : [true, false],
                        tablespace: ["test_tablespace"],
                        columns   : CollectionUtil.toSingletonLists(createAllPermutations(PrimaryKey.PrimaryKeyColumn, [
                                direction: [Index.IndexDirection.ASC, Index.IndexDirection.DESC],
                                name     : [standardCaseObjectName("col_name", Column, scope.database)]
                        ])),
                        table     : connectionSupplier.allSchemas.collect({ return new ObjectReference(Table, it, tableName) }),
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        for (def pk : ((AddPrimaryKeysAction) action).primaryKeys) {
            snapshot.add(new Table(pk.table))
            for (def col : pk.columns) {
                if (pk.table != null && col.name != null) {
                    snapshot.add(new Column(pk.table, col.name, DataType.parse("int"), false))
                }
            }
            snapshot.add(new Column(pk.table, standardCaseObjectName("non_pk_col", Column, scope.database), "int"))
        }

        return snapshot

    }
}
