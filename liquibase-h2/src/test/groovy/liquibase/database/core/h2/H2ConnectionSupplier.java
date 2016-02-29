package liquibase.database.core.h2;

import liquibase.Scope;
import liquibase.action.ExecuteSqlAction;
import liquibase.actionlogic.ActionExecutor;
import liquibase.database.ConnectionSupplier;
import liquibase.database.DatabaseConnection;
import liquibase.exception.ActionPerformException;
import liquibase.exception.DatabaseException;

public class H2ConnectionSupplier extends ConnectionSupplier {

    public H2ConnectionSupplier() {
        primaryCatalog = "LIQUIBASE";
        primarySchema = "PUBLIC";
    }

    @Override
    protected String getDatabaseShortName() {
        return "h2";
    }

    @Override
    public String createJdbcUrl() {
        return "jdbc:h2:mem:liquibase";
    }

    protected DatabaseConnection initConnection(DatabaseConnection connection, Scope scope) throws DatabaseException {
        connection = super.initConnection(connection, scope);
        try {
            Scope initScope = scope.child(Scope.Attr.database, getDatabase());
            initScope.getDatabase().setConnection(connection, scope);

            initScope.getSingleton(ActionExecutor.class).execute(new ExecuteSqlAction("CREATE SCHEMA "+alternateSchema), initScope);
        } catch (ActionPerformException e) {
            throw new DatabaseException(e);
        }

        return connection;
    }
}
