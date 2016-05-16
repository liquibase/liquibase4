package liquibase.parser.mapping.core

import liquibase.JUnitScope
import liquibase.changelog.ChangeLog
import liquibase.parser.ParsedNode
import liquibase.parser.ParserFactory
import spock.lang.Specification
import spock.lang.Unroll

class ContextExpressionNodeMappingTest extends Specification {

    def "simple context correctly created from changelog nodes"() {
        when:
        def scope = JUnitScope.instance;

        def node = ParsedNode.createRootNode("changeLog");
        node.addChildren([
                changeSet: [
                        id: "testId",
                        author: "tester",
                        contexts: "context1"
                ]
        ])

        ChangeLog changeLog = scope.getSingleton(ParserFactory.class).parse(node, ChangeLog, scope);

        then:
        changeLog.getChangeSets().get(0).contexts.toString() == "context1"
    }

    def "complex context correctly created from changelog nodes"() {
        when:
        def scope = JUnitScope.instance;

        def node = ParsedNode.createRootNode("changeLog");
        node.addChildren([
                changeSet: [
                        id: "testId",
                        author: "tester",
                        contexts: "context1, context2"
                ]
        ])

        ChangeLog changeLog = scope.getSingleton(ParserFactory.class).parse(node, ChangeLog, scope);

        then:
        changeLog.getChangeSets().get(0).contexts.contexts.size() == 2
        changeLog.getChangeSets().get(0).contexts.toString() == "context1, context2"
    }

    @Unroll
    def "null and empty context maps to null value"() {
        when:
        def scope = JUnitScope.instance;

        def node = ParsedNode.createRootNode("changeLog");
        node.addChildren([
                changeSet: [
                        id: "testId",
                        author: "tester",
                        contexts: context
                ]
        ])

        ChangeLog changeLog = scope.getSingleton(ParserFactory.class).parse(node, ChangeLog, scope);

        then:
        changeLog.getChangeSets().get(0).contexts == null

        where:
        context << [null, "", "   "]
    }

}
