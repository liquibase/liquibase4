package liquibase.item.function;

import liquibase.item.FunctionCall;

/**
 * A database-independent description of a function that returns the current value of the sequence name stored in {@link #value}
 */
public class SequenceCurrentValueFunction extends FunctionCall {

    public SequenceCurrentValueFunction() {
    }

    public SequenceCurrentValueFunction(String sequenceName) {
        super(sequenceName);
    }
}
