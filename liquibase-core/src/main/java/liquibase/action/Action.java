package liquibase.action;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.action.core.AlterDefaultValueAction;
import liquibase.parser.mapping.core.ActionNodeMapping;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.plugin.Plugin;

/**
 * Implementations of Action describe interactions to perform against the outside environment.
 * If the action is more of a query or update, implement {@link liquibase.action.QueryAction} or {@link liquibase.action.UpdateAction} instead.
 * Implementations should not actually contain the logic to perform the action, they only describe it. The logic to perform the action goes in implementations of {@link liquibase.actionlogic.ActionLogic}.
 * For ease of implementation, consider subclassing {@link AbstractAction}.
 * Actions are executed using {@link liquibase.actionlogic.ActionExecutor}
 * <br><br>
 * Naming and implementation best practices:<br>
 *     <ul>
 *         <li>"Create" actions should pass instances of the objects to create, such as {@link liquibase.item.core.Table} or {@link liquibase.item.core.Index}</li>
 *         <li>"Create" actions should allow many objects to be included in a single action, such as a List of Columns to allow {@link liquibase.actionlogic.ActionLogic} implementations to group creation as possible to improve performance</li>
 *         <li>"Drop" actions should normally only apply to a single object because while you could pass a collection of ObjectReferences to drop, there is normally additional info or parameters that is needed for the drop such as a data type in {@link AlterDefaultValueAction}.
 *         Even if there no other paramters currently, there may be more down the road.
 *         NOTE: if there are overriding performance reasons, it may make sense to create a multi-object drop, such as {@link liquibase.action.core.DropColumnsAction}</li>
 *         <li>Normally {@link liquibase.item.ItemReference} fields should not include "Name" as part of the field name. Just use "table" or "column" etc.</li>
 *     </ul>
 */
public interface Action extends ExtensibleObject, Plugin {

    /**
     * Return a text description of this action.
     * Two Action instances should return the same description if and only if they are equivalent calls.
     * This function should return a description of this action that contains enough information to know everything the Action is going to do and nothing that has no impact on what the Action does.
     * The return value is similar to a serialization of the object, but does not need to be deserialized.
     * Example output would include SQL to execute, a function name and all parameters, or a query string.
     * Used for equals() testing as well as logging and testing.
     */
    String describe();

    /**
     * Return the priority for this object for the given actionName.
     * Used by {@link ActionNodeMapping} to know what object to create for a given {@link liquibase.parser.ParsedNode}.
     */
    int getPriority(String actionName, Scope scope);

    /**
     * Creates {@link ParsedNodePreprocessor} instances to setup in the {@link liquibase.parser.preprocessor.ParsedNodePreprocessorFactory}.
     * Because every action normally needs to do preprocessing, we use this method instead of needing to add these classes to the META-INF/services configuration.
     * Can return null if no preprocessors are needed.
     */
    ParsedNodePreprocessor[] createPreprocessors();
}