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

    public List<Cell> data = new ArrayList<>();

    public RowData() {
    }

    public RowData(RelationReference relation) {
        this.relation = relation;
    }

    public RowData(RelationReference relation, Map<String, ?> data) {
        this.relation = relation;
        for (Map.Entry<String, ?> cell : data.entrySet()) {
            this.data.add(new Cell(cell.getKey(), cell.getValue()));
        }
    }

    public RowData add(String columnName, Object value, DataType targetType) {
        data.add(new Cell(columnName, value, targetType));
        return this;
    }


    /**
     * Convenience method to return the column names
     */
    public final List<String> getColumns() {
        List<String> returnList = new ArrayList<>();
        for (Cell cell : data) {
            returnList.add(cell.columnName);
        }
        return Collections.unmodifiableList(returnList);
    }

    /**
     * Convenience method to return the cell values as a list
     */
    public final List<?> getValues() {
        List returnList = new ArrayList<>();
        for (Cell cell : data) {
            returnList.add(cell.value);
        }
        return Collections.unmodifiableList(returnList);
    }

    /**
     * Convenience method to return the cell data types as a list
     */
    public final List<DataType> getTypes() {
        List<DataType> returnList = new ArrayList<>();
        for (Cell cell : data) {
            returnList.add(cell.type);
        }
        return Collections.unmodifiableList(returnList);
    }

    /**
     * Convenience method to return the value of a given column name
     */
    public final Object getValue(String column) {
        for (Cell cell : data) {
            if (cell.columnName.equals(column)) {
                return cell.value;
            }
        }
        return null;
    }

    @Override
    public ItemReference toReference() {
        return new ItemReference(RowData.class, "#data", relation);
    }

    public static class Cell extends AbstractExtensibleObject {
        public String columnName;
        public Object value;
        public DataType type;

        public Cell() {
        }

        public Cell(String columnName, Object value) {
            this.columnName = columnName;
            this.value = value;
        }

        public Cell(String columnName, Object value, DataType type) {
            this.columnName = columnName;
            this.value = value;
            this.type = type;
        }
    }
}
