package liquibase.parser.xml;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.AbstractUnparser;
import liquibase.parser.ParsedNode;
import liquibase.util.StringUtil;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard {@link liquibase.parser.Unparser} for XML files. Expects files to have an .xml extension.
 */
public class XmlUnparser extends AbstractUnparser {

    @Override
    public int getPriority(String path, Scope scope) {
        if (path.toLowerCase().endsWith(".xml")) {
            return PRIORITY_DEFAULT;
        } else {
            return PRIORITY_NOT_APPLICABLE;
        }
    }

    @Override
    public void unparse(ParsedNode node, OutputStream output, Scope scope) throws ParseException {
        try {
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = null;
            writer = xmlOutputFactory.createXMLStreamWriter(output, "UTF-8");

            writer.writeStartDocument("utf-8", "1.1");
            writeNode(node, writer, 0, scope);

            writer.writeEndDocument();
        } catch (XMLStreamException e) {
            throw new ParseException(e, null);
        }

    }

    protected void writeNode(ParsedNode node, XMLStreamWriter writer, int depth, Scope scope) throws XMLStreamException, ParseException {
        List<ParsedNode> childElements = new ArrayList<>();
        List<ParsedNode> childAttributes = new ArrayList<>();

        for (ParsedNode child : node.getChildren()) {
            if (child.getName().equals(XmlParser.XML_ATTRIBUTE_PROPERTY)) {
                continue;
            }

            ParsedNode xmlAttrNode = child.getChild(XmlParser.XML_ATTRIBUTE_PROPERTY, false);
            if (xmlAttrNode != null && xmlAttrNode.getValue(false, Boolean.class)) {
                childAttributes.add(child);
            } else {
                childElements.add(child);
            }
        }

        writer.writeCharacters(scope.getLineSeparator());

        Object nodeValue = node.getValue();
        if (childElements.size() == 0 && nodeValue == null) {
            writer.writeCharacters(StringUtil.pad("", depth * 4));
            writer.writeEmptyElement(node.getName());
        } else {
            writer.writeCharacters(StringUtil.pad("", depth * 4));
            writer.writeStartElement(node.getName());

        }
        for (ParsedNode attributeNode : childAttributes) {
            writer.writeAttribute(attributeNode.getName(), attributeNode.getValue().toString());
        }

        for (ParsedNode child : childElements) {
            writeNode(child, writer, depth + 1, scope);
        }

        boolean multiLine = false;
        if (nodeValue != null && (nodeValue = StringUtil.trimToNull(nodeValue.toString())) != null) {
            String valueString = (String) nodeValue;
            multiLine = valueString.contains("\n");
            if (multiLine || childElements.size() > 0) {
                writer.writeCharacters(scope.getLineSeparator());
                writer.writeCharacters(StringUtil.indent(valueString, (depth + 1) * 4));
            } else {
                writer.writeCharacters(valueString);
            }
        }


        if (multiLine || childElements.size() > 0) {
            writer.writeCharacters(scope.getLineSeparator());
            writer.writeCharacters(StringUtil.pad("", depth * 4));
        }

        if (childElements.size() > 0 || nodeValue != null) {
            writer.writeEndElement();
        }

    }
}
