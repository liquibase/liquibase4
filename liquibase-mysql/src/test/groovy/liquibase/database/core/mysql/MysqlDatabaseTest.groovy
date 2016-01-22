package liquibase.database.core.mysql

import spock.lang.Specification
import spock.lang.Unroll

class MysqlDatabaseTest extends Specification {

    @Unroll
    def "escapeForLike"() {
        expect:
        new MysqlDatabase().escapeStringForLike(input, forUseinSql) == expected

        where:
        input         | expected              | forUseinSql
        null          | null                  | true
        null          | null                  | false
        "a"           | "a"                   | true
        "a"           | "a"                   | false
        "   space   " | "   space   "         | true
        "   space   " | "   space   "         | false
        "%"           | "\\%"                 | true
        "%"           | "\\%"                 | false
        "_"           | "\\_"                 | true
        "_"           | "\\_"                 | false
        "a%_%_a"      | "a\\%\\_\\%\\_a"      | true
        "a%_%_a"      | "a\\%\\_\\%\\_a"      | false
        "\\%\\"       | "\\\\\\\\\\%\\\\\\\\" | true
        "\\%\\"       | "\\\\\\%\\\\"         | false
    }
}
