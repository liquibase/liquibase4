package liquibase.parser.preprocessor;

import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.plugin.AbstractPlugin;

/**
 * Convenience base class for {@link ParsedNodePreprocessor} implementations.
 */
public abstract class AbstractParsedNodePreprocessor extends AbstractPlugin implements ParsedNodePreprocessor {

    @Override
    public Class<? extends ParsedNodePreprocessor>[] mustBeBefore() {
        return null;
    }

    @Override
    public Class<? extends ParsedNodePreprocessor>[] mustBeAfter() {
        return null;
    }

}
