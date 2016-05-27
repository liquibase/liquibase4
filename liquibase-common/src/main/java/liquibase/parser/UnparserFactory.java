package liquibase.parser;

import liquibase.Scope;
import liquibase.exception.DependencyException;
import liquibase.exception.ParseException;
import liquibase.parser.mapping.ParsedNodeMappingFactory;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessorFactory;
import liquibase.plugin.AbstractPluginFactory;
import liquibase.util.StringUtil;
import org.slf4j.LoggerFactory;

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
     * <p/>
     * Does nothing if object is null.
     *
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
            ParsedNode parsedObject = scope.getSingleton(ParsedNodeMappingFactory.class).toParsedNode(object, null, null, null, scope);

            for (ParsedNodeUnprocessor unprocessor : scope.getSingleton(ParsedNodeUnprocessorFactory.class).getUnprocessors()) {
                unprocessor.unprocess(parsedObject, scope);
            }

            Unparser unparser = getUnparser(outputPath, scope);
            try {
                if (unparser == null) {
                    throw new ParseException("Could not find an unparser for " + outputPath, null);
                }
                unparser.unparse(parsedObject, outputStream, scope);
            } catch (ParseException e) {
                String message = e.getMessage();
                ParsedNode problemNode = e.getProblemNode();

                String parseErrorMessage = "Error unparsing ";

                if (problemNode != null && problemNode.getOriginalName() != null) {
                    parseErrorMessage += "\"" + problemNode.getOriginalName() + "\" in ";
                }

                if (problemNode == null || problemNode.fileName == null) {
                    parseErrorMessage += outputPath;
                } else {
                    parseErrorMessage += problemNode.fileName;
                }

                if (problemNode != null && problemNode.lineNumber != null) {
                    parseErrorMessage += " line " + problemNode.lineNumber;
                    if (problemNode.columnNumber != null) {
                        parseErrorMessage = parseErrorMessage + ", column" + problemNode.columnNumber;
                    }
                }

                String near = null;
                if (problemNode != null) {
                    near = unparser.describeOriginal(problemNode);
                }

                if (near == null) {
                    message = parseErrorMessage + " " + message;
                } else {
                    message = parseErrorMessage + "\n" + StringUtil.indent(near + "\n\n" + message);
                }


                if (problemNode != null) {
                    LoggerFactory.getLogger(getClass()).debug("Error parsing:\n" + StringUtil.indent(problemNode.prettyPrint()));
                }

                throw new ParseException(message, e, problemNode);
            }
        } catch (DependencyException e) {
            throw new ParseException(e, null);
        }

    }
}
