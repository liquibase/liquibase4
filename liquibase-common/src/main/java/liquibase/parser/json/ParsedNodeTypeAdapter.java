package liquibase.parser.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import liquibase.parser.ParsedNode;
import liquibase.util.ObjectUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * GSON TypeAdapter for {@link JsonParser} and {@link JsonUnparser}
 */
class ParsedNodeTypeAdapter extends TypeAdapter<ParsedNode> {
    @Override
    public void write(JsonWriter jsonWriter, ParsedNode parsedNode) throws IOException {
        Object value = parsedNode.getValue();

        if (parsedNode.getParent() == null) {
            jsonWriter.beginObject();
            jsonWriter.name(parsedNode.getName());

        } else if (!parsedNode.getParent().hasMarker(ParsedNode.Marker.isCollectionNode)) {
            jsonWriter.name(parsedNode.getName());
        }

        boolean isCollection = parsedNode.hasMarker(ParsedNode.Marker.isCollectionNode) || parsedNode.hasDuplicateChildren();

        if (value != null) {
            writeValue(jsonWriter, value);
        } else if (parsedNode.getChildren().size() == 0) {
            jsonWriter.nullValue();
        } else if (isCollection) {
            jsonWriter.beginArray();

            for (ParsedNode child : parsedNode.getChildren()) { //not sorting to preserve collection order
                jsonWriter.beginObject();
                write(jsonWriter, child);
                jsonWriter.endObject();
            }

            jsonWriter.endArray();
        } else {
            jsonWriter.beginObject();

            ArrayList<ParsedNode> children = new ArrayList<>(parsedNode.getChildren());
            Collections.sort(children);
            for (ParsedNode child : children) {
                write(jsonWriter, child);
            }
            jsonWriter.endObject();

        }
        if (parsedNode.getParent() == null) {
            jsonWriter.endObject();
        }
    }

    protected void writeValue(JsonWriter jsonWriter, Object value) throws IOException {
        if (value instanceof Boolean) {
            jsonWriter.value((boolean) value);
        } else if (value instanceof Number) {
            jsonWriter.value((Number) value);
        } else {
            jsonWriter.value(ObjectUtil.convert(value, String.class));
        }
    }

    @Override
    public ParsedNode read(JsonReader jsonReader) throws IOException {
        ParsedNode rootNode = null;

        if (jsonReader.hasNext()) {
            jsonReader.beginObject();
            String nodeName = jsonReader.nextName();
            rootNode = ParsedNode.createRootNode(nodeName);

            JsonToken nextNode = jsonReader.peek();
            if (nextNode == JsonToken.BEGIN_OBJECT) {
                jsonReader.beginObject();
                addChildren(jsonReader, rootNode);
                jsonReader.endObject();
            } else if (nextNode == JsonToken.BEGIN_ARRAY) {
                jsonReader.beginArray();
                addToCollection(jsonReader, rootNode);
                jsonReader.endArray();
            } else {
                rootNode.setValue(readValue(jsonReader));
            }


            jsonReader.endObject();
        }
        return rootNode;
    }

    private void addToCollection(JsonReader jsonReader, ParsedNode parentNode) throws IOException {
        JsonToken nextToken = jsonReader.peek();
//        if (nextToken == JsonToken.BEGIN_OBJECT) {
//            ParsedNode
//        }

        throw new RuntimeException("TODO");
    }

    protected void addChildren(JsonReader jsonReader, ParsedNode parentNode) throws IOException {
        JsonToken nextToken = jsonReader.peek();
        while (nextToken == JsonToken.NAME) { //has children
            String name = jsonReader.nextName();
            ParsedNode node = parentNode.addChild(name);

            JsonToken childToken = jsonReader.peek();
            if (childToken == JsonToken.BEGIN_OBJECT) {
                jsonReader.beginObject();
                addChildren(jsonReader, node);
                jsonReader.endObject();
            } else if (childToken == JsonToken.BEGIN_ARRAY) {
                throw new RuntimeException("array");
            } else {
                node.setValue(readValue(jsonReader));
            }

            nextToken = jsonReader.peek();
        }
    }

    private Object readValue(JsonReader jsonReader) throws IOException {
        JsonToken nextToken = jsonReader.peek();

        switch (nextToken) {
            case NUMBER:
                String stringNumber = jsonReader.nextString();
                if (stringNumber.contains(".")) {
                    return new BigDecimal(stringNumber);
                } else {
                    return Long.parseLong(stringNumber);
                }
            case NULL:
                jsonReader.nextNull();
                return null;
            case BOOLEAN:
                return jsonReader.nextBoolean();
            case STRING:
                return jsonReader.nextString();
            default:
                throw new IOException("Unexpected json type: " + nextToken.name());
        }
    }
}
