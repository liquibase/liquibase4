package liquibase.parser

import liquibase.JUnitScope
import liquibase.changelog.ChangeLog
import liquibase.changelog.ChangeSet
import spock.lang.Specification
import spock.lang.Unroll

class UnparserFactoryTest extends Specification {

    @Unroll
    def "unparse object to xml"() {
        expect:
        def scope = JUnitScope.instance
        def outStream = new ByteArrayOutputStream();
        scope.getSingleton(UnparserFactory).unparse(object, outStream, "com/example/out.xml", scope)

        new String(outStream.toByteArray()).replace("\r", "") == expected.replace("\r", "").trim()

        where:
        [object, expected] << [
                [new ChangeLog(), """
<?xml version="1.1" encoding="utf-8"?>
<changeLog xmlns="http://www.liquibase.org/xml/ns/changelog" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.liquibase.org/xml/ns/changelog http://www.liquibase.org/xml/ns/changelog/changelog-4.0.xsd"/>
"""],

                //-------
                [new ChangeLog().each { it.changeLogEntries.add(new ChangeSet("1", "test user", "path/to/logical.xml"))}, """
<?xml version="1.1" encoding="utf-8"?>
<changeLog xmlns="http://www.liquibase.org/xml/ns/changelog" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.liquibase.org/xml/ns/changelog http://www.liquibase.org/xml/ns/changelog/changelog-4.0.xsd">
    <changeSet author="test user" id="1"/>
</changeLog>
"""],

        ]
    }
}
