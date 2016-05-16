package liquibase.changelog.filter;

import liquibase.ContextExpression;
import liquibase.Contexts;
import liquibase.Scope;
import liquibase.changelog.ChangeSet;

public class ContextsChangeSetFilter implements ChangeSetFilter {
    private Contexts contexts;

    public ContextsChangeSetFilter() {
        this(new Contexts());
    }

    public ContextsChangeSetFilter(Contexts contexts) {
        this.contexts = contexts;
    }

    @Override
    public Result allow(ChangeSet changeSet, Scope scope) {
//        List<SqlVisitor> visitorsToRemove = new ArrayList<SqlVisitor>();
//        for (SqlVisitor visitor : changeSet.getSqlVisitors()) {
//            if (visitor.getContexts() != null && !visitor.getContexts().matches(contexts)) {
//                visitorsToRemove.add(visitor);
//            }
//        }
//        changeSet.getSqlVisitors().removeAll(visitorsToRemove);

        if (contexts == null || contexts.isEmpty()) {
            return new Result(true, "No runtime context specified, all contexts will run", this.getClass());
        }

        ContextExpression completeContextExpression = changeSet.getCompleteContextExpression();

        if (completeContextExpression == null || completeContextExpression.isEmpty()) {
            return new Result(true, "ChangSet runs under all contexts", this.getClass());
        } else if (completeContextExpression.matches(contexts)) {
            return new Result(true, "Context matches '"+contexts.toString()+"'", this.getClass());
        } else {
            return new Result(false, "Context does not match '"+contexts.toString()+"'", this.getClass());
        }
    }
}
