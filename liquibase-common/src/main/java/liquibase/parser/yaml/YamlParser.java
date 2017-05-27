package liquibase.parser.yaml;

import liquibase.Scope;
import liquibase.exception.LiquibaseException;
import liquibase.exception.ParseException;
import liquibase.parser.AbstractParser;
import liquibase.parser.ParsedNode;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.parser.ParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Parses yaml files using SnakeYAML.
 */
public class YamlParser extends AbstractParser {

    @Override
    public int getPriority(String path, Scope scope) {
        if ((path.toLowerCase().endsWith(".yaml") || path.toLowerCase().endsWith(".yml")) && scope.getResourceAccessor() != null) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public ParsedNode parse(String path, Scope scope) throws ParseException {
        Yaml yaml = new Yaml(new StrictMapAppenderConstructor());

        try (InputStream inputStream = scope.getResourceAccessor().openStream(path)) {

            if (inputStream == null) {
                throw new ParseException("Could not find file to parse: " + path, null);
            }

            Map<String, ?> parsedYaml;
            try {
                parsedYaml = yaml.loadAs(inputStream, Map.class);
            } catch (Exception e) {
                throw new ParseException("Syntax error in " + path + ": " + e.getMessage(), e, null);
            }

            if (parsedYaml == null || parsedYaml.size() == 0) {
                throw new ParseException("Empty file " + path, null);
            } else if (parsedYaml.size() > 1) {
                throw new ParseException("Found " + parsedYaml.size() + " root nodes in " + path, null);
            }

            Map.Entry<String, ?> rootEntry = parsedYaml.entrySet().iterator().next();
            ParsedNode rootNode = ParsedNode.createRootNode(rootEntry.getKey());

            addToParsedNode(rootEntry.getValue(), rootNode);

            rootNode.addChild("physicalPath").setValue(path);

            return rootNode;
        } catch (ParseException e) {
            throw e;
        } catch (LiquibaseException | IOException e) {
            throw new ParseException(e, null);
        }
    }

    protected void addToParsedNode(Object value, ParsedNode parent) throws ParseException {

        if (value instanceof Map) {
            for (Map.Entry<String, Object> entry : ((Map<String, Object>) value).entrySet()) {
                ParsedNode child = parent.addChild(entry.getKey());
                addToParsedNode(entry.getValue(), child);
            }
        } else if (value instanceof Collection) {
            for (Object childValue : (Collection) value) {
                if (childValue instanceof Map) {
                    addToParsedNode(childValue, parent);
                } else {
                    List parentValue = (List) parent.getValue();
                    if (parentValue == null) {
                        parentValue = new ArrayList<>();
                        parent.setValue(parentValue);
                    }
                    parentValue.add(childValue);
                }
            }
        } else {
            parent.setValue(value);
        }
    }

    @Override
    public String describeOriginal(ParsedNode parsedNode) {
        return new YamlUnparser().describeOriginal(parsedNode);
    }

    public static class StrictMapAppenderConstructor extends Constructor {

        public StrictMapAppenderConstructor(Class<?> theRoot) {
            super(theRoot);
        }

        public StrictMapAppenderConstructor() {
            super();
        }

        @Override
        protected Map<Object, Object> constructMapping(MappingNode node) {
            try {
                return super.constructMapping(node);
            } catch (IllegalStateException e) {
                throw new ParserException("while parsing MappingNode", node.getStartMark(), e.getMessage(), node.getEndMark());
            }
        }

        @Override
        protected Map<Object, Object> createDefaultMap() {
            final Map<Object, Object> delegate = super.createDefaultMap();
            return new AbstractMap<Object, Object>() {
                @Override
                public Object put(Object key, Object value) {
                    if (delegate.containsKey(key)) {
                        throw new IllegalStateException("duplicate key: " + key);
                    }
                    return delegate.put(key, value);
                }
                @Override
                public Set<Entry<Object, Object>> entrySet() {
                    return delegate.entrySet();
                }
            };
        }

    }
}
