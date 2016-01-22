package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope;
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.snapshot.Snapshot
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.ObjectReference
import liquibase.structure.core.Column
import liquibase.structure.core.ForeignKey
import liquibase.structure.core.PrimaryKey
import liquibase.structure.core.Schema
import liquibase.structure.core.Table
import liquibase.structure.datatype.DataType
import liquibase.util.CollectionUtil;
import spock.lang.Unroll;

public class AddPrimaryKeysActionTest extends AbstractActionTest {

    @Unroll("#featureName: add #pkName for #tableName #columnName on #conn")
    def "Can apply single column with standard settings but complex names"() {
        when:
        def action = new AddPrimaryKeysAction()

        def pk = new PrimaryKey(pkName, tableName, columnName)

        action.primaryKeys = [pk]

        then:
        testAction([
                pkName_asTable    : pkName.toString(),
                tableName_asTable : tableName.toString(),
                columnName_asTable: columnName.toString()
        ], action, conn, scope)

        where:
        [conn, scope, columnName, tableName, pkName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique(),
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope),
                    CollectionUtil.addNull(getObjectNames(PrimaryKey, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique()),
            ])
        }
    }

    @Unroll("#featureName: add #pkName for #tableName #columnName on #conn")
    def "Can apply multi-column with standard settings but complex names"() {
        when:
        def action = new AddPrimaryKeysAction()

        def column2 = concatConsistantCaseObjectName(columnName, "_2")
        def pk = new PrimaryKey(pkName, tableName, columnName, column2)

        action.primaryKeys = [pk]

        then:
        testAction([
                pkName_asTable    : pkName.toString(),
                tableName_asTable : tableName.toString(),
                columnName_asTable: columnName.toString()
        ], action, conn, scope)

        where:
        [conn, scope, columnName, tableName, pkName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique(),
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope),
                    CollectionUtil.addNull(getObjectNames(PrimaryKey, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique()),
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
            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    getObjectNames(Schema, scope)
            ])
        }
    }

    @Unroll("#featureName: #pkDef.describe() #colDef.describe() on #conn")
    def "Can apply primary key with with various settings"() {
        when:
        ((PrimaryKey.PrimaryKeyColumn) colDef).table = ((PrimaryKey) pkDef).table

        then:
        testAction([
                clustered_asTable : action.primaryKeys*.clustered,
                tablespace_asTable: action.primaryKeys*.tablespace,
                table_asTable     : action.primaryKeys*.table,
                descending_asTable: action.primaryKeys*.descending,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope),
            ], new ValidActionFilter())
        }
    }

    @Override
    def createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        def tableName = standardCaseObjectName("test_table", Table, scope.database)

        return createAllPermutations(AddPrimaryKeysAction, [
                primaryKeys: CollectionUtil.addNull(CollectionUtil.toSingletonLists(createAllPermutations(PrimaryKey, [
                        clustered : [null, true, false],
                        tablespace: [null, "test_tablespace"],
                        columns   : CollectionUtil.toSingletonLists(createAllPermutations(PrimaryKey.PrimaryKeyColumn, [
                                descending: [null, true, false],
                                name      : [null, standardCaseObjectName("col_name", Column, scope.database)]
                        ])),
                        table     : CollectionUtil.addNull(connectionSupplier.allSchemas.each({ return new ObjectReference(Table, it, tableName) })),
                ])))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        for (def pk : ((AddPrimaryKeysAction) action).primaryKeys) {
            snapshot.add(new Table(pk.table))
            for (def col : pk.columns) {
                def colRef = col.toReference()
                if (colRef.container != null && colRef.name != null) {
                    snapshot.add(new Column(colRef.container, colRef.name, DataType.parse("int"), false))
                }
            }
            snapshot.add(new Column(pk.table, standardCaseObjectName("non_pk_col", Column, scope.database), "int"))
        }

        return snapshot

    }
}
