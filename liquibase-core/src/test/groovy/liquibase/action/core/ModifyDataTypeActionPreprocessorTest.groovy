package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class ModifyDataTypeActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new ModifyDataTypeAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "relation is created",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "table_name",
                                columnName : "column_name"

                        ],
                        """
changeLog
    changeSet
        modifyDataType
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