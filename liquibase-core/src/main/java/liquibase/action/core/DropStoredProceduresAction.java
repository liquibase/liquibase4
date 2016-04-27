package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.Schema;
import liquibase.item.core.StoredDatabaseLogicReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.CollectionUtil;

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
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(DropStoredProceduresAction.class) {

                    @Override
                    protected String[] getAliases() {
                        return new String[] { "dropProcedure"};
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        ParsedNode procedures = actionNode.getChild("procedures", true);

                        ParsedNode procedureName = actionNode.getChild("procedureName", false);
                        if (procedureName != null) {
                            procedureName.rename("procedure");
                            ParsedNode schema = convertToSchemaReferenceNode("catalogName", "schemaName", actionNode);
                            if (schema != null) {
                                schema.rename("container").moveTo(procedureName);
                                schema.addChild("type").setValue(Schema.class.getName());
                            }

                            procedureName.moveTo(procedures);
                        }
                    }
                }
        };
    }
}
