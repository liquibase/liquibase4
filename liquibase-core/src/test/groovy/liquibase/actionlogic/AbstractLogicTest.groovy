package liquibase.actionlogic

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.database.ConnectionSupplier
import liquibase.database.MockJdbcConnection
import liquibase.util.StringUtil
import org.junit.Assert
import spock.lang.Specification

/**
 * Base class for testing {@link ActionLogic} implementations.
 */
public abstract class AbstractLogicTest extends Specification {

    /**
     * Collects and sorts all unique validation errors from the actions returned by {@link AbstractActionTest#createAllActionPermutations(liquibase.database.ConnectionSupplier, liquibase.Scope)}.
     * Used to test that there has been no unexpected changes to the validation errors.
     */
    protected String collectAllValidationErrors(AbstractActionTest actionTest, ConnectionSupplier supplier) {
        def scope = JUnitScope.getInstance()
        scope = scope.child(Scope.Attr.database.name(), supplier.database)
        scope.database.setConnection(new MockJdbcConnection(), scope)
        def allPermutations = actionTest.createAllActionPermutations(supplier, scope)

        def hasValid = false;
        def executor = scope.getSingleton(ActionExecutor)
        SortedSet<String> allErrors = new TreeSet<>()
        for (def permutation : allPermutations) {
            def errors = executor.validate(permutation, scope)
            if (errors.hasErrors()) {
                allErrors.addAll(StringUtil.splitAndTrim(errors.toString(), ";"))
            } else {
                hasValid = true
            }
        }

        def allErrorMessages = StringUtil.join(allErrors, "\n")
        if (!hasValid) {
            Assert.fail("No valid objects create:\n"+StringUtil.indent(allErrorMessages, 8))
        }

        return allErrorMessages
    }

}
