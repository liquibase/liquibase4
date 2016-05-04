package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class CreateSequencesActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(actionName, new CreateSequencesAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, actionName, children, expected] << [
                [
                        "if schemaName is set, attributes on local object are moved to a sequence obj",
                        null,
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                sequenceName  : "seq_name",
                                cacheSize: "313",
                                cycle: "false",
                                incrementBy: "55",
                                maxValue: "513",
                                minValue: "44",
                                ordered: "true",
                                startValue: "95",
                        ],
                        """
changeLog
    changeSet
        createSequences
            sequences
                sequence
                    cacheSize: 313
                    cycle: false
                    incrementBy: 55
                    maxValue: 513
                    minValue: 44
                    ordered: true
                    startValue: 95
                    name: seq_name
                    schema
                        name: schema_name
                        container
                            name: cat_name
"""
                ],
        ]
    }
}
