package liquibase.parser.core.xml

import liquibase.JUnitScope
import spock.lang.Specification

class XmlEntityResolverFactoryTest extends Specification {

    def "correctly finds LiquibaseXmlEntityResolver"() {
        expect:
        JUnitScope.instance.getSingleton(XmlEntityResolverFactory).getResolver(null, null, null, "http://www.liquibase.org/xml/ns/test.xsd", JUnitScope.instance) instanceof LiquibaseXmlEntityResolver
    }
}
