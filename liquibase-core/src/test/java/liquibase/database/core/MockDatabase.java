package liquibase.database.core;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Schema;
import liquibase.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MockDatabase extends AbstractExtensibleObject implements Database {

    private DatabaseConnection connection;

    private boolean caseSensitive;
    private Map<Class<? extends LiquibaseObject>, Boolean> supports = new HashMap<>();
    private boolean supportsAutoIncrement = true;


    @Override
    public int getPriority(Scope scope) {
        return PRIORITY_NOT_APPLICABLE;
    }


    public Schema getSchema() {
        return null;
    }

    public String getName() {
        return "Mock Database";
    }

    public LiquibaseObject[] getContainingObjects() {
        return null;
    }

    public boolean equals(final LiquibaseObject otherObject, final Database accordingTo) {
        return otherObject.getName().equalsIgnoreCase(this.getName());
    }

    @Override
    public boolean supports(final DatabaseConnection conn, Scope scope) {
        return false;
    }

    @Override
    public boolean supports(Feature feature, Scope scope) {
        return feature.getSupportedByDefault();
    }

    @Override
    public boolean supports(String featureKey, Scope scope) {
        try {
            return supports(Feature.valueOf(featureKey), scope);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public DatabaseConnection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(final DatabaseConnection conn, Scope scope) {
        this.connection = conn;
    }

    @Override
    public IdentifierCaseHandling getIdentifierCaseHandling(Class<? extends LiquibaseObject> type, boolean quoted, Scope scope) {
        return IdentifierCaseHandling.CASE_SENSITIVE;
    }

    @Override
    public boolean isValidObjectName(String name, boolean quoted, Class<? extends LiquibaseObject> type, Scope scope) {
        return true;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    @Override
    public String getShortName() {
        return "mock";
    }

    public String getDriverName() throws DatabaseException {
        return null;
    }

    public String getConnectionURL() throws DatabaseException {
        return null;
    }

    public String getConnectionUsername() throws DatabaseException {
        return null;
    }

    @Override
    public String getDateString(Date date, Scope scope) {
        return date.toString();
    }

    @Override
    public String getTimeString(Date time, Scope scope) {
        return time.toString();
    }

    @Override
    public String getDateTimeString(Date date, Scope scope) {
        return date.toString();
    }

    @Override
    public String getCurrentDateTimeFunction(Scope scope) {
        return "DATETIME()";
    }

    @Override
    public String getLineComment(Scope scope) {
        return null;
    }

    @Override
    public boolean isSystemObject(final ObjectReference object, Scope scope) {
        return false;
    }

    @Override
    public boolean isLiquibaseObject(final ObjectReference object, Scope scope) {
        return false;
    }

    @Override
    public boolean supports(Class<? extends LiquibaseObject> type, Scope scope) {
        if (supports.containsKey(type)) {
            return supports.get(type);
        }
        return true;
    }

    public MockDatabase setSupports(Class<? extends LiquibaseObject> type, Boolean supports) {
        this.supports.put(type, supports);
        return this;
    }

    @Override
    public String quoteObjectName(final String objectName, final Class<? extends LiquibaseObject> objectType, Scope scope) {
        return "`" + objectName + "`";
    }

    @Override
    public String quoteObjectName(ObjectReference objectReference, Scope scope) {
        return StringUtils.join(objectReference.asList(), ".", new StringUtils.ObjectNameFormatter(objectReference.type, scope));
    }

    @Override
    public String quoteString(String string, Scope scope) {
        if (string == null) {
            return null;
        }
        return "'" + string.replaceAll("'", "''") + "'";
    }

    @Override
    public boolean isReservedWord(final String string, Scope scope) {
        return false;
    }

    @Override
    public String toString() {
        return "Mock database";
    }

}
