package liquibase;

import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.StringUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AbstractExpression {

    protected HashSet<String> expressions = new LinkedHashSet<>();
    protected String originalString = null;

    public AbstractExpression() {
    }

    public AbstractExpression(String expressions) {
        if (expressions != null) {
            expressions = expressions.replace("\\", "");
        }
        parseExpressions(expressions);
        originalString = expressions;
    }

    public AbstractExpression(Collection<String> expressions) {
        if (expressions != null) {
            for (String expression : expressions) {
                this.expressions.add(expression.toLowerCase());
            }
        }
    }

    public boolean add(String expression) {
        return this.expressions.add(expression.toLowerCase());
    }

    @Override
    public String toString() {
        if (originalString != null) {
            return originalString;
        }
        return "(" + StringUtil.join(this.expressions, "), (") + ")";
    }



    protected void parseExpressions(String expressions) {
        expressions = StringUtil.trimToNull(expressions);

        if (expressions == null) {
            return;
        }
        for (String expression : StringUtil.splitAndTrim(expressions, ",")) {
            this.expressions.add(expression.toLowerCase());
        }

    }

    protected boolean matches(String expression, Collection<String> runtimeValues) {
        if (runtimeValues.isEmpty()) {
            return true;
        }

        if (expression.trim().equals(":TRUE")) {
            return true;
        }
        if (expression.trim().equals(":FALSE")) {
            return false;
        }

        while (expression.contains("(")) {
            Pattern pattern = Pattern.compile("(.*?)\\(([^\\(\\)]*?)\\)(.*)");
            Matcher matcher = pattern.matcher(expression);
            if (!matcher.matches()) {
                throw new UnexpectedLiquibaseException("Cannot parse pattern "+expression);
            }
            String parenExpression = matcher.group(2);

            parenExpression = ":"+String.valueOf(matches(parenExpression, runtimeValues)).toUpperCase();

            expression = matcher.group(1)+" "+parenExpression+" "+matcher.group(3);
        }

        String[] orSplit = expression.split("\\s+or\\s+");
        if (orSplit.length > 1) {
            for (String split : orSplit) {
                if (matches(split, runtimeValues)) {
                    return true;
                }
            }
            return false;
        }

        String[] andSplit = expression.split("\\s+and\\s+");
        if (andSplit.length > 1) {
            for (String split : andSplit) {
                if (!matches(split, runtimeValues)) {
                    return false;
                }
            }
            return true;
        }


        boolean notExpression = false;
        if (expression.startsWith("!")) {
            notExpression = true;
            expression = expression.substring(1);
        } else if (expression.toLowerCase().startsWith("not ")) {
            notExpression = true;
            expression = expression.substring(4);
        }


        if (expression.trim().equals(":TRUE")) {
            return !notExpression;
        }
        if (expression.trim().equals(":FALSE")) {
            return notExpression;
        }

        for (String runtimeValue : runtimeValues) {
            if (runtimeValue.equalsIgnoreCase(expression)) {
                if (notExpression) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        if (notExpression) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean matches(Collection<String> runtimeExpressions) {
        for (String expression : this.expressions) {
            if (matches(expression, runtimeExpressions)) {
                return true;
            }
        }
        return false;

    }

    public boolean isEmpty() {
        return this.expressions == null || this.expressions.size() == 0;
    }

}
