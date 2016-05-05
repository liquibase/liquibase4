package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class AddPrimaryKeysActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new AddPrimaryKeysAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "table references are created",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "tab_name",
                                columnNames: "col_name"
                        ],
                        """
changeLog
    changeSet
        addPrimaryKeys
            primaryKeys
                primaryKey
                    relation
                        name: tab_name
                        container
                            name: schema_name
                            container
                                name: cat_name
                    columns
                        primaryKeyColumn
                            name: col_name
"""
                ],

//-----
                [
                        "comma separated columns are split",
                        [
                                tableName  : "tab_name",
                                columnNames: "col1, col2, col3"
                        ],
                        """
changeLog
    changeSet
        addPrimaryKeys
            primaryKeys
                primaryKey
                    relation
                        name: tab_name
                    columns
                        primaryKeyColumn
                            name: col1
                        primaryKeyColumn
                            name: col2
                        primaryKeyColumn
                            name: col3
"""
                ],

        ]
    }
}
