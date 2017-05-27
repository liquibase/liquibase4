package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.snapshot.Snapshot

import liquibase.item.core.*
import liquibase.item.datatype.DataType
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class CreateTableActionTest extends AbstractActionTest {

    def "empty constructor"() {
        expect:
        new CreateTableAction().describe() == "createTable()"
    }

    def "parametrized constructor"() {
        expect:
        new CreateTableAction(new Table("cat", "schem", "tab")).describe() == "createTable(table=Table{name=tab, schema=cat.schem})"
    }

    @Unroll("#featureName #table")
    def "create simple table with complex name"() {
        expect:
        def action = new CreateTableAction(new Table(table.name, table.container)).addColumn(standardCaseItemName("col_name", Column, scope), new DataType(DataType.StandardType.INTEGER))

        testAction([table_asTable: table.toString()],
                action, conn, scope)

        where:
        [conn, scope, table] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemReferences(Table, it.getAllSchemas(), scope),
            ])
        }
    }

    @Unroll("#featureName: #columnName")
    def "create simple table with complex column name"() {
        expect:
        def action = new CreateTableAction(new Table(standardCaseItemName("table_name", Table, scope))).addColumn(columnName, new DataType(DataType.StandardType.INTEGER))

        testAction([
                column_asTable: columnName.toString()
        ],
                action, conn, scope)

        where:
        [conn, scope, columnName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemNames(Column, scope),
            ])
        }
    }

    @Unroll("#featureName: #action.describe() on #conn")
    def "check all table and permutations"() {
        expect:
        testAction([
                table_asTable              : action.table == null ? null : action.table.name,
                tableTablespace_asTable    : action.table == null ? null : action.table.tablespace,
                tableRemarks_asTable       : action.table == null ? null : action.table.remarks,
                tableSchema_asTable        : action.table == null ? null : action.table.schema,

                column_asTable             : action.columns[0].name,
                columnType_asTable         : action.columns[0].type,
                columnDefaultValue_asTable : action.columns[0].defaultValue,
                columnRemarks_asTable      : action.columns[0].remarks,
                columnNullable_asTable     : action.columns[0].nullable,
                columnAutoInc_asTable      : action.columns[0].autoIncrementDetails,

                pk_asTable                 : action.primaryKey == null ? null : action.primaryKey.name,
                pkTablespace_asTable       : action.primaryKey == null ? null : action.primaryKey.tablespace,
                pkClustered_asTable        : action.primaryKey == null ? null : action.primaryKey.clustered,
                pkColumns_asTable          : action.primaryKey == null ? null : action.primaryKey.columns*.describe(),

                fk_asTable                 : action.foreignKeys*.name,
                fkTable_asTable            : action.foreignKeys*.referencedTable,
                fkDeferrable_asTable       : action.foreignKeys*.deferrable,
                fkInitiallyDeferred_asTable: action.foreignKeys*.initiallyDeferred,
                fkUpdateRule_asTable       : action.foreignKeys*.updateRule,
                fkDeleteRule_asTable       : action.foreignKeys*.deleteRule,
                fkColumnChecks_asTable     : action.foreignKeys*.columnChecks,

                uq_asTable                 : action.uniqueConstraints*.name,
                uqColumns_asTable          : action.uniqueConstraints*.columns,
                uqDeferrable_asTable       : action.uniqueConstraints*.deferrable,
                uqInitiallyDeferred_asTable: action.uniqueConstraints*.initiallyDeferred,
                uqDisabled_asTable         : action.uniqueConstraints*.disabled,
                uqTablespace_asTable       : action.uniqueConstraints*.tablespace,
        ],
                action, conn, scope)

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

    /**
     * For performance reasons, we need to break up the generation of CreateTableActions into smaller groups, otherwise the permutations get far to large.
     * Even the foreign key permutations are broken up
     */
    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        def schema = connectionSupplier.allSchemas[0]
        def tableRef = new RelationReference(Table, standardCaseItemName("test_table", Table, scope), schema)
        def testColName = standardCaseItemName("test_col", Column, scope)
        def col1Name = standardCaseItemName("col1", Column, scope)
        def col2Name = standardCaseItemName("col2", Column, scope)

        def refTableRef = new RelationReference(Table, standardCaseItemName("ref_table", Column, scope), schema)
        def refCol1Name = standardCaseItemName("ref_col1", Column, scope)
        def refCol2Name = standardCaseItemName("ref_col2", Column, scope)

        def returnList = []
        //setup standard column permutations
        def columnPermutations = CollectionUtil.toSingletonLists(CollectionUtil.addTo(TestUtil.createAllPermutationsWithoutNulls(Column, [
                relation            : [tableRef],
                name                : [testColName],
                type                : [new DataType(DataType.StandardType.INTEGER)],
                defaultValue        : [null, 3],
                remarks             : [null, "Remarks Here"],
                nullable            : [null, true, false],
                autoIncrementDetails: [null, new Column.AutoIncrementDetails(), new Column.AutoIncrementDetails(2, 3)]
        ]),
                new Column(testColName, tableRef, new DataType(DataType.StandardType.VARCHAR, 10), true), //varchar type, nullable
                new Column(testColName, tableRef, new DataType(DataType.StandardType.VARCHAR, 10), false), //varchar type, not null
                new Column(null, tableRef, new DataType(DataType.StandardType.INTEGER), true), //null name column
                new Column(testColName, tableRef, (DataType) null, true), //null dataType column
        )).each({
            it.add(new Column(col1Name, tableRef, new DataType(DataType.StandardType.INTEGER), true))
            it.add(new Column(col2Name, tableRef, new DataType(DataType.StandardType.INTEGER), true))
        })

        //column and table-field variations
        returnList.addAll(TestUtil.createAllPermutations(CreateTableAction, [
                table  : TestUtil.createAllPermutations(Table, [
                        name      : [tableRef.name],
                        schema    : [schema],
                        tablespace: ["test_tablespace"],
                        remarks   : ["Remarks Here", "Crazy '!@#\$%^&*()\""],
                ]),
                columns: columnPermutations,
        ]))

        //add primary key variations
        returnList.addAll(TestUtil.createAllPermutationsWithoutNulls(CreateTableAction, [
                table     : TestUtil.createAllPermutationsWithoutNulls(Table, [
                        name  : [tableRef.name],
                        schema: [schema],
                ]),
                columns   : columnPermutations,
                primaryKey: TestUtil.createAllPermutations(PrimaryKey, [
                        name      : [standardCaseItemName("test_pk", PrimaryKey, scope)],
                        tablespace: ["test_tablespace"],
                        relation  : [tableRef],
                        clustered : [true, false],
                        columns   : [[new PrimaryKey.PrimaryKeyColumn(testColName)],
                                     [new PrimaryKey.PrimaryKeyColumn(testColName, Index.IndexDirection.DESC)],
                                     [new PrimaryKey.PrimaryKeyColumn(testColName, Index.IndexDirection.ASC)],
                                     [
                                             new PrimaryKey.PrimaryKeyColumn(col1Name),
                                             new PrimaryKey.PrimaryKeyColumn(col2Name)
                                     ],
                        ]
                ])
        ]))

        //add unique constraint variations
        returnList.addAll(TestUtil.createAllPermutationsWithoutNulls(CreateTableAction, [
                table            : TestUtil.createAllPermutationsWithoutNulls(Table, [
                        name  : [tableRef.name],
                        schema: [schema],
                ]),
                columns          : columnPermutations,
                uniqueConstraints: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(UniqueConstraint, [
                        name             : [standardCaseItemName("uq_name", UniqueConstraint, scope)],
                        relation         : [tableRef],
                        columns          : [[testColName], [col1Name, col2Name]],
                        deferrable       : [true, false],
                        initiallyDeferred: [true, false],
                        disabled         : [true, false],
//            backingIndex:
                        tablespace       : [null, "test_tablespace"],
                ]))
        ]))

        //add fk variations without deferrable or update/delete rules
        returnList.addAll(TestUtil.createAllPermutationsWithoutNulls(CreateTableAction, [
                table      : TestUtil.createAllPermutationsWithoutNulls(Table, [
                        name  : [tableRef.name],
                        schema: [schema],
                ]),
                columns    : columnPermutations,
                foreignKeys: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(ForeignKey, [
                        name           : [standardCaseItemName("test_fk", ForeignKey, scope)],
                        relation       : [tableRef],
                        referencedTable: [refTableRef],
                        columnChecks   : [
                                [new ForeignKey.ForeignKeyColumnCheck(col1Name, refCol1Name)],
                                [
                                        new ForeignKey.ForeignKeyColumnCheck(col1Name, refCol1Name),
                                        new ForeignKey.ForeignKeyColumnCheck(col2Name, refCol2Name)
                                ],
                        ]
                ]))
        ]))

