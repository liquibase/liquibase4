package liquibase.item.function;

import liquibase.item.FunctionCall;

public class SequenceCurrentValueFunction extends FunctionCall {

    public SequenceCurrentValueFunction() {
    }

    public SequenceCurrentValueFunction(String sequenceName) {
        super(sequenceName);
    }
}
