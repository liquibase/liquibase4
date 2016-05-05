package liquibase.plugin;

import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.action.Action;

import java.util.*;

/**
 * Convenience base class for all factories that find correct {@link Plugin} implementations.
 */
public abstract class AbstractPluginFactory<T extends Plugin> implements PluginFactory {

    private final Scope factoryScope;

    private Collection<T> allInstances;

    protected AbstractPluginFactory(Scope factoryScope) {
        this.factoryScope = factoryScope;
    }

    protected abstract Class<T> getPluginClass();

    /**
     * Returns the priority of the given object based on the passed scope and args array.
     * The args are created as as part of the custom public getPlugin method in implementations are are passed through {@link #getPlugin(Scope, Object...)}
     */
    protected abstract int getPriority(T obj, Scope scope, Object... args);

    /**
     * Return the scope passed to the constructor.
     */
    protected Scope getFactoryScope() {
        return factoryScope;
    };

    /**
     * Finds the plugin for which {@link #getPriority(Plugin, Scope, Object...)}  returns the highest value for the given scope and args.
     * This method is called by a public implementation-specific method such as {@link liquibase.actionlogic.ActionLogicFactory#getActionLogic(Action, Scope)}.
     * Normally this does not need to be overridden, instead override {@link #getPriority(Plugin, Scope, Object...)} to compute the priority of each object for the scope and arguments passed to this method.
     *
     * @return null if no plugins are found or have a priority greater than zero.
     */
    protected T getPlugin(final Scope scope, final Object... args) {
        TreeSet<T> applicable = new TreeSet<>(new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                Integer o1Priority = getPriority(o1, scope, args);
                Integer o2Priority = getPriority(o2, scope, args);

                int i = o2Priority.compareTo(o1Priority);
                if (i == 0) {
                    return o1.getClass().getName().compareTo(o2.getClass().getName());
                }
                return i;
            }
        });

        for (T plugin : findAllInstances()) {
            if (getPriority(plugin, scope, args) >= 0) {
                applicable.add(plugin);
            }
        }

        if (applicable.size() == 0) {
            return null;
        }

        return applicable.iterator().next();

    }

    /**
     * Finds implementations of the given interface or class and returns instances of them.
     * Standard implementation uses {@link ServiceLoader} to find implementations and caches results in {@link #allInstances} which means the same objects are always returned.
     * If the instances should not be treated as singletons, clone the objects before returning them from {@link #getPlugin(Scope, Object...)}.
     */
    protected synchronized Collection<T> findAllInstances() {
        if (this.allInstances == null) {
            this.allInstances = new ArrayList<>();

            for (T t : ServiceLoader.load(getPluginClass(), factoryScope.getClassLoader(true))) {
                this.allInstances.add(t);
            }
        }

        return this.allInstances;
    }

}
