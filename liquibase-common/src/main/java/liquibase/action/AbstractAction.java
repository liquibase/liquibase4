package liquibase.action;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;
import liquibase.util.StringUtil;

/**
 * Convenience standard implementation of {@link liquibase.action.Action}.
 * If your class is an update or query, be sure to also implement {@link liquibase.action.UpdateAction} or {@link liquibase.action.UpdateAction}.
 */
public abstract class AbstractAction extends AbstractExtensibleObject implements Action {

    private String name;

    /**
     * Default implementation returns {@link #PRIORITY_DEFAULT} if the actionName matches this class's simpleName.
     * Otherwise, returns {@link #PRIORITY_NOT_APPLICABLE}
     */
    @Override
    public int getPriority(String actionName, Scope scope) {
        if (actionName.equals(getName())) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    /**
     * Standard implementation uses reflection to list out the set properties on this object.
     */
    @Override
    public String describe() {
        String name = getName();
        return name + "(" + StringUtil.join(this, ", ", new StringUtil.DefaultFormatter()) + ")";
    }

    /**
     * Default implementation uses a lower-cased version the class name minus the trailing "Action"
     */
    public String getName() {
        if (this.name == null) {
            name = getClass().getSimpleName();
            name = name.replaceFirst("Action$", "");
            name = StringUtil.lowerCaseFirst(name);
        }
        return name;
    }

    /**
     * Default implementation compares the output of the {@link #describe()} method.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Action)) {
            return false;
        }
        return this.describe().equals(((Action) obj).describe());
    }

    @Override
    public int hashCode() {
        return this.describe().hashCode();
    }

    /**
     * Default implementation returns output from {@link #describe()}
     */
    @Override
    public String toString() {
        return this.describe();
    }

    /**
     * Default implementation returns null.
     */
    public ParsedNodePreprocessor createPreprocessor() {
       return null;
    }

    /**
     * Default implementation returns null.
     */
    @Override
    public ParsedNodeUnprocessor createUnprocessor() {
        return null;
    }
}
