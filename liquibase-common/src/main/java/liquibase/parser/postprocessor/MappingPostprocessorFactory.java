package liquibase.parser.postprocessor;

import liquibase.Scope;
import liquibase.exception.DependencyException;
import liquibase.plugin.AbstractPluginFactory;
import liquibase.util.DependencyUtil;

import java.util.List;

/**
 * Factory for {@link MappingPostprocessor} implementations
 */
public class MappingPostprocessorFactory extends AbstractPluginFactory<MappingPostprocessor> {

    protected MappingPostprocessorFactory(Scope factoryScope) {
        super(factoryScope);
    }

    @Override
    protected Class<MappingPostprocessor> getPluginClass() {
        return MappingPostprocessor.class;
    }

    /**
     * Always returns zero because postprocessors are not prioritized, just ordered.
     */
    @Override
    protected int getPriority(MappingPostprocessor obj, Scope scope, Object... args) {
        return 0;
    }

    /**
     * Return the list of {@link MappingPostprocessor} implementations to run, in the correct sorted order.
     */
    public List<MappingPostprocessor> getPostprocessors() throws DependencyException {
        return DependencyUtil.sort(findAllInstances());
    }
}
