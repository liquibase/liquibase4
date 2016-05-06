package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class InsertDataActionPreprocessorTest extends AbstractActionPreprocessorTest {
    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new InsertDataAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "table and columns are converted to rowData",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "table_name",

                                column     : [
                                        name : "col_name",
                                        value: "a value here"
                                ]
                        ],
                        """
changeLog
    changeSet
        insertData
            row
                column
                    name: col_name
                    value: a value here
                relation
                    name: table_name
                    container
                        name: schema_name
                        container
                            name: cat_name
"""
                ],

                //-----
                [
                        "convertValue is used",
                        [
                                tableName: "table_name",

                                column   : [
                                        name        : "col_name",
                                        valueNumeric: "13"
                                ]
                        ],
                        """
changeLog
    changeSet
        insertData
            row
                column
                    name: col_name
                    valueNumeric: 13
                relation
                    name: table_name
"""
                ],

                //-----
                [
                        "root tables is copied to nested row",
                        [
                                tableName: "table_name",
                                row: [
                                        column   : [
                                                name : "col_name",
                                                value: "13"
                                        ]
                                ]
                        ],
                        """
changeLog
    changeSet
        insertData
            row
                column
                    name: col_name
                    value: 13
                relation
                    name: table_name
"""
                ],

                //-----
                [
                        "can set table on row",
                        [
                                row: [
                                        tableName: "table_name",
                                        column   : [
                                                name : "col_name",
                                                value: "13"
                                        ]
                                ]
                        ],
                        """
changeLog
    changeSet
        insertData
            row
                column
                    name: col_name
                    value: 13
                relation
                    name: table_name
"""
                ],
        ]
    }
}
