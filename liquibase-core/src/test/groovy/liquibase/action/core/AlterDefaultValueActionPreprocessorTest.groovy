package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import liquibase.parser.ParsedNode
import spock.lang.Specification
import spock.lang.Unroll

class AlterDefaultValueActionPreprocessorTest extends AbstractActionPreprocessorTest {


    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new AlterDefaultValueAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()
        assertion(changeLog);

        where:
        [desc, children, expected, assertion] << [
                [
                        "table references are created",
                        [
                                catalogName : "cat_name",
                                schemaName  : "schema_name",
                                tableName   : "tab_name",
                                columnName  : "col_name",
                                defaultValue: "new value"
                        ],
                        """
changeLog
    changeSet
        alterDefaultValue
            defaultValue: new value
            column
                name: col_name
                container
                    name: tab_name
                    container
                        name: schema_name
                        container
                            name: cat_name
""",
                        {
                            node ->
                                return true;
                        }
                ],

                //-----
                [
                        "defaultValue calls convertValueOptions",
                        [
                                tableName          : "tab_name",
                                columnName         : "col_name",
                                defaultValueNumeric: "81"
                        ],
                        """
changeLog
    changeSet
        alterDefaultValue
            defaultValue: 81
            column
                name: col_name
                container
                    name: tab_name
""",
                        {
                            node ->
                                ((ParsedNode) node).getChildren("defaultValue", true).each {
                                    assert it.value instanceof Number
                                }
                        }
                ],

        ]
    }


}
