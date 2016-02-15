package liquibase.database.core.h2;

import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;
import liquibase.structure.LiquibaseObject;

import java.util.Arrays;

public class H2Database extends AbstractJdbcDatabase {

    public H2Database() {
        this.currentDateTimeFunction = "NOW()";

        this.reservedWords.addAll(Arrays.asList(
                "LIMIT",
                "MINUS",
                "ROWNUM",
                "SYSDATE",
                "SYSTIME",
                "SYSTIMESTAMP",
                "TODAY",
                "WHERE"
        ));
    }

    @Override
    public String getShortName() {
        return "h2";
    }

    @Override
    public String getDefaultDriver(String url, Scope scope) {
        return "org.h2.Driver";
    }

    @Override
    public boolean supports(DatabaseConnection conn, Scope scope) throws DatabaseException {
        return "H2".equals(conn.getDatabaseProductName());
    }

    @Override
    protected int getMaxObjectPathLength(Class<? extends LiquibaseObject> objectType, Scope scope) {
        int length = super.getMaxObjectPathLength(objectType, scope);
        if (length > 2) { //Can only return up to object + schema
            return 2;
        } else {
            return length;
        }
    }

    @Override
    public boolean supports(Feature feature, Scope scope) {
        switch (feature) {
            case AUTO_CREATES_INDEXES_FOR_FOREIGN_KEYS:
                return true;
        }
        return super.supports(feature, scope);
    }
}
