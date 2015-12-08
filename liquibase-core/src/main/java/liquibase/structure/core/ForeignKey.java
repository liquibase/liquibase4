package liquibase.structure.core;

import liquibase.AbstractExtensibleObject;
import liquibase.structure.AbstractObject;
import liquibase.structure.AbstractTableObject;
import liquibase.structure.ObjectReference;
import liquibase.util.CollectionUtil;
import liquibase.util.StringUtils;
import liquibase.util.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 * Assumes no container for object, base table and referenced table are based on the ForeignKeyColumnChecks
 */
public class ForeignKey extends AbstractTableObject {

    public List<ForeignKeyColumnCheck> columnChecks = new ArrayList<>();
    public Boolean deferrable;
    public Boolean initiallyDeferred;
    public ForeignKey.ConstraintType updateRule;
    public ForeignKey.ConstraintType deleteRule;

    public ForeignKey() {
    }

    public ForeignKey(String name) {
        super(name);
    }

    public ForeignKey(ObjectReference reference) {
        super(reference);
    }

    public ForeignKey(ObjectReference table, String name) {
        super(name);
        this.table = table;
    }

    public ForeignKey(ObjectReference table, String name, List<Column.ColumnReference> foreignKeyColumns, List<Column.ColumnReference> primaryKeyColumns) {
        this(name);
        for (int i=0; i<CollectionUtil.createIfNull(foreignKeyColumns).size(); i++) {
            this.columnChecks.add(new ForeignKeyColumnCheck(foreignKeyColumns.get(i), primaryKeyColumns.get(i), i));
        }
    }

    @Override
    public String toString() {
        return getName() + "(" + StringUtils.join(columnChecks, ", ", new ForeignKeyColumnCheckStringUtilsFormatter())+")";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForeignKey that = (ForeignKey) o;

        String thisString = StringUtils.join(this.columnChecks, ", ", new ForeignKeyColumnCheckStringUtilsFormatter());
        String thatString = StringUtils.join(that.columnChecks, ", ", new ForeignKeyColumnCheckStringUtilsFormatter());

        return thisString.equals(thatString);

    }

    @Override
    public int hashCode() {
        String string = StringUtils.join(this.columnChecks, ", ", new ForeignKeyColumnCheckStringUtilsFormatter());
        return string.hashCode();
    }


    @Override
    public int compareTo(Object other) {
        ForeignKey that = (ForeignKey) other;

        String thisString = StringUtils.join(this.columnChecks, ", ", new ForeignKeyColumnCheckStringUtilsFormatter());
        String thatString = StringUtils.join(that.columnChecks, ", ", new ForeignKeyColumnCheckStringUtilsFormatter());

        return thisString.compareTo(thatString);
    }

    public static class ForeignKeyColumnCheck extends AbstractExtensibleObject {
        public ObjectReference baseColumn;
        public ObjectReference referencedColumn;

        public Integer position;

        public ForeignKeyColumnCheck() {
        }

        public ForeignKeyColumnCheck(ObjectReference baseColumn, ObjectReference referencedColumn) {
            this(baseColumn, referencedColumn, null);
        }

        public ForeignKeyColumnCheck(ObjectReference baseColumn, ObjectReference referencedColumn, Integer position) {
            this.baseColumn = baseColumn;
            this.referencedColumn = referencedColumn;
            this.position = position;
        }
    }

    private static class ForeignKeyColumnCheckStringUtilsFormatter implements StringUtils.StringUtilsFormatter<ForeignKeyColumnCheck> {

        @Override
        public String toString(ForeignKeyColumnCheck obj) {
            return obj.baseColumn + "->" +obj.referencedColumn;
        }
    }

    /**
     * Object container is the constrained table ObjectReference
     */
    public static class ForeignKeyReference extends ObjectReference {

        public ForeignKeyReference() {
            super(ForeignKey.class);
        }

        public ForeignKeyReference(ObjectReference table, String fkName) {
            super(ForeignKey.class, table.container, fkName);
        }

        public ObjectReference getTable() {
            return container;
        }
    }

    public enum ConstraintType {
        importedKeyCascade,
        importedKeySetNull,
        importedKeySetDefault,
        importedKeyRestrict,
        importedKeyNoAction
    }
}
