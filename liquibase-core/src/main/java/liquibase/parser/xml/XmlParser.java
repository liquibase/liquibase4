package liquibase.parser.xml;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.AbstractParser;
import liquibase.parser.ParsedNode;
import liquibase.resource.InputStreamList;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;
import org.xml.sax.ext.EntityResolver2;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Standard parser for XML files. Expects files to have a .xml extension.
 * Find entities using {@link XmlEntityResolver} before looking for them on the network.
 */
public class XmlParser extends AbstractParser {

    private SAXParserFactory saxParserFactory;

    public XmlParser() {
        saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setValidating(true);
        saxParserFactory.setNamespaceAware(true);
    }

    @Override
    public int getPriority(String path, Scope scope) {
        if (path.toLowerCase().endsWith(".xml") && scope.getResourceAccessor() != null) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public ParsedNode parse(String path, Scope scope) throws ParseException {
        try {
            SAXParser parser = saxParserFactory.newSAXParser();
            try {
                parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
            } catch (SAXNotRecognizedException | SAXNotSupportedException e) {
                //ok, parser must not support it
            }

            XMLReader xmlReader = parser.getXMLReader();
            XmlParserEntityResolver resolver = new XmlParserEntityResolver(scope);
            xmlReader.setEntityResolver(resolver);


            try (InputStreamList inputStreams = scope.getResourceAccessor().openStreams(path)) {
                if (inputStreams == null || inputStreams.size() == 0) {
                    throw new ParseException("Could not find any files that match " + path);
                } else if (inputStreams.size() > 1) {
                    throw new ParseException("Found " + inputStreams.size() + " files that match " + path);
                }

                XmlParserSaxHandler handler = new XmlParserSaxHandler();
                xmlReader.setContentHandler(handler);

                xmlReader.setErrorHandler(new ErrorHandler() {
                    @Override
                    public void warning(SAXParseException exception) throws SAXException {
                        LoggerFactory.getLogger(getClass()).warn(exception.getMessage());
                        throw exception;
                    }

                    @Override
                    public void error(SAXParseException exception) throws SAXException {
                        LoggerFactory.getLogger(getClass()).error(exception.getMessage());
                        throw exception;
                    }

                    @Override
                    public void fatalError(SAXParseException exception) throws SAXException {
                        LoggerFactory.getLogger(getClass()).error(exception.getMessage());
                        throw exception;
                    }
                });

                xmlReader.parse(new InputSource(inputStreams.get(0)));

                ParsedNode rootNode = handler.getRootNode();

                rootNode.addChild("physicalPath").setValue(path);
                return rootNode;
            }
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }

    /**
     * {@link EntityResolver2} that finds local copies of XSD files before falling back to the network.
     */
    protected static class XmlParserEntityResolver implements EntityResolver2 {

        private Scope scope;

        public XmlParserEntityResolver(Scope scope) {
            this.scope = scope;
        }

        @Override
        public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId) throws SAXException, IOException {
            Logger log = LoggerFactory.getLogger(getClass());
            log.debug("Resolving XML entity name='" + name + "', publicId='" + publicId + "', baseURI='" + baseURI + "', systemId='" + systemId + "'");

            if (systemId == null) {
                log.debug("Unable to resolve XML entity locally. Will load from network.");
                return null;
            }

            XmlEntityResolver resolver = scope.getSingleton(XmlEntityResolverFactory.class).getResolver(name, publicId, baseURI, systemId, scope);
            if (resolver == null) {
                log.debug("No resolver configured for "+systemId+". Will load from network");
                return null;
            }

            InputSource resolved;
            try {
                resolved = resolver.resolveEntity(name, publicId, baseURI, systemId, scope);
            } catch (ParseException e) {
                throw new SAXException(e);
            }

            if (resolved == null) {
                log.debug("Unable to resolve XML entity locally. Will load from network.");
            }
            return resolved;
        }

        @Override
        public InputSource getExternalSubset(String name, String baseURI) throws SAXException, IOException {
            return null;
        }

        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            return resolveEntity(null, publicId, null, systemId);
        }

    }

    protected static class XmlParserSaxHandler extends DefaultHandler {

        private Deque<ParsedNode> nodeQueue = new ArrayDeque<>();
        private StringBuilder text = null;
        private ParsedNode rootNode;

        public XmlParserSaxHandler() {
        }

        public ParsedNode getRootNode() {
            return rootNode;
        }

        @Override
        public void characters(char ch[], int start, int length) throws SAXException {
            text.append(new String(ch, start, length));
        }

        @Override
        public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException {
            ParsedNode parentNode = null;
            if (!nodeQueue.isEmpty()) {
                parentNode = nodeQueue.peek();
            }

            ParsedNode node;
            if (parentNode == null) {
                node = ParsedNode.createRootNode(localName);
            } else {
                node = parentNode.addChild(localName);
            }
            if (attributes != null) {
                for (int i = 0; i < attributes.getLength(); i++) {
                    node.addChild(attributes.getLocalName(i)).setValue(attributes.getValue(i));
                }
            }

            nodeQueue.push(node);

            text = new StringBuilder();
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            ParsedNode node = nodeQueue.pop();

            String seenText = this.text.toString();
            if (StringUtils.trimToNull(seenText) != null) {
                node.setValue(seenText.trim());
            }
            text = new StringBuilder();

            if (nodeQueue.isEmpty()) {
                this.rootNode = node;
            }
        }
    }
}
