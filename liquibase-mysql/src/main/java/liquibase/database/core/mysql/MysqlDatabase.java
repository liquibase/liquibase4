package liquibase.database.core.mysql;

import com.mysql.jdbc.JDBC4Connection;
import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.JdbcConnection;
import liquibase.structure.LiquibaseObject;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Index;
import liquibase.structure.core.Sequence;

import java.sql.Connection;
import java.util.Arrays;

public class MysqlDatabase extends AbstractJdbcDatabase {

    public MysqlDatabase() {
        this.currentDateTimeFunction = "NOW()";
        this.identifierStartQuote = "`";
        this.identifierEndQuote = "`";
    }

    @Override
    public String getShortName() {
        return "mysql";
    }

    @Override
    public void setConnection(DatabaseConnection conn, Scope scope) {
        if (conn instanceof JdbcConnection) {
            Connection underlyingConnection = ((JdbcConnection) conn).getUnderlyingConnection();
            ((JDBC4Connection) underlyingConnection).setUseInformationSchema(true);
        }
        super.setConnection(conn, scope);
    }

    @Override
    public boolean supports(DatabaseConnection conn, Scope scope) {
        return "MySQL".equalsIgnoreCase(conn.getDatabaseProductName());
    }

    @Override
    public boolean supports(Class<? extends LiquibaseObject> type, Scope scope) {
        return !type.isAssignableFrom(Sequence.class)
                && super.supports(type, scope);
    }

    @Override
    protected int getMaxObjectPathLength(Class<? extends LiquibaseObject> objectType, Scope scope) {
        if (Index.class.isAssignableFrom(objectType) || ForeignKey.class.isAssignableFrom(objectType)) {
            return 1;
        }
        return super.getMaxObjectPathLength(objectType, scope);
    }

    @Override
    public String quoteString(String string, Scope scope) {
        if (string == null) {
            return null;
        }
        return super.quoteString(string.replace("\\", "\\\\"), scope);
    }

    @Override
    public boolean supports(Feature feature, Scope scope) {
        switch (feature) {
            case DISABLING_FOREIGN_KEYS:
                return true;
            case NAMED_PRIMARY_KEYS:
                return false;
            case INDEXES_DESC:
                return false;
            case AUTO_CREATES_INDEXES_FOR_FOREIGN_KEYS:
                return true;
            default:
                return super.supports(feature, scope);
        }
    }

    @Override
    public boolean metaDataCallsSchemasCatalogs() {
        return true;
    }

    {
        //list from http://dev.mysql.com/doc/refman/5.6/en/reserved-words.html
        reservedWords.addAll(Arrays.asList("ACCESSIBLE",
                "ANALYZE",
                "ASC",
                "BEFORE",
                "CASCADE",
                "CHANGE",
                "CONDITION",
                "CONVERT",
                "DATABASE",
                "DATABASES",
                "DAY_HOUR",
                "DAY_MICROSECOND",
                "DAY_MINUTE",
                "DAY_SECOND",
                "DELAYED",
                "DESC",
                "DISTINCTROW",
                "DIV",
                "DUAL",
                "ELSEIF",
                "ENCLOSED",
                "ESCAPED",
                "EXIT",
                "EXPLAIN",
                "FLOAT4",
                "FLOAT8",
                "FORCE",
                "FULLTEXT",
                "HIGH_PRIORITY",
                "HOUR_MICROSECOND",
                "HOUR_MINUTE",
                "HOUR_SECOND",
                "IF",
                "IGNORE",
                "INDEX",
                "INFILE",
                "INT1",
                "INT2",
                "INT3",
                "INT4",
                "INT8",
                "ITERATE",
                "KEY",
                "KEYS",
                "KILL",
                "LEAVE",
                "LIMIT",
                "LINEAR",
                "LINES",
                "LOAD",
                "LOCK",
                "LONG",
                "LONGBLOB",
                "LONGTEXT",
                "LOOP",
                "LOW_PRIORITY",
                "MAXVALUE",
                "MEDIUMBLOB",
                "MEDIUMINT",
                "MEDIUMTEXT",
                "MIDDLEINT",
                "MINUTE_MICROSECOND",
                "MINUTE_SECOND",
                "MOD",
                "NO_WRITE_TO_BINLOG",
                "OPTIMIZE",
                "OPTION",
                "OPTIONALLY",
                "OUTFILE",
                "PURGE",
                "READ",
                "READ_WRITE",
                "REGEXP",
                "RENAME",
                "REPEAT",
                "REPLACE",
                "REQUIRE",
                "RESIGNAL",
                "RESTRICT",
                "RLIKE",
                "SCHEMA",
                "SCHEMAS",
                "SECOND_MICROSECOND",
                "SEPARATOR",
                "SHOW",
                "SIGNAL",
                "SQL_BIG_RESULT",
                "SQL_CALC_FOUND_ROWS",
                "SQL_SMALL_RESULT",
                "SSL",
                "STARTING",
                "STRAIGHT_JOIN",
                "TERMINATED",
                "TINYBLOB",
                "TINYINT",
                "TINYTEXT",
                "UNDO",
                "UNLOCK",
                "UNSIGNED",
                "USAGE",
                "USE",
                "UTC_DATE",
                "UTC_TIME",
                "UTC_TIMESTAMP",
                "VARBINARY",
                "VARCHARACTER",
                "WHILE",
                "WRITE",
                "XOR",
                "YEAR_MONTH",
                "ZEROFILL"));
    }

}
