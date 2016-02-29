package liquibase.database.core.mysql;


import liquibase.database.ConnectionSupplier;

public class MysqlConnectionSupplier extends ConnectionSupplier {

    public MysqlConnectionSupplier() {
        primarySchema = "lbcat";
        alternateSchema = "lbcat2";
    }

    @Override
    protected String getDatabaseShortName() {
        return "mysql";
    }

    @Override
    public String createJdbcUrl() {
        return "jdbc:mysql://"+ host +"/"+primaryCatalog;
    }

    @Override
    public String getName() {
        return "caseInsensitive";
    }

}
