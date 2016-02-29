package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.item.TestItemSupplier
import liquibase.snapshot.Snapshot

import liquibase.item.core.*
import liquibase.item.datatype.DataType
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class AddColumnsActionTest extends AbstractActionTest {

    @Unroll("#featureName: add #tableName #columnName on #conn")
    def "Can apply single column with standard settings but complex names"() {
        when:
        def action = new AddColumnsAction(new Column(columnName, tableName, new DataType(DataType.StandardType.INTEGER), true))

        then:
        testAction([
                table_asTable : tableName,
                column_asTable: columnName
        ], action, conn, scope)

        where:
        [conn, scope, columnName, tableName] << connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemNames(Column, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                    getItemReferences(Table, it.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
            ])
        }

    }

    @Unroll("#featureName: add #columnNames to #schemaName #tableName on #conn")
    def "Can apply multiple columns with standard settings but complex names"() {
        when:
        def action = new AddColumnsAction()
        action.columns = [new Column(columnNames[0], new RelationReference(Table, tableName, schemaName), new DataType(DataType.StandardType.INTEGER), true), new Column(columnNames[1], new RelationReference(Table, tableName, schemaName), new DataType(DataType.StandardType.INTEGER), true)]

        then:
        testAction([
                schema_asTable : schemaName,
                table_asTable  : tableName,
                columnNames_asTable: columnNames
        ], action, conn, scope)

        where:
        [conn, scope, columnNames, tableName, schemaName] << connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    [[standardCaseItemName("col_1", Column, scope), standardCaseItemName("col_2", Column, scope)]],
                    [standardCaseItemName("table_name", Table, scope)],
                    it.allSchemas
            ])
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "Can add single column with various settings"() {
//        when:
//        columnDef.addAfterColumn;
//        columnDef.addBeforeColumn;
//        columnDef.addAtPosition;
//        columnDef.constraints;

        expect:
        testAction([
                type_asTable                    : action.columns*.type,
                defaultValue_asTable            : action.columns*.defaultValue,
                remarks_asTable                 : action.columns*.remarks,
                primaryKey_asTable              : action.primaryKey,
                nullable_asTable                : action.columns*.nullable,
                foreignKeys_asTable             : action.foreignKeys*.toString(),
                autoIncrementInformation_asTable: action.columns*.autoIncrementInformation,
        ], action, conn, scope)

        where:
        [conn, scope, action] << connectionSuppliers.collectMany {
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
        def tableName = standardCaseItemName("test_table", Table, scope)
        def tableRef = new RelationReference(Table, tableName, connectionSupplier.allSchemas[0]);
        def testColName = standardCaseItemName("test_col", Column, scope)
        def col1Name = standardCaseItemName("col1", Column, scope)
        def col2Name = standardCaseItemName("col2", Column, scope)

        def refTableName = standardCaseItemName("ref_table", Table, scope)
        def refCol1Name = standardCaseItemName("ref_col1", Column, scope)
        def refCol2Name = standardCaseItemName("ref_col2", Column, scope)


        def returnList = []

        def columnPermutations = CollectionUtil.addTo(CollectionUtil.toSingletonLists(TestUtil.createAllPermutationsWithoutNulls(Column, [
                name                    : [testColName],
                relation                : [tableRef],
                type                    : [new DataType(DataType.StandardType.INTEGER)],
                defaultValue            : [null, 3],
                remarks                 : [null, "Remarks Here"],
                nullable                : [null, true, false],
                autoIncrementInformation: [null, new Column.AutoIncrementInformation(), new Column.AutoIncrementInformation(2, 3)]
        ]))).each({
            it.add(new Column(col1Name, tableRef, new DataType(DataType.StandardType.INTEGER), true))
            it.add(new Column(col2Name, tableRef, new DataType(DataType.StandardType.INTEGER), true))
        })

        //add standard columns
        returnList.addAll(TestUtil.createAllPermutations(AddColumnsAction, [
                columns: columnPermutations,
        ]))

        //add invalid permutations
        returnList.add(new AddColumnsAction(new Column(testColName, null, new DataType(DataType.StandardType.VARCHAR, 10), true)));
        //varchar type, nullable
        returnList.add(new AddColumnsAction(new Column(testColName, null, new DataType(DataType.StandardType.VARCHAR, 10), false)));
        //varchar type, not null
        returnList.add(new AddColumnsAction(new Column(null, null, new DataType(DataType.StandardType.INTEGER), true)));
        //null name column
        returnList.add(new AddColumnsAction(new Column(testColName, null, (DataType) null, true)));
        //null dataType column))

        //add primary key permutations
        returnList.addAll(TestUtil.createAllPermutationsWithoutNulls(AddColumnsAction, [
                columns   : columnPermutations,
                primaryKey: TestUtil.createAllPermutations(PrimaryKey, [
                        name      : [standardCaseItemName("test_pk", PrimaryKey, scope)],
                        relation  : [tableRef],
                        tablespace: ["test_tablespace"],
                        clustered : [true, false],
                        columns   : [[new PrimaryKey.PrimaryKeyColumn(testColName)],
                                     [new PrimaryKey.PrimaryKeyColumn(testColName, Index.IndexDirection.DESC)],
                                     [new PrimaryKey.PrimaryKeyColumn(testColName, Index.IndexDirection.ASC)],
                                     [
                                             new PrimaryKey.PrimaryKeyColumn(testColName),
                                             new PrimaryKey.PrimaryKeyColumn(col2Name)
                                     ],
                        ]
                ])
        ]))

        //add unique constraint permutations
        returnList.addAll(TestUtil.createAllPermutationsWithoutNulls(AddColumnsAction, [
                columns          : columnPermutations,
                uniqueConstraints: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(UniqueConstraint, [
                        name             : [standardCaseItemName("uq_name", UniqueConstraint, scope)],
                        columns          : [[testColName], [testColName, col2Name]],
                        deferrable       : [true, false],
                        initiallyDeferred: [true, false],
                        disabled         : [true, false],
//            backingIndex:
                        tablespace       : ["test_tablespace"],
                ]))

        ]))

        //add fk variations without deferrable or update/delete rules
        returnList.addAll(TestUtil.createAllPermutationsWithoutNulls(AddColumnsAction, [
                columns    : columnPermutations,
                foreignKeys: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(ForeignKey, [
                        name        : [standardCaseItemName("test_fk", ForeignKey, scope)],
                        columnChecks: [
                                [new ForeignKey.ForeignKeyColumnCheck(col1Name, refCol1Name)],
                                [
                                        new ForeignKey.ForeignKeyColumnCheck(col1Name, refCol1Name),
                                        new ForeignKey.ForeignKeyColumnCheck(col2Name, refCol2Name)
                                ],
                        ]
                ]))
        ]))

