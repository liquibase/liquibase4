package liquibase.resource;

import liquibase.AbstractExtensibleObject;
import liquibase.exception.LiquibaseException;

import java.io.ByteArrayInputStream;
import java.util.*;

public class MockResourceAccessor extends AbstractExtensibleObject implements ResourceAccessor {

    private Map<String, List<byte[]>> dataByPath = new HashMap<>();

    @Override
    public InputStreamList openStreams(String path) throws LiquibaseException {
        List<byte[]> list = dataByPath.get(path);
        if (list == null || list.size() == 0) {
            return null;
        } else {
            InputStreamList returnList = new InputStreamList();
            for (byte[] data : list) {
                returnList.add(new ByteArrayInputStream(data));
            }
            return returnList;
        }
    }

    @Override
    public SortedSet<String> list(String path, String relativeTo, boolean recursive) throws LiquibaseException {
        return null;
    }

    public MockResourceAccessor addData(String path, String data) {
        List<byte[]> dataList = dataByPath.get(path);
        if (dataList == null) {
            dataList = new ArrayList<>();
            dataByPath.put(path, dataList);
        }

        dataList.add(data.getBytes());

        return this;
    }
}
