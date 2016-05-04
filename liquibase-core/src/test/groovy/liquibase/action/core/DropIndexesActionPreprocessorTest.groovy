package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class DropIndexesActionPreprocessorTest extends AbstractActionPreprocessorTest {


    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new DropIndexesAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "index reference is set up",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "table_name",
                                indexName  : "idx_name",
                        ],
                        """
changeLog
    changeSet
        dropForeignKeys
            foreignKeys
                foreignKey
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
