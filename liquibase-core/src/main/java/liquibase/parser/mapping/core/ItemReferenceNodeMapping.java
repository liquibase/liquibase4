package liquibase.parser.mapping.core;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.AbstractCatalogBasedObject;
import liquibase.item.AbstractRelationBasedObject;
import liquibase.item.AbstractSchemaBasedObject;
import liquibase.item.ItemReference;
import liquibase.item.core.RelationReference;
import liquibase.item.core.SchemaReference;
import liquibase.parser.ParsedNode;
import liquibase.parser.mapping.AbstractParsedNodeMapping;
import liquibase.parser.mapping.ParsedNodeMapping;

import java.lang.reflect.Type;

/**
 * {@link ParsedNodeMapping} for {@link ItemReference} objects.
*/
public class ItemReferenceNodeMapping extends AbstractParsedNodeMapping<ItemReference> {

    @Override
    public int getPriority(ParsedNode parsedNode, Class objectType, Type containerType, String containerAttribute, Scope scope) {
        if (ItemReference.class.equals(objectType) && containerType != null) { //only handle case when the objectType may not match the containerType
            return PRIORITY_SPECIALIZED;
        } else {
            return PRIORITY_NOT_APPLICABLE;
        }
    }

    @Override
    protected ItemReference createObject(ParsedNode parsedNode, Class<ItemReference> objectType, Class containerType, String containerAttribute, Scope scope) throws ParseException {
        if (containerAttribute.equals("container")) {
            if (containerType == null) {
                return new ItemReference();
            } else {
                if (AbstractRelationBasedObject.RelationBasedObjectReference.class.isAssignableFrom(containerType)) {
                    return new RelationReference();
                } else if (AbstractSchemaBasedObject.SchemaBasedObjectReference.class.isAssignableFrom(containerType)) {
                    return new SchemaReference();
                } else if (AbstractCatalogBasedObject.CatalogBasedObjectReference.class.isAssignableFrom(containerType)) {
                    return new SchemaReference();
                }
            }
        }
        return super.createObject(parsedNode, objectType, containerType, containerAttribute, scope);
    }
}
