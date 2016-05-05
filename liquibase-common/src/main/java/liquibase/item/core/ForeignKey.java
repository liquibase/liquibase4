package liquibase.item.core;

import liquibase.AbstractExtensibleObject;
import liquibase.item.AbstractRelationBasedObject;
import liquibase.util.CollectionUtil;
import liquibase.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A foreign key constraint. The table in {@link AbstractRelationBasedObject} is the table the constraint is on.
 * The field {@link #referencedTable} points to the table with the possible values.
 */
public class ForeignKey extends AbstractRelationBasedObject<ForeignKeyReference> {

    public RelationReference referencedTable;
    public List<ForeignKeyColumnCheck> columnChecks = new ArrayList<>();
    public Boolean deferrable;
    public Boolean initiallyDeferred;
    public ReferentialAction updateRule;
    public ReferentialAction deleteRule;

    private static final StringUtil.StringUtilsFormatter DEFAULT_COLUMN_CHECK_FORMATTER = new StringUtil.StringUtilsFormatter<ForeignKeyColumnCheck>() {
        @Override
        public String toString(ForeignKeyColumnCheck obj) {
            return obj.baseColumn + "->" + obj.referencedColumn;
        }
    };

    public ForeignKey() {
    }

    public ForeignKey(String name) {
        super(Table.class, name);
    }

    public ForeignKey(String name, RelationReference baseTable) {
        super(name, baseTable);
    }

    public ForeignKey(String foreignKeyName, RelationReference baseTable, RelationReference referencedTable, List<String> baseColumns, List<String> referencedColumns) {
        this(foreignKeyName, baseTable);
        this.referencedTable = referencedTable;
        for (int i = 0; i < CollectionUtil.createIfNull(baseColumns).size(); i++) {
            this.columnChecks.add(new ForeignKeyColumnCheck(baseColumns.get(i), referencedColumns.get(i)));
        }
    }

    @Override
    public ForeignKeyReference toReference() {
        return new ForeignKeyReference(name, relation);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return createComparisonString().equals(((ForeignKey) o).createComparisonString());
    }

    /**
     * Creates a string for use in equals, hasCode and compareTo.
     */
    protected String createComparisonString() {
        return this.relation + "(" + StringUtil.join(this.columnChecks, ", ", DEFAULT_COLUMN_CHECK_FORMATTER) +") to " +
                this.referencedTable + "(" + StringUtil.join(this.columnChecks, ", ", DEFAULT_COLUMN_CHECK_FORMATTER) + ")";
    }

    @Override
    public int hashCode() {
        return createComparisonString().hashCode();
    }


    @Override
    public int compareTo(Object other) {
        return createComparisonString().compareTo(((ForeignKey) other).createComparisonString());
    }

    /**
     * Describes a {@link ForeignKey} check between two columns.
     * Only includes the column names since the tables are part of ForeignKey.
     */
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

    /**
     * How to handle violations of a foreign key constraint
     */
    public enum ReferentialAction {
        cascade,
        setNull,
        setDefault,
        restrict,
        noAction
    }
}
