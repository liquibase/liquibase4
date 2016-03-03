package liquibase.action.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.item.TestItemSupplier
import liquibase.item.core.Column
import liquibase.item.core.Index
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.snapshot.Snapshot
import liquibase.util.CollectionUtil
import liquibase.util.TestUtil
import spock.lang.Unroll

class CreateIndexesActionTest extends AbstractActionTest {

    @Unroll("#featureName: #action on #conn")
    def "can add columns with standard options but complex names"() {
        expect:
        testAction([
                name_asTable    : action.indexes*.name,
                relation_asTable: action.indexes*.relation,
                columns_asTable : action.indexes*.columns,
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    TestUtil.createAllPermutations(CreateIndexesAction, [
                            indexes: CollectionUtil.toSingletonLists(TestUtil.createAllPermutationsWithoutNulls(Index, [
                                    name    : getItemNames(Index, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                                    relation: getItemReferences(Table, it.allSchemas, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope),
                                    columns : CollectionUtil.toSingletonLists(getItemNames(Column, TestItemSupplier.NameStrategy.COMPLEX_NAMES, scope).collect({
                                        return new Index.IndexedColumn(it)
                                    })),
                            ]))
                    ])
            ], new ValidActionFilter(scope))
        }
    }

    @Unroll("#featureName: #action on #conn")
    def "can add columns with all permutations"() {
        expect:
        testAction([
                name_asTable       : action.indexes*.name,
                relation_asTable   : action.indexes*.relation,
                columns_asTable    : action.indexes*.columns,
                indexSchema_asTable: action.indexes*.indexSchema,
                tablespace_asTable : action.indexes*.tablespace,
                unique_asTable     : action.indexes*.unique,
                clustered_asTable  : action.indexes*.clustered
        ], action, conn, scope)

        where:
        [conn, scope, action] << JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers.collectMany {
            def scope = JUnitScope.getInstance(it)
            return CollectionUtil.permutationsWithoutNulls([
                    [it],
                    [scope],
                    createAllActionPermutations(it, scope)
            ], new ValidActionFilter(scope))
        }
    }

    @Override
    List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope) {
        def columnName = standardCaseItemName("column_name", Column, scope);
        def column2 = standardCaseItemName("column2", Column, scope);

        return TestUtil.createAllPermutations(CreateIndexesAction, [
                indexes: CollectionUtil.toSingletonLists(TestUtil.createAllPermutations(Index, [
                        name       : [standardCaseItemName("idx_test", Index, scope)],
                        relation   : [new RelationReference(Table, standardCaseItemName("table_name", Table, scope))],
                        indexSchema: connectionSupplier.allSchemas,
                        columns    : [
                                [],
                                [new Index.IndexedColumn(columnName)],
                                [new Index.IndexedColumn(columnName, Index.IndexDirection.ASC)],
                                [new Index.IndexedColumn(columnName, Index.IndexDirection.DESC)],
                                [new Index.IndexedColumn(columnName), new Index.IndexedColumn(column2)]
                        ],
                        tablespace : [connectionSupplier.alternateTablespace],
                        unique     : [true, false],
                        clustered  : [true, false]
                ]))
        ])
    }

    @Override
    protected Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope) {
        Snapshot snapshot = new Snapshot(scope)
        for (def index : ((CreateIndexesAction) action).indexes) {
            snapshot.add(new Table(index.relation.name, index.relation.schema))
            for (def col : index.columns) {
                snapshot.add(new Column(col.name, index.relation, DataType.parse("int"), false))
            }
            snapshot.add(new Column(standardCaseItemName("not_indexed", Column, scope), index.relation, DataType.parse("int"), false))
        }
        return snapshot
    }

}
