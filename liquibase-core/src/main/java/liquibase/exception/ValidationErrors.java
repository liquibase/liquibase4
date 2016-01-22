package liquibase.exception;

import liquibase.ExtensibleObject;
import liquibase.util.StringUtils;

import java.util.*;

public class ValidationErrors {

    protected List<String> errorMessages = new ArrayList<String>();
    protected List<String> warningMessages = new ArrayList<String>();

    public boolean hasErrors() {
        return errorMessages.size() > 0;
    }

    public boolean hasError(String message) {
        return errorMessages.contains(message);
    }

    public boolean hasWarnings() {
        return warningMessages.size() > 0;
    }

    public ValidationErrors checkRequiredFields(ExtensibleObject object, String... requiredFields) {
        if (object == null || hasErrors() || requiredFields == null) {
            return this;
        }

        for (String requiredFieldName : requiredFields) {
            Object value = object.get(requiredFieldName, Object.class);

            String fieldPrefix = object.getClass().getSimpleName()+".";
            if (value == null) {
                return addError(fieldPrefix+requiredFieldName + " is required");
            } else if (value instanceof Collection) {
                if (((Collection) value).size() == 0) {
                    return addError(fieldPrefix + requiredFieldName + " is required");
                } else {
                    boolean foundValue = false;
                    for (Object obj : (Collection) value) {
                        if (obj != null) {
                            foundValue = true;
                            break;
                        }
                    }
                    if (!foundValue) {
                        return addError(fieldPrefix + requiredFieldName + " is required");
                    }
                }
            } else if (value instanceof Object[]) {
                if (((Object[]) value).length == 0) {
                    return addError(fieldPrefix+requiredFieldName + " is required");
                } else {
                    boolean foundValue = false;
                    for (Object obj : (Object[]) value) {
                        if (obj != null) {
                            foundValue = true;
                            break;
                        }
                    }
                    if (!foundValue) {
                        return addError(fieldPrefix + requiredFieldName + " is required");
                    }
                }
            }
        }

        return this;
    }

    public ValidationErrors checkRequiredFields(Collection<? extends ExtensibleObject> objectCollection, String... requiredFields) {
        if (!hasErrors() && objectCollection != null) {
            for (ExtensibleObject object : objectCollection) {
                if (object == null) {
                    continue;
                }
                checkRequiredFields(object, requiredFields);
            }
        }

        return this;
    }

    public ValidationErrors checkRequiredFields(ExtensibleObject object, Enum... requiredFields) {
        String[] fieldsNames = null;
        if (requiredFields != null) {
            fieldsNames = new String[requiredFields.length];
            for (int i=0; i<requiredFields.length; i++) {
                fieldsNames[i] = requiredFields[i].name();
            }
        }
        return checkRequiredFields(object, fieldsNames);
    }

    /**
     * If an error was added that the given field is required, remove the error.
     */
    public ValidationErrors removeRequiredField(String field) {
        ListIterator<String> it = errorMessages.listIterator();
        while (it.hasNext()) {
            String message = it.next();
            if (message.equals(field+" is required") || message.equals(field+" is empty")) {
                it.remove();
            }
        }
        return this;
    }

    public ValidationErrors removeRequiredField(Enum field) {
        return removeRequiredField(field.name());

    }

    public ValidationErrors addUnsupportedError(String message, ExtensibleObject object) {
        String className = "";
        if (object != null) {
            className = object.getClass().getSimpleName() + " does not support ";
        }

        addError(className + message);
        return this;
    }

    public ValidationErrors checkUnsupportedFields(ExtensibleObject object, String... unsupportedFields) {
        if (!hasErrors() && object != null) {
            String fieldPrefix = object.getClass().getSimpleName()+".";
            for (String field : unsupportedFields) {
                Object value = object.get(field, Object.class);
                if (value != null) {
                    if (value instanceof Collection) {
                        for (Object obj : (Collection) value) {
                            if (obj != null) {
                                addError(fieldPrefix+field + " is not supported");
                                return this;
                            }
                        }
                    } else {
                        addError(fieldPrefix+field + " is not supported");
                    }
                }
            }
        }
        return this;
    }

    public ValidationErrors checkUnsupportedFields(ExtensibleObject object, Enum... disallowedFields) {
        String[] names = new String[0];
        if (disallowedFields != null) {
            names = new String[disallowedFields.length];
            for (int i=0; i<disallowedFields.length;i++) {
                names[i] = disallowedFields[i].name();
            }
        }
        return checkUnsupportedFields(object, names);
    }

    /**
     * Adds the given error message if the check param is true.
     */
    public ValidationErrors addError(boolean check, String message) {
        if (check) {
            return addError(message);
        } else {
            return this;
        }
    }

    public ValidationErrors addError(String message) {
        errorMessages.add(message);
        return this;
    }

    public ValidationErrors addWarning(String message) {
        warningMessages.add(message);
        return this;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public List<String> getWarningMessages() {
        return warningMessages;
    }

    public ValidationErrors addAll(ValidationErrors validationErrors) {
        if (validationErrors == null) {
            return this;
        }
        this.errorMessages.addAll(validationErrors.getErrorMessages());
        this.warningMessages.addAll(validationErrors.getWarningMessages());
        return this;
    }

    @Override
    public String toString() {
        String string;
        if (!hasErrors()) {
            string = "No errors";
        } else {
            string = StringUtils.join(getErrorMessages(), "; ");
        }

        if (hasWarnings()) {
            string = StringUtils.join(getWarningMessages(), "; ", new StringUtils.StringUtilsFormatter() {
                @Override
                public String toString(Object obj) {
                    return "WARNING: " + obj;
                }
            });
        }

        return string;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public List<String> getRequiredErrorMessages() {
        List<String> requiredErrorMessages = new ArrayList<String>();
        for (String message : errorMessages) {
            if (message.contains("is required")) {
                requiredErrorMessages.add(message);
            }
        }
        return Collections.unmodifiableList(requiredErrorMessages);
    }

    public List<String> getUnsupportedErrorMessages() {
        List<String> unsupportedErrorMessages = new ArrayList<String>();
        for (String message : errorMessages) {
            if (message.contains(" is not allowed on ") || message.contains(" is not supported in ")) {
                unsupportedErrorMessages.add(message);
            }
        }
        return Collections.unmodifiableList(unsupportedErrorMessages);
    }

    public ValidationErrors removeUnsupportedField(Enum field) {
        return this;
    }

    public ValidationErrors removeUnsupportedField(String field) {
        return this;
    }

    public ValidationErrors check(String errorMessage, ErrorCheck check) {
        if (!hasErrors()) {
            if (!check.check()) {
                addError(errorMessage);
            }
        }
        return this;
    }

    public ValidationErrors checkField(ExtensibleObject object, String field, FieldCheck check) {
        if (object == null) {
            return this;
        }

        Object value = object.get(field, Object.class);
        if (value != null) {
            if (value instanceof Collection) {
                for (Object obj : (Collection) value) {
                    if (this.hasErrors()) {
                        break;
                    }
                    String errorMessage = check.check(obj);
                    if (errorMessage != null) {
                        this.addError(object.getClass().getSimpleName()+"."+field+" "+errorMessage);
                    }
                }
            } else {
                String errorMessage = check.check(value);
                if (errorMessage != null) {
                    this.addError(object.getClass().getSimpleName()+"."+field+" "+errorMessage);
                }
            }
        }
        return this;
    }

    public interface ErrorCheck {

        boolean check();
    }

    public interface FieldCheck<T> {
        String check(T obj);
    }
}
