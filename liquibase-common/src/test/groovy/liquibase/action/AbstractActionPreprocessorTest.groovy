package liquibase.action;

import liquibase.JUnitScope;
import liquibase.parser.ParsedNode;
import liquibase.util.StringUtil;
import spock.lang.Specification;

import java.util.Map;

/**
 * Base class for testing {@link liquibase.parser.preprocessor.core.changelog.AbstractActionPreprocessor} implementations.
 * The actual classes are usually nested in the {@link Action} class, but since the tests tend to be long it's best to separate them out into
 * their own test classes.
 */
public abstract class AbstractActionPreprocessorTest extends Specification {

    def createAndProcessChangelog(String actionName = null, Action action, Map children) {
        def changeLog = ParsedNode.createRootNode("changeLog")
        def changeSet = changeLog.addChild("changeSet")

        if (actionName == null) {
            actionName = action.getName();
        }
        def actionNode = changeSet.addChild(actionName)
        actionNode.addChildren(children)


        action.createPreprocessor().process(changeLog, JUnitScope.instance)

        return changeLog

    }

}
