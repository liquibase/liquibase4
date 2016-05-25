package liquibase.util;

import liquibase.ExtensibleObject;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.core.GenericDatabase;
import liquibase.item.DatabaseObject;
import liquibase.item.DatabaseObjectReference;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Various utility methods for working with strings.
 */
public class StringUtil {
    private static final Pattern upperCasePattern = Pattern.compile("[A-Z]");
    private static final Pattern lowerCasePattern = Pattern.compile("[a-z]");


    public static String trimToEmpty(String string) {
        if (string == null) {
            return "";
        }
        return string.trim();
    }

    public static String trimToNull(String string) {
        if (string == null) {
            return null;
        }
        String returnString = string.trim();
        if (returnString.length() == 0) {
            return null;
        } else {
            return returnString;
        }
    }

    public static String join(Object[] array, String delimiter, StringUtilFormatter formatter) {
        if (array == null) {
            return null;
        }
        return join(Arrays.asList(array), delimiter, formatter);
    }

    public static String join(String[] array, String delimiter) {
        return join(Arrays.asList(array), delimiter);
    }

    public static String join(Collection<String> collection, String delimiter) {
        return join(collection, delimiter, new ToStringFormatter());

    }

    public static String join(Collection collection, String delimiter, StringUtilFormatter formatter) {
        if (collection == null) {
            return null;
        }

        if (collection.size() == 0) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();
        for (Object val : collection) {
            buffer.append(formatter.toString(val)).append(delimiter);
        }

        String returnString = buffer.toString();
        return returnString.substring(0, returnString.length() - delimiter.length());
    }

    public static String join(Collection collection, String delimiter, StringUtilFormatter formatter, boolean sorted) {
        if (sorted) {
            TreeSet<String> sortedSet = new TreeSet<>();
            for (Object obj : collection) {
                sortedSet.add(formatter.toString(obj));
            }
            return join(sortedSet, delimiter);
        }
        return join(collection, delimiter, formatter);
    }

    public static String join(Collection<String> collection, String delimiter, boolean sorted) {
        if (sorted) {
            return join(new TreeSet<>(collection), delimiter);
        } else {
            return join(collection, delimiter);
        }
    }

    public static String join(Map map, String delimiter) {
        return join(map, delimiter, new ToStringFormatter());
    }

    public static String join(Map map, String delimiter, StringUtilFormatter formatter) {
        List<String> list = new ArrayList<>();
        for (Map.Entry entry : (Set<Map.Entry>) map.entrySet()) {
            list.add(entry.getKey().toString() + "=" + formatter.toString(entry.getValue()));
        }
        return join(list, delimiter);
    }

    public static String join(ExtensibleObject extensibleObject, String delimiter) {
        return join(extensibleObject, delimiter, new ToStringFormatter());
    }

    public static String join(ExtensibleObject extensibleObject, String delimiter, StringUtilFormatter formatter) {
        List<String> list = new ArrayList<>();
        for (String attribute : new TreeSet<>(extensibleObject.getAttributes())) {
            String formattedValue = formatter.toString(extensibleObject.get(attribute, Object.class));
            if (formattedValue != null) {
                list.add(attribute + "=" + formattedValue);
            }
        }
        return join(list, delimiter);
    }

    public static List<String> splitAndTrim(String string, String regex) {
        if (string == null) {
            return null;
        }
        List<String> returnList = new ArrayList<>();
        for (String part : string.split(regex)) {
            returnList.add(part.trim());
        }

        return returnList;
    }

    public static String repeat(String string, int times) {
        String returnString = "";
        for (int i = 0; i < times; i++) {
            returnString += string;
        }

        return returnString;
    }

    public static String join(Integer[] array, String delimiter) {
        if (array == null) {
            return null;
        }

        int[] ints = new int[array.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = array[i];
        }
        return StringUtil.join(ints, delimiter);
    }

    public static String join(int[] array, String delimiter) {
        if (array == null) {
            return null;
        }

        if (array.length == 0) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();
        for (int val : array) {
            buffer.append(val).append(delimiter);
        }

        String returnString = buffer.toString();
        return returnString.substring(0, returnString.length() - delimiter.length());
    }

    public static String indent(String string) {
        return indent(string, 4);
    }

    public static String indent(String string, int padding) {
        String pad = StringUtil.repeat(" ", padding);
        return pad + (string.replaceAll("\n", "\n" + pad));
    }

    public static String lowerCaseFirst(String string) {
        return string.substring(0, 1).toLowerCase() + string.substring(1);
    }

    public static String upperCaseFirst(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static boolean hasUpperCase(String string) {
        return upperCasePattern.matcher(string).find();
    }

    public static boolean hasLowerCase(String string) {
        return lowerCasePattern.matcher(string).find();
    }

    public static String standardizeLineEndings(String string) {
        if (string == null) {
            return null;
        }
        return string.replace("\r\n", "\n").replace("\r", "\n");
    }

    /**
     * Concatenates the addition string to the baseString string, adjusting the case of "addition" to match the base string.
     * If the string is all caps, append addition in all caps. If all lower case, append in all lower case. If baseString is mixed case, make no changes to addition.
     */
    public static String concatConsistentCase(String baseString, String addition) {
        boolean hasLowerCase = hasLowerCase(baseString);
        boolean hasUpperCase = hasUpperCase(baseString);
        if ((hasLowerCase && hasUpperCase) || (!hasLowerCase && !hasUpperCase)) { //mixed case || no letters
            return baseString + addition;
        } else if (hasLowerCase) {
            return baseString + addition.toLowerCase();
        } else {
            return baseString + addition.toUpperCase();
        }
    }


    public static String pad(String value, int length) {
        value = StringUtil.trimToEmpty(value);
        if (value.length() >= length) {
            return value;
        }

        return value + StringUtil.repeat(" ", length - value.length());
    }

    /**
     * Returns the original value, unless it is null or only whitespace. If so, it returns the defaultValue.
     */
    public static String defaultIfEmpty(String original, String defaultValue) {
        if (trimToNull(original) == null) {
            return defaultValue;
        } else {
            return original;
        }
    }

    public interface StringUtilFormatter<Type> {
        String toString(Type obj);
    }

    public static class ToStringFormatter implements StringUtilFormatter {
        @Override
        public String toString(Object obj) {
            if (obj == null) {
                return null;
            }
            return obj.toString();
        }
    }

    public static class DefaultFormatter implements StringUtilFormatter {
        @Override
        public String toString(Object obj) {
            if (obj == null) {
                return null;
            } else if (obj instanceof Class) {
                return ((Class) obj).getName();
            } else if (obj instanceof Object[]) {
                if (((Object[]) obj).length == 0) {
                    return null;
                } else {
                    return "[" + StringUtil.join((Object[]) obj, ", ", this) + "]";
                }
            } else if (obj instanceof Collection) {
                if (((Collection) obj).size() == 0) {
                    return null;
                } else {
                    return "[" + StringUtil.join((Collection) obj, ", ", this) + "]";
                }

            }
            return obj.toString();
        }
    }

    public static class DatabaseObjectNameFormatter implements StringUtilFormatter {

        private Database database;
        private Class<? extends DatabaseObject> objectType;
        private Scope scope;

        public DatabaseObjectNameFormatter(Class<? extends DatabaseObject> objectType, Scope scope) {
            this.objectType = objectType;
            this.database = scope.getDatabase();
            this.scope = scope;

            if (this.database == null) {
                this.database = new GenericDatabase();
            }
        }

        @Override
        public String toString(Object obj) {
            if (obj == null) {
                return null;
            } else if (obj instanceof DatabaseObjectReference) {
                return database.quoteObjectName((DatabaseObjectReference) obj, scope);
            } else {
                return database.quoteObjectName(obj.toString(), objectType, scope);
            }
        }
    }
}
