package liquibase.parser.mapping.core;

import liquibase.ContextExpression;
import liquibase.DatabaseExpression;
import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.mapping.ParsedNodeMapping;
import liquibase.util.StringUtil;

import java.lang.reflect.Type;

public class DatabaseExpressionNodeMapping implements ParsedNodeMapping {

    @Override
    public int getPriority(ParsedNode parsedNode, Class objectType, Type containerType, String containerAttribute, Scope scope) {
        if (DatabaseExpression.class.isAssignableFrom(objectType)) {
            return PRIORITY_SPECIALIZED;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public ParsedNode toParsedNode(Object objectToConvert, Class containerType, String containerAttribute, ParsedNode parentNode, Scope scope) throws ParseException {
        return null;
    }

    @Override
    public Object toObject(ParsedNode parsedNode, Class objectType, Class containerType, String containerAttribute, Scope scope) throws ParseException {
        String value = parsedNode.getValue(null, String.class);
        if (StringUtil.trimToNull(value) == null) {
            return null;
        }
        return new DatabaseExpression(value);
    }
}