package liquibase.action.core

import liquibase.JUnitScope
import liquibase.actionlogic.ActionExecutor
import liquibase.database.ConnectionSupplierFactory
import liquibase.structure.ObjectReference
import liquibase.structure.core.Table
import spock.lang.Specification
import spock.lang.Unroll
import testmd.TestMD
import testmd.logic.SetupResult

class DropTableActionTest extends Specification {
    def "empty constructor"() {
        expect:
        new DropTableAction().describe() == "dropTable()"
    }

    def "parametrized constructor"() {
        expect:
        new DropTableAction(new ObjectReference(Table, "cat", "schem", "tab")).describe() == "dropTable(table=cat.schem.tab)"
    }

//    @Unroll
//    def "drop table in different schemas"() {
//        expect:
//        for (def conn : JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers) {
//            if (catalogName == "_primary") catalogName = conn.primaryCatalog
//            if (schemaName == "_primary") schemaName = conn.primarySchema
//            if (catalogName == "_alt") catalogName = conn.alternateCatalog
//            if (schemaName == "_alt") schemaName = conn.alternateSchema
//
//            def action = new DropTablesAction(new ObjectReference(Table, catalogName, schemaName, table))
//
//
//            def scope = JUnitScope.getInstance(conn.getDatabase())
//            def plan = scope.getSingleton(ActionExecutor).createPlan(action, scope)
//
//            TestMD.test(this.class, "create simple table ${conn.databaseShortName}", conn.getDatabase().class)
//                    .withPermutation([connection: conn, catalogName_asTable: catalogName, schemaName_asTable: schemaName, tableName_asTable: table])
//                    .addOperations(plan: plan)
//                    .setup({
//                conn.connect(scope)
//
//                def table = new Table(new ObjectReference(Table, catalogName, schemaName, table))
//                scope.getSingleton(ActionExecutor).execute(new CreateTableAction(table.addColumn("id", "int"), scope))
//                throw SetupResult.OK
//            })
//                    .run({
//                plan.execute(scope)
//            })
//
//        }
//
//
//        where:
//        catalogName | schemaName | table
//        null        | null       | "test_table"
//        null        | "_primary" | "test_table"
//        "_primary"  | "_primary" | "test_table"
//        null        | "_alt"     | "test_table"
//        "_primary"  | "_alt"     | "test_table"
//        "_alt"      | "_alt"     | "test_table"
//    }

}
