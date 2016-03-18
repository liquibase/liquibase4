package liquibase.actionlogic

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.database.Database
import liquibase.database.MockJdbcConnection
import liquibase.database.core.GenericConnectionSupplier
import liquibase.database.core.GenericDatabase
import liquibase.exception.UnexpectedLiquibaseException
import liquibase.util.StringUtil
import org.junit.Assert
import spock.lang.Specification

/**
 * Base class for testing {@link ActionLogic} implementations.
 * {@link liquibase.CodeInspectorTest} will check that all ActionLogic implementations have a corresponding test that extends this class.
 */
public abstract class AbstractActionLogicTest extends Specification {

    /**
     * Automatically checks all invalid error messages for permutations of an action.
     * This is useful because it ensures:
     * <ul>
     *     <li>The validation errors are well described</li>
     *     <li>There are no unexpected validation errors</li>
     *     <li>You can see what actions are filtered out in the ActionTests</li>
     *     <li>Pseudo-documentation of what is not supported by an action</li>
     * </ul>
     * This test will run through all permutations of an {@link liquibase.action.Action} and store a sorted set of the validation errors returned by the ActionLogic implementations under test.
     * <br><br>
     * By default, uses the permutations from {@link AbstractActionTest#createAllActionPermutations(liquibase.database.ConnectionSupplier, liquibase.Scope)} in the corresponding action class
     * and compares the output of {@link #collectAllValidationErrors(liquibase.action.AbstractActionTest, liquibase.database.ConnectionSupplier, liquibase.Scope)} with {@link #getExpectedValidationErrors()}.
     * If the default implementation works for you, just implement {@link #getExpectedValidationErrors()} to return the validation errors you expect to see.
     */
    def "validation failures are as expected"() {
        when:
        def actionTest = createActionTest()
        if (actionTest == null) { //if there is no applicable ActionTest, don't run this test.
            return;
        }

        Scope scope = JUnitScope.instance;
        scope = scope.child(Scope.Attr.database, getDatabaseUnderTest(scope))

        then:
        collectAllValidationErrors(actionTest, getConnectionSupplier(scope), scope) == getExpectedValidationErrors()

    }

    /**
     * Used by "validation failures are as expected()"
     */
    protected abstract String getExpectedValidationErrors();

    /**
     * Used by "validation failures are as expected()"
     */
    protected ConnectionSupplier getConnectionSupplier(Scope scope) {
        ConnectionSupplier correctSupplier;
        Database db = scope.getDatabase()
        for (ConnectionSupplier supplier : scope.getSingleton(ConnectionSupplierFactory).connectionSuppliers) {
            if (supplier.database.getClass().equals(db.getClass())) {
                if (correctSupplier == null) {
                    correctSupplier = supplier;
                } else {
                    throw new UnexpectedLiquibaseException("Found multiple suppliers for "+db.getShortName()+". You may need to override AbstractLogicTest.getConnectionSupplier")
                }
            }
        }
        if (correctSupplier == null) {
            if (db.getClass().equals(GenericDatabase)) {
                return new GenericConnectionSupplier()
            }
            throw new UnexpectedLiquibaseException("Could not find a connection supplier for "+db.getClass().getName())
        }
        return correctSupplier;
    }

    /**
     * Used by "validation failures are as expected()"
     */
    protected Database getDatabaseUnderTest(Scope scope) {
        def logicClass = Class.forName(getClass().getName().replaceFirst(/Test$/, "")).newInstance()
        def databaseClass = ((AbstractActionLogic) logicClass).getRequiredDatabase()
        if (databaseClass.equals(Database)) {
            databaseClass = GenericDatabase.class;
        }

        def database = databaseClass.newInstance()
        database.setConnection(new MockJdbcConnection(), scope)

        return database;
    }

    /**
     * Used by "validation failures are as expected()"
     */
    protected AbstractActionTest createActionTest() {
        def logic = (AbstractActionLogic) Class.forName(getClass().getName().replaceFirst(/Test$/, "")).newInstance()
        return Class.forName(logic.getSupportedAction().getName() + "Test").newInstance()
    }

    /**
     * Used by "validation failures are as expected()"
     */
    protected String collectAllValidationErrors(AbstractActionTest actionTest, ConnectionSupplier supplier, Scope scope) {
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
            Assert.fail("No valid objects create:\n" + StringUtil.indent(allErrorMessages, 8))
        }

        return allErrorMessages
    }

}
