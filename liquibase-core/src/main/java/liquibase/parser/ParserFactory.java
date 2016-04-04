package liquibase.parser;

import liquibase.Scope;
import liquibase.exception.DependencyException;
import liquibase.exception.ParseException;
import liquibase.parser.mapping.ParsedNodeMappingFactory;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.ParsedNodePreprocessorFactory;
import liquibase.plugin.AbstractPluginFactory;

/**
 * Factory for {@link Parser} plugins.
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


    /**
     * Converts the file at sourcePath to the passed objectType using the configured {@link Parser}(s), {@link ParsedNodePreprocessor}(s) and {@link liquibase.parser.mapping.ParsedNodeMapping}.
     * <b>This is the primary method to use when parsing files into objects</b>
     */
    public <ObjectType> ObjectType parse(String sourcePath, Class<ObjectType> objectType, Scope scope) throws ParseException {
        Parser parser = scope.getSingleton(ParserFactory.class).getParser(sourcePath, scope);
        if (parser == null) {
            throw new ParseException("Cannot find parser for " + sourcePath);
        }

        ParsedNode rootNode = parser.parse(sourcePath, scope);

        try {
            for (ParsedNodePreprocessor preprocessor : scope.getSingleton(ParsedNodePreprocessorFactory.class).getPreprocessors()) {
                preprocessor.process(rootNode, scope);
            }
        } catch (DependencyException e) {
            throw new ParseException(e);
        }

        return scope.getSingleton(ParsedNodeMappingFactory.class).toObject(rootNode, objectType, null, null, scope);
    }

}
