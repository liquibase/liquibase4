package liquibase.item.core;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.AbstractRelationBasedObject;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.item.AbstractItemPreprocessor;
import liquibase.parser.unprocessor.AbstractItemUnprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;
import liquibase.parser.unprocessor.core.item.ItemReferenceUnprocessor;
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

    private static final StringUtil.StringUtilFormatter DEFAULT_COLUMN_CHECK_FORMATTER = new StringUtil.StringUtilFormatter<ForeignKeyColumnCheck>() {
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

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractItemPreprocessor(ForeignKey.class) {
            @Override
            protected void processItemNode(ParsedNode itemNode, Scope scope) throws ParseException {
                ParsedNode referencedTable = this.convertToRelationReferenceNode("referencedTableCatalogName", "referencedTableSchemaName", "referencedTableName", itemNode);
                if (referencedTable != null) {
                    referencedTable.rename("referencedTable");
                }

                itemNode.renameChildren("tableName", "relationName");
                this.convertToRelationReferenceNode("catalogName", "schemaName", "relationName", itemNode);
                itemNode.renameChildren("constraint", "foreignKeyColumnCheck");
                this.groupChildren("columnChecks",itemNode, "foreignKeyColumnCheck");


                for (ParsedNode deleteRule : itemNode.getChildren("deleteRule", true)) {
                    String value = deleteRule.getValue(null, String.class);
                    if (value != null) {
                        value = value.replaceAll(" ", "");
                        for (ForeignKey.ReferentialAction possibleValue : ForeignKey.ReferentialAction.values()) {
                            if (value.equalsIgnoreCase(possibleValue.name()) && !value.equals(possibleValue.name())) {
                                deleteRule.setValue(possibleValue.name());
                            }
                        }
                    }
                }

                for (ParsedNode updateRule : itemNode.getChildren("updateRule", true)) {
                    String value = updateRule.getValue(null, String.class);
                    value = value.replaceAll(" ", "");
                    if (value != null) {
                        for (ForeignKey.ReferentialAction possibleValue : ForeignKey.ReferentialAction.values()) {
                            if (value.equalsIgnoreCase(possibleValue.name()) && !value.equals(possibleValue.name())) {
                                updateRule.setValue(possibleValue.name());
                            }
                        }
                    }
                }
            }
        };
    }

    @Override
    public ParsedNodeUnprocessor createUnprocessor() {
        return new AbstractItemUnprocessor(ForeignKey.class) {

            @Override
            protected void unprocessItem(ParsedNode typeNode, Scope scope) throws ParseException {
                ParsedNode columnChecks = typeNode.getChild("columnChecks", false);
                if (columnChecks != null) {
                    columnChecks.renameChildren("foreignKeyColumnCheck", "constraint");
                }

                typeNode.renameChildren("relationName", "tableName");
            }
        };
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
