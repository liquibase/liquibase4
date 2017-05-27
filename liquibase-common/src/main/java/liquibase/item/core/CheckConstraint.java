package liquibase.item.core;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.AbstractRelationBasedObject;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.item.AbstractItemPreprocessor;
import liquibase.parser.unprocessor.AbstractItemUnprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;

/**
 * Describes a check constraint on a table.
 */
public class CheckConstraint extends AbstractRelationBasedObject<CheckConstraintReference> {

    public Boolean disabled;
    public String body;

    @Override
    public CheckConstraintReference toReference() {
        return new CheckConstraintReference(name, relation);
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractItemPreprocessor(CheckConstraint.class) {
            @Override
            protected void processItemNode(ParsedNode itemNode, Scope scope) throws ParseException {
                moveValueToNode(itemNode, "body");
                itemNode.renameChildren("tableName", "relationName");
                this.convertToRelationReferenceNode("catalogName", "schemaName", "relationName", itemNode);
            }
        };
    }

    @Override
    public ParsedNodeUnprocessor createUnprocessor() {
        return new AbstractItemUnprocessor(CheckConstraint.class) {
            @Override
            protected void unprocessItem(ParsedNode typeNode, Scope scope) throws ParseException {
                this.markChildAsText(typeNode, "body");
                typeNode.renameChildren("relationName", "tableName");
            }
        };
    }
}
