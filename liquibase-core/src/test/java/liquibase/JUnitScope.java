package liquibase;

import liquibase.database.ConnectionSupplier;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.resource.JUnitResourceAccessor;
import liquibase.util.SmartMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton root scope for JUnit tests. Use this for all Scope objects in tests to avoid re-initialization of singletons. If you want fresh Singletons for a test, use {@link JUnitEmptyScope}.
 */
public class JUnitScope extends Scope {

    private static JUnitScope instance;
    private SmartMap singletons = new SmartMap();

    public enum Attr {
        connectionSupplier,
        objectNameStrategy
    }

    private JUnitScope() throws Exception {
        super(new JUnitResourceAccessor(), new HashMap<String, Object>());
    }

    private JUnitScope(Scope parent, Map<String, Object> scopeValues) {
        super(parent, scopeValues);
    }

    public static JUnitScope getInstance() {
        if (instance == null) {
            try {
                instance = new JUnitScope();
            } catch (Exception e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        return instance;
    }

    public static JUnitScope getInstance(Database database) {
        return (JUnitScope) getInstance().child(Scope.Attr.database, database);
    }

    public static JUnitScope getInstance(ConnectionSupplier supplier) throws DatabaseException {
        Scope scope = getInstance()
                .child(Scope.Attr.database, supplier.getDatabase())
                .child(Attr.connectionSupplier, supplier);

        return (JUnitScope) supplier.connect(scope);
    }

    @Override
    public Scope child(Map<String, Object> scopeValues) {
        return new JUnitScope(this, scopeValues);
    }

    /**
     * Create a child scope that uses the given singleton instead falling back to the root scope's singleton like usual.
     */
    public <T> JUnitScope overrideSingleton(Class<T> type, T singleton) {
        JUnitScope childScope = (JUnitScope) child(new HashMap<String, Object>());
        childScope.singletons.put(type.getName(), singleton);

        return childScope;
    }

    @Override
    public <T extends SingletonObject> T getSingleton(Class<T> type) {
        T singleton = this.singletons.get(type.getName(), type);
        if (singleton == null) {
            return super.getSingleton(type);
        } else {
            return singleton;
        }
    }
}
