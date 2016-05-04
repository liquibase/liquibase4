package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class DropAllForeignKeysActionPreprocessorTest extends AbstractActionPreprocessorTest {
    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new DropAllForeignKeysAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "relation is created correctly",
                        [
                                baseTableCatalogName: "cat_name",
                                baseTableSchemaName : "schema_name",
                                baseTableName  : "table_name",
                        ],
                        """
changeLog
    changeSet
        dropAllForeignKeys
            table
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
