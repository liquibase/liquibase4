package liquibase.changelog.filter

import liquibase.JUnitScope
import liquibase.LabelExpression
import liquibase.Labels
import liquibase.changelog.ChangeSet
import spock.lang.Specification
import spock.lang.Unroll

class LabelsChangeSetFilterTest extends Specification {


    @Unroll
    def "filter test"() {
        when:
        LabelsChangeSetFilter filter = new LabelsChangeSetFilter(new LabelExpression(runtimeLabel));

        then:
        assert filter.allow(new ChangeSet(null, null, null).each {
            it.labels = changeSetLabel == null ? null : new Labels(changeSetLabel);
        }, JUnitScope.instance).allow == expected

        where:
        changeSetLabel | runtimeLabel   | expected
        "test1"        | null           | true
        "test2"        | null           | true
        ""             | null           | true
        null           | null           | true
        "test1"        | "TEST1"        | true
        "test1, test2" | "TEST1"        | true
        "test2"        | "TEST1"        | false
        ""             | "TEST1"        | true
        null           | "TEST1"        | true
        "test1"        | "test1, test2" | true
        "test2"        | "test1, test2" | true
        "test3"        | "test1, test2" | false
        "test3, test1" | "test1, test2" | true
        "test3, TEST1" | "test1, test2" | true

    }
}
