package liquibase.parser.unprocessor.core;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.unprocessor.AbstractParsedNodeUnprocessor;

public class XmlUnprocessor extends AbstractParsedNodeUnprocessor {

    @Override
    public void unprocess(ParsedNode node, String outputPath, Scope scope) throws ParseException {
        if (outputPath.toLowerCase().endsWith(".xml")) {
            if (node.getName().equals("changeLog")) {
                node.addChild("xmlns").addMarker(ParsedNode.Marker.isAttribute).setValue("http://www.liquibase.org/xml/ns/changelog");
                node.addChild("xmlns:xsi").addMarker(ParsedNode.Marker.isAttribute).setValue("http://www.w3.org/2001/XMLSchema-instance");
                node.addChild("xsi:schemaLocation").addMarker(ParsedNode.Marker.isAttribute).setValue("http://www.liquibase.org/xml/ns/changelog http://www.liquibase.org/xml/ns/changelog/changelog-4.0.xsd");
            }
        }
    }
}
