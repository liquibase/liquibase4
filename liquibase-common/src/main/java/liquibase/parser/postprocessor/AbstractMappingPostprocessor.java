package liquibase.parser.postprocessor;

import liquibase.plugin.AbstractPlugin;

/**
 * Convenience base class for {@link MappingPostprocessor} implementations.
 */
public abstract class AbstractMappingPostprocessor extends AbstractPlugin implements MappingPostprocessor {

    @Override
    public Class<? extends MappingPostprocessor>[] mustBeBefore() {
        return null;
    }

    @Override
    public Class<? extends MappingPostprocessor>[] mustBeAfter() {
        return null;
    }
}
