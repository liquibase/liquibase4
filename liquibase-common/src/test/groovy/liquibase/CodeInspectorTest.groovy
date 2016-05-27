package liquibase

import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.AbstractActionLogicTest
import liquibase.actionlogic.ActionLogic
import liquibase.database.ConnectionSupplier
import liquibase.database.Database
import liquibase.diff.output.changelog.ActionGenerator
import liquibase.item.Item
import liquibase.item.TestItemSupplier
import liquibase.item.datatype.DataTypeLogic
import liquibase.parser.Parser
import liquibase.parser.Unparser
import liquibase.parser.xml.XmlEntityResolver
import liquibase.parser.mapping.ParsedNodeMapping
import liquibase.util.TestUtil
import spock.lang.Specification
import spock.lang.Unroll

/**
 * General tests of code-structure and tests for violations of required patterns.
 */
public class CodeInspectorTest extends Specification {

    @Unroll("#featureName: #objectType.name")
    def "plugin classes are correctly configured"() {
        when:
        def it = ServiceLoader.load(objectType).iterator()
        def listedTypes = []
        while (it.hasNext()) {
            listedTypes.add(it.next().getClass())
        }

        then:
        assert getClass().getClassLoader().getResources("META-INF/services/" + objectType.name).hasMoreElements(): "No META-INF/services/$objectType.name file"

        TestUtil.getClasses(objectType) != null
        new TreeSet<>(listedTypes*.name) == new TreeSet((TestUtil.getClasses(objectType)*.name.findAll({
            !it.contains("MockActionLogic") && !it.contains("MockExternalInteractionLogic")  && !it.contains("MockAction")
        }))) //want ordered alphabetically in the file

        where:
        objectType << [
                Action,
                ActionGenerator,
                ActionLogic,
                ConnectionSupplier,
                Database,
                DataTypeLogic,
                Item,
                TestItemSupplier,
                XmlEntityResolver,
                ParsedNodeMapping,
                AbstractActionTest.TestDetails,
                Parser,
                Unparser,
        ]
    }

    @Unroll("featureName: #actionClass")
    def "action classes have tests"() {
        when:
        getClass().forName(actionClass + "Test")

        then:
        noExceptionThrown()

        where:
        actionClass << TestUtil.getClasses(Action)*.name.findAll { !it.contains("Mock") }
    }

    @Unroll("featureName: #logicClass")
    def "logic classes have tests"() {
        when:
        def testClass = getClass().forName(logicClass+"Test")

        then:
        noExceptionThrown()
        assert AbstractActionLogicTest.isAssignableFrom(testClass)

        where:
        logicClass << TestUtil.getClasses(ActionLogic)*.name.findAll { !it.contains("Mock")  && !it.contains("Snapshot")} //exclude SnapshotLogicTests because they don't correspond to a unique action
    }

}
