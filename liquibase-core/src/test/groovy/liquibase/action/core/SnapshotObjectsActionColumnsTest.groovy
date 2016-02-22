package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ActionExecutor
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.database.Database
import liquibase.snapshot.Snapshot
import liquibase.snapshot.SnapshotFactory
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.ObjectReference
import liquibase.structure.core.Catalog
import liquibase.structure.core.Column
import liquibase.structure.core.PrimaryKey
import liquibase.structure.core.Table
import liquibase.structure.datatype.DataType
import liquibase.util.CollectionUtil
import org.junit.Assume
import spock.lang.Unroll

class SnapshotObjectsActionColumnsTest extends AbstractActionTest {

    @Unroll("#featureName: #columnRef on #conn")
    def "can find fully qualified complex column names"() {
        expect:
        def action = new SnapshotObjectsAction(Column, columnRef)

        testAction([columnName_asTable: columnRef.toString()], action, conn, scope, {
            plan, result ->
                assert result.asList(Column).size() == 1
                assert result.asObject(Object) instanceof Column
                assert result.asObject(Column).toReference() == columnRef
        })

        where:
        [conn, scope, columnRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getColumnNamesWithTables(scope)
            ])
        }
    }

    @Unroll("#featureName: #tableRef on #conn")
    def "can find all columns in a fully qualified complex table name"() {
        expect:
        def action = new SnapshotObjectsAction(Column, tableRef)

        testAction([tableName_asTable: tableRef], action, conn, scope, {
            plan, result ->
                assert result.asList(Column).size() > 0
                result.asList(Object).each {
                    assert it instanceof Column;
                    assert it.table == tableRef
                }
        })

        where:
        [conn, scope, tableRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope)
            ])
        }
    }

    @Unroll("#featureName: #schemaRef on #conn")
    def "can find all columns in a schema"() {
        expect:
        def action = new SnapshotObjectsAction(Column, schemaRef)

        testAction([schemaName_asTable: schemaRef], action, conn, scope, {
            plan, result ->
                assert result.asList(Column).size() > 0
                result.asList(Object).each {
                    assert it instanceof Column;
                    assert it.table.container.equals(schemaRef, true)
                }
        })

        where:
        [conn, scope, schemaRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.allSchemas
            ])
        }
    }

    @Unroll("#featureName: #catalogRef on #conn")
    def "can find all columns in a catalog"() {
        expect:
        def action = new SnapshotObjectsAction(Column, catalogRef)

        testAction([catalogName_asTable: catalogRef], action, conn, scope, {
            plan, result ->
                assert result.asList(Column).size() > 0
                result.asList(Object).each {
                    assert it instanceof Column;
                    assert it.table.container.container.name.equals(catalogRef.name)
                }
        })

        where:
        [conn, scope, catalogRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            Assume.assumeTrue("Database does not support catalogs", it.database.supports(Catalog, scope));

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.allSchemas*.container.unique(),
            ])
        }
    }

    @Unroll("#featureName: #columnRef (autoIncrement: #autoIncrement) on #conn")
    def "autoIncrement information set correctly"() {
        expect:
        def action = new SnapshotObjectsAction(columnRef)

        testAction([columnName_asTable: columnRef, autoIncrement_asTable: autoIncrement], action, conn, scope, {
            plan, result ->
                assert result.asList(Column).size() > 0
                result.asList(Object).each {
                    assert it instanceof Column;
                    assert it.toReference() == columnRef
                    if (autoIncrement) {
                        assert it.autoIncrement
                        //no jdbc interface to get auto increment start/incrementBy info
                    } else {
                        assert !it.autoIncrement
                    }
                }
        }, {
            def executor = scope.getSingleton(ActionExecutor)
            if (autoIncrement) {
                if (((AddAutoIncrementActionTest.TestDetails) new AddAutoIncrementActionTest().getTestDetails(scope)).createPrimaryKeyBeforeAutoIncrement()) {
                    executor.execute(new AddPrimaryKeysAction(new PrimaryKey(null, columnRef.relation, columnRef.name)), scope)
                }
                executor.execute(new AddAutoIncrementAction(columnRef, DataType.parse("int"), new Column.AutoIncrementInformation()), scope)
            }
        })

        where:
        [conn, scope, autoIncrement, columnRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            Assume.assumeTrue("Database does not support autoIncrement", it.database.supports(Database.Feature.AUTO_INCREMENT, (Scope) scope));

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    [true, false],
                    getColumnNamesWithTables(scope),
            ])
        }
    }

    @Unroll("#featureName: #typeString on #conn")
    def "dataType comes through correctly"() {
        when:
        def schema = conn.getAllSchemas()[0]
        def tableName = standardCaseObjectName("testtable", Table, conn.database)
        def columnName = standardCaseObjectName("testcol", Column, conn.database)
        def type = DataType.parse(typeString)

        def snapshot = new Snapshot(scope)

        def column = new Column(new ObjectReference(Table, schema, tableName), columnName, type)
        snapshot.add(column)
        snapshot.add(new Table(new ObjectReference(Table, schema, tableName)))

        then:
        def action = new SnapshotObjectsAction(column.toReference())

        testAction([columnName_asTable: column.getName(), type_asTable: ((Column) column).type], snapshot, action, conn, scope, {
            plan, result ->
                assert result.asList(Column).size() == 1
                def snapshotColumn = result.asObject(Column)
                assert snapshotColumn.name == columnName

                //do some minor checks based on things that are always consistent
                if (typeString == "int") {
                    assert snapshotColumn.type.toString().toLowerCase().startsWith("int")
                } else if (typeString == "varchar(10)") {
                    assert snapshotColumn.type.toString().toLowerCase().startsWith("varchar") && snapshotColumn.type.toString().contains("(10")
                }

                //since data types change to what the database thinks, test by adding a new columnRef with the snapshot's datatype and check that those are consistant
                def addColumnsAction = new AddColumnsAction()
                def columnToAdd = snapshotColumn.clone() as Column
                columnToAdd.name = snapshotColumn.name + "_added"
                addColumnsAction.columns = [columnToAdd]

                scope.getSingleton(ActionExecutor).execute(addColumnsAction, scope)

                def newColumnSnapshot = scope.getSingleton(SnapshotFactory).snapshot(Column, columnToAdd.toReference(), scope)

                assert snapshotColumn.type.toString() != null
                assert snapshotColumn.type.toString() == newColumnSnapshot.type.toString()
        })

        where:
        [conn, scope, typeString] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getDataTypesToTest()
            ])
        }
    }

    @Unroll("#featureName: #typeAndValue on #conn")
    def "defaultValue comes through correctly"() {
        when:
        def typeString = typeAndValue[0]
        def defaultValue = typeAndValue[1]

        def schema = conn.getAllSchemas()[0]
        def tableName = standardCaseObjectName("testtable", Table, conn.database)
        def columnName = standardCaseObjectName("testcol", Column, conn.database)
        def type = DataType.parse(typeString)

        def snapshot = new Snapshot(scope)

        def column = new Column(new Column.ColumnReference(schema, tableName), columnName, type)
        column.defaultValue = defaultValue
        snapshot.add(column)
        snapshot.add(new Table(new ObjectReference(Table, schema, tableName)))

        then:
        def action = new SnapshotObjectsAction(column.toReference())

        testAction([type_asTable: column.getName(), defaultValue_asTable: ((Column) column).type], snapshot, action, conn, scope, {
            plan, result ->
                assert result.asList(Column).size() == 1
                def snapshotColumn = result.asObject(Column)
                assert snapshotColumn.name == columnName

                assert snapshotColumn.defaultValue == defaultValue
        })

        where:
        [conn, scope, typeAndValue] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    [
                            ["int", 3],
                            ["varchar(20)", "A test varchar"]
                    ]
            ])
        }
    }

    protected ArrayList<String> getDataTypesToTest() {
        ["int", "bigint", "smallint", "varchar(10)", "float", "double"]
    }

    @Override
    def createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return null;
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        for (ObjectReference tableName : getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope)) {
            snapshot.add(new Table(tableName))
            for (ObjectReference columnName : getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope)) {
                snapshot.add(new Column(tableName, columnName.name, "int"))
            }
        }

        return snapshot
    }

    List<Column.ColumnReference> getColumnNamesWithTables(Scope scope) {
        getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope)*.name.unique().collectMany {
            def colName = it
            return getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope).collect {
                return new Column.ColumnReference(it, colName)
            }
        }
    }


}
