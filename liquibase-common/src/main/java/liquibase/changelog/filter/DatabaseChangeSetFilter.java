package liquibase.changelog.filter;

import liquibase.DatabaseExpression;
import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;

public class DatabaseChangeSetFilter implements ChangeSetFilter {

    private Database database;

    public DatabaseChangeSetFilter(Database database) {
        this.database = database;
    }

    @Override
    public Result allow(ChangeSet changeSet, Scope scope) {
        if (database == null) {
            return new Result(true, "No database connection, cannot evaluate dbms attribute", this.getClass());
        }
//         List<SqlVisitor> visitorsToRemove = new ArrayList<SqlVisitor>();
//        for (SqlVisitor visitor : changeSet.getSqlVisitors()) {
//            if (!DatabaseList.definitionMatches(visitor.getApplicableDbms(), database, true)) {
//                visitorsToRemove.add(visitor);
//            }
//        }
//        changeSet.getSqlVisitors().removeAll(visitorsToRemove);

        DatabaseExpression expression = changeSet.dbms;

        if (expression == null || expression.isEmpty()) {
            return new Result(true, "ChangSet runs under all databases", this.getClass());
        } else if (expression.matches(database)) {
            return new Result(true, "Database matches dbms expression '"+database.getShortName()+"'", this.getClass());
        } else {
            return new Result(false, "Database does not match dbms expression '"+database.toString()+"'", this.getClass());
        }
    }
}
