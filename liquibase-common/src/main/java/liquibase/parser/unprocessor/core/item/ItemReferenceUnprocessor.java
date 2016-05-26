package liquibase.parser.unprocessor.core.item;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.ItemReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.unprocessor.AbstractParsedNodeUnprocessor;

public class ItemReferenceUnprocessor extends AbstractParsedNodeUnprocessor {

    @Override
    public void unprocess(ParsedNode node, Scope scope) throws ParseException {
        for (ParsedNode refNode : node.getChildren(ItemReference.class, true)) {
            Boolean virtual = refNode.getChildValue("virtual", Boolean.class, false);
            if (virtual != null && !virtual) {
                refNode.removeChildren("virtual");
            }

            markChildrenAsAttributes(refNode, "name", "type");

            String type = refNode.getChildValue("type", String.class, false);
            if (type != null && refNode.sourceType != null && refNode.sourceType.getSimpleName().replaceFirst("Reference$", "").equalsIgnoreCase(refNode.getName())) {
                refNode.removeChildren("type");
            }

            unprocess(refNode, scope);
        }
    }
}
