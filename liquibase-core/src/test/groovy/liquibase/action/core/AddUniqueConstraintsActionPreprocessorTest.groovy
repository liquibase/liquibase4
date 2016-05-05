package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class AddUniqueConstraintsActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new AddUniqueConstraintsAction(), children)

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
        addUniqueConstraints
            uniqueConstraints
                uniqueConstraint
                    relation
                        name: tab_name
                        container
                            name: schema_name
                            container
                                name: cat_name
                    columns: [col_name]
"""
                ],

                //---------
                [
                        "constraint options are copied",
                        [
                                tableName        : "tab_name",
                                columnNames      : "col_name",
                                deferrable       : "true",
                                disabled         : "true",
                                initiallyDeferred: "true",
                                constraintName   : "uq_name"
                        ],
                        """
changeLog
    changeSet
        addUniqueConstraints
            uniqueConstraints
                uniqueConstraint
                    relation
                        name: tab_name
                    disabled: true
                    deferrable: true
                    initiallyDeferred: true
                    name: uq_name
                    columns: [col_name]
"""
                ],

                //-----
                [
                        "column names are split",
                        [
                                tableName  : "tab_name",
                                columnNames: "col1,   col2" //extra space so we see they are trimmed correctly
                        ],
                        """
changeLog
    changeSet
        addUniqueConstraints
            uniqueConstraints
                uniqueConstraint
                    relation
                        name: tab_name
                    columns: [col1, col2]
"""
                ],

        ]
    }
}
