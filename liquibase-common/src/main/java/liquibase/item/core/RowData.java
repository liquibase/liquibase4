package liquibase.item.core;

import liquibase.AbstractExtensibleObject;
import liquibase.item.AbstractRelationBasedObject;
import liquibase.item.ItemReference;
import liquibase.item.datatype.DataType;

import java.util.*;

/**
 * Data in a row.
 */
public class RowData extends AbstractRelationBasedObject {

    public List<ColumnData> columns = new ArrayList<>();

    public RowData() {
    }

    public RowData(RelationReference relation) {
        this.relation = relation;
    }

    public RowData(RelationReference relation, Map<String, ?> columns) {
        this.relation = relation;
        for (Map.Entry<String, ?> cell : columns.entrySet()) {
            this.columns.add(new ColumnData(cell.getKey(), cell.getValue()));
        }
    }

    public RowData add(String columnName, Object value, DataType targetType) {
        columns.add(new ColumnData(columnName, value, targetType));
        return this;
    }


    /**
     * Convenience method to return the column names
     */
    public final List<String> getColumns() {
        List<String> returnList = new ArrayList<>();
        for (ColumnData columnData : columns) {
            returnList.add(columnData.name);
        }
        return Collections.unmodifiableList(returnList);
    }

    /**
     * Convenience method to return the cell values as a list
     */
    public final List<?> getValues() {
        List returnList = new ArrayList<>();
        for (ColumnData columnData : columns) {
            returnList.add(columnData.value);
        }
        return Collections.unmodifiableList(returnList);
    }

    /**
     * Convenience method to return the cell data types as a list
     */
    public final List<DataType> getTypes() {
        List<DataType> returnList = new ArrayList<>();
        for (ColumnData columnData : columns) {
            returnList.add(columnData.type);
        }
        return Collections.unmodifiableList(returnList);
    }

    /**
     * Convenience method to return the value of a given column name
     */
    public final Object getValue(String column) {
        for (ColumnData columnData : columns) {
            if (columnData.name.equals(column)) {
                return columnData.value;
            }
        }
        return null;
    }

    @Override
    public ItemReference toReference() {
        return new ItemReference(RowData.class, "#data", relation);
    }

    public static class ColumnData extends AbstractExtensibleObject {
        public String name;
        public Object value;
        public DataType type;

        public ColumnData() {
        }

        public ColumnData(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public ColumnData(String name, Object value, DataType type) {
            this.name = name;
            this.value = value;
            this.type = type;
        }
    }
}
