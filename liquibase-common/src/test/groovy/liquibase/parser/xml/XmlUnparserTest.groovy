package liquibase.parser.xml

import liquibase.JUnitScope
import liquibase.parser.ParsedNode
import spock.lang.Specification
import spock.lang.Unroll

class XmlUnparserTest extends Specification {

    @Unroll
    def "deparse"() {
        when:
        def stream = new ByteArrayOutputStream()
        def node = ParsedNode.createRootNode("root")
        node.addChildren(children)
        if (additionalSetup) {
            additionalSetup.call(node)
        }

        new XmlUnparser().unparse(node, stream, JUnitScope.instance)

        then:
        new String(stream.toByteArray()).replace("\r\n", "\n") == expected.replace("\r\n", "\n").trim()

        where:
        [children, additionalSetup, expected] << [
                [[:], null,"<?xml version=\"1.1\" encoding=\"utf-8\"?>\n<root/>"],

                //------
                [[emptyChild: null], null, """
<?xml version="1.1" encoding="utf-8"?>
<root>
    <emptyChild/>
</root>
"""],

                //------
                [[child: "child value"], null, """
<?xml version="1.1" encoding="utf-8"?>
<root>
    <child>child value</child>
</root>
"""],

                //------
                [[child: "child value"], {root -> ((ParsedNode) root).getChild("child", false).addMarker(ParsedNode.Marker.isAttribute)}, """
<?xml version="1.1" encoding="utf-8"?>
<root child="child value"/>
"""],

                //------
                [[child: "child value", number: 52], null, """
<?xml version="1.1" encoding="utf-8"?>
<root>
    <child>child value</child>
    <number>52</number>
</root>
"""],

                //------
                [[child: [grandchild1: "grandchild 1 value", grandchild2: "grandchild 2 value"]], null, """
<?xml version="1.1" encoding="utf-8"?>
<root>
    <child>
        <grandchild1>grandchild 1 value</grandchild1>
        <grandchild2>grandchild 2 value</grandchild2>
    </child>
</root>
"""],

                //------
                [[child: [grandchild: "grandchild 1 value"]], {root -> ((ParsedNode) root).getChild("child", false).value = "child value"}, """
<?xml version="1.1" encoding="utf-8"?>
<root>
    <child>
        <grandchild>grandchild 1 value</grandchild>
        child value
    </child>
</root>
"""],

                //------
                [[child: "value with angle <> brackets"], null, """
<?xml version="1.1" encoding="utf-8"?>
<root>
    <child>value with angle &lt;&gt; brackets</child>
</root>
"""],

                //------
                [[child: "value with\nmultiple lines"], null, """
<?xml version="1.1" encoding="utf-8"?>
<root>
    <child>
        value with
        multiple lines
    </child>
</root>
"""],
        ]
    }
}
