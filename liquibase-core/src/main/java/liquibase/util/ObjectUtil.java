package liquibase.util;

import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.Item;
import liquibase.item.ItemReference;

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
            if (Number.class.isAssignableFrom(targetClass)) {
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
}
