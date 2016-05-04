package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class CreateIndexesActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(actionName, new CreateIndexesAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, actionName, children, expected] << [
                [
                        "table reference is created",
                        null,
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "table_name",
                        ],
                        """
changeLog
    changeSet
        createIndexes
            indexes
                index
                    relation
                        name: table_name
                            schema
                                name: schema_name
                                container
                                    name: cat_name
                    columns
"""
                ],

                //--------------
                [
                        "index attributes on action are moved to the index object",
                        null,
                        [
                                tableName  : "table_name",
                                indexName: "index_name",
                                tablespace: "tbsp",
                                clustered: "true",
                                unique: "true",
                                columns: [
                                        column1: [
                                                name: "col1"
                                        ],
                                        column2: [
                                                name: "col2"
                                        ]
                                ]


                        ],
                        """
changeLog
    changeSet
        createIndexes
            columns
                column1
                    name: col1
                column2
                    name: col2
            indexes
                index
                    relation
                        name: table_name
                    name: index_name
                    tablespace: tbsp
                    clustered: true
                    unique: true
                    columns
"""
                ],

        ]
    }
}
