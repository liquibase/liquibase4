package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class CreateViewsActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new CreateViewsAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "creates view node from attributes on action",
                        [
                                catalogName   : "cat_name",
                                schemaName    : "schema_name",
                                viewName      : "view_name",
                                definition    : "select * from table",
                                fullDefinition: "true",
                        ],
                        """
changeLog
    changeSet
        createViews
            views
                view
                    definition: select * from table
                    completeDefinition: true
                    name: view_name
                    schema
                        name: schema_name
                        container
                            name: cat_name
"""
                ],
        ]
    }
}
