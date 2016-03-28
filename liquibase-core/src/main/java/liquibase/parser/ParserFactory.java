package liquibase.parser;

import liquibase.Scope;
import liquibase.plugin.AbstractPluginFactory;

/**
 * Fatory for {@link Parser} plugins.
 */
public class ParserFactory extends AbstractPluginFactory<Parser> {

    protected ParserFactory(Scope factoryScope) {
        super(factoryScope);
    }

    @Override
    protected Class<Parser> getPluginClass() {
        return Parser.class;
    }

    @Override
    protected int getPriority(Parser obj, Scope scope, Object... args) {
        return obj.getPriority((String) args[0], scope);
    }

    /**
     * Returns the {@link Parser} to use for the given path.
     */
    public Parser getParser(String path, Scope scope) {
        return getPlugin(scope, path);
    }
}
