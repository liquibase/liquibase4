package liquibase.command.core;

import liquibase.ExecuteMode;
import liquibase.ExtensibleObjectAttribute;
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

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;

public class UpdateSqlCommand extends UpdateCommand {

    public PrintStream outputStream = System.out;

    @ExtensibleObjectAttribute(description = "File to write SQL to")
    public String outputFile;

    @Override
    public String getName() {
        return "updateSql";
    }

    @Override
    protected CommandResult run(Scope scope) throws Exception {
        if (outputFile != null) {
            outputStream = new PrintStream(new FileOutputStream(outputFile));
        }

        try {
            return super.run(scope);
        } finally {
            if (this.outputFile != null) {
                outputStream.close();
            }
        }
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

        outputStream.println();

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
                    outputStream.println(((AbstractSqlAction) action).sql.toString());
                }
            } else if (action instanceof CommentAction) {

                if (((CommentAction) action).header != null) {
                    outputStream.println(lineComment + "*********************************************************************");
                    outputStream.println(lineComment + ((CommentAction) action).header);
                    outputStream.println(lineComment + "*********************************************************************");
                }

                String allComments = StringUtil.trimToEmpty(((CommentAction) action).comment);
                for (String comment : StringUtil.splitAndTrim(allComments, "\n")) {
                    outputStream.println(lineComment + comment);
                }

                if (((CommentAction) action).header != null && !allComments.equals("")) {
                    outputStream.println(lineComment + "*********************************************************************");
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
                    outputStream.println();
                }
                scope.getSingleton(ActionExecutor.class).execute(new CommentAction("Changeset "+changeSet.getIdentifier()), scope);
            } catch (ActionPerformException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }

        @Override
        public void ran(ChangeSet changeSet, Scope scope) {
            outputStream.println();
        }
    }
}
