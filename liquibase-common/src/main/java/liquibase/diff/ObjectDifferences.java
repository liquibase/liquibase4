package liquibase.diff;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class ObjectDifferences {

    private HashMap<String, Difference> differences = new HashMap<String, Difference>();

    public Set<Difference> getDifferences() {
        return Collections.unmodifiableSet(new TreeSet<>(differences.values()));
    }

    public boolean hasDifferences() {
        return differences.size() > 0;
    }


    public Difference getDifference(String field) {
        return differences.get(field);
    }

    public boolean isDifferent(String field) {
        return differences.containsKey(field);
    }


    public ObjectDifferences addDifference(String changedField, Object referenceValue, Object compareToValue) {
        this.differences.put(changedField, new Difference(changedField, referenceValue, compareToValue));

        return this;
    }

    public ObjectDifferences addDifference(String message, String changedField, Object referenceValue, Object compareToValue) {
        this.differences.put(changedField, new Difference(message, changedField, referenceValue, compareToValue));

        return this;
    }

    public boolean removeDifference(String attribute) {
        return differences.remove(attribute) != null;
    }

}
