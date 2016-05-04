package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class DropTablesActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new DropTablesAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "container is set up",
                        [
                                catalogName   : "cat_name",
                                schemaName    : "schema_name",
                                tableName     : "table_name",
                        ],
                        """
changeLog
    changeSet
        dropTables
            tables
                relation
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
