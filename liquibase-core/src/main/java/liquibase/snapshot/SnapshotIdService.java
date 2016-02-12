package liquibase.snapshot;

import liquibase.Scope;
import liquibase.SingletonService;
import liquibase.util.MD5Util;

import java.util.Date;

public class SnapshotIdService implements SingletonService {

    private int nextId = 100;
    private String base = MD5Util.computeMD5(Long.toString(new Date().getTime())).substring(0, 4);

    protected SnapshotIdService(Scope scope) {

    }

    public String generateId() {
        return base+Integer.toString(nextId++);
    }
}
