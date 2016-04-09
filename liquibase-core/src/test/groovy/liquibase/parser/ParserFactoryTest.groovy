package liquibase.parser

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.changelog.ChangeLog
import liquibase.parser.xml.XmlParserTest
import liquibase.resource.MockResourceAccessor
import spock.lang.Specification

class ParserFactoryTest extends Specification {

    MockResourceAccessor resourceAccessor;
    Scope scope;

    def setup() {
        resourceAccessor = new MockResourceAccessor()
                .addMockXsd("changeLog")
        scope = JUnitScope.instance.child(Scope.Attr.resourceAccessor, resourceAccessor)
    }


    def "can build object"() {
        when:
        resourceAccessor.addData("com/example/path.xml", """
<changeLog $XmlParserTest.MOCK_XSD_HEADER logicalPath="com/example/logical.xml">
</changeLog>
""")

        then:
        scope.getSingleton(ParserFactory).parse("com/example/path.xml", ChangeLog, scope).describe() == "ChangeLog{logicalPath=com/example/logical.xml, physicalPath=com/example/path.xml}"
    }

    def "can build changelog from empty 3.x-style xml"() {
        when:
        resourceAccessor.addData("com/example/path.xml", """
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.5.xsd">
</databaseChangeLog>
""")

        then:
        scope.getSingleton(ParserFactory).parse("com/example/path.xml", ChangeLog, scope).describe() == "ChangeLog{physicalPath=com/example/path.xml}"
    }


    def "can build changelog from simple 3.x-style xml"() {
        when:
        resourceAccessor.addData("com/example/path.xml", """
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.5.xsd">
    <changeSet author="bob" id="1">
        <addColumn tableName="test_table">
            <column name="test_col" type="int"/>
        </addColumn>
    </changeSet>
</databaseChangeLog>
""".trim())

        then:
        scope.getSingleton(ParserFactory).parse("com/example/path.xml", ChangeLog, scope).describe() == "ChangeLog{changeLogEntries=[ChangeSet{actions=[addColumns(columns=[Column{name=test_col, relation=test_table, type=int}])], author=bob, id=1, runInTransaction=true}], physicalPath=com/example/path.xml}"
    }
}
