package liquibase.database.core.h2;

import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.ObjectReference;
import liquibase.structure.core.Relation;
import liquibase.util.ISODateFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class H2Database extends AbstractJdbcDatabase {

    private static String START_CONCAT = "CONCAT(";
    private static String END_CONCAT = ")";
    private static String SEP_CONCAT = ", ";

    public H2Database() {
        super.unquotedObjectsAreUppercased=true;
        super.setCurrentDateTimeFunction("NOW()");
    }

    @Override
    public String getShortName() {
        return "h2";
    }

    @Override
    public Integer getDefaultPort() {
        return 8082;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "H2";
    }

    @Override
    public String getDefaultDriver(String url) {
        if (url.startsWith("jdbc:h2")) {
            return "org.h2.Driver";
        }
        return null;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        return "H2".equals(conn.getDatabaseProductName());
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

    @Override
    public String escapeObjectName(ObjectReference objectReference) {
        if (objectReference.type != null && Relation.class.isAssignableFrom(objectReference.type) && objectReference.container != null && objectReference.container.container != null ) { //cannot reference catalog level
            objectReference = (ObjectReference) objectReference.clone();
            objectReference.container.container = null;
        }
        return super.escapeObjectName(objectReference);
    }

    @Override
    public String getDateLiteral(String isoDate) {
        String returnString = isoDate;
        try {
            if (isDateTime(isoDate)) {
                ISODateFormat isoTimestampFormat = new ISODateFormat();
                DateFormat dbTimestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                returnString = dbTimestampFormat.format(isoTimestampFormat.parse(isoDate));
            }
        } catch (ParseException e) {
            throw new RuntimeException("Unexpected date format: " + isoDate, e);
        }
        return "'" + returnString + "'";
    }



    @Override
    public boolean isReservedWord(String objectName) {
        return keywords.contains(objectName.toUpperCase());
    }

    private static List keywords = Arrays.asList(
            "CROSS",
            "CURRENT_DATE",
            "CURRENT_TIME",
            "CURRENT_TIMESTAMP",
            "DISTINCT",
            "EXCEPT",
            "EXISTS",
            "FALSE",
            "FOR",
            "FROM",
            "FULL",
            "GROUP",
            "HAVING",
            "INNER",
            "INTERSECT",
            "IS",
            "JOIN",
            "LIKE",
            "LIMIT",
            "MINUS",
            "NATURAL",
            "NOT",
            "NULL",
            "ON",
            "ORDER",
            "PRIMARY",
            "ROWNUM",
            "SELECT",
            "SYSDATE",
            "SYSTIME",
            "SYSTIMESTAMP",
            "TODAY",
            "TRUE",
            "UNION",
            "UNIQUE",
            "WHERE");

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    protected String getAutoIncrementClause() {
        return "AUTO_INCREMENT";
    }

    @Override
    protected String getAutoIncrementStartWithClause() {
	return "%d";
    }

    @Override
    protected String getAutoIncrementByClause() {
	return "%d";
    }

    @Override
    public boolean createsIndexesForForeignKeys() {
        return true;
    }

    @Override
    public boolean isCaseSensitive(Class<? extends LiquibaseObject> type) {
        return true;
    }
}
