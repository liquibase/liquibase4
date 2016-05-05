package liquibase.changelog;

import liquibase.AbstractExtensibleObject;
import liquibase.database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class ChangeLog extends AbstractExtensibleObject implements ChangeLogEntry {

    /**
     * The physical path from which the changelog was actually loaded. Used for reporting and finding relative files.
     */
    public String physicalPath;

    /**
     * The path used in 'path' section of a changeSet's id+author+path identifier. If null, the {@link #physicalPath} will be used.
     */
    public String logicalPath;

    /**
     * The default {@link liquibase.database.Database.QuotingStrategy} to use for actions objects in this changeLog.
     */
    public Database.QuotingStrategy objectQuotingStrategy;

    public List<ChangeLogEntry> changeLogEntries = new ArrayList<>();


    public ChangeLog addEntry(ChangeLogEntry entry) {
        this.changeLogEntries.add(entry);
        return this;
    }

    public List<ChangeSet> getChangeSets() {
        List<ChangeSet> returnList = new ArrayList<>();
        for (ChangeLogEntry entry : changeLogEntries) {
            if (entry instanceof ChangeSet) {
                returnList.add((ChangeSet) entry);
            }
        }
        return Collections.unmodifiableList(returnList);
    }

    /**
     * Returns the {@link #logicalPath}, or if not set then returns the {@link #physicalPath}
     */
    public String getPath() {
        if (logicalPath != null) {
            return logicalPath;
        }
        return physicalPath;
    }
}
