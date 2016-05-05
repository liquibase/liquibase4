package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class CustomClassActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new CustomClassAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "class and param nodes are moved correctly",
                        [
                                class: "com.example.CustomClass",
                                param: "a",
                        ],
                        """
changeLog
    changeSet
        customClass
            customClass: com.example.CustomClass
            parameters
                param: a
"""
                ],
        ]
    }
}
