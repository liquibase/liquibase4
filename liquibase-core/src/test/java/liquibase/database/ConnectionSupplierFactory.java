package liquibase.database;

import liquibase.Scope;
import liquibase.database.core.GenericDatabaseSupplier;
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
            if (this.connectionSuppliers.size() > 1) { //don't want to include the UnsupportedSupplier
                Iterator<ConnectionSupplier> iterator = this.connectionSuppliers.iterator();
                while (iterator.hasNext()) {
                    ConnectionSupplier supplier = iterator.next();
                    if (supplier instanceof GenericDatabaseSupplier) {
                        iterator.remove();
                    }
                }
            }
        }
        return this.connectionSuppliers;
    }
}
