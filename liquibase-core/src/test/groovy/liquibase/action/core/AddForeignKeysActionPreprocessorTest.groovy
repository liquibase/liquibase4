package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class AddForeignKeysActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new AddForeignKeysAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "baseTable and reference fields work",
                        [
                                baseTableCatalogName      : "base_cat",
                                baseTableSchemaName       : "base_schema",
                                baseTableName             : "base_tab",
                                baseColumnNames           : "base_col",

                                referencedTableCatalogName: "ref_cat",
                                referencedTableSchemaName : "ref_schema",
                                referencedTableName       : "ref_table",
                                referencedColumnNames     : "ref_col",
                        ],
                        """
changeLog
    changeSet
        addForeignKeys
            foreignKeys
                foreignKey
                    relation
                        name: base_tab
                            schema
                                name: base_schema
                                container
                                    name: base_cat
                    referencedTable
                        name: ref_table
                            schema
                                name: ref_schema
                                container
                                    name: ref_cat
                    columnChecks
                        columnCheck
                            baseColumn: base_col
                            referencedColumn: ref_col
                                                                """
                ],

                //-------
                [
                        "config flags are copied correctly, including two-word 'no action' and wrong-case 'RESTRICT'",
                        [
                                baseTableName        : "base_tab",
                                baseColumnNames      : "base_col",

                                referencedTableName  : "ref_table",
                                referencedColumnNames: "ref_col",

                                constraintName       : "fk_name",
                                deferrable           : "true",
                                initiallyDeferred    : "true",
                                onDelete             : "RESTRICT",
                                onUpdate             : "NO ACTION"
                        ],
                        """
changeLog
    changeSet
        addForeignKeys
            foreignKeys
                foreignKey
                    relation
                        name: base_tab
                    name: fk_name
                    referencedTable
                        name: ref_table
                    columnChecks
                        columnCheck
                            baseColumn: base_col
                            referencedColumn: ref_col
                    deferrable: true
                    initiallyDeferred: true
                    deleteRule: restrict
                    updateRule: noAction
"""
                ],
        ]
    }
}
