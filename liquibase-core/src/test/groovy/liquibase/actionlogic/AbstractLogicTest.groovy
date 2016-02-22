package liquibase.actionlogic

import liquibase.JUnitScope
import liquibase.action.AbstractActionTest
import liquibase.database.ConnectionSupplier
import liquibase.util.StringUtils
import org.junit.Assert
import spock.lang.Specification

public abstract class AbstractLogicTest extends Specification {

    protected String collectAllValidationErrors(AbstractActionTest actionTest, ConnectionSupplier supplier) {
        def scope = JUnitScope.getInstance(supplier)
        def allPermutations = actionTest.createAllActionPermutations(supplier, scope)

        def hasValid = false;
        def executor = scope.getSingleton(ActionExecutor)
        SortedSet<String> allErrors = new TreeSet<>()
        for (def permutation : allPermutations) {
            def errors = executor.validate(permutation, scope)
            if (errors.hasErrors()) {
                allErrors.addAll(StringUtils.splitAndTrim(errors.toString(), ";"))
            } else {
                hasValid = true
            }
        }

        def allErrorMessages = StringUtils.join(allErrors, "\n")
        if (!hasValid) {
            Assert.fail("No valid objects create:\n"+StringUtils.indent(allErrorMessages, 8))
        }

        return allErrorMessages
    }

}
