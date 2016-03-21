package liquibase.action.core;

import liquibase.action.AbstractAction;

import java.util.ArrayList;
import java.util.List;

public class ExecuteShellCommandAction extends AbstractAction {

    public String executable;
    public List<String> args = new ArrayList<>();
    public List<String> osFilters = new ArrayList<>();
}
