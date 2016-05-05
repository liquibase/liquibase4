package liquibase.action.core

import liquibase.item.core.ColumnReference
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import spock.lang.Specification

class SnapshotItemsActionTest extends Specification { //there are object-specific action tests like SnapshotItemsActionIndexesTest which do normal AbstractActionTest work

    def "constructor"() {
        expect:
        new SnapshotItemsAction(new ColumnReference("schema_name", "table_name", "col_name")).describe() == "snapshotItems(relatedTo=[schema_name.table_name.col_name], typeToSnapshot=liquibase.item.core.Column)"
        new SnapshotItemsAction(Table, new RelationReference(Table, "schema_name", "table_name")).describe() == "snapshotItems(relatedTo=[schema_name.table_name], typeToSnapshot=liquibase.item.core.Table)"
    }
}
