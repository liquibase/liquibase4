package liquibase

import liquibase.action.AbstractActionTest
import liquibase.action.Action
import liquibase.actionlogic.ActionLogic
import liquibase.database.ConnectionSupplier
import liquibase.database.Database
import liquibase.diff.output.changelog.ActionGenerator
import liquibase.item.TestItemSupplier
import liquibase.item.datatype.DataTypeLogic
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
            !it.contains("MockActionLogic") && !it.contains("MockExternalInteractionLogic")
        }))) //want ordered alphabetically in the file

        where:
        objectType << [
                ActionLogic,
                ConnectionSupplier,
                Database,
                DataTypeLogic,
                ActionGenerator,
                TestItemSupplier,
                AbstractActionTest.TestDetails,
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
//
//    @Unroll("featureName: #logicClass")
//    def "logic classes have tests"() {
//        when:
//        def testClass = getClass().forName(logicClass+"Test")
//
//        then:
//        noExceptionThrown()
//        testClass.metaClass.getMetaMethod("validation failures are as expected") != null
//
//        where:
//        logicClass << TestUtil.getClasses(ActionLogic)*.name
//    }

}
