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

    public String comments;
    public String created;

    public ChangeLog changeLog;

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
}
