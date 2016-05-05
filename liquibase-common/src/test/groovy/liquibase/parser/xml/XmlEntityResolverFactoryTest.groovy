package liquibase.parser.xml

import liquibase.JUnitScope
import liquibase.parser.xml.LiquibaseXmlEntityResolver
import liquibase.parser.xml.XmlEntityResolverFactory
import spock.lang.Specification

class XmlEntityResolverFactoryTest extends Specification {

    def "correctly finds LiquibaseXmlEntityResolver"() {
        expect:
        JUnitScope.instance.getSingleton(XmlEntityResolverFactory).getResolver(null, null, null, "http://www.liquibase.org/xml/ns/test.xsd", JUnitScope.instance) instanceof LiquibaseXmlEntityResolver
    }
}
