package liquibase.action

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.actionlogic.ActionExecutor
import liquibase.command.DropAllCommand
import liquibase.database.ConnectionSupplier
import liquibase.database.ConnectionSupplierFactory
import liquibase.database.Database
import liquibase.database.core.GenericDatabase
import liquibase.diff.output.changelog.ActionGeneratorFactory
import liquibase.exception.ActionPerformException
import liquibase.servicelocator.AbstractServiceFactory
import liquibase.servicelocator.Service
import liquibase.snapshot.Snapshot
import liquibase.structure.LiquibaseObject
import liquibase.structure.ObjectNameStrategy
import liquibase.structure.ObjectReference
import liquibase.structure.TestObjectReferenceSupplierFactory
import liquibase.structure.core.*
import liquibase.test.TestObjectFactory
import liquibase.util.CollectionUtil
import liquibase.util.StringUtils
import org.junit.Assert
import org.junit.Assume
import org.slf4j.LoggerFactory
import org.spockframework.runtime.SpecificationContext
import spock.lang.Specification
import testmd.Permutation
import testmd.TestMD
import testmd.logic.SetupResult

import java.text.NumberFormat

abstract class AbstractActionTest extends Specification {

    def testMDPermutation(ConnectionSupplier conn, Scope scope) {
        return testMDPermutation(null, conn, scope)
    }

    protected abstract Snapshot createSnapshot(Action action, ConnectionSupplier connectionSupplier, Scope scope)

    public abstract createAllActionPermutations(ConnectionSupplier connectionSupplier, Scope scope)

    def testAction(Map parameters, Action action, ConnectionSupplier connectionSupplier, Scope scope, Closure assertClosure = {plan, results -> }, Closure setupClosure = {}) {
        return testAction(parameters, null, action, connectionSupplier, scope, assertClosure, setupClosure)
    }

    def testAction(Map parameters, Snapshot snapshot, Action action, ConnectionSupplier connectionSupplier, Scope scope, Closure assertClosure = {plan, results ->}, Closure setupClosure = {}) {
        def executor = scope.getSingleton(ActionExecutor)
        try {
            executor.resetPlanHistory()

            def errors = executor.validate(action, scope)
//            Assume.assumeFalse(errors.toString() + " for action" + action.describe(), errors.hasErrors())
            if (errors.hasErrors()) {
                Assert.fail("Should not have pass invalid action to testAction. Filter beforehand for performance reasons: "+errors.toString())
            }
            def plan = executor.createPlan(action, scope)


            if (snapshot == null) {
                snapshot = createSnapshot(action, connectionSupplier, scope)
            }

            testMDPermutation(snapshot, setupClosure, connectionSupplier, scope)
                    .addParameters(parameters)
                    .addOperations(plan: plan.describe(false))
                    .run({
                def results
                try {
                    results = plan.execute(scope)
                } catch (ActionPerformException e) {
                    LoggerFactory.getLogger(getClass()).warn("Snapshot: "+snapshot.describe())
                    throw e;
                }

                if (!(action instanceof QueryAction)) {
                    assert executor.checkStatus(action, scope).applied
                }

                assertClosure(plan, results)
            })

            return true;
        } catch (Throwable e) {
            LoggerFactory.getLogger(getClass()).error("Error on testAction for "+action.describe(), e)
            LoggerFactory.getLogger(getClass()).error("All executed:\n"+StringUtils.pad(StringUtils.join(executor.getExecutedPlans(), "\n"), 4))
            throw e
        }
    }

    protected Collection assumeNotEmpty(String errorMessage, Collection values) {
        Assume.assumeTrue(errorMessage, values != null && values.size() > 0)
        return values;
    }


    protected Set<ConnectionSupplier> getConnectionSuppliers() {
        JUnitScope.instance.getSingleton(ConnectionSupplierFactory).connectionSuppliers
    }

    protected List createAllPermutationsWithoutNulls(Class type, Map<String, List<Object>> defaultValues) {
        JUnitScope.instance.getSingleton(TestObjectFactory).createAllPermutationsWithoutNulls(type, defaultValues)
    }

    protected List createAllPermutations(Class type, Map<String, List<Object>> defaultValues) {
        JUnitScope.instance.getSingleton(TestObjectFactory).createAllPermutations(type, defaultValues)
    }

    protected List<ObjectReference> getObjectNames(Class<? extends LiquibaseObject> objectType, Scope scope) {
        return scope.getSingleton(TestObjectReferenceSupplierFactory).getObjectReferenceSupplier(objectType, scope).getObjectNames(objectType, scope)
    }

    protected List<ObjectReference> getObjectNames(Class<? extends LiquibaseObject> objectType, ObjectNameStrategy strategy, Scope scope) {
        return scope.getSingleton(TestObjectReferenceSupplierFactory).getObjectReferenceSupplier(objectType, scope).getObjectNames(objectType, strategy, scope)
    }

    protected String standardCaseObjectName(String name, Class<? extends LiquibaseObject> type, Database database) {
        if (name == null) {
            return null;
        }

        switch(database.getIdentifierCaseHandling(type, false, JUnitScope.instance)) {
            case Database.IdentifierCaseHandling.LOWERCASE: return name.toLowerCase();
            case Database.IdentifierCaseHandling.UPPERCASE: return name.toUpperCase();
            default: return name;
        }
    }

