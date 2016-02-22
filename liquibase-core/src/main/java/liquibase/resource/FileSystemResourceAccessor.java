package liquibase.resource;

import liquibase.exception.LiquibaseException;
import liquibase.util.CollectionUtil;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * A @{link ResourceAccessor} implementation for files on the file system.
 * Will look for files in zip and jar files if they are added as root paths.
 */
public class FileSystemResourceAccessor extends AbstractResourceAccessor {

    //Set to avoid duplicates but LinkedHashSet to preserve order. Kept private to control access through get/set since we are an ExtensibleObject
    private LinkedHashSet<Path> rootPaths = new LinkedHashSet<>();

    /**
     * Creates a FileSystemResourceAccessor with the given directories/files as the roots.
     */
    public FileSystemResourceAccessor(String... baseDirsAndFiles) {
        for (String baseDir : CollectionUtil.createIfNull(baseDirsAndFiles)) {
            Path path = FileSystems.getDefault().getPath(baseDir);
            if (path.toFile().isDirectory()) {
                addRootPath(path);
            } else if (path.toString().toLowerCase().endsWith(".jar") || path.toString().toLowerCase().endsWith("zip")) {
                addRootPath(path);
            } else {
                throw new IllegalArgumentException(path.toString() + " must be a directory, jar or zip");
            }
        }
    }

    protected void addRootPath(Path path) {
        rootPaths.add(path);
    }

    protected LinkedHashSet<Path> getRootPaths() {
        return rootPaths;
    }

    @Override
    public InputStreamList openStreams(String path) throws LiquibaseException {
        final InputStreamList streams = new InputStreamList();
        visitFiles(path, null, false, new ResourceFileVisitor() {
            @Override
            public void visitRegularFile(Path visitedFile, Path rootPath, BasicFileAttributes attrs) throws IOException {
                InputStream stream = new BufferedInputStream(Files.newInputStream(visitedFile));
                if (isCompressedFile(rootPath)) {//ZipFileSystem will be closed before the stream can be read, so must copy the data out of the zip.
                    ByteArrayInputStream newStream;
                    try {
                        newStream = new ByteArrayInputStream(StreamUtil.readStream(stream));
                    } finally {
                        stream.close();
                    }
                    stream = newStream;

                }

                if (visitedFile.toString().toLowerCase().endsWith(".gz")) {
                    stream = new GZIPInputStream(stream);
                }

                streams.add(stream);
            }
        });

        return streams;
    }

    @Override
    public SortedSet<String> list(String path, String relativeTo, boolean recursive) throws LiquibaseException {
        final SortedSet<String> returnList = new TreeSet<>();

        visitFiles(path, relativeTo, recursive, new ResourceFileVisitor() {
            @Override
            public void visitRegularFile(Path visitedFile, Path rootPath, BasicFileAttributes attrs) {
                if (isCompressedFile(rootPath)) {
                    returnList.add(visitedFile.normalize().toString().substring(1)); //pull off leading /
                } else {
                    returnList.add(rootPath.relativize(visitedFile).normalize().toString().replace("\\", "/"));
                }
            }
        });
        return returnList;
    }

    /**
     * Visit the files in the all the {@link #getRootPaths()} with the given {@link liquibase.resource.FileSystemResourceAccessor.ResourceFileVisitor}.
     * Called by {@link #list(String, String, boolean)} and {@link ResourceAccessor#openStreams(String)} with method-specific visitors.
     * This method correctly handles zip/jar files and searches them.
     * Will visit the passed path, so ignore that path if you should.
     */
    protected void visitFiles(String path, String relativeTo, boolean recursive, final ResourceFileVisitor visitor) throws LiquibaseException {
        int maxDepth = recursive ? Integer.MAX_VALUE : 1;

        for (final Path rootPath : getRootPaths()) {
            SimpleFileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (attrs.isRegularFile()) {
                        visitor.visitRegularFile(file, rootPath, attrs);
                    }
                    return FileVisitResult.CONTINUE;
                }
            };


            if (isCompressedFile(rootPath)) {
                try (FileSystem fs = FileSystems.newFileSystem(rootPath, null)) {
                    Path basePath = fs.getRootDirectories().iterator().next();

                    if (path != null) {
                        basePath = basePath.resolve(path);
                    }

                    Files.walkFileTree(basePath, Collections.singleton(FileVisitOption.FOLLOW_LINKS), maxDepth, fileVisitor);
                } catch (NoSuchFileException e) {
                    //nothing to do, return null
                } catch (IOException e) {
                    throw new LiquibaseException(e);
                }
            } else {
                Path basePath = rootPath;
                if (relativeTo != null) {
                    basePath = basePath.resolve(relativeTo);
                    if (basePath.toFile().isFile()) {
                        basePath = basePath.getParent();
                    }
                }

                if (path != null) {
                    basePath = basePath.resolve(path);
                }

                try {
                    if (!basePath.toFile().exists()) {
                        continue;
                    }
                    Files.walkFileTree(basePath, Collections.singleton(FileVisitOption.FOLLOW_LINKS), maxDepth, fileVisitor);
                } catch (IOException e) {
                    throw new LiquibaseException(e);
                }

            }
        }
    }

    /**
     * Returns true if the given path is a compressed file.
     */
    protected boolean isCompressedFile(Path path) {
        return path != null && (path.toString().toLowerCase().endsWith(".jar") || path.toString().toLowerCase().endsWith(".zip"));
    }

    @Override
    public String toString() {
        return getClass().getName() + " (" + StringUtils.join(getRootPaths(), ", ", new StringUtils.ToStringFormatter()) + ")";
    }

    /**
     * Encapsulates logic used by {@link ResourceAccessor#openStreams(String)} and {@link #list(String, String, boolean)} for each file.
     */
    public abstract class ResourceFileVisitor {

        public abstract void visitRegularFile(Path visitedFile, Path rootPath, BasicFileAttributes attrs) throws IOException;

    }
}
