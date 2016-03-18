package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.core.ColumnReference;

/**
 * Sets remarks/comments on a database object.
 * Setting remarks as "null" should clear out existing remarks.
 * There is no SQL-standard for setting remarks, so each database implementation must implement it's own logic class or setting remarks will not be supported.
 * Logic implementations can be a single class to support objects on any type, or separate logic classes for each type, depending on what works best.
 */
public class AlterRemarksAction extends AbstractAction {
    /**
     * The object to set the remarks on
     */
    public DatabaseObjectReference object;

    public String remarks;

    public AlterRemarksAction() {
    }

    public AlterRemarksAction(DatabaseObjectReference object, String remarks) {
        this.object = object;
        this.remarks = remarks;
    }
}
