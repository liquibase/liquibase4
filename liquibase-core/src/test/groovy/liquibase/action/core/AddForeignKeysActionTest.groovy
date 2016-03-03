package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.TestItemSupplier
import liquibase.snapshot.Snapshot

import liquibase.item.core.Column
import liquibase.item.core.ForeignKey
import liquibase.item.core.Index
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.util.CollectionUtil
import liquibase.util.StringUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class AddForeignKeysActionTest extends AbstractActionTest {

    @Unroll("#featureName: add #fkName for #tableName #columnName on #conn")
    def "Can apply single column with standard settings but complex names"() {
        when:
        def action = new AddForeignKeysAction()

        def pkTable = new Table(StringUtil.concatConsistentCase(tableName.name, "_ref"), tableName.container)

        def foreignKey = new ForeignKey(fkName, tableName, pkTable.toReference(), [columnName], [StringUtil.concatConsistentCase(columnName, "_ref")])

        action.foreignKeys = [foreignKey]

        then:
        testAction([
                fk_asTable    : fkName.toString(),
                table_asTable : tableName.toString(),
                column_asTable: columnName.toString()
        ], action, conn, scope)

        where:
        [conn, scope, columnName, tableName, fkName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemNames(Column, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                    getItemReferences(Table, it.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                    getItemNames(ForeignKey, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
            ])
        }

    }

    @Unroll("#featureName: add to #schema on #conn")
    def "Can apply multiple columns with standard settings"() {
        when:
        def action = new AddForeignKeysAction()
        def tableName = new RelationReference(Table, standardCaseItemName("table_name", Table, scope), schema)
        def fkName = standardCaseItemName("fk_test", ForeignKey, scope)
        def columnName = standardCaseItemName("col_name", Column, scope)

        def pkTable = new Table(StringUtil.concatConsistentCase(tableName.name, "_ref"), tableName.container)

        def foreignKey = new ForeignKey(fkName, tableName, pkTable.toReference(), [columnName, columnName + "_2"], [StringUtil.concatConsistentCase(columnName, "_ref"), StringUtil.concatConsistentCase(columnName, "_2_ref")])

        action.foreignKeys = [foreignKey]


        then:
        testAction([
                schema_asTable: schema
        ], action, conn, scope)

        where:
        [conn, scope, schema] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it).child(JUnitScope.Attr.itemNameStrategy, TestItemSupplier.NameStrategy.COMPLEX_NAMES)
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
                table_asTable            : action.foreignKeys*.relation,
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
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        def baseTableName = standardCaseItemName("base_table", Table, scope)
        def refTableName = standardCaseItemName("ref_table", Table, scope)

        def baseColumnName = standardCaseItemName("id", Column, scope)
        def refColumnName = standardCaseItemName("id_ref", Column, scope)

        return TestUtil.createAllPermutations(AddForeignKeysAction, [
                foreignKeys: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(ForeignKey, [
                        name             : ["test_fk"],
                        relation            : CollectionUtil.addNull(connectionSupplier.allSchemas.collect({ return new RelationReference(Table, baseTableName, it) })),
                        referencedTable  : CollectionUtil.addNull(connectionSupplier.allSchemas.collect({ return new RelationReference(Table, refTableName, it) })),
                        columnChecks     : [[new ForeignKey.ForeignKeyColumnCheck(baseColumnName, refColumnName)]],
                        deferrable       : [true, false],
                        initiallyDeferred: [true, false],
                        updateRule       : [ForeignKey.ReferentialAction.cascade, ForeignKey.ReferentialAction.noAction, ForeignKey.ReferentialAction.restrict, ForeignKey.ReferentialAction.setDefault, ForeignKey.ReferentialAction.setNull],
                        deleteRule       : [ForeignKey.ReferentialAction.cascade, ForeignKey.ReferentialAction.noAction, ForeignKey.ReferentialAction.restrict, ForeignKey.ReferentialAction.setDefault, ForeignKey.ReferentialAction.setNull],
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        def seenTables = new HashSet()
        for (def fk : ((AddForeignKeysAction) action).foreignKeys) {
            def baseTableName = fk.relation
            def refTableName = fk.referencedTable
            def index;
            for (def check : fk.columnChecks) {
                if (index == null) {
                    index = new Index(StringUtil.concatConsistentCase(refTableName.name, "_idx"), refTableName)
                }

                if (seenTables.add(baseTableName)) {
                    snapshot.add(new Table(baseTableName.name, baseTableName.container))
                }
                if (seenTables.add(refTableName)) {
                    snapshot.add(new Table(refTableName.name, refTableName.container))
                }

                index.columns.add(new Index.IndexedColumn(check.referencedColumn)) //index, not PK to support nulls

                snapshot.add(new Column(check.baseColumn, baseTableName, new DataType(DataType.StandardType.INTEGER), true))
                snapshot.add(new Column(check.referencedColumn, refTableName, new DataType(DataType.StandardType.INTEGER), true))
            }

            snapshot.add(index)

        }

        return snapshot
    }
}
