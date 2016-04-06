package liquibase.command;

import liquibase.ExtensibleObjectAttribute;
import liquibase.ObjectMetaData;
import liquibase.Scope;
import liquibase.ValidationErrors;
import liquibase.changelog.ChangeLog;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.visitor.ExecuteChangeSetVisitor;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
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
        scope = scope.child(Scope.Attr.database, scope.getSingleton(DatabaseFactory.class).connect(
                url,
                username,
                password,
                driver,
                databaseClass,
                null, //todo
                null, //todo
                scope));


        Database database = scope.getDatabase();

        ChangeLog changeLog = scope.getSingleton(ParserFactory.class).parse(changeLogFile, ChangeLog.class, scope);

        ChangeLogIterator changeLogIterator = new ChangeLogIterator(changeLog);
        changeLogIterator.run(new ExecuteChangeSetVisitor(), scope);

        return new CommandResult("Updated "+database);
    }
}
