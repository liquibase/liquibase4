package liquibase.parser;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.SingletonObject;
import liquibase.exception.ParseException;
import liquibase.plugin.Plugin;

import java.io.OutputStream;

/**
 * Unparsers take a {@link ParsedNode} and convert it into a file.
 * They should not do any processing or fixing of the parsedNode, just output something that directly matches the node structure.
 *
 * @see AbstractUnparser
 * @see UnparserFactory
 */
public interface Unparser extends Plugin, ExtensibleObject, SingletonObject {

    int getPriority(String path, Scope scope);

    /**
     * Outputs the passed node to the output stream.
     */
    void unparse(ParsedNode node, OutputStream output, Scope scope) throws ParseException;

    /**
     * Outputs a pseudo version of the original version of the given parsedNode.
     * Used for error messages and other times Liquibase needs to direct users to the original version of a parsed node,
     * especially when there are no original line numbers stored.
     */
    String describeOriginal(ParsedNode parsedNode);

}
