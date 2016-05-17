package liquibase.command.core;

import liquibase.*;
import liquibase.changelog.ChangeLog;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.filter.ContextsChangeSetFilter;
import liquibase.changelog.filter.DatabaseChangeSetFilter;
import liquibase.changelog.filter.LabelsChangeSetFilter;
import liquibase.changelog.filter.ShouldRunChangeSetFilter;
import liquibase.changelog.visitor.ExecuteChangeSetVisitor;
import liquibase.command.AbstractLiquibaseCommand;
import liquibase.command.CommandResult;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.LiquibaseException;
import liquibase.lockservice.LockServiceFactory;
import liquibase.lockservice.LockService;
import liquibase.parser.ParserFactory;
import liquibase.util.ObjectUtil;

/**
 * Updates a database to match the given {@link #changeLogFile}.
 */
public class UpdateCommand extends AbstractLiquibaseCommand<CommandResult> {

    @ExtensibleObjectAttribute(description = "Path to changelog", required = true)
    public String changeLogFile;

    @ExtensibleObjectAttribute(description = "Database url", required = true)
    public String url;

    @ExtensibleObjectAttribute(description = "Database username")
    public String username;

    @ExtensibleObjectAttribute(description = "Database password")
    public String password;

    @ExtensibleObjectAttribute(description = "Database driver")
    public String driver;

    @ExtensibleObjectAttribute(description = "Liquibase Database class to use")
    public String databaseClass;

    @ExtensibleObjectAttribute(description = "ChangeSet 'contexts' to execute")
    public Contexts contexts;

    @ExtensibleObjectAttribute(description = "ChangeSet 'labels' to execute")
    public LabelExpression labels;

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public ValidationErrors validate(Scope scope) {
        ValidationErrors validationErrors = new ValidationErrors(this);

        for (ObjectMetaData.Attribute attr : getObjectMetaData().attributes) {
            if (ObjectUtil.defaultIfNull(attr.required, false)) {
                validationErrors.checkRequiredFields(attr.name);
            }
        }
        return validationErrors;
    }

    @Override
    protected CommandResult run(Scope scope) throws Exception {
        scope = setupScope(scope);
        ChangeLog changeLog = parseChangelog(scope);

        scope = setupLockService(scope);
        LockService lockService = scope.get(Scope.Attr.lockService, LockService.class);
        lockService.waitForLock(scope);

        try {
            scope = setupHistoryService(scope);

            ChangeLogIterator changeLogIterator = createChangeLogIterator(changeLog, scope);

            changeLogIterator.run(new ExecuteChangeSetVisitor(), scope);

            return new CommandResult("Updated "+this.url);
        } finally {
            lockService.releaseLock(scope);
        }
    }

    protected ChangeLogIterator createChangeLogIterator(ChangeLog changeLog, Scope scope) throws LiquibaseException {
        ChangeLogHistoryService historyService = scope.get(Scope.Attr.changeLogHistoryService, ChangeLogHistoryService.class);
        Database database = scope.getDatabase();

        return new ChangeLogIterator(changeLog,
                    new ShouldRunChangeSetFilter(historyService.getRanChangeSets(scope)),
                    new ContextsChangeSetFilter(contexts),
                    new LabelsChangeSetFilter(labels),
                    new DatabaseChangeSetFilter(database));
    }

    protected Scope setupHistoryService(Scope scope) throws LiquibaseException {
        ChangeLogHistoryService historyService = scope.getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogHistoryService(scope);
        scope = scope.child(Scope.Attr.changeLogHistoryService, historyService);
        historyService.init(scope);

        return scope;
    }

    protected Scope setupLockService(Scope scope) throws LiquibaseException {
        LockService lockService = scope.getSingleton(LockServiceFactory.class).getLockService(scope);
        lockService.init(scope);

        scope = scope.child(Scope.Attr.lockService, lockService);

        return scope;
    }

    protected ChangeLog parseChangelog(Scope scope) throws liquibase.exception.ParseException {
        return scope.getSingleton(ParserFactory.class).parse(changeLogFile, ChangeLog.class, scope);
    }

    protected Scope setupScope(Scope scope) throws LiquibaseException {
        scope = scope.child(Scope.Attr.database, scope.getSingleton(DatabaseFactory.class).connect(
                url,
                username,
                password,
                driver,
                databaseClass,
                null, //todo
                null, //todo
                scope));
        return scope;
    }

    @Override
    public ExtensibleObject set(String attribute, Object value) {
        if (attribute.equals("contexts") && value instanceof String) {
            value = new Contexts((String) value);
        }
        if (attribute.equals("labels") && value instanceof String) {
            value = new LabelExpression((String) value);
        }

        return super.set(attribute, value);
    }
}
