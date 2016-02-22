package liquibase.resource;

import liquibase.AbstractExtensibleObject;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.util.CollectionUtil;
import liquibase.util.StringUtils;
import liquibase.util.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Convenience base class for {@link ResourceAccessor} implementations.
 */
public abstract class AbstractResourceAccessor extends AbstractExtensibleObject implements ResourceAccessor {

}
