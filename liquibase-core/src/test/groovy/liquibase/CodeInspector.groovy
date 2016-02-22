package liquibase

import liquibase.action.AbstractActionTest
import liquibase.actionlogic.ActionLogic
import liquibase.database.ConnectionSupplier
import liquibase.database.Database
import liquibase.diff.output.changelog.ActionGenerator
import liquibase.structure.TestObjectReferenceSupplier
import liquibase.structure.datatype.DataTypeLogic
import liquibase.util.TestUtil
import spock.lang.Specification
import spock.lang.Unroll

public class CodeInspector extends Specification {

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
        new TreeSet<>(listedTypes*.name) == new TreeSet((TestUtil.getClasses(objectType)*.name.findAll({!it.contains("MockActionLogic") && !it.contains("MockExternalInteractionLogic")}))) //want ordered alphabetically in the file

        where:
        objectType << [
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
