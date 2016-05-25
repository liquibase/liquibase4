package liquibase.parser.unprocessor;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.exception.DependencyException;
import liquibase.item.Item;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;
import liquibase.plugin.AbstractPluginFactory;
import liquibase.util.DependencyUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Factory for {@link ParsedNodeUnprocessor} implementations
 */
public class ParsedNodeUnprocessorFactory extends AbstractPluginFactory<ParsedNodeUnprocessor> {

    protected ParsedNodeUnprocessorFactory(Scope factoryScope) {
        super(factoryScope);
    }

    @Override
    protected Class<ParsedNodeUnprocessor> getPluginClass() {
        return ParsedNodeUnprocessor.class;
    }

    /**
     * Always returns zero because unprocessors are not prioritized, just ordered.
     */
    @Override
    protected int getPriority(ParsedNodeUnprocessor obj, Scope scope, Object... args) {
        return 0;
    }

    /**
     * Return the list of {@link ParsedNodeUnprocessor} implementations to run, in the correct sorted order.
     */
    public List<ParsedNodeUnprocessor> getUnprocessors() throws DependencyException {
        return DependencyUtil.sort(findAllInstances());
    }

    @Override
    protected synchronized Collection<ParsedNodeUnprocessor> findAllInstances() {
        List<ParsedNodeUnprocessor> returnList = new ArrayList<>(super.findAllInstances());

        for (Action action: ServiceLoader.load(Action.class, getFactoryScope().getClassLoader(true))) {
            ParsedNodeUnprocessor unprocessor = action.createUnprocessor();
            if (unprocessor != null) {
                returnList.add(unprocessor);
            }
        }

        for (Item item: ServiceLoader.load(Item.class, getFactoryScope().getClassLoader(true))) {
            ParsedNodeUnprocessor unprocessor = item.createUnprocessor();
            if (unprocessor != null) {
                returnList.add(unprocessor);
            }
        }

        return returnList;
    }
}
