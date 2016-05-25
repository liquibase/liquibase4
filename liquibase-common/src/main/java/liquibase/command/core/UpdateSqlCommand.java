package liquibase.command.core;

import liquibase.ExecuteMode;
import liquibase.Scope;
import liquibase.action.AbstractSqlAction;
import liquibase.action.Action;
import liquibase.action.QuerySqlAction;
import liquibase.action.core.CommentAction;
import liquibase.actionlogic.ActionExecutor;
import liquibase.actionlogic.ActionLogic;
import liquibase.changelog.ChangeSet;
import liquibase.command.CommandResult;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.listener.ActionListener;
import liquibase.listener.ChangeSetListener;
import liquibase.util.LiquibaseUtil;
import liquibase.util.StringUtil;

import java.text.DateFormat;
import java.util.Date;

public class UpdateSqlCommand extends UpdateCommand {

    StringBuilder sql = new StringBuilder();

    @Override
    public String getName() {
        return "updateSql";
    }

    @Override
    protected CommandResult run(Scope scope) throws Exception {
        CommandResult result = super.run(scope);
        if (result.succeeded) {
            return new UpdateSqlCommandResult(this.sql);
        }
        return result;
    }

    @Override
    protected Scope setupScope(Scope scope) throws LiquibaseException {
        scope = scope.child(Scope.Attr.executeMode, ExecuteMode.READ_ONLY);
        scope = scope.child(new LogSqlActionListener());
        scope = scope.child(new LogChangeSetListener());

        scope = super.setupScope(scope);

        CommentAction commentAction = new CommentAction();
        commentAction.header = "Update Database Script";
        commentAction.comment = "Change Log: " + this.changeLogFile + "\n" +
                "Ran at: " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date()) + "\n" +
                "Against: " + this.username + "@" + this.url + "\n" +
                "Liquibase version: " + LiquibaseUtil.getBuildVersion();

        scope.getSingleton(ActionExecutor.class).execute(commentAction, scope);

        sql.append(scope.getLineSeparator());

        return scope;
    }

    private class LogSqlActionListener extends ActionListener {

        @Override
        public void willRun(Action action, ActionLogic logic, Scope scope) {
            String lineComment = null;

            Database database = scope.getDatabase();
            if (database != null) {
                lineComment = database.getLineComment(scope);
            }
            if (lineComment == null) {
                lineComment = "--";
            }


            if (action instanceof AbstractSqlAction) {
                if (!(action instanceof QuerySqlAction)) {
                    sql.append(((AbstractSqlAction) action).sql.toString()).append(scope.getLineSeparator());
                }
            } else if (action instanceof CommentAction) {

                if (((CommentAction) action).header != null) {
                    sql.append(lineComment).append("*********************************************************************").append(scope.getLineSeparator());;
                    sql.append(lineComment + ((CommentAction) action).header).append(scope.getLineSeparator());
                    sql.append(lineComment + "*********************************************************************").append(scope.getLineSeparator());;
                }

                String allComments = StringUtil.trimToEmpty(((CommentAction) action).comment);
                for (String comment : StringUtil.splitAndTrim(allComments, "\n")) {
                    sql.append(lineComment + comment).append(scope.getLineSeparator());
                }

                if (((CommentAction) action).header != null && !allComments.equals("")) {
                    sql.append(lineComment + "*********************************************************************").append(scope.getLineSeparator());;
                }
            }

        }
    }

    protected class LogChangeSetListener extends ChangeSetListener {

        int changeSetCount = 0;

        @Override
        public void willRun(ChangeSet changeSet, Scope scope) {
            try {
                if (changeSetCount == 0) {
                    sql.append(scope.getLineSeparator());
                }
                scope.getSingleton(ActionExecutor.class).execute(new CommentAction("Changeset "+changeSet.getIdentifier()), scope);
            } catch (ActionPerformException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }

        @Override
        public void ran(ChangeSet changeSet, Scope scope) {
            sql.append(scope.getLineSeparator());
        }
    }

    public static class UpdateSqlCommandResult extends CommandResult {
        public String sql;

        public UpdateSqlCommandResult(StringBuilder sql) {
            this.sql = sql.toString();
        }

        @Override
        public String print(Scope scope) throws LiquibaseException {
            return sql;
        }
    }
}
