package liquibase.action

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.core.CommitAction
import liquibase.actionlogic.ActionExecutor
import liquibase.command.core.DropAllCommand
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.database.Database
import liquibase.database.core.GenericDatabase
import liquibase.diff.output.changelog.ActionGeneratorFactory
import liquibase.exception.ActionPerformException
import liquibase.plugin.AbstractPlugin
import liquibase.plugin.AbstractPluginFactory
import liquibase.plugin.Plugin
import liquibase.snapshot.Snapshot
import liquibase.item.Item

import liquibase.item.ItemReference
import liquibase.item.TestItemSupplierFactory
import liquibase.item.core.*

import liquibase.util.CollectionUtil
import liquibase.util.StringUtil
import org.junit.Assert
import org.junit.Assume
import org.slf4j.LoggerFactory
import org.spockframework.runtime.SpecificationContext
import spock.lang.Specification
import testmd.Permutation
import testmd.TestMD
import testmd.logic.SetupResult

import java.text.NumberFormat

/**
 * ActionTests are designed to actually execute {@link Action}s against the database, but will only do so if the SQL or database commands have changes.
 * This "only test if needed" logic is managed using <a href="http://www.testmd.org">TestMD</a>.
 * This base class provides convenience methods for making ActionTests as easy to write as possible.
 * There should be one ActionTest per Action implementation.
 */
abstract class AbstractActionTest extends Specification {

