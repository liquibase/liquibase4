package liquibase.changelog;

import liquibase.AbstractExtensibleObject;
import liquibase.database.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChangeLog extends AbstractExtensibleObject implements ChangeLogEntry {

    public String physicalPath;
    public String logicalPath;
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
}
