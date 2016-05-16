package liquibase.listener;

import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.exception.LiquibaseException;

public class ChangeSetListener implements LiquibaseListener {

    public void willRun(ChangeSet changeSet, Scope scope) {

    }

    public void ran(ChangeSet changeSet, Scope scope) {

    }

    public void runFailed(LiquibaseException exception, ChangeSet changeSet, Scope scope) {

    }

    public void actionsRan(ChangeSet changeSet, Scope scope) {

    }
}
