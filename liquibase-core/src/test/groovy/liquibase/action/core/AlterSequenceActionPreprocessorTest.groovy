package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class AlterSequenceActionPreprocessorTest extends AbstractActionPreprocessorTest {

        @Unroll("#featureName: #desc")
        def "preprocessor logic"() {
            when:
            def changeLog = createAndProcessChangelog(actionName, new AlterSequenceAction(), children)

            then:
            changeLog.prettyPrint() == expected.trim()

            where:
            [desc, actionName, children, expected] << [
                    [
                            "table reference is created if no other attributes",
                            null,
                            [
                                    catalogName: "cat_name",
                                    schemaName : "schema_name",
                                    sequenceName  : "seq_name",
                            ],
                            """
changeLog
    changeSet
        alterSequence
            sequence
                name: seq_name
                container
                    name: schema_name
                    container
                        name: cat_name
"""
                    ],


                    //-----------
                    [
                            "startValue is renamed",
                            null,
                            [
                                    catalogName: "cat_name",
                                    schemaName : "schema_name",
                                    sequenceName  : "seq_name",
                                    startValue: "13"
                            ],
                            """
changeLog
    changeSet
        alterSequence
            restartWith: 13
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
