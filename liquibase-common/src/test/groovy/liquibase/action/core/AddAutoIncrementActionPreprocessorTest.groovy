package liquibase.action.core;

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll;

public class AddAutoIncrementActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new AddAutoIncrementAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "full column ref is converted correctly",
                        [
                                catalogName: "cat_name",
                                schemaName : "schema_name",
                                tableName  : "tab_name",
                                columnName : "col_name"
                        ], """
changeLog
    changeSet
        addAutoIncrement
            column
                name: col_name
                container
                    name: tab_name
                    container
                        name: schema_name
                        container
                            name: cat_name
            autoIncrementDetails
"""
                ],

                [
                        "just table and  column ref is converted correctly",
                        [
                                tableName : "tab_name",
                                columnName: "col_name"
                        ], """
changeLog
    changeSet
        addAutoIncrement
            column
                name: col_name
                container
                    name: tab_name
            autoIncrementDetails
"""
                ],

                // ----
                [
                        "columnDataType is renamed",
                        ["columnDataType": "dataType"],
                        """
changeLog
    changeSet
        addAutoIncrement
            dataType: dataType
            autoIncrementDetails
"""
                ],

                // ----
                [
                        "auto increment info is moved",
                        ["incrementBy": "34", "startWith": "313"],
                        """
changeLog
    changeSet
        addAutoIncrement
            autoIncrementDetails
                incrementBy: 34
                startWith: 313
"""
                ]
        ]


    }
}