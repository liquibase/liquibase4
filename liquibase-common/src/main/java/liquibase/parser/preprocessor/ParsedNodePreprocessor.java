package liquibase.parser.preprocessor;

import liquibase.DependencyObject;
import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.plugin.Plugin;

/**
 * Implementations of this interface are used to transform {@link ParsedNode}s before attempting to map them into objects.
 * This interface extends {@link DependencyObject} so that ordering can be controlled.
 */
public interface ParsedNodePreprocessor extends Plugin, DependencyObject<ParsedNodePreprocessor>, ExtensibleObject{

    /**
     * Process the given node, making whatever changes are needed to it.
     * Note: this method is only called once during the parsing process and is called on the root parsed node,
     * so normally this method must find child and grandchild nodes that need to be changed
     */
    void process(ParsedNode node, Scope scope) throws ParseException;
}
