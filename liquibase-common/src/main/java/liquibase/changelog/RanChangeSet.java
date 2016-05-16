package liquibase.changelog;

import liquibase.AbstractExtensibleObject;
import liquibase.ContextExpression;
import liquibase.Labels;

import java.util.Date;

/**
 * Information about a {@link ChangeSet} that was ran in the past.
 */
public class RanChangeSet extends AbstractExtensibleObject {

    public String id;
    public String author;
    public String path;

    public Date dateExecuted;
    public String tag;
    public ChangeSet.ExecType execType;
    public String description;
    public String comments;
    public Integer orderExecuted;

    public ContextExpression contextExpression;
    public Labels labels;
    public String deploymentId;


    public boolean matches(ChangeSet changeSet) {
        return matches(changeSet.id, changeSet.author, changeSet.getPath());
    }

    public boolean matches(String id, String author, String path) {
        return this.id.equalsIgnoreCase(id) && this.author.equalsIgnoreCase(author) && this.path.equalsIgnoreCase(path);
    }
}
