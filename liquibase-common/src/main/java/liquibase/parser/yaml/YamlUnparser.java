package liquibase.parser.yaml;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.AbstractUnparser;
import liquibase.parser.ParsedNode;
import liquibase.util.StringClauses;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

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
        return new Yaml(createRepresenter(), createDumperOptions());
    }

    protected Representer createRepresenter() {
        return new YamlRepresenter();
    }


    protected DumperOptions createDumperOptions() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setIndent(4);
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        return dumperOptions;
    }

    @Override
    public void unparse(ParsedNode node, OutputStream output, Scope scope) throws ParseException {
        LinkedHashMap<String, Object> outputMap = new LinkedHashMap<>();
        outputMap.put(node.getName(), getChildren(node));

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output))) {
            writer.write(yaml.dumpAsMap(outputMap));
            writer.write(scope.getLineSeparator());
            writer.flush();
        } catch (IOException e) {
            throw new ParseException(e, null);
        }

    }

    protected Object getChildren(ParsedNode node) throws ParseException {
        List<ParsedNode> children = new ArrayList<>(node.getChildren());
        if (children.size() == 0) {
            return node.getValue();
        } else {
            if (node.getValue() != null) {
                throw new ParseException("Cannot convert node with children and a value to YAML", node);
            }

            boolean isCollection = node.hasMarker(ParsedNode.Marker.isCollectionNode) || node.hasDuplicateChildren();

            if (isCollection) {
                List returnList = new ArrayList();
//                boolean onlyScalarValues = true;
//                for (ParsedNode childNode : children) {
//                    if (childNode.getChildren().size() > 0 || childNode.getValue() == null) {
//                        onlyScalarValues = false;
//                        break;
//                    }
//                }


                for (ParsedNode childNode : children) {
//                    if (onlyScalarValues) {
//                        returnList.add(childNode.getValue());
//                    } else {
                        LinkedHashMap<String, Object> childMap = new LinkedHashMap<>();
                        childMap.put(childNode.getName(), getChildren(childNode));
                        returnList.add(childMap);
//                    }
                }

                return returnList;
            } else {
                Collections.sort(children);

                LinkedHashMap returnMap = new LinkedHashMap();
                for (ParsedNode childNode : children) {
                    returnMap.put(childNode.getName(), getChildren(childNode));
                }
                return returnMap;
            }
        }
    }

    protected static class YamlRepresenter extends Representer {

        public YamlRepresenter() {
            multiRepresenters.put(StringClauses.class, new AsStringRepresenter());
            multiRepresenters.put(Enum.class, new AsStringRepresenter());
        }


        private class AsStringRepresenter implements Represent {
            @Override
            public Node representData(Object data) {
                return representScalar(Tag.STR, data.toString());
            }
        }
    }
}
