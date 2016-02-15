package liquibase.database.core.mysql

import spock.lang.Specification
import spock.lang.Unroll

class MysqlDatabaseTest extends Specification {

    @Unroll
    def "escapeForLike"() {
        expect:
        new MysqlDatabase().escapeStringForLike(input) == expected

        where:
        input         | expected
        null          | null
        "a"           | "a"
        "   space   " | "   space   "
        "%"           | "\\%"
        "_"           | "\\_"
        "a%_%_a"      | "a\\%\\_\\%\\_a"
        "\\%\\"       | "\\\\\\%\\\\"
    }
}
