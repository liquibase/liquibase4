package liquibase.resource;

import liquibase.ExtensibleObject;
import liquibase.exception.LiquibaseException;

import java.io.InputStream;
import java.util.SortedSet;

/**
 * ResourceAccessors abstract file access so they can be read in a variety of environments.
 * Implementations my look for local files in known locations, read from the network, or do whatever else they need to find files.
 * Think of ResourceAccessors as a {@link ClassLoader} but for finding and reading files, not finding and loading classes.
 */
public interface ResourceAccessor extends ExtensibleObject {

    /**
     * Return the streams for each resource mapped by the given path.
     * The path is often a URL but does not have to be.
     * Should accept both / and \ chars for file paths to be platform-independent.
     * If path points to a compressed resource, return a stream of the uncompressed contents of the file.
     * Returns List since multiple resources can map to the same path, such as "META-INF/MAINFEST.MF".
     * Remember to close streams when finished with them.
     *
     * @return null if the resource does not exist.
     * @throws LiquibaseException if there is an error reading an existing path.
     */
    InputStreamList openStreams(String path) throws LiquibaseException;

    /**
     * Returns a single stream matching the given path. See {@link #openStreams(String)} for details about path options.

     * @return null if the resource does not exist
     * @throws LiquibaseException if multiple paths matched the stream
     * @throws LiquibaseException if there is an error reading an existing path
     */
    InputStream openStream(String path) throws LiquibaseException;

    /**
     * Returns the path to all resources contained in the given path.
     * The passed path is not included in the returned set.
     * Returned strings should use "/" for file path separators, regardless of the OS and should accept both / and \ chars for file paths to be platform-independent.
     * Returned set is sorted, normally alphabetically but subclasses can use different comparators.
     * The values returned should be able to be passed into {@link #openStreams(String)} and return the contents.
     * Returned paths should normally be root-relative and therefore not be an absolute path, unless there is a good reason to be absolute.
     *
     * @param path The path to lookup resources in.
     * @param relativeTo Path to an existing file or directory. If not null, the path param is looked up relative to this path. Returns empty set if the relativeTo path does not exist.
     * @param recursive Set to true and will return paths to contents in sub directories as well. Defaults to false
     * @return empty set if nothing was found
     * @throws LiquibaseException if there is an error reading an existing root.
     */
    SortedSet<String> list(String path, String relativeTo, boolean recursive) throws LiquibaseException;
}
