package liquibase.action.core;

import liquibase.AbstractExtensibleObject;
import liquibase.action.AbstractAction;
import liquibase.item.core.RelationReference;
import liquibase.item.datatype.DataType;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads data from a file.
 */
public class LoadDataAction extends AbstractAction {

    public RelationReference table;
    public List<LoadDataColumn> columns = new ArrayList<>();
    public String path;
    public Character commentLineStart;
    public String encoding;

    public static class LoadDataColumn extends AbstractExtensibleObject {

        public Integer index;
        public String header;
        public String columnName;
        public DataType dataType;
        public boolean skip = false;

        public LoadDataColumn(String header, DataType dataType) {
            this.header = header;
            this.dataType = dataType;
        }

        public LoadDataColumn(int index, DataType dataType) {
            this.index = index;
            this.dataType = dataType;
        }
    }

}
