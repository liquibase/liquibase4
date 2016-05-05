package liquibase.database;

import liquibase.Scope;
import liquibase.database.core.GenericConnectionSupplier;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.plugin.AbstractPluginFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConnectionSupplierFactory extends AbstractPluginFactory<ConnectionSupplier> {

    private Set<ConnectionSupplier> connectionSuppliers;

    protected ConnectionSupplierFactory(Scope scope) {
        super(scope);

    }

    @Override
    protected Class<ConnectionSupplier> getPluginClass() {
        return ConnectionSupplier.class;
    }

    @Override
    protected int getPriority(ConnectionSupplier obj, Scope scope, Object... args) {
        throw new UnexpectedLiquibaseException("Not used");
    }

    public Set<ConnectionSupplier> getConnectionSuppliers() {
        if (this.connectionSuppliers == null) {
            Collection<ConnectionSupplier> suppliers = findAllInstances();

            if (suppliers.size() == 0) {
                throw new UnexpectedLiquibaseException("Could not find ConnectionSupplier implementations");
            }

            this.connectionSuppliers = new HashSet<>(suppliers);
        }
        return this.connectionSuppliers;
    }
}
