package liquibase.action.core;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.action.UpdateAction;
import liquibase.exception.ParseException;
import liquibase.item.core.RelationReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.CollectionUtil;
import liquibase.util.StringClauses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Updates existing data.
 * For mixed insert/updates, use {@link InsertDataAction} with {@link InsertDataAction#columnsForUpdateCheck}.
 */
public class UpdateDataAction extends AbstractAction implements UpdateAction {
    public RelationReference relation;
    public List<UpdatedColumn> columns = new ArrayList<>();

    /**
     * Default where clauses are " AND " joined.
     * This can be replaced if you need a different standard operator, or just add a single StringClauses element with a different operator joining it's own clauses.
     */
    public StringClauses where = new StringClauses(" AND ");


    public UpdateDataAction() {
    }

    public UpdateDataAction(RelationReference relation, StringClauses where, UpdatedColumn... columns) {
        this.relation = relation;
        if (where != null) {
            this.where = where;
        }
        this.columns.addAll(Arrays.asList(CollectionUtil.createIfNull(columns)));
    }

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[] {
                new AbstractActionPreprocessor(UpdateDataAction.class) {
                    @Override
                    protected String[] getAliases() {
                        return new String[] {"update"};
                    }

                    @Override
                    protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
                        convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode);

                        for (ParsedNode child : actionNode.getChildren("column", false)) {
                            convertValueOptions("value", child);
                        }
                        actionNode.moveChildren("column", actionNode.getChild("columns", true));
                    }
                }
        };
    }

    public static class UpdatedColumn extends AbstractExtensibleObject {
        public String name;
        public Object value;

        public UpdatedColumn() {
        }

        public UpdatedColumn(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }


}
