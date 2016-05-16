package liquibase;

import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.StringUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LabelExpression extends AbstractExpression {

    public LabelExpression() {
    }

    public LabelExpression(String expressions) {
        super(expressions);
    }

    public LabelExpression(Collection<String> expressions) {
        super(expressions);
    }

    public Set<String> getLabels() {
        return Collections.unmodifiableSet(expressions);
    }

    /**
     * Returns true if the passed runtime labels match this label expression
     */
    public boolean matches(Labels runtimeLabels) {
        if (runtimeLabels == null || runtimeLabels.isEmpty()) {
            return true;
        }
        if (this.expressions.size() == 0) {
            return true;
        }

        return super.matches(new HashSet<>(runtimeLabels.getLabels()));
    }

}
