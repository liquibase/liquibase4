package liquibase.util;

import liquibase.resource.ResourceAccessor;

import java.io.*;
import java.util.Set;

/**
 * Utilities for working with streams.
 */
public class StreamUtil {
	

    /**
     * Reads all the characters into a String.
     *
     * @param reader The Reader to read.
     * @return The contents of the input stream as a String
     * @throws IOException If there is an error reading the stream.
     */
    public static String getReaderContents(Reader reader) throws IOException {
        try {
            StringBuffer result = new StringBuffer();

            char[] buffer = new char[2048];
            int read;
            while ((read = reader.read(buffer)) > -1) {
                result.append(buffer, 0, read);
            }
            return result.toString();
        } finally {
            closeQuietly(reader);
        }
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bytes = new byte[1024];
        int r = inputStream.read(bytes);
        while (r > 0) {
            outputStream.write(bytes, 0, r);
            r = inputStream.read(bytes);
        }
    }

    public static long getContentLength(InputStream in) throws IOException
    {
        long length = 0;
        byte[] buf = new byte[4096];
        int bytesRead = in.read(buf);
        while (bytesRead > 0) {
            length += bytesRead;
            bytesRead = in.read(buf);
        }
        return length;
    }

    public static long getContentLength(Reader reader) throws IOException
    {
        long length = 0;
        char[] buf = new char[2048];
        int charsRead = reader.read(buf);
        while (charsRead > 0) {
            length += charsRead;
            charsRead = reader.read(buf);
        }
        return length;
    }
    
    public static void closeQuietly(Reader input) {
        closeQuietly((Closeable) input);
    }
    
    public static void closeQuietly(InputStream input) {
        closeQuietly((Closeable) input);
    }
    
    public static void closeQuietly(Closeable input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    public static InputStream singleInputStream(String path, ResourceAccessor resourceAccessor) throws IOException {
        Set<InputStream> streams = resourceAccessor.getResourcesAsStream(path);
        if (streams == null || streams.size() == 0) {
            return null;
        }
        if (streams.size() != 1) {
            for (InputStream stream : streams) {
                stream.close();
            }
            throw new IOException("Found "+streams.size()+" files that match "+path);
        }

        return streams.iterator().next();
    }


}
