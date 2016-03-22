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

    /**
     * If non-empty, the column names in this list are used to check for previously existing rows that should be updated rather than inserted.
     * Standard implementation uses "MERGE" sql statement, but actual SQL will vary by database.
     * For best cross-database compatibility, use only primary key columns.
     */
    public List<String> columnsForUpdateCheck = new ArrayList<>();
    public String path;
    public Character commentLineStart;
    public String encoding;

    public static class LoadDataColumn extends AbstractExtensibleObject {

        public Integer index;
        public String header;
        public String columnName;
        public DataType dataType;
        public Boolean skip;

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
