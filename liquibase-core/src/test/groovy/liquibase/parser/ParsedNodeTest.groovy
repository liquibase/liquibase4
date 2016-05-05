package liquibase.parser

import liquibase.exception.ParseException
import spock.lang.Specification

class ParsedNodeTest extends Specification {

    ParsedNode root
    ParsedNode child1
    ParsedNode child2

    ParsedNode child1A
    ParsedNode child1B
    ParsedNode child2A
    ParsedNode child2B

    def "setup"() {
        root = ParsedNode.createRootNode("root")
        child1 = root.addChild("child1").setValue("child 1 value")
        child2 = root.addChild("child2").setValue("child 2 value")

        child1A = child1.addChild("child1A").setValue("child 1A value")
        child1B = child1.addChild("child1B").setValue("child 1B value")
        child2A = child2.addChild("child2A").setValue("child 2A value")
        child2B = child2.addChild("child2B").setValue("child 2B value")
    }

    def "adding children"() {
        when:
        def root = ParsedNode.createRootNode("root").each {
            it.addChild("child").setValue("child value")
        }

        then:
        root.toString() == "ParsedNode{root, children=[ParsedNode{child=child value}]}"
        root.children.collect {
            assert it.parent == root
        }

        ParsedNode.createRootNode("root").each({
            it.addChild("child1").setValue("child 1 value")
            it.addChild("child2").setValue("child 2 value")
        }).toString() == "ParsedNode{root, children=[ParsedNode{child1=child 1 value}, ParsedNode{child2=child 2 value}]}"

        ParsedNode.createRootNode("root").each({
            it.addChild("duplicateChild").setValue("child 1 value")
            it.addChild("duplicateChild").setValue("child 2 value")
        }).toString() == "ParsedNode{root, children=[ParsedNode{duplicateChild=child 1 value}, ParsedNode{duplicateChild=child 2 value}]}"
    }

    def "removeChildren by name"() {
        when:
        root.removeChildren("child2")

        then:
        root.toString() == "ParsedNode{root, children=[ParsedNode{child1=child 1 value, children=[ParsedNode{child1A=child 1A value}, ParsedNode{child1B=child 1B value}]}]}"
    }

    def "getChild"() {
        expect:
        assert root.getChild("child1", false).is(child1)
        assert root.getChild("child2", false).is(child2)
        assert root.getChild("child1", true).is(child1)

        root.getChild("invalid", false) == null
        root.getChild("invalid", true).name == "invalid"

        when:
        root.addChild("child1").setValue("another child1")
        root.getChild("child1", true)

        then:
        def e = thrown(ParseException)
        e.message == "Multiple children match child1"
    }

    def "getChildValue"() {
        expect:
        root.getChildValue("child1", String, false) == "child 1 value"
        root.getChildValue("child1", String, false) == "child 1 value" //can get it a second time

        root.getChildValue("child1", String, true) == "child 1 value" //still get it
        root.getChildValue("child1", String, false) == null //but now gone
        root.getChildValue("child1", String, true) == null //still gone
    }

    def "getChildren"() {
        when:
        root.addChild("child1").setValue("another child1")
        root.addChild("child2").setValue("another child1")
        root.addChild("child1").setValue("yet another child1")
        root.addChild("child1A").setValue("a child 1a on root")

        then:
        root.getChildren("child1", false)*.name == ["child1", "child1", "child1"]
        root.getChildren("child2", false)*.name == ["child2", "child2"]
        root.getChildren("child2A", false).size() == 0 //don't pick up grandchildren
        root.getChildren("child2A", true)*.name == ["child2A"] //just the one grandchild
        root.getChildren("child1A", false)*.name == ["child1A"] //only one on root
        root.getChildren("child1A", true)*.name == ["child1A", "child1A"] //get the grand child too
        root.getChildren("invalid", false).size() == 0
        root.getChildren("invalid", true).size() == 0
    }

    def "remove"() {
        when:
        child2.remove()

        then:
        child2.parent == null
        root.getChild("child2", false) == null

        when:
        root.remove()
        then:
        def e = thrown(ParseException)
        e.message == "Cannot remove root node"
    }

    def "moveChildren"() {
        when:
        root.addChild("child1").setValue("another child1")
        root.addChild("child2").setValue("another child1")
        root.addChild("child1").setValue("yet another child1")
        root.addChild("child1A").setValue("a child 1a on root")

        root.moveChildren("child1", child2)

        then:
        assert child1.parent.is(child2)
        assert root.toString() == "ParsedNode{root, children=[ParsedNode{child2=child 2 value, children=[ParsedNode{child2A=child 2A value}, ParsedNode{child2B=child 2B value}, ParsedNode{child1=child 1 value, children=[ParsedNode{child1A=child 1A value}, ParsedNode{child1B=child 1B value}]}, ParsedNode{child1=another child1}, ParsedNode{child1=yet another child1}]}, ParsedNode{child2=another child1}, ParsedNode{child1A=a child 1a on root}]}"

        when:
        root.moveChildren("invalid", child2)
        then:
        assert root.toString() == "ParsedNode{root, children=[ParsedNode{child2=child 2 value, children=[ParsedNode{child2A=child 2A value}, ParsedNode{child2B=child 2B value}, ParsedNode{child1=child 1 value, children=[ParsedNode{child1A=child 1A value}, ParsedNode{child1B=child 1B value}]}, ParsedNode{child1=another child1}, ParsedNode{child1=yet another child1}]}, ParsedNode{child2=another child1}, ParsedNode{child1A=a child 1a on root}]}"
    }

    def "moveTo"() {
        when:
        child1.moveTo(child2)

        then:
        assert child1.parent.is(child2)
        assert root.toString() == "ParsedNode{root, children=[ParsedNode{child2=child 2 value, children=[ParsedNode{child2A=child 2A value}, ParsedNode{child2B=child 2B value}, ParsedNode{child1=child 1 value, children=[ParsedNode{child1A=child 1A value}, ParsedNode{child1B=child 1B value}]}]}]}"
    }

    def "print"() {
        expect:
        child1.prettyPrint() == """
root / child1: child 1 value
    child1A: child 1A value
    child1B: child 1B value
""".trim()

        child2.prettyPrint() == """
root / child2: child 2 value
    child2A: child 2A value
    child2B: child 2B value
""".trim()

        child1A.prettyPrint() == """
root / child1 / child1A: child 1A value
""".trim()

        root.prettyPrint() == """
root
    child1: child 1 value
        child1A: child 1A value
        child1B: child 1B value
    child2: child 2 value
        child2A: child 2A value
        child2B: child 2B value
""".trim()
    }

    def "copy"() {
        when:
        def root = ParsedNode.createRootNode("root")
        def child1 = root.addChild("child1")
        child1.fileName = "path/to/file.txt"
        child1.lineNumber = 3
        child1.columnNumber = 65
        child1.rename("newChild1")

        def child2 = root.addChild("child2")
        child2.fileName = "path/to/file2.txt"
        child2.lineNumber = 312
        child2.columnNumber = 6315

        def child1Copy = child1.copyTo(child2)

        then:
        root.prettyPrint() == """
root
    newChild1
    child2
        newChild1
""".trim()

        assert !child1Copy.is(child1)
        child1Copy.fileName == "path/to/file.txt"
        child1Copy.lineNumber == 3
        child1Copy.columnNumber == 65
        child1Copy.originalName == "child1"
        child1Copy.name == "newChild1"

    }
}
