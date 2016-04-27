package liquibase.util

import liquibase.action.core.CreateTableAction
import liquibase.parser.preprocessor.core.changelog.ChangeLogPreprocessor
import liquibase.parser.preprocessor.core.changelog.ChangeSetPreprocessor

import spock.lang.Specification

class DependencyUtilTest extends Specification {

    def "sort"() {
        when:
        def objects = [
                new ChangeSetPreprocessor(),
                new ChangeLogPreprocessor(),
                new CreateTableAction().createPreprocessors()
        ]
        def sorted = DependencyUtil.sort(objects)

        then:
        sorted.size() == objects.size()
    }
}
