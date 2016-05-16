package liquibase

import liquibase.listener.ActionListener
import liquibase.listener.SnapshotListener
import liquibase.resource.MockResourceAccessor
import spock.lang.Specification

class ScopeTest extends Specification {

    def "adding and getting listeners work"() {
        when:
        def testScope = new Scope(new MockResourceAccessor(), null).child(new ActionListener() {})

        then:
        testScope.getListeners(ActionListener).size() == 1
        testScope.getListeners(SnapshotListener).size() == 0

        testScope.child("newValue", "3").getListeners(ActionListener).size() == 1
    }
}
