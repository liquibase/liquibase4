package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ActionExecutor
import liquibase.actionlogic.ObjectBasedQueryResult
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.exception.ValidationErrors
import liquibase.snapshot.Snapshot
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.ObjectReference
import liquibase.structure.core.*
import liquibase.util.CollectionUtil
import org.junit.Assume
import spock.lang.Unroll

class SnapshotObjectsActionPrimaryKeysTest extends AbstractActionTest {

    @Unroll("#featureName: #pkRef on #conn")
    def "can find complex pk names without a table"() {
        expect:
        def action = new SnapshotObjectsAction(pkRef)

        runStandardTest([
                pkName_asTable: pkRef
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundPk = results.asObject(PrimaryKey)

            def foundPkRef = foundPk.toReference()
            assert foundPkRef.equals(pkRef, true)
            assert foundPk.columns.size() > 0
        })

        where:
        [conn, scope, pkRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            def pkNames = getObjectNames(PrimaryKey, ObjectNameStrategy.COMPLEX_NAMES, scope).collect({ it.container.name = null; return it })

            def validationErrors = scope.getSingleton(ActionExecutor).validate(new SnapshotObjectsAction(pkNames.get(0)), scope)

            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    pkNames,
            ])
        }
    }

    @Unroll("#featureName: #pkRef on #conn")
    def "can find complex pk names with a table"() {
        expect:
        def action = new SnapshotObjectsAction(pkRef)

        runStandardTest([
                pkName_asTable: pkRef
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundPk = results.asObject(PrimaryKey)

            def foundPkRef = foundPk.toReference()
            assert foundPkRef.equals(pkRef, true)
            assert foundPk.columns.size() > 0
        })

        where:
        [conn, scope, pkRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            def pkNames = getObjectNames(PrimaryKey, ObjectNameStrategy.COMPLEX_NAMES, scope).collect({ it.container.name = standardCaseObjectName("known_table", Table, scope.getDatabase()); return it })

            def validationErrors = scope.getSingleton(ActionExecutor).validate(new SnapshotObjectsAction(pkNames.get(0)), scope)

            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    pkNames,
            ])
        }
    }

    @Unroll("#featureName: #pkRef on #conn")
    def "can find by PrimaryKeyReference with a table name but null primary key name"() {
        expect:
        def action = new SnapshotObjectsAction(pkRef)

        runStandardTest([
                pkName_asTable: pkRef
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundPk = results.asObject(PrimaryKey)

            def foundPkRef = foundPk.toReference()
            assert foundPkRef.equals(pkRef, true)
            assert foundPk.columns.size() > 0
        })

        where:
        [conn, scope, pkRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope).collect({ new PrimaryKey.PrimaryKeyReference(it, null) }),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all primaryKeys in a fully qualified complex table name"() {
        expect:
        def action = new SnapshotObjectsAction(PrimaryKey, tableName)

        runStandardTest([
                tableName_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;


            def foundPk = results.asObject(PrimaryKey)
            assert foundPk.table.equals(tableName, true)
            assert foundPk.columns.size() > 0
        })


        where:
        [conn, scope, tableName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all primaryKeys related to a table with a null name"() {
        expect:
        def action = new SnapshotObjectsAction(PrimaryKey, tableName)

        runStandardTest([
                tableName_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0;


            for (def foundPk : results.asList(PrimaryKey)) {
                assert foundPk.table.equals(tableName, true)
                assert foundPk.columns.size() > 0
            }
        })


        where:
        [conn, scope, tableName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    getObjectNames(Schema, ObjectNameStrategy.COMPLEX_NAMES, scope).collect({ return new ObjectReference(Table, it, null) }),
            ])
        }
    }

    @Unroll("#featureName: #schemaName on #conn")
    def "can find all primaryKeys related to a schema"() {
        expect:
        def action = new SnapshotObjectsAction(PrimaryKey, schemaName)

        runStandardTest([
                schemaName_asTable: schemaName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 0;


            for (def foundPk : results.asList(PrimaryKey)) {
                assert foundPk.table.container.equals(schemaName, true)
                assert foundPk.columns.size() > 0
            }
        })


        where:
        [conn, scope, schemaName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    getObjectNames(Schema, scope),
            ])
        }
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)

        Set<String> addedPrimaryKeys = new HashSet<>();

        //Crate the expected PK/table combo
        for (ObjectReference relatedTo : ((SnapshotObjectsAction) action).relatedTo) {
            if (relatedTo.instanceOf(PrimaryKey) && relatedTo.name != null && relatedTo.container.name != null) {
                def columnName = standardCaseObjectName("id", Column, scope.database)

                snapshot.add(new Table(relatedTo.container))
                snapshot.add(new Column(relatedTo.container, columnName, "int"))
                snapshot.add(new PrimaryKey(relatedTo.name, relatedTo.container, columnName))
                addedPrimaryKeys.add(relatedTo.name)

            }
        }

        int i = 0;
        //create generated-named PKs on complex object names
        for (ObjectReference tableName : getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope)) {
            i = i + 1;
            def columnName = standardCaseObjectName("id", Column, scope.database)
            snapshot.add(new Table(tableName))
            snapshot.add(new Column(tableName, columnName, "int"))
            snapshot.add(new PrimaryKey(standardCaseObjectName("pk_test_" + i, PrimaryKey, scope.database), tableName, columnName))
        }

        //create complex-named PKs on generated object names
        i = 0;
        for (ObjectReference pkName : getObjectNames(PrimaryKey, ObjectNameStrategy.COMPLEX_NAMES, scope)) {
            if (addedPrimaryKeys.contains(pkName.name)) {
                continue
            }

            i = i + 1;
            def tableName = new ObjectReference(Table, pkName.container.container, standardCaseObjectName("table_" + i, Table, scope.database))
            def column = new Column(tableName, standardCaseObjectName("id", Column, scope.database), "int");

            snapshot.add(new Table(tableName))
            snapshot.add(column)
            snapshot.add(new PrimaryKey(pkName.name, tableName, column.name))
        }

        return snapshot
    }
}
