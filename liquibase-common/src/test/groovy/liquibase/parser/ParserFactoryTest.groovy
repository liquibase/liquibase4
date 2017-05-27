package liquibase.parser

import liquibase.ExtensibleObject
import liquibase.JUnitScope
import liquibase.ObjectMetaData
import liquibase.Scope
import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.action.ActionFactory
import liquibase.action.QuerySqlAction
import liquibase.action.UpdateSqlAction
import liquibase.changelog.ChangeLog
import liquibase.changelog.ChangeSet
import liquibase.database.core.MockDatabase
import liquibase.exception.ParseException
import liquibase.item.AbstractRelationBasedObject
import liquibase.item.ItemReference
import liquibase.item.core.CatalogReference
import liquibase.item.core.RelationReference
import liquibase.item.core.SchemaReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.lockservice.ChangeLogLock
import liquibase.parser.mapping.ParsedNodeMappingFactory
import liquibase.parser.xml.TestXmlGenerator
import liquibase.parser.xml.XmlParserTest
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.resource.CompositeResourceAccessor
import liquibase.resource.JUnitResourceAccessor
import liquibase.resource.MockResourceAccessor
import liquibase.util.CollectionUtil
import liquibase.util.ObjectUtil
import liquibase.util.StreamUtil
import liquibase.util.StringClauses
import liquibase.util.StringUtil
import liquibase.util.TestUtil
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll
import testmd.TestMD
import testmd.util.StringUtils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.TypeVariable

class ParserFactoryTest extends Specification {

    static actionsNotInChangelog = [QuerySqlAction.class, UpdateSqlAction.class]

    MockResourceAccessor resourceAccessor;
    Scope scope;

