package liquibase.item.core;

import liquibase.AbstractExtensibleObject;
import liquibase.Scope;
import liquibase.exception.ParseException;
import liquibase.item.AbstractRelationBasedObject;
import liquibase.parser.ParsedNode;
import liquibase.parser.preprocessor.ParsedNodePreprocessor;
import liquibase.parser.preprocessor.core.item.AbstractItemPreprocessor;
import liquibase.parser.unprocessor.AbstractItemUnprocessor;
import liquibase.parser.unprocessor.AbstractParsedNodeUnprocessor;
import liquibase.parser.unprocessor.ParsedNodeUnprocessor;
import liquibase.util.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PrimaryKey extends AbstractRelationBasedObject<PrimaryKeyReference> {

    public List<PrimaryKeyColumn> columns = new ArrayList<>();
    public String tablespace;
    public Boolean clustered;

    public PrimaryKey() {
    }

    public PrimaryKey(String... name) {
        super(Table.class, name);
    }

    public PrimaryKey(String name, RelationReference table) {
        super(name, table);
    }

    public PrimaryKey(String pkName, RelationReference table, String... columns) {
        super(pkName, table);

        for (String columnName : columns) {
            this.columns.add(new PrimaryKeyColumn(columnName));
        }
    }

    @Override
    public PrimaryKeyReference toReference() {
        return new PrimaryKeyReference(name, relation);
    }

    public boolean containsColumn(Column column) {
        if (this.relation != null && column.relation != null && !column.relation.equals(this.relation, true)) {
            return false;
        }
        for (PrimaryKeyColumn ref : CollectionUtil.createIfNull(columns)) {
            if (ref.name.equals(column.name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ParsedNodePreprocessor createPreprocessor() {
        return new AbstractItemPreprocessor(PrimaryKey.class) {
            @Override
            public Class<? extends ParsedNodePreprocessor>[] mustBeBefore() {
                return CollectionUtil.union(Class.class, super.mustBeBefore(), new Column().createPreprocessor().getClass());
            }

            @Override
            protected void processItemNode(ParsedNode itemNode, Scope scope) throws ParseException {
                ParsedNode columns = itemNode.getChild("columns", true);
                itemNode.moveChildren("column", columns);
                columns.renameChildren("column", "primaryKeyColumn");

                if (columns.getValue(Collections.emptyList(), List.class).size() == 0) {
                    columns.remove();
                }

                itemNode.renameChildren("tableName", "relationName");
                this.convertToRelationReferenceNode("catalogName", "schemaName", "relationName", itemNode);       }
        };
    }

    @Override
    public ParsedNodeUnprocessor createUnprocessor() {
        return new AbstractItemUnprocessor(PrimaryKey.class) {
            @Override
            protected void unprocessItem(ParsedNode typeNode, Scope scope) throws ParseException {
                ParsedNode columns = typeNode.getChild("columns", false);
                if (columns != null) {
                    columns.renameChildren("primaryKeyColumn", "column");
                }

                typeNode.renameChildren("relationName", "tableName");
            }
        };
    }

    public static class PrimaryKeyColumn extends AbstractExtensibleObject {

        public String name;
        /**
         * Not normally included in a snapshot because the primary key does not actually have a direction. Supported when creating a primary key, however.
         */
        public Index.IndexDirection direction;

        public PrimaryKeyColumn() {
        }

        public PrimaryKeyColumn(String name) {
            this.name = name;
        }

        public PrimaryKeyColumn(String name, Index.IndexDirection direction) {
            this(name);
            this.direction = direction;
        }

        public String toString(boolean includeRelation) {
            if (includeRelation) {
                return toString();
            } else {
                return name + (direction == null ? "" : " " + direction);
            }
        }
    }


}