package liquibase.parser.postprocessor.core;

import liquibase.Scope;
import liquibase.changelog.ChangeLog;
import liquibase.changelog.ChangeLogEntry;
import liquibase.changelog.ChangeSet;
import liquibase.exception.ParseException;
import liquibase.parser.postprocessor.AbstractMappingPostprocessor;

/**
 * {@link liquibase.parser.postprocessor.MappingPostprocessor} for {@link ChangeLog} objects.
 */
public class ChangeLogPostprocessor extends AbstractMappingPostprocessor {

    /**
     * Sets the {@link ChangeSet#changeLog} value for all changeSets in this ChangeLog.
     */
    @Override
    public void process(Object object, Scope scope) throws ParseException {
        if (object instanceof ChangeLog) {
            for (ChangeLogEntry entry : ((ChangeLog) object).changeLogEntries) {
                if (entry instanceof ChangeSet) {
                    ((ChangeSet) entry).setChangeLog((ChangeLog) object);
                }
            }
        }
    }
}
