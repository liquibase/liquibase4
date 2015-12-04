package liquibase.database.core;

import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.InternalDatabase;
import liquibase.exception.DatabaseException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Schema;
import liquibase.util.StringUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MockDatabase implements Database, InternalDatabase {

    private DatabaseConnection connection;

    private boolean caseSensitive;
    private Map<Class<? extends LiquibaseObject>, Boolean> supports = new HashMap<>();
    private boolean supportsAutoIncrement = true;


    @Override
    public int getPriority(Scope scope) {
        return PRIORITY_DEFAULT;
    }


    public Schema getSchema() {
        return null;
    }

    public String getName() {
        return "Mock Database";
    }

    @Override
    public Integer getDefaultPort() {
        return null;
    }

    public LiquibaseObject[] getContainingObjects() {
        return null;
    }

    public boolean equals(final LiquibaseObject otherObject, final Database accordingTo) {
        return otherObject.getName().equalsIgnoreCase(this.getName());
    }


    @Override
    public boolean requiresUsername() {
        return false;
    }

    @Override
    public boolean requiresPassword() {
        return false;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(final DatabaseConnection conn) throws DatabaseException {
        return false;
    }

    @Override
    public String getDefaultDriver(final String url) {
        return null;
    }

    @Override
    public DatabaseConnection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(final DatabaseConnection conn) {
        this.connection = conn;
    }

    @Override
    public boolean getAutoCommitMode() {
        return false;
    }

    @Override
    public boolean isAutoCommit() throws DatabaseException {
        return false;
    }


    @Override
    public boolean isCaseSensitive(Class<? extends LiquibaseObject> type) {
        return caseSensitive;
    }

    @Override
    public boolean canStoreObjectName(String name, boolean quoted, Class<? extends LiquibaseObject> type) {
        return true;
    }

    @Override
    public boolean canStoreObjectName(String name, Class<? extends LiquibaseObject> type) {
        return true;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    @Override
    public void setAutoCommit(final boolean b) throws DatabaseException {

    }

    @Override
    public boolean supportsDDLInTransaction() {
        return false;
    }

    @Override
    public String getDatabaseProductName() {
        return null;
    }

    @Override
    public String getDatabaseProductVersion() throws DatabaseException {
        return null;
    }


    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        return 0;
    }

    @Override
    public int getDatabaseMinorVersion() throws DatabaseException {
        return 0;
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
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }


    @Override
    public String getDateLiteral(final java.sql.Date date) {
        return date.toString();
    }

    @Override
    public String getTimeLiteral(final Time time) {
        return time.toString();
    }

    @Override
    public String getDateTimeLiteral(final Timestamp timeStamp) {
        return timeStamp.toString();
    }

    @Override
    public String getCurrentDateTimeFunction() {
        return "DATETIME()";
    }

    @Override
    public void setCurrentDateTimeFunction(final String function) {
    }

    @Override
    public String getLineComment() {
        return null;
    }

    @Override
    public boolean isSystemObject(final ObjectReference example) {
        return false;
    }

    @Override
    public boolean isLiquibaseObject(final ObjectReference object) {
        return false;
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

    @Override
    public String getDateLiteral(final Date defaultDateValue) {
        return defaultDateValue.toString();
    }

    @Override
    public boolean supports(Class<? extends LiquibaseObject> type) {
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
    public boolean supportsAutoIncrement() {
        return supportsAutoIncrement;
    }

    public void setSupportsAutoIncrement(boolean supportsAutoIncrement) {
        this.supportsAutoIncrement = supportsAutoIncrement;
    }

    @Override
    public String generatePrimaryKeyName(final String tableName) {
        return "PK_" + tableName;
    }

    @Override
    public void commit() {
        ;
    }

    @Override
    public void rollback() {
        ;
    }

    @Override
    public void close() throws DatabaseException {
        ;
    }

    @Override
    public boolean supportsRestrictForeignKeys() {
        return true;
    }

    @Override
    public String escapeObjectName(final String objectName, final Class<? extends LiquibaseObject> objectType) {
        return "`" + objectName + "`";
    }

    @Override
    public String escapeObjectName(ObjectReference objectReference) {
        return StringUtils.join(objectReference.asList(), ".", new StringUtils.ObjectNameFormatter(objectReference.type, this));
    }

    @Override
    public String escapeString(String string) {
        return string;
    }

    @Override
    public boolean supportsForeignKeyDisable() {
        return false;
    }

    @Override
    public boolean disableForeignKeyChecks() throws DatabaseException {
        return false;
    }

    @Override
    public void enableForeignKeyChecks() throws DatabaseException {

    }

    @Override
    public boolean isReservedWord(final String string) {
        return false;
    }

    @Override
    public boolean createsIndexesForForeignKeys() {
        return false;
    }

    @Override
    public boolean supportsPrimaryKeyNames() {
        return true;
    }

    @Override
    public String toString() {
        return "Mock database";
    }

    @Override
    public boolean supportsClustered(Class<? extends LiquibaseObject> objectType) {
        return true;
    }

    @Override
    public boolean requiresDefiningColumnsAsNull() {
        return false;
    }

    @Override
    public String escapeDataTypeName(String dataTypeName) {
        return dataTypeName;
    }

}
