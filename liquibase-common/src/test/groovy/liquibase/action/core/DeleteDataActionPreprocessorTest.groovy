package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class DeleteDataActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new DeleteDataAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "relation is created correctly",
                        [
                                catalogName: "cat_name",
                                schemaName: "schema_name",
                                tableName: "table_name",
                        ],
                        """
changeLog
    changeSet
        deleteData
            relation
                name: table_name
                container
                    name: schema_name
                    container
                        name: cat_name
"""
                ],
        ]
    }
}