//        //add fk variations with deferrable
        returnList.addAll(TestUtil.createAllPermutationsWithoutNulls(AddColumnsAction, [
                columns    : columnPermutations,
                foreignKeys: CollectionUtil.toSingletonLists(TestUtil.createAllPermutationsWithoutNulls(ForeignKey, [
                        name             : [null],
                        deferrable       : [true, false],
                        initiallyDeferred: [true, false],
                        columnChecks     : [
                                [new ForeignKey.ForeignKeyColumnCheck(col1Name, refCol1Name)],
                        ]
                ]))
        ]))

        // add fk variations with update/delete checks
        returnList.addAll(TestUtil.createAllPermutationsWithoutNulls(AddColumnsAction, [
                columns    : columnPermutations,
                foreignKeys: CollectionUtil.toSingletonLists(TestUtil.createAllPermutationsWithoutNulls(ForeignKey, [
                        name        : [null],
                        updateRule  : [ForeignKey.ReferentialAction.cascade, ForeignKey.ReferentialAction.noAction, ForeignKey.ReferentialAction.restrict, ForeignKey.ReferentialAction.setDefault, ForeignKey.ReferentialAction.setNull],
                        deleteRule  : [ForeignKey.ReferentialAction.cascade, ForeignKey.ReferentialAction.noAction, ForeignKey.ReferentialAction.restrict, ForeignKey.ReferentialAction.setDefault, ForeignKey.ReferentialAction.setNull],
                        columnChecks: [
                                [new ForeignKey.ForeignKeyColumnCheck(col1Name, refCol1Name)],
                        ]
                ]))
        ]))

        return returnList.each({
            if (it.foreignKeys != null && it.columns != null && it.columns.size > 0) {
                def action = it
                it.foreignKeys.each({
                    if (it == null || (it.relation != null && it.referencedTable != null)) {
                        return;
                    }
                    it.relation = action.columns[0].relation;
                    it.referencedTable = new RelationReference(Table, refTableName, it.relation.container)
                })
            }
        });
    }


    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        def seenTables = new HashSet()
        for (def column : ((AddColumnsAction) action).columns) {
            def tableName = column.relation
            if (seenTables.add(tableName)) {
                snapshot.add(new Table(tableName.name, tableName.container))
                snapshot.add(new Column(standardCaseItemName("column_existing", Column, scope), tableName, new DataType(DataType.StandardType.INTEGER), true))
            }
        }

        for (ForeignKey fk : CollectionUtil.createIfNull((List) ((AddColumnsAction) action).foreignKeys)) {
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
        return snapshot;
    }
}
