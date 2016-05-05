package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class AlterNullableActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(actionName, new AlterNullableAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, actionName, children, expected] << [
                [
                        "table references are created",
                        null,
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "tab_name",
                                columnName : "col_name",
                        ],
                        """
changeLog
    changeSet
        alterNullable
            column
                name: col_name
                container
                    name: tab_name
                    container
                        name: schema_name
                        container
                            name: cat_name
"""
                ],

//----
                [
                        "valueForExistingNulls is uses convertValueOptions",
                        null,
                        [
                                tableName                   : "tab_name",
                                columnName                  : "col_name",
                                valueForExistingNullsNumeric: "81"
                        ],
                        """
changeLog
    changeSet
        alterNullable
            valueForExistingNulls: 81
            column
                name: col_name
                container
                    name: tab_name"""
                ],

                //----
                [
                        "addNotNullConstraint sets nullable flag=false",
                        "addNotNullConstraint",
                        [
                                tableName                   : "tab_name",
                                columnName                  : "col_name",
                        ],
                        """
changeLog
    changeSet
        alterNullable
            nullable: false
            column
                name: col_name
                container
                    name: tab_name
"""
                ],

                //----
                [
                        "dropNotNullConstraint sets nullable flag=true",
                        "dropNotNullConstraint",
                        [
                                tableName                   : "tab_name",
                                columnName                  : "col_name",
                        ],
                        """
changeLog
    changeSet
        alterNullable
            nullable: true
            column
                name: col_name
                container
                    name: tab_name
"""
                ],

            //------
                [
                        "nullable=false is kept",
                        null,
                        [
                                tableName  : "tab_name",
                                columnName : "col_name",
                                nullable: "false"
                        ],
                        """
changeLog
    changeSet
        alterNullable
            nullable: false
            column
                name: col_name
                container
                    name: tab_name
"""
                ],

        ]
    }
}
