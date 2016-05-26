package liquibase.item.core;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.parser.ParsedNode;
import liquibase.parser.unprocessor.AbstractParsedNodeUnprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;

public class Table extends Relation {

    public String tablespace;

    public Table() {
    }

    public Table(String... name) {
        super(name);
    }

    public Table(String name, SchemaReference schema) {
        super(name, schema);
    }

    @Override
    public ParsedNodeUnprocessor createUnprocessor() {
        return new AbstractParsedNodeUnprocessor() {
            @Override
            public void unprocess(ParsedNode node, Scope scope) throws ParseException {
                for (ParsedNode tableName : node.getChildren(Table.class, true)) {
                    markChildrenAsAttributes(tableName, "name");
                }
            }
        };
    }
}
