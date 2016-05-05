package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class DropStoredProceduresActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new DropStoredProceduresAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "container is set up",
                        [
                                catalogName   : "cat_name",
                                schemaName    : "schema_name",
                                procedureName     : "proc_name",
                        ],
                        """
changeLog
    changeSet
        dropStoredProcedures
            procedures
                procedure
                    name: proc_name
                    container
                        name: schema_name
                        container
                            name: cat_name
                        type: liquibase.item.core.Schema
"""
                ],

        ]
    }
}
