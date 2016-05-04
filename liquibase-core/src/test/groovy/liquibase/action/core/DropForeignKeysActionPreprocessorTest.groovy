package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class DropForeignKeysActionPreprocessorTest extends AbstractActionPreprocessorTest {


    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new DropForeignKeysAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "baseRelation is set up",
                        [
                                baseTableCatalogName: "cat_name",
                                baseTableSchemaName : "schema_name",
                                baseTableName  : "table_name",
                        ],
                        """
changeLog
    changeSet
        dropForeignKeys
            foreignKeys
                foreignKey
                    container
                        name: table_name
                        container
                            name: schema_name
                            container
                                name: cat_name
"""
                ],

// ----
                [
                        "can just specify constraintName",
                        [
                                constraintName : "fk_name"
                        ],
                        """
changeLog
    changeSet
        dropForeignKeys
            foreignKeys
                foreignKey
                    name: fk_name
"""
                ],

// ----
                [
                        "can just specify constraintName and relation",
                        [
                                constraintName : "fk_name",
                                baseTableName: "base_table"
                        ],
                        """
changeLog
    changeSet
        dropForeignKeys
            foreignKeys
                foreignKey
                    name: fk_name
                    container
                        name: base_table
"""
                ],
        ]
    }
}
