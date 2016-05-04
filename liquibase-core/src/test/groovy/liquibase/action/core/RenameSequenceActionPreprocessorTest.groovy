package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class RenameSequenceActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new RenameSequenceAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "oldName is created",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                oldSequenceName  : "old_name",

                        ],
                        """
changeLog
    changeSet
        renameSequence
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
                                newSequenceName  : "new_name",

                        ],
                        """
changeLog
    changeSet
        renameSequence
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
                                oldSequenceName  : "old_name",
                                newSequenceName  : "new_name",

                        ],
                        """
changeLog
    changeSet
        renameSequence
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