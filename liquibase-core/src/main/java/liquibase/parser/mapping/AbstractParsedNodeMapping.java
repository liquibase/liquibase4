package liquibase.parser.mapping;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.util.ObjectUtil;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

/**
 * Convenience base class for {@link ParsedNodeMapping} implementations that map between ParsedNodes and {@link ExtensibleObject}s
 */
public abstract class AbstractParsedNodeMapping<ObjectType extends ExtensibleObject> implements ParsedNodeMapping<ObjectType> {

    /**
     * Default implementation uses {@link #getNodeName(ExtensibleObject, Class, String, Scope)} for the returned ParsedNode's name,
     * then copies all values from the original object to the parsed node.
     * If any attributes or objects in collections are {@link ExtensibleObject}s, this method will call {@link ParsedNodeMappingFactory#toParsedNode(Object, Class, String, ParsedNode, Scope)} on the value
     * before setting it in the returned parsed node.
     */
    @Override
    public ParsedNode toParsedNode(ObjectType objectToConvert, Class containerType, String containerAttribute, ParsedNode parentNode, Scope scope) throws ParseException {
        ParsedNode node;
        String nodeName = containerAttribute;
        if (nodeName == null) {
            nodeName = getNodeName(objectToConvert, containerType, containerAttribute, scope);
        }
        if (parentNode == null) {
            node = ParsedNode.createRootNode(nodeName);
        } else {
            node = parentNode.addChild(nodeName);
        }
        for (String attr : objectToConvert.getAttributeNames()) {
            Object childValue = objectToConvert.get(attr, Object.class);
            if (childValue instanceof Collection) {
                if (((Collection) childValue).size() == 0) {
                    continue;
                }

                ParsedNode collectionNode = node.addChild(attr);
                for (Object childObject : (Collection) childValue) {
                    if (childObject instanceof ExtensibleObject) {
                        scope.getSingleton(ParsedNodeMappingFactory.class).toParsedNode(childObject, objectToConvert.getClass(), null, collectionNode, scope);
                    } else {
                        collectionNode.addChild("value").setValue(childObject);
                    }
                }
            } else {
                if (childValue instanceof ExtensibleObject) {
                    childValue = scope.getSingleton(ParsedNodeMappingFactory.class).toParsedNode(childValue, objectToConvert.getClass(), attr, node, scope);
                }
                node.addChild(attr).setValue(childValue);
            }

        }
        return node;
    }

    /**
     * Default implementation creates a new instance using {@link #createObject(ParsedNode, Class, Class, String, Scope)},
     * then for each child in the parsed node, sets the same attribute value on the instance to return.
     * If any attributes are ParsedNodes or or collections of ParsedNodes,
     * it will call {@link ParsedNodeMappingFactory#toObject(ParsedNode, Class, Class, String, Scope)} before setting the value on the instance to return.
     */
    @Override
    public ObjectType toObject(ParsedNode parsedNode, Class<ObjectType> objectType, Class containerType, String containerAttribute, Scope scope) throws ParseException {
        ObjectType returnObject = createObject(parsedNode, objectType, containerType, containerAttribute, scope);

        for (ParsedNode child : parsedNode.getChildren()) {
            if (!returnObject.getStandardAttributeNames().contains(child.name)) {
                throw new ParseException("Unexpected attribute: " + returnObject.getClass() + "." + child.name);
            }
            Type attributeType = returnObject.getAttributeType(child.name);
            Class attributeClass;
            Class collectionElementClass = Object.class;
            if (attributeType instanceof ParameterizedType) {
                attributeClass = (Class) ((ParameterizedType) attributeType).getRawType();
                if (Collection.class.isAssignableFrom(attributeClass)) {
                    Type[] typeArgs = ((ParameterizedType) attributeType).getActualTypeArguments();
                    if (typeArgs != null && typeArgs.length > 0) {
                        collectionElementClass = (Class) typeArgs[0];
                    }
                }
            } else if (attributeType instanceof TypeVariable) {
                attributeClass = (Class) ((TypeVariable) attributeType).getBounds()[0];
            } else if (attributeType instanceof Class) {
                attributeClass = (Class) attributeType;
            } else {
                throw new ParseException("Unexpected attributeType: " + attributeType.getClass().getName() + " " + attributeType.toString());
            }

            if (Collection.class.isAssignableFrom(attributeClass)) {
                Collection collection = returnObject.get(child.name, Collection.class);
                collection.clear();

                if (child.getChildren().size() > 0) {
                    for (ParsedNode valueEntry : child.getChildren()) {
                        collection.add(scope.getSingleton(ParsedNodeMappingFactory.class).toObject((ParsedNode) valueEntry, collectionElementClass, returnObject.getClass(), child.name, scope));
                    }
                } else if (child.value instanceof ParsedNode) {
                    collection.add(scope.getSingleton(ParsedNodeMappingFactory.class).toObject((ParsedNode) child.value, collectionElementClass, returnObject.getClass(), child.name, scope));
                } else {
                    if (child.value != null) {
                        collection.add(ObjectUtil.convert(child.value, collectionElementClass));
                    }
                }
            } else {
                if (child.getChildren().size() == 0) {
                    if (child.value instanceof ParsedNode) {
                        returnObject.set(child.name, scope.getSingleton(ParsedNodeMappingFactory.class).toObject((ParsedNode) child.value, attributeClass, returnObject.getClass(), child.name, scope));
                    } else {
                        returnObject.set(child.name, scope.getSingleton(ParsedNodeMappingFactory.class).toObject(child, attributeClass, returnObject.getClass(), child.name, scope));
                    }
                } else {
                    returnObject.set(child.name, scope.getSingleton(ParsedNodeMappingFactory.class).toObject(child, attributeClass, returnObject.getClass(), child.name, scope));
                }
            }
        }

        return returnObject;
    }

    /**
     * Default implementation uses a first-letter-lower-cased version of the passed object's simpleName.
     * Returns "null" if object is null.
     */
    protected String getNodeName(ObjectType object, Class containerType, String containerAttribute, Scope scope) {
        if (object == null) {
            return "null";
        }
        String name = object.getClass().getSimpleName();
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return name;
    }

    /**
     * Default implementation creates an object of the passed objectType using the empty constructor.
     * If objectType is abstract or an interface, throws {@link ParseException}.
     */
    protected ObjectType createObject(ParsedNode parsedNode, Class<ObjectType> objectType, Class containerType, String containerAttribute, Scope scope) throws ParseException {
        try {
            int modifiers = objectType.getModifiers();
            if (Modifier.isInterface(modifiers) || Modifier.isAbstract(modifiers)) {
                throw new ParseException("Cannot create interface/abstract class " + objectType.getName() + ". There needs to be a custom ParsedNodeMapping for " + describeParams(parsedNode, objectType, containerType, containerAttribute));
            }
            return objectType.newInstance();
        } catch (InstantiationException | IllegalArgumentException | IllegalAccessException e) {
            throw new ParseException(e);
        }
    }

    protected String describeParams(ParsedNode parsedNode, Class<ObjectType> objectTypeClass, Class containerType, String containerAttribute) {
        String returnString = "";
        if (parsedNode == null) {
            returnString += "parsedNode: null";
        } else {
            returnString += "node.name: " + parsedNode.name;
        }

        returnString += ", objectType: " + (objectTypeClass == null ? "null" : objectTypeClass.getName());
        returnString += ", containerType: " + (containerType == null ? "null" : containerType.getName());
        returnString += ", containerAttribute: " + containerAttribute;

        return returnString;
    }
}
