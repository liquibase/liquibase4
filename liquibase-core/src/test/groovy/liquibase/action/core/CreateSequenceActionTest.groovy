package liquibase.action.core

import liquibase.structure.ObjectReference
import liquibase.structure.core.Sequence
import spock.lang.Specification
import spock.lang.Unroll

class CreateSequenceActionTest extends Specification {

    @Unroll
    def "describe"() {
        expect:
        object.describe() == expected

        where:
        object                                                                                                                   | expected
        new CreateSequenceAction()                                                                                               | "createSequence()"
        new CreateSequenceAction().set("sequenceName", new ObjectReference(Sequence, "test_seq"))                                | "createSequence(sequenceName=test_seq)"
        new CreateSequenceAction().set("sequenceName", "test_seq", new ObjectReference(Sequence, "test_seq")).set("cycle", true) | "createSequence(cycle=true, sequenceName=test_seq)"
    }
}
