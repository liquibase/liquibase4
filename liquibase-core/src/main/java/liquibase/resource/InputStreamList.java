package liquibase.resource;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A list of {@link InputStream}s. Custom class to allow try-with-resources using output from {@link ResourceAccessor#openStreams(String)}.
 */
public class InputStreamList extends ArrayList<InputStream> implements AutoCloseable {

    /**
     * Close the streams in this collection.
     */
    @Override
    public void close() throws IOException {
        for (InputStream stream : this) {
            try {
                stream.close();
            } catch (IOException e) {
                LoggerFactory.getLogger(getClass()).error("Error closing stream. Logging error and continuing", e);
            }
        }
    }
}
