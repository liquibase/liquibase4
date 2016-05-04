package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class UpdateDataActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new UpdateDataAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "relation is converted",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "table_name",

                        ],
                        """
changeLog
    changeSet
        updateData
            relation
                name: table_name
                    schema
                        name: schema_name
                        container
                            name: cat_name
            columns
"""
                ],

                //-----
                [
                        "column is moved to columns",
                        [
                                tableName  : "table_name",
                                column: [
                                        name: "col_name",
                                        value: "new value"
                                ]

                        ],
                        """
changeLog
    changeSet
        updateData
            relation
                name: table_name
            columns
                column
                    name: col_name
                    value: new value
"""
                ],

                //-----
                [
                        "value is converted",
                        [
                                tableName  : "table_name",
                                column: [
                                        name: "col_name",
                                        valueNumeric: "52"
                                ]

                        ],
                        """
changeLog
    changeSet
        updateData
            relation
                name: table_name
            columns
                column
                    name: col_name
                    value: 52
"""
                ],
        ]
    }
}
