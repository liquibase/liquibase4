package liquibase.resource;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class JUnitResourceAccessor extends ClassLoaderResourceAccessor {

    public JUnitResourceAccessor() throws Exception {
        super(new URLClassLoader(new URL[]{
                //integrationClassesDir.toURL(),
                //integrationTestClassesDir.toURL(),
                //new File(srcDir, "test/java").toURL(),
//                new File(TestContext.getInstance().findIntegrationTestProjectRoot(), "src/test/resources/packaged-changelog.jar").toURL(),
                new File(System.getProperty("java.io.tmpdir")).toURL(),
        }));

    }
}
