package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ActionExecutor
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.snapshot.Snapshot
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.ObjectReference
import liquibase.structure.core.Column
import liquibase.structure.core.ForeignKey
import liquibase.structure.core.Index
import liquibase.structure.core.PrimaryKey
import liquibase.structure.core.Schema
import liquibase.structure.core.Table
import liquibase.structure.core.UniqueConstraint
import liquibase.structure.datatype.DataType
import liquibase.util.CollectionUtil
import org.junit.Assert
import spock.lang.Unroll

class CreateTableActionTest extends AbstractActionTest {

    def "empty constructor"() {
        expect:
        new CreateTableAction().describe() == "createTable()"
    }

    def "parametrized constructor"() {
        expect:
        new CreateTableAction(new Table(new ObjectReference(Table, "cat", "schem", "tab"))).describe() == "createTable(table=Table{name=tab, schema=cat.schem})"
    }

    @Unroll("#featureName #tableName")
    def "create simple table with complex name"() {
        expect:
        def action = new CreateTableAction(new Table(tableName)).addColumn(standardCaseObjectName("col_name", Column, scope.database), new DataType(DataType.StandardType.INTEGER).toString())

        testAction([tableName_asTable: tableName.toString()],
                action, conn, scope)

        where:
        [conn, scope, tableName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope),
            ])
        }
    }

    @Unroll("#featureName: #columnName")
    def "create simple table with complex column name"() {
        expect:
        def action = new CreateTableAction(new Table(standardCaseObjectName("table_name", Table, scope.database))).addColumn(columnName, new DataType(DataType.StandardType.INTEGER).toString())

        testAction([
                columnName_asTable: columnName.toString()
        ],
                action, conn, scope)

        where:
        [conn, scope, columnName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique(),
            ])
        }
    }

    @Unroll("#featureName: #action.describe() on #conn")
    def "check all table and permutations"() {
        expect:
        testAction([
                tableName_asTable           : action.table == null ? null : action.table.name,
                tableTablespace_asTable     : action.table == null ? null : action.table.tablespace,
                tableRemarks_asTable        : action.table == null ? null : action.table.remarks,
                tableSchema_asTable         : action.table == null ? null : action.table.schema,

                columnName_asTable          : action.columns[0].name,
                columnType_asTable          : action.columns[0].type,
                columnDefaultValue_asTable  : action.columns[0].defaultValue,
                columnRemarks_asTable       : action.columns[0].remarks,
                columnNullable_asTable      : action.columns[0].nullable,
                columnAutoInc_asTable       : action.columns[0].autoIncrementInformation,

                pkName_asTable              : action.primaryKey == null ? null : action.primaryKey.name,
                pkTablespace_asTable        : action.primaryKey == null ? null : action.primaryKey.tablespace,
                pkClustered_asTable         : action.primaryKey == null ? null : action.primaryKey.clustered,
                pkColumns_asTable           : action.primaryKey == null ? null : action.primaryKey.columns*.describe(),

                fkName_asTable              : action.foreignKeys*.name,
                fkTableName_asTable         : action.foreignKeys*.referencedTable,
                fkDeferrable_asTable        : action.foreignKeys*.deferrable,
                fkInitiallyDeferred_asTable : action.foreignKeys*.initiallyDeferred,
                fkUpdateRule_asTable        : action.foreignKeys*.updateRule,
                fkDeleteRule_asTable        : action.foreignKeys*.deleteRule,
                fkColumnChecks_asTable      : action.foreignKeys*.columnChecks,

                uqName_asTable              : action.uniqueConstraints*.name,
                uqColumns_asTable           : action.uniqueConstraints*.columns,
                uqDeferrable_asTable        : action.uniqueConstraints*.deferrable,
                uqInitiallyDeferred_asTable : action.uniqueConstraints*.initiallyDeferred,
                uqInitiallyDeferred_disabled: action.uniqueConstraints*.disabled,
                uqTablespace_disabled       : action.uniqueConstraints*.tablespace,
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
    def createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        def schema = connectionSupplier.allSchemas[0]
        def tableRef = new ObjectReference(Table, schema, standardCaseObjectName("test_table", Table, scope.database))
        def testColName = standardCaseObjectName("test_col", Column, scope.database)
        def col1Name = standardCaseObjectName("col1", Column, scope.database)
        def col2Name = standardCaseObjectName("col2", Column, scope.database)

        def refTableRef = new ObjectReference(Table, schema, standardCaseObjectName("ref_table", Column, scope.database))
        def refCol1Name = standardCaseObjectName("ref_col1", Column, scope.database)
        def refCol2Name = standardCaseObjectName("ref_col2", Column, scope.database)

        def returnList = []
        //setup standard column permutations
        def columnPermutations = CollectionUtil.toSingletonLists(CollectionUtil.addTo(createAllPermutationsWithoutNulls(Column, [
                table                   : [tableRef],
                name                    : [testColName],
                type                    : [new DataType(DataType.StandardType.INTEGER)],
                defaultValue            : [null, 3],
                remarks                 : [null, "Remarks Here"],
                nullable                : [null, true, false],
                autoIncrementInformation: [null, new Column.AutoIncrementInformation(), new Column.AutoIncrementInformation(2, 3)]
        ]),
                new Column(tableRef, testColName, new DataType(DataType.StandardType.VARCHAR, 10), true), //varchar type, nullable
                new Column(tableRef, testColName, new DataType(DataType.StandardType.VARCHAR, 10), false), //varchar type, not null
                new Column(tableRef, null, new DataType(DataType.StandardType.INTEGER)), //null name column
                new Column(tableRef, testColName, (DataType) null), //null dataType column
        )).each({
            it.add(new Column(tableRef, col1Name, new DataType(DataType.StandardType.INTEGER)))
            it.add(new Column(tableRef, col2Name, new DataType(DataType.StandardType.INTEGER)))
        })

        //column and table-field variations
        returnList.addAll(createAllPermutations(CreateTableAction, [
                table  : createAllPermutations(Table, [
                        name      : [tableRef],
                        tablespace: ["test_tablespace"],
                        remarks   : ["Remarks Here", "Crazy '!@#\$%^&*()\""],
                ]).each({ it.schema = schema }),
                columns: columnPermutations,
        ]))

        //add primary key variations
        returnList.addAll(createAllPermutationsWithoutNulls(CreateTableAction, [
                table     : createAllPermutationsWithoutNulls(Table, [
                        name  : [tableRef],
                        schema: [schema],
                ]),
                columns   : columnPermutations,
                primaryKey: createAllPermutations(PrimaryKey, [
                        name      : [standardCaseObjectName("test_pk", PrimaryKey, scope.database)],
                        tablespace: ["test_tablespace"],
                        table     : [tableRef],
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
        returnList.addAll(createAllPermutationsWithoutNulls(CreateTableAction, [
                table            : createAllPermutationsWithoutNulls(Table, [
                        name  : [tableRef],
                        schema: [schema],
                ]),
                columns          : columnPermutations,
                uniqueConstraints: CollectionUtil.toSingletonLists(createAllPermutations(UniqueConstraint, [
                        name             : [standardCaseObjectName("uq_name", UniqueConstraint, scope.database)],
                        table            : [tableRef],
                        columns          : [[testColName], [col1Name, col2Name]],
                        deferrable       : [true, false],
                        initiallyDeferred: [true, false],
                        disabled         : [true, false],
//            backingIndex:
                        tablespace       : [null, "test_tablespace"],
                ]))
        ]))

        //add fk variations without deferrable or update/delete rules
        returnList.addAll(createAllPermutationsWithoutNulls(CreateTableAction, [
                table      : createAllPermutationsWithoutNulls(Table, [
                        name  : [tableRef],
                        schema: [schema],
                ]),
                columns    : columnPermutations,
                foreignKeys: CollectionUtil.toSingletonLists(createAllPermutations(ForeignKey, [
                        name           : [standardCaseObjectName("test_fk", ForeignKey, scope.database)],
                        table          : [tableRef],
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
        returnList.addAll(createAllPermutationsWithoutNulls(CreateTableAction, [
                table      : createAllPermutationsWithoutNulls(Table, [
                        name  : [tableRef],
                        schema: [schema],
                ]),
                columns    : columnPermutations,
                foreignKeys: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(ForeignKey, [
                        name             : [null],
                        table            : [tableRef],
                        referencedTable  : [refTableRef],
                        deferrable       : [true, false],
                        initiallyDeferred: [true, false],
                        columnChecks     : [
                                [new ForeignKey.ForeignKeyColumnCheck(col1Name, refCol1Name)],
                        ]
                ]))
        ]))

        // add fk variations with update/delete checks
        returnList.addAll(createAllPermutationsWithoutNulls(CreateTableAction, [
                table      : createAllPermutationsWithoutNulls(Table, [
                        name  : [tableRef],
                        schema: [schema],
                ]),
                columns    : columnPermutations,
                foreignKeys: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(ForeignKey, [
                        name           : [null],
                        table          : [tableRef],
                        referencedTable: [refTableRef],
                        updateRule     : [ForeignKey.ConstraintType.importedKeyCascade, ForeignKey.ConstraintType.importedKeyNoAction, ForeignKey.ConstraintType.importedKeyRestrict, ForeignKey.ConstraintType.importedKeySetDefault, ForeignKey.ConstraintType.importedKeySetNull],
                        deleteRule     : [ForeignKey.ConstraintType.importedKeyCascade, ForeignKey.ConstraintType.importedKeyNoAction, ForeignKey.ConstraintType.importedKeyRestrict, ForeignKey.ConstraintType.importedKeySetDefault, ForeignKey.ConstraintType.importedKeySetNull],
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
                Table fkTable = new Table(fk.referencedTable)
                PrimaryKey pk
                for (ForeignKey.ForeignKeyColumnCheck check : fk.columnChecks) {
                    if (pk == null) {
                        pk = new PrimaryKey(fkTable.toReference(), null)
                    }

                    def column = new Column(new Column.ColumnReference(fkTable.toReference(), check.referencedColumn), new DataType(DataType.StandardType.INTEGER), false)
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
