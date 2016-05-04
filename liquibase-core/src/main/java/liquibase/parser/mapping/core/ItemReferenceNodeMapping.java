package liquibase.parser.mapping.core;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.*;
import liquibase.item.core.*;
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
        if ((ItemReference.class.equals(objectType) || DatabaseObjectReference.class.equals(objectType)) && containerType != null) { //only handle case when the objectType may not match the containerType
            return PRIORITY_SPECIALIZED;
        } else {
            return PRIORITY_NOT_APPLICABLE;
        }
    }

    @Override
    protected ItemReference createObject(ParsedNode parsedNode, Class<ItemReference> objectType, Class containerType, String containerAttribute, Scope scope) throws ParseException {
        String type = parsedNode.getChildValue("type", String.class, false);
        if (type != null) {
            if (Table.class.getName().equals(type) || View.class.getName().equals(type)) {
                return new RelationReference();
            } else if (Schema.class.getName().equals(type)) {
                return new SchemaReference();
            } else if (Catalog.class.getName().equals(type)) {
                return new CatalogReference();
            } else if (Column.class.getName().equals(type)) {
                return new ColumnReference();
            }
        }

        if (containerAttribute.equals("container")) {
            if (containerType == null) {
                return new ItemReference();
            } else {
                if (AbstractRelationBasedObject.RelationBasedObjectReference.class.isAssignableFrom(containerType)) {
                    return new RelationReference();
                } else if (AbstractSchemaBasedObject.SchemaBasedObjectReference.class.isAssignableFrom(containerType)) {
                    return new SchemaReference();
                } else if (AbstractCatalogBasedObject.CatalogBasedObjectReference.class.isAssignableFrom(containerType)) {
                    return new CatalogReference();
                }
            }
        }

        if (parsedNode.getName().equals("schema")) {
            return new SchemaReference();
        } else if (parsedNode.getName().equals("catalog")) {
            return new CatalogReference();
        }
        return super.createObject(parsedNode, objectType, containerType, containerAttribute, scope);
    }
}
