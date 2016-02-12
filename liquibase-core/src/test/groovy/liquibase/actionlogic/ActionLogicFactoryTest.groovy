package liquibase.actionlogic

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.UpdateSqlAction
import liquibase.action.core.CreateSequencesAction
import liquibase.action.core.DropSequenceAction
import liquibase.database.core.MockDatabase
import liquibase.servicelocator.MockServiceLocator
import liquibase.servicelocator.ServiceLocator
import spock.lang.Specification

class ActionLogicFactoryTest extends Specification {

    MockServiceLocator serviceLocator
    Scope scope

    def setup() {
        serviceLocator = new MockServiceLocator()
        scope = JUnitScope.getInstance(new MockDatabase()).overrideSingleton(ServiceLocator, serviceLocator)
    }

    def "getActionLogic when empty"() {
        expect:
        scope.getSingleton(ActionLogicFactory).getActionLogic(new UpdateSqlAction("some sql"), JUnitScope.instance) == null
    }

    def "getActionLogic"() {
        when:
        serviceLocator.addService(new MockActionLogic("create 1", 1, CreateSequencesAction))
        serviceLocator.addService(new MockActionLogic("create 2", 2, CreateSequencesAction))

        serviceLocator.addService(new MockActionLogic("drop 3", 3, DropSequenceAction))
        serviceLocator.addService(new MockActionLogic("drop 2", 2, DropSequenceAction))
        serviceLocator.addService(new MockActionLogic("drop 1", 1, DropSequenceAction))

        def scope = scope.child(Scope.Attr.database, new MockDatabase())

        then:
        scope.getSingleton(ActionLogicFactory).getActionLogic(new CreateSequencesAction(), scope).toString() == "Mock action logic 'create 2'"
        scope.getSingleton(ActionLogicFactory).getActionLogic(new DropSequenceAction(), scope).toString() == "Mock action logic 'drop 3'"
        scope.getSingleton(ActionLogicFactory).getActionLogic(new UpdateSqlAction("some sql"), scope) == null

    }

    def "Automatically finds action classes"() {
        expect:
        JUnitScope.instance.getSingleton(ServiceLocator).findAllServices(ActionLogic.class).size() > 1
    }
}
