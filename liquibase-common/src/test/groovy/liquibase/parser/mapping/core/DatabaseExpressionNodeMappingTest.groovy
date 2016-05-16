package liquibase.parser.mapping.core

import liquibase.JUnitScope
import liquibase.changelog.ChangeLog
import liquibase.parser.ParsedNode
import liquibase.parser.ParserFactory
import spock.lang.Specification
import spock.lang.Unroll

class DatabaseExpressionNodeMappingTest extends Specification {

    def "simple database correctly created from changelog nodes"() {
        when:
        def scope = JUnitScope.instance;

        def node = ParsedNode.createRootNode("changeLog");
        node.addChildren([
                changeSet: [
                        id: "testId",
                        author: "tester",
                        dbms: "database1"
                ]
        ])

        ChangeLog changeLog = scope.getSingleton(ParserFactory.class).parse(node, ChangeLog, scope);

        then:
        changeLog.getChangeSets().get(0).dbms.toString() == "database1"
    }

    def "complex database correctly created from changelog nodes"() {
        when:
        def scope = JUnitScope.instance;

        def node = ParsedNode.createRootNode("changeLog");
        node.addChildren([
                changeSet: [
                        id: "testId",
                        author: "tester",
                        dbms: "database1, database2"
                ]
        ])

        ChangeLog changeLog = scope.getSingleton(ParserFactory.class).parse(node, ChangeLog, scope);

        then:
        changeLog.getChangeSets().get(0).dbms.databases.size() == 2
        changeLog.getChangeSets().get(0).dbms.toString() == "database1, database2"
    }

    @Unroll
    def "null and empty database maps to null value"() {
        when:
        def scope = JUnitScope.instance;

        def node = ParsedNode.createRootNode("changeLog");
        node.addChildren([
                changeSet: [
                        id: "testId",
                        author: "tester",
                        dbms: database
                ]
        ])

        ChangeLog changeLog = scope.getSingleton(ParserFactory.class).parse(node, ChangeLog, scope);

        then:
        changeLog.getChangeSets().get(0).dbms == null

        where:
        database << [null, "", "   "]
    }

}
