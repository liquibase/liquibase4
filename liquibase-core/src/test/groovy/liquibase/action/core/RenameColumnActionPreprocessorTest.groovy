package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class RenameColumnActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new RenameColumnAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "relation is created",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "table_name",

                        ],
                        """
changeLog
    changeSet
        renameColumn
            relation
                name: table_name
                    schema
                        name: schema_name
                        container
                            name: cat_name
"""
                ],

                //----
                [
                        "attributes are renamed",
                        [
                                oldColumnName : "old_col",
                                newColumnName : "new_col",
                                columnDataType: "col type",

                        ],
                        """
changeLog
    changeSet
        renameColumn
            oldName: old_col
            newName: new_col
            columnDefinition: col type
"""
                ],

        ]
    }
}