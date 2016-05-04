package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.View;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateViewsAction extends AbstractAction {

    public List<View> views = new ArrayList<>();
    public Boolean replaceIfExists;

    public CreateViewsAction() {
    }

    public CreateViewsAction(View... views) {
        if (views != null) {
            this.views.addAll(Arrays.asList(views));
        }
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractActionPreprocessor(CreateViewsAction.class) {

            @Override
            protected String[] getAliases() {
                return new String[]{
                        "createView"
                };
            }

            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                if (actionNode.getValue()!= null) {
                    actionNode.moveValue(actionNode.addChild("definition"));
                }

                ParsedNode definition = actionNode.getChild("definition", false);
                ParsedNode viewNode = null;
                if (definition != null) {
                    viewNode = actionNode.addChild("view");
                    actionNode.moveChildren("definition", viewNode);
                    actionNode.moveChildren("fullDefinition", viewNode);
                    actionNode.moveChildren("viewName", viewNode);
                    actionNode.moveChildren("catalogName", viewNode);
                    actionNode.moveChildren("schemaName", viewNode);
                }

                ParsedNode views = actionNode.getChild("views", true);
                actionNode.moveChildren("view", views);

                for (ParsedNode view : views.getChildren("view", false)) {
                    convertToSchemaReferenceNode("catalogName", "schemaName", view);
                    view.renameChildren("viewName", "name");
                    view.renameChildren("fullDefinition", "completeDefinition");
                }
            }
        };
    }
}
