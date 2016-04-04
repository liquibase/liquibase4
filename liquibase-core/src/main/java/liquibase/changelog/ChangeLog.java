package liquibase.changelog;

import liquibase.AbstractExtensibleObject;
import liquibase.database.Database;

import java.util.ArrayList;
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

}
