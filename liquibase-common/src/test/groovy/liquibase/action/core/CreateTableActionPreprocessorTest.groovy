package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class CreateTableActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new CreateTableAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "simple createTable",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "table_name",
                                column     : [
                                        name: "col_name",
                                        type: "int"
                                ],
                        ],
                        """
changeLog
    changeSet
        createTable
            table
                name: table_name
                schema
                    name: schema_name
                    container
                        name: cat_name
            columns
                column
                    name: col_name
                    type: int
                    relation
                        name: table_name
                        container
                            name: schema_name
                            container
                                name: cat_name

"""
                ],

                //---
                [
                        "table-related attributes get moved to the table node",
                        [
                                tableName : "table_name",
                                remarks   : "some remarks",
                                tablespace: "tbsp",
                                column    : [
                                        name: "col_name",
                                        type: "int"
                                ],
                        ],
                        """
changeLog
    changeSet
        createTable
            table
                name: table_name
                tablespace: tbsp
                remarks: some remarks
            columns
                column
                    name: col_name
                    type: int
                    relation
                        name: table_name

"""

                ],

                //---
                [
                        "foreign keys get set up correctly with 'references'",
                        [
                                tableName: "table_name",
                                column   : [
                                        name       : "col_name",
                                        type       : "int",
                                        constraints: [
                                                foreignKeyName   : "fk_name",
                                                references       : "other(id)",
                                                deferrable       : "true",
                                                initiallyDeferred: "true",
                                                deleteCascade    : "true",
                                        ]
                                ],
                        ],
                        """
changeLog
    changeSet
        createTable
            table
                name: table_name
            columns
                column
                    name: col_name
                    type: int
                    relation
                        name: table_name
            foreignKeys
                foreignKey
                    relation
                        name: table_name
                    name: fk_name
                    referencedTable
                        name: other
                    columnChecks
                        columnCheck
                            baseColumn: col_name
                            referencedColumn: id
                    deferrable: true
                    initiallyDeferred: true
                    deleteRule: cascade
"""

                ],

                //---
                [
                        "foreign keys get set up correctly with 'referencedTable' etc.",
                        [
                                tableName: "table_name",
                                column   : [
                                        name       : "col_name",
                                        type       : "int",
                                        constraints: [
                                                foreignKeyName            : "fk_name",
                                                referencedTableName       : "other_table",
                                                referencedTableCatalogName: "other_cat",
                                                referencedTableSchemaName : "other_schem",
                                                referencedColumnNames     : "other_col",
                                                deferrable                : "true",
                                                initiallyDeferred         : "true",
                                                deleteCascade             : "true",
                                        ]
                                ],
                        ],
                        """
changeLog
    changeSet
        createTable
            table
                name: table_name
            columns
                column
                    name: col_name
                    type: int
                    relation
                        name: table_name
            foreignKeys
                foreignKey
                    relation
                        name: table_name
                    name: fk_name
                    referencedTable
                        name: other_table
                        container
                            name: other_schem
                            container
                                name: other_cat
                    columnChecks
                        columnCheck
                            baseColumn: col_name
                            referencedColumn: other_col
                    deferrable: true
                    initiallyDeferred: true
                    deleteRule: cascade
"""

                ],
                //---
                [
                        "primary keys get set up correctly",
                        [
                                tableName: "table_name",
                                column   : [
                                        name       : "col_name",
                                        type       : "int",
                                        constraints: [
                                                primaryKeyName: "pk_name",
                                                primaryKey    : "true",
                                        ]
                                ],
                        ],
                        """
changeLog
    changeSet
        createTable
            table
                name: table_name
            columns
                column
                    name: col_name
                    type: int
                    relation
                        name: table_name
            primaryKey
                columns
                    primaryKeyColumn
                        name: col_name
                name: pk_name
            foreignKeys
"""

                ],

                //---
                [
                        "unique constraint gets set up correctly",
                        [
                                tableName: "table_name",
                                column   : [
                                        name       : "col_name",
                                        type       : "int",
                                        constraints: [
                                                uniqueConstraintName: "uk_name",
                                                unique              : "true",
                                        ]
                                ],
                        ],
                        """
changeLog
    changeSet
        createTable
            table
                name: table_name
            columns
                column
                    name: col_name
                    type: int
                    relation
                        name: table_name
            uniqueConstraints
                uniqueConstraint
                    name: uk_name
                    columns: [col_name]
            foreignKeys
"""

                ],
        ]
    }
}
