package liquibase.servicelocator

import liquibase.JUnitScope
import liquibase.action.AbstractActionTest
import liquibase.actionlogic.ActionLogic
import liquibase.database.ConnectionSupplier
import liquibase.database.Database
import liquibase.diff.output.changelog.ActionGenerator
import liquibase.structure.TestStructureSupplier
import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Modifier

import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.that

public class ServiceLocatorTest extends Specification {

    def static Map<Class, Set<Class>> allClasses

    def setup() {
        if (allClasses == null) {
            allClasses = [:]
            findAllClasses("liquibase")
        }

    }

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

        allClasses.get(serviceType) != null
        new TreeSet<>(listedTypes*.name) == new TreeSet((allClasses.get(serviceType)*.name)) //want ordered alphabetically in the file

        where:
        serviceType << [
                ActionLogic,
                ConnectionSupplier,
                Database,
                ActionGenerator,
                TestStructureSupplier,
                AbstractActionTest.TestDetails,
        ]
    }

    def findAllClasses(String dirName) {
        def resources = getClass().getClassLoader().getResources(dirName)
        while (resources.hasMoreElements()) {
            def url = resources.nextElement()
            def relativeName = url.toExternalForm().replaceFirst(".*/$dirName", dirName)
            def file = new File(url.toURI())
            if (file.isDirectory()) {
                for (File sub : file.listFiles()) {
                    if (sub.isDirectory()) {
                        findAllClasses(relativeName + "/" + sub.name)
                    } else if (sub.name.endsWith(".class")) {
                        if (sub.name.contains('$_$') || sub.name.find(/_closure\d+/)) { // a groovy closure
                            continue;
                        }
                        if (sub.name.contains("Abstract")) {
                            continue;
                        }
                        Class clazz = Class.forName("$relativeName/$sub.name".replace("/", ".").replaceFirst(/\.class$/, ""));
                        Class superClass = clazz.superclass;
                        Set<Class> interfaces = [] as Set
                        addInterfaces(clazz, interfaces)


                        while (superClass != null && !superClass.equals(Object.class)) {
                            def classList = allClasses.get(superClass)
                            if (classList == null) {
                                classList = [] as Set;
                                allClasses.put(superClass, classList)
                            }
                            if (isValidService(clazz)) {
                                classList.add(clazz);
                            }
                            interfaces.addAll(superClass.interfaces)
                            superClass = superClass.superclass
                        }

                        for (def iface : interfaces) {
                            def classList = allClasses.get(iface)
                            if (classList == null) {
                                classList = [] as Set;
                                allClasses.put(iface, classList)
                            }
                            if (isValidService(clazz)) {
                                classList.add(clazz);
                            }
                        }
                    }
                }
            }
        }
    }

    protected addInterfaces(Class<?> clazz, Set<Class> interfaces) {
        def thisInterfaces = clazz.interfaces
        if (thisInterfaces.size() > 0) {
            interfaces.addAll(thisInterfaces)
            for (Class iface : thisInterfaces) {
                addInterfaces(iface, interfaces)
            }
        }

    }

    protected isValidService(Class<?> clazz) {
        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers()) || clazz.isSynthetic() || clazz.isAnonymousClass()) {
            return false;
        }
        try {
            clazz.getConstructor()
        } catch (NoSuchMethodException e) {
            return false;
        }

        return true;
    }
}