    def testMDPermutation(Snapshot snapshot, Closure setupClosure = {}, ConnectionSupplier conn, Scope scope) {
        def database = scope.database

        def permutation = new ActionTestPermutation(this.specificationContext, this, snapshot, setupClosure, conn, scope, [:])

        permutation.addParameter("connection", conn.toString())

        return TestMD.test(this.specificationContext, database.class)
                .withPermutation(permutation)
    }

    def setupDatabase(Snapshot snapshot, Closure setupClosure, ConnectionSupplier supplier, Scope scope) {
        Database database = scope.database
        if (database instanceof GenericDatabase) {
            throw SetupResult.OK;
        }

        for (ObjectReference name : supplier.getAllSchemas()) {
            new DropAllCommand(name).execute(scope);
        }

        if (snapshot != null) {
            for (Schema schema : snapshot.get(Schema.class)) {
                new DropAllCommand(schema.toReference()).execute(scope);
            }

            def executor = scope.getSingleton(ActionExecutor)

            for (def type : [Table, UniqueConstraint, Index, ForeignKey]) {
                for (def obj : snapshot.get(type)) {
                    for (def action : scope.getSingleton(ActionGeneratorFactory).fixMissing(obj, snapshot, new Snapshot(scope), scope)) {
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

        setupClosure()

        throw SetupResult.OK
    }

    def cleanupDatabase(Snapshot snapshot, ConnectionSupplier supplier, Scope scope) {}

    AbstractActionTest.TestDetails getTestDetails(Scope scope) {
        return scope.getSingleton(AbstractActionTest.TestDetailsFactory).getTestDetails(this, scope)
    }

    String concatConsistantCaseObjectName(String baseName, String stringToAdd) {
        if (baseName.matches(/[^A-Z]+/)) { //keep it lower case
            return baseName + stringToAdd.toLowerCase()
        } else { //mixed case or all caps. Use upper case
            return baseName + stringToAdd.toUpperCase()
        }

    }

    Collection okIfEmpty(String assertionErrorMessage, Collection collection) {
        Assume.assumeTrue(assertionErrorMessage, collection != null && collection.size() > 0)
        return collection
    }

    static class ActionTestPermutation extends Permutation {
        Scope scope
        Database database
        ConnectionSupplier conn
        AbstractActionTest test
        Snapshot snapshot
        Closure setupClosure

        ActionTestPermutation(SpecificationContext specificationContext, AbstractActionTest test, Snapshot snapshot, Closure setupClosure, ConnectionSupplier connectionSupplier, Scope scope, Map<String, Object> parameters) {
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

        @Override
        Permutation setup(Runnable setup) {
            super.setup({
                if (scope.database instanceof GenericDatabase) {
                    throw new SetupResult.CannotVerify("Generic");
                }

                conn.connect(scope)
                test.setupDatabase(snapshot, setupClosure, conn, scope)
                setup.run();
            })
        }
    }

    public static class TestDetails implements Service {

    }

    public static class TestDetailsFactory<T extends AbstractActionTest.TestDetails> extends AbstractServiceFactory<T> {

        public TestDetailsFactory(Scope scope) {
            super(scope);
        }

        @Override
        protected Class<T> getServiceClass() {
            return (Class<T>) AbstractActionTest.TestDetails.class;
        }

        public T getTestDetails(AbstractActionTest test, Scope scope) {
            return getService(scope, test);
        }

        @Override
        protected int getPriority(AbstractActionTest.TestDetails obj, Scope scope, Object... args) {
            AbstractActionTest test = (AbstractActionTest) args[0];
            Class testName = test.getClass();
            Class testDetails = obj.getClass();

            if ((testName.getName() + '$TestDetails').equals(testDetails.getName())) {
                return Service.PRIORITY_DEFAULT;
            } else if ((testName.getSimpleName() + "Details" + scope.getDatabase().getShortName()).equalsIgnoreCase(testDetails.getSimpleName())) {
                return Service.PRIORITY_SPECIALIZED;
            } else {
                return Service.PRIORITY_NOT_APPLICABLE;
            }
        }

    }

    protected static class ValidActionFilter implements CollectionUtil.CollectionFilter<Map> {

        private Scope scope

        private static int totalActions = 0;
        private static int filteredActions = 0;
        private static Map<String, Integer> filteredActionsByReason = new HashMap<>()


        static {
            //output the total filtered actions for informational purposes
            Runtime.getRuntime().addShutdownHook({
                if (filteredActions > 0) {
                    def logger = LoggerFactory.getLogger(ValidActionFilter)
                    logger.error("Total filtered actions: "+NumberFormat.instance.format(filteredActions)+" out of "+totalActions+". Top reasons:\n"+StringUtils.indent(StringUtils.join(filteredActionsByReason.sort({a, b -> b.value <=> a.value}).take(5), "\n")))
                }
            })
        }
        ValidActionFilter(Scope scope) {
            this.scope = scope
        }

        @Override
        boolean include(Map obj) {
            totalActions++;
            boolean foundAction = false;;
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