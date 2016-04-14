package liquibase.changelog;

import liquibase.AbstractExtensibleObject;
import liquibase.action.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * A changeSet is the level at which actions are tracked.
 * Should be ran as a transaction if at all possible.
 */
public class ChangeSet extends AbstractExtensibleObject implements ChangeLogEntry {

    public String id;
    public String author;
    public String logicalPath;

    public String comment;
    public String created;

    private ChangeLog changeLog;

    public Boolean alwaysRun;
    public Boolean runOnChange;
    public Boolean failOnError;
    public Boolean runInTransaction;


    public List<Action> actions = new ArrayList<>();

    public ChangeSet() {
    }

    public ChangeSet(String id, String author, String logicalPath) {
        this.id = id;
        this.author = author;
        this.logicalPath = logicalPath;
    }

    public ChangeSet addAction(Action action) {
        this.actions.add(action);
        return this;
    }

    public ChangeLog getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(ChangeLog changeLog) {
        this.changeLog = changeLog;
    }

    /**
     * Return the path for this changeSet, starting with the locally configured {@link #logicalPath} and if null moving up to {@link ChangeLog#getPath()}.
     */
    public String getPath() {
        if (this.logicalPath != null) {
            return this.logicalPath;
        }
        if (this.changeLog == null) {
            return null;
        }
        return changeLog.getPath();
    }

    /**
     * Returns a string containing the id + author + path unique identifier.
     */
    public String getIdentifier() {
        return id+"::"+author+"::"+getPath();
    }

    public enum ExecType {
        EXECUTED("EXECUTED", false, true),
        FAILED("FAILED", false, false),
        SKIPPED("SKIPPED", false, false),
        RERAN("RERAN", true, true),
        MARK_RAN("MARK_RAN", false, true);

        ExecType(String value, boolean ranBefore, boolean ran) {
            this.value = value;
            this.ranBefore = ranBefore;
            this.ran = ran;
        }

        public final String value;
        public final boolean ranBefore;
        public final boolean ran;
    }
}
