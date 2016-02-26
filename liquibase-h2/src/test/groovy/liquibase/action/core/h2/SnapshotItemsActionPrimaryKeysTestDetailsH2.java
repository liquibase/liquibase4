package liquibase.action.core.h2;

import liquibase.action.core.SnapshotItemsActionPrimaryKeysTest;

public class SnapshotItemsActionPrimaryKeysTestDetailsH2 extends SnapshotItemsActionPrimaryKeysTest.TestDetails {

    @Override
    public boolean testNamedPrimaryKeys() {
        return false;
    }
}