package liquibase.parser.postprocessor;

import liquibase.DependencyObject;
import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.plugin.Plugin;

/**
 * Implementations of this interface are used to transform objects as part of parsing, after ParsedNodes have been converted to objects with a {@link liquibase.parser.mapping.ParsedNodeMapping}.
 * This interface extends {@link DependencyObject} so that ordering can be controlled.
 */
public interface MappingPostprocessor extends Plugin, DependencyObject<MappingPostprocessor>, ExtensibleObject {

    /**
     * Process the given object, making whatever changes are needed to it.
     * Note: this method is only called once during the parsing process and is called on the root object,
     * so this method will often traverse down the object to make whatever changes are needed.
     */
    void process(Object object, Scope scope) throws ParseException;

}
