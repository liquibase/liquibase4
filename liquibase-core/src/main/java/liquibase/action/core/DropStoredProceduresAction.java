package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.StoredDatabaseLogicReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Drop existing stored procedures
 */
public class DropStoredProceduresAction extends AbstractAction {

    public List<StoredDatabaseLogicReference> procedures = new ArrayList<>();

    public DropStoredProceduresAction() {

    }

    public DropStoredProceduresAction(StoredDatabaseLogicReference... procedures) {
        if (procedures != null) {
            this.procedures.addAll(Arrays.asList(procedures));
        }
    }

}
