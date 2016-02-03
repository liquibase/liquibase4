package liquibase.action.core.h2;

import liquibase.action.core.SnapshotObjectsActionPrimaryKeysTest;

public class SnapshotObjectsActionPrimaryKeysTestDetailsH2 extends SnapshotObjectsActionPrimaryKeysTest.TestDetails {

    @Override
    public boolean testNamedPrimaryKeys() {
        return false;
    }
}