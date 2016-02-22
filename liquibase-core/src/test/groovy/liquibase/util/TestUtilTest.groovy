package liquibase.util

import liquibase.SingletonObject
import liquibase.database.DatabaseFactory
import liquibase.plugin.AbstractPluginFactory
import liquibase.test.TestObjectFactory
import spock.lang.Specification

class TestUtilTest extends Specification {

    def "getClasses should find subclasses as well"() {
        when:
        def classes = TestUtil.getClasses(SingletonObject)

        then:
        assert classes.contains(TestObjectFactory)
        assert !classes.contains(AbstractPluginFactory)
        assert classes.contains(DatabaseFactory)
    }
}
