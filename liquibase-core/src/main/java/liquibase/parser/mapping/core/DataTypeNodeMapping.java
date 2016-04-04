package liquibase.parser.mapping.core;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.datatype.DataType;
import liquibase.parser.ParsedNode;
import liquibase.parser.mapping.AbstractParsedNodeMapping;

import java.lang.reflect.Type;

/**
 * Converts string descriptions to DataTypes by using the {@link DataType#parse(String)} method.
 */
public class DataTypeNodeMapping extends AbstractParsedNodeMapping<DataType> {

    @Override
    public int getPriority(ParsedNode parsedNode, Class objectType, Type containerType, String containerAttribute, Scope scope) {
        if (DataType.class.isAssignableFrom(objectType)) {
            return PRIORITY_SPECIALIZED;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public DataType toObject(ParsedNode parsedNode, Class<DataType> objectType, Class containerType, String containerAttribute, Scope scope) throws ParseException {
        return DataType.parse((String) parsedNode.value);
    }
}
