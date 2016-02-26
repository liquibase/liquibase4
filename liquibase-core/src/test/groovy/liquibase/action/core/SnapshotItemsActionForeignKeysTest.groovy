package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ObjectBasedQueryResult
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.snapshot.Snapshot
import liquibase.item.ItemNameStrategy
import liquibase.item.ItemReference
import liquibase.item.core.*
import liquibase.item.datatype.DataType
import liquibase.util.CollectionUtil
import spock.lang.Unroll

class SnapshotItemsActionForeignKeysTest extends AbstractActionTest {

    @Unroll("#featureName: #fkName in #schema on #conn")
    def "can find fully qualified complex foreign key names"() {
        def fkRef = new ForeignKeyReference(fkName, new RelationReference(Table, standardCaseItemName("test_table", Table, scope.database), schema))

        expect:
        def action = new SnapshotItemsAction(fkRef)

        testAction([
                schema_asTable: schema,
                fk_asTable: fkName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1;

            def foundFk = results.asObject(ForeignKey)

            def foundFKRef = foundFk.toReference()
            assert foundFKRef.equals(fkRef, true)
        })

        where:
        [conn, scope, fkName, schema] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemNames(ForeignKey, ItemNameStrategy.COMPLEX_NAMES, scope),
                    it.getAllSchemas(),
            ])
        }
    }

    @Unroll("#featureName: #tableName on #conn")
    def "can find all foreignKeys in a fully qualified complex table name"() {
        expect:
        def action = new SnapshotItemsAction(ForeignKey, tableName)

        testAction([
                table_asTable: tableName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 1; //found more than one object

            for (ForeignKey fk : results.asList(ForeignKey)) {
                assert fk.relation.equals(tableName, true)
            }

        })


        where:
        [conn, scope, tableName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    getItemReferences(Table, it.getAllSchemas(), ItemNameStrategy.COMPLEX_NAMES, scope),
            ])
        }
    }

    @Unroll("#featureName: #schemaName on #conn")
    def "can find all foreignKeys in a schema"() {
        expect:
        def action = new SnapshotItemsAction(ForeignKey, schemaName)

        testAction([
                schema_asTable: schemaName
        ], action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() > 1; //found more than one object

            for (ForeignKey fk : results.asList(ForeignKey)) {
                assert fk.relation.container.equals(schemaName, true)
            }

        })


        where:
        [conn, scope, schemaName] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)

            return okIfEmpty("May not support schema-only snapshotting", CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    it.allSchemas
            ], new ValidActionFilter(scope)))
        }
    }

    @Unroll("#featureName: #schema on #conn")
    def "Finds multi-column FKs correctly"() {
        expect:

        def table = new Table(standardCaseItemName("table_name", Table, scope.database), schema)
        def column1 = new Column(standardCaseItemName("col1", Column, scope.database), table.toReference(), DataType.parse("int"), true)
        def column2 = new Column(standardCaseItemName("col2", Column, scope.database), table.toReference(), DataType.parse("int"), true)
        def column3 = new Column(standardCaseItemName("col3", Column, scope.database), table.toReference(), DataType.parse("int"), true)

        def refTable = new Table(standardCaseItemName("ref_table_name", Table, scope.database), schema)
        def refColumn1 = new Column(standardCaseItemName("col1_ref", Column, scope.database), refTable.toReference(), DataType.parse("int"), true)
        def refColumn2 = new Column(standardCaseItemName("col2_ref", Column, scope.database), refTable.toReference(), DataType.parse("int"), true)
        def refColumn3 = new Column(standardCaseItemName("col3_ref", Column, scope.database), refTable.toReference(), DataType.parse("int"), true)

        def index = new Index(standardCaseItemName("ref_idx", Index, scope.database), refTable.toReference(), new Index.IndexedColumn(refColumn1.name), new Index.IndexedColumn(refColumn3.name))
        def fk = new ForeignKey("test_fk", table.toReference(), refTable.toReference(), [column1.name, column3.name], [refColumn1.name, refColumn3.name])

        def snapshot = new Snapshot(scope)
        snapshot.addAll([table, column1, column2, column3, refTable, refColumn1, refColumn2, refColumn3, index, fk])

        def action = new SnapshotItemsAction(fk.toReference())

        testAction([
                schema_asTable: schema
        ], snapshot, action, conn, scope, { plan, results ->
            assert results instanceof ObjectBasedQueryResult
            assert results.size() == 1; //found more than one object

            ForeignKey foundFk = results.asObject(ForeignKey)
            assert foundFk.relation.equals(table.toReference(), true)
            assert foundFk.columnChecks.size() == 2

            assert foundFk.columnChecks[0].baseColumn == column1.name
            assert foundFk.columnChecks[1].baseColumn == column3.name

            assert foundFk.referencedTable.name == refTable.name
            assert foundFk.referencedTable.container.equals(schema, true)

            assert foundFk.columnChecks[0].referencedColumn == refColumn1.name
            assert foundFk.columnChecks[1].referencedColumn == refColumn3.name

        })


        where:
        [conn, scope, schema] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
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

        def baseColumn1Name = standardCaseItemName("fk_id1", Column, scope.database)
        def refColumn1Name = standardCaseItemName("id1", Column, scope.database)

        def baseColumn2Name = standardCaseItemName("fk_id2", Column, scope.database)
        def refColumn2Name = standardCaseItemName("id2", Column, scope.database)

        def schema = null;
        for (ItemReference relatedTo : ((SnapshotItemsAction) action).relatedTo) {
            if (relatedTo.instanceOf(ForeignKey)) {
                if (schema == null) {
                    schema = relatedTo.container.container
                }
                snapshot.add(new Table(relatedTo.container.name, schema))
                snapshot.add(new Column(baseColumn1Name, relatedTo.container, DataType.parse("int"), true))
                snapshot.add(new Column(baseColumn2Name, relatedTo.container, DataType.parse("int"), true))

                def refTable = new RelationReference(Table, standardCaseItemName(relatedTo.container.name + "_ref", Table, scope.database), schema)
                snapshot.add(new Table(refTable.name, refTable.container))
                snapshot.add(new Column(refColumn1Name, refTable, DataType.parse("int"), true))
                snapshot.add(new Column(refColumn2Name, refTable, DataType.parse("int"), true))
                snapshot.add(new Index(standardCaseItemName("idx1_"+refTable.name, Index, scope.database), refTable, new Index.IndexedColumn(refColumn1Name)))
                snapshot.add(new Index(standardCaseItemName("idx2_"+refTable.name, Index, scope.database), refTable, new Index.IndexedColumn(refColumn2Name)))
                snapshot.add(new ForeignKey(relatedTo.name, relatedTo.container, refTable, [baseColumn1Name], [refColumn1Name]))
                snapshot.add(new ForeignKey(null, relatedTo.container, refTable, [baseColumn2Name], [refColumn2Name]))
            } else if (relatedTo.instanceOf(Relation)) {
                if (schema == null) {
                    schema = relatedTo.container
                }
                snapshot.add(new Table(relatedTo.name, relatedTo.container))
                snapshot.add(new Column(baseColumn1Name, relatedTo, DataType.parse("int"), true))
                snapshot.add(new Column(baseColumn2Name, relatedTo, DataType.parse("int"), true))

                def refTableName = new RelationReference(Table, standardCaseItemName(relatedTo.name + "_ref", Table, scope.database), relatedTo.container)
                snapshot.add(new Table(refTableName.name, refTableName.container))
                snapshot.add(new Column(refColumn1Name, refTableName, DataType.parse("int"), true))
                snapshot.add(new Column(refColumn2Name, refTableName, DataType.parse("int"), true))
                snapshot.add(new Index(standardCaseItemName("idx1_$refTableName.name", Index, scope.database), refTableName, new Index.IndexedColumn( refColumn1Name)))
                snapshot.add(new Index(standardCaseItemName("idx2_$refTableName.name", Index, scope.database), refTableName, new Index.IndexedColumn( refColumn2Name)))
                snapshot.add(new ForeignKey(standardCaseItemName("fk1_" + relatedTo, ForeignKey, scope.database), relatedTo, refTableName, [baseColumn1Name], [refColumn1Name]))
                snapshot.add(new ForeignKey(standardCaseItemName("fk2_" + relatedTo, ForeignKey, scope.database), relatedTo, refTableName, [baseColumn2Name], [refColumn2Name]))
            } else if (relatedTo.instanceOf(Schema)) {
                schema = relatedTo
            }
        }

        for (int i=0; i<5; i++) {
            def baseTable = new RelationReference(Table, standardCaseItemName("test_table_$i", Table, scope.database), schema)
            def refTable = new RelationReference(Table, standardCaseItemName("ref_table_$i", Table, scope.database), schema)

            snapshot.add(new Table(baseTable.name, baseTable.container))
            snapshot.add(new Column(baseColumn1Name, baseTable, DataType.parse("int"), true))

            snapshot.add(new Table(refTable.name, refTable.container))
            snapshot.add(new Column(refColumn1Name, refTable, DataType.parse("int"), true))
            snapshot.add(new Index(standardCaseItemName("ref_idx_$i", Index, scope.database), refTable, new Index.IndexedColumn(refColumn1Name)))
            snapshot.add(new ForeignKey(standardCaseItemName("fk_" + baseTable.name, ForeignKey, scope.database), baseTable, refTable, [baseColumn1Name], [refColumn1Name]))
        }

        return snapshot
    }
}
