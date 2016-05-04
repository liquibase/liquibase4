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
     * and calls {@link #processActionNode(ParsedNode, Scope)} for each node that ends up matching {@link #standardNodeName}
     */
    @Override
    public void process(ParsedNode node, Scope scope) throws ParseException {
        for (ParsedNode changeSet : node.getChildren("changeSet", true)) {
            String[] aliases = getAliases();
            if (aliases != null) {
                for (String alias : aliases) {
                    for (ParsedNode wrongName : changeSet.getChildren(alias, true)) {
                        String originalName = wrongName.name;
                        wrongName.name = standardNodeName;
                        processRenamedNode(originalName, wrongName);
                    }
                }
            }

            for (ParsedNode action : changeSet.getChildren(standardNodeName, true)) {
                processActionNode(action, scope);
            }
        }
    }

    protected void processRenamedNode(String originalName, ParsedNode node) throws ParseException {

    }

    /**
     * Usually easier to override this method with action-specific preprocessing logic than override the entire {@link #process(ParsedNode, Scope)} method.
     */
    protected abstract void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException;

    protected ParsedNode convertToIndexReferenceNode(String catalogNodeName, String schemaNodeName, String tableNodeName, String indexName, ParsedNode existingNode) throws ParseException {
        ParsedNode indexNode = existingNode.addChild("index");

        ParsedNode indexNameNode = existingNode.getChild(indexName, false);
        if (indexNameNode == null) {
            return null;
        }

        indexNameNode.moveTo(indexNode);
        indexNameNode.name = "name";

        existingNode.moveChildren(tableNodeName, indexNameNode);
        existingNode.moveChildren(schemaNodeName, indexNameNode);
        existingNode.moveChildren(catalogNodeName, indexNameNode);
        ParsedNode tableNode = convertToRelationReferenceNode(catalogNodeName, schemaNodeName, tableNodeName, indexNode);
        if (tableNode != null) {
            tableNode.rename("container");
        }

        return indexNode;
    }

    protected ParsedNode convertToSequenceReferenceNode(String catalogNodeName, String schemaNodeName, String sequenceName, ParsedNode existingNode) throws ParseException {
        ParsedNode sequenceNode = existingNode.addChild("sequence");

        ParsedNode sequenceNameNode = existingNode.getChild(sequenceName, false);
        if (sequenceNameNode == null) {
            return null;
        }

        sequenceNameNode.moveTo(sequenceNode);
        sequenceNameNode.name = "name";

        existingNode.moveChildren(schemaNodeName, sequenceNameNode);
        existingNode.moveChildren(catalogNodeName, sequenceNameNode);
        ParsedNode schemaNode = convertToSchemaReferenceNode(catalogNodeName, schemaNodeName, sequenceNameNode);
        if (schemaNode != null) {
            schemaNode.rename("container");
        }

        return sequenceNode;
    }

    protected ParsedNode convertToColumnReferenceNode(String catalogNodeName, String schemaNodeName, String tableNodeName, String columnName, ParsedNode existingNode) throws ParseException {
        ParsedNode columnNameNode = existingNode.getChild(columnName, false);
        if (columnNameNode == null) {
            return null;
        }

        ParsedNode columnNode = existingNode.addChild("column");

        columnNameNode.moveTo(columnNode);
        columnNameNode.name = "name";

        existingNode.moveChildren(tableNodeName, columnNameNode);
        existingNode.moveChildren(schemaNodeName, columnNameNode);
        existingNode.moveChildren(catalogNodeName, columnNameNode);
        ParsedNode relationNode = convertToRelationReferenceNode(catalogNodeName, schemaNodeName, tableNodeName, columnNameNode);
        if (relationNode != null) {
            relationNode.rename("container");
        }

        return columnNode;
    }

    protected ParsedNode convertToRelationReferenceNode(String catalogNodeName, String schemaNodeName, String tableNodeName, ParsedNode existingNode) throws ParseException {
        ParsedNode tableNameNode = existingNode.getChild(tableNodeName, false);
        if (tableNameNode == null) {
            return null;
        }

        ParsedNode relationNode = existingNode.addChild("relation");

        tableNameNode.moveTo(relationNode);
        tableNameNode.name = "name";

        existingNode.moveChildren(schemaNodeName, tableNameNode);
        existingNode.moveChildren(catalogNodeName, tableNameNode);
        convertToSchemaReferenceNode(catalogNodeName, schemaNodeName, tableNameNode);

        return relationNode;
    }

    protected ParsedNode convertToSchemaReferenceNode(String catalogNodeName, String schemaNodeName, ParsedNode existingNode) throws ParseException {
        ParsedNode schemaNameNode = existingNode.getChild(schemaNodeName, false);
        ParsedNode catalogNameNode = existingNode.getChild(catalogNodeName, false);

        if (schemaNameNode == null && catalogNameNode == null) {
            return null;
        }

        ParsedNode schemaNode = existingNode.getChild("schema", true);
        if (schemaNameNode != null) {
            schemaNameNode.moveTo(schemaNode);
            schemaNameNode.name = "name";
        }

        if (catalogNameNode != null) {
            ParsedNode catalogNode = schemaNode.addChild("container");
            catalogNameNode.moveTo(catalogNode);
            catalogNameNode.name = "name";
        }

        return schemaNode;
    }

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
                if (valueOptionNode.name.equals(baseName + "Numeric")) {
                    valueOptionNode.setValue(valueOptionNode.getValue(null, BigDecimal.class));
                } else if (valueOptionNode.name.equals(baseName + "Date")) {
                    valueOptionNode.setValue(valueOptionNode.getValue(null, Date.class));
                } else if (valueOptionNode.name.equals(baseName + "Boolean")) {
                    valueOptionNode.setValue(valueOptionNode.getValue(null, Boolean.class));
                } else if (valueOptionNode.name.equals(baseName + "Computed")) {
                    if (!(valueOptionNode.value instanceof FunctionCall)) {
                        valueOptionNode.setValue(new FunctionCall(valueOptionNode.getValue(null, String.class)));
                    }
                } else if (valueOptionNode.name.equals(baseName + "SequenceCurrent")) {
                    if (!(valueOptionNode.value instanceof SequenceCurrentValueFunction)) {
                        valueOptionNode.setValue(new SequenceCurrentValueFunction(valueOptionNode.getValue(null, String.class)));
                    }
                } else if (valueOptionNode.name.equals(baseName + "SequenceNext")) {
                    if (!(valueOptionNode.value instanceof SequenceNextValueFunction)) {
                        valueOptionNode.setValue(new SequenceNextValueFunction(valueOptionNode.getValue(null, String.class)));
                    }
                } else {
                    throw new ParseException("Unknown defaultValue attribute: " + valueOptionNode.name, valueOptionNode);
                }
                valueOptionNode.rename(baseName);

            }
        }
    }
}
