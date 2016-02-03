package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ObjectBasedQueryResult
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.snapshot.Snapshot
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.ObjectReference
import liquibase.structure.core.*
import liquibase.structure.datatype.DataType
import liquibase.util.CollectionUtil
import spock.lang.Unroll

class SnapshotObjectsActionForeignKeysTest extends AbstractActionTest {

    @Unroll("#featureName: #fkRef in #schemaName on #conn")
    def "can find fully qualified complex foreign key names"() {
        when:
        fkRef.container = new ObjectReference(Table, schemaName, standardCaseObjectName("test_table", Table, scope.database))

        then:
        def action = new SnapshotObjectsAction(fkRef)

        testAction([
                fkName_asTable: fkRef
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundFk = results.asObject(ForeignKey)

            def foundFKRef = foundFk.toReference()
            assert foundFKRef.equals(fkRef, true)
        })

        where:
        [conn, scope, fkRef, schemaName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getObjectNames(ForeignKey, ObjectNameStrategy.COMPLEX_NAMES, scope),
                    getObjectNames(Schema, scope),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all foreignKeys in a fully qualified complex table name"() {
        expect:
        def action = new SnapshotObjectsAction(ForeignKey, tableName)

        testAction([
                tableName_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 1; //found more than one object

            for (ForeignKey fk : results.asList(ForeignKey)) {
                assert fk.table.equals(tableName, true)
            }

        })


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

    @Unroll("#featureName: #schemaName on #conn")
    def "can find all foreignKeys in a schema"() {
        expect:
        def action = new SnapshotObjectsAction(ForeignKey, schemaName)

        testAction([
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

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.allSchemas
            ])
        }
    }

    @Unroll("#featureName: #schemaName on #conn")
    def "Finds multi-column FKs correctly"() {
        expect:

        def table = new Table(schemaName, standardCaseObjectName("table_name", Table, scope.database))
        def column1 = new Column(table.toReference(), standardCaseObjectName("col1", Column, scope.database), "int")
        def column2 = new Column(table.toReference(), standardCaseObjectName("col2", Column, scope.database), "int")
        def column3 = new Column(table.toReference(), standardCaseObjectName("col3", Column, scope.database), "int")

        def refTable = new Table(schemaName, standardCaseObjectName("ref_table_name", Table, scope.database))
        def refColumn1 = new Column(refTable.toReference(), standardCaseObjectName("col1_ref", Column, scope.database), "int")
        def refColumn2 = new Column(refTable.toReference(), standardCaseObjectName("col2_ref", Column, scope.database), "int")
        def refColumn3 = new Column(refTable.toReference(), standardCaseObjectName("col3_ref", Column, scope.database), "int")

        def index = new Index("ref_idx", new Index.IndexedColumn(refColumn1.toReference()), new Index.IndexedColumn(refColumn3.toReference()))
        def fk = new ForeignKey(table.toReference(), refTable.toReference(), "test_fk", [column1.name, column3.name], [refColumn1.name, refColumn3.name])

        def snapshot = new Snapshot(scope)
        snapshot.addAll([table, column1, column2, column3, refTable, refColumn1, refColumn2, refColumn3, index, fk])

        def action = new SnapshotObjectsAction(fk.toReference())

        testAction([
                schemaName_asTable: schemaName
        ], snapshot, action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1; //found more than one object

            ForeignKey foundFk = results.asObject(ForeignKey)
            assert foundFk.table.equals(table.toReference(), true)
            assert foundFk.columnChecks.size() == 2

            assert foundFk.columnChecks[0].baseColumn == column1.name
            assert foundFk.columnChecks[1].baseColumn == column3.name

            assert foundFk.referencedTable.name == refTable.name
            assert foundFk.referencedTable.container.equals(schemaName, true)

            assert foundFk.columnChecks[0].referencedColumn == refColumn1.name
            assert foundFk.columnChecks[1].referencedColumn == refColumn3.name

        })


        where:
        [conn, scope, schemaName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.allSchemas
            ])
        }
    }

    @Override
    def createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        return null;
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)

        def baseColumn1Name = standardCaseObjectName("fk_id1", Column, scope.database)
        def refColumn1Name = standardCaseObjectName("id1", Column, scope.database)

        def baseColumn2Name = standardCaseObjectName("fk_id2", Column, scope.database)
        def refColumn2Name = standardCaseObjectName("id2", Column, scope.database)

        def schema = null;
        for (ObjectReference relatedTo : ((SnapshotObjectsAction) action).relatedTo) {
            if (relatedTo.instanceOf(ForeignKey)) {
                if (schema == null) {
                    schema = relatedTo.container.container
                }
                snapshot.add(new Table(relatedTo.container))
                snapshot.add(new Column(relatedTo.container, baseColumn1Name, "int"))
                snapshot.add(new Column(relatedTo.container, baseColumn2Name, "int"))

                def refTableName = new ObjectReference(Table, relatedTo.container.container, standardCaseObjectName(relatedTo.container.name + "_ref", Table, scope.database))
                snapshot.add(new Table(refTableName))
                snapshot.add(new Column(refTableName, refColumn1Name, "int"))
                snapshot.add(new Column(refTableName, refColumn2Name, "int"))
                snapshot.add(new Index(standardCaseObjectName("idx1_"+refTableName.name, Index, scope.database), new Index.IndexedColumn(refTableName, refColumn1Name)))
                snapshot.add(new Index(standardCaseObjectName("idx2_"+refTableName.name, Index, scope.database), new Index.IndexedColumn(refTableName, refColumn2Name)))
                snapshot.add(new ForeignKey(relatedTo.container, refTableName, relatedTo.name, [baseColumn1Name], [refColumn1Name]))
                snapshot.add(new ForeignKey(relatedTo.container, refTableName, null, [baseColumn2Name], [refColumn2Name]))
            } else if (relatedTo.instanceOf(Table)) {
                if (schema == null) {
                    schema = relatedTo.container
                }
                snapshot.add(new Table(relatedTo))
                snapshot.add(new Column(relatedTo, baseColumn1Name, "int"))
                snapshot.add(new Column(relatedTo, baseColumn2Name, "int"))

                def refTableName = new ObjectReference(Table, relatedTo.container, standardCaseObjectName(relatedTo.name + "_ref", Table, scope.database))
                snapshot.add(new Table(refTableName))
                snapshot.add(new Column(refTableName, refColumn1Name, "int"))
                snapshot.add(new Column(refTableName, refColumn2Name, "int"))
                snapshot.add(new Index(standardCaseObjectName("idx1_$refTableName.name", Index, scope.database), new Index.IndexedColumn(refTableName, refColumn1Name)))
                snapshot.add(new Index(standardCaseObjectName("idx2_$refTableName.name", Index, scope.database), new Index.IndexedColumn(refTableName, refColumn2Name)))
                snapshot.add(new ForeignKey(relatedTo, refTableName, standardCaseObjectName("fk1_"+relatedTo, ForeignKey, scope.database), [baseColumn1Name], [refColumn1Name]))
                snapshot.add(new ForeignKey(relatedTo, refTableName, standardCaseObjectName("fk2_"+relatedTo, ForeignKey, scope.database), [baseColumn2Name], [refColumn2Name]))
            } else if (relatedTo.instanceOf(Schema)) {
                schema = relatedTo
            }
        }

        for (int i=0; i<5; i++) {
            def baseTable = new ObjectReference(Table, schema, standardCaseObjectName("test_table_$i", Table, scope.database))
            def refTable = new ObjectReference(Table, schema, standardCaseObjectName("ref_table_$i", Table, scope.database))

            snapshot.add(new Table(baseTable))
            snapshot.add(new Column(baseTable, baseColumn1Name, "int"))

            snapshot.add(new Table(refTable))
            snapshot.add(new Column(refTable, refColumn1Name, "int"))
            snapshot.add(new Index(standardCaseObjectName("ref_idx_$i", Index, scope.database), new Index.IndexedColumn(refTable, refColumn1Name)))
            snapshot.add(new ForeignKey(baseTable, refTable, standardCaseObjectName("fk_"+baseTable.name, ForeignKey, scope.database), [baseColumn1Name], [refColumn1Name]))
        }

        return snapshot
    }
}
