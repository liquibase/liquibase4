package liquibase.lockservice

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.core.SelectDataAction
import liquibase.action.core.UpdateDataAction
import liquibase.actionlogic.ActionExecutor
import liquibase.actionlogic.RowBasedQueryResult
import liquibase.actionlogic.UpdateResult
import liquibase.database.Database.IdentifierCaseHandling
import liquibase.database.MockJdbcConnection
import liquibase.database.core.MockDatabase
import liquibase.item.core.Table
import liquibase.snapshot.Snapshot
import liquibase.util.StringUtil
import spock.lang.Specification
import spock.lang.Unroll

class StandardLockServiceTest extends Specification {

    def initLockService(IdentifierCaseHandling caseHandling = IdentifierCaseHandling.UPPERCASE) {
        def database = new MockDatabase()
        database.identifierCaseHandling = caseHandling

        def scope = JUnitScope.getInstance(database)
        ((MockJdbcConnection) database.connection).snapshot = new Snapshot(scope).add(new Table((caseHandling == IdentifierCaseHandling.LOWERCASE ? "databasechangeloglock" : "DATABASECHANGELOGLOCK")));

        def lockService = new StandardLockService()
        lockService.init(scope)
        scope.getSingleton(ActionExecutor.class).resetPlanHistory();

        scope = scope.child(Scope.Attr.lockService, lockService)

        return scope;
    }

    @Unroll("#featureName: #caseHandling")
    def "init creates table and inserts data if there are no existing tables"() {
        when:
        def database = new MockDatabase()
        database.identifierCaseHandling = caseHandling

        def scope = JUnitScope.getInstance(database)
        ((MockJdbcConnection) database.connection).snapshot = new Snapshot(scope);

        def lockService = new StandardLockService()

        def executor = scope.getSingleton(ActionExecutor)
        executor.resetPlanHistory()

        lockService.init(scope)

        then:
        StringUtil.join(executor.getExecutedPlans(), "\n", false) == expected.trim()

        where:
        [caseHandling, expected] << [
                [IdentifierCaseHandling.UPPERCASE, """
Execute snapshotItems(relatedTo=[DATABASECHANGELOGLOCK], typeToSnapshot=liquibase.item.core.Table) with liquibase.actionlogic.MockSnapshotLogic
Execute snapshotItems(relatedTo=[DATABASECHANGELOGLOCK], typeToSnapshot=liquibase.item.core.Column) with liquibase.actionlogic.MockSnapshotLogic
Execute snapshotItems(relatedTo=[DATABASECHANGELOGLOCK], typeToSnapshot=liquibase.item.core.PrimaryKey) with liquibase.actionlogic.core.SnapshotPrimaryKeysLogicOffline
Execute snapshotItems(relatedTo=[DATABASECHANGELOGLOCK], typeToSnapshot=liquibase.item.core.Column) with liquibase.actionlogic.core.SnapshotColumnsLogicOffline
Execute comment(comment=Creating or upgrading DatabaseChangeLogLock Table) with liquibase.actionlogic.core.CommentLogic
Execute CREATE TABLE `DATABASECHANGELOGLOCK` (`ID` INTEGER NOT NULL, `LOCKED` BOOLEAN NOT NULL, `LOCKGRANTED` TIMESTAMP, `LOCKEDBY` VARCHAR(255)) with liquibase.actionlogic.core.ExecuteSqlLogic
Execute INSERT INTO `DATABASECHANGELOGLOCK` (`ID`, `LOCKED`) VALUES (1, 'false') with liquibase.actionlogic.core.ExecuteSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
"""],

                [IdentifierCaseHandling.LOWERCASE, """
Execute snapshotItems(relatedTo=[databasechangeloglock], typeToSnapshot=liquibase.item.core.Table) with liquibase.actionlogic.MockSnapshotLogic
Execute snapshotItems(relatedTo=[databasechangeloglock], typeToSnapshot=liquibase.item.core.Column) with liquibase.actionlogic.MockSnapshotLogic
Execute snapshotItems(relatedTo=[databasechangeloglock], typeToSnapshot=liquibase.item.core.PrimaryKey) with liquibase.actionlogic.core.SnapshotPrimaryKeysLogicOffline
Execute snapshotItems(relatedTo=[databasechangeloglock], typeToSnapshot=liquibase.item.core.Column) with liquibase.actionlogic.core.SnapshotColumnsLogicOffline
Execute comment(comment=Creating or upgrading DatabaseChangeLogLock Table) with liquibase.actionlogic.core.CommentLogic
Execute CREATE TABLE `databasechangeloglock` (`id` INTEGER NOT NULL, `locked` BOOLEAN NOT NULL, `lockgranted` TIMESTAMP, `lockedby` VARCHAR(255)) with liquibase.actionlogic.core.ExecuteSqlLogic
Execute INSERT INTO `databasechangeloglock` (`id`, `locked`) VALUES (1, 'false') with liquibase.actionlogic.core.ExecuteSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
"""],

                [IdentifierCaseHandling.CASE_SENSITIVE, """
Execute snapshotItems(relatedTo=[DATABASECHANGELOGLOCK], typeToSnapshot=liquibase.item.core.Table) with liquibase.actionlogic.MockSnapshotLogic
Execute snapshotItems(relatedTo=[DATABASECHANGELOGLOCK], typeToSnapshot=liquibase.item.core.Column) with liquibase.actionlogic.MockSnapshotLogic
Execute snapshotItems(relatedTo=[DATABASECHANGELOGLOCK], typeToSnapshot=liquibase.item.core.PrimaryKey) with liquibase.actionlogic.core.SnapshotPrimaryKeysLogicOffline
Execute snapshotItems(relatedTo=[DATABASECHANGELOGLOCK], typeToSnapshot=liquibase.item.core.Column) with liquibase.actionlogic.core.SnapshotColumnsLogicOffline
Execute comment(comment=Creating or upgrading DatabaseChangeLogLock Table) with liquibase.actionlogic.core.CommentLogic
Execute CREATE TABLE `DATABASECHANGELOGLOCK` (`ID` INTEGER NOT NULL, `LOCKED` BOOLEAN NOT NULL, `LOCKGRANTED` TIMESTAMP, `LOCKEDBY` VARCHAR(255)) with liquibase.actionlogic.core.ExecuteSqlLogic
Execute INSERT INTO `DATABASECHANGELOGLOCK` (`ID`, `LOCKED`) VALUES (1, 'false') with liquibase.actionlogic.core.ExecuteSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
"""],
        ]
    }

