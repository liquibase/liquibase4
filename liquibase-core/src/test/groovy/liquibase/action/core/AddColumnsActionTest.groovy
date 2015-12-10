package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.snapshot.Snapshot
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.core.Column
import liquibase.structure.datatype.DataType
import liquibase.structure.core.PrimaryKey
import liquibase.structure.core.Table
import liquibase.util.CollectionUtil
import org.junit.Assert
import org.junit.Assume
import spock.lang.Unroll

class AddColumnsActionTest extends AbstractActionTest {

    @Unroll("#featureName: add #tableName #columnName on #conn")
    def "Can apply single column with standard settings but complex names"() {
        when:
        columnName = new Column.ColumnReference(tableName, columnName.name)

        def action = new AddColumnsAction(new Column(columnName.container, columnName.name, new DataType(DataType.StandardType.INTEGER)))

        then:
        runStandardTest([
                columnName_asTable: columnName.toString()
        ], action, conn, scope)

        where:
        [conn, scope, columnName, tableName] << connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope),
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope),
            ])
        }

    }

    @Unroll("#featureName: add #columnNames to #tableName on #conn")
    def "Can apply multiple columns with standard settings but complex names"() {
        when:
        def action = new AddColumnsAction()
        action.columns = [new Column(tableName, columnNames[0], new DataType(DataType.StandardType.INTEGER)), new Column(tableName, columnNames[1], new DataType(DataType.StandardType.INTEGER))]

        then:
        runStandardTest([
                tableName_asTable: tableName.toString(),
                columnNames_asTable: columnNames.toString()
        ], action, conn, scope)

        where:
        [conn, scope, columnNames, tableName] << connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it).child(JUnitScope.Attr.objectNameStrategy, ObjectNameStrategy.COMPLEX_NAMES)
            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope).collect {[it.name+"_2", it.name+"_3"]},
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope)
            ])
        }
    }

    @Unroll("#featureName: with PK #primaryKey and FKs #foreignKeys on #conn")
    def "Can apply single column with various settings"() {
        when:
        def tableName = getObjectNames(Table, ObjectNameStrategy.SIMPLE_NAMES, scope)[0]

        def action = new AddColumnsAction()
        column.table = tableName
        column.name = standardCaseObjectName("column_name", Column, scope.getDatabase())

        if (column.type != null && column.defaultValue == "WITH_DEFAULT_VALUE") {
            if (column.type.standardType == DataType.StandardType.INTEGER) {
                column.defaultValue = 3
            } else if (column.type.standardType  == DataType.StandardType.VARCHAR) {
                column.defaultValue = "test value"
            } else {
                Assert.fail("Unknown dataType: "+column.type)
            }
        }

        if (primaryKey == "Unnamed PK") {
            primaryKey = new PrimaryKey(null, tableName, column.name)
        } else if (primaryKey == "Named PK") {
            primaryKey = new PrimaryKey(standardCaseObjectName("test_pk", PrimaryKey, scope.getDatabase()), tableName, column.name)
        }

//        columnDef.addAfterColumn;
//        columnDef.addBeforeColumn;
//        columnDef.addAtPosition;
//        columnDef.constraints;

        action.primaryKey = primaryKey
        action.columns = [column]
        action.foreignKeys = foreignKeys.each {
            it.columnChecks.each {
                it.baseColumn = column.name
                it.referencedColumn.container.container = tableName.container //set referenced table to be in the same container as this test
            }
        }

        then:
        Assume.assumeTrue("Auto-increment does not apply to "+column.type, column.autoIncrementInformation == null || column.type.toString().toLowerCase().contains("int"))

        runStandardTest([
                type_asTable: column.type,
                defaultValue_asTable: column.defaultValue,
                remarks_asTable: column.remarks,
                primaryKey_asTable: primaryKey,
                isNullable_asTable: column.nullable,
                foreignKeys_asTable: foreignKeys,
                autoIncrementInformation_asTable: column.autoIncrementInformation,
        ], action, conn, scope)

        where:
        [conn, scope, column, primaryKey, foreignKeys] << connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    createAllPermutations(Column, [
                            type: [new DataType(DataType.StandardType.INTEGER), new DataType(DataType.StandardType.VARCHAR, 10)],
                            defaultValue: [null, "WITH_DEFAULT_VALUE"],
                            remarks: [null, "Remarks Here"],
                            nullable: [null, true, false],
                            autoIncrementInformation: [null, new Column.AutoIncrementInformation(), new Column.AutoIncrementInformation(2, 3)]
                    ]),
                    [null, "Unnamed PK", "Named PK"],
                    [[]] //, [new ForeignKey(null, [new Column.ColumnReference("this_col")], [new ObjectReference("other_table", "other_col")])]]

            ])
        }

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
            type = column.type
        }

        for (def fk : ((AddColumnsAction) action).foreignKeys) {
            for (def check : fk.columnChecks) {
                def refTableName = check.referencedColumn.container
                if (!seenTables.contains(refTableName)) {
                    snapshot.add(new Table(refTableName))
                }
                snapshot.add(new Column(check.referencedColumn.container, check.referencedColumn.name, type))
                snapshot.add(new PrimaryKey(null, check.referencedColumn.table, check.referencedColumn.name))
            }
        }
        return snapshot;
    }
}
