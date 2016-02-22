package liquibase.util;

import liquibase.Scope;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ResourceAccessor;

import java.io.*;
import java.util.List;
import java.util.Set;

/**
 * Utilities for working with streams.
 */
public class StreamUtil {

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bytes = new byte[1024];
        int r = inputStream.read(bytes);
        while (r > 0) {
            outputStream.write(bytes, 0, r);
            r = inputStream.read(bytes);
        }
    }

    public static byte[] readStream(InputStream stream) throws IOException {
        try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            copy(stream, buffer);
            buffer.flush();

            return buffer.toByteArray();
        }
    }

    public static String readStreamAsString(InputStream stream, Scope scope) throws IOException {
        return new String(readStream(stream), "UTF-8");
    }

}