    def setup() {
        resourceAccessor = new MockResourceAccessor()
                .addMockXsd("changeLog")
        scope = JUnitScope.instance.child(Scope.Attr.resourceAccessor, new CompositeResourceAccessor(resourceAccessor, new ClassLoaderResourceAccessor(getClass().getClassLoader())));
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


    @Unroll("#featureName: #xml")
    def "generated 3.5-style changeSet xml is parsed correctly"() {
        when:
        def scope = JUnitScope.getInstance()
        def path = "com/example/test.xml"
        def parserFactory = scope.getSingleton(ParserFactory)

        def changeLogXml = """
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.5.xsd">

    <changeSet id="1" author="tester">
        $xml
    </changeSet>

</databaseChangeLog>
""".trim()

        scope = scope.child(Scope.Attr.resourceAccessor, new MockResourceAccessor()
                .addData(path, changeLogXml)
                .addData("liquibase/parser/core/xml/dbchangelog-3.5.xsd", StreamUtil.readStreamAsString(getClass().getResourceAsStream("/liquibase/parser/core/xml/dbchangelog-3.5.xsd"), null, scope))
        )

        def object = parserFactory.parse(path, ChangeLog, scope)

        def actionName = xml.replaceFirst(/ .*/, "")
                .replaceFirst(/>.*/, "")
                .replaceFirst("<", "");

        then:
        TestMD.test(getClass().getName(), "generated 3.5 changeSet xml is parsed correctly (does not actually test anything, but tracks the output so we can watch for unexpected changes)", getClass())
                .withPermutation([action_asTable: actionName])
                .addOperation("output", object.describe())
                .run({ true })

        where:
        xml << new TestXmlGenerator().generateXml("liquibase/parser/core/xml/dbchangelog-3.5.xsd", "//group[@name=\"changeSetChildren\"]/choice/element")

    }

    def "invalid attributes throw an exception"() {
        when:
        def scope = JUnitScope.getInstance()
        def parserFactory = scope.getSingleton(ParserFactory)

        def node = ParsedNode.createRootNode("changeLog")
        node.addChildren([
                changeSet: [
                        id         : "1",
                        author     : "test",
                        invalidAttr: "should throw exception"
                ]
        ])
        parserFactory.parse(node, ChangeLog, scope)

        then:
        def e = thrown(ParseException)
        e.message == "Unexpected attribute 'invalidAttr' for liquibase.changelog.ChangeSet. Possible attributes: [actions, alwaysRun, author, comment, contexts, created, dbms, failOnError, id, labels, logicalPath, runInTransaction, runOnChange]"

    }

    def "invalid attributes on objects that just take a value throw an exception"() {
        when:
        def scope = JUnitScope.getInstance()
        def parserFactory = scope.getSingleton(ParserFactory)

        def node = ParsedNode.createRootNode("changeLog")
        node.addChildren([
                changeSet: [
                        id            : "1",
                        author        : "test",
                        executeCommand: [
                                arg: [
                                        value      : "this",
                                        invalidAttr: "should throw exception"
                                ]

                        ]
                ]
        ])
        parserFactory.parse(node, ChangeLog, scope)

        then:
        def e = thrown(ParseException)
        e.message == "Unexpected attribute(s) 'invalidAttr' for java.lang.String"

    }

    def "can build changelog from simple 3.x-style yaml"() {
        when:
        resourceAccessor.addData("com/example/path.yaml", """
databaseChangeLog:
    - changeSet:
        id: 1
        author: bob
        changes:
            - addColumn:
                tableName: test_table
                columns:
                    column:
                        name: test_col
                        type: int
""".trim())

        then:
        scope.getSingleton(ParserFactory).parse("com/example/path.yaml", ChangeLog, scope).describe() == "ChangeLog{changeLogEntries=[ChangeSet{actions=[addColumns(columns=[Column{name=test_col, relation=test_table, type=int}])], author=bob, id=1}], physicalPath=com/example/path.yaml}"
    }

    @Unroll("#featureName: #action.name as #format")
    def "Empty Objects unparse to native format and re-parse back to the same object without error"() {
        when:
        def changeLog = new ChangeLog()
        def changeSet = new ChangeSet("1", "test", null)
        changeSet.addAction(action)
        changeLog.addEntry(changeSet)

        def out = new ByteArrayOutputStream();
        scope.getSingleton(UnparserFactory).unparse(changeLog, out, "out.$format", scope)


        then:
        StringUtils.trimToNull(out.toString()) != null

        when:
        scope = scope.withMockResource("out.$format", out.toString())

        scope = scope.child(Scope.Attr.resourceAccessor, new CompositeResourceAccessor(new ClassLoaderResourceAccessor(getClass().getClassLoader()), scope.resourceAccessor))

        def parsed = scope.getSingleton(ParserFactory).parse("out.$format", ChangeLog, scope)
        then:

        parsed.changeSets.size() == 1
        parsed.changeSets.get(0).actions.size() == 1
        formatObject(parsed.changeSets.get(0).actions[0])== formatObject(action)

        where:
        [format, action] << CollectionUtil.permutations([
                format: ["xml", "yaml", "json"],
                action: JUnitScope.instance.getSingleton(ActionFactory).findAllInstances().findAll({
                    return actionsNotInChangelog.contains(it.getClass())
                })
        ], false).collect { return [it.get("format"), it.get("action")] }
    }

    @Unroll("#featureName: #action.name as #format")
    def "Objects unparse to native format and re-parse back to the same object"() {
        when:
        action = action.clone()
        action.setReadOnlyObject(false)
        ObjectUtil.populate(action)

        def changeLog = new ChangeLog()
        changeLog.physicalPath = "out.$format"
        def changeSet = new ChangeSet("1", "test", null)
        changeSet.addAction(action)
        changeLog.addEntry(changeSet)

        def out = new ByteArrayOutputStream();
        scope.getSingleton(UnparserFactory).unparse(changeLog, out, "out.$format", scope)


        def expectedFileName = "/liquibase/parser/expected/ParserFactoryTest.unparsed.$action.name.$format"
        def expectedUnparsed = getClass().getResourceAsStream(expectedFileName)

        if (expectedUnparsed == null) {
            expectedUnparsed = new ByteArrayInputStream("Missing expected value file liquibase-common/src/test/resources$expectedFileName".getBytes())
        }

        then:
        StringUtil.standardizeLineEndings(out.toString().trim()) == StringUtil.standardizeLineEndings(StreamUtil.readStreamAsString(expectedUnparsed, null, scope).trim())

        when:
        scope = scope.withMockResource("out.$format", out.toString())

        scope = scope.child(Scope.Attr.resourceAccessor, new CompositeResourceAccessor(new ClassLoaderResourceAccessor(getClass().getClassLoader()), scope.resourceAccessor))
        def parsedFile = scope.getSingleton(ParserFactory).parse("out.$format", ChangeLog, scope)

        then:
        formatObject(parsedFile) == formatObject(changeLog)

        where:
        [format, action] << CollectionUtil.permutations([
                format: ["xml", "yaml", "json"],
                action: JUnitScope.instance.getSingleton(ActionFactory).findAllInstances().findAll({
                    return !actionsNotInChangelog.contains(it.getClass())
                })
        ], false).collect { return [it.get("format"), it.get("action")] }
    }

    def formatObject(ExtensibleObject object) {
        def node = scope.getSingleton(ParsedNodeMappingFactory.class).toParsedNode(object, null, null, null, scope)
        for (def typeNode : node.getChildren("type", true)) {
            if (typeNode.getValue("", String).contains(".")) {
                typeNode.remove()
            }
        }
        return node.prettyPrint()

    }
}
