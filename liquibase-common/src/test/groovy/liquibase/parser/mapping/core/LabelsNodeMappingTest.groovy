package liquibase.parser.mapping.core

import liquibase.JUnitScope
import liquibase.changelog.ChangeLog
import liquibase.parser.ParsedNode
import liquibase.parser.ParserFactory
import spock.lang.Specification
import spock.lang.Unroll

class LabelsNodeMappingTest extends Specification {

    def "simple labels correctly created from changelog nodes"() {
        when:
        def scope = JUnitScope.instance;

        def node = ParsedNode.createRootNode("changeLog");
        node.addChildren([
                changeSet: [
                        id: "testId",
                        author: "tester",
                        labels: "label1"
                ]
        ])

        ChangeLog changeLog = scope.getSingleton(ParserFactory.class).parse(node, ChangeLog, scope);

        then:
        changeLog.getChangeSets().get(0).labels.toString() == "label1"
    }

    def "complex label correctly created from changelog nodes"() {
        when:
        def scope = JUnitScope.instance;

        def node = ParsedNode.createRootNode("changeLog");
        node.addChildren([
                changeSet: [
                        id: "testId",
                        author: "tester",
                        labels: "label1, label2"
                ]
        ])

        ChangeLog changeLog = scope.getSingleton(ParserFactory.class).parse(node, ChangeLog, scope);

        then:
        changeLog.getChangeSets().get(0).labels.labels.size() == 2
        changeLog.getChangeSets().get(0).labels.toString() == "label1,label2"
    }

    @Unroll
    def "null and empty label maps to null value"() {
        when:
        def scope = JUnitScope.instance;

        def node = ParsedNode.createRootNode("changeLog");
        node.addChildren([
                changeSet: [
                        id: "testId",
                        author: "tester",
                        labels: label
                ]
        ])

        ChangeLog changeLog = scope.getSingleton(ParserFactory.class).parse(node, ChangeLog, scope);

        then:
        changeLog.getChangeSets().get(0).labels == null

        where:
        label << [null, "", "   "]
    }

}
