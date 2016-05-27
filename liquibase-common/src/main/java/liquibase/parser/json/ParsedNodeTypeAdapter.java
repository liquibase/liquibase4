package liquibase.parser.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import liquibase.parser.ParsedNode;
import liquibase.util.ObjectUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * GSON TypeAdapter for {@link JsonParser} and {@link JsonUnparser}
 */
class ParsedNodeTypeAdapter extends TypeAdapter<ParsedNode> {
    @Override
    public void write(JsonWriter jsonWriter, ParsedNode parsedNode) throws IOException {
        Object value = parsedNode.getValue();

        if (parsedNode.getParent() == null) {
            jsonWriter.beginObject();
        }
        jsonWriter.name(parsedNode.getName());

        if (value != null) {
            writeValue(jsonWriter, value);
        } else if (parsedNode.getChildren().size() == 0) {
            jsonWriter.nullValue();
        } else {
            boolean duplicateChildren = false;
            Set<String> names = new HashSet<>();
            for (ParsedNode child : parsedNode.getChildren()) {
                if (!names.add(child.getName())) {
                    duplicateChildren = true;
                    break;
                }
            }

            if (duplicateChildren) {
                jsonWriter.beginArray();
            } else {
                jsonWriter.beginObject();
            }

            for (ParsedNode child : parsedNode.getChildren()) {
                if (duplicateChildren) {
                    jsonWriter.beginObject();
                }
                write(jsonWriter, child);
                if (duplicateChildren) {
                    jsonWriter.endObject();
                }
            }

            if (duplicateChildren) {
                jsonWriter.endArray();
            } else {
                jsonWriter.endObject();
            }
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
        } else  {
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
            addChildrenAndValue(jsonReader, rootNode);
            jsonReader.endObject();
        }
        return rootNode;
    }

    protected void addChildrenAndValue(JsonReader jsonReader, ParsedNode parentNode) throws IOException {
        Stack<ParsedNode> stack = new Stack<>();
        stack.push(parentNode);

        while (!stack.isEmpty()) {
            JsonToken nextToken = jsonReader.peek();
            switch (nextToken) {
                case BEGIN_OBJECT:
                    jsonReader.beginObject();
                    ParsedNode childNode = stack.peek().addChild(jsonReader.nextName());
                    stack.push(childNode);
                    break;
                case NAME:
                    ParsedNode newNode = stack.peek().addChild(jsonReader.nextName());
                    stack.push(newNode);
                    break;
                case END_OBJECT:
                    jsonReader.endObject();
                    stack.pop();

                    break; //done with this object
                case NUMBER:
                    String stringNumber = jsonReader.nextString();
                    if (stringNumber.contains(".")) {
                        stack.peek().setValue(new BigDecimal(stringNumber));
                    } else {
                        stack.peek().setValue(Long.parseLong(stringNumber));
                    }
                    stack.pop();
                    break;
                case NULL:
                    stack.pop();
                    break;
                case BOOLEAN:
                    stack.peek().setValue(jsonReader.nextBoolean());
                    stack.pop();
                    break;
                case STRING:
                    stack.peek().setValue(jsonReader.nextString());
                    stack.pop();
                    break;
                default:
                    throw new IOException("Unexpected json type: " + nextToken.name());
            }
        }
    }
}
