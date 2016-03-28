package liquibase.resource

import org.eclipse.osgi.launch.EquinoxFactory
import spock.lang.Specification

class OsgiResourceAccessorTest extends Specification {

    private createResourceAccessor() {

        def framework = EquinoxFactory.newInstance().newFramework([
                "osgi.clean"             : "true",
                "osgi.configuration.area": "tmp/osgi/config",
                "osgi.install.area"      : "tmp/osgi/install"
        ])

        framework.start()
        def context = framework.getBundleContext()

        return new OsgiResourceAccessor(context.getBundles())
    }

    def "using app classloader finds classes in jars and directories"() {
        when:
        def loader = createResourceAccessor();

        then:
        loader.list("liquibase/exception", null, true).join("\n") == """
liquibase/exception/ActionPerformException.class
liquibase/exception/DatabaseException.class
liquibase/exception/LiquibaseException.class
liquibase/exception/ParseException.class
liquibase/exception/UnexpectedLiquibaseException.class
liquibase/exception/ValidationErrorsTest.class
""".trim()

        loader.list("org/slf4j", null, false).join("\n") == """org/slf4j/ILoggerFactory.class
org/slf4j/IMarkerFactory.class
org/slf4j/Logger.class
org/slf4j/LoggerFactory.class
org/slf4j/MDC\$1.class
org/slf4j/MDC\$MDCCloseable.class
org/slf4j/MDC.class
org/slf4j/Marker.class
org/slf4j/MarkerFactory.class
""".trim()
    }

}
