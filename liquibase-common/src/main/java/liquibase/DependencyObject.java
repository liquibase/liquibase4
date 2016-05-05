package liquibase;

import java.util.Collection;

/**
 * A DependencyObject can be sorted based on the classes returned by {@link #mustBeBefore()} and {@link #mustBeAfter()}.
 *
 * @see liquibase.util.DependencyUtil#sort(Collection) for sorting
 */
public interface DependencyObject<BaseClass extends DependencyObject> {

    /**
     * List of classes this object must be before.
     * Return null if it doesn't have to be before anything.
     */
    Class<? extends BaseClass>[] mustBeBefore();

    /**
     * List of classes this object must be after.
     * Return null if it doesn't have to be after anything.
     */
    Class<? extends BaseClass>[] mustBeAfter();
}
