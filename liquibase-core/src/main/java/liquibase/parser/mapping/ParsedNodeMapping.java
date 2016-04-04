package liquibase.parser.mapping;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.mapping.core.DefaultParsedNodeMapping;
import liquibase.plugin.Plugin;

import java.lang.reflect.Type;

/**
 * Implementations of this interface are able to convert between {@link ParsedNode}s and normal objects.
 *
 * @see ParsedNodeMappingFactory
 * @see AbstractParsedNodeMapping
 * @see DefaultParsedNodeMapping
 */
public interface ParsedNodeMapping<ObjectType> extends Plugin {

    /**
     * Return the priority for this implementation for the given options.
     * @param parsedNode the node to parse. May be null if this mapping will be used to create a parsedNode.
     * @param objectType the type of the object to create via {@link #toObject(ParsedNode, Class, Class, String, Scope)} and/or to parse via {@link #toParsedNode(Object, Class, String, ParsedNode, Scope)}
     * @param containerType the {@link Type} of the object containing the object to convert to a ParsedNode and/or to save the object created to.
     * @param containerAttribute the attribute on the containerType containing the object to convert to a ParsedNode and/or to save the object created to.
     */
    int getPriority(ParsedNode parsedNode, Class objectType, Type containerType, String containerAttribute, Scope scope);

    /**
     * Converts the given objectToConvert into a parsed node. Using the containerType and/or containerAttribute as needed.
     */
    ParsedNode toParsedNode(ObjectType objectToConvert, Class containerType, String containerAttribute, ParsedNode parentNode, Scope scope) throws ParseException;

    /**
     * Converts the given parsedNode into an object of the given objectType. Use the containerType and/or containerAttribute as needed.
     */
    ObjectType toObject(ParsedNode parsedNode, Class<ObjectType> objectType, Class containerType, String containerAttribute, Scope scope) throws ParseException;
}
