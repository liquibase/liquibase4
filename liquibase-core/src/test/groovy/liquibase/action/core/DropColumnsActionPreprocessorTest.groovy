package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class DropColumnsActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new DropColumnsAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "column is created correctly",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "table_name",
                                columnName : "column_name"
                        ],
                        """
changeLog
    changeSet
        dropColumns
            columns
                column
                    name: column_name
                    container
                        name: table_name
                            schema
                                name: schema_name
                                container
                                    name: cat_name
"""
                ],

                //------
                [
                        "reference names on nested column is converted correctly",
                        [
                                columns: [
                                        column: [
                                                catalogName: "cat_name",
                                                schemaName : "schema_name",
                                                tableName  : "table_name",
                                                columnName : "column_name",
                                        ]
                                ]
                        ],
                        """
changeLog
    changeSet
        dropColumns
            columns
                column
                    name: column_name
                    container
                        name: table_name
                            schema
                                name: schema_name
                                container
                                    name: cat_name
"""
                ],
        ]
    }
}