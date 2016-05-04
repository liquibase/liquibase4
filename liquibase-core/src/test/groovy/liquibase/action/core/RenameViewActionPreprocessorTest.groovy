package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class RenameViewActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new RenameViewAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "oldName is created",
                        [
                                catalogName : "cat_name",
                                schemaName  : "schema_name",
                                oldViewName: "old_name",

                        ],
                        """
changeLog
    changeSet
        renameView
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
                                catalogName : "cat_name",
                                schemaName  : "schema_name",
                                newViewName: "new_name",

                        ],
                        """
changeLog
    changeSet
        renameView
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
                                catalogName : "cat_name",
                                schemaName  : "schema_name",
                                oldViewName: "old_name",
                                newViewName: "new_name",

                        ],
                        """
changeLog
    changeSet
        renameView
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