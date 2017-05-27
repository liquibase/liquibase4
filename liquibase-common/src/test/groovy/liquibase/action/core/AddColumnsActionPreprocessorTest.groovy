package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class AddColumnsActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new AddColumnsAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "table on the action is correctly applied to the column",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "tab_name",
                                column     : [
                                        name: "col_name"
                                ]
                        ],
                        """
changeLog
    changeSet
        addColumns
            columns
                column
                    name: col_name
                    relation
                        name: tab_name
                        container
                            name: schema_name
                            container
                                name: cat_name
                                    """
                ],

                //----
                [
                        "table on the column is correctly converted",
                        [
                                column: [
                                        catalogName: "cat_name",
                                        schemaName : "schema_name",
                                        tableName  : "tab_name",
                                        name       : "col_name"
                                ]
                        ],
                        """
changeLog
    changeSet
        addColumns
            columns
                column
                    name: col_name
                    relation
                        name: tab_name
                        container
                            name: schema_name
                            container
                                name: cat_name
                                    """
                ],

                //--------
                [
                        "defaultValueNumeric converts to defaultValue",
                        [
                                column: [
                                        defaultValueNumeric: "42.5"
                                ]
                        ],
                        """
changeLog
    changeSet
        addColumns
            columns
                column
                    defaultValue: 42.5
"""

                ],

                //--------
                [
                        "autoIncrement is moved correctly",
                        [
                                column: [
                                        name         : "col_name",
                                        autoIncrement: "true",
                                        startWith    : "51",
                                        incrementBy  : "81"
                                ]
                        ],
                        """
changeLog
    changeSet
        addColumns
            columns
                column
                    name: col_name
                    autoIncrementDetails
                        startWith: 51
                        incrementBy: 81
                        """

                ],

                //--------
                [
                        "primary key is setup correctly",
                        [
                                column: [
                                        name       : "col_name",
                                        constraints: [
                                                primaryKey          : "true",
                                                primaryKeyName      : "pk_name",
                                                primaryKeyTablespace: "pk_tablespace"
                                        ]
                                ]
                        ],
                        """
changeLog
    changeSet
        addColumns
            columns
                column
                    name: col_name
            primaryKey
                columns
                    primaryKeyColumn
                        name: col_name
                name: pk_name
                tablespace: pk_tablespace
            foreignKeys
                        """

                ],

                //--------
                [
                        "unique constraint is setup correctly",
                        [
                                column: [
                                        name       : "col_name",
                                        constraints: [
                                                unique              : "true",
                                                uniqueConstraintName: "uq_name",
                                                deferrable          : "true",
                                                initiallyDeferred   : "true"
                                        ]
                                ]
                        ],
                        """
changeLog
    changeSet
        addColumns
            columns
                column
                    name: col_name
            uniqueConstraints
                uniqueConstraint
                    name: uq_name
                    columns: [col_name]
                    deferrable: true
                    initiallyDeferred: true
            foreignKeys
                        """

                ],

                //--------
                [
                        "foreign key is setup correctly using 'references'",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "tab_name",
                                column     : [
                                        name       : "col_name",
                                        constraints: [
                                                foreignKeyName   : "fk_name",
                                                references       : "other_table(id)",
                                                deferrable       : "true",
                                                initiallyDeferred: "true"
                                        ]
                                ]
                        ],
                        """
changeLog
    changeSet
        addColumns
            columns
                column
                    name: col_name
                    relation
                        name: tab_name
                        container
                            name: schema_name
                            container
                                name: cat_name
            foreignKeys
                foreignKey
                    relation
                        name: tab_name
                        container
                            name: schema_name
                            container
                                name: cat_name
                    name: fk_name
                    referencedTable
                        name: other_table
                    columnChecks
                        columnCheck
                            baseColumn: col_name
                            referencedColumn: id
                    deferrable: true
                    initiallyDeferred: true
                       """
                ],

                //--------
                [
                        "foreign key is setup correctly using 'referencedTable'",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "tab_name",
                                column     : [
                                        name       : "col_name",
                                        constraints: [
                                                foreignKeyName            : "fk_name",
                                                referencedTableCatalogName: "other_cat",
                                                referencedTableSchemaName : "other_schema",
                                                referencedTableName       : "other_table",
                                                referencedColumnNames     : "other_col",
                                                deferrable                : "true",
                                                initiallyDeferred         : "true"
                                        ]
                                ]
                        ],
                        """
changeLog
    changeSet
        addColumns
            columns
                column
                    name: col_name
                    relation
                        name: tab_name
                        container
                            name: schema_name
                            container
                                name: cat_name
            foreignKeys
                foreignKey
                    relation
                        name: tab_name
                        container
                            name: schema_name
                            container
                                name: cat_name
                    name: fk_name
                    referencedTable
                        name: other_table
                        container
                            name: other_schema
                            container
                                name: other_cat
                    columnChecks
                        columnCheck
                            baseColumn: col_name
                            referencedColumn: other_col
                    deferrable: true
                    initiallyDeferred: true
                       """
                ],

                //--------
                [
                        "check constraint is coped correctly",
                        [
                                column: [
                                        name       : "col_name",
                                        catalogName: "cat_name",
                                        schemaName : "schema_name",
                                        tableName  : "tab_name",
                                        constraints: [
                                                checkConstraint  : "check_something()",
                                                deferrable       : "true",
                                                initiallyDeferred: "true",
                                        ]
                                ]
                        ],
                        """
changeLog
    changeSet
        addColumns
            columns
                column
                    name: col_name
                    relation
                        name: tab_name
                        container
                            name: schema_name
                            container
                                name: cat_name
            foreignKeys
            checkConstraints
                checkConstraint
                    body: check_something()
                    relation
                        name: tab_name
                        container
                            name: schema_name
                            container
                                name: cat_name
                        """

                ],

                //--------
                [
                        "nullable true is copied",
                        [
                                column: [
                                        name       : "col_name",
                                        tableName  : "tab_name",
                                        constraints: [
                                                nullable: "true"
                                        ]
                                ]
                        ],
                        """
changeLog
    changeSet
        addColumns
            columns
                column
                    name: col_name
                    relation
                        name: tab_name
                    nullable: true
            foreignKeys
                       """
                ],
                //--------
                [
                        "nullable false is copied",
                        [
                                column: [
                                        name       : "col_name",
                                        tableName  : "tab_name",
                                        constraints: [
                                                nullable: "false"
                                        ]
                                ]
                        ],
                        """
changeLog
    changeSet
        addColumns
            columns
                column
                    name: col_name
                    relation
                        name: tab_name
                    nullable: false
            foreignKeys
                       """
                ],

                //--------
                [
                        "deferrable and initially deferred is copied to all constraints",
                        [
                                column: [
                                        name       : "col_name",
                                        tableName  : "tab_name",
                                        constraints: [
                                                nullable         : "false",
                                                deferrable       : "true",
                                                initiallyDeferred: "true",
                                                unique           : "true",
                                                references       : "other_table(id)",
                                                checkConstraint  : "test_this()",
                                                primaryKey       : "true"

                                        ]
                                ]
                        ],
                        """
changeLog
    changeSet
        addColumns
            columns
                column
                    name: col_name
                    relation
                        name: tab_name
                    nullable: false
            primaryKey
                columns
                    primaryKeyColumn
                        name: col_name
            uniqueConstraints
                uniqueConstraint
                    columns: [col_name]
                    deferrable: true
                    initiallyDeferred: true
            foreignKeys
                foreignKey
                    relation
                        name: tab_name
                    referencedTable
                        name: other_table
                    columnChecks
                        columnCheck
                            baseColumn: col_name
                            referencedColumn: id
                    deferrable: true
                    initiallyDeferred: true
            checkConstraints
                checkConstraint
                    body: test_this()
                    relation
                        name: tab_name
                       """

                ],

        ]
    }
}
