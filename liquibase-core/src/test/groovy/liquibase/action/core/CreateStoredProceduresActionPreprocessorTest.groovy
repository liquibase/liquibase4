package liquibase.action.core

import liquibase.JUnitScope
import liquibase.action.AbstractActionPreprocessorTest
import liquibase.parser.ParsedNode
import spock.lang.Unroll

class CreateStoredProceduresActionPreprocessorTest extends AbstractActionPreprocessorTest {

    def "value on actionNode is converted to a body node"() {
        when:
        def changeLog = ParsedNode.createRootNode("changeLog")
        def changeSet = changeLog.addChild("changeSet")

        def actionNode = changeSet.addChild("createProcedure")
        actionNode.value = "body as value"

        new CreateStoredProceduresAction().createPreprocessor().process(changeLog, JUnitScope.instance)

        then:
        changeLog.prettyPrint() == """
changeLog
    changeSet
        createStoredProcedures
            procedures
                storedProcedure
                    body: body as value
""".trim()

    }

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(actionName, new CreateStoredProceduresAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, actionName, children, expected] << [
                [
                        "a procedure node is created if body is set",
                        null,
                        [
                                catalogName  : "cat_name",
                                schemaName   : "schema_name",
                                procedureName: "seq_name",
                                body         : "proc body here",
                                path         : "path/to/proc.sql",
                                encoding     : "utf-8"
                        ],
                        """
changeLog
    changeSet
        createStoredProcedures
            procedures
                storedProcedure
                    body: proc body here
                    path: path/to/proc.sql
                    encoding: utf-8
                    name: seq_name
                    container
                        name: schema_name
                        container
                            name: cat_name
"""
                ],
        ]
    }
}
