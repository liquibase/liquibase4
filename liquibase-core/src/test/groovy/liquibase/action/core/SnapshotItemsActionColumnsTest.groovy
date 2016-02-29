package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ActionExecutor
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.database.Database
import liquibase.item.TestItemSupplier
import liquibase.snapshot.Snapshot
import liquibase.snapshot.SnapshotFactory

import liquibase.item.ItemReference
import liquibase.item.core.Catalog
import liquibase.item.core.Column
import liquibase.item.core.ColumnReference
import liquibase.item.core.PrimaryKey
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.util.CollectionUtil
import org.junit.Assume
import spock.lang.Unroll

class SnapshotItemsActionColumnsTest extends AbstractActionTest {

    @Unroll("#featureName: #columnRef on #conn")
    def "can find fully qualified complex column names"() {
        expect:
        def action = new SnapshotItemsAction(Column, columnRef)

        testAction([column_asTable: columnRef.toString()], action, conn, scope, {
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
                    getColumnNamesWithTables(it, scope)
            ])
        }
    }

    @Unroll("#featureName: #tableRef on #conn")
    def "can find all columns in a fully qualified complex table name"() {
        expect:
        def action = new SnapshotItemsAction(Column, tableRef)

        testAction([table_asTable: tableRef], action, conn, scope, {
            plan, result ->
                assert result.asList(Column).size() > 0
                result.asList(Object).each {
                    assert it instanceof Column;
                    assert it.relation == tableRef
                }
        })

        where:
        [conn, scope, tableRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemReferences(Table, it.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope)
            ])
        }
    }

    @Unroll("#featureName: #schemaRef on #conn")
    def "can find all columns in a schema"() {
        expect:
        def action = new SnapshotItemsAction(Column, schemaRef)

        testAction([schema_asTable: schemaRef], action, conn, scope, {
            plan, result ->
                assert result.asList(Column).size() > 0
                result.asList(Object).each {
                    assert it instanceof Column;
                    assert it.relation.container.equals(schemaRef, true)
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
        def action = new SnapshotItemsAction(Column, catalogRef)

        testAction([catalog_asTable: catalogRef], action, conn, scope, {
            plan, result ->
                assert result.asList(Column).size() > 0
                result.asList(Object).each {
                    assert it instanceof Column;
                    assert it.relation.container.container.name.equals(catalogRef.name)
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
        def action = new SnapshotItemsAction(columnRef)

        testAction([column_asTable: columnRef, autoIncrement_asTable: autoIncrement], action, conn, scope, {
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
                    getColumnNamesWithTables(it, scope),
            ])
        }
    }

    @Unroll("#featureName: #typeString on #conn")
    def "dataType comes through correctly"() {
        when:
        def schema = conn.getAllSchemas()[0]
        def tableName = standardCaseItemName("testtable", Table, scope)
        def columnName = standardCaseItemName("testcol", Column, scope)
        def type = DataType.parse(typeString)

        def snapshot = new Snapshot(scope)

        def column = new Column(columnName, new RelationReference(Table, tableName, schema), type, true)
        snapshot.add(column)
        snapshot.add(new Table(tableName, schema))

        then:
        def action = new SnapshotItemsAction(column.toReference())

        testAction([column_asTable: column.getName(), type_asTable: ((Column) column).type], snapshot, action, conn, scope, {
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

                //since data types change to what the database thinks, test by adding a new columnRef with the snapshot's datatype and check that those are consistent
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
        def tableName = standardCaseItemName("testtable", Table, scope)
        def columnName = standardCaseItemName("testcol", Column, scope)
        def type = DataType.parse(typeString)

        def snapshot = new Snapshot(scope)

        def column = new Column(columnName, new RelationReference(Table.class, tableName, schema), type, true)
        column.defaultValue = defaultValue
        snapshot.add(column)
        snapshot.add(new Table(tableName, schema))

        then:
        def action = new SnapshotItemsAction(column.toReference())

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
        for (ItemReference tableName : getItemReferences(Table, connectionSupplier.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope)) {
            snapshot.add(new Table(tableName.name, tableName.container))
            for (def columnName : getItemNames(Column, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope)) {
                snapshot.add(new Column(columnName, tableName, DataType.parse("int"), true))
            }
        }

        return snapshot
    }

    List<ColumnReference> getColumnNamesWithTables(ConnectionSupplier connectionSupplier, Scope scope) {
        getItemNames(Column, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope).collectMany {
            def colName = it
            return getItemReferences(Table, connectionSupplier.getAllSchemas(), TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope).collect {
                return new ColumnReference(colName, it)
            }
        }
    }
}
