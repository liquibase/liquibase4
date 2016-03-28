package liquibase.parser.xml;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.plugin.AbstractPlugin;
import liquibase.resource.InputStreamList;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

/**
 * Base class for plugins that are able to find XML Entities locally. Primarily used for finding XSD schemas in the classpath.
 * Lookup objects with {@link XmlEntityResolverFactory}
 */
public abstract class XmlEntityResolver extends AbstractPlugin {

    public abstract int getPriority(String name, String publicId, String baseURI, String systemId, Scope scope);

    /**
     * Return an InputSource for the given entity description.
     * Default implementation uses {@link #getRootPath(String, String, String, String, Scope)} plus the filename in the passed systemId and looks that up in the scope's resourceAccessor.
     */
    public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId, Scope scope) throws ParseException {
        String xsdFile = systemId.replaceFirst(".*/(.*?)", "$1");
        String path = getRootPath(name, publicId, baseURI, systemId, scope)+"/"+xsdFile;

        try {
            InputStreamList inputStreams = scope.getResourceAccessor().openStreams(path);
            if (inputStreams == null || inputStreams.size() == 0) {
                LoggerFactory.getLogger(getClass()).debug("Could not find "+path+" in resourceAccessor");
                return null;
            }
            if (inputStreams.size() > 1) {
                LoggerFactory.getLogger(getClass()).debug("Found "+inputStreams.size()+" files matching "+path+" in resourceAccessor");
                inputStreams.close();
                return null;
            }
            return new InputSource(inputStreams.get(0));
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }

    protected abstract String getRootPath(String name, String publicId, String baseURI, String systemId, Scope scope);
}