    @Unroll("#featureName: #caseHandling")
    def "init does nothing if the table exists"() {
        when:
        def database = new MockDatabase()
        database.identifierCaseHandling = caseHandling

        def scope = JUnitScope.getInstance(database)
        ((MockJdbcConnection) database.connection).snapshot = new Snapshot(scope).add(new Table((caseHandling == IdentifierCaseHandling.LOWERCASE ? "databasechangeloglock" : "DATABASECHANGELOGLOCK")));

        def lockService = new StandardLockService()

        def executor = scope.getSingleton(ActionExecutor)
        executor.resetPlanHistory()

        lockService.init(scope)

        then:
        StringUtil.join(executor.getExecutedPlans(), "\n", false) == expected.trim()

        where:
        [caseHandling, expected] << [
                [IdentifierCaseHandling.UPPERCASE, """
Execute snapshotItems(relatedTo=[DATABASECHANGELOGLOCK], typeToSnapshot=liquibase.item.core.Table) with liquibase.actionlogic.MockSnapshotLogic
Execute snapshotItems(relatedTo=[DATABASECHANGELOGLOCK], typeToSnapshot=liquibase.item.core.Column) with liquibase.actionlogic.MockSnapshotLogic
"""],

                [IdentifierCaseHandling.LOWERCASE, """
Execute snapshotItems(relatedTo=[databasechangeloglock], typeToSnapshot=liquibase.item.core.Table) with liquibase.actionlogic.MockSnapshotLogic
Execute snapshotItems(relatedTo=[databasechangeloglock], typeToSnapshot=liquibase.item.core.Column) with liquibase.actionlogic.MockSnapshotLogic
"""],

                [IdentifierCaseHandling.CASE_SENSITIVE, """
Execute snapshotItems(relatedTo=[DATABASECHANGELOGLOCK], typeToSnapshot=liquibase.item.core.Table) with liquibase.actionlogic.MockSnapshotLogic
Execute snapshotItems(relatedTo=[DATABASECHANGELOGLOCK], typeToSnapshot=liquibase.item.core.Column) with liquibase.actionlogic.MockSnapshotLogic
"""],
        ]
    }


    @Unroll("#featureName: #caseHandling")
    def "forceReleaseLock"() {
        when:
        def scope = initLockService(caseHandling)

        def executor = Spy(ActionExecutor)
        scope = scope.overrideSingleton(ActionExecutor, executor);
        executor.update(_ as UpdateDataAction, scope) >> {
            callRealMethod(); return new UpdateResult(new UpdateDataAction(), 1)
        }

        scope.get(Scope.Attr.lockService, LockService).forceReleaseLock(scope);

        then:
        StringUtil.join(executor.getExecutedPlans(), "\n", false) == expected.trim()

        where:
        [caseHandling, expected] << [
                [IdentifierCaseHandling.UPPERCASE, """
Execute comment(comment=Release Liquibase Lock) with liquibase.actionlogic.core.CommentLogic
Execute UPDATE `DATABASECHANGELOGLOCK` SET `LOCKED`='false', `LOCKGRANTED`=NULL, `LOCKEDBY`=NULL WHERE ID = 1 with liquibase.actionlogic.core.UpdateSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
"""],

                [IdentifierCaseHandling.LOWERCASE, """
Execute comment(comment=Release Liquibase Lock) with liquibase.actionlogic.core.CommentLogic
Execute UPDATE `databasechangeloglock` SET `locked`='false', `lockgranted`=NULL, `lockedby`=NULL WHERE id = 1 with liquibase.actionlogic.core.UpdateSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
"""],

                [IdentifierCaseHandling.CASE_SENSITIVE, """
Execute comment(comment=Release Liquibase Lock) with liquibase.actionlogic.core.CommentLogic
Execute UPDATE `DATABASECHANGELOGLOCK` SET `LOCKED`='false', `LOCKGRANTED`=NULL, `LOCKEDBY`=NULL WHERE ID = 1 with liquibase.actionlogic.core.UpdateSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
"""],
        ]
    }

    @Unroll("#featureName: #caseHandling")
    def "releaseLock"() {
        when:
        def scope = initLockService(caseHandling)

        def executor = Spy(ActionExecutor)
        def selfDesc = "test machine"
        scope = scope.overrideSingleton(ActionExecutor, executor);
        executor.update(_ as UpdateDataAction, scope) >> {
            callRealMethod(); return new UpdateResult(new UpdateDataAction(), 1)
        }
        executor.query(_ as SelectDataAction, scope) >> {
            callRealMethod(); return new RowBasedQueryResult(null, [
                    (caseHandling == IdentifierCaseHandling.LOWERCASE ? "id" : "ID")                  : 1,
                    (caseHandling == IdentifierCaseHandling.LOWERCASE ? "locked" : "LOCKED")          : true,
                    (caseHandling == IdentifierCaseHandling.LOWERCASE ? "lockedby" : "LOCKEDBY")      : selfDesc,
                    (caseHandling == IdentifierCaseHandling.LOWERCASE ? "lockgranted" : "LOCKGRANTED"): new Date(),
            ])
        }



        StandardLockService lockService = scope.get(Scope.Attr.lockService, LockService)
        lockService.myDescription = selfDesc
        lockService.releaseLock(scope);

        then:
        StringUtil.join(executor.getExecutedPlans(), "\n", false) == expected.trim()

        where:
        [caseHandling, expected] << [
                [IdentifierCaseHandling.UPPERCASE, """
Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic
Execute comment(comment=Release Liquibase Lock) with liquibase.actionlogic.core.CommentLogic
Execute UPDATE `DATABASECHANGELOGLOCK` SET `LOCKED`='false', `LOCKGRANTED`=NULL, `LOCKEDBY`=NULL WHERE ID = 1 with liquibase.actionlogic.core.UpdateSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
"""],

                [IdentifierCaseHandling.LOWERCASE, """
Execute SELECT * FROM `databasechangeloglock` with liquibase.actionlogic.core.QuerySqlLogic
Execute comment(comment=Release Liquibase Lock) with liquibase.actionlogic.core.CommentLogic
Execute UPDATE `databasechangeloglock` SET `locked`='false', `lockgranted`=NULL, `lockedby`=NULL WHERE id = 1 with liquibase.actionlogic.core.UpdateSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
"""],

                [IdentifierCaseHandling.CASE_SENSITIVE, """
Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic
Execute comment(comment=Release Liquibase Lock) with liquibase.actionlogic.core.CommentLogic
Execute UPDATE `DATABASECHANGELOGLOCK` SET `LOCKED`='false', `LOCKGRANTED`=NULL, `LOCKEDBY`=NULL WHERE ID = 1 with liquibase.actionlogic.core.UpdateSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
"""],
        ]
    }

