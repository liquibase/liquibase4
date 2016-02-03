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
import liquibase.structure.core.ForeignKey
import liquibase.structure.core.Index
import liquibase.structure.core.Schema
import liquibase.structure.core.Table
import liquibase.structure.datatype.DataType
import liquibase.test.TestObjectFactory
import liquibase.util.CollectionUtil
import spock.lang.Unroll

class AddForeignKeysActionTest extends AbstractActionTest {

    @Unroll("#featureName: add #fkName for #tableName #columnName on #conn")
    def "Can apply single column with standard settings but complex names"() {
        when:
        def action = new AddForeignKeysAction()

        def pkTable = new Table(tableName.container, concatConsistantCaseObjectName(tableName.name, "_ref"))

        def foreignKey = new ForeignKey(tableName, pkTable.toReference(), fkName, [columnName], [concatConsistantCaseObjectName(columnName, "_ref")])

        action.foreignKeys = [foreignKey]

        then:
        testAction([
                fkName_asTable    : fkName.toString(),
                tableName_asTable : tableName.toString(),
                columnName_asTable: columnName.toString()
        ], action, conn, scope)

        where:
        [conn, scope, columnName, tableName, fkName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique(),
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope),
                    getObjectNames(ForeignKey, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique(),
            ])
        }

    }

    @Unroll("#featureName: add to #schemaName on #conn")
    def "Can apply multiple columns with standard settings"() {
        when:
        def action = new AddForeignKeysAction()
        def tableName = new ObjectReference(Table, schemaName, standardCaseObjectName("table_name", Table, scope.database))
        def fkName = standardCaseObjectName("fk_test", ForeignKey, scope.database)
        def columnName = standardCaseObjectName("col_name", Column, scope.database)

        def pkTable = new Table(tableName.container, concatConsistantCaseObjectName(tableName.name, "_ref"))

        def foreignKey = new ForeignKey(tableName, pkTable.toReference(), fkName, [columnName, columnName + "_2"], [concatConsistantCaseObjectName(columnName, "_ref"), concatConsistantCaseObjectName(columnName, "_2_ref")])

        action.foreignKeys = [foreignKey]


        then:
        testAction([
                schemaName_asTable: schemaName
        ], action, conn, scope)

        where:
        [conn, scope, schemaName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it).child(JUnitScope.Attr.objectNameStrategy, ObjectNameStrategy.COMPLEX_NAMES)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.getAllSchemas()
            ])
        }

    }

    @Unroll("#featureName: #action.describe() on #conn")
    def "Valid parameter foreignKey permutations work"() {
        expect:
        testAction([
                name_asTable             : action.foreignKeys*.name,
                table_asTable            : action.foreignKeys*.table,
                referencedTable_asTable  : action.foreignKeys*.referencedTable,
                columnChecks_asTable     : action.foreignKeys*.columnChecks,
                deferrable_asTable       : action.foreignKeys*.deferrable,
                initiallyDeferred_asTable: action.foreignKeys*.initiallyDeferred,
                updateRule_asTable       : action.foreignKeys*.updateRule,
                deleteRule_asTable       : action.foreignKeys*.deleteRule,
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
        def baseTableName = standardCaseObjectName("base_table", Table, scope.database)
        def refTableName = standardCaseObjectName("ref_table", Table, scope.database)

        def baseColumnName = standardCaseObjectName("id", Column, scope.database)
        def refColumnName = standardCaseObjectName("id_ref", Column, scope.database)

        return createAllPermutations(AddForeignKeysAction, [
                foreignKeys: CollectionUtil.toSingletonLists(createAllPermutations(ForeignKey, [
                        name             : ["test_fk"],
                        table            : CollectionUtil.addNull(connectionSupplier.allSchemas.collect({ return new ObjectReference(Table, it, baseTableName) })),
                        referencedTable  : CollectionUtil.addNull(connectionSupplier.allSchemas.collect({ return new ObjectReference(Table, it, refTableName) })),
                        columnChecks     : [[new ForeignKey.ForeignKeyColumnCheck(baseColumnName, refColumnName)]],
                        deferrable       : [true, false],
                        initiallyDeferred: [true, false],
                        updateRule       : [ForeignKey.ConstraintType.importedKeyCascade, ForeignKey.ConstraintType.importedKeyNoAction, ForeignKey.ConstraintType.importedKeyRestrict, ForeignKey.ConstraintType.importedKeySetDefault, ForeignKey.ConstraintType.importedKeySetNull],
                        deleteRule       : [ForeignKey.ConstraintType.importedKeyCascade, ForeignKey.ConstraintType.importedKeyNoAction, ForeignKey.ConstraintType.importedKeyRestrict, ForeignKey.ConstraintType.importedKeySetDefault, ForeignKey.ConstraintType.importedKeySetNull],
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        def seenTables = new HashSet()
        for (def fk : ((AddForeignKeysAction) action).foreignKeys) {
            def baseTableName = fk.table
            def refTableName = fk.referencedTable
            def index;
            for (def check : fk.columnChecks) {
                if (index == null) {
                    index = new Index(refTableName, concatConsistantCaseObjectName(refTableName.name, "_idx"))
                }

                if (!seenTables.contains(baseTableName)) {
                    snapshot.add(new Table(baseTableName))
                }
                if (!seenTables.contains(refTableName)) {
                    snapshot.add(new Table(refTableName))
                }

                index.columns.add(new Index.IndexedColumn(check.referencedColumn)) //index, not PK to support nulls

                snapshot.add(new Column(baseTableName, check.baseColumn, new DataType(DataType.StandardType.INTEGER), true))
                snapshot.add(new Column(refTableName, check.referencedColumn, new DataType(DataType.StandardType.INTEGER), true))
            }

            snapshot.add(index)

        }

        return snapshot
    }
}