    /**
     * Creates all permutations of the action this test is for. This method is public because it can be used by other tests that operate on all actions of a given type.
     * Normally don't create multiple permutations of names since that causes an explosion of permutations. Instead, just use a single standard name and test complex names with standard settings in separate tests.
     */
    public abstract List<Action> createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope)

    /**
     * Used by {@link #testAction(java.util.Map, liquibase.action.Action, liquibase.database.ConnectionSupplier, liquibase.Scope)} to create a snapshot that contains everything needed to test the given action.
     */
    protected abstract Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope)

    /**
     * Convenience version of {@link #testAction(java.util.Map, Snapshot, liquibase.action.Action, liquibase.database.ConnectionSupplier, liquibase.Scope)}
     * that uses {@link #createSnapshot(liquibase.action.Action, liquibase.database.ConnectionSupplier, liquibase.Scope)} as the snapshot.
     */
    protected testAction(Map parameters, Action action, ConnectionSupplier connectionSupplier, Scope scope, Closure assertClosure = { plan, results -> }, Closure setupClosure = {
    }) {
        return testAction(parameters, null, action, connectionSupplier, scope, assertClosure, setupClosure)
    }

    /**
     * Executes an action through TestMD. The parameters are used to lookup the last time the action was ran, then a {@link liquibase.actionlogic.ActionExecutor.Plan} is created for the action.
     * If the action was not ran before or plan differs, the action will be executed against the database.
     * The success of the action is tested by calling {@link ActionExecutor#checkStatus(liquibase.action.Action, liquibase.Scope)} and the assertClosure if passed.
     * For performance reasons, filter out invalid actions before calling this method. This method will perform action validation, but will throw an exception if validation fails.
     *
     * @param parameters A map of the permutation parameters to pass to TestMD. An additional "connection" parameter is automatically added.
     * @param snapshot If the permutation must be re-tested, populate the database with the objects in the snapshot
     * @param action The action to test
     * @param assertClosure an additional assertion function if the standard assertion is insufficient. Parameters passed are (plan, result)
     */
    protected testAction(Map parameters, Snapshot snapshot, Action action, ConnectionSupplier connectionSupplier, Scope scope, Closure assertClosure = { plan, results -> }, Closure setupClosure = {
        throw SetupResult.OK
    }) {
        def executor = scope.getSingleton(ActionExecutor)
        try {
            executor.resetPlanHistory()

            def errors = executor.validate(action, scope)
//            Assume.assumeFalse(errors.toString() + " for action" + action.describe(), errors.hasErrors())
            if (errors.hasErrors()) {
                Assert.fail("Should not have pass invalid action to testAction. Filter beforehand for performance reasons: " + errors.toString())
            }
            def plan = executor.createPlan(action, scope)


            if (snapshot == null) {
                snapshot = createSnapshot(action, connectionSupplier, scope)
            }

            parameters.put("connection", connectionSupplier.toString());

            TestMD.test(specificationContext.currentIteration.parent.spec.getPackage() + "." + specificationContext.currentIteration.parent.spec.name, specificationContext.currentIteration.parent.name, (scope.database == null ? GenericDatabase.class : scope.database.class))
                    .withPermutation(parameters)
                    .addOperations(plan: plan.describe(false))
                    .setup({
                if (scope.database == null) {
                    throw SetupResult.OK
                } else if (scope.database instanceof GenericDatabase) {
                    throw new SetupResult.CannotVerify("Generic");
                }

                scope = connectionSupplier.connect(scope)
                this.setupDatabase(snapshot, scope)
                setupClosure();

                throw SetupResult.OK
            })
                    .cleanup({
                this.cleanupDatabase(snapshot, scope)
            })
                    .run({
                def results
                try {
                    results = plan.execute(scope)

                    if (scope.database != null) {
                        scope.getSingleton(ActionExecutor).execute(new CommitAction(), scope)
                    }
                } catch (ActionPerformException e) {
                    LoggerFactory.getLogger(getClass()).warn("Snapshot: " + snapshot.describe())
                    throw e;
                }

                if (!(action instanceof QueryAction)) {
                    assert executor.checkStatus(action, scope).applied
                }

                assertClosure(plan, results)

            })

            return true;
        } catch (Throwable e) {
            LoggerFactory.getLogger(getClass()).error("Error on testAction for " + action.describe(), e)
            LoggerFactory.getLogger(getClass()).error("All executed:\n" + StringUtil.pad(StringUtil.join(executor.getExecutedPlans(), "\n"), 4))
            throw e
        }
    }

    /**
     * Throw {@link org.junit.internal.AssumptionViolatedException} if collection is null or size is zero.
     * Otherwise, return the collection.
     * Used when the permutations generated by a where clause may end up being empty, and that is expected.
     */
    protected Collection ignoreIfEmpty(String assertionErrorMessage, Collection collection) {
        Assume.assumeTrue(assertionErrorMessage, collection != null && collection.size() > 0)
        return collection
    }

    /**
     * Convenience method to call {@link ConnectionSupplierFactory#getConnectionSuppliers()}
     */
    protected Set<ConnectionSupplier> getConnectionSuppliers() {
        JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers
    }

    /**
     * Convenience method to {@link liquibase.item.TestItemSupplier#getReferences(Class < T >, List < ? extends ItemReference >, Scope)}
     */
    protected List<ItemReference> getItemReferences(Class<? extends Item> itemType, List<? extends ItemReference> containers, Scope scope) {
        return scope.getSingleton(TestItemSupplierFactory).getItemSupplier(itemType, scope).getReferences(itemType, containers, scope)
    }

    /**
     * Convenience method to {@link liquibase.item.TestItemSupplier#getNames(Class < T >, Scope)}
     */
    protected List<String> getItemNames(Class<? extends Item> itemType, Scope scope) {
        return scope.getSingleton(TestItemSupplierFactory).getItemSupplier(itemType, scope).getNames(itemType, scope)
    }

    /**
     * Returns the given name with whatever the standard naming case is for the database in the scope.
     * Example: if the database store everything as upper case, returns name.toUpperCase().
     */
    protected String standardCaseItemName(String name, Class<? extends Item> type, Scope scope) {
        if (name == null) {
            return null;
        }

        Database database = scope.database;
        if (database == null) {
            return name;
        }

        switch (database.getIdentifierCaseHandling(type, false, JUnitScope.instance)) {
            case Database.IdentifierCaseHandling.LOWERCASE: return name.toLowerCase();
            case Database.IdentifierCaseHandling.UPPERCASE: return name.toUpperCase();
            default: return name;
        }
    }

    /**
     * Creates ItemReferences with the given name for each container in the passed containers.
     */
    protected <T extends ItemReference> List<T> standardCaseReferences(Class<T> relationType, String name, List<? extends ItemReference> containers, Scope scope) {
        List<T> returnList = new ArrayList<>();
        for (ItemReference container : containers) {
            def ref = relationType.newInstance()
            ref.name = standardCaseItemName(name, ref.type, scope)
            ref.container = container
            returnList.add(ref)
        }
        return returnList
    }

    /**
     * Called by {@link ActionTestPermutation} to setup the database as part of {@link Permutation#setup}.
     * Default implementation:
     * <ol>
     * <li>drops all objects in the database</li>
     * <li>creates everything in the ActionTestPermutation's snapshot</li>
     * <li>runs the ActionTestPermutation's setupClosure</li>
     */
    protected setupDatabase(Snapshot snapshot, Scope scope) {
        Database database = scope.database
        if (database instanceof GenericDatabase) {
            throw SetupResult.OK;
        }

        for (ItemReference name : scope.get(JUnitScope.Attr.connectionSupplier.name(), ConnectionSupplier).getAllSchemas()) {
            new DropAllCommand(name).execute(scope);
        }

        if (snapshot != null) {
            for (Schema schema : snapshot.get(Schema.class)) {
                new DropAllCommand(schema.toReference()).execute(scope);
            }

            def executor = scope.getSingleton(ActionExecutor)

            for (def type : [Table, View, UniqueConstraint, Index, ForeignKey, Sequence, RowData, StoredProcedure]) {
                for (def obj : snapshot.get(type)) {
                    for (
                            def action : scope.getSingleton(ActionGeneratorFactory).fixMissing(obj, snapshot, new Snapshot(scope), scope)) {
                        def errors = executor.validate(action, scope)
                        LoggerFactory.getLogger(this.getClass()).debug("Setup action: " + executor.createPlan(action, scope).describe(true))
                        if (errors.hasErrors()) {
                            throw new RuntimeException(errors.toString() + " for action " + action.describe())
                        }
                        executor.execute(action, scope)
                    }
                }
            }
        }
    }

    /**
     * Called by {@link ActionTestPermutation} to clean up the database as part of {@link Permutation#cleanup}.
     * Default implementation does nothing.
     */
    protected void cleanupDatabase(Snapshot snapshot, Scope scope) {}

    AbstractActionTest.TestDetails getTestDetails(Scope scope) {
        return scope.getSingleton(AbstractActionTest.TestDetailsFactory).getTestDetails(this, scope)
    }

    /**
     * A specialized
     */
    protected static class ActionTestPermutation extends Permutation {
        Scope scope
        Database database
        ConnectionSupplier conn
        AbstractActionTest test
        Snapshot snapshot
        Closure setupClosure

        ActionTestPermutation(Map<String, Object> parameters, Snapshot snapshot, AbstractActionTest test, ConnectionSupplier connectionSupplier, SpecificationContext specificationContext, Scope scope, Closure setupClosure) {
            super(specificationContext.currentIteration.parent.spec.getPackage() + "." + specificationContext.currentIteration.parent.spec.name, specificationContext.currentIteration.parent.name, parameters)
            this.scope = scope
            this.database = scope.database
            this.conn = connectionSupplier
            this.snapshot = snapshot
            this.test = test;
            this.setupClosure = setupClosure

            this.setup({ throw SetupResult.OK })
            this.cleanup({
                test.cleanupDatabase(snapshot, connectionSupplier, scope)
            })
        }

        @Override
        String formatNotVerifiedMessage(String message) {
            if (message != null) {
                if (message.startsWith("Cannot open connection: No suitable driver found for")) {
                    message = "No suitable driver"
                } else if (message.startsWith("Cannot open connection: Access denied for user")) {
                    message = "Access denied"
                }
            }
            return message;
        }
    }

    /**
     * Since Spock doesn't let us override tests in subclasses, ActionTests can create inner classes that extend this to provide attributes which tests can check to modify behavior
     * to support various database differences.
     * The inner class must just be called "TestDetails" or it will not be found.
     */
    public static class TestDetails extends AbstractPlugin {

    }

    public static class TestDetailsFactory<T extends AbstractActionTest.TestDetails> extends AbstractPluginFactory<T> {

        public TestDetailsFactory(Scope scope) {
            super(scope);
        }

        @Override
        protected Class<T> getPluginClass() {
            return (Class<T>) AbstractActionTest.TestDetails.class;
        }

        public T getTestDetails(AbstractActionTest test, Scope scope) {
            return getPlugin(scope, test);
        }

        @Override
        protected int getPriority(AbstractActionTest.TestDetails obj, Scope scope, Object... args) {
            AbstractActionTest test = (AbstractActionTest) args[0];
            Class testName = test.getClass();
            Class testDetails = obj.getClass();

            if ((testName.getName() + '$TestDetails').equals(testDetails.getName())) {
                return Plugin.PRIORITY_DEFAULT;
            } else if ((testName.getSimpleName() + "Details" + scope.getDatabase().getShortName()).equalsIgnoreCase(testDetails.getSimpleName())) {
                return Plugin.PRIORITY_SPECIALIZED;
            } else {
                return Plugin.PRIORITY_NOT_APPLICABLE;
            }
        }

    }

    /**
     * Use this {@link liquibase.util.CollectionUtil.CollectionFilter} to trim out actions that fail validation before passing them to {@link #testAction(java.util.Map, liquibase.action.Action, liquibase.database.ConnectionSupplier, liquibase.Scope)}
     */
    protected static class ValidActionFilter implements CollectionUtil.CollectionFilter<Map> {

        private Scope scope

        private static int totalActions = 0;
        private static int filteredActions = 0;
        private static Map<String, Integer> filteredActionsByReason = new HashMap<>()


        static {
            //output the total filtered actions for informational/troubleshooting purposes
            Runtime.getRuntime().addShutdownHook({
                if (filteredActions > 0) {
                    def logger = LoggerFactory.getLogger(ValidActionFilter)
                    logger.error("Total filtered actions: " + NumberFormat.instance.format(filteredActions) + " out of " + NumberFormat.instance.format(totalActions) + ". Top reasons:\n" + StringUtil.indent(StringUtil.join(filteredActionsByReason.sort({ a, b -> b.value <=> a.value }).take(5), "\n")))
                }
            })
        }

        ValidActionFilter(Scope scope) {
            this.scope = scope
        }

        @Override
        boolean include(Map obj) {
            totalActions++;
            boolean foundAction = false; ;
            for (Map.Entry entry : obj.entrySet()) {
                if (entry.value instanceof Action) {
                    foundAction = true;
                    def errors = scope.getSingleton(ActionExecutor).validate(entry.value, scope)
                    def valid = !errors.hasErrors()
                    if (!valid) {
                        trackFilteredAction(errors.toString())
                        return false;
                    }
                }
            }
            if (!foundAction) {
                trackFilteredAction("No action found")
                return false;
            }
            return true;
        }

        protected trackFilteredAction(String message) {
            filteredActions++;

            def lastCount = filteredActionsByReason.get(message) ?: 0
            lastCount = lastCount + 1
            filteredActionsByReason.put(message, lastCount)
        }
    }
}