package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.StoredProcedure;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates stored procedures.
 */
public class CreateStoredProceduresAction extends AbstractAction {

    public List<StoredProcedure> procedures = new ArrayList<>();

    /**
     * If true, replace an existing procedure with the same name if they exist.
     */
    public Boolean replaceIfExists;

}
