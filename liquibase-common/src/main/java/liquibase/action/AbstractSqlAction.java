package liquibase.action;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor;
import liquibase.parser.unprocessor.AbstractActionUnprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;
import liquibase.util.ObjectUtil;
import liquibase.util.StringClauses;
import liquibase.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard base class for sql-based actions.
 * Normally subclass from the more specific {@link UpdateSqlAction}, {@link QuerySqlAction} and {@link ExecuteSqlAction}.
 */
public abstract class AbstractSqlAction extends AbstractAction {

    public StringClauses sql;
    public String endDelimiter;
    public Boolean splitStatements;
    public Boolean stripComments;
    public List<String> dbmsFilters = new ArrayList<>();

    public AbstractSqlAction() {
    }

    public AbstractSqlAction(String sql) {
        this.sql = new StringClauses().append(sql);
    }

    public AbstractSqlAction(StringClauses sql) {
        this.sql = sql;
    }

    @Override
    public String describe() {
        return sql + ObjectUtil.defaultIfNull(endDelimiter, "");
    }

    protected abstract static class SqlPreprocessor extends AbstractActionPreprocessor {

        public SqlPreprocessor(Class<? extends AbstractSqlAction> actionType) {
            super(actionType);
        }

        @Override
        protected void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException {
            actionNode.removeChildren("comment");

            if (actionNode.getValue() != null) {
                actionNode.moveValue(actionNode.addChild("sql"));
            }

            ParsedNode dbms = actionNode.getChild("dbms", false);
            if (dbms != null) {
                dbms.rename("dbmsFilters").setValue(StringUtil.splitAndTrim(dbms.getValue(null, String.class), ","));
                dbms.removeMarker(ParsedNode.Marker.isAttribute);
            }
        }
    }

    protected abstract class SqlUnprocessor extends AbstractActionUnprocessor {
        public SqlUnprocessor(Class<? extends AbstractSqlAction> actionType) {
            super(actionType);
        }

        @Override
        protected void unprocessAction(ParsedNode actionNode, Scope scope) throws ParseException {
            convertListToCommaSeparatedAttribute("dbmsFilters", actionNode);
            actionNode.renameChildren("dbmsFilters", "dbms");

            ParsedNode sql = actionNode.getChild("sql", false);
            if (sql != null) {
                sql.removeMarker(ParsedNode.Marker.isAttribute);
                sql.addMarker(ParsedNode.Marker.isText);
            }
        }
    }
}
