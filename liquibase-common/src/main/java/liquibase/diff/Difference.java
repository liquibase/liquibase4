package liquibase.diff;

import liquibase.AbstractExtensibleObject;

public class Difference extends AbstractExtensibleObject implements Comparable {
    public String message;
    public String field;
    public Object referenceValue;
    public Object comparedValue;

    public Difference(String field, Object referenceValue, Object comparedValue) {
        this(null, field, referenceValue, comparedValue);
    }

    public Difference(String message, String field, Object referenceValue, Object comparedValue) {
        if (message == null) {
            message = field+" changed from '"+referenceValue+"' to '"+comparedValue+"'";
        }
        this.message = message;
        this.field = field;
        this.referenceValue = referenceValue;
        this.comparedValue = comparedValue;
    }


    @Override
    public String toString() {
        return message;
    }

    @Override
    public int compareTo(Object o) {
        return this.field.compareTo(((Difference) o).field);
    }
}
