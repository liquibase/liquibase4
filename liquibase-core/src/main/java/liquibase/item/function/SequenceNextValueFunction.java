package liquibase.item.function;

import liquibase.item.FunctionCall;

public class SequenceNextValueFunction extends FunctionCall {

    public SequenceNextValueFunction() {
    }

    public SequenceNextValueFunction(String sequenceName) {
        super(sequenceName);
    }
}
