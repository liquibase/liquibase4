package liquibase.parser.preprocessor.core.changelog;

import liquibase.Scope;
import liquibase.action.ActionFactory;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.AbstractParsedNodePreprocessor;

import java.util.SortedSet;

/**
 * Preprocessing that is applied to all action nodes.
 */
public class StandardActionPreprocessor extends AbstractParsedNodePreprocessor {

    @Override
    public void process(ParsedNode node, Scope scope) throws ParseException {
    }
}
