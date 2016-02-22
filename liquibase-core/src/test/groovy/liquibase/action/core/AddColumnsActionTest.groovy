package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.snapshot.Snapshot
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.ObjectReference
import liquibase.structure.core.*
import liquibase.structure.datatype.DataType
import liquibase.util.CollectionUtil
import spock.lang.Unroll

class AddColumnsActionTest extends AbstractActionTest {

    @Unroll("#featureName: add #tableName #columnName on #conn")
    def "Can apply single column with standard settings but complex names"() {
        when:
        def action = new AddColumnsAction(new Column(new Column.ColumnReference(tableName, columnName), new DataType(DataType.StandardType.INTEGER), true))

        then:
        testAction([
                tableName_asTable : tableName,
                columnName_asTable: columnName
        ], action, conn, scope)

        where:
        [conn, scope, columnName, tableName] << connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique(),
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope),
            ])
        }

    }

    @Unroll("#featureName: add #columnNames to #schemaName #tableName on #conn")
    def "Can apply multiple columns with standard settings but complex names"() {
        when:
        def action = new AddColumnsAction()
        action.columns = [new Column(new ObjectReference(Table, schemaName, tableName), columnNames[0], new DataType(DataType.StandardType.INTEGER)), new Column(new ObjectReference(Table, schemaName, tableName), columnNames[1], new DataType(DataType.StandardType.INTEGER))]

        then:
        testAction([
                schemaName_asTable : schemaName,
                tableName_asTable  : tableName,
                columnNames_asTable: columnNames
        ], action, conn, scope)

        where:
        [conn, scope, columnNames, tableName, schemaName] << connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    [[standardCaseObjectName("col_1", Column, scope.database), standardCaseObjectName("col_2", Column, scope.database)]],
                    [standardCaseObjectName("table_name", Column, scope.database)],
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
        def tableName = standardCaseObjectName("test_table", Table, scope.database)
        def tableRef = new ObjectReference(Table, connectionSupplier.allSchemas[0], tableName);
        def testColName = standardCaseObjectName("test_col", Column, scope.database)
        def col1Name = standardCaseObjectName("col1", Column, scope.database)
        def col2Name = standardCaseObjectName("col2", Column, scope.database)

        def refTableName = standardCaseObjectName("ref_table", Column, scope.database)
        def refCol1Name = standardCaseObjectName("ref_col1", Column, scope.database)
        def refCol2Name = standardCaseObjectName("ref_col2", Column, scope.database)


        def returnList = []

        def columnPermutations = CollectionUtil.addTo(CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(Column, [
                name                    : [testColName],
                table                   : [tableRef],
                type                    : [new DataType(DataType.StandardType.INTEGER)],
                defaultValue            : [null, 3],
                remarks                 : [null, "Remarks Here"],
                nullable                : [null, true, false],
                autoIncrementInformation: [null, new Column.AutoIncrementInformation(), new Column.AutoIncrementInformation(2, 3)]
        ]))).each({
            it.add(new Column(tableRef, col1Name, new DataType(DataType.StandardType.INTEGER)))
            it.add(new Column(tableRef, col2Name, new DataType(DataType.StandardType.INTEGER)))
        })

        //add standard columns
        returnList.addAll(createAllPermutations(AddColumnsAction, [
                columns: columnPermutations,
        ]))

        //add invalid permutations
        returnList.add(new AddColumnsAction(new Column(null, testColName, new DataType(DataType.StandardType.VARCHAR, 10), true))); //varchar type, nullable
        returnList.add(new AddColumnsAction(new Column(null, testColName, new DataType(DataType.StandardType.VARCHAR, 10), false))); //varchar type, not null
        returnList.add(new AddColumnsAction(new Column(null, null, new DataType(DataType.StandardType.INTEGER)))); //null name column
        returnList.add(new AddColumnsAction(new Column(null, testColName, (DataType) null))); //null dataType column))

        //add primary key permutations
        returnList.addAll(createAllPermutationsWithoutNulls(AddColumnsAction, [
                columns   : columnPermutations,
                primaryKey: createAllPermutations(PrimaryKey, [
                        name      : [standardCaseObjectName("test_pk", PrimaryKey, scope.database)],
                        table     : [tableRef],
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
        returnList.addAll(createAllPermutationsWithoutNulls(AddColumnsAction, [
                columns          : columnPermutations,
                uniqueConstraints: CollectionUtil.toSingletonLists(createAllPermutations(UniqueConstraint, [
                        name             : [standardCaseObjectName("uq_name", UniqueConstraint, scope.database)],
                        columns          : [[testColName], [testColName, col2Name]],
                        deferrable       : [true, false],
                        initiallyDeferred: [true, false],
                        disabled         : [true, false],
//            backingIndex:
                        tablespace       : ["test_tablespace"],
                ]))

        ]))

        //add fk variations without deferrable or update/delete rules
        returnList.addAll(createAllPermutationsWithoutNulls(AddColumnsAction, [
                columns    : columnPermutations,
                foreignKeys: CollectionUtil.toSingletonLists(createAllPermutations(ForeignKey, [
                        name        : [standardCaseObjectName("test_fk", ForeignKey, scope.database)],
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
        returnList.addAll(createAllPermutationsWithoutNulls(AddColumnsAction, [
                columns    : columnPermutations,
                foreignKeys: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(ForeignKey, [
                        name             : [null],
                        deferrable       : [true, false],
                        initiallyDeferred: [true, false],
                        columnChecks     : [
                                [new ForeignKey.ForeignKeyColumnCheck(col1Name, refCol1Name)],
                        ]
                ]))
        ]))

        // add fk variations with update/delete checks
        returnList.addAll(createAllPermutationsWithoutNulls(AddColumnsAction, [
                columns    : columnPermutations,
                foreignKeys: CollectionUtil.toSingletonLists(createAllPermutationsWithoutNulls(ForeignKey, [
                        name        : [null],
                        updateRule  : [ForeignKey.ConstraintType.importedKeyCascade, ForeignKey.ConstraintType.importedKeyNoAction, ForeignKey.ConstraintType.importedKeyRestrict, ForeignKey.ConstraintType.importedKeySetDefault, ForeignKey.ConstraintType.importedKeySetNull],
                        deleteRule  : [ForeignKey.ConstraintType.importedKeyCascade, ForeignKey.ConstraintType.importedKeyNoAction, ForeignKey.ConstraintType.importedKeyRestrict, ForeignKey.ConstraintType.importedKeySetDefault, ForeignKey.ConstraintType.importedKeySetNull],
                        columnChecks: [
                                [new ForeignKey.ForeignKeyColumnCheck(col1Name, refCol1Name)],
                        ]
                ]))
        ]))

        return returnList.each({
            if (it.foreignKeys != null && it.columns != null && it.columns.size > 0) {
                def action = it
                it.foreignKeys.each({
                    if (it == null || (it.table != null && it.referencedTable != null)) {
                        return;
                    }
                    it.table = action.columns[0].table;
                    it.referencedTable = new ObjectReference(Table, it.table.container, refTableName)
                })
            }
        });
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        def seenTables = new HashSet()
        def type
        for (def column : ((AddColumnsAction) action).columns) {
            def tableName = column.table
            if (!seenTables.contains(tableName)) {
                snapshot.add(new Table(tableName))
            }
            snapshot.add(new Column(tableName, standardCaseObjectName("column_existing", Column, scope.getDatabase()), new DataType(DataType.StandardType.INTEGER)))
        }

        for (ForeignKey fk : CollectionUtil.createIfNull((List) ((AddColumnsAction) action).foreignKeys)) {
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
        return snapshot;
    }
}
