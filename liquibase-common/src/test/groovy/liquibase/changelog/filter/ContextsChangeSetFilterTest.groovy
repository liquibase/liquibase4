package liquibase.changelog.filter

import liquibase.ContextExpression
import liquibase.Contexts
import liquibase.JUnitScope
import liquibase.changelog.ChangeSet
import spock.lang.Specification
import spock.lang.Unroll

public class ContextsChangeSetFilterTest extends Specification {

    @Unroll
    def "filter test"() {
        when:
        ContextsChangeSetFilter filter = new ContextsChangeSetFilter(new Contexts(runtimeContext));

        then:
        assert filter.allow(new ChangeSet(null, null, null).each {
            it.contexts = changeSetContext == null ? null : new ContextExpression(changeSetContext);
        }, JUnitScope.instance).allow == expected

        where:
        changeSetContext | runtimeContext | expected
        "test1"          | null           | true
        "test2"          | null           | true
        ""               | null           | true
        null             | null           | true
        "test1"          | "TEST1"        | true
        "test1, test2"   | "TEST1"        | true
        "test2"          | "TEST1"        | false
        ""               | "TEST1"        | true
        null             | "TEST1"        | true
        "test1"          | "test1, test2" | true
        "test2"          | "test1, test2" | true
        "test3"          | "test1, test2" | false
        "test3, test1"   | "test1, test2" | true
        "test3, TEST1"  | "test1, test2" | true

    }


}
