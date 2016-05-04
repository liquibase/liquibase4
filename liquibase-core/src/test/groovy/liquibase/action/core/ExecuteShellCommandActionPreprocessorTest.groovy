package liquibase.action.core

import liquibase.action.AbstractActionPreprocessorTest
import spock.lang.Unroll

class ExecuteShellCommandActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new ExecuteShellCommandAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "os is split correctly",
                        [
                                os: "win,    c64",
                        ],
                        """
changeLog
    changeSet
        executeShellCommand
            osFilters: [win, c64]
            args
"""
                ],

                //----
                [
                        "args are moved",
                        [
                                arg: "arg 1"
                        ],
                        """
changeLog
    changeSet
        executeShellCommand
            args
                arg: arg 1
"""
                ],



        ]
    }
}
