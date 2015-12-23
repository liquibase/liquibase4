package liquibase.database.core.mysql;


import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.ConnectionSupplier;
import liquibase.database.Database;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class MySQLConnectionSupplier extends ConnectionSupplier {

    @Override
    public String getDatabaseShortName() {
        return "mysql";
    }

    public int getPort() {
        return 3306;
    }

    @Override
    public String getAdminUsername() {
        return "root";
    }

    @Override
    public String getJdbcUrl() {
        return "jdbc:mysql://"+ getIpAddress() +"/"+getPrimaryCatalog();
    }

    @Override
    public String getConfigurationName() {
        return "caseInsensitive";
    }

    @Override
    public String getPrimarySchema() {
        return "lbcat".toLowerCase();
    }

    @Override
    public String getAlternateSchema() {
        return "lbcat2".toLowerCase();
    }
}
