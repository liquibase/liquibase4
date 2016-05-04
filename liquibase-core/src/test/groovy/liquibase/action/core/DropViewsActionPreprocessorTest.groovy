package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class DropViewsActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new DropViewsAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "container is set up",
                        [
                                catalogName   : "cat_name",
                                schemaName    : "schema_name",
                                viewName     : "view_name",
                        ],
                        """
changeLog
    changeSet
        dropViews
            views
                view
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
