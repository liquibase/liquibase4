package liquibase.changelog;

import liquibase.AbstractExtensibleObject;

import java.util.ArrayList;
import java.util.List;

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


    public List<ChangeSetEntry> changeSetEntries = new ArrayList<>();
}
