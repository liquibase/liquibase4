package liquibase.parser.xml

import liquibase.util.StringUtil
import org.apache.commons.lang.StringUtils
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.w3c.dom.Text

import javax.xml.XMLConstants
import javax.xml.namespace.NamespaceContext
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

/**
 * Generates XML based on an XSD.
 */
class TestXmlGenerator {

    DocumentBuilder documentBuilder
    XPath xpath

    TestXmlGenerator() {
        this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        this.xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new NamespaceContext() {
            @Override
            String getNamespaceURI(String prefix) {
                return XMLConstants.NULL_NS_URI;
            }

            @Override
            String getPrefix(String namespaceURI) {
                throw new UnsupportedOperationException();
            }

            @Override
            Iterator getPrefixes(String namespaceURI) {
                throw new UnsupportedOperationException();
            }
        })
    }

    /**
     * Returns a list of strings containing XML for all the elements matched by the given xpath selector
     */
    public List<String> generateXml(String xsdPath, String selector) {
        List<String> xml = new ArrayList<>()

        TestXmlGenerator.class.getClassLoader().getResourceAsStream(xsdPath).withStream { xsdStream ->
            def xsdDocument = documentBuilder.parse(xsdStream);

            def elements = (NodeList) xpath.compile(selector).evaluate(xsdDocument, XPathConstants.NODESET)
            for (int i = 0; i < elements.getLength(); i++) {
                xml.add(createElement((Element) elements.item(i), xsdDocument))
            }
        }

        return xml
    }

    private String createElement(Element elementPointer, Document xsd) {
        String nodeName = StringUtil.trimToNull(elementPointer.getAttribute("ref"))

        Element elementDefinition
        if (nodeName == null) {
            nodeName = StringUtil.trimToNull(elementPointer.getAttribute("name"));
            if (nodeName == null) {
                throw new RuntimeException("Could not find element ref or name");
            }
            elementDefinition = elementPointer
        } else {
            elementDefinition = (Element) xpath.compile("/schema/element[@name='$nodeName']").evaluate(xsd, XPathConstants.NODE)
        }


        def attributes = [:]
        def childElements = []
        boolean hasText = false;

        String type = StringUtils.trimToNull(elementDefinition.getAttribute("type"));
        if (nodeName.equals("empty")) {
            //nothing in there
        } else if (type != null) {
            hasText = true
        } else {

            Element complexType = (Element) xpath.compile("/schema/element[@name='$nodeName']/complexType").evaluate(xsd, XPathConstants.NODE)

            if (complexType == null) {
                hasText = true;
            } else {

                String mixed = complexType.getAttribute("mixed");
                hasText = "true".equalsIgnoreCase(mixed)
                NodeList children = complexType.getChildNodes();

                for (int i = 0; i < children.getLength(); i++) {
                    def childNode = children.item(i)
                    if (childNode instanceof Text) {
                        continue;
                    }

                    def tagName = ((Element) childNode).getTagName()
                    if (tagName.equals("xsd:attribute")) {
                        attributes.put(childNode.getAttributes().getNamedItem("name").getNodeValue(), childNode.getAttributes().getNamedItem("type").getNodeValue());
                    } else if (tagName.equals("xsd:choice")) {
                        NodeList elements = ((Element) childNode).getElementsByTagName("xsd:element");
                        for (int j = 0; j < elements.length; j++) {
                            childElements.add(createElement((Element) elements.item(j), xsd))
                        }
                    } else if (tagName.equals("xsd:attributeGroup")) {
                        addAttributesFromGroup(childNode.getAttribute("ref"), attributes, xsd)
                    } else if (tagName.equals("xsd:simpleContent")) {
                        hasText = true;
                        NodeList simpleAttributes = ((Element) ((Element) childNode).getElementsByTagName("xsd:extension").item(0)).getElementsByTagName("xsd:attribute")
                        for (int k = 0; k < simpleAttributes.length; k++) {
                            attributes.put(((Element) simpleAttributes.item(k)).getAttribute("name"), ((Element) simpleAttributes.item(k)).getAttribute("type"))
                        }
                    } else if (tagName.equals("xsd:sequence")) {
                        NodeList sequenceElements = ((Element) childNode).getElementsByTagName("xsd:element");
                        for (int k = 0; k < sequenceElements.length; k++) {
                            childElements.add(createElement(sequenceElements.item(k), xsd))
                        }
                    } else if (tagName.equals("xsd:anyAttribute")) {
                        //don't do anything
                    } else {
                        throw new RuntimeException("Unexpected tag $tagName")

                    }
                }
            }
        }

        StringBuilder xml = new StringBuilder()
        xml.append("<$nodeName")

        if (attributes.size() > 0) {
            xml.append(" ")
            List<String> list = new ArrayList<>();
            for (Map.Entry entry : (Set<Map.Entry>) attributes.entrySet()) {
                String value;
                switch (entry.getValue()) {
                    case "xsd:string":
                        value = "a string for " + entry.getKey();
                        break;
                    case "xsd:boolean":
                    case "booleanExp":
                        value = "true";
                        break;
                    case "xsd:long":
                    case "xsd:nonNegativeInteger":
                        value = "42";
                        break;
                    case "fkCascadeActionOptions":
                        value = "RESTRICT"
                        break;
                    default: throw new RuntimeException("Unknown type $type")
                }

                list.add(entry.getKey().toString() + "='" + value + "'");
            }
            xml.append(StringUtil.join(list, " "));
        }

        xml.append(">")
        for (String childXml : childElements) {
            xml.append(childXml)
        }
        if (hasText) {
            xml.append("text for $nodeName")
        }
        xml.append("</$nodeName>")

        return xml.toString();

    }

    private addAttributesFromGroup(String groupName, Map attributes, Document xsd) {
        assert groupName != null
        NodeList groupNodes = (NodeList) xpath.compile("/schema/attributeGroup[@name='$groupName']/*").evaluate(xsd, XPathConstants.NODESET)
        for (int i = 0; i < groupNodes.getLength(); i++) {
            def element = (Element) groupNodes.item(i)
            def nodeName = element.getTagName();
            if (nodeName.equals("xsd:attribute")) {
                attributes.put(element.getAttribute("name"), element.getAttribute("type"))
            } else if (nodeName.equals("xsd:attributeGroup")) {
                addAttributesFromGroup(element.getAttribute("ref"), attributes, xsd)
            } else if (nodeName.equals("xsd:anyAttribute")) {
                //don't do anything
            } else {
                throw new RuntimeException("Unknown element: $nodeName");
            }
        }

    }
}
