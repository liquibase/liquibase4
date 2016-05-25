package liquibase.parser.mapping.core;

import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.Item;
import liquibase.parser.ParsedNode;
import liquibase.parser.mapping.AbstractParsedNodeMapping;
import liquibase.parser.mapping.ParsedNodeMappingFactory;
import liquibase.snapshot.Snapshot;

import java.lang.reflect.Type;

public class SnapshotNodeMapping extends AbstractParsedNodeMapping<Snapshot> {

    @Override
    public int getPriority(ParsedNode parsedNode, Class objectType, Type containerType, String containerAttribute, Scope scope) {
        if (Snapshot.class.isAssignableFrom(objectType) || (parsedNode != null && parsedNode.getName().equals("snapshot"))) {
            return PRIORITY_SPECIALIZED;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public ParsedNode toParsedNode(Snapshot snapshot, Class containerType, String containerAttribute, ParsedNode parentNode, Scope scope) throws ParseException {
        ParsedNode snapshotAsNode = super.toParsedNode(snapshot, containerType, containerAttribute, parentNode, scope);
        ParsedNodeMappingFactory parsedNodeMappingFactory = scope.getSingleton(ParsedNodeMappingFactory.class);

        for (Class<? extends Item> type : snapshot.getTypes()) {
            ParsedNode typeNode = snapshotAsNode.addChild("items");
            typeNode.addChild("type").setValue(type.getName());
            for (Item item : snapshot.get(type)) {
                parsedNodeMappingFactory.toParsedNode(item, null, null, typeNode, scope);
            }
        }
        return snapshotAsNode;
    }
}
