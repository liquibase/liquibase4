package liquibase.parser.json

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.exception.ParseException
import liquibase.resource.MockResourceAccessor
import spock.lang.Specification

class JsonParserTest extends Specification {

    def "throws exception if nothing matches"() {
        when:
        new JsonParser().parse("com/example/invalid.json", JUnitScope.instance)

        then:
        def e = thrown(ParseException)
        e.message == "Could not find file to parse: com/example/invalid.json"
    }

    def "can parse a simple file"() {
        when:
        def scope = createScope("com/example/test.json", '{ "rootNode": 1}')
        def parsedNode = new JsonParser().parse("com/example/test.json", scope)

        then:
        parsedNode.name == "rootNode"
        parsedNode.value == 1L;
        parsedNode.children*.name == ["physicalPath"]
    }

    def "can parse a file with attributes"() {
        when:
        def scope = createScope("com/example/test.json", """
{
  "rootNode": {
    "attr1": "A",
    "attr2": "B"
  }
}
""")
        def parsedNode = new JsonParser().parse("com/example/test.json", scope)

        then:
        parsedNode.prettyPrint() == """
rootNode
    attr1: A
    attr2: B
    physicalPath: com/example/test.json
""".trim()
    }

    def "nested nodes work"() {
        when:
        def scope = createScope("com/example/test.json", """
{
  "rootNode": {
    "child1": "Child 1",
    "child2": "Child 2",
    "parent": {
      "innerNode": "inner node value",
      "innerNode2": "inner node value 2"
    }
  }
}
""")
        def parsedNode = new JsonParser().parse("com/example/test.json", scope)

        then:
        parsedNode.prettyPrint() == """
rootNode
    child1: Child 1
    child2: Child 2
    parent
        innerNode: inner node value
        innerNode2: inner node value 2
    physicalPath: com/example/test.json
""".trim()
    }

    def "invalid Json throws an exception"() {
        when:
        def scope = createScope("com/example/test.json", "rootNode {")
        new JsonParser().parse("com/example/test.json", scope)

        then:
        def e = thrown(ParseException)
        e.message == "com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 1 path \$";
    }

    def "can parse a file with an array of objects"() {
        when:
        def scope = createScope("com/example/test.json", """
{
  "rootNode": [
      {
        "Aattr1": "AA",
        "Aattr2": "AB"
      },
      {
        "Battr1": "BA",
        "Battr2": "BB"
      }
    ]
}
""")
        def parsedNode = new JsonParser().parse("com/example/test.json", scope)

        then:
        parsedNode.prettyPrint() == """
rootNode
    attr1: A
    attr2: B
    physicalPath: com/example/test.json
""".trim()
    }

    def createScope(String path, String data) {
        def mockAccessor = new MockResourceAccessor()
        mockAccessor.addData(path, data)
        mockAccessor.addMockXsd("rootNode")

        return JUnitScope.instance.child(Scope.Attr.resourceAccessor, mockAccessor)
    }

}
