package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.Schema;
import liquibase.item.core.StoredProcedure;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates stored procedures.
 */
public class CreateStoredProceduresAction extends AbstractAction {

    public List<StoredProcedure> procedures = new ArrayList<>();

    /**
     * If true, replace an existing procedure with the same name if they exist.
     */
    public Boolean replaceIfExists;

    public String dbms;

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(CreateStoredProceduresAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{"createProcedure"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                actionNode.removeChildren("comment");

                ParsedNode procedures = actionNode.getChild("procedures", true);

                if (actionNode.value != null) {
                    ParsedNode body = actionNode.addChild("body");
                    actionNode.moveValue(body);
                }

                ParsedNode bodyNode = actionNode.getChild("body", false);
                ParsedNode pathNode = actionNode.getChild("path", false);

                if (bodyNode != null || pathNode != null) {
                    ParsedNode storedProcedure = procedures.addChild("storedProcedure");
                    actionNode.moveChildren("body", storedProcedure);
                    actionNode.moveChildren("path", storedProcedure);
                    actionNode.moveChildren("encoding", storedProcedure);

                    actionNode.moveChildren("procedureName", storedProcedure);
                    storedProcedure.renameChildren("procedureName", "name");

                    ParsedNode schema = convertToSchemaReferenceNode("catalogName", "schemaName", actionNode);
                    if (schema != null) {
                        schema.rename("container").moveTo(storedProcedure);
                    }
                }

                for (ParsedNode storedProc : actionNode.getChildren("storedProcedure", false)) {
                    for (ParsedNode container : storedProc.getChildren("container", false)) {
                        ParsedNode type = container.getChild("type", true);
                        if (type.value == null) {
                            type.value = Schema.class.getName();
                        }
                    }
                }
            }
        };
    }
}
