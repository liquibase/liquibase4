package liquibase.resource;

import liquibase.AbstractExtensibleObject;
import liquibase.exception.LiquibaseException;

import java.io.ByteArrayInputStream;
import java.util.*;

public class MockResourceAccessor extends AbstractExtensibleObject implements ResourceAccessor {

    private Map<String, List<byte[]>> dataByPath = new HashMap<>();

    public MockResourceAccessor() {
    }

    public MockResourceAccessor(Map<String, ?> values) {
        super(values);
    }

    public MockResourceAccessor addMockXsd(String rootElement) {
        this.addData("liquibase/parser/core/xml/mock.xsd", "<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema' "+
        "targetNamespace='http://www.liquibase.org/xml/ns/mock' xmlns='http://www.liquibase.org/xml/ns/mock' "+
        "elementFormDefault='qualified'>\n"+
        "<xsd:element name='"+rootElement+"'/>\n"+
        "</xsd:schema>");

        return this;
    }

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