    @Unroll("#featureName: #caseHandling")
    def "getCurrentLock (not locked)"() {
        when:
        def scope = initLockService(caseHandling)

        def executor = Spy(ActionExecutor)
        scope = scope.overrideSingleton(ActionExecutor, executor);
        executor.query(_ as SelectDataAction, scope) >> {
            callRealMethod(); return new RowBasedQueryResult(null, [
                    (caseHandling == IdentifierCaseHandling.LOWERCASE ? "id" : "ID")        : 1,
                    (caseHandling == IdentifierCaseHandling.LOWERCASE ? "locked" : "LOCKED"): false,
            ])
        }

        def currentLock = ((StandardLockService) scope.get(Scope.Attr.lockService, LockService)).getCurrentLock(scope);

        then:
        StringUtil.join(executor.getExecutedPlans(), "\n", false) == expected.trim()
        currentLock == null

        where:
        [caseHandling, expected] << [
                [IdentifierCaseHandling.UPPERCASE, "Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic"],
                [IdentifierCaseHandling.LOWERCASE, "Execute SELECT * FROM `databasechangeloglock` with liquibase.actionlogic.core.QuerySqlLogic"],
                [IdentifierCaseHandling.CASE_SENSITIVE, "Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic"],
        ]
    }

    @Unroll("#featureName: #caseHandling - #lockedBy")
    def "getCurrentLock (locked)"() {
        when:
        def scope = initLockService(caseHandling)

        def selfString = "test machine"

        def executor = Spy(ActionExecutor)
        scope = scope.overrideSingleton(ActionExecutor, executor);
        executor.query(_ as SelectDataAction, scope) >> {
            callRealMethod(); return new RowBasedQueryResult(null, [
                    (caseHandling == IdentifierCaseHandling.LOWERCASE ? "id" : "ID")                  : 1,
                    (caseHandling == IdentifierCaseHandling.LOWERCASE ? "locked" : "LOCKED")          : true,
                    (caseHandling == IdentifierCaseHandling.LOWERCASE ? "lockedby" : "LOCKEDBY")      : lockedBy,
                    (caseHandling == IdentifierCaseHandling.LOWERCASE ? "lockgranted" : "LOCKGRANTED"): new Date(),
            ])
        }

        def lockService = (StandardLockService) scope.get(Scope.Attr.lockService, LockService)
        lockService.myDescription = selfString
        def currentLock = lockService.getCurrentLock(scope);

        then:
        StringUtil.join(executor.getExecutedPlans(), "\n", false) == expectedActions.trim()
        currentLock.id == 1
        assert currentLock.isOwner == isOwner
        currentLock.lockedBy == lockedBy
        currentLock.lockGranted != null


        where:
        [caseHandling, lockedBy, isOwner, expectedActions] << [
                [IdentifierCaseHandling.UPPERCASE, "test machine", true, "Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic"],
                [IdentifierCaseHandling.UPPERCASE, "not us", false, "Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic"],
                [IdentifierCaseHandling.UPPERCASE, null, false, "Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic"],
                [IdentifierCaseHandling.LOWERCASE, "not us", false, "Execute SELECT * FROM `databasechangeloglock` with liquibase.actionlogic.core.QuerySqlLogic"],
                [IdentifierCaseHandling.CASE_SENSITIVE, "not us", false, "Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic"],
        ]
    }

    @Unroll("#featureName: #caseHandling")
    def "acquireLock"() {
        when:
        def scope = initLockService()

        def selfString = "test machine"

        def executor = Spy(ActionExecutor)
        scope = scope.overrideSingleton(ActionExecutor, executor);
        executor.update(_ as UpdateDataAction, scope) >> {
            callRealMethod(); return new UpdateResult(new UpdateDataAction(), 1)
        }
        1 * executor.query(_ as SelectDataAction, scope) >> {
            callRealMethod(); return new RowBasedQueryResult(null, [
                    "ID"    : 1,
                    "LOCKED": false,
            ])
        }
        executor.query(_ as SelectDataAction, scope) >> {
            callRealMethod(); return new RowBasedQueryResult(null, [
                    "ID"         : 1,
                    "LOCKED"     : true,
                    "LOCKEDBY"   : selfString,
                    "LOCKGRANTED": new Date(),
            ])
        }

        StandardLockService lockService = (StandardLockService) scope.get(Scope.Attr.lockService, LockService)
        lockService.myDescription = selfString

        lockService.acquireLock(scope);

        then:
        StringUtil.join(executor.getExecutedPlans(), "\n", false) == """
Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic
Execute comment(comment=Lock Liquibase) with liquibase.actionlogic.core.CommentLogic
Execute UPDATE `DATABASECHANGELOGLOCK` SET `LOCKED`='true', `LOCKGRANTED`=DATETIME(), `LOCKEDBY`='test machine' WHERE ID=1 AND LOCKED='false' with liquibase.actionlogic.core.UpdateSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic
""".trim()
    }

