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
        new CreateTableAction(new Table(new ObjectReference(Table, "cat", "schem", "tab"))).describe() == "createTable(table=Table{name=tab, schema=cat.schem (SCHEMA)})"
    }

    @Unroll("#featureName #tableName with #columnName")
    def "create simple table with complex names"() {
        expect:
        def action = new CreateTableAction(new Table(tableName)).addColumn(columnName, "int")

        testAction([connection: conn, tableName_asTable: tableName.toString(), columnName_asTable: columnName.toString()],
                action, conn, scope)

        where:
        [conn, scope, tableName, columnName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope),
                    getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique(),
            ])
        }
    }

    @Unroll("#featureName: #action.describe() on #conn")
    def "check all table and PK permutations"() {
        expect:
        testAction([
                connection                : conn,
                tableName_asTable         : action.table == null ? null : action.table.name,
                tableTablespace_asTable   : action.table == null ? null : action.table.tablespace,
                tableRemarks_asTable      : action.table == null ? null : action.table.remarks,
                tableSchema_asTable       : action.table == null ? null : action.table.schema,
                columnType_asTable        : action.columns[0].type,
                columnDefaultValue_asTable: action.columns[0].defaultValue,
                columnRemarks_asTable     : action.columns[0].remarks,
                columnNullable_asTable    : action.columns[0].nullable,
                columnAutoInc_asTable     : action.columns[0].autoIncrementInformation,
                pkName_asTable            : action.primaryKey == null ? null : action.primaryKey.name,
                pkTablespace_asTable      : action.primaryKey == null ? null : action.primaryKey.tablespace,
                pkClustered_asTable       : action.primaryKey == null ? null : action.primaryKey.clustered,
                pkColumns_asTable         : action.primaryKey == null ? null : action.primaryKey.columns*.describe(),
        ],
                action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    createAllActionPermutations(scope)
            ], new CollectionUtil.CollectionFilter<Map>() {
                @Override
                boolean include(Map obj) {
                    CreateTableAction action = obj.get("2");

                    def column = action.columns[0]
                    if (column.table != null) {
                        column.table = action.table.toReference()
                    }
                    if (column.type != null && column.defaultValue == "WITH_DEFAULT_VALUE") {
                        if (column.type.standardType == DataType.StandardType.INTEGER) {
                            column.defaultValue = 3
                        } else if (column.type.standardType == DataType.StandardType.VARCHAR) {
                            column.defaultValue = "test value"
                        } else {
                            Assert.fail("Unknown dataType: " + column.type)
                        }
                    }

                    if (action.table != null && action.columns.size() < 3) {
                        action.addColumn(col1Name, "int")
                                .addColumn(col2Name, "int")
                    }

                    def valid = !scope.getSingleton(ActionExecutor).validate(action, scope).hasErrors()
                    return valid
                }
            })
        }
    }

    @Override
    def createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        def testColName = standardCaseObjectName("test_col", Column, scope.database)
        def col1Name = standardCaseObjectName("col1", Column, scope.database)
        def col2Name = standardCaseObjectName("col2", Column, scope.database)

        return createAllPermutations(CreateTableAction, [
                table     : CollectionUtil.addNull(createAllPermutations(Table, [
                        name      : [null, standardCaseObjectName("test_table", Table, scope.database)],
                        tablespace: [null, "test_tablespace"],
                        remarks   : [null, "Remarks Here", "Crazy '!@#\$%^&*()\""],
                        schema    : getObjectNames(Schema, scope)
                ])),
                primaryKey: CollectionUtil.addNull(createAllPermutations(PrimaryKey, [
                        name      : [null, standardCaseObjectName("test_pk", PrimaryKey, scope.database)],
                        tablespace: [null, "test_tablespace"],
                        clustered : [null, true, false],
                        columns   : [null,
                                     [new PrimaryKey.PrimaryKeyColumn(new Column.ColumnReference((ObjectReference) null, testColName))],
                                     [new PrimaryKey.PrimaryKeyColumn(new Column.ColumnReference((ObjectReference) null, testColName), true)],
                                     [new PrimaryKey.PrimaryKeyColumn(new Column.ColumnReference((ObjectReference) null, testColName), false)],
                                     [
                                             new PrimaryKey.PrimaryKeyColumn(new Column.ColumnReference((ObjectReference) null, col1Name)),
                                             new PrimaryKey.PrimaryKeyColumn(new Column.ColumnReference((ObjectReference) null, col2Name))
                                     ],
                        ]
                ])),
                columns   : CollectionUtil.toSingletonLists(createAllPermutations(Column, [
                        name                    : [null, testColName],
                        type                    : [new DataType(DataType.StandardType.INTEGER), new DataType(DataType.StandardType.VARCHAR, 10)],
                        defaultValue            : [null, "WITH_DEFAULT_VALUE"],
                        remarks                 : [null, "Remarks Here"],
                        nullable                : [null, true, false],
                        autoIncrementInformation: [null, new Column.AutoIncrementInformation(), new Column.AutoIncrementInformation(2, 3)]
                ])),
        ])
    }

    @Unroll("#featureName: #action.describe() on #conn")
    def "check all FK permutations"() {
        expect:

        testAction([
                connection           : conn,
                tableName_asTable    : action.table.name,
                tableSchema_asTable  : action.table.schema,
                fkName_asTable : action.foreignKeys*.name,
                fkDeferrable_asTable : action.foreignKeys*.deferrable,
                fkUpdateRule_asTable : action.foreignKeys*.updateRule,
                fkDeleteRule_asTable : action.foreignKeys*.deleteRule,
                fkColumnNames_asTable: action.foreignKeys*.columnChecks*.referencedColumn*.name,
        ],
                action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            def tableName = standardCaseObjectName("test_table", Table, scope.database)
            def testColName = standardCaseObjectName("test_col", Column, scope.database)
            def col1Name = standardCaseObjectName("col1", Column, scope.database)
            def col2Name = standardCaseObjectName("col2", Column, scope.database)

            def refTableName = standardCaseObjectName("ref_table", Column, scope.database)
            def refCol1Name = standardCaseObjectName("ref_col1", Column, scope.database)
            def refCol2Name = standardCaseObjectName("ref_col2", Column, scope.database)


            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    createAllPermutations(CreateTableAction, [
                            table      : createAllPermutations(Table, [
                                    name  : [tableName],
                                    schema: getObjectNames(Schema, scope)
                            ]),
                            columns    : CollectionUtil.toSingletonLists(createAllPermutations(Column, [
                                    name        : [testColName],
                                    type        : [new DataType(DataType.StandardType.INTEGER)],
                                    defaultValue: [null, "WITH_DEFAULT_VALUE"],
                                    remarks     : [null, "Remarks Here"],
                                    nullable    : [null, true, false],
                            ])),

                            foreignKeys: CollectionUtil.addTo(CollectionUtil.toSingletonLists(CollectionUtil.addNull(createAllPermutations(ForeignKey, [
                                    name             : [null, standardCaseObjectName("test_fk", ForeignKey, scope.database)],
                                    deferrable       : [null, true, false],
                                    initiallyDeferred: [null, true, false],
                                    updateRule       : [null, ForeignKey.ConstraintType.importedKeyCascade, ForeignKey.ConstraintType.importedKeyNoAction, ForeignKey.ConstraintType.importedKeyRestrict, ForeignKey.ConstraintType.importedKeySetDefault, ForeignKey.ConstraintType.importedKeySetNull],
                                    deleteRule       : [null, ForeignKey.ConstraintType.importedKeyCascade, ForeignKey.ConstraintType.importedKeyNoAction, ForeignKey.ConstraintType.importedKeyRestrict, ForeignKey.ConstraintType.importedKeySetDefault, ForeignKey.ConstraintType.importedKeySetNull],
                                    columnChecks     : [null,
                                                        [new ForeignKey.ForeignKeyColumnCheck(testColName, new Column.ColumnReference(new ObjectReference(Table, refTableName), refCol1Name))],
                                                        [new ForeignKey.ForeignKeyColumnCheck(col1Name, new Column.ColumnReference(new ObjectReference(Table, refTableName), refCol1Name))],
                                                        [
                                                                new ForeignKey.ForeignKeyColumnCheck(col1Name, new Column.ColumnReference(new ObjectReference(Table, refTableName), refCol1Name)),
                                                                new ForeignKey.ForeignKeyColumnCheck(col2Name, new Column.ColumnReference(new ObjectReference(Table, refTableName), refCol2Name))
                                                        ],
                                    ]
                            ]))),
                                    [new ForeignKey(new ObjectReference(Table, tableName), null, [col1Name], [new Column.ColumnReference(new ObjectReference(Table, refTableName), refCol1Name)])],
                                    [new ForeignKey(new ObjectReference(Table, tableName), null, [col2Name], [new Column.ColumnReference(new ObjectReference(Table, refTableName), refCol2Name)])],
                            ),
                    ])
            ], new CollectionUtil.CollectionFilter<Map>() {
                @Override
                boolean include(Map obj) {
                    CreateTableAction action = obj.get("2");

                    def column = action.columns[0]
                    if (column.table != null) {
                        column.table = action.table.toReference()
                    }
                    if (column.type != null && column.defaultValue == "WITH_DEFAULT_VALUE") {
                        if (column.type.standardType == DataType.StandardType.INTEGER) {
                            column.defaultValue = 3
                        } else {
                            Assert.fail("Unknown dataType: " + column.type)
                        }
                    }

                    if (action.table != null && action.columns.size() < 3) {
                        action.addColumn(col1Name, "int")
                                .addColumn(col2Name, "int")
                    }

                    if (action.table != null) {
                        for (ForeignKey fk : action.foreignKeys) {
                            if (fk != null) {
                                for (def fkCheck : fk.columnChecks) {
                                    fkCheck.referencedColumn.relation.container = action.table.schema
                                }
                            }
                        }
                    }


                    return !scope.getSingleton(ActionExecutor).validate(action, scope).hasErrors()
                }
            })
        }
    }

    @Unroll("#featureName: #action.describe() on #conn")
    def "check all unique constraint permutations"() {
        expect:
        testAction([
                connection           : conn,
                tableName_asTable    : action.table.name,
                tableSchema_asTable  : action.table.schema,
                columnName_asTable: action.columns*.name,
                columnType_asTable: action.columns*.type,
                columnDefaultValue_asTable: action.columns*.defaultValue,
                columnRemarks_asTable: action.columns*.remarks,
                columnNullable_asTable: action.columns*.nullable,
                uqName_asTable : action.uniqueConstraints*.name,
                deferrable_asTable : action.uniqueConstraints*.deferrable,
                initiallyDeferrable_asTable : action.uniqueConstraints*.initiallyDeferred,
                disabled_asTable : action.uniqueConstraints*.disabled,
                tablespace_asTable : action.uniqueConstraints*.tablespace,
                uniqueColumnNames_asTable: action.uniqueConstraints*.columns,
                backingIndex_asTable: action.uniqueConstraints*.backingIndex,
        ],
                action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            def tableName = standardCaseObjectName("test_table", Table, scope.database)
            def testColName = standardCaseObjectName("test_col", Column, scope.database)
            def col1Name = standardCaseObjectName("col1", Column, scope.database)
            def col2Name = standardCaseObjectName("col2", Column, scope.database)

            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    createAllPermutations(CreateTableAction, [
                            table            : createAllPermutations(Table, [
                                    name  : [tableName],
                                    schema: getObjectNames(Schema, scope)
                            ]),
                            columns          : CollectionUtil.toSingletonLists(createAllPermutations(Column, [
                                    name        : [testColName],
                                    type        : [new DataType(DataType.StandardType.INTEGER)],
                                    defaultValue: [null, "WITH_DEFAULT_VALUE"],
                                    remarks     : [null, "Remarks Here"],
                                    nullable    : [null, true, false],
                            ])),

                            uniqueConstraints: CollectionUtil.addTo(CollectionUtil.toSingletonLists(CollectionUtil.addNull(createAllPermutations(UniqueConstraint, [
                                    name             : [null, standardCaseObjectName("test_uq", UniqueConstraint, scope.database)],
                                    deferrable       : [null, true, false],
                                    initiallyDeferred: [null, true, false],
                                    disabled         : [null, true, false],
                                    tablespace       : [null, "test_tablespace"],
                                    columns          : [null,
                                                        [testColName],
                                                        [col1Name],
                                                        [col1Name, col2Name],
                                    ],
                                    backingIndex: [null, new ObjectReference(Index, "idx_uq_test")]
                            ]))),
                                    [new UniqueConstraint(null, new ObjectReference(Table, tableName), col1Name), new UniqueConstraint(null, new ObjectReference(Table, tableName), col2Name)]
                            ),
                    ])
            ], new CollectionUtil.CollectionFilter<Map>() {
                @Override
                boolean include(Map obj) {
                    CreateTableAction action = obj.get("2");

                    def column = action.columns[0]
                    if (column.table != null) {
                        column.table = action.table.toReference()
                    }
                    if (column.type != null && column.defaultValue == "WITH_DEFAULT_VALUE") {
                        if (column.type.standardType == DataType.StandardType.INTEGER) {
                            column.defaultValue = 3
                        } else {
                            Assert.fail("Unknown dataType: " + column.type)
                        }
                    }

                    if (action.table != null && action.columns.size() < 3) {
                        action.addColumn(col1Name, "int")
                                .addColumn(col2Name, "int")
                    }

                    return !scope.getSingleton(ActionExecutor).validate(action, scope).hasErrors()
                }
            })
        }
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        def snapshot = new Snapshot(scope)
        for (ForeignKey fk : CollectionUtil.createIfNull((List) ((CreateTableAction) action).foreignKeys)) {
            if (fk != null) {
                Table fkTable = new Table()
                PrimaryKey pk
                for (ForeignKey.ForeignKeyColumnCheck check : fk.columnChecks) {
                    fkTable.name = check.referencedColumn.relation.name
                    fkTable.schema = check.referencedColumn.relation.container

                    if (pk == null) {
                        pk = new PrimaryKey(fkTable.toReference(), null)
                    }

                    def column = new Column(new Column.ColumnReference(fkTable.toReference(), check.referencedColumn.name), DataType.parse("int"), false)
                    snapshot.add(column)
                    pk.columns.add(new PrimaryKey.PrimaryKeyColumn(column.toReference()))
                }
                snapshot.add(pk)
                snapshot.add(fkTable)
            }
        }

        return snapshot
    }
}
