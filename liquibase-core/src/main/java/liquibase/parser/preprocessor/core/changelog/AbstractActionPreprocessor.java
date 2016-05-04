package liquibase.parser.preprocessor.core.changelog;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.exception.ParseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.FunctionCall;
import liquibase.item.function.SequenceCurrentValueFunction;
import liquibase.item.function.SequenceNextValueFunction;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.AbstractParsedNodePreprocessor;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.util.CollectionUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Convenience base class for {@link ParsedNodePreprocessor} needed by {@link Action} classes.
 */
public abstract class AbstractActionPreprocessor extends AbstractParsedNodePreprocessor {

    public final String standardNodeName;

    public AbstractActionPreprocessor(Class<? extends Action> actionType) {
        try {
            standardNodeName = actionType.newInstance().getName();
        } catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    /**
     * Runs before {@link ChangeSetPreprocessor} if it defines aliases.
     */
    @Override
    public Class<? extends ParsedNodePreprocessor>[] mustBeBefore() {
        if (getAliases() != null) {
            return new Class[]{ChangeSetPreprocessor.class};
        }
        return null;
    }

    /**
     * Return other node names that need to be translated to {@link #standardNodeName}.
     * Default implementation returns null.
     */
    protected String[] getAliases() {
        return null;
    }

    /**
     * Default implementation renames nodes that match values from {@link #getAliases()}
     * and calls {@link #processActionNode(ParsedNode, Scope)} for each node that ends up matching {@link #standardNodeName}.
     * If a node is renamed due to an alias, {@link #processRenamedNode(String, ParsedNode)} is called first.
     */
    @Override
    public void process(ParsedNode node, Scope scope) throws ParseException {
        for (ParsedNode changeSet : node.getChildren("changeSet", true)) {
            String[] aliases = getAliases();
            if (aliases != null) {
                for (String alias : aliases) {
                    for (ParsedNode wrongName : changeSet.getChildren(alias, true)) {
                        String originalName = wrongName.getName();
                        wrongName.rename(standardNodeName);
                        processRenamedNode(originalName, wrongName);
                    }
                }
            }

            for (ParsedNode action : changeSet.getChildren(standardNodeName, true)) {
                processActionNode(action, scope);
            }
        }
    }

    /**
     * Called by {@link #process(ParsedNode, Scope)} when a node is renamed. Allows custom processing depending on what the original node name was.
     */
    protected void processRenamedNode(String originalName, ParsedNode node) throws ParseException {

    }

    /**
     * Usually easier to override this method with action-specific preprocessing logic than override the entire {@link #process(ParsedNode, Scope)} method.
     */
    protected abstract void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException;

    /**
     * Converts the nodes with the given names into a node that will map to a {@link liquibase.item.core.IndexReference}.
     *
     * @return the created node, unless NONE of the listed nodes exist.
     */
    protected ParsedNode convertToIndexReferenceNode(String catalogNodeName, String schemaNodeName, String tableNodeName, String indexNodeName, ParsedNode existingNode) throws ParseException {
        ParsedNode catalogNode = existingNode.getChild(catalogNodeName, false);
        ParsedNode schemaNode = existingNode.getChild(schemaNodeName, false);
        ParsedNode tableNode = existingNode.getChild(tableNodeName, false);
        ParsedNode indexNameNode = existingNode.getChild(indexNodeName, false);
        if (catalogNode == null && schemaNode == null && tableNode == null && indexNameNode == null) {
            return null;
        }

        ParsedNode indexNode = existingNode.addChild("index");

        if (indexNameNode != null) {
            indexNameNode.moveTo(indexNode);
            indexNameNode.rename("name");
        }

        if (catalogNode != null) {
            catalogNode.moveTo(indexNode);
        }
        if (schemaNode != null) {
            schemaNode.moveTo(indexNode);
        }
        if (tableNode != null) {
            tableNode.moveTo(indexNode);
        }

        ParsedNode finalTableNode = convertToRelationReferenceNode(catalogNodeName, schemaNodeName, tableNodeName, indexNode);
        if (finalTableNode != null) {
            finalTableNode.rename("container");
        }

        return indexNode;
    }

    /**
     * Converts the nodes with the given names into a node that will map to a {@link liquibase.item.core.SequenceReference}.
     *
     * @return the created node, unless NONE of the listed nodes exist.
     */
    protected ParsedNode convertToSequenceReferenceNode(String catalogNodeName, String schemaNodeName, String sequenceName, ParsedNode existingNode) throws ParseException {
        ParsedNode catalogNode = existingNode.getChild(catalogNodeName, false);
        ParsedNode schemaNode = existingNode.getChild(schemaNodeName, false);
        ParsedNode sequenceNameNode = existingNode.getChild(sequenceName, false);

        if (catalogNode == null && schemaNode == null && sequenceNameNode == null) {
            return null;
        }

        ParsedNode sequenceNode = existingNode.addChild("sequence");

        if (sequenceNameNode != null) {
            sequenceNameNode.moveTo(sequenceNode);
            sequenceNameNode.rename("name");
        }

        if (catalogNode != null) {
            catalogNode.moveTo(sequenceNode);
        }
        if (schemaNode != null) {
            schemaNode.moveTo(sequenceNode);
        }

        ParsedNode finalSchemaNode = convertToSchemaReferenceNode(catalogNodeName, schemaNodeName, sequenceNode);
        if (finalSchemaNode != null) {
            finalSchemaNode.rename("container");
        }

        return sequenceNode;
    }

    /**
     * Converts the nodes with the given names into a node that will map to a {@link liquibase.item.core.ColumnReference}.
     *
     * @return the created node, unless NONE of the listed nodes exist.
     */
    protected ParsedNode convertToColumnReferenceNode(String catalogNodeName, String schemaNodeName, String tableNodeName, String columnName, ParsedNode existingNode) throws ParseException {
        ParsedNode catalogNode = existingNode.getChild(catalogNodeName, false);
        ParsedNode schemaNode = existingNode.getChild(schemaNodeName, false);
        ParsedNode tableNode = existingNode.getChild(tableNodeName, false);
        ParsedNode columnNameNode = existingNode.getChild(columnName, false);

        if (catalogNode == null && schemaNode == null && tableNode == null && columnNameNode == null) {
            return null;
        }

        ParsedNode columnNode = existingNode.addChild("column");

        if (columnNameNode != null) {
            columnNameNode.moveTo(columnNode);
            columnNameNode.rename("name");
        }

        if (catalogNode != null) {
            catalogNode.moveTo(columnNode);
        }
        if (schemaNode != null) {
            schemaNode.moveTo(columnNode);
        }
        if (tableNode != null) {
            tableNode.moveTo(columnNode);
        }

        ParsedNode relationNode = convertToRelationReferenceNode(catalogNodeName, schemaNodeName, tableNodeName, columnNode);
        if (relationNode != null) {
            relationNode.rename("container");
        }

        return columnNode;
    }

    /**
     * Converts the nodes with the given names into a node that will map to a {@link liquibase.item.core.RelationReference}.
     *
     * @return the created node, unless NONE of the listed nodes exist.
     */
    protected ParsedNode convertToRelationReferenceNode(String catalogNodeName, String schemaNodeName, String tableNodeName, ParsedNode existingNode) throws ParseException {
        ParsedNode catalogNode = existingNode.getChild(catalogNodeName, false);
        ParsedNode schemaNode = existingNode.getChild(schemaNodeName, false);
        ParsedNode tableNameNode = existingNode.getChild(tableNodeName, false);

        if (catalogNode == null && schemaNode == null && tableNameNode == null) {
            return null;
        }

        ParsedNode relationNode = existingNode.addChild("relation");

        if (tableNameNode != null) {
            tableNameNode.moveTo(relationNode);
            tableNameNode.rename("name");
        }

        if (catalogNode != null) {
            catalogNode.moveTo(relationNode);
        }
        if (schemaNode != null) {
            schemaNode.moveTo(relationNode);
        }
        ParsedNode finalSchemaNode = convertToSchemaReferenceNode(catalogNodeName, schemaNodeName, relationNode);
        if (finalSchemaNode != null) {
            finalSchemaNode.rename("container");
        }

        return relationNode;
    }

    /**
     * Converts the nodes with the given names into a node that will map to a {@link liquibase.item.core.SchemaReference}.
     *
     * @return the created node, unless NONE of the listed nodes exist.
     */
    protected ParsedNode convertToSchemaReferenceNode(String catalogNodeName, String schemaNodeName, ParsedNode existingNode) throws ParseException {
        ParsedNode schemaNameNode = existingNode.getChild(schemaNodeName, false);
        ParsedNode catalogNameNode = existingNode.getChild(catalogNodeName, false);

        if (schemaNameNode == null && catalogNameNode == null) {
            return null;
        }

        ParsedNode schemaNode = existingNode.addChild("schema");
        if (schemaNameNode != null) {
            schemaNameNode.moveTo(schemaNode);
            schemaNameNode.rename("name");
        }

        if (catalogNameNode != null) {
            ParsedNode catalogNode = schemaNode.addChild("container");
            catalogNameNode.moveTo(catalogNode);
            catalogNameNode.rename("name");
        }

        return schemaNode;
    }

    /**
     * Finds and converts "value" nodes.
     * The node is checked for a child node with a name of the passed baseName along with baseName+"Number", +Date, +Boolean, +Computed, +SequenceCurrent and +SequenceNext.
     * If multiple nodes are round, a {@link ParseException} is thrown. Otherwise, the node is renamed to just the baseName if and the value is parsed based on the type.
     */
    protected void convertValueOptions(String baseName, ParsedNode node) throws ParseException {
        Map<String, ParsedNode> valueOptions = new HashMap<>();
        valueOptions.put(baseName, node.getChild(baseName, false));
        valueOptions.put(baseName + "Numeric", node.getChild(baseName + "Numeric", false));
        valueOptions.put(baseName + "Date", node.getChild(baseName + "Date", false));
        valueOptions.put(baseName + "Boolean", node.getChild(baseName + "Boolean", false));
        valueOptions.put(baseName + "Computed", node.getChild(baseName + "Computed", false));
        valueOptions.put(baseName + "SequenceCurrent", node.getChild(baseName + "SequenceCurrent", false));
        valueOptions.put(baseName + "SequenceNext", node.getChild(baseName + "SequenceNext", false));

        valueOptions = CollectionUtil.select(valueOptions, new CollectionUtil.CollectionFilter<Map.Entry<String, ParsedNode>>() {
            @Override
            public boolean include(Map.Entry<String, ParsedNode> obj) {
                return obj.getValue() != null;
            }
        });

        if (valueOptions.size() == 0) {
            ;//nothing to do
        } else if (valueOptions.size() > 1) {
            throw new ParseException("Cannot specify multiple " + baseName + "* attributes", node);
        } else {
            Map.Entry<String, ParsedNode> valueOption = valueOptions.entrySet().iterator().next();
            String defaultValueType = valueOption.getKey();
            ParsedNode valueOptionNode = valueOption.getValue();

            if (defaultValueType.equals(baseName)) {
                ;//nothing do to
            } else {
                if (valueOptionNode.getName().equals(baseName + "Numeric")) {
                    valueOptionNode.setValue(valueOptionNode.getValue(null, BigDecimal.class));
                } else if (valueOptionNode.getName().equals(baseName + "Date")) {
                    valueOptionNode.setValue(valueOptionNode.getValue(null, Date.class));
                } else if (valueOptionNode.getName().equals(baseName + "Boolean")) {
                    valueOptionNode.setValue(valueOptionNode.getValue(null, Boolean.class));
                } else if (valueOptionNode.getName().equals(baseName + "Computed")) {
                    if (!(valueOptionNode.getValue() instanceof FunctionCall)) {
                        valueOptionNode.setValue(new FunctionCall(valueOptionNode.getValue(null, String.class)));
                    }
                } else if (valueOptionNode.getName().equals(baseName + "SequenceCurrent")) {
                    if (!(valueOptionNode.getValue() instanceof SequenceCurrentValueFunction)) {
                        valueOptionNode.setValue(new SequenceCurrentValueFunction(valueOptionNode.getValue(null, String.class)));
                    }
                } else if (valueOptionNode.getName().equals(baseName + "SequenceNext")) {
                    if (!(valueOptionNode.getValue() instanceof SequenceNextValueFunction)) {
                        valueOptionNode.setValue(new SequenceNextValueFunction(valueOptionNode.getValue(null, String.class)));
                    }
                } else {
                    throw new ParseException("Unknown defaultValue attribute: " + valueOptionNode.getName(), valueOptionNode);
                }
                valueOptionNode.rename(baseName);

            }
        }
    }
}
