package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class DropPrimaryKeysActionPreprocessorTest extends AbstractActionPreprocessorTest {
    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new DropPrimaryKeysAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "table reference is set up",
                        [
                                catalogName   : "cat_name",
                                schemaName    : "schema_name",
                                tableName     : "table_name",
                                constraintName: "pk_name",
                        ],
                        """
changeLog
    changeSet
        dropPrimaryKeys
            primaryKeys
                primaryKey
                    container
                        name: table_name
                            schema
                                name: schema_name
                                container
                                    name: cat_name
                    name: pk_name
"""
                ],

        ]
    }
}
