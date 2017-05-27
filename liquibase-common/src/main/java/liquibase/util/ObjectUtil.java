package liquibase.util;

import liquibase.ExtensibleObject;
import liquibase.ObjectMetaData;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.AbstractRelationBasedObject;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import liquibase.item.core.*;
import liquibase.item.datatype.DataType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class ObjectUtil {

    /**
     * Converts the given object to the targetClass
     */
    public static <T> T convert(Object object, Class<T> targetClass) throws IllegalArgumentException {
        if (object == null) {
            return null;
        }
        if (targetClass.isAssignableFrom(object.getClass())) {
            return (T) object;
        }

        try {
            if (Enum.class.isAssignableFrom(targetClass)) {
                return (T) Enum.valueOf((Class<Enum>) targetClass, object.toString());
            } else if (Number.class.isAssignableFrom(targetClass)) {
                if (object instanceof Number) {
                    Number number = (Number) object;
                    String numberAsString = number.toString();
                    numberAsString = numberAsString.replaceFirst("\\.0+$", ""); //remove zero decimal so int/long/etc. can parse it correctly.

                    if (targetClass.equals(Byte.class)) {
                        long value = Long.valueOf(numberAsString);
                        if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
                            raiseOverflowException(number, targetClass);
                        }
                        return (T) (Byte) number.byteValue();
                    } else if (targetClass.equals(Short.class)) {
                        long value = Long.valueOf(numberAsString);
                        if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
                            raiseOverflowException(number, targetClass);
                        }
                        return (T) (Short) number.shortValue();
                    } else if (targetClass.equals(Integer.class)) {
                        long value = Long.valueOf(numberAsString);
                        if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                            raiseOverflowException(number, targetClass);
                        }
                        return (T) (Integer) number.intValue();
                    } else if (targetClass.equals(Long.class)) {
                        return (T) Long.valueOf(numberAsString);
                    } else if (targetClass.equals(Float.class)) {
                        return (T) (Float) number.floatValue();
                    } else if (targetClass.equals(Double.class)) {
                        return (T) (Double) number.doubleValue();
                    } else if (targetClass.equals(BigInteger.class)) {
                        return (T) new BigInteger(numberAsString);
                    } else if (targetClass.equals(BigDecimal.class)) {
                        // using BigDecimal(String) here, to avoid unpredictability of BigDecimal(double)
                        // (see BigDecimal javadoc for details)
                        return (T) new BigDecimal(numberAsString);
                    } else {
                        return raiseUnknownConversionException(object, targetClass);
                    }
                } else if (object instanceof String) {
                    String string = (String) object;
                    if (string.contains(".")) {
                        string = string.replaceFirst("\\.0+$", "");
                    }
                    if (string.equals("")) {
                        string = "0";
                    }
                    if (targetClass.equals(Byte.class)) {
                        return (T) Byte.decode(string);
                    } else if (targetClass.equals(Short.class)) {
                        return (T) Short.decode(string);
                    } else if (targetClass.equals(Integer.class)) {
                        return (T) Integer.decode(string);
                    } else if (targetClass.equals(Long.class)) {
                        return (T) Long.decode(string);
                    } else if (targetClass.equals(Float.class)) {
                        return (T) Float.valueOf(string);
                    } else if (targetClass.equals(Double.class)) {
                        return (T) Double.valueOf(string);
                    } else if (targetClass.equals(BigInteger.class)) {
                        return (T) new BigInteger(string);
                    } else if (targetClass.equals(BigDecimal.class)) {
                        return (T) new BigDecimal(string);
                    } else {
                        return raiseUnknownConversionException(object, targetClass);
                    }
                } else {
                    return raiseUnknownConversionException(object, targetClass);
                }
            } else if (targetClass.isAssignableFrom(Boolean.class)) {
                String lowerCase = object.toString().toLowerCase();
                return (T) (Boolean) (lowerCase.equals("true") || lowerCase.equals("t") || lowerCase.equals("1") || lowerCase.equals("1.0") || lowerCase.equals("yes"));
            } else if (targetClass.isAssignableFrom(String.class)) {
                return (T) object.toString();
            } else if (targetClass.isAssignableFrom(ItemReference.class)) {
                if (object instanceof String) {
                    try {
                        Item newInstance = (Item) targetClass.getConstructor().newInstance();
                        newInstance.set("name", object);
                        return (T) newInstance;
                    } catch (Exception e) {
                        throw new UnexpectedLiquibaseException(e);
                    }
                } else {
                    throw new UnexpectedLiquibaseException("Cannot convert "+ object.getClass()+" to "+ItemReference.class.getName());
                }
            } else if (targetClass.isAssignableFrom(List.class)) {
                if (object instanceof List) {
                    return (T) object;
                } else if (object instanceof Collection) {
                    return (T) new ArrayList((Collection) object);
                } else if (object instanceof Object[]) {
                    return (T) new ArrayList(Arrays.asList((Object[]) object));
                } else {
                    return (T) object;
                }
            } else if (targetClass.isAssignableFrom(StringClauses.class)) {
                return (T) new StringClauses().append(object.toString());
            } else if (targetClass.isAssignableFrom(Class.class)) {
                try {
                    return (T) Class.forName(object.toString());
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            return (T) object;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static <T> T raiseUnknownConversionException(Object object, Class<T> targetClass) {
        throw new IllegalArgumentException("Could not convert '" + object + "' of type " + object.getClass().getName() + " to unknown target class " + targetClass.getName());
    }

    private static void raiseOverflowException(Number number, Class targetClass) {
        throw new IllegalArgumentException("Could not convert '" + number + "' of type " + number.getClass().getName() + " to target class " + targetClass.getName() + ": overflow");
    }

    /**
     * Return the defaultValue if the passed value is null. Otherwise, return the original value.
     */
    public static <T> T defaultIfNull(T value, T defaultValue) {
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }


    public static void populate(ExtensibleObject extensibleObject) throws Exception {
        for (ObjectMetaData.Attribute attr : extensibleObject.getObjectMetaData().attributes) {
            Type type = attr.type;

            Class classType;
            Class collectionType = null;
            if (type instanceof Class) {
                classType = (Class) type;
            } else if (type instanceof TypeVariable) {
                classType = (Class) ((TypeVariable) type).getGenericDeclaration();
            } else {
                classType = (Class) ((ParameterizedType) type).getRawType();
                if (Collection.class.isAssignableFrom(classType)) {
                    collectionType = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
                }
            }

            Object value = getTestValue(classType, collectionType, attr);

            extensibleObject.set(attr.name, value);
        }

    }

    private static Object getTestValue(Class classType, Class collectionType, ObjectMetaData.Attribute attr) throws Exception {
        if (classType.equals(String.class)) {
            return "abc";
        } else if (classType.equals(StringClauses.class)) {
            return new StringClauses().append("x").append("y").append("z");
        } else if (classType.equals(Boolean.class) || classType.equals(boolean.class)) {
            return true;
        } else if (classType.equals(Integer.class)) {
            return 15;
        } else if (classType.equals(BigInteger.class)) {
            return new BigInteger("4221");
        } else if (classType.equals(Class.class)) {
            return String.class;
        } else if (classType.equals(Throwable.class)) {
            return new RuntimeException("test exception");
        } else if (Enum.class.isAssignableFrom(classType)) {
            return classType.getEnumConstants()[0];
        } else if (AbstractRelationBasedObject.RelationBasedObjectReference.class.isAssignableFrom(classType)) {
            Object value = classType.newInstance();
            ((AbstractRelationBasedObject.RelationBasedObjectReference) value).name = "object_name";
            ((AbstractRelationBasedObject.RelationBasedObjectReference) value).container = new RelationReference(Table.class, "table_name", new SchemaReference("schema_name", new CatalogReference("cat_name")));
            return value;
        } else if (RelationReference.class.isAssignableFrom(classType)) {
            Object value = classType.newInstance();
            ((RelationReference) value).name = "object_name";
            ((RelationReference) value).container = new SchemaReference("schema_name", new CatalogReference("cat_name"));
            return value;
        } else if (SequenceReference.class.isAssignableFrom(classType)) {
            return new SequenceReference("seq_name", new SchemaReference("schema_name", new CatalogReference("cat_name")));
        } else if (StoredDatabaseLogicReference.class.isAssignableFrom(classType)) {
            return new SequenceReference("proc_name", new SchemaReference("schema_name", new CatalogReference("cat_name")));
        } else if (SchemaReference.class.isAssignableFrom(classType)) {
            Object value = classType.newInstance();
            ((SchemaReference) value).name = "schema_name";
            ((SchemaReference) value).container = new CatalogReference("cat_name");
            return value;
        } else if (CatalogReference.class.isAssignableFrom(classType)) {
            Object value = classType.newInstance();
            ((CatalogReference) value).name = "object_name";
            return value;
        } else if (DatabaseObjectReference.class.equals(classType) || ItemReference.class.equals(classType)) {
            return new RelationReference(Table.class, "object_name", new SchemaReference("schema_name", new CatalogReference("cat_name")));
        } else if (ItemReference.class.isAssignableFrom(classType)) {
            throw new RuntimeException("Unexpected reference type: " + classType.getName());
        } else if (DataType.class.isAssignableFrom(classType)) {
            return new DataType(DataType.StandardType.INTEGER);
        } else if (ExtensibleObject.class.isAssignableFrom(classType)) {
            ExtensibleObject value = (ExtensibleObject) classType.newInstance();
            populate(value);
            return value;
        } else if (List.class.isAssignableFrom(classType)) {
            List value = new ArrayList<>();
            value.add(getTestValue(collectionType, null, attr));
            value.add(getTestValue(collectionType, null, attr));
            return value;
        } else if (Set.class.isAssignableFrom(classType)) {
            Set value = new HashSet();
            value.add(getTestValue(collectionType, null, attr));
            value.add(getTestValue(collectionType, null, attr));
            return value;
        } else if (Object.class.equals(classType)) {
            return "ANY OBJECT VALUE";
        } else {
            throw new RuntimeException("Unknown type "+classType+" when setting action value");
        }
    }


}
