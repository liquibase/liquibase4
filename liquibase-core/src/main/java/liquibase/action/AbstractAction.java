package liquibase.action;

import liquibase.AbstractExtensibleObject;
import liquibase.util.StringUtil;

/**
 * Convenience standard implementation of {@link liquibase.action.Action}.
 * If your class is an update or query, be sure to also implement {@link liquibase.action.UpdateAction} or {@link liquibase.action.UpdateAction}.
 */
public abstract class AbstractAction extends AbstractExtensibleObject implements Action {

    /**
     * Standard implementation uses reflection to list out the set properties on this object.
     */
    @Override
    public String describe() {
        String name = getClass().getSimpleName();
        name = name.replaceFirst("Action$", "");
        name = StringUtil.lowerCaseFirst(name);
        return name+"("+ StringUtil.join(this, ", ", new StringUtil.DefaultFormatter())+")";
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
}
