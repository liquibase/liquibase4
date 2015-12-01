package liquibase.snapshot

import liquibase.JUnitScope
import spock.lang.Specification

class SnapshotIdServiceTest extends Specification {

    def generate() {
        when:
        def id1 = JUnitScope.instance.getSingleton(SnapshotIdService).generateId()
        def id2 = JUnitScope.instance.getSingleton(SnapshotIdService).generateId()

        then:
        id1 != id2
        id1.subSequence(0, 4) == id2.subSequence(0, 4)
    }
}
