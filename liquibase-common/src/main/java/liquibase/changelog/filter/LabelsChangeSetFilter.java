package liquibase.changelog.filter;

import liquibase.LabelExpression;
import liquibase.Scope;
import liquibase.changelog.ChangeSet;

public class LabelsChangeSetFilter implements ChangeSetFilter {
    private LabelExpression labelExpression;

    public LabelsChangeSetFilter() {
        this(new LabelExpression());
    }

    public LabelsChangeSetFilter(LabelExpression labels) {
        this.labelExpression = labels;
    }

    @Override
    public Result allow(ChangeSet changeSet, Scope scope) {
//        List<SqlVisitor> visitorsToRemove = new ArrayList<SqlVisitor>();
//        for (SqlVisitor visitor : changeSet.getSqlVisitors()) {
//            if (visitor.getLabels() != null && !labelExpression.matches(visitor.getLabels())) {
//                visitorsToRemove.add(visitor);
//            }
//        }
//        changeSet.getSqlVisitors().removeAll(visitorsToRemove);

        if (labelExpression == null || labelExpression.isEmpty()) {
            return new Result(true, "No runtime labels specified, all labels will run", this.getClass());
        }

        if (changeSet.labels == null || changeSet.labels.isEmpty()) {
            return new Result(true, "Change set runs under all labels", this.getClass());
        }

        if (labelExpression.matches(changeSet.labels)) {
            return new Result(true, "Labels matches '" + labelExpression.toString() + "'", this.getClass());
        } else {
            return new Result(false, "Labels does not match '" + labelExpression.toString() + "'", this.getClass());
        }
    }
}
