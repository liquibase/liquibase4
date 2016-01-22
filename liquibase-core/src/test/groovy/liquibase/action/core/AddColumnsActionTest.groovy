package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.exception.UnexpectedLiquibaseException
import liquibase.snapshot.Snapshot
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.ObjectReference
import liquibase.structure.core.Column
import liquibase.structure.core.ForeignKey
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
        testAction([
                columnName_asTable: columnName.toString()
        ], action, conn, scope)

        where:
        [conn, scope, columnName, tableName] << connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
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
        testAction([
                tableName_asTable  : tableName.toString(),
                columnNames_asTable: columnNames.toString()
        ], action, conn, scope)

        where:
        [conn, scope, columnNames, tableName] << connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope).collect { [it.name + "_2", it.name + "_3"] },
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope)
            ])
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "Can add single column with various PK settings"() {
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
                primaryKey_asTable              : action.primaryKeys,
                isNullable_asTable              : action.columns*.nullable,
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
        def tableRef = new ObjectReference(Table, tableName);
        def columnName = standardCaseObjectName("column_name", Column, scope.getDatabase())

        return createAllPermutations(AddColumnsAction, [
                columns   : CollectionUtil.toSingletonLists(createAllPermutations(Column, [
                        name                    : [columnName],
                        table                   : [tableRef],
                        type                    : [new DataType(DataType.StandardType.INTEGER), new DataType(DataType.StandardType.VARCHAR, 10)],
                        defaultValue            : ["WITH_DEFAULT_VALUE"],
                        remarks                 : ["Remarks Here"],
                        nullable                : [true, false],
                        autoIncrementInformation: [new Column.AutoIncrementInformation(), new Column.AutoIncrementInformation(2, 3)]
                ]).each({
                    if (it.type != null && it.defaultValue == "WITH_DEFAULT_VALUE") {
                        if (it.type.standardType == DataType.StandardType.INTEGER) {
                            it.defaultValue = 3
                        } else if (it.type.standardType == DataType.StandardType.VARCHAR) {
                            it.defaultValue = "test value"
                        } else {
                            throw new RuntimeException("Unknown dataType: " + column.type)
                        }
                    }
                })),
                primaryKeys: [[], [new PrimaryKey(null, tableRef, columnName)], [new PrimaryKey(standardCaseObjectName("pk_name", PrimaryKey, scope.database), tableRef, columnName)]],

        ])
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
