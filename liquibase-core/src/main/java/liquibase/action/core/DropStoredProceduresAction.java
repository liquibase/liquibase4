package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.Schema;
import liquibase.item.core.StoredDatabaseLogicReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Drop existing stored procedures
 */
public class DropStoredProceduresAction extends AbstractAction {

    public List<StoredDatabaseLogicReference> procedures = new ArrayList<>();

    public DropStoredProceduresAction() {

    }

    public DropStoredProceduresAction(StoredDatabaseLogicReference... procedures) {
        if (procedures != null) {
            this.procedures.addAll(Arrays.asList(procedures));
        }
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(DropStoredProceduresAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{"dropProcedure"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                ParsedNode procedureName = actionNode.getChild("procedureName", false);
                if (procedureName != null) {
                    ParsedNode procedure = actionNode.addChild("procedure");
                    procedureName.moveTo(procedure);
                    actionNode.moveChildren("catalogName", procedure);
                    actionNode.moveChildren("schemaName", procedure);
                }

                ParsedNode procedures = actionNode.getChild("procedures", true);
                actionNode.moveChildren("procedure", procedures);

                for (ParsedNode procedure : procedures.getChildren("procedure", false)) {
                    procedure.renameChildren("procedureName", "name");
                    ParsedNode schema = convertToSchemaReferenceNode("catalogName", "schemaName", procedure);
                    if (schema != null) {
                        schema.rename("container");
                        schema.addChild("type").setValue(Schema.class.getName());
                    }

                }
            }
        };
    }
}
