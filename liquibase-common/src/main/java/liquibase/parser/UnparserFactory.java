package liquibase.parser;

import liquibase.Scope;
import liquibase.exception.DependencyException;
import liquibase.exception.ParseException;
import liquibase.parser.mapping.ParsedNodeMappingFactory;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessorFactory;
import liquibase.plugin.AbstractPluginFactory;

import java.io.OutputStream;

/**
 * Factory for {@link Unparser} plugins.
 */
public class UnparserFactory extends AbstractPluginFactory<Unparser> {

    protected UnparserFactory(Scope factoryScope) {
        super(factoryScope);
    }

    @Override
    protected Class<Unparser> getPluginClass() {
        return Unparser.class;
    }

    @Override
    protected int getPriority(Unparser obj, Scope scope, Object... args) {
        return obj.getPriority((String) args[0], scope);
    }

    /**
     * Returns the {@link Unparser} to use for the given path.
     */
    public Unparser getUnparser(String path, Scope scope) {
        return getPlugin(scope, path);
    }

    /**
     * Converts the object to the given outputStream using the configured @link liquibase.parser.mapping.ParsedNodeMapping}, {@link ParsedNodeUnprocessor}(s) and {@link Unparser}.
     * The passed outputPath is used to determine the unparser to use, so it must be set, even if writing to stdout or somewhere else without a real path.
     *
     * Does nothing if object is null.
     * @throws ParseException
     */
    public void unparse(Object object, OutputStream outputStream, String outputPath, Scope scope) throws ParseException {
        if (object == null) {
            return;
        }

        if (outputPath == null) {
            throw new ParseException("No outputPath set", null);
        }
        if (outputStream == null) {
            throw new ParseException("No outputStream set", null);
        }
        try {
            ParsedNode rootNode = ParsedNode.createRootNode("rootPlaceHolder");
            ParsedNode parsedObject = scope.getSingleton(ParsedNodeMappingFactory.class).toParsedNode(object, null, null, rootNode, scope);

            for (ParsedNodeUnprocessor unprocessor : scope.getSingleton(ParsedNodeUnprocessorFactory.class).getUnprocessors()) {
                unprocessor.unprocess(parsedObject, scope);
            }

            Unparser unparser = getUnparser(outputPath, scope);
            if (unparser == null) {
                throw new ParseException("Could not find an unparser for "+outputPath, null);
            }
            unparser.unparse(parsedObject, outputStream, scope);
        } catch (DependencyException e) {
            throw new ParseException(e, null);
        }

    }
}
