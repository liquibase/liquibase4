package liquibase.changelog.filter

import liquibase.DatabaseExpression
import liquibase.JUnitScope
import liquibase.changelog.ChangeSet
import liquibase.database.core.MockDatabase
import spock.lang.Specification
import spock.lang.Unroll

class DatabaseChangeSetFilterTest extends Specification {

    @Unroll
    def "filter test"() {
        when:
        DatabaseChangeSetFilter filter = new DatabaseChangeSetFilter(getDatabase(runtimeDatabase));

        then:
        assert filter.allow(new ChangeSet(null, null, null).each {
            it.dbms = expression == null ? null : new DatabaseExpression(expression);
        }, JUnitScope.instance).allow == expected

        where:
        expression     | runtimeDatabase | expected
        "test1"        | null            | true
        "test2"        | null            | true
        ""             | null            | true
        null           | null            | true
        "test1"        | "test1"         | true
        "test1"        | "TEST1"         | true
        "test1, test2" | "TEST1"         | true
        "test2"        | "TEST1"         | false
        ""             | "TEST1"         | true
        null           | "TEST1"         | true

    }

    protected getDatabase(String runtimeDatabase) {
        if (runtimeDatabase == null) {
            return null;
        }
        return new MockDatabase() {
            @Override
            String getShortName() {
                return runtimeDatabase;
            }
        }
    }
}
