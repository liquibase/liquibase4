package liquibase.util

import liquibase.action.core.AddColumnsAction
import liquibase.action.core.CreateTableAction
import liquibase.parser.preprocessor.core.changelog.ChangeLogPreprocessor
import liquibase.parser.preprocessor.core.changelog.ChangeSetPreprocessor
import liquibase.parser.unprocessor.core.changelog.ChangeLogUnprocessor
import liquibase.parser.unprocessor.core.changelog.ChangeSetUnprocessor
import liquibase.parser.unprocessor.core.item.ItemReferenceUnprocessor
import spock.lang.Specification

class DependencyUtilTest extends Specification {

    def "sort"() {
        when:
        def objects = [
                new ChangeSetPreprocessor(),
                new ChangeLogPreprocessor(),

        ]
        objects.add(new CreateTableAction().createPreprocessor())

        def sorted = DependencyUtil.sort(objects)

        then:
        sorted.size() == objects.size()
    }

    def "standard unparsers are in the correct order"() {
        when:
        def changeSetUnprocessor = new ChangeSetUnprocessor()
        def changeLogUnprocessor = new ChangeLogUnprocessor()
        def itemReferenceUnprocessor = new ItemReferenceUnprocessor()
        def addColumnsUnprocessor = new AddColumnsAction().createUnprocessor()

        def objects = [
                addColumnsUnprocessor,
                changeSetUnprocessor,
                changeLogUnprocessor,
                itemReferenceUnprocessor,
        ]
        def sorted = DependencyUtil.sort(objects)

        then:
        sorted.size() == objects.size()
        sorted.indexOf(changeSetUnprocessor) < sorted.indexOf(addColumnsUnprocessor)
        sorted.indexOf(changeLogUnprocessor) < sorted.indexOf(addColumnsUnprocessor)
        sorted.indexOf(itemReferenceUnprocessor) < sorted.indexOf(addColumnsUnprocessor)
    }
}
