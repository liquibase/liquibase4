package liquibase.action.core;

import liquibase.Scope;
import liquibase.action.AbstractAction;
import liquibase.exception.ParseException;
import liquibase.item.core.ForeignKey;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Add foreign keys to a database. {@link liquibase.actionlogic.ActionLogic} implementations that handle this should handle the case when there are keys added to multiple tables.
 */
public class AddForeignKeysAction extends AbstractAction {

    public List<ForeignKey> foreignKeys = new ArrayList<>();

    public AddForeignKeysAction() {
    }

    public AddForeignKeysAction(ForeignKey... foreignKeys) {
        if (foreignKeys != null) {
            this.foreignKeys.addAll(Arrays.asList(foreignKeys));
        }
    }

    @Override
    public ParsedNodePreprocessor[] createPreprocessors() {
        return new ParsedNodePreprocessor[]{
                new AddForeignKeysPreprocessor()
        };
    }

    public static class AddForeignKeysPreprocessor extends AbstractActionPreprocessor {

        public AddForeignKeysPreprocessor() {
            super(AddForeignKeysAction.class);
        }

        @Override
        protected String[] getAliases() {
            return new String[]{"addForeignKeyConstraint"};
        }

        @Override
        protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
            ParsedNode baseTable = convertToRelationReferenceNode("baseTableCatalogName", "baseTableSchemaName", "baseTableName", actionNode);
            if (baseTable != null) {
                fixForeignKey(actionNode, baseTable);
                baseTable.remove();
            }
            ParsedNode foreignKeys = actionNode.getChild("foreignKeys", true);
            actionNode.moveChildren("foreignKey", foreignKeys);

            for (ParsedNode deleteRule : foreignKeys.getChildren("deleteRule", true)) {
                String value = deleteRule.getValue(null, String.class);
                if (value != null) {
                    for (ForeignKey.ReferentialAction possibleValue : ForeignKey.ReferentialAction.values()) {
                        if (value.equalsIgnoreCase(possibleValue.name()) && !value.equals(possibleValue.name())) {
                            deleteRule.setValue(possibleValue.name());
                        }
                    }
                }
            }

            for (ParsedNode updateRule : foreignKeys.getChildren("updateRule", true)) {
                String value = updateRule.getValue(null, String.class);
                if (value != null) {
                    for (ForeignKey.ReferentialAction possibleValue : ForeignKey.ReferentialAction.values()) {
                        if (value.equalsIgnoreCase(possibleValue.name()) && !value.equals(possibleValue.name())) {
                            updateRule.setValue(possibleValue.name());
                        }
                    }
                }
            }
        }

        public void fixForeignKey(ParsedNode baseNode, ParsedNode baseTableReferenceNode) throws ParseException {
            ParsedNode referencesNode = baseNode.getChild("references", false);
            ParsedNode referencedTableName = baseNode.getChild("referencedTableName", false);

            if (referencesNode != null || referencedTableName != null) {
                if (referencesNode != null && referencedTableName != null) {
                    throw new ParseException("Cannot specify both references and referencedTableName", baseNode);
                }

                if (referencesNode != null) {
                    String references = referencesNode.getValue(null, String.class);
                    Matcher referencesMatcher = Pattern.compile("(.*)\\(.*\\)").matcher(references);
                    if (!referencesMatcher.matches()) {
                        throw new ParseException("Cannot parse foreign key pattern", referencesNode);
                    }
                    String table = referencesMatcher.group(1).trim();
                    String schema = null;
                    String catalog = null;
                    String column = referencesMatcher.group(2).trim();

                    String[] splitTable = table.split(".");
                    if (splitTable.length == 2) {
                        schema = splitTable[0];
                        table = splitTable[1];
                    } else if (splitTable.length > 2) {
                        catalog = splitTable[0];
                        schema = splitTable[1];
                        table = splitTable[2];
                    }

                    baseNode.addChild("referencedTableName").setValue(table);
                    if (schema != null) {
                        baseNode.addChild("referencedTableSchemaName").setValue(schema);
                    }
                    if (catalog != null) {
                        baseNode.addChild("referencedTableCatalogName").setValue(catalog);
                    }

                    baseNode.addChild("referencedColumnNames").setValue(column);

                    referencesNode.remove();
                }

                ParsedNode foreignKey = baseNode.addChild("foreignKey");
                baseTableReferenceNode.copyTo(foreignKey).rename("relation");
                baseNode.renameChildren("foreignKeyName", "constraintName");
                baseNode.moveChildren("constraintName", foreignKey);
                baseNode.moveChildren("referencedColumnNames", foreignKey);
                baseNode.moveChildren("referencedTableName", foreignKey);
                baseNode.moveChildren("referencedTableSchemaName", foreignKey);
                baseNode.moveChildren("referencedTableCatalogName", foreignKey);
                ParsedNode referencedTable = convertToRelationReferenceNode("referencedTableCatalogName", "referencedTableSchemaName", "referencedTableName", foreignKey);
                referencedTable.rename("referencedTable");

                foreignKey.renameChildren("constraintName", "name");

                ParsedNode columnChecks = foreignKey.getChild("columnChecks", true);
                foreignKey.renameChildren("referencedColumnNames", "referencedColumnName");
                for (ParsedNode refColumnName : foreignKey.getChildren("referencedColumnName", false)) {
                    ParsedNode check = columnChecks.addChild("foreignKeyColumnCheck");
                    check.addChild("baseColumn").value = baseNode.getChildValue("baseColumnNames", String.class, true);
                    refColumnName.rename("referencedColumn").moveTo(check);
                }

                ParsedNode deleteCascade = baseNode.getChild("deleteCascade", false);
                if (deleteCascade != null) {
                    if (deleteCascade.getValue(false, Boolean.class)) {
                        foreignKey.addChild("deleteRule").setValue("cascade");
                    }
                    deleteCascade.remove();
                }

                baseNode.copyChildren("deferrable", foreignKey);
                baseNode.copyChildren("initiallyDeferred", foreignKey);

                baseNode.moveChildren("onDelete", foreignKey);
                baseNode.moveChildren("onUpdate", foreignKey);

                foreignKey.renameChildren("onDelete", "deleteRule");
                foreignKey.renameChildren("onUpdate", "updateRule");
            }
        }
    }
}