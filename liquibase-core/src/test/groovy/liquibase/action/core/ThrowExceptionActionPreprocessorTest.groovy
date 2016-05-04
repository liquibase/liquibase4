package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Specification
import spock.lang.Unroll

class ThrowExceptionActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new ThrowExceptionAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "exception is created from message",
                        [
                                message: "the message"

                        ],
                        """
changeLog
    changeSet
        throwException
            message: liquibase.action.core.ThrowExceptionAction\$ThrowExceptionActionException: the message
"""
                ],
        ]
    }
}
