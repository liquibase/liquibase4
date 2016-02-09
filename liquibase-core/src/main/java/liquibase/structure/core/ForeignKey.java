package liquibase.structure.core;

import liquibase.AbstractExtensibleObject;
import liquibase.structure.AbstractObject;
import liquibase.structure.AbstractTableObject;
import liquibase.structure.LiquibaseObject;
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

    public ObjectReference referencedTable;
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

    public ForeignKey(ForeignKeyReference reference) {
        super(reference);
    }

    public ForeignKey(ObjectReference baseTable, String name) {
        super(name);
        this.table = baseTable;
    }

    public ForeignKey(ObjectReference baseTable, ObjectReference referencedTable, String name, List<String> baseColumns, List<String> referencedColumns) {
        this(baseTable, name);
        this.referencedTable = referencedTable;
        for (int i = 0; i < CollectionUtil.createIfNull(baseColumns).size(); i++) {
            this.columnChecks.add(new ForeignKeyColumnCheck(baseColumns.get(i), referencedColumns.get(i)));
        }
    }

    @Override
    public ObjectReference toReference() {
        return new ForeignKeyReference(table, name);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return createComparisonString().equals(((ForeignKey) o).createComparisonString());
    }

    protected String createComparisonString() {
        return this.table +
                "(" +
                StringUtils.join(this.columnChecks, ", ", new ForeignKeyColumnCheckStringUtilsFormatter()) +
                ") to " +
                this.referencedTable +
                "(" + StringUtils.join(this.columnChecks, ", ", new ForeignKeyColumnCheckStringUtilsFormatter()) +
                ")";
    }

    @Override
    public int hashCode() {
        String string = StringUtils.join(this.columnChecks, ", ", new ForeignKeyColumnCheckStringUtilsFormatter());
        return string.hashCode();
    }


    @Override
    public int compareTo(Object other) {
        return createComparisonString().compareTo(((ForeignKey) other).createComparisonString());
    }

    public static class ForeignKeyColumnCheck extends AbstractExtensibleObject {
        public String baseColumn;
        public String referencedColumn;

        public ForeignKeyColumnCheck() {
        }

        public ForeignKeyColumnCheck(String baseColumn, String referencedColumn) {
            this.baseColumn = baseColumn;
            this.referencedColumn = referencedColumn;
        }

        @Override
        public String toString() {
            return baseColumn+" TO "+referencedColumn;
        }
    }

    private static class ForeignKeyColumnCheckStringUtilsFormatter implements StringUtils.StringUtilsFormatter<ForeignKeyColumnCheck> {

        @Override
        public String toString(ForeignKeyColumnCheck obj) {
            return obj.baseColumn + "->" + obj.referencedColumn;
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
            super(ForeignKey.class, table, fkName);
        }

        public ForeignKeyReference(String... names) {
            super(ForeignKey.class, names);
        }

        public ObjectReference getTable() {
            return container;
        }

        @Override
        public String toString() {
            return name + " on " + (container == null ? null : container.toString());
        }

        @Override
        public String toLongString() {
            return name + " on " + (container == null ? null : container.toString());
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
