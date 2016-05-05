package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class RenameTableActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new RenameTableAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "oldName is created",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                oldTableName  : "old_name",

                        ],
                        """
changeLog
    changeSet
        renameTable
            oldName: old_name
                container
                    name: schema_name
                    container
                        name: cat_name
"""
                ],

//-----
                [
                        "newName is created",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                newTableName  : "new_name",

                        ],
                        """
changeLog
    changeSet
        renameTable
            newName: new_name
                container
                    name: schema_name
                    container
                        name: cat_name
"""
                ],

                //-----
                [
                        "old and new names get catalog/schema",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                oldTableName  : "old_name",
                                newTableName  : "new_name",

                        ],
                        """
changeLog
    changeSet
        renameTable
            oldName: old_name
                container
                    name: schema_name
                    container
                        name: cat_name
            newName: new_name
                container
                    name: schema_name
                    container
                        name: cat_name
"""
                ],

        ]
    }
}