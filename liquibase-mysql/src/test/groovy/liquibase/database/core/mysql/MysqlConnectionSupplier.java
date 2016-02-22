package liquibase.database.core.mysql;


import liquibase.database.ConnectionSupplier;

public class MysqlConnectionSupplier extends ConnectionSupplier {

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
