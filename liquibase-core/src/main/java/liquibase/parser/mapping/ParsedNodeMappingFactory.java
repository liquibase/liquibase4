package liquibase.parser.mapping;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.mapping.core.DefaultParsedNodeMapping;
import liquibase.plugin.AbstractPluginFactory;

/**
 * Manages {@link ParsedNodeMapping} plugins
 */
public class ParsedNodeMappingFactory extends AbstractPluginFactory<ParsedNodeMapping> {

    protected ParsedNodeMappingFactory(Scope factoryScope) {
        super(factoryScope);
    }

    @Override
    protected Class<ParsedNodeMapping> getPluginClass() {
        return ParsedNodeMapping.class;
    }

    @Override
    protected int getPriority(ParsedNodeMapping obj, Scope scope, Object... args) {
        return obj.getPriority((ParsedNode) args[0], (Class) args[1], (Class) args[2], (String) args[3], scope);
    }

    /**
     * Returns the correct {@link ParsedNodeMapping} for the passed attributes
     */
    public ParsedNodeMapping getMapping(ParsedNode node, Class objectType, Class containerType, String containerAttribute, Scope scope) {
        return getPlugin(scope, node, objectType, containerType, containerAttribute);
    }

    /**
     * Convenience method to call {@link ParsedNodeMapping#toObject(ParsedNode, Class, Class, String, Scope)} on the correct {@link ParsedNodeMapping}.
     */
    public <ObjectType> ObjectType toObject(ParsedNode node, Class<ObjectType> objectType, Class containerType, String containerAttribute, Scope scope) throws ParseException {
        if (node == null) {
            return null;
        }
        ParsedNodeMapping mapping = getMapping(node, objectType, containerType, containerAttribute, scope);
        if (mapping == null) {
            throw new ParseException("Cannot find ParsedNodeMapping for "+new DefaultParsedNodeMapping().describeParams(node, objectType, containerType, containerAttribute));
        }
        return (ObjectType) mapping.toObject(node, objectType, containerType, containerAttribute, scope);
    }

    /**
     * Convenience method to call {@link ParsedNodeMapping#toParsedNode(Object, Class, String, Scope)} on the correct {@link ParsedNodeMapping}.
     */
    public ParsedNode toParsedNode(Object object, Class containerType, String containerAttribute, Scope scope) throws ParseException {
        if (object == null) {
            return null;
        }

        ParsedNodeMapping mapping = getMapping(null, object.getClass(), containerType, containerAttribute, scope);
        if (mapping == null) {
            throw new ParseException("Cannot find ParsedNodeMapping for "+new DefaultParsedNodeMapping().describeParams(null, object.getClass(), containerType, containerAttribute));
        }

        return mapping.toParsedNode(object, containerType, containerAttribute, scope);
    }


}
