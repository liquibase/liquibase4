package liquibase.action.core;

import liquibase.action.AbstractAction;
import liquibase.item.core.RelationReference;

import java.util.List;

/**
 * Insert data into a table based on the output of a query.
 */
public class InsertDataFromQueryAction extends AbstractAction {

    public RelationReference destination;
    public List<String> destinationColumns;
    public SelectDataAction query;

    public InsertDataFromQueryAction() {
    }

    public InsertDataFromQueryAction(RelationReference destination, SelectDataAction query) {
        this.destination = destination;
        this.query = query;
    }
}
