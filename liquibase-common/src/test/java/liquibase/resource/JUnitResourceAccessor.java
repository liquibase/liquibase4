package liquibase.resource;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class JUnitResourceAccessor extends ClassLoaderResourceAccessor {

    public JUnitResourceAccessor() throws Exception {
        super(new URLClassLoader(new URL[]{
                new File(System.getProperty("java.io.tmpdir")).toURL(),
        }, JUnitResourceAccessor.class.getClassLoader()));

    }
}
