package liquibase.actionlogic.core

import liquibase.ExecuteMode
import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.ExecuteSqlAction
import liquibase.actionlogic.AbstractActionLogicTest
import liquibase.actionlogic.NoOpResult

class ExecuteSqlLogicTest extends AbstractActionLogicTest {

    @Override
    String getExpectedValidationErrors() {
        return null;
    }

    @Override
    protected AbstractActionTest createActionTest() {
        return null; //no AbstractActionTest
    }


    def "is a no-op in read-only mode"() {
        when:
        def logic = new ExecuteSqlLogic()
        def result = logic.execute(new ExecuteSqlAction("update test set x=1"), JUnitScope.instance.child(Scope.Attr.executeMode, ExecuteMode.READ_ONLY))

        then:
        result instanceof NoOpResult
    }

}
