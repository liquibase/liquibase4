package liquibase

import liquibase.database.Database
import liquibase.database.core.MockDatabase
import spock.lang.Specification
import spock.lang.Unroll

import static org.hamcrest.Matchers.containsInAnyOrder

class DatabaseExpressionTest extends Specification {


    @Unroll("featureName: '#string'")
    def "string constructor splits on commas correctly"() {
        expect:
        def expression = new DatabaseExpression(string).getDatabases()
        expression containsInAnyOrder(expected.toArray())

        where:
        string    | expected
        ""        | []
        "   "     | []
        "a,b"     | ["a", "b"]
        "a, b"    | ["a", "b"]
        " a , b " | ["a", "b"]
    }

    @Unroll("#featureName: testExpression #testExpression currentDatabase: #currentDatabase")
    def "run when at least one expression matches"() {
        expect:
        assert new DatabaseExpression(testExpression).matches(getDatabase(currentDatabase)) == expectedResult

        where:
        testExpression   | currentDatabase   | expectedResult
        "oracle"         | "oracle"          | true
        "oracle"         | "ORACLE"          | true
        "ORACLE"         | "oracle"          | true
        "oracle"         | "mssql"           | false
        "mssql"          | "oracle"          | false
        "oracle, mysql"  | "oracle"          | true
        "oracle, mysql"  | "mysql"           | true
        "oracle, MYSQL"  | "Mysql"           | true
        "oracle, mysql"  | "sept"            | false
    }

    @Unroll("#featureName: testExpression #expression currentDatabase: #currentDatabase")
    def "'not' expressions"() {
        expect:
        assert new DatabaseExpression(expression).matches(getDatabase(currentDatabase)) == expectedResult

        where:
        expression | currentDatabase | expectedResult
        "!oracle"    | "oracle"        | false
        "!oracle"    | "ORACLE"        | false
        "!ORACLE"    | "oracle"        | false
        "!oracle"    | "mssql"         | true
    }

    protected getDatabase(String name) {
        return new MockDatabase() {

            @Override
            String getShortName() {
                return name;
            }
        }
    }

    @Unroll("#featureName: testExpression #expression currentDatabase: #currentDatabase")
    def "'and' expressions"() {
        expect:
        assert new DatabaseExpression(expression).matches(getDatabase(currentDatabase)) == expectedResult

        where:
        expression        | currentDatabase | expectedResult
        "a and b"         | "a"             | false
        "a and b"         | "b"             | false
        "a and b"         | "c"             | false
        "a AND b"         | "a"             | false
        "a and !b"        | "a"             | true
        "a and !b"        | "b"             | false
        "  a   and   b  " | "a"             | false
        "  a   and   b  " | "b"             | false
    }

    @Unroll("#featureName: testExpression #expression currentDatabase: #currentDatabase")
    def "'or' expression"() {
        expect:
        assert new DatabaseExpression(expression).matches(getDatabase(currentDatabase)) == expectedResult

        where:
        expression      | currentDatabase | expectedResult
        "a or b"        | "a"             | true
        "a or b"        | "b"             | true
        "a or b"        | "c"             | false
        "a OR b"        | "a"             | true
        "a OR b"        | "c"             | false
        "a or !b"       | "a"             | true
        "a or !b"       | "b"             | false
        "a    or   b  " | "a"             | true
        " a   or   b  " | "b"             | true
        " a   or   b  " | "c"             | false
    }

    @Unroll("#featureName: testExpression #expression currentDatabase: #currentDatabase")
    def "complex expressions"() {
        expect:
        assert new DatabaseExpression(expression).matches(getDatabase(currentDatabase)) == expectedResult

        where:
        expression        | currentDatabase | expectedResult
        "a and b or c"    | "a"             | false
        "a and b or c"    | "c"             | true
        "a and (b or c)"  | "a"             | false
        "a and (b or c)"  | "b"             | false
        "a and (b or c)"  | "c"             | false
        "a or b and c"    | "a"             | true
        "a or b and c"    | "b"             | false
        "!a and b or c"   | "a"             | false
        "!a and b or c"   | "d"             | false
        "a and !b or c"   | "c"             | true
        "a and b or c, d" | "d"             | true
        "a and b or c, d" | "e"             | false
    }

    @Unroll
    def isEmpty() {
        expect:
        assert new DatabaseExpression(expression).isEmpty() == expected

        where:
        expression     | expected
        null           | true
        ""             | true
        "oracle"       | false
        "test1, test2" | false

    }

}
