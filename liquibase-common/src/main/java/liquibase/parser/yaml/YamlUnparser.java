package liquibase.parser.yaml;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.AbstractUnparser;
import liquibase.parser.ParsedNode;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * Unparses yaml files using SnakeYAML.
 */
public class YamlUnparser extends AbstractUnparser {

    protected Yaml yaml;

    @Override
    public int getPriority(String path, Scope scope) {
        if (path.toLowerCase().endsWith(".yaml") || path.toLowerCase().endsWith(".yml")) {
            return PRIORITY_DEFAULT;
        } else {
            return PRIORITY_NOT_APPLICABLE;
        }

    }

    public YamlUnparser() {
        yaml = createYaml();
    }

    protected Yaml createYaml() {
//        if (isJson()) {
//            DumperOptions dumperOptions = new DumperOptions();
//            dumperOptions.setPrettyFlow(true);
//            dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
//            dumperOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.DOUBLE_QUOTED);
//            dumperOptions.setWidth(Integer.MAX_VALUE);
//
//            return new Yaml(getLiquibaseRepresenter(), dumperOptions);
//        }


        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setIndent(4);
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        return new Yaml(dumperOptions);
    }

    @Override
    public void unparse(ParsedNode node, OutputStream output, Scope scope) throws ParseException {
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put(node.getName(), getChildren(node));

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output))) {

            writer.write(yaml.dumpAsMap(outputMap));
            writer.write(scope.getLineSeparator());
            writer.flush();
        } catch (IOException e) {
            throw new ParseException(e, null);
        }

    }

    private Object getChildren(ParsedNode node) throws ParseException {
        if (node.getChildren().size() == 0) {
            return node.getValue();
        } else {
            if (node.getValue() != null) {
                throw new ParseException("Cannot convert node with children and a value to YAML", node);
            }

            boolean duplicateChildren = false;
            Set<String> names = new HashSet<>();
            for (ParsedNode child : node.getChildren()) {
                if (!names.add(child.getName())) {
                    duplicateChildren = true;
                    break;
                }
            }

            if (duplicateChildren) {
                List returnList = new ArrayList();
                for (ParsedNode childNode : node.getChildren()) {
                    Map<String, Object> childMap = new HashMap<>();
                    childMap.put(childNode.getName(), getChildren(childNode));
                    returnList.add(childMap);
                }
                return returnList;
            } else {
                Map returnMap = new HashMap();
                for (ParsedNode childNode : node.getChildren()) {
                    returnMap.put(childNode.getName(), getChildren(childNode));
                }
                return returnMap;
            }
        }
    }

    @Override
    public String describeOriginal(ParsedNode parsedNode) {
        return new YamlParser().describeOriginal(parsedNode);
    }
}
