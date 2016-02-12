package liquibase.actionlogic

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.*
import liquibase.action.core.SnapshotObjectsAction
import liquibase.database.MockJdbcConnection
import liquibase.database.core.MockDatabase
import liquibase.database.core.GenericDatabase
import liquibase.exception.ActionPerformException
import liquibase.exception.ValidationErrors
import liquibase.servicelocator.MockServiceLocator
import liquibase.servicelocator.ServiceLocator
import liquibase.structure.ObjectReference
import liquibase.structure.core.Table
import spock.lang.Specification
import spock.lang.Unroll

class ActionExecutorTest extends Specification {

    MockServiceLocator serviceLocator
    Scope scope;

    def setup() {
        serviceLocator = new MockServiceLocator()
        scope = JUnitScope.getInstance(new MockDatabase()).overrideSingleton(ServiceLocator, serviceLocator)
        this.scope.database.setConnection(new MockJdbcConnection())
    }

    def "execute when null actionLogic"() {
        when:
        scope.getSingleton(ActionExecutor).execute(new MockAction(), scope) == null

        then:
        def e = thrown(ActionPerformException)
        e.message == "Validation Error(s): MockAction: no supported ActionLogic implementation found for liquibase.action.MockAction 'mock() for mock()"
    }

    def "execute when validation fails with errors"() {
        when:
        serviceLocator.addService(new MockExternalInteractionLogic("mock logic", 1, MockAction) {

            ValidationErrors validate(Action action, Scope scope) {
                return new ValidationErrors()
                        .addError(": mock validation error")
                        .addError(": another error")
            }
        })

        scope.getSingleton(ActionExecutor).execute(new MockAction(), scope)

        then:
        def e = thrown(ActionPerformException)
        e.message == "Validation Error(s): MockAction: mock validation error; MockAction: another error for mock()"
    }

    def "execute update logic"() {
        when:
        serviceLocator.addService(new MockExternalInteractionLogic("mock logic", 1, MockAction, {
            return new UpdateResult(it, "update logic ran", 12);
        }))

        def result = scope.getSingleton(ActionExecutor).execute(new MockAction(), scope)

        then:
        result instanceof UpdateResult
        result.message == "update logic ran"
        ((UpdateResult) result).numberAffected == 12
    }

    def "execute 'execute' logic"() {
        when:
        serviceLocator.addService(new MockExternalInteractionLogic("mock logic", 1, MockAction, {
            return new ExecuteResult(it, "execute logic ran");
        }))

        def result = scope.getSingleton(ActionExecutor).execute(new MockAction(), scope)

        then:
        result instanceof ExecuteResult
        result.message == "execute logic ran"
    }

    def "execute 'query' logic"() {
        when:
        serviceLocator.addService(new MockExternalInteractionLogic("mock logic", 1, MockAction, {
            return new RowBasedQueryResult(it, "query logic ran", "DATA");
        }))

        def result = scope.getSingleton(ActionExecutor).execute(new MockAction(), scope)

        then:
        result instanceof QueryResult
        result.message == "query logic ran"
    }

    def "execute 'rewrite' logic with an empty rewrite action list throws an exception"() {
        when:
        serviceLocator.addService(new MockActionLogic("mock logic", 1, MockAction, {
            return new DelegateResult(it, null);
        }))

        def result = scope.getSingleton(ActionExecutor).execute(new MockAction(), scope)

        then:
        def e = thrown(ActionPerformException)
        e.message == "Validation Error(s): MockAction: liquibase.actionlogic.MockActionLogic tried to handle 'mock()' but returned no actions to run for mock()"
    }

    def "execute 'rewrite' logic with a single rewrite action"() {
        when:
        serviceLocator.addService(new MockActionLogic("mock logic", 1, MockAction, {
            return new DelegateResult(it, null, new UpdateSqlAction("sql action 1"));
        }))
        serviceLocator.addService(new MockExternalInteractionLogic("mock sql", 1, UpdateSqlAction) {
            @Override
            ActionResult execute(Action action, Scope scope) throws ActionPerformException {
                return new ExecuteResult(action, "executed sql: " + ((AbstractSqlAction) action).sql);
            }
        })

        then:
        def result = scope.getSingleton(ActionExecutor).execute(new MockAction(), scope)

        result instanceof CompoundResult
        ((CompoundResult) result).flatResults.size() == 1

        when:
        result = ((CompoundResult) result).flatResults[0]

        then:
        result instanceof ExecuteResult
        result.message == "executed sql: sql action 1"
    }

