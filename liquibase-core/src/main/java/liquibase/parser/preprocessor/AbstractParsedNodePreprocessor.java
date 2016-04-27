package liquibase.parser.preprocessor;

import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.plugin.AbstractPlugin;

/**
 * Convenience base class for {@link ParsedNodePreprocessor} implementations.
 */
public abstract class AbstractParsedNodePreprocessor extends AbstractPlugin implements ParsedNodePreprocessor {

    @Override
    public Class<? extends ParsedNodePreprocessor>[] mustBeBefore() {
        return null;
    }

    @Override
    public Class<? extends ParsedNodePreprocessor>[] mustBeAfter() {
        return null;
    }

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
        ParsedNode columnNode = existingNode.addChild("column");

        ParsedNode columnNameNode = existingNode.getChild(columnName, false);
        if (columnNameNode == null) {
            return null;
        }

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
}
