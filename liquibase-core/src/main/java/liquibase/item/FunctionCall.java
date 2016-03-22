package liquibase.item;

import liquibase.AbstractExtensibleObject;

/**
 * A value that describes a function being called.
 */
public class FunctionCall extends AbstractExtensibleObject{

    public FunctionCall() {
    }

    public FunctionCall(String value) {
        this.value = value;
    }

    public String value;
}
