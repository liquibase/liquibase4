package liquibase.actionlogic

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.UpdateSqlAction
import liquibase.action.core.CreateSequencesAction
import liquibase.action.core.DropSequencesAction
import liquibase.database.core.MockDatabase
import spock.lang.Specification

class ActionLogicFactoryTest extends Specification {

    List<? extends ActionLogic> actionLogicImpls;
    JUnitScope scope

    def setup() {
        actionLogicImpls = []
        scope = JUnitScope.getInstance(new MockDatabase())
        scope = scope.overrideSingleton(ActionLogicFactory, new ActionLogicFactory(scope) {
            @Override
            protected synchronized Collection<ActionLogic> findAllInstances() {
                return actionLogicImpls;
            }
        })
    }

    def "getActionLogic when empty"() {
        expect:
        scope.getSingleton(ActionLogicFactory).getActionLogic(new UpdateSqlAction("some sql"), JUnitScope.instance) == null
    }

    def "getActionLogic"() {
        when:
        actionLogicImpls.add(new MockActionLogic("create 1", 1, CreateSequencesAction))
        actionLogicImpls.add(new MockActionLogic("create 2", 2, CreateSequencesAction))

        actionLogicImpls.add(new MockActionLogic("drop 3", 3, DropSequencesAction))
        actionLogicImpls.add(new MockActionLogic("drop 2", 2, DropSequencesAction))
        actionLogicImpls.add(new MockActionLogic("drop 1", 1, DropSequencesAction))

        def scope = scope.child(Scope.Attr.database, new MockDatabase())

        then:
        scope.getSingleton(ActionLogicFactory).getActionLogic(new CreateSequencesAction(), scope).toString() == "Mock action logic 'create 2'"
        scope.getSingleton(ActionLogicFactory).getActionLogic(new DropSequencesAction(), scope).toString() == "Mock action logic 'drop 3'"
        scope.getSingleton(ActionLogicFactory).getActionLogic(new UpdateSqlAction("some sql"), scope) == null

    }

    def "Automatically finds action classes"() {
        expect:
        scope.overrideSingleton(ActionLogicFactory, null)
        JUnitScope.instance.getSingleton(ActionLogicFactory).findAllInstances().size() > 1
    }
}
