package liquibase;

import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.StringUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Encapsulates logic for evaluating if a set of runtime contexts matches a context expression string.
 */
public class ContextExpression extends AbstractExpression {

    public ContextExpression() {
    }

    public ContextExpression(String expressions) {
        super(expressions);
    }

    public ContextExpression(Collection<String> expressions) {
        super(expressions);
    }

    public Set<String> getContexts() {
        return Collections.unmodifiableSet(this.expressions);
    }

    /**
     * Returns true if the passed runtime contexts match this context expression
     */
    public boolean matches(Contexts runtimeContexts) {
        if (runtimeContexts == null || runtimeContexts.isEmpty()) {
            return true;
        }
        if (this.expressions.size() == 0) {
            return true;
        }

        return matches(new HashSet<>(runtimeContexts.getContexts()));
    }
}
