package liquibase;

import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.CollectionUtil;
import liquibase.util.ObjectUtil;
import liquibase.util.SmartMap;
import liquibase.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Convenience class implementing ExtensibleObject. It is usually easiest to extend this class rather than implement all of ExtensibleObject yourself.
 */
public class AbstractExtensibleObject implements ExtensibleObject, Cloneable {

    private SmartMap attributes = new SmartMap();
    private Set<String> standardAttributeNames;

    private static Map<Class, Map<String, Field>> attributeFieldCache = new HashMap<>();

    public AbstractExtensibleObject() {
    }

    public AbstractExtensibleObject(Map<String, ?> values) {
        attributes.putAll(values);
    }

    @Override
    public Set<String> getAttributeNames() {
        HashSet<String> returnSet = new HashSet<>(attributes.keySet());
        for (String field : getAttributeFields().keySet()) {
            if (has(field)) {
                returnSet.add(field);
            }
        }
        return Collections.unmodifiableSet(returnSet);
    }

    /**
     * Default implementation looks for an inner enum called "Attr" and returns the fields in there
     */
    @Override
    public Set<String> getStandardAttributeNames() {
        return getAttributeFields().keySet();
    }

    /**
     * Return true if the given key is defined.
     */
    public boolean has(String key) {
        return get(key, Object.class) != null;
    }

    /**
     * Return true if the given key is defined.
     */
    public boolean has(Enum key) {
        return has(key.name());
    }

    @Override
    public <T> T get(String attribute, Class<T> type) {
        return get(attribute, null, type);
    }

    @Override
    public <T> T get(String attribute, T defaultValue) {
        Class<T> type = (Class<T>) Object.class;
        if (defaultValue != null) {
            type = (Class<T>) defaultValue.getClass();
        }
        return get(attribute, defaultValue, type);
    }

    private Map<String, Field> getAttributeFields() {
        Map<String, Field> fields = attributeFieldCache.get(this.getClass());
        if (fields == null) {
            fields = new HashMap<>();
            for (Field field : this.getClass().getFields()) {
                int modifiers = field.getModifiers();
                if (Modifier.isPublic(modifiers) && !field.isSynthetic() && !Modifier.isFinal(modifiers) && !Modifier.isStatic(modifiers)) {
                    fields.put(field.getName(), field);
                }
            }
            attributeFieldCache.put(this.getClass(), fields);
        }
        return fields;
    }

    private <T> T get(String attribute, T defaultValue, Class<T> type) {
        Object value;
        if (attribute.contains(".")) {
            List path = getPathOfValues(attribute, type);
            value = path.get(path.size() - 1);
        } else {
            value = getFieldValue(attribute, type);
        }

        if (value == null) {
            return defaultValue;
        } else {
            return (T) value;
        }
    }

    public List getPathOfValues(String attribute, Class lastType) {
        List path = new ArrayList();

        String baseField;
        String remainingAttribute = null;
        int separatorIndex = attribute.indexOf('.');
        if (separatorIndex < 0) {
            baseField = attribute;
        } else {
            baseField = attribute.substring(0, separatorIndex);
            remainingAttribute = attribute.substring(separatorIndex + 1);
        }

        Object lastValue = this;

        while (baseField != null) {
            boolean isLastField = remainingAttribute == null;

            Object newValue;
            Class typeToGet = isLastField ? lastType : Object.class;

            if (lastValue == null) {
                newValue = null;
            } else if (lastValue instanceof ExtensibleObject) {
                newValue = ((ExtensibleObject) lastValue).get(baseField, typeToGet);
            } else if (lastValue instanceof Collection) {
                newValue = new ArrayList();
                boolean foundNonNullValue = false;
                for (Object object : (Collection) lastValue) {
                    if (object == null) {
                        ((Collection) newValue).add(null);
                    } else if (object instanceof ExtensibleObject) {
                        ((Collection) newValue).add(((ExtensibleObject) object).get(baseField, typeToGet));
                        foundNonNullValue = true;
                    } else {
                        throw new UnexpectedLiquibaseException("Cannot traverse field(s) " + baseField + " on a " + object.getClass().getName());
                    }
                }
                if (!foundNonNullValue) {
                    newValue = null;
                }
            } else {
                throw new UnexpectedLiquibaseException("Cannot traverse field(s) " + baseField + " on a " + lastValue.getClass().getName());
            }

            if (newValue instanceof Collection) {
                List flattenedCollection = new ArrayList();
                for (Object obj : (Collection) newValue) {
                    if (obj instanceof Collection) {
                        flattenedCollection.addAll((Collection) obj);
                    } else {
                        flattenedCollection.add(obj);
                    }
                }
                newValue = flattenedCollection;
            }

            path.add(newValue);
            lastValue = newValue;


            if (remainingAttribute == null) {
                baseField = null;
            } else {
                separatorIndex = remainingAttribute.indexOf('.');
                if (separatorIndex < 0) {
                    baseField = remainingAttribute;
                    remainingAttribute = null;
                } else {
                    baseField = remainingAttribute.substring(0, separatorIndex);
                    remainingAttribute = remainingAttribute.substring(separatorIndex + 1);
                }
            }
        }


        return path;
    }

    protected Object getFieldValue(String attribute, Class type) {
        Object value;

        Field field = getAttributeFields().get(attribute);
        if (field == null) {
            value = attributes.get(attribute, type);
        } else {
            try {
                value = ObjectUtil.convert(field.get(this), type);
            } catch (IllegalAccessException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        return value;
    }

    @Override
    public <T> T get(Enum attribute, Class<T> type) {
        return get(attribute.name(), type);
    }

    @Override
    public <T> T get(Enum attribute, T defaultValue) {
        return get(attribute.name(), defaultValue);
    }

    @Override
    public ExtensibleObject set(Enum attribute, Object value) {
        return this.set(attribute.name(), value);
    }

    @Override
    public ExtensibleObject set(String attribute, Object value) {
        Field field = getAttributeFields().get(attribute);
        if (field == null) {
            attributes.set(attribute, value);
        } else {
            try {
                field.set(this, ObjectUtil.convert(value, field.getType()));
            } catch (Exception e) {
                throw new UnexpectedLiquibaseException("Error setting " + getClass().getName() + "." + attribute, e);
            }
        }

        return this;
    }

    @Override
    public ExtensibleObject add(String attribute, Object value) {
        Object existingValue = get(attribute, Object.class);
        if (existingValue == null) {
            existingValue = new ArrayList<>();
            set(attribute, existingValue);
        } else if (!(existingValue instanceof Collection)) {
            List newCollection = new ArrayList();
            newCollection.add(existingValue);
            set(attribute, newCollection);
            existingValue = newCollection;
        }

        ((Collection) existingValue).add(value);

        return this;
    }

    @Override
    public ExtensibleObject add(Enum attribute, Object value) {
        return add(attribute.name(), value);
    }

    public String describe() {
        String name = getClass().getSimpleName();
        return name + "{" + StringUtils.join(this, ", ", new StringUtils.DefaultFormatter()) + "}";
    }

    @Override
    public String toString() {
        return describe();
    }

    @Override
    public Object clone() {
        try {
            AbstractExtensibleObject clone = (AbstractExtensibleObject) super.clone();
            for (String attr : getAttributeNames()) {
                clone.set(attr, this.get(attr, Object.class));
            }

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }
}