    def "waitForLock when no waiting"() {
        when:
        def scope = initLockService()

        def selfString = "test machine"

        def executor = Spy(ActionExecutor)
        scope = scope.overrideSingleton(ActionExecutor, executor);
        executor.update(_ as UpdateDataAction, scope) >> {
            callRealMethod(); return new UpdateResult(new UpdateDataAction(), 1)
        }
        1 * executor.query(_ as SelectDataAction, scope) >> {
            callRealMethod(); return new RowBasedQueryResult(null, [
                    "ID"    : 1,
                    "LOCKED": false,
            ])
        }
        executor.query(_ as SelectDataAction, scope) >> {
            callRealMethod(); return new RowBasedQueryResult(null, [
                    "ID"         : 1,
                    "LOCKED"     : true,
                    "LOCKEDBY"   : selfString,
                    "LOCKGRANTED": new Date(),
            ])
        }



        StandardLockService lockService = (StandardLockService) scope.get(Scope.Attr.lockService, LockService)
        lockService.myDescription = selfString

        lockService.waitForLock(scope);

        then:
        StringUtil.join(executor.getExecutedPlans(), "\n", false) == """Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic
Execute comment(comment=Lock Liquibase) with liquibase.actionlogic.core.CommentLogic
Execute UPDATE `DATABASECHANGELOGLOCK` SET `LOCKED`='true', `LOCKGRANTED`=DATETIME(), `LOCKEDBY`='test machine' WHERE ID=1 AND LOCKED='false' with liquibase.actionlogic.core.UpdateSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic
""".trim()
    }

    def "waitForLock when waiting"() {
        when:
        def scope = initLockService()

        def selfString = "test machine"

        def executor = Spy(ActionExecutor)
        scope = scope.overrideSingleton(ActionExecutor, executor);
        executor.update(_ as UpdateDataAction, scope) >> {
            callRealMethod(); return new UpdateResult(new UpdateDataAction(), 1)
        }
        3 * executor.query(_ as SelectDataAction, scope) >> {
            callRealMethod(); return new RowBasedQueryResult(null, [
                    "ID"         : 1,
                    "LOCKED"     : true,
                    "LOCKEDBY"   : "other machine",
                    "LOCKGRANTED": new Date(),
            ])
        }
        executor.query(_ as SelectDataAction, scope) >> {
            callRealMethod(); return new RowBasedQueryResult(null, [
                    "ID"         : 1,
                    "LOCKED"     : true,
                    "LOCKEDBY"   : selfString,
                    "LOCKGRANTED": new Date(),
            ])
        }



        StandardLockService lockService = (StandardLockService) scope.get(Scope.Attr.lockService, LockService)
        lockService.pollRate = 5
        lockService.myDescription = selfString

        lockService.waitForLock(scope);

        then:
        StringUtil.join(executor.getExecutedPlans(), "\n", false) == """
Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic
Execute comment(comment=Lock Liquibase) with liquibase.actionlogic.core.CommentLogic
Execute UPDATE `DATABASECHANGELOGLOCK` SET `LOCKED`='true', `LOCKGRANTED`=DATETIME(), `LOCKEDBY`='test machine' WHERE ID=1 AND LOCKED='false' with liquibase.actionlogic.core.UpdateSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic
Execute comment(comment=Lock Liquibase) with liquibase.actionlogic.core.CommentLogic
Execute UPDATE `DATABASECHANGELOGLOCK` SET `LOCKED`='true', `LOCKGRANTED`=DATETIME(), `LOCKEDBY`='test machine' WHERE ID=1 AND LOCKED='false' with liquibase.actionlogic.core.UpdateSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic
Execute comment(comment=Lock Liquibase) with liquibase.actionlogic.core.CommentLogic
Execute UPDATE `DATABASECHANGELOGLOCK` SET `LOCKED`='true', `LOCKGRANTED`=DATETIME(), `LOCKEDBY`='test machine' WHERE ID=1 AND LOCKED='false' with liquibase.actionlogic.core.UpdateSqlLogic
Execute COMMIT with liquibase.actionlogic.core.ExecuteSqlLogic
Execute SELECT * FROM `DATABASECHANGELOGLOCK` with liquibase.actionlogic.core.QuerySqlLogic
""".trim()
    }
}
