package liquibase

import liquibase.util.TestUtil
import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Modifier

class SingletonServiceTest extends Specification {

    @Unroll("#featureName: #service")
    def "implementations of singleton service should have non-public constructors"() {
        def constructors = service.getDeclaredConstructors()
        expect:
        if (service.getName().contains("Mock")) {
            return;
        }

        assert constructors.size() > 0: "No non-default constructors"
        for (def constructor : constructors) {
            assert !Modifier.isPublic(constructor.getModifiers()): "Constructor " + constructor.toString() + " is public"
        }

        where:
        service << TestUtil.getClasses(SingletonService)
    }


    @Unroll("#featureName: #service")
    def "implementations of singleton service have a Scope-only or empty constructor"() {
        when:
        if (service.getName().contains("Mock")) {
            return;
        }
        try {
            service.getDeclaredConstructor()
        } catch (NoSuchMethodException) {
            service.getDeclaredConstructor(Scope)
        }

        then:
        noExceptionThrown()

        where:
        service << TestUtil.getClasses(SingletonService)
    }
}
