package liquibase.action.core

import liquibase.structure.ObjectReference
import liquibase.structure.core.Sequence
import spock.lang.Specification
import spock.lang.Unroll

class CreateSequencesActionTest extends Specification {

    @Unroll
    def "describe"() {
        expect:
        object.describe() == expected

        where:
        object                                                                 | expected
        new CreateSequencesAction()                                            | "createSequences()"
        new CreateSequencesAction(new Sequence("test_seq"))                    | "createSequences(sequences=[Sequence{name=test_seq}])"
        new CreateSequencesAction(new Sequence("test_seq").set("cycle", true)) | "createSequences(sequences=[Sequence{cycle=true, name=test_seq}])"
    }
}
