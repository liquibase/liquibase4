package liquibase.parser.preprocessor.core.changelog;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.AbstractParsedNodePreprocessor;

/**
 * Preprocessing that is applied to all action nodes.
 */
public class StandardActionPreprocessor extends AbstractParsedNodePreprocessor {

    @Override
    public void process(ParsedNode node, Scope scope) throws ParseException {
        for (ParsedNode tableNameNode : node.getChildren("tableName", true)) {
            ParsedNode parentNode = tableNameNode.getParent();

            ParsedNode relationNode = parentNode.addChild("relation");
            tableNameNode.moveTo(relationNode);
            tableNameNode.name = "name";

            ParsedNode schemaName = parentNode.getChild("schemaName", false);
            if (schemaName != null) {
                ParsedNode schemaNode = relationNode.addChild("schema");
                schemaName.moveTo(schemaNode);
                schemaName.name = "name";

                ParsedNode catalogName = relationNode.getChild("catalogName", false);
                if (catalogName != null) {
                    ParsedNode catalogNode = schemaNode.addChild("catalog");
                    catalogName.moveTo(catalogNode);
                    catalogName.name = "name";
                }
            }
        }
    }
}
