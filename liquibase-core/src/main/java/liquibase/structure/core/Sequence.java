package liquibase.structure.core;

import liquibase.structure.AbstractSchemaObject;
import liquibase.structure.ObjectReference;

import java.math.BigInteger;

public class Sequence extends AbstractSchemaObject {

    public BigInteger startValue;
    public BigInteger incrementBy;
    public BigInteger minValue;
    public BigInteger maxValue;
    public Boolean willCycle;
    public Boolean ordered;
    public BigInteger lastReturnedValue;
    public BigInteger cacheSize;

    public Sequence() {
    }

    public Sequence(String name) {
        super(name);
    }

    public Sequence(ObjectReference reference) {
        super(reference);
    }

    public Sequence(ObjectReference schema, String name) {
        super(schema, name);
    }
}
