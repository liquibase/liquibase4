package liquibase;

import liquibase.util.StringUtil;

import java.util.*;

/**
 * Contains errors and warnings from validation logic. Used by both {@link liquibase.actionlogic.ActionLogic} and {@link liquibase.command.LiquibaseCommand}.
 */
public class ValidationErrors {

    private ExtensibleObject objectToValidate;

    protected List<String> errorMessages = new ArrayList<String>();
    protected List<String> warningMessages = new ArrayList<String>();

    public ValidationErrors(ExtensibleObject objectToValidate) {
        this.objectToValidate = objectToValidate;
    }

    public boolean hasErrors() {
        return errorMessages.size() > 0;
    }

    public boolean hasError(String message) {
        return errorMessages.contains(message);
    }

    public boolean hasWarnings() {
        return warningMessages.size() > 0;
    }

    public ValidationErrors checkRequiredFields(String... requiredFields) {
        if (objectToValidate == null || hasErrors() || requiredFields == null) {
            return this;
        }

        for (String requiredFieldName : requiredFields) {
            List path = objectToValidate.getValuePath(requiredFieldName, Object.class);

            boolean parentNull = false;
            for (int i=0; i<path.size()-1; i++) {
                if (!isSet(path.get(i))) {
                    parentNull = true;
                    break;
                }
            }

            if (path.size() == 1 || !parentNull) {
                Object value = path.get(path.size() - 1);

                if (value instanceof Object[]) {
                    value = Arrays.asList(value);
                }

                boolean fieldCorrect;
                if (value == null) {
                    fieldCorrect = false;
                } else if (value instanceof Collection) {
                    if (((Collection) value).size() == 0) {
                        fieldCorrect = false;
                    } else {
                        boolean nullValueFound = false;
                        for (Object obj : (Collection) value) {
                            if (obj == null) {
                                nullValueFound = true;
                                break;
                            }
                        }
                        fieldCorrect = !nullValueFound;
                    }
                } else {
                    fieldCorrect = true;
                }

                if (!fieldCorrect) {
                    return addError(requiredFieldName + " is required");

                }
            }
        }

        return this;
    }