    def "execute 'rewrite' logic with multiple delegate results"() {
        when:
        serviceLocator.addService(new MockActionLogic("mock logic", 1, MockAction, {
            return new DelegateResult(it, null, new UpdateSqlAction("sql action 1"), new UpdateSqlAction("sql action 2"));
        }))
        serviceLocator.addService(new MockExternalInteractionLogic("mock sql", 1, UpdateSqlAction) {
            @Override
            ActionResult execute(Action action, Scope scope) throws ActionPerformException {
                return new ExecuteResult(action, "executed sql: " + ((AbstractSqlAction) action).sql);
            }
        })

        then:
        CompoundResult result = scope.getSingleton(ActionExecutor).execute(new MockAction(), scope)

        result.flatResults.size() == 2
        result.flatResults[0].sourceAction == new UpdateSqlAction("sql action 1")
        result.flatResults[0].message == "executed sql: sql action 1"

        result.flatResults[1].sourceAction == new UpdateSqlAction("sql action 2")
        result.flatResults[1].message == "executed sql: sql action 2"
    }

    def "execute 'rewrite' logic with multiple levels of DelegateResults"() {
        when:
        serviceLocator.addService(new MockActionLogic("mock logic", 1, MockAction, {
            return new DelegateResult(it, null, new UpdateSqlAction("sql action 1"), new ExecuteSqlAction("exec sql action"), new UpdateSqlAction("sql action 2"));
        }))
        serviceLocator.addService(new MockExternalInteractionLogic("mock sql", 1, UpdateSqlAction) {
            @Override
            ActionResult execute(Action action, Scope scope) throws ActionPerformException {
                return new ExecuteResult(action, "executed sql: " + ((AbstractSqlAction) action).sql);
            }
        })

        serviceLocator.addService(new MockActionLogic("mock execute sql", 1, ExecuteSqlAction) {
            @Override
            ActionResult execute(Action action, Scope scope) throws ActionPerformException {
                return new DelegateResult(action, null, new UpdateSqlAction("nested 1"), new UpdateSqlAction("nested 2"));
            }
        })

        then:
        CompoundResult result = scope.getSingleton(ActionExecutor).execute(new MockAction(), scope)

        result.flatResults.size() == 4
        result.flatResults[0].sourceAction == new UpdateSqlAction("sql action 1")
        result.flatResults[0].message == "executed sql: sql action 1"

        result.flatResults[1].sourceAction == new UpdateSqlAction("nested 1")
        result.flatResults[1].message == "executed sql: nested 1"

        result.flatResults[2].sourceAction == new UpdateSqlAction("nested 2")
        result.flatResults[2].message == "executed sql: nested 2"

        result.flatResults[3].sourceAction == (new UpdateSqlAction("sql action 2"))
        result.flatResults[3].message == "executed sql: sql action 2"
    }

    def "execute 'rewrite' logic with multiple levels of DelegateResults and a top level modifier"() {
        when:
        serviceLocator.addService(new MockActionLogic("mock logic", 1, MockAction, {
            return new DelegateResult(it, new DelegateResult.Modifier() {
                @Override
                ActionResult rewrite(ActionResult result) throws ActionPerformException {
                    if (result instanceof ExecuteResult) {
                        ((ExecuteResult) result).message = result.message + "X"
                    }
                    return result
                }
            }, new UpdateSqlAction("sql action 1"), new ExecuteSqlAction("exec sql action"), new UpdateSqlAction("sql action 2"));
        }))
        serviceLocator.addService(new MockExternalInteractionLogic("mock sql", 1, UpdateSqlAction) {
            @Override
            ActionResult execute(Action action, Scope scope) throws ActionPerformException {
                return new ExecuteResult(action, "executed sql: " + ((AbstractSqlAction) action).sql);
            }
        })

        serviceLocator.addService(new MockActionLogic("mock execute sql", 1, ExecuteSqlAction) {
            @Override
            ActionResult execute(Action action, Scope scope) throws ActionPerformException {
                return new DelegateResult(action, null, new UpdateSqlAction("nested 1"), new UpdateSqlAction("nested 2"));
            }
        })

        then:
        CompoundResult result = scope.getSingleton(ActionExecutor).execute(new MockAction(), scope)

        result.flatResults.size() == 4
        result.flatResults[0].sourceAction == new UpdateSqlAction("sql action 1")
        result.flatResults[0].message == "executed sql: sql action 1"

        result.flatResults[1].sourceAction == new UpdateSqlAction("nested 1")
        result.flatResults[1].message == "executed sql: nested 1"

        result.flatResults[2].sourceAction == new UpdateSqlAction("nested 2")
        result.flatResults[2].message == "executed sql: nested 2"

        result.flatResults[3].sourceAction == (new UpdateSqlAction("sql action 2"))
        result.flatResults[3].message == "executed sql: sql action 2"
    }


    @Unroll
    def "plan is built correctly"() {
        expect:
        scope.getSingleton(ActionExecutor).createPlan(action, JUnitScope.getInstance(new GenericDatabase(new MockJdbcConnection()))).describe(true) == expected

        where:
        action                                                                             | expected
        new SnapshotObjectsAction(new ObjectReference(Table, "table_schem", "table_name")) | "Execute getTables(null, table_schem, table\\_name, [TABLE]) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic"
    }
}
