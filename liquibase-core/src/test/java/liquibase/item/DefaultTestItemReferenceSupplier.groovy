package liquibase.item

import liquibase.Scope

public class DefaultTestItemReferenceSupplier<T extends Item> extends TestItemReferenceSupplier<T> {

    int getPriority(Class<? extends Item> type, Scope scope) {
        return PRIORITY_DEFAULT;
    }
}
