package liquibase.parser.preprocessor.core.changelog;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.exception.ParseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.FunctionCall;
import liquibase.item.function.SequenceCurrentValueFunction;
import liquibase.item.function.SequenceNextValueFunction;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.AbstractParsedNodePreprocessor;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.util.CollectionUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Convenience base class for {@link ParsedNodePreprocessor} needed by {@link Action} classes.
 */
public abstract class AbstractActionPreprocessor extends AbstractParsedNodePreprocessor {

    public final String standardNodeName;

    public AbstractActionPreprocessor(Class<? extends Action> actionType) {
        try {
            standardNodeName = actionType.newInstance().getName();
        } catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    public Class<? extends ParsedNodePreprocessor>[] mustBeBefore() {
        if (getAliases() != null) {
            return new Class[]{ChangeSetPreprocessor.class};
        }
        return null;
    }

    /**
     * Return other node names that need to be translated to {@link #standardNodeName}.
     * Default implementation returns null.
     */
    protected String[] getAliases() {
        return null;
    }

    /**
     * Default implementation renames nodes that match values from {@link #getAliases()}
     * and calls {@link #processActionNode(ParsedNode, Scope)} for each node that ends up matching {@link #standardNodeName}
     */
    @Override
    public void process(ParsedNode node, Scope scope) throws ParseException {
        for (ParsedNode changeSet : node.getChildren("changeSet", true)) {
            String[] aliases = getAliases();
            if (aliases != null) {
                for (String alias : aliases) {
                    for (ParsedNode wrongName : changeSet.getChildren(alias, true)) {
                        String originalName = wrongName.name;
                        wrongName.name = standardNodeName;
                        processRenamedNode(originalName, wrongName);
                    }
                }
            }

            for (ParsedNode action : changeSet.getChildren(standardNodeName, true)) {
                processActionNode(action, scope);
            }
        }
    }

    protected void processRenamedNode(String originalName, ParsedNode node) throws ParseException {

    }

    /**
     * Usually easier to override this method with action-specific preprocessing logic than override the entire {@link #process(ParsedNode, Scope)} method.
     */
    protected abstract void processActionNode(ParsedNode actionNode, Scope scope) throws ParseException;

    protected void convertValueOptions(String baseName, ParsedNode node) throws ParseException {
        Map<String, ParsedNode> valueOptions = new HashMap<>();
        valueOptions.put(baseName, node.getChild(baseName, false));
        valueOptions.put(baseName + "Numeric", node.getChild(baseName + "Numeric", false));
        valueOptions.put(baseName + "Date", node.getChild(baseName + "Date", false));
        valueOptions.put(baseName + "Boolean", node.getChild(baseName + "Boolean", false));
        valueOptions.put(baseName + "Computed", node.getChild(baseName + "Computed", false));
        valueOptions.put(baseName + "SequenceCurrent", node.getChild(baseName + "SequenceCurrent", false));
        valueOptions.put(baseName + "SequenceNext", node.getChild(baseName + "SequenceNext", false));

        valueOptions = CollectionUtil.select(valueOptions, new CollectionUtil.CollectionFilter<Map.Entry<String, ParsedNode>>() {
            @Override
            public boolean include(Map.Entry<String, ParsedNode> obj) {
                return obj.getValue() != null;
            }
        });

        if (valueOptions.size() == 0) {
            ;//nothing to do
        } else if (valueOptions.size() > 1) {
            throw new ParseException("Cannot specify multiple " + baseName + "* attributes", node);
        } else {
            Map.Entry<String, ParsedNode> valueOption = valueOptions.entrySet().iterator().next();
            String defaultValueType = valueOption.getKey();
            ParsedNode originalDefaultValueNode = valueOption.getValue();

            if (defaultValueType.equals(baseName)) {
                ;//nothing do to
            } else {
                ParsedNode valueNode = node.addChild(baseName);
                if (originalDefaultValueNode.name.equals(baseName + "Numeric")) {
                    valueNode.value = originalDefaultValueNode.getValue(null, BigDecimal.class);
                } else if (originalDefaultValueNode.name.equals(baseName + "Date")) {
                    valueNode.value = originalDefaultValueNode.getValue(null, Date.class);
                } else if (originalDefaultValueNode.name.equals(baseName + "Boolean")) {
                    valueNode.value = originalDefaultValueNode.getValue(null, Boolean.class);
                } else if (originalDefaultValueNode.name.equals(baseName + "Computed")) {
                    valueNode.value = originalDefaultValueNode.value;
                    if (!(valueNode.value instanceof FunctionCall)) {
                        valueNode.value = new FunctionCall(valueNode.getValue(null, String.class));
                    }
                } else if (originalDefaultValueNode.name.equals(baseName + "SequenceCurrent")) {
                    valueNode.value = originalDefaultValueNode.value;
                    if (!(valueNode.value instanceof SequenceCurrentValueFunction)) {
                        valueNode.value = new SequenceCurrentValueFunction(valueNode.getValue(null, String.class));
                    }
                } else if (originalDefaultValueNode.name.equals(baseName + "SequenceNext")) {
                    valueNode.value = originalDefaultValueNode.value;
                    if (!(valueNode.value instanceof SequenceNextValueFunction)) {
                        valueNode.value = new SequenceNextValueFunction(valueNode.getValue(null, String.class));
                    }
                } else {
                    throw new ParseException("Unknown defaultValue attribute: " + originalDefaultValueNode.name, originalDefaultValueNode);
                }
            }
        }
    }
}
