package liquibase.diff.output.changelog

import liquibase.JUnitScope
import liquibase.diff.output.changelog.core.MissingTableActionGenerator
import liquibase.snapshot.Snapshot
import liquibase.item.core.Table
import spock.lang.Specification

class ActionGeneratorFactoryTest extends Specification {

    def "getGenerator finds MissingObjectActionGenerator implementations"() {
        def scope = JUnitScope.instance

        expect:
        assert scope.getSingleton(ActionGeneratorFactory).getGenerator(MissingObjectActionGenerator, Table, new Snapshot(scope), new Snapshot(scope), scope) instanceof MissingTableActionGenerator
    }
}
