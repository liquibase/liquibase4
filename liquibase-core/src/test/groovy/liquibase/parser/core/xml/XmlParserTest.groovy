package liquibase.parser.core.xml

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.exception.ParseException
import liquibase.resource.MockResourceAccessor
import spock.lang.Specification

class XmlParserTest extends Specification {

    static MOCK_XSD_HEADER = "xmlns='http://www.liquibase.org/xml/ns/mock' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://www.liquibase.org/xml/ns/mock http://www.liquibase.org/xml/ns/dbchangelog/mock.xsd'"

    def "throws exception if nothing matches"() {
        when:
        new XmlParser().parse("com/example/invalid.xml", JUnitScope.instance)

        then:
        def e = thrown(ParseException)
        e.message == "liquibase.exception.ParseException: Could not find any files that match com/example/invalid.xml"
    }

    def "can parse a simple file"() {
        when:
        def scope = createScope("com/example/test.xml", "<rootNode $MOCK_XSD_HEADER/>")
        def parsedNode = new XmlParser().parse("com/example/test.xml", scope)

        then:
        parsedNode.name == "rootNode"
        parsedNode.children*.name == ["schemaLocation"]
    }

    def "can parse a file with attributes"() {
        when:
        def scope = createScope("com/example/test.xml", "<rootNode $MOCK_XSD_HEADER attr1='A' attr2='B'/>")
        def parsedNode = new XmlParser().parse("com/example/test.xml", scope)

        then:
        parsedNode.toString() == "ParsedNode{children=[ParsedNode{name=schemaLocation, value=http://www.liquibase.org/xml/ns/mock http://www.liquibase.org/xml/ns/dbchangelog/mock.xsd}, ParsedNode{name=attr1, value=A}, ParsedNode{name=attr2, value=B}], name=rootNode}"
    }

    def "can parse a file with attributes and a text body"() {
        when:
        def scope = createScope("com/example/test.xml", "<rootNode $MOCK_XSD_HEADER attr1='A' attr2='B'>Body Here</rootNode>")
        def parsedNode = new XmlParser().parse("com/example/test.xml", scope)

        then:
        parsedNode.toString() == "ParsedNode{children=[ParsedNode{name=schemaLocation, value=http://www.liquibase.org/xml/ns/mock http://www.liquibase.org/xml/ns/dbchangelog/mock.xsd}, ParsedNode{name=attr1, value=A}, ParsedNode{name=attr2, value=B}], name=rootNode, value=Body Here}"
    }

    def "whitespace around text body is ignored"() {
        when:
        def scope = createScope("com/example/test.xml", "<rootNode $MOCK_XSD_HEADER>    Body Here                  </rootNode>")
        def parsedNode = new XmlParser().parse("com/example/test.xml", scope)

        then:
        parsedNode.toString() == "ParsedNode{children=[ParsedNode{name=schemaLocation, value=http://www.liquibase.org/xml/ns/mock http://www.liquibase.org/xml/ns/dbchangelog/mock.xsd}], name=rootNode, value=Body Here}"
    }

    def "nested nodes work"() {
        when:
        def scope = createScope("com/example/test.xml", """
<rootNode $MOCK_XSD_HEADER>
    <child1>Child 1</child1>
    <child2>Child 2</child2>
    <child1>Child 1 again</child1>
    <parent>
        <innerNode/>
        <innerNode2>Inner Node</innerNode2>
    </parent>
</rootNode>""")
        def parsedNode = new XmlParser().parse("com/example/test.xml", scope)

        then:
        parsedNode.toString().replaceAll("[\\s\\r\\n]", "") == """ParsedNode{children=[
    ParsedNode{name=schemaLocation,value=http://www.liquibase.org/xml/ns/mockhttp://www.liquibase.org/xml/ns/dbchangelog/mock.xsd},
    ParsedNode{name=child1, value=Child 1},
    ParsedNode{name=child2, value=Child 2},
    ParsedNode{name=child1, value=Child 1 again},
    ParsedNode{children=[
        ParsedNode{name=innerNode},
        ParsedNode{name=innerNode2, value=Inner Node}
    ], name=parent}
], name=rootNode
}""".replaceAll("[\\s\\r\\n]", "") //clean up formatting introduced by breaking up for readability
    }

    def "invalid XML throws an exception"() {
        when:
        def scope = createScope("com/example/test.xml", "<rootNode $MOCK_XSD_HEADER></invalid>")
        new XmlParser().parse("com/example/test.xml", scope)

        then:
        def e = thrown(ParseException)
        e.message == "org.xml.sax.SAXParseException; lineNumber: 1; columnNumber: 223; The element type \"rootNode\" must be terminated by the matching end-tag \"</rootNode>\"."

    }

    def "invalid elements throw an exception"() {
        when:
        def scope = createScope("com/example/test.xml", """
<databaseChangeLog $MOCK_XSD_HEADER>
    <invalid/>
</databaseChangeLog>
""");

        scope.getSingleton(XmlParser.class).parse("com/example/test.xml", scope)

        then:
        def e = thrown(ParseException)
        e.message == "org.xml.sax.SAXParseException; lineNumber: 2; columnNumber: 230; cvc-elt.1: Cannot find the declaration of element 'databaseChangeLog'."

    }

    def createScope(String path, String data) {
        def mockAccessor = new MockResourceAccessor()
        mockAccessor.addData(path, data)
        mockAccessor.addData("liquibase/parser/core/xml/mock.xsd", """
<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema"
    targetNamespace="http://www.liquibase.org/xml/ns/mock" xmlns="http://www.liquibase.org/xml/ns/mock"
	elementFormDefault="qualified">
  <xsd:element name="rootNode"/>
</xsd:schema>
""")

        return JUnitScope.instance.child(Scope.Attr.resourceAccessor, mockAccessor)
    }
}
