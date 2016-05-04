package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class AddLookupTableActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new AddLookupTableAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "table references are created",
                        [
                                existingTableCatalogName      : "new_cat",
                                existingTableSchemaName       : "new_schema",
                                existingTableName             : "new_tab",
                                existingColumnName           : "new_col",

                                newTableCatalogName: "new_cat",
                                newTableSchemaName : "new_schema",
                                newTableName       : "new_table",
                                newColumnName     : "new_col",
                        ],
                        """
changeLog
    changeSet
        addLookupTable
            existingColumn
                name: new_col
                    container
                        name: new_tab
                            schema
                                name: new_schema
                                container
                                    name: new_cat
            newColumn
                name: new_col
                    container
                        name: new_table
                            schema
                                name: new_schema
                                container
                                    name: new_cat
"""
                ],
        ]
    }
}
