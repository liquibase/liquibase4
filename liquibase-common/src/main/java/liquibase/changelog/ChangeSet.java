package liquibase.changelog;

import liquibase.*;
import liquibase.action.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * A changeSet is the level at which actions are tracked. ChangeSets are uniquely identified by a combination of their {@link #id} + {@link #author} + {@link #getPath()}
 * Should be ran as a transaction if at all possible.
 */
public class ChangeSet extends AbstractExtensibleObject implements ChangeLogEntry {

    /**
     * "id" specified in changeLog file.  Combination of id+author+filePath must be unique
     */
    public String id;

    /**
     * "author" defined in changeLog file.  Having each developer use a unique author tag allows duplicates of "id" attributes between developers.
     */
    public String author;

    /**
     * File changeSet is defined in.  May be a logical/non-physical string.  It is included in the unique identifier to allow duplicate id+author combinations in different files
     */
    public String logicalPath;

    /**
     * ChangeSet comments defined in changeLog file
     */
    public String comment;

    /**
     * Describes when the changeSet was created. Used for reporting purposes.
     */
    public String created;

    private ChangeLog changeLog;

    /**
     * If set to true, the changeSet will be executed on every update.
     */
    public Boolean alwaysRun;

    /**
     * If set to true, the changeSet will be executed when the checksum changes.
     */
    public Boolean runOnChange;


    /**
     * If false, do not stop liquibase update execution if an error is thrown executing the changeSet.
     */
    public Boolean failOnError;

    /**
     * If true, the changeSet will run in a database transaction.  Defaults to true
     */
    public Boolean runInTransaction;

    /**
     * Runtime contexts in which the changeSet will be executed.  If null or empty, will execute regardless of contexts set
     */
    public ContextExpression contexts;

    /**
     * "Labels" associated with this changeSet.  If null or empty, will execute regardless of contexts set
     */
    public Labels labels;


    public List<Action> actions = new ArrayList<>();

    public DatabaseExpression dbms;

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

    public ContextExpression getCompleteContextExpression() {
        return this.contexts;
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
