package liquibase.servicelocator

import liquibase.JUnitScope
import liquibase.action.AbstractActionTest
import liquibase.actionlogic.ActionLogic
import liquibase.database.ConnectionSupplier
import liquibase.database.Database
import liquibase.diff.output.changelog.ActionGenerator
import liquibase.structure.datatype.DataTypeLogic
import liquibase.structure.TestObjectReferenceSupplier
import liquibase.util.TestUtil
import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Modifier

public class ServiceLocatorTest extends Specification {

    def "findAllServices when there are valid services"() {
        expect:
        assert JUnitScope.instance.getSingleton(ServiceLocator).findAllServices(ActionLogic).size() > 1
    }

    def "findAllServices when there no valid services"() {
        expect:
        assert JUnitScope.instance.getSingleton(ServiceLocator).findAllServices(ServiceLocatorTest).size() == 0
    }

    @Unroll("#featureName: #serviceType.name")
    def "service classes are correctly configured"() {
        when:
        def it = ServiceLoader.load(serviceType).iterator()
        def listedTypes = []
        while (it.hasNext()) {
            listedTypes.add(it.next().getClass())
        }

        then:
        assert getClass().getClassLoader().getResources("META-INF/services/" + serviceType.name).hasMoreElements(): "No META-INF/services/$serviceType.name file"

        TestUtil.getClasses(serviceType) != null
        new TreeSet<>(listedTypes*.name) == new TreeSet((TestUtil.getClasses(serviceType)*.name.findAll({!it.contains("MockActionLogic") && !it.contains("MockExternalInteractionLogic")}))) //want ordered alphabetically in the file

        where:
        serviceType << [
                ActionLogic,
                ConnectionSupplier,
                Database,
                DataTypeLogic,
                ActionGenerator,
                TestObjectReferenceSupplier,
                AbstractActionTest.TestDetails,
        ]
    }
}
