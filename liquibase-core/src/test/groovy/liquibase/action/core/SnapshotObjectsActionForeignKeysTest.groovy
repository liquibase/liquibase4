package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ObjectBasedQueryResult
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.snapshot.Snapshot
import liquibase.structure.*
import liquibase.structure.core.*
import liquibase.structure.datatype.DataType
import liquibase.util.CollectionUtil
import spock.lang.Unroll

class SnapshotObjectsActionForeignKeysTest extends AbstractActionTest {

    @Unroll("#featureName: #fkRef on #conn")
    def "can find fully qualified complex foreign key names"() {
        expect:
        def action = new SnapshotObjectsAction(fkRef)

        runStandardTest([
                fkName_asTable: fkRef
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundFk = results.asObject(ForeignKey)

            def foundFKRef = foundFk.toReference()
            assert foundFKRef.equals(fkRef, true)
        })

        where:
        [conn, scope, fkRef] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    getObjectNames(ForeignKey, ObjectNameStrategy.COMPLEX_NAMES, scope),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all foreignKeys in a fully qualified complex table name"() {
        expect:
        def action = new SnapshotObjectsAction(ForeignKey, tableName)

        runStandardTest([
                tableName_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 1; //found more than one object

            for (ForeignKey fk : results.asList(ForeignKey)) {
                assert fk.columnChecks.get(0).baseColumn.container.equals(tableName, true)
            }

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

    @Unroll("#featureName: #schemaName on #conn")
    def "can find all foreignKeys in a schema"() {
        expect:
        def action = new SnapshotObjectsAction(ForeignKey, schemaName)

        runStandardTest([
                schemaName_asTable: schemaName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 1; //found more than one object

            for (ForeignKey fk : results.asList(ForeignKey)) {
                assert fk.table.container.equals(schemaName, true)
            }

        })


        where:
        [conn, scope, schemaName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutations([
                    [it],
                    [scope],
                    it.allSchemas
            ])
        }
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        int i = 0;
        //create generated-named FKs on complex object names
        for (ObjectReference tableName : getObjectNames(Table, ObjectNameStrategy.COMPLEX_NAMES, scope)) {
            ObjectReference refTableName = (ObjectReference) tableName.clone()
            refTableName.name = refTableName.name+"_REF"
            if (tableName.name.matches(/[a-z\\d]+/)) { //keep it lower case
                refTableName.name = tableName.name+"_ref"
            }

            snapshot.add(new Table(tableName))
            snapshot.add(new Table(refTableName))
            for (ObjectReference columnName : getObjectNames(Column, ObjectNameStrategy.COMPLEX_NAMES, scope)) {
                i = i + 1;
                def refColumnName = columnName.name+"_REF"
                if (columnName.name.matches(/[a-z\\d]+/)) { //keep it lower case
                    refColumnName = columnName.name+"_ref"
                }
                snapshot.add(new Column(tableName, columnName.name, "int"))
                snapshot.add(new Column(refTableName, refColumnName, "int"))
                snapshot.add(new Index(standardCaseObjectName("idx_"+i, Index, scope.getDatabase()), new Index.IndexedColumn(refTableName, refColumnName)))
                snapshot.add(new ForeignKey(tableName, standardCaseObjectName("fk_test_"+i, ForeignKey, scope.database), [new Column.ColumnReference(tableName, columnName.name)], [new Column.ColumnReference(refTableName, refColumnName)]))
            }
        }

        //create complex-named FKs on generated object names
        i=0;
        for (ObjectReference fkName : getObjectNames(ForeignKey, ObjectNameStrategy.COMPLEX_NAMES, scope)) {
            i = i + 1;
            def tableName = new ObjectReference(Table, fkName.container.container, standardCaseObjectName("table_"+i, Table, scope.database))
            def refTableName = new ObjectReference(Table, fkName.container.container, standardCaseObjectName("table_"+i+"_ref", Table, scope.database))

            def column = new Column(tableName, standardCaseObjectName("id_ref", Column, scope.database), "int");
            def refColumn = new Column(refTableName, standardCaseObjectName("id", Column, scope.database), DataType.parse("int"), false);

            snapshot.add(new Table(tableName))
            snapshot.add(new Table(refTableName))
            snapshot.add(column)
            snapshot.add(refColumn)
            snapshot.add(new ForeignKey(tableName, fkName.name, [column.toReference()], [refColumn.toReference()]))
            snapshot.add(new PrimaryKey(null, refTableName, refColumn.name))
        }

        return snapshot
    }
}
