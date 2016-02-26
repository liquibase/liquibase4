package liquibase.item.core;

import liquibase.item.AbstractSchemaBasedObject;

import java.math.BigInteger;

public class Sequence extends AbstractSchemaBasedObject<SequenceReference> {

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

    public Sequence(String name, SchemaReference schema) {
        super(name, schema);
    }

    @Override
    public SequenceReference toReference() {
        return new SequenceReference(name, schema);
    }

}
