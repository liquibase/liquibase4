package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class AlterRemarksActionPreprocessorTest extends AbstractActionPreprocessorTest {


    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(actionName, new AlterRemarksAction(), children)

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
                                tableName  : "tab_name",
                        ],
                        """
changeLog
    changeSet
        alterRemarks
            relation
                name: tab_name
                    schema
                        name: schema_name
                        container
                            name: cat_name
                type: liquibase.item.core.Table
"""
                ],

                //-----
                [
                        "column reference is created if columnName",
                        null,
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "tab_name",
                                columnName : "column_name"
                        ],
                        """
changeLog
    changeSet
        alterRemarks
            column
                name: column_name
                    container
                        name: tab_name
                            schema
                                name: schema_name
                                container
                                    name: cat_name
                type: liquibase.item.core.Column
"""
                ],

        ]
    }
}
