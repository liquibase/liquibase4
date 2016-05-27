package liquibase.parser.json;

import com.google.gson.*;
import liquibase.Scope;
import liquibase.exception.LiquibaseException;
import liquibase.exception.ParseException;
import liquibase.parser.AbstractParser;
import liquibase.parser.ParsedNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Parses json files using GSON.
 *
 * @see ParsedNodeTypeAdapter
 */
public class JsonParser extends AbstractParser {

    @Override
    public int getPriority(String path, Scope scope) {
        if ((path.toLowerCase().endsWith(".json")) && scope.getResourceAccessor() != null) {
            return PRIORITY_DEFAULT;
        }
        return PRIORITY_NOT_APPLICABLE;
    }

    @Override
    public ParsedNode parse(String path, Scope scope) throws ParseException {
        try (InputStream inputStream = scope.getResourceAccessor().openStream(path)) {
            if (inputStream == null) {
                throw new ParseException("Could not find file to parse: " + path, null);
            }

            try (Reader reader = new InputStreamReader(inputStream)) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .registerTypeAdapter(ParsedNode.class, new ParsedNodeTypeAdapter())
                        .create();

                ParsedNode node = null;
                try {
                    node = gson.fromJson(reader, ParsedNode.class);
                } catch (JsonSyntaxException | JsonIOException e) {
                    throw new ParseException(e, null);
                }

                node.addChild("physicalPath").setValue(path);
                return node;
            }
        } catch (ParseException e) {
            throw e;
        } catch (LiquibaseException | IOException e) {
            throw new ParseException(e, null);
        }
    }

    @Override
    public String describeOriginal(ParsedNode parsedNode) {
        return new JsonParser().describeOriginal(parsedNode);
    }
}
