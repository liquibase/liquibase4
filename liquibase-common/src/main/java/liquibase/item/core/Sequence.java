package liquibase.item.core;

import liquibase.item.AbstractSchemaBasedObject;
import liquibase.item.datatype.DataType;

import java.math.BigInteger;

public class Sequence extends AbstractSchemaBasedObject<SequenceReference> {

    public DataType dataType;
    public BigInteger startValue;
    public BigInteger incrementBy;
    public BigInteger minValue;
    public BigInteger maxValue;
    public Boolean cycle;
    public Boolean ordered;
    public BigInteger cacheSize;

    public BigInteger lastReturnedValue;

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
