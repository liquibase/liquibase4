package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.*;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.util.ObjectUtil;

import java.util.*;

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

    public static class AddColumnsPreprocessor extends AbstractActionPreprocessor {

        public AddColumnsPreprocessor() {
            super(AddColumnsAction.class);
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
            ParsedNode columns = actionNode.getChild("columns", true);
            actionNode.moveChildren("column", columns);

            for (ParsedNode column : new ArrayList<>(columns.getChildren("column", false))) {
                actionNode.copyChildren("tableName", column);
                actionNode.copyChildren("schemaName", column);
                actionNode.copyChildren("catalogName", column);

                convertToRelationReferenceNode("catalogName", "schemaName", "tableName", column);

                fixColumn(column, column.getChild("relation", false), actionNode);
            }

            actionNode.removeChildren("catalogName");
            actionNode.removeChildren("schemaName");
            actionNode.removeChildren("tableName");
        }

        public void fixColumn(ParsedNode column, ParsedNode relation, ParsedNode mainNode) throws ParseException {
            convertValueOptions("defaultValue", column);
            this.fixAutoIncrement(column);
            this.fixConstraints(column, relation, mainNode);
            column.renameChildren("computed", "virtual");
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

        public void fixAutoIncrement(ParsedNode column) throws ParseException {
            ParsedNode autoIncrementNode = column.getChild("autoIncrement", false);
            if (autoIncrementNode != null) {
                if (autoIncrementNode.getValue(false, Boolean.class)) {
                    autoIncrementNode.rename("autoIncrementInformation");
                    autoIncrementNode.setValue(null);
                } else {
                    autoIncrementNode.remove();
                }
            }
            autoIncrementNode = column.getChild("autoIncrementInformation", false);
            ParsedNode startWith = column.getChild("startWith", false);
            if (startWith != null) {
                if (autoIncrementNode == null) {
                    throw new ParseException("Cannot specify 'startWith' without autoIncrement defined", startWith);
                } else {
                    startWith.moveTo(autoIncrementNode);
                }
            }

            ParsedNode incrementBy = column.getChild("incrementBy", false);
            if (incrementBy != null) {
                if (autoIncrementNode == null) {
                    throw new ParseException("Cannot specify 'incrementBy' without autoIncrement defined", incrementBy);
                } else {
                    incrementBy.moveTo(autoIncrementNode);
                }
            }
        }
    }
}
