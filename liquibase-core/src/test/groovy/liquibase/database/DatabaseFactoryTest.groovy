package liquibase.database

import liquibase.JUnitScope
import spock.lang.Specification

class DatabaseFactoryTest extends Specification {

    def "getDatabase finds correct implementations"() {
        expect:
        JUnitScope.instance.getSingleton(DatabaseFactory).getDatabase("unsupported").shortName == "unsupported"
        JUnitScope.instance.getSingleton(DatabaseFactory).getDatabase("mock").shortName == "mock"
    }
}
