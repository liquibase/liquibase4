package liquibase.parser;

import liquibase.Scope;
import liquibase.exception.DependencyException;
import liquibase.exception.ParseException;
import liquibase.parser.mapping.ParsedNodeMappingFactory;
import liquibase.parser.postprocessor.MappingPostprocessor;
import liquibase.parser.postprocessor.MappingPostprocessorFactory;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.ParsedNodePreprocessorFactory;
import liquibase.plugin.AbstractPluginFactory;
import liquibase.util.LogUtil;
import liquibase.util.StringUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

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
     * Converts the file at sourcePath to the passed objectType using the configured {@link Parser}(s), {@link ParsedNodePreprocessor}(s), {@link liquibase.parser.mapping.ParsedNodeMapping} and {@link liquibase.parser.postprocessor.MappingPostprocessor}(s).
     * <b>This is the primary method to use when parsing files into objects.</b>
     * If an exception is thrown, a more descriptive message will be constructed in the resulting {@link ParseException}
     */
    public <ObjectType> ObjectType parse(String sourcePath, Class<ObjectType> objectType, Scope scope) throws ParseException {
        Parser parser = scope.getSingleton(ParserFactory.class).getParser(sourcePath, scope);
        if (parser == null) {
            throw new ParseException("Cannot find parser for " + sourcePath, null);
        }
        try {

            ParsedNode rootNode = parser.parse(sourcePath, scope);

            return parse(rootNode, objectType, scope);
        } catch (ParseException e) {
            String message = e.getMessage();
            ParsedNode problemNode = e.getProblemNode();

            String parseErrorMessage = "Error parsing ";

            if (problemNode != null && problemNode.getOriginalName() != null) {
                parseErrorMessage += "\"" + problemNode.getOriginalName() + "\" in ";
            }

            if (problemNode == null || problemNode.fileName == null) {
                parseErrorMessage += sourcePath;
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
                near = parser.describeOriginal(problemNode);
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

    }

    public <ObjectType> ObjectType parse(ParsedNode parsedNode, Class<ObjectType> objectType, Scope scope) throws ParseException {
        try {
            for (ParsedNodePreprocessor preprocessor : scope.getSingleton(ParsedNodePreprocessorFactory.class).getPreprocessors()) {
                MDC.put(LogUtil.MDC_PREPROCESSOR, preprocessor.getClass().getName());
                try {
                    preprocessor.process(parsedNode, scope);
                } finally {
                    MDC.remove(LogUtil.MDC_PREPROCESSOR);
                }
            }

            ObjectType returnObject = scope.getSingleton(ParsedNodeMappingFactory.class).toObject(parsedNode, objectType, null, null, scope);

            for (MappingPostprocessor postprocessor : scope.getSingleton(MappingPostprocessorFactory.class).getPostprocessors()) {
                postprocessor.process(returnObject, scope);
            }

            return returnObject;
        } catch (DependencyException e) {
            throw new ParseException(e, null);
        }
    }

}
