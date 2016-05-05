package liquibase.parser;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.exception.ParseException;
import liquibase.plugin.Plugin;

/**
 * Parsers take a file and convert it into {@link ParsedNode} object.
 * They should not do any processing or fixing of the parsedNode, just output something that directly matches the file.
 *
 * @see AbstractParser
 * @see ParserFactory
 */
public interface Parser extends Plugin, ExtensibleObject, SingletonObject {

    int getPriority(String path, Scope scope);

    /**
     * Parse the given path into a {@link ParsedNode}
     */
    ParsedNode parse(String path, Scope scope) throws ParseException;

    /**
     * Outputs a pseudo version of the original version of the given parsedNode.
     * Used for error messages and other times Liquibase needs to direct users to the original version of a parsed node,
     * especially when there are no original line numbers stored.
     */
    String describeOriginal(ParsedNode parsedNode);
}
