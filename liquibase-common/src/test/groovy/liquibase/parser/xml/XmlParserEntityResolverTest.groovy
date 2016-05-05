package liquibase.parser.xml

import liquibase.JUnitScope
import liquibase.parser.xml.XmlParser
import spock.lang.Specification
import spock.lang.Unroll

class XmlParserEntityResolverTest extends Specification {

    @Unroll("#featureName #url")
    def "can find xsd in resourceAccessor using LiquibaseXsdResolver"() {
        when:
        def scope = JUnitScope.instance.withMockResource("liquibase/parser/core/xml/test.xsd", "<xsd:schema/>")

        then:
        new XmlParser.XmlParserEntityResolver(scope).resolveEntity(null, null, null, url) != null

        where:
        url << ["http://liquibase.org/xml/ns/test.xsd", "http://www.liquibase.org/xml/ns/test.xsd"]
    }

    def "returns null if no xsd in resourceAccessor"() {
        when:
        def scope = JUnitScope.instance

        then:
        new XmlParser.XmlParserEntityResolver(scope).resolveEntity(null, null, null, url) == null

        where:
        url << ["http://www.liquibase.org/xml/ns/test.xsd", "http://www.example.com/xml/ns/test.xsd"]
    }
}
