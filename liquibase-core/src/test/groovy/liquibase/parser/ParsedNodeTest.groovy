package liquibase.parser

import liquibase.JUnitScope
import liquibase.changelog.ChangeLog
import liquibase.changelog.ChangeSet
import spock.lang.Specification

class ParsedNodeTest extends Specification {

    def "various add methods"() {
        expect:
        new ParsedNode("root")
                .addChild(new ParsedNode("child").setValue("child value"))
                .toString() == "ParsedNode{children=[ParsedNode{name=child, value=child value}], name=root}"

        new ParsedNode("root")
                .addChild(new ParsedNode("child1").setValue("child 1 value"))
                .addChild(new ParsedNode("child2").setValue("child 2 value"))
                .toString() == "ParsedNode{children=[ParsedNode{name=child1, value=child 1 value}, ParsedNode{name=child2, value=child 2 value}], name=root}"

        new ParsedNode("root")
                .setValue("a value")
                .toString() == "ParsedNode{name=root, value=a value}"

        new ParsedNode("root")
                .addChild("child", "child value")
                .toString() == "ParsedNode{children=[ParsedNode{name=child, value=child value}], name=root}"

        new ParsedNode("root")
                .addChild(new ParsedNode("duplicateChild").setValue("child 1 value"))
                .addChild(new ParsedNode("duplicateChild").setValue("child 2 value"))
                .toString() == "ParsedNode{children=[ParsedNode{name=duplicateChild, value=child 1 value}, ParsedNode{name=duplicateChild, value=child 2 value}], name=root}"
    }
}
