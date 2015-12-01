package liquibase.resource;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * A @{link ResourceAccessor} implementation which finds Files in the File System.
 */
public class FileSystemResourceAccessor extends AbstractResourceAccessor {

    private File baseDirectory;

    /**
     * Creates with no base directory. All files will be resolved exactly as they are given.
     */
    public FileSystemResourceAccessor() {
        baseDirectory = null;
    }

    /**
     * Creates with base directory for relative path support.
     */
    public FileSystemResourceAccessor(String base) {
        baseDirectory = new File(base);
        if (!baseDirectory.isDirectory()) {
            throw new IllegalArgumentException(base+" must be a directory");
        }
    }

    @Override
    public Set<InputStream> getResourcesAsStream(String path) throws IOException {
        File absoluteFile = new File(path);
        File relativeFile = (baseDirectory == null) ? new File(path) : new File(baseDirectory, path);

        InputStream fileStream = null;
        if (absoluteFile.isAbsolute()) {
            try {
                fileStream =  openStream(absoluteFile);
            } catch (FileNotFoundException e) {
                //will try relative
            }
        }

        if (fileStream == null) {
            try {
                fileStream = openStream(relativeFile);
            } catch (FileNotFoundException e2) {
                return null;
            }
        }


        Set<InputStream> returnSet = new HashSet<InputStream>();
        returnSet.add(fileStream);
        return returnSet;
    }

    private InputStream openStream(File file) throws IOException, FileNotFoundException {
        if (file.getName().toLowerCase().endsWith(".gz")) {
            return new BufferedInputStream(new GZIPInputStream(new FileInputStream(file)));
        } else {
            return new BufferedInputStream(new FileInputStream(file));
        }
    }

    @Override
    public Set<String> list(String relativeTo, String path, boolean includeFiles, boolean includeDirectories, boolean recursive) throws IOException {
        File absoluteFile = new File(path);
        File relativeFile = (baseDirectory == null) ? new File(path) : new File(baseDirectory, path);

        if (absoluteFile.exists() && absoluteFile.isDirectory()) {
            Set<String> returnSet = new HashSet<String>();
            getContents(absoluteFile, recursive, includeFiles, includeDirectories, path, returnSet);
            return returnSet;
        } else if (relativeFile.exists() && relativeFile.isDirectory()) {
            Set<String> returnSet = new HashSet<String>();
            getContents(relativeFile, recursive, includeFiles, includeDirectories, path, returnSet);
            return returnSet;
        }

        return null;
    }

    @Override
    public ClassLoader toClassLoader() {
        try {
            return new URLClassLoader(new URL[]{new URL("file://" + baseDirectory)});
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        File dir = baseDirectory;
        if (dir == null) {
            dir = new File(".");
        }
        return getClass().getName()+"("+ dir.getAbsolutePath() +")";
    }

}
