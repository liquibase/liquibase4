package liquibase.resource;

import liquibase.AbstractExtensibleObject;
import liquibase.exception.LiquibaseException;
import liquibase.util.CollectionUtil;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A {@link liquibase.resource.ResourceAccessor} that contains multiple sub-accessors and combines the results of all of them.
 */
public class CompositeResourceAccessor extends AbstractExtensibleObject implements ResourceAccessor {

    private List<ResourceAccessor> resourceAccessors;

    public CompositeResourceAccessor(ResourceAccessor... resourceAccessors) {
        this.resourceAccessors = Arrays.asList(CollectionUtil.createIfNull(resourceAccessors));
    }

    @Override
    public InputStreamList openStreams(String path) throws LiquibaseException {
        InputStreamList returnList = new InputStreamList();
        for (ResourceAccessor accessor : resourceAccessors) {
            returnList.addAll(CollectionUtil.createIfNull(accessor.openStreams(path)));
        }
        return returnList;
    }

    @Override
    public SortedSet<String> list(String path, String relativeTo, boolean recursive) throws LiquibaseException {
        SortedSet<String> returnSet = new TreeSet<>();
        for (ResourceAccessor accessor : resourceAccessors) {
            returnSet.addAll(accessor.list(path, relativeTo, recursive));
        }

        return returnSet;
    }
}
