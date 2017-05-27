package liquibase.parser.preprocessor;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.exception.DependencyException;
import liquibase.item.Item;
import liquibase.plugin.AbstractPluginFactory;
import liquibase.util.DependencyUtil;

import java.util.*;

/**
 * Factory for {@link ParsedNodePreprocessor} implementations
 */
public class ParsedNodePreprocessorFactory extends AbstractPluginFactory<ParsedNodePreprocessor> {

    protected ParsedNodePreprocessorFactory(Scope factoryScope) {
        super(factoryScope);
    }

    @Override
    protected Class<ParsedNodePreprocessor> getPluginClass() {
        return ParsedNodePreprocessor.class;
    }

    /**
     * Always returns zero because preprocessors are not prioritized, just ordered.
     */
    @Override
    protected int getPriority(ParsedNodePreprocessor obj, Scope scope, Object... args) {
        return 0;
    }

    /**
     * Return the list of {@link ParsedNodePreprocessor} implementations to run, in the correct sorted order.
     */
    public List<ParsedNodePreprocessor> getPreprocessors() throws DependencyException {
        return DependencyUtil.sort(findAllInstances());
    }

    @Override
    protected synchronized Collection<ParsedNodePreprocessor> findAllInstances() {
        List<ParsedNodePreprocessor> returnList = new ArrayList<>(super.findAllInstances());

        for (Action action: ServiceLoader.load(Action.class, getFactoryScope().getClassLoader(true))) {
            ParsedNodePreprocessor preprocessor = action.createPreprocessor();
            if (preprocessor != null) {
                returnList.add(preprocessor);
            }
        }

        for (Item item: ServiceLoader.load(Item.class, getFactoryScope().getClassLoader(true))) {
            ParsedNodePreprocessor preprocessor = item.createPreprocessor();
            if (preprocessor != null) {
                returnList.add(preprocessor);
            }
        }

        return returnList;
    }
}
