package liquibase.resource;

import liquibase.exception.UnexpectedLiquibaseException;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

import java.nio.file.Path;

/**
 * Custom {@link ClassLoaderResourceAccessor} find local file roots in {@link Bundle}s.
 */
public class OsgiResourceAccessor extends ClassLoaderResourceAccessor {

    public OsgiResourceAccessor(Bundle... bundles) {
        for (Bundle bundle : bundles) {
            try {
                for (Path path : findRootPaths(bundle.adapt(BundleWiring.class).getClassLoader())) {
                    addRootPath(path);
                }
            } catch (Exception e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
    }
}
