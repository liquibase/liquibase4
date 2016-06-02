package liquibase.command.core;

import liquibase.ExtensibleObjectAttribute;
import liquibase.Scope;
import liquibase.action.Action;
import liquibase.changelog.ChangeLog;
import liquibase.changelog.ChangeSet;
import liquibase.command.AbstractSnapshotCommand;
import liquibase.command.CommandResult;
import liquibase.diff.output.changelog.ActionGeneratorFactory;
import liquibase.exception.LiquibaseException;
import liquibase.parser.UnparserFactory;
import liquibase.snapshot.Snapshot;
import liquibase.util.StringUtil;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

public class GenerateChangeLogCommand extends AbstractSnapshotCommand<GenerateChangeLogCommand.GenerateChangeLogCommandResult> {

    public String idRoot = String.valueOf(new Date().getTime());
    private int changeNumber = 1;
    public String changeSetAuthor;

    @ExtensibleObjectAttribute(description = "Path to changelog file", required = false)
    public String changeLogFile;


    @Override
    public String getName() {
        return "generateChangeLog";
    }

    @Override
    protected GenerateChangeLogCommandResult run(Scope scope) throws Exception {
        if (changeSetAuthor == null) {
            changeSetAuthor = StringUtil.trimToNull(System.getProperty("user.name"));
            if (changeSetAuthor == null) {
                changeSetAuthor = "diff-generated";
            } else {
                changeSetAuthor = changeSetAuthor + " (generated)";
            }

        }

        scope = setupDatabase(scope);

        Snapshot snapshot = snapshot(scope);
        Snapshot emptySnapshot = new Snapshot(scope);

        List<? extends Action> actions = scope.getSingleton(ActionGeneratorFactory.class).fix(emptySnapshot, snapshot, scope);

        ChangeLog changeLog = new ChangeLog();
        changeLog.physicalPath = this.changeLogFile;

        for (Action action : actions) {
            ChangeSet changeSet = new ChangeSet(generateId(), this.changeSetAuthor, this.changeLogFile);
            changeSet.addAction(action);

            changeLog.addEntry(changeSet);
        }

        return new GenerateChangeLogCommandResult(changeLog);
    }

    protected String generateId() {
        return idRoot + "-" + changeNumber++;
    }


    public static class GenerateChangeLogCommandResult extends CommandResult {
        private final ChangeLog changeLog;

        public GenerateChangeLogCommandResult(ChangeLog changeLog) {
            this.changeLog = changeLog;
        }

        @Override
        public String print(Scope scope) throws LiquibaseException {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            scope.getSingleton(UnparserFactory.class).unparse(changeLog, outputStream, changeLog.physicalPath, scope);

            return outputStream.toString();
        }
    }
}
