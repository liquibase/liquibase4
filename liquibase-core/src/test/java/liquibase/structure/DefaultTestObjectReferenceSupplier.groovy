package liquibase.structure

import liquibase.Scope

public class DefaultTestObjectReferenceSupplier<T extends LiquibaseObject> extends TestObjectReferenceSupplier<T> {

    int getPriority(Class<? extends LiquibaseObject> type, Scope scope) {
        return PRIORITY_DEFAULT;
    }
}
