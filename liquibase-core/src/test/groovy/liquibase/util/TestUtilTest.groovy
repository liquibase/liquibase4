package liquibase.util

import liquibase.SingletonService
import liquibase.database.DatabaseFactory
import liquibase.servicelocator.AbstractServiceFactory
import liquibase.test.TestObjectFactory
import spock.lang.Specification

class TestUtilTest extends Specification {

    def "getClasses should find subclasses as well"() {
        when:
        def classes = TestUtil.getClasses(SingletonService)

        then:
        assert classes.contains(TestObjectFactory)
        assert !classes.contains(AbstractServiceFactory)
        assert classes.contains(DatabaseFactory)
    }
}
