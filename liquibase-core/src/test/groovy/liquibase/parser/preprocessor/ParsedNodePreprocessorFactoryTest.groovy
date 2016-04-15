package liquibase.parser.preprocessor

import liquibase.JUnitScope
import liquibase.parser.preprocessor.core.changelog.ChangeLogPreprocessor
import liquibase.parser.preprocessor.core.changelog.ChangeSetPreprocessor

import spock.lang.Specification

class ParsedNodePreprocessorFactoryTest extends Specification {

    def "finds and sorts preprocessors correctly"() {
        when:
        def processors = JUnitScope.instance.getSingleton(ParsedNodePreprocessorFactory).getPreprocessors()

        then:
        processors.size() > 0
        assert processors.contains(new ChangeLogPreprocessor())
        assert processors.indexOf(new ChangeLogPreprocessor()) < processors.indexOf(new ChangeSetPreprocessor())
        assert !processors.contains(null)
    }
}
