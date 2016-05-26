package liquibase.parser.yaml

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.exception.ParseException
import liquibase.resource.MockResourceAccessor
import spock.lang.Specification

class YamlParserTest extends Specification {

    def "throws exception if nothing matches"() {
        when:
        new YamlParser().parse("com/example/invalid.yaml", JUnitScope.instance)

        then:
        def e = thrown(ParseException)
        e.message == "Could not find file to parse: com/example/invalid.yaml"
    }

    def "can parse a simple file"() {
        when:
        def scope = createScope("com/example/test.yaml", "rootNode: 1")
        def parsedNode = new YamlParser().parse("com/example/test.yaml", scope)

        then:
        parsedNode.name == "rootNode"
        parsedNode.value == 1;
        parsedNode.children*.name == ["physicalPath"]
    }

    def "can parse a file with attributes"() {
        when:
        def scope = createScope("com/example/test.yaml", """
rootNode:
    attr1: A
    attr2: B
""")
        def parsedNode = new YamlParser().parse("com/example/test.yaml", scope)

        then:
        parsedNode.prettyPrint() == """
rootNode
    attr1: A
    attr2: B
    physicalPath: com/example/test.yaml
""".trim()
    }

    def "can parse a file with a list of attributes"() {
        when:
        def scope = createScope("com/example/test.yaml", """
rootNode:
    - attr1: A
    - attr2: B
""")
        def parsedNode = new YamlParser().parse("com/example/test.yaml", scope)

        then:
        parsedNode.prettyPrint() == """
rootNode
    attr1: A
    attr2: B
    physicalPath: com/example/test.yaml
""".trim()
    }

    def "nested nodes work"() {
        when:
        def scope = createScope("com/example/test.yaml", """
rootNode:
    child1: Child 1
    child2: Child 2
    parent:
        innerNode: inner node value
        innerNode2: inner node value 2
""")
        def parsedNode = new YamlParser().parse("com/example/test.yaml", scope)

        then:
        parsedNode.prettyPrint() == """
rootNode
    child1: Child 1
    child2: Child 2
    parent
        innerNode: inner node value
        innerNode2: inner node value 2
    physicalPath: com/example/test.yaml
""".trim()
    }

    def "nested node lists work"() {
        when:
        def scope = createScope("com/example/test.yaml", """
rootNode:
    child1: Child 1
    child2: Child 2
    parent:
        - innerNode: inner node value
        - innerNode2: inner node value 2
""")
        def parsedNode = new YamlParser().parse("com/example/test.yaml", scope)

        then:
        parsedNode.prettyPrint() == """
rootNode
    child1: Child 1
    child2: Child 2
    parent
        innerNode: inner node value
        innerNode2: inner node value 2
    physicalPath: com/example/test.yaml
""".trim()
    }

    def "invalid Yaml throws an exception"() {
        when:
        def scope = createScope("com/example/test.yaml", "rootNode")
        new YamlParser().parse("com/example/test.yaml", scope)

        then:
        def e = thrown(ParseException)
        e.message == "Syntax error in com/example/test.yaml: Can't construct a java object for tag:yaml.org,2002:java.util.Map; exception=No single argument constructor found for interface java.util.Map\n" +
                " in 'reader', line 1, column 1:\n" +
                "    rootNode\n" +
                "    ^\n" +
                "";

    }

    def createScope(String path, String data) {
        def mockAccessor = new MockResourceAccessor()
        mockAccessor.addData(path, data)
        mockAccessor.addMockXsd("rootNode")

        return JUnitScope.instance.child(Scope.Attr.resourceAccessor, mockAccessor)
    }
}
