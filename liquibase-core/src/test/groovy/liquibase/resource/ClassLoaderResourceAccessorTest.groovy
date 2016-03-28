package liquibase.resource

import spock.lang.Specification

class ClassLoaderResourceAccessorTest extends Specification {

    private ClassLoaderResourceAccessor createResourceAccessor(String subdir) {
        File thisClassFile = new File(new URI(this.getClass().getClassLoader().getResource("liquibase/Scope.class").toExternalForm()));

        def rootPathFile = thisClassFile.getParentFile().getParentFile().getAbsoluteFile()
        if (subdir != null) {
            rootPathFile = new File(rootPathFile, subdir);
        }


        def loader = new URLClassLoader([rootPathFile.toURL()].toArray(new URL[1]), (ClassLoader) null)

        return new ClassLoaderResourceAccessor(loader);
    }

    def "using custom classloader doesn't include main classloader"() {
        when:
        File thisClassFile = new File(new URI(this.getClass().getClassLoader().getResource("liquibase/Scope.class").toExternalForm()));

        def rootPathFile = thisClassFile.getParentFile().getParentFile().getAbsoluteFile()
        def loader = new ClassLoaderResourceAccessor(new URLClassLoader([rootPathFile.toURL()].toArray(new URL[1]), (ClassLoader) null))

        then:
        loader.list("liquibase/exception", null, true).join("\n") == """
liquibase/exception/ActionPerformException.class
liquibase/exception/DatabaseException.class
liquibase/exception/LiquibaseException.class
liquibase/exception/ParseException.class
liquibase/exception/UnexpectedLiquibaseException.class
""".trim()

        loader.list("/org/slf4j", null, true).join("\n") == ""
    }

    def "using app classloader finds classes in jars and directories"() {
        when:
        def loader = new ClassLoaderResourceAccessor(getClass().getClassLoader());

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
