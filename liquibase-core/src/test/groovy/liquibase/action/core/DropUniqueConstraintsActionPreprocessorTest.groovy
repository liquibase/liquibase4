package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class DropUniqueConstraintsActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new DropUniqueConstraintsAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "table is set up",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "table_name",
                        ],
                        """
changeLog
    changeSet
        dropUniqueConstraints
            constraints
                uniqueConstraint
                    container
                        name: table_name
                            schema
                                name: schema_name
                                container
                                    name: cat_name
"""
                ],

                //-----
                [
                        "just the constraintName works",
                        [
                                constraintName: "uq_name",
                        ],
                        """
changeLog
    changeSet
        dropUniqueConstraints
            constraints
                uniqueConstraint
                    name: uq_name

"""
                ],

                //-----
                [
                        "just the name works",
                        [
                                name: "uq_name",
                        ],
                        """
changeLog
    changeSet
        dropUniqueConstraints
            constraints
                uniqueConstraint
                    name: uq_name

"""

                ],
        ]
    }
}
