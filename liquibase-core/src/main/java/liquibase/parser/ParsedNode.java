package liquibase.parser;

import liquibase.AbstractExtensibleObject;

import java.util.ArrayList;
import java.util.List;

public class ParsedNode extends AbstractExtensibleObject {

    public String name;
    public List<ParsedNode> children = new ArrayList<>();
    public Object value;

    public ParsedNode() {
    }

    public ParsedNode(String name) {
        this.name = name;
    }

    public ParsedNode addChild(String name, Object value) {
        children.add(new ParsedNode(name).setValue(value));
        return this;
    }

    public ParsedNode setValue(Object value) {
        this.value = value;
        return this;
    }

    public ParsedNode addChild(ParsedNode node) {
        this.children.add(node);
        return this;
    }
}
