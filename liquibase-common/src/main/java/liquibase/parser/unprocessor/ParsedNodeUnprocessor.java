package liquibase.parser.unprocessor;

import liquibase.DependencyObject;
import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.plugin.Plugin;

/**
 * Implementations of this interface are used to transform {@link ParsedNode}s before attempting to map them into files.
 * This interface extends {@link DependencyObject} so that ordering can be controlled.
 */
public interface ParsedNodeUnprocessor extends Plugin, DependencyObject<ParsedNodeUnprocessor>, ExtensibleObject {

    /**
     * Process the given node, making whatever changes are needed to it.
     * Note: this method is only called once during the unparsing process and is called on the root parsed node,
     * so normally this method must find child and grandchild nodes that need to be changed
     */
    void unprocess(ParsedNode node, String outputPath, Scope scope) throws ParseException;
}
