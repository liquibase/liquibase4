package liquibase.parser.preprocessor.core.changelog

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.core.AddColumnsAction
import liquibase.exception.ParseException
import liquibase.parser.ParsedNode
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Test for methods in {@link AbstractActionPreprocessor}
 */
class TestAbstractActionPreprocessorTest extends Specification {

    @Unroll("#featureName: #desc")
    def "convertRelation"() {
        when:
        def preprocessor = new AbstractActionPreprocessor(AddColumnsAction) {
            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {

            }
        }

        def actionNode = ParsedNode.createRootNode("action")
        actionNode.addChildren(children)

        preprocessor.convertToRelationReferenceNode("catalogName", "schemaName", "tableName", actionNode)

        then:
        actionNode.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                ["empty", [:], "action"],
                ["just tableName", [tableName: "col_name"], """
action
    relation
        name: col_name
"""],

                //----
                ["table and schema", [
                        tableName : "col_name",
                        schemaName: "schema_name"
                ], """
action
    relation
        name: col_name
        container
            name: schema_name
"""],

                //----
                ["all fields", [
                        tableName  : "col_name",
                        schemaName : "schema_name",
                        catalogName: "cat_name",
                ], """
action
    relation
        name: col_name
        container
            name: schema_name
            container
                name: cat_name
"""],

                //----
                ["no table name", [
                        schemaName : "schema_name",
                        catalogName: "cat_name",
                ], """
action
    relation
        container
            name: schema_name
            container
                name: cat_name
"""],

                //----
                ["no schema", [
                        tableName  : "col_name",
                        catalogName: "cat_name",
                ], """
action
    relation
        name: col_name
        container
            container
                name: cat_name
"""],
        ]
    }

    @Unroll("#featureName: #desc")
    def "convertColumn"() {
        when:
        def preprocessor = new AbstractActionPreprocessor(AddColumnsAction) {
            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {

            }
        }

        def actionNode = ParsedNode.createRootNode("action")
        actionNode.addChildren(children)

        preprocessor.convertToColumnReferenceNode("catalogName", "schemaName", "tableName", "columnName", actionNode)

        then:
        actionNode.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                ["empty", [:], "action"],
                ["just columnName", [columnName: "col_name"], """
action
    column
        name: col_name"""],

                //-----
                ["column and table", [columnName: "col_name",
                                      tableName : "table_name"], """
action
    column
        name: col_name
        container
            name: table_name"""],

                //-----
                ["col, table and schema", [columnName: "col_name",
                                           tableName : "table_name",
                                           schemaName: "schema_name"], """
action
    column
        name: col_name
        container
            name: table_name
            container
                name: schema_name"""],

                //-----
                ["all fields", [columnName : "col_name",
                                tableName  : "table_name",
                                schemaName : "schema_name",
                                catalogName: "cat_name"], """
action
    column
        name: col_name
        container
            name: table_name
            container
                name: schema_name
                container
                    name: cat_name"""],

                //-----
                ["no column", [tableName  : "table_name",
                                schemaName : "schema_name",
                                catalogName: "cat_name"], """
action
    column
        container
            name: table_name
            container
                name: schema_name
                container
                    name: cat_name"""],

                //-----
                ["no table", [columnName : "col_name",
                                schemaName : "schema_name",
                                catalogName: "cat_name"], """
action
    column
        name: col_name
        container
            container
                name: schema_name
                container
                    name: cat_name"""],

        ]

    }


    @Unroll("#featureName: #desc")
    def "convertIndex"() {
        when:
        def preprocessor = new AbstractActionPreprocessor(AddColumnsAction) {
            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {

            }
        }

        def actionNode = ParsedNode.createRootNode("action")
        actionNode.addChildren(children)

        preprocessor.convertToIndexReferenceNode("catalogName", "schemaName", "tableName", "indexName", actionNode)

        then:
        actionNode.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                ["empty", [:], "action"],
                ["just indexName", [indexName: "col_name"], """
action
    index
        name: col_name"""],

                //-----
                ["index and table", [indexName: "col_name",
                                      tableName : "table_name"], """
action
    index
        name: col_name
        container
            name: table_name"""],

                //-----
                ["col, table and schema", [indexName: "col_name",
                                           tableName : "table_name",
                                           schemaName: "schema_name"], """
action
    index
        name: col_name
        container
            name: table_name
            container
                name: schema_name"""],

                //-----
                ["all fields", [indexName : "col_name",
                                tableName  : "table_name",
                                schemaName : "schema_name",
                                catalogName: "cat_name"], """
action
    index
        name: col_name
        container
            name: table_name
            container
                name: schema_name
                container
                    name: cat_name"""],

                //-----
                ["no index", [tableName  : "table_name",
                               schemaName : "schema_name",
                               catalogName: "cat_name"], """
action
    index
        container
            name: table_name
            container
                name: schema_name
                container
                    name: cat_name"""],

                //-----
                ["no table", [indexName : "col_name",
                              schemaName : "schema_name",
                              catalogName: "cat_name"], """
action
    index
        name: col_name
        container
            container
                name: schema_name
                container
                    name: cat_name"""],

        ]

    }


    @Unroll("#featureName: #desc")
    def "convertSequence"() {
        when:
        def preprocessor = new AbstractActionPreprocessor(AddColumnsAction) {
            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {

            }
        }

        def actionNode = ParsedNode.createRootNode("action")
        actionNode.addChildren(children)

        preprocessor.convertToSequenceReferenceNode("catalogName", "schemaName", "tableName", actionNode)

        then:
        actionNode.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                ["empty", [:], "action"],
                ["just tableName", [tableName: "col_name"], """
action
    sequence
        name: col_name
"""],

                //----
                ["table and schema", [
                        tableName : "col_name",
                        schemaName: "schema_name"
                ], """
action
    sequence
        name: col_name
        container
            name: schema_name
"""],

                //----
                ["all fields", [
                        tableName  : "col_name",
                        schemaName : "schema_name",
                        catalogName: "cat_name",
                ], """
action
    sequence
        name: col_name
        container
            name: schema_name
            container
                name: cat_name
"""],

                //----
                ["no table name", [
                        schemaName : "schema_name",
                        catalogName: "cat_name",
                ], """
action
    sequence
        container
            name: schema_name
            container
                name: cat_name
"""],

                //----
                ["no schema", [
                        tableName  : "col_name",
                        catalogName: "cat_name",
                ], """
action
    sequence
        name: col_name
        container
            container
                name: cat_name
"""],
        ]
    }

    @Unroll("#featureName: #desc")
    def "convertSchema"() {
        when:
        def preprocessor = new AbstractActionPreprocessor(AddColumnsAction) {
            @Override
            protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {

            }
        }

        def actionNode = ParsedNode.createRootNode("action")
        actionNode.addChildren(children)

        preprocessor.convertToSchemaReferenceNode("catalogName", "schemaName", actionNode)

        then:
        actionNode.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                ["empty", [:], "action"],
                ["just schemaName", [schemaName: "schema_name"], """
action
    schema
        name: schema_name
"""],

                //----
                ["all fields", [
                        schemaName : "schema_name",
                        catalogName: "cat_name",
                ], """
action
    schema
        name: schema_name
        container
            name: cat_name
"""],

                //----
                ["no schema name", [
                        catalogName: "cat_name",
                ], """
action
    schema
        container
            name: cat_name
"""],

        ]
    }
}
