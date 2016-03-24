package liquibase.parser.core.xml;

import liquibase.Scope;

/**
 * Finds liquibase XSD classes.
 */
public class LiquibaseXmlEntityResolver extends XmlEntityResolver {

    @Override
    public int getPriority(String name, String publicId, String baseURI, String systemId, Scope scope) {
        if (systemId.startsWith("http://www.liquibase.org/") || systemId.startsWith("http://liquibase.org/")) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    protected String getRootPath(String name, String publicId, String baseURI, String systemId, Scope scope) {
        return "liquibase/parser/core/xml";
    }
}
