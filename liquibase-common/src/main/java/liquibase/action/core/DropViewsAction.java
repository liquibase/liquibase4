package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.RelationReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Drops existing views.
 */
public class DropViewsAction extends AbstractAction {

    public List<RelationReference> views = new ArrayList<>();

    public DropViewsAction() {
    }

    public DropViewsAction(RelationReference... views) {
        if (views != null) {
            this.views.addAll(Arrays.asList(views));
        }
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(DropViewsAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{"dropView"};
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                ParsedNode relation = convertToRelationReferenceNode("catalogName", "schemaName", "viewName", actionNode);
                if (relation != null) {
                    ParsedNode views = actionNode.getChild("views", true);
                    relation.rename("view").moveTo(views);
                }
            }
        };
    }
}
