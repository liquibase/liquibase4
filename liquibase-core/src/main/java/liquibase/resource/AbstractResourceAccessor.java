package liquibase.resource;

import liquibase.AbstractExtensibleObject;
import liquibase.exception.LiquibaseException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Convenience base class for {@link ResourceAccessor} implementations.
 */
public abstract class AbstractResourceAccessor extends AbstractExtensibleObject implements ResourceAccessor {

    @Override
    public InputStream openStream(String path) throws LiquibaseException {
        InputStreamList streamList = this.openStreams(path);

        if (streamList == null || streamList.size() == 0) {
            throw new LiquibaseException("Cannot find " + path);
        } else if (streamList.size() > 1) {
            try {
                streamList.close();
            } catch (IOException e) {
                throw new LiquibaseException(e);
            }
            throw new LiquibaseException("Found " + streamList.size() + " files that match " + path);
        } else {
            return streamList.get(0);
        }
    }
}