    protected boolean isSet(Object value) {
        if (value == null) {
            return false;
        }

        if (value instanceof Object[]) {
            value = Arrays.asList(value);
        }


        if (value instanceof Collection) {
            for (Object obj : (Collection) value) {
                if (obj != null) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    protected boolean hasParentValues(ExtensibleObject objectToValidate, String requiredFieldName) {
        if (requiredFieldName.contains(".")) {
            String[] parentFields = requiredFieldName.split("\\.");
            String currentPath = null;
            for (int i=0; i<parentFields.length-1; i++) { //don''t recheck current field
                if (i==0) {
                    currentPath = parentFields[i];
                } else {
                    currentPath = currentPath+"."+parentFields[i];
                }
                Object value = objectToValidate.get(currentPath, Object.class);
                if (value == null || (value instanceof Collection) && ((Collection) value).size() == 0) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

//    public ValidationErrors checkRequiredFields(Collection<? extends ExtensibleObject> objectCollection, String... requiredFields) {
//        if (!hasErrors() && objectCollection != null) {
//            for (ExtensibleObject object : objectCollection) {
//                if (object == null) {
//                    continue;
//                }
//                checkRequiredFields(object, requiredFields);
//            }
//        }
//
//        return this;
//    }

    public ValidationErrors checkRequiredFields(Enum... requiredFields) {
        String[] fieldsNames = null;
        if (requiredFields != null) {
            fieldsNames = new String[requiredFields.length];
            for (int i = 0; i < requiredFields.length; i++) {
                fieldsNames[i] = requiredFields[i].name();
            }
        }
        return checkRequiredFields(fieldsNames);
    }

    /**
     * If an error was added that the given field is required, remove the error.
     */
    public ValidationErrors removeRequiredField(String field) {
        ListIterator<String> it = errorMessages.listIterator();
        while (it.hasNext()) {
            String message = it.next();
            if (message.equals(field + " is required") || message.equals(field + " is empty")) {
                it.remove();
            }
        }
        return this;
    }

    public ValidationErrors addUnsupportedError(String message) {
        addError(": " + (StringUtil.trimToEmpty(message) + " is not supported").trim());
        return this;
    }

    public ValidationErrors checkUnsupportedFields(String... unsupportedFields) {
        if (!hasErrors() && objectToValidate != null) {
            for (String field : unsupportedFields) {
                Object value = objectToValidate.get(field, Object.class);
                if (value != null) {
                    if (value instanceof Collection) {
                        for (Object obj : (Collection) value) {
                            if (obj != null) {
                                addError(field + " is not supported");
                                return this;
                            }
                        }
                    } else {
                        addError(field + " is not supported");
                    }
                }
            }
        }
        return this;
    }

    public ValidationErrors checkUnsupportedFields(Enum... disallowedFields) {
        String[] names = new String[0];
        if (disallowedFields != null) {
            names = new String[disallowedFields.length];
            for (int i = 0; i < disallowedFields.length; i++) {
                names[i] = disallowedFields[i].name();
            }
        }
        return checkUnsupportedFields(names);
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
        List<String> returnMessages = new ArrayList<>();
        if (objectToValidate == null) {
            returnMessages = this.errorMessages;
        } else {
            String classPrefix = objectToValidate.getClass().getSimpleName();
            String fieldPrefix = classPrefix + ".";

            for (String originalMessage : this.errorMessages) {
                if (originalMessage.startsWith(": ")) {
                    returnMessages.add(classPrefix + originalMessage);
                } else {
                    returnMessages.add(fieldPrefix + originalMessage);
                }
            }
        }
        return Collections.unmodifiableList(returnMessages);
    }

    public List<String> getWarningMessages() {
        return warningMessages;
    }

    public ValidationErrors addAll(ValidationErrors validationErrors, String originalAadNewPrefix) {
        return addAll(validationErrors, originalAadNewPrefix, originalAadNewPrefix);
    }

    public ValidationErrors addAll(ValidationErrors validationErrors, String originalPrefix, String newPrefix) {
        if (validationErrors == null) {
            return this;
        }
        for (String message : validationErrors.errorMessages) {
            if (newPrefix != null) {
                if (originalPrefix == null) {
                    message = newPrefix + message;
                } else if (message.startsWith(originalPrefix)) {
                    message = newPrefix + message.substring(originalPrefix.length());
                } else if (!message.startsWith(":")) {
                    message = newPrefix + "." + message;
                }
            }
            this.errorMessages.add(message);
        }

        for (String message : validationErrors.warningMessages) {
//            if (fieldErrorsCameFrom != null && !message.startsWith(": ")) {
//                message = fieldErrorsCameFrom+"."+message;
//            }
            this.warningMessages.add(message);
        }
        return this;
    }

    @Override
    public String toString() {
        String string;
        if (!hasErrors()) {
            string = "No errors";
        } else {
            string = StringUtil.join(getErrorMessages(), "; ");
        }

        if (hasWarnings()) {
            string = StringUtil.join(getWarningMessages(), "; ", new StringUtil.StringUtilsFormatter() {
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
        return removeUnsupportedField(field.name());
    }

    public ValidationErrors removeUnsupportedField(String field) {
        Iterator<String> messages = errorMessages.iterator();
        while (messages.hasNext()) {
            String message = messages.next();
            if (message.equals(field+" is not supported")) {
                messages.remove();
            }
        }
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

    public ValidationErrors checkField(String field, FieldCheck check) {
        if (objectToValidate == null) {
            return this;
        }

        Object value = objectToValidate.get(field, Object.class);
        if (value != null) {
            if (value instanceof Collection) {
                for (Object obj : (Collection) value) {
                    if (this.hasErrors()) {
                        break;
                    }
                    String errorMessage = check.check(obj);
                    if (errorMessage != null) {
                        this.addError(field + ": " + errorMessage);
                    }
                }
            } else {
                String errorMessage = check.check(value);
                if (errorMessage != null) {
                    this.addError(field + " " + errorMessage);
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
