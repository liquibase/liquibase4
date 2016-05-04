package liquibase.parser.xml;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.plugin.AbstractPlugin;
import liquibase.resource.InputStreamList;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import java.io.InputStream;

/**
 * Base class for plugins that are able to find XML Entities locally rather than over the network. Primarily used for finding XSD schemas in the classpath.
 * Lookup objects with {@link XmlEntityResolverFactory}
 */
public abstract class XmlEntityResolver extends AbstractPlugin {

    public abstract int getPriority(String name, String publicId, String baseURI, String systemId, Scope scope);

    /**
     * Return an InputSource for the given entity description.
     * Default implementation uses {@link #getRootPath(String, String, String, String, Scope)} plus the filename in the passed systemId and looks that up in the scope's resourceAccessor.
     *
     * @return null if cannot be resolved
     */
    public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId, Scope scope) throws ParseException {
        String xsdFile = systemId.replaceFirst(".*/(.*?)", "$1");
        String path = getRootPath(name, publicId, baseURI, systemId, scope)+"/"+xsdFile;

        try {
            InputStream stream = scope.getResourceAccessor().openStream(path);

            if (stream == null) {
                return null;
            }

            return new InputSource(stream);
        } catch (Exception e) {
            throw new ParseException(e, null);
        }
    }

    protected abstract String getRootPath(String name, String publicId, String baseURI, String systemId, Scope scope);
}
