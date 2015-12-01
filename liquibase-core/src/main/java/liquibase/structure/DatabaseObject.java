package liquibase.structure;

import liquibase.ExtensibleObject;

public interface DatabaseObject extends Comparable, ExtensibleObject {

    String getSnapshotId();

    String getObjectTypeName();

    String getName();

    ObjectReference getContainer();

    boolean snapshotByDefault();

    ObjectReference toReference();
}

