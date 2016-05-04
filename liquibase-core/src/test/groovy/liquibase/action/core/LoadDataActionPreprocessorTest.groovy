package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class LoadDataActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new LoadDataAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "table is converted to rowData",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "table_name",

                        ],
                        """
changeLog
    changeSet
        loadData
            table
                name: table_name
                    schema
                        name: schema_name
                        container
                            name: cat_name
            columns
"""
                ],

                //----
                [
                        "attributes are renamed",
                        [
                                tableName  : "table_name",
                                file: "path/to/file.csv",
                                quotchar: "&",
                                commentLineStartsWith: "%",
                        ],
                        """
changeLog
    changeSet
        loadData
            path: path/to/file.csv
            quoteChar: &
            commentLineStart: %
            table
                name: table_name
            columns
"""
                ],


                //----
                [
                        "column is moved to columns",
                        [
                                tableName  : "table_name",
                                column: [
                                        name: "col_name",
                                        position: "48"
                                ]
                        ],
                        """
changeLog
    changeSet
        loadData
            table
                name: table_name
            columns
                column
                    header: col_name
                    index: 48
"""
                ],

                //----
                [
                        "primaryKey node is handled",
                        [
                                tableName  : "table_name",
                                primaryKey: "col_name",
                                column: [
                                        name: "col_name",
                                        position: "48"
                                ]
                        ],
                        """
changeLog
    changeSet
        loadData
            columnsForUpdateCheck: [col_name]
            table
                name: table_name
            columns
                column
                    header: col_name
                    index: 48
"""
                ],
        ]
    }
}
