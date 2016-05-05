package liquibase.action

import liquibase.action.core.AddAutoIncrementAction
import spock.lang.Specification
import spock.lang.Unroll

class ExecuteSqlActionPreprocessorTest extends AbstractActionPreprocessorTest {

    @Unroll("#featureName: #desc")
    def "preprocessor logic"() {
        when:
        def changeLog = createAndProcessChangelog(new ExecuteSqlAction(), children)

        then:
        changeLog.prettyPrint() == expected.trim()

        where:
        [desc, children, expected] << [
                [
                        "dbms is parsed",
                        [
                                dbms: "oracle,    mysql"
                        ], """
changeLog
    changeSet
        executeSql
            dbmsFilters: [oracle, mysql]
"""
                ],
        ]
    }
}
