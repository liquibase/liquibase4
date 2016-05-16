package liquibase.actionlogic.core

import liquibase.ExecuteMode
import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.UpdateSqlAction
import liquibase.actionlogic.AbstractActionLogicTest
import liquibase.actionlogic.NoOpResult;

public class UpdateSqlLogicTest extends AbstractActionLogicTest{

    @Override
    String getExpectedValidationErrors() {
        return """
        """.trim()
    }

    @Override
    protected AbstractActionTest createActionTest() {
        return null; //no AbstractActionTest
    }

    def "is a no-op in read-only mode"() {
        when:
        def logic = new UpdateSqlLogic()
        def result = logic.execute(new UpdateSqlAction("update test set x=1"), JUnitScope.instance.child(Scope.Attr.executeMode, ExecuteMode.READ_ONLY))

        then:
        result instanceof NoOpResult
    }


}
