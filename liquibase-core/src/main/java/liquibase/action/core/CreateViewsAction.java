package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.View;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.CollectionUtil;

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
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(CreateViewsAction.class) {

                    @Override
                    protected String[] getAliases() {
                        return new String[] {
                                "createView"
                        };
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        ParsedNode views = actionNode.getChild("views", true);

                        if (actionNode.value != null) {
                            ParsedNode viewNode = views.addChild("view");
                            actionNode.moveChildren("fullDefinition", viewNode);
                            actionNode.moveChildren("viewName", viewNode);
                            actionNode.moveValue(viewNode.addChild("definition"));

                            ParsedNode schema = convertToSchemaReferenceNode("catalogName", "schemaName", actionNode);
                            if (schema != null) {
                                schema.moveTo(viewNode);
                            }
                        }

                        for (ParsedNode view : actionNode.getChildren("view", true)) {
                            view.renameChildren("viewName", "name");
                            view.renameChildren("fullDefinition", "completeDefinition");

                        }
                    }
                }
        };
    }
}
