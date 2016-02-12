package liquibase.database;

import liquibase.Scope;
import liquibase.SingletonService;
import liquibase.database.core.GenericDatabaseSupplier;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.servicelocator.ServiceLocator;

import java.util.*;

public class ConnectionSupplierFactory implements SingletonService {

    private final Scope scope;
    private Set<ConnectionSupplier> connectionSuppliers;

    protected ConnectionSupplierFactory(Scope scope) {
        this.scope = scope;

    }

    public Set<ConnectionSupplier> getConnectionSuppliers() {
        if (this.connectionSuppliers == null) {
            Set<ConnectionSupplier> suppliers = scope.getSingleton(ServiceLocator.class).findAllServices(ConnectionSupplier.class);

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
