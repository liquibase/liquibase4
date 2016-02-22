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
import liquibase.structure.core.Column
import liquibase.structure.core.Schema
import liquibase.structure.core.Table
import liquibase.structure.core.UniqueConstraint
import liquibase.structure.datatype.DataType
import liquibase.util.CollectionUtil
import spock.lang.Unroll

public class AddUniqueConstraintsActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "Can apply single column with standard settings but complex constraint names"() {
        expect:
        testAction([
                tableName_asTable: action.uniqueConstraints*.table,
                uqName_asTable: action.uniqueConstraints*.name,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllPermutationsWithoutNulls(AddUniqueConstraintsAction, [
                            uniqueConstraints: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(UniqueConstraint, [
                                    name   : getObjectNames(UniqueConstraint, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique(),
                                    table  : it.allSchemas.collect({ return new ObjectReference(Table.class, it, standardCaseObjectName("table_name", Table, scope.database)) }),
                                    columns: [[standardCaseObjectName("column_name", Column.class, scope.database)]],
                            ]))
                    ])
            ])
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "Can apply single column with standard settings but complex table names"() {
        expect:
        testAction([
                tableName_asTable: action.uniqueConstraints.table,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllPermutationsWithoutNulls(AddUniqueConstraintsAction, [
                            uniqueConstraints: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(UniqueConstraint, [
                                    name   : [standardCaseObjectName("uq_name", UniqueConstraint.class, scope.database)],
                                    table  : getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope),
                                    columns: [[standardCaseObjectName("column_name", Column.class, scope.database)]],
                            ]))
                    ])
            ])
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "Can apply single column with standard settings but complex column names"() {
        expect:
        testAction([
                tableName_asTable: action.uniqueConstraints*.table,
                columnName_asTable: action.uniqueConstraints.columns,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllPermutationsWithoutNulls(AddUniqueConstraintsAction, [
                            uniqueConstraints: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(UniqueConstraint, [
                                    name   : [standardCaseObjectName("uq_name", UniqueConstraint.class, scope.database)],
                                    table  : it.allSchemas.collect({ return new ObjectReference(Table.class, it, standardCaseObjectName("table_name", Table, scope.database)) }),
                                    columns: CollectionUtil.toSingletonLists(getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique()),
                            ]))
                    ])
            ])
        }
    }

    @Unroll("#featureName: add #action on #conn")
    def "Can apply multi-column with standard settings"() {
        expect:
        testAction([
                tableName_asTable: action.uniqueConstraints*.table,
                columnName_asTable: action.uniqueConstraints*.columns
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllPermutationsWithoutNulls(AddUniqueConstraintsAction, [
                            uniqueConstraints: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(UniqueConstraint, [
                                    name   : [standardCaseObjectName("uq_name", UniqueConstraint.class, scope.database)],
                                    table  : it.allSchemas.collect({ return new ObjectReference(Table, it, standardCaseObjectName("table_name", Table.class, scope.database)) }),
                                    columns: [[standardCaseObjectName("col_1", Column.class, scope.database), standardCaseObjectName("col_2", Column.class, scope.database)]],
                            ]))
                    ])
            ])
        }
    }

    @Unroll("#featureName: for #schemaName on #conn")
    def "Can add multiple constraints at once"() {
        when:
        def action = new AddUniqueConstraintsAction()

        action.uniqueConstraints = [
                new UniqueConstraint(null, new ObjectReference(Table, schemaName, standardCaseObjectName("test_table_1", Table, scope.database)), standardCaseObjectName("col_name", Column, scope.database)),
                new UniqueConstraint(null, new ObjectReference(Table, schemaName, standardCaseObjectName("test_table_2", Table, scope.database)), standardCaseObjectName("col_name", Column, scope.database)),
                new UniqueConstraint(null, new ObjectReference(Table, schemaName, standardCaseObjectName("test_table_3", Table, scope.database)), standardCaseObjectName("col_name", Column, scope.database)),
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
    def "Can apply unique constraint with with various settings"() {
        when:
        def uqDef = action.uniqueConstraints[0]

        then:
        testAction([
                name_asTable            : uqDef.name,
                table_asTable            : uqDef.table,
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
        def tableName = standardCaseObjectName("test_table", Table, scope.database)

        return createAllPermutations(AddUniqueConstraintsAction, [
                uniqueConstraints: CollectionUtil.toSingletonLists(createAllPermutations(UniqueConstraint, [
                        name             : [null, standardCaseObjectName("uq_test", UniqueConstraint, scope.database)],
                        table            : CollectionUtil.addNull(connectionSupplier.allSchemas.collect({ return new ObjectReference(Table, it, tableName) })),
                        deferrable       : [null, true, false],
                        initiallyDeferred: [null, true, false],
                        disabled         : [null, true, false],
                        tablespace       : [null, "test_tablespace"],
                        backingIndex     : [null, "idx_test"],
                        columns          : [
                                null,
                                [],
                                [standardCaseObjectName("col_name", Column, scope.database)],
                                [standardCaseObjectName("col_name1", Column, scope.database), standardCaseObjectName("col_name2", Column, scope.database)],
                        ]
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        for (def uq : ((AddUniqueConstraintsAction) action).uniqueConstraints) {
            snapshot.add(new Table(uq.table))
            for (def colName : uq.columns) {
                snapshot.add(new Column(uq.table, colName, DataType.parse("int"), false))
            }
            snapshot.add(new Column(uq.table, standardCaseObjectName("non_uq_col", Column, scope.database), "int"))
        }

        return snapshot

    }
}
