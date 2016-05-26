package liquibase.parser.yaml

import liquibase.JUnitScope
import liquibase.parser.ParsedNode
import liquibase.parser.Parser
import spock.lang.Specification
import spock.lang.Unroll

class YamlUnparserTest extends Specification {

    @Unroll
    def "unparse"() {
        when:
        def stream = new ByteArrayOutputStream()
        def node = ParsedNode.createRootNode("root")
        node.addChildren(children)
        if (additionalSetup) {
            additionalSetup.call(node)
        }

        new YamlUnparser().unparse(node, stream, JUnitScope.instance)

        then:
        new String(stream.toByteArray()).replace("\r\n", "\n").trim() == expected.replace("\r\n", "\n").trim()

        where:
        [children, additionalSetup, expected] << [
                [[:], { rootNode -> rootNode.value = "root value" }, "root: root value"],

                //------
                [[child1: "child value"], null, """
root:
    child1: child value
"""],

                //------
                [[:], { ParsedNode rootNode ->
                    rootNode.addChild("child1").value = "child1 value 1";
                    rootNode.addChild("child2").value = "child2 value 1";
                    rootNode.addChild("child1").value = "child1 value 2";
                }, """
root:
-   child1: child1 value 1
-   child2: child2 value 1
-   child1: child1 value 2
"""],

                //------
                [[:], { ParsedNode rootNode ->
                    def childNode = rootNode.addChild("child1")
                    childNode.addChild("child1a").value = "child1a value 1";
                    childNode.addChild("child1b").value = "child1b value 1";
                    childNode = childNode.addChild("child1a");
                    childNode.addChild("grandChild1")
                    childNode.addChild("grandChild2").value = "grand child 2 value"
                    childNode.addChild("grandChild1").value = "grand child 1 value 2"

                    childNode = rootNode.addChild("child2");
                    childNode.addChild("child2a");
                    childNode.addChild("child2b").addChild("grandChild3");

                    rootNode.addChild("child1").value = "child1 value 2";
                }, """
root:
-   child1:
    -   child1a: child1a value 1
    -   child1b: child1b value 1
    -   child1a:
        -   grandChild1: null
        -   grandChild2: grand child 2 value
        -   grandChild1: grand child 1 value 2
-   child2:
        child2a: null
        child2b:
            grandChild3: null
-   child1: child1 value 2
"""],

                //------
                [[nullChild: null], null, """
root:
    nullChild: null
"""],

                //------
                [[child: "child value", number: 52], null, """
root:
    child: child value
    number: 52
"""],

                //------
                [[child: [grandchild1: "grandchild 1 value", grandchild2: "grandchild 2 value"]], null, """
root:
    child:
        grandchild1: grandchild 1 value
        grandchild2: grandchild 2 value
"""],

                //------
                [[child: "value with\nmultiple lines"], null, """
root:
    child: |-
        value with
        multiple lines
"""],

        ]
    }

}
