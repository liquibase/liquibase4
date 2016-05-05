package liquibase.item.function;

import liquibase.item.FunctionCall;

/**
 * A database-independent description of a function that returns the next value of the sequence name stored in {@link #value}
 */
public class SequenceNextValueFunction extends FunctionCall {

    public SequenceNextValueFunction() {
    }

    public SequenceNextValueFunction(String sequenceName) {
        super(sequenceName);
    }
}
