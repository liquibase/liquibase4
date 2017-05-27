package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.*;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.parser.unprocessor.AbstractActionUnprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;
import liquibase.parser.unprocessor.core.item.ItemReferenceUnprocessor;
import liquibase.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Adds columns to a database. {@link liquibase.actionlogic.ActionLogic} implementations that handle this should handle the case when there are columns added to multiple tables.
 * For performance and/or necessity, primary keys, unique constraints and foreign keys can be added along with the columns.
 */
public class AddColumnsAction extends AbstractAction {

    public PrimaryKey primaryKey;
    public List<Column> columns = new ArrayList<>();
    public List<UniqueConstraint> uniqueConstraints = new ArrayList<>();
    public List<ForeignKey> foreignKeys = new ArrayList<>();
    public List<CheckConstraint> checkConstraints = new ArrayList<>();

    public AddColumnsAction() {
    }

    public AddColumnsAction(Column... columns) {
        if (columns != null) {
            this.columns = new ArrayList<>(Arrays.asList(columns));
        }
    }


    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AddColumnsPreprocessor();
    }

    @Override
    public ParsedNodeUnprocessor createUnprocessor() {
        return new AbstractActionUnprocessor(AddColumnsAction.class) {
            @Override
            public Class<? extends ParsedNodeUnprocessor>[] mustBeAfter() {
                return CollectionUtil.union(Class.class, super.mustBeAfter(), ItemReferenceUnprocessor.class);
            }

            @Override
            protected void unprocessAction(ParsedNode actionNode, Scope scope) throws ParseException {
//                this.hoistListNodes(actionNode, "checkConstraints", "columns", "foreignKeys", "uniqueConstraints");
                for (ParsedNode relationName : actionNode.getChildren("relationName", true)) {
                    relationName.rename("tableName");
                }

                String actionTableName = actionNode.getChildValue("tableName", String.class, false);
                String actionSchemaName = actionNode.getChildValue("schemaName", String.class, false);
                String actionCatalogName = actionNode.getChildValue("catalogName", String.class, false);

                ParsedNode checkConstraintsNode = actionNode.getChild("checkConstraints", false);
                ParsedNode columnsNode = actionNode.getChild("columns", false);
                ParsedNode foreignKeysNode = actionNode.getChild("foreignKeys", false);
                ParsedNode uniqueConstraintsNode = actionNode.getChild("uniqueConstraints", false);
                ParsedNode primaryKeyNode = actionNode.getChild("primaryKey", false);

                if (actionTableName == null && actionSchemaName == null && actionCatalogName == null) {
                    List<ParsedNode> columns = columnsNode.getChildren("column", false);
                    if (columns.size() > 0) {
                        ParsedNode column = columns.get(0);
                        column.copyChildren("tableName", actionNode);
                        column.copyChildren("schemaName", actionNode);
                        column.copyChildren("catalogName", actionNode);

                        actionTableName = actionNode.getChildValue("tableName", String.class, false);
                        actionSchemaName = actionNode.getChildValue("schemaName", String.class, false);
                        actionCatalogName = actionNode.getChildValue("catalogName", String.class, false);
                    }
                }

                if (primaryKeyNode != null) {
                    removeTableAttributesIfSameAsDefault(actionCatalogName, actionSchemaName, actionTableName, primaryKeyNode);
                }

                for (ParsedNode collectionNode : new ParsedNode[] {checkConstraintsNode, columnsNode, foreignKeysNode, uniqueConstraintsNode} ) {
                    if (collectionNode == null) {
                        continue;
                    }
                    for (ParsedNode child : collectionNode.getChildren()) {
                        removeTableAttributesIfSameAsDefault(actionCatalogName, actionSchemaName, actionTableName, child);
                    }
                }

            }

            protected void removeTableAttributesIfSameAsDefault(String actionCatalogName, String actionSchemaName, String actionTableName, ParsedNode child) throws ParseException {
                for (ParsedNode tableName : child.getChildren("tableName", true)) {
                    if (tableName.getValue().equals(actionTableName)) {
                        tableName.remove();
                    }
                }
                for (ParsedNode schemaName : child.getChildren("schemaName", true)) {
                    if (schemaName.getValue().equals(actionSchemaName)) {
                        schemaName.remove();
                    }
                }
                for (ParsedNode catalogName : child.getChildren("catalogName", true)) {
                    if (catalogName.getValue().equals(actionCatalogName)) {
                        catalogName.remove();
                    }
                }
            }
        };
    }

    public static class AddColumnsPreprocessor extends AbstractActionPreprocessor {

        public AddColumnsPreprocessor() {
            super(AddColumnsAction.class);
        }

        @Override
        public Class<? extends ParsedNodePreprocessor>[] mustBeBefore() {
            return CollectionUtil.union(Class.class, super.mustBeBefore(),
                    new UniqueConstraint().createPreprocessor().getClass(),
                    new PrimaryKey().createPreprocessor().getClass(),
                    new ForeignKey().createPreprocessor().getClass(),
                    new CheckConstraint().createPreprocessor().getClass()
            );
        }

        @Override
        public String[] getAliases() {
            return new String[]{"addColumn"};
        }

        /**
         * <ul>
         * <li>Creates a "columns" node and moves all addColumns.column nodes to it</li>
         * <li>Copies a tableName value from "addColumns" to "column" nodes</li>
         * <li>Creates "column.relation" node</li>
         * </ul>
         */
        @Override
        protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
            groupChildren("checkConstraints", actionNode, "checkConstraint");
            groupChildren("columns", actionNode, "column");
            groupChildren("foreignKeys", actionNode, "foreignKey");
            groupChildren("uniqueConstraints", actionNode, "uniqueConstraint");


            for (ParsedNode groupNode : new ArrayList<>(actionNode.getChildren(new ParsedNode.ParsedNodeFilter() {
                @Override
                public boolean matches(ParsedNode node) {
                    return node.getName().equals("checkConstraints")
                            || node.getName().equals("columns")
                            || node.getName().equals("foreignKeys")
                            || node.getName().equals("uniqueConstraints");
                }
            }, false))) {
                for (ParsedNode objectNode : groupNode.getChildren()) {
                    copyAsDefault("tableName", actionNode, objectNode);
                    copyAsDefault("schemaName", actionNode, objectNode);
                    copyAsDefault("catalogName", actionNode, objectNode);

//                    convertToRelationReferenceNode("catalogName", "schemaName", "tableName", column);

                    if (objectNode.getName().equals("column")) {
                        fixColumn(objectNode, objectNode.getChild("relation", false), actionNode);
                    }
                }
            }

            ParsedNode primaryKeyNode = actionNode.getChild("primaryKey", false);
            if (primaryKeyNode != null) {
                copyAsDefault("tableName", actionNode, primaryKeyNode);
                copyAsDefault("schemaName", actionNode, primaryKeyNode);
                copyAsDefault("catalogName", actionNode, primaryKeyNode);
            }

            actionNode.removeChildren("catalogName");
            actionNode.removeChildren("schemaName");
            actionNode.removeChildren("tableName");
        }

        public void fixColumn(ParsedNode column, ParsedNode relation, ParsedNode mainNode) throws ParseException {
            this.fixConstraints(column, relation, mainNode);
        }


        public void fixConstraints(ParsedNode columnNode, ParsedNode relation, ParsedNode actionNode) throws ParseException {
            ParsedNode constraints = columnNode.getChild("constraints", false);
            if (constraints != null) {
                String columnName = columnNode.getChildValue("name", String.class, false);

                //primary key handling
                ParsedNode primaryKeyBoolean = constraints.getChild("primaryKey", false);
                ParsedNode primaryKey = null;
                if (primaryKeyBoolean != null) {
                    if (primaryKeyBoolean.getValue(false, Boolean.class)) {
                        primaryKey = actionNode.getChild("primaryKey", true);
                        ParsedNode primaryKeyColumns = primaryKey.getChild("columns", true);

                        ParsedNode primaryKeyColumn = primaryKeyColumns.addChild("primaryKeyColumn");
                        primaryKeyColumn.addChild("name").setValue(columnName);
                    }
                    primaryKeyBoolean.remove();
                }
                if (primaryKey == null) {
                    primaryKey = actionNode.getChild("primaryKey", false);
                }

                //unique handling
                ParsedNode uniqueConstraintBoolean = constraints.getChild("unique", false);
                if (uniqueConstraintBoolean != null) {
                    if (uniqueConstraintBoolean.getValue(false, Boolean.class)) {
                        ParsedNode uniqueConstraints = actionNode.getChild("uniqueConstraints", true);
                        ParsedNode uniqueConstraint = uniqueConstraints.addChild("uniqueConstraint");

                        constraints.moveChildren("uniqueConstraintName", uniqueConstraint);
                        uniqueConstraint.renameChildren("uniqueConstraintName", "name");

                        ParsedNode columns = uniqueConstraint.addChild("columns");
                        columns.setValue(new ArrayList<>());
                        ((List) columns.getValue()).add(columnName);

                        constraints.copyChildren("deferrable", uniqueConstraint);
                        constraints.copyChildren("initiallyDeferred", uniqueConstraint);
                    }

                    uniqueConstraintBoolean.remove();
                }

                //foreign key handling
                new AddForeignKeysAction.AddForeignKeysPreprocessor().fixForeignKey(constraints, relation);
                ParsedNode foreignKeys = actionNode.getChild("foreignKeys", true);
                constraints.moveChildren("foreignKey", foreignKeys);
                for (ParsedNode columnCheck : foreignKeys.getChildren("columnCheck", true)) {
                    ParsedNode baseColumn = columnCheck.getChild("baseColumn", true);
                    if (baseColumn.getValue() == null) {
                        baseColumn.setValue(columnNode.getChildValue("name", String.class, false));
                    }
                }

//                    ParsedNode deleteCascade = constraints.getChild("deleteCascade", false);
//                    if (deleteCascade != null) {
//                        if (deleteCascade.getValue(false, Boolean.class)) {
//                            fkNode.addChild("deleteRule").setValue("cascade");
//                        }
//                        deleteCascade.remove();
//                    }
//
//                    constraints.copyChildren("deferrable", fkNode);
//                    constraints.copyChildren("initiallyDeferred", fkNode);

                //check constraint handling
                ParsedNode originalCheckConstraintsNode = constraints.getChild("checkConstraint", false);
                if (originalCheckConstraintsNode != null) {
                    ParsedNode checkConstraints = actionNode.getChild("checkConstraints", true);
                    ParsedNode newCheckConstraint = checkConstraints.addChild("checkConstraint");

                    originalCheckConstraintsNode.rename("body").moveTo(newCheckConstraint);
                    if (relation != null) {
                        relation.rename("relation").copyTo(newCheckConstraint);
                    }
                }


                //final cleanup
                constraints.removeChildren("deferrable");
                constraints.removeChildren("initiallyDeferred");
                for (ParsedNode child : new ArrayList<>(constraints.getChildren())) {
                    switch (child.getName()) {
                        case "nullable":
                            constraints.moveChildren(child.getName(), columnNode);
                            break;
                        case "primaryKeyName":
                            if (primaryKey == null) {
                                throw new ParseException("Cannot specify " + child.getName() + " without a primary key", child);
                            }
                            child.rename("name").moveTo(primaryKey);
                            break;
                        case "primaryKeyTablespace":
                            if (primaryKey == null) {
                                throw new ParseException("Cannot specify " + child.getName() + " without a primary key", child);
                            }
                            child.rename("tablespace").moveTo(primaryKey);
                            break;
                        default:
                            throw new ParseException("Unknown constraints attribute '" + child.getName() + "'", child);
                    }
                }
                constraints.remove();
            }
        }
    }
}
