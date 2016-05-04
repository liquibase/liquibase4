package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class DropSequencesActionPreprocessorTest extends AbstractActionPreprocessorTest {
    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new DropSequencesAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "container is set up",
                        [
                                catalogName   : "cat_name",
                                schemaName    : "schema_name",
                                sequenceName     : "seq_name",
                        ],
                        """
changeLog
    changeSet
        dropSequences
            sequences
                sequence
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