package liquibase.structure;

import liquibase.Scope;
import liquibase.snapshot.Snapshot;
import liquibase.structure.core.Schema;
import liquibase.util.ObjectUtil;

import java.util.Collections;
import java.util.List;

public class SchemaTestObjectReferenceSupplier extends TestObjectReferenceSupplier {

    @Override
    public int getPriority(Class type, Scope scope) {
        if (Schema.class.isAssignableFrom(type)) {
            return PRIORITY_SPECIALIZED;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public List<ObjectReference> getObjectNames(Class type, ObjectNameStrategy nameStrategy, Scope scope) {
        return ObjectUtil.defaultIfEmpty(getObjectContainers(scope), Collections.singletonList((ObjectReference) null));
    }
}