//        //add fk variations with deferrable
        returnList.addAll(TestUtil.createAllPermutationsWithoutNulls(CreateTableAction, [
                table      : TestUtil.createAllPermutationsWithoutNulls(Table, [
                        name  : [tableRef.name],
                        schema: [schema],
                ]),
                columns    : columnPermutations,
                foreignKeys: CollectionUtil.toSingletonLists(TestUtil.createAllPermutationsWithoutNulls(ForeignKey, [
                        name             : [null],
                        relation         : [tableRef],
                        referencedTable  : [refTableRef],
                        deferrable       : [true, false],
                        initiallyDeferred: [true, false],
                        columnChecks     : [
                                [new ForeignKey.ForeignKeyColumnCheck(col1Name, refCol1Name)],
                        ]
                ]))
        ]))

        // add fk variations with update/delete checks
        returnList.addAll(TestUtil.createAllPermutationsWithoutNulls(CreateTableAction, [
                table      : TestUtil.createAllPermutationsWithoutNulls(Table, [
                        name  : [tableRef.name],
                        schema: [schema],
                ]),
                columns    : columnPermutations,
                foreignKeys: CollectionUtil.toSingletonLists(TestUtil.createAllPermutationsWithoutNulls(ForeignKey, [
                        name           : [null],
                        relation       : [tableRef],
                        referencedTable: [refTableRef],
                        updateRule     : [ForeignKey.ReferentialAction.cascade, ForeignKey.ReferentialAction.noAction, ForeignKey.ReferentialAction.restrict, ForeignKey.ReferentialAction.setDefault, ForeignKey.ReferentialAction.setNull],
                        deleteRule     : [ForeignKey.ReferentialAction.cascade, ForeignKey.ReferentialAction.noAction, ForeignKey.ReferentialAction.restrict, ForeignKey.ReferentialAction.setDefault, ForeignKey.ReferentialAction.setNull],
                        columnChecks   : [
                                [new ForeignKey.ForeignKeyColumnCheck(col1Name, refCol1Name)],
                        ]
                ]))
        ]))


        return returnList
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)
        for (ForeignKey fk : CollectionUtil.createIfNull((List) ((CreateTableAction) action).foreignKeys)) {
            if (fk != null) {
                Table fkTable = new Table(fk.referencedTable.name, fk.referencedTable.container)
                PrimaryKey pk
                for (ForeignKey.ForeignKeyColumnCheck check : fk.columnChecks) {
                    if (pk == null) {
                        pk = new PrimaryKey(null, fkTable.toReference())
                    }

                    def column = new Column(check.referencedColumn, fkTable.toReference(), new DataType(DataType.StandardType.INTEGER), false)
                    snapshot.add(column)
                    pk.columns.add(new PrimaryKey.PrimaryKeyColumn(column.name))
                }
                snapshot.add(pk)
                snapshot.add(fkTable)
            }
        }

        return snapshot
    }
}
