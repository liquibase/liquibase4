package liquibase.action.core;

import liquibase.AbstractExtensibleObject;
import liquibase.action.AbstractAction;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.core.RelationReference;
import liquibase.util.StringClauses;
import liquibase.util.StringUtil;

import java.util.*;

/**
 * Selects data from a database. Supports common SELECT features including where clause, joins, qualifiers, and aliases.
 */
public class SelectDataAction extends AbstractAction {

    public Boolean distinct;

    /**
     * Relation to select from
     */
    public RelationReference relation;

    /**
     * Alias to use for {@link #relation}
     */
    public String relationAlias;

    /**
     * Columns to select.
     */
    public List<SelectedColumn> columns = new ArrayList<>();

    /**
     * Set how rows are ordered
     */
    public List<OrderedByColumn> order = new ArrayList<>();

    /**
     * Tables and/or views to join.
     */
    public List<JoinedRelation> joins = new ArrayList<>();

    /**
     * Default where clauses are " AND " joined.
     * This can be replaced if you need a different standard operator, or just add a single StringClauses element with a different operator joining it's own clauses.
     */
    public StringClauses where = new StringClauses(" AND ");

    public SelectDataAction() {
    }

    public SelectDataAction(RelationReference relation, SelectedColumn... columns) {
        this(null, relation, columns);
    }

    public SelectDataAction(String relationAlias, RelationReference relation, SelectedColumn... columns) {
        this.relationAlias = relationAlias;
        this.relation = relation;
        addColumn(columns);
    }

    /**
     * Add new clause(s) to the existing {@link #where} clause.
     */
    public SelectDataAction addWhere(String... whereClauses) {
        if (whereClauses != null) {
            for (String whereClause : whereClauses) {
                where.append(whereClause);
            }
        }

        return this;
    }

    /**
     * Add a new join(s) to {@link #joins}
     */
    public SelectDataAction addJoin(JoinedRelation... joins) {
        if (joins != null) {
            Collections.addAll(this.joins, joins);
        }

        return this;
    }

    /**
     * Removes an existing join from {@link #joins}.
     * For safety, throws {@link UnexpectedLiquibaseException} if the join is not in the join list.
     */
    public SelectDataAction removeJoin(JoinedRelation join) {
        Iterator it = joins.iterator();
        while (it.hasNext()) {
            if (it.next().equals(join)) {
                it.remove();
                return this;
            }
        }
        throw new UnexpectedLiquibaseException("Did not find " + join + " to remove");
    }

    /**
     * Replaces an existing join in {@link #joins} with a different one.
     * For safety, throws {@link UnexpectedLiquibaseException} if the join is not in the join list.
     */
    public SelectDataAction replaceJoin(JoinedRelation existingJoin, JoinedRelation newJoin) {
        ListIterator<JoinedRelation> it = joins.listIterator();
        while (it.hasNext()) {
            if (it.next().equals(existingJoin)) {
                it.remove();
                it.add(newJoin);
                return this;
            }
        }
        throw new UnexpectedLiquibaseException("Did not find join " + existingJoin + " to replace");
    }


    /**
     * Add new order(s) to {@link #order}
     */
    public SelectDataAction addOrder(OrderedByColumn... column) {
        if (column != null) {
            this.order.addAll(Arrays.asList(column));
        }
        return this;
    }

    /**
     * Convenience method to add new column(s) to {@link #columns}
     */
    public SelectDataAction addColumn(SelectedColumn... columns) {
        if (columns != null) {
            Collections.addAll(this.columns, columns);
        }
        return this;
    }

    /**
     * Removes an existing column from {@link #columns}.
     * For safety, throws {@link UnexpectedLiquibaseException} if the column is not in the column list.
     */
    public SelectDataAction removeColumn(SelectedColumn column) {
        Iterator it = columns.iterator();
        while (it.hasNext()) {
            if (it.next().equals(column)) {
                it.remove();
                return this;
            }
        }
        throw new UnexpectedLiquibaseException("Did not find column " + column + " to remove");
    }

    /**
     * Replaces an existing column in {@link #columns} with a different column.
     * For safety, throws {@link UnexpectedLiquibaseException} if the column is not in the column list.
     */
    public SelectDataAction replaceColumn(SelectedColumn existingColumn, SelectedColumn newColumn) {
        ListIterator<SelectedColumn> it = columns.listIterator();
        while (it.hasNext()) {
            if (it.next().equals(existingColumn)) {
                it.remove();
                it.add(newColumn);
                return this;
            }
        }
        throw new UnexpectedLiquibaseException("Did not find column " + existingColumn + " to replace");
    }

    /**
     * Type of Join to use for a {@link liquibase.action.core.SelectDataAction.JoinedRelation}.
     * Standard join type is {@link liquibase.action.core.SelectDataAction.JoinType#inner}.
     */
    public enum JoinType {
        inner,
        leftOuter,
        rightOuter,
    }

    public enum OrderDirection {
        ASC,
        DESC,
    }


    public static class JoinedRelation extends AbstractExtensibleObject {
        /**
         * Relation to join
         */
        public RelationReference relation;

        /**
         * Alias to assign to the joined relation
         */
        public String alias;

        public JoinType joinType = JoinType.inner;

        /**
         * How to join the relation.
         * Defaults to " AND " delimited StringClauses.
         */
        public StringClauses onClause = new StringClauses(" AND ");

        public JoinedRelation() {
        }

        public JoinedRelation(RelationReference joinedRelation, String alias) {
            this.relation = joinedRelation;
            this.alias = alias;
        }

        public JoinedRelation(RelationReference joinedRelation, String alias, JoinType joinType) {
            this(joinedRelation, alias);
            this.joinType = joinType;
        }

        public JoinedRelation addOnClause(String wherePortion) {
            onClause.append(wherePortion);

            return this;
        }

        @Override
        public String toString() {
            String returnString = joinType + " join on " + this.relation;

            if (alias != null) {
                returnString = returnString + " AS " + alias;
            }
            if (onClause != null) {
                returnString += " ON " + onClause.toString();
            }
            return returnString;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof JoinedRelation && this.toString().equals(obj.toString());
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }
    }

    /**
     * Describes a column to select.
     * Columns can be marked as "virtual" meaning that they are not a named column but a function.
     * For virtual columns, use {@link #name} to store the funcion.
     */
    public static class SelectedColumn extends AbstractExtensibleObject {
        public String qualifier;
        public String name;
        public String alias;
        public boolean virtual = false;

        public SelectedColumn() {
        }

        public SelectedColumn(String name) {
            this(null, name, null);
        }

        public SelectedColumn(String qualifier, String name, String alias) {
            this(qualifier, name, alias, false);
        }

        public SelectedColumn(String qualifier, String name, String alias, boolean virtual) {
            this.qualifier = qualifier;
            this.name = name;
            this.alias = alias;
            this.virtual = virtual;
        }

        @Override
        public String toString() {
            String string = name;

            if (qualifier != null) {
                string = qualifier + string;
            }

            if (alias != null) {
                string += " AS " + alias;
            }

            if (this.virtual) {
                string += " (virtual)";
            }

            return string;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof SelectedColumn && toString().equals(obj.toString());
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }
    }

    public static class OrderedByColumn extends AbstractExtensibleObject {
        public String qualifier;
        public String name;
        public OrderDirection direction;

        public OrderedByColumn() {
        }

        public OrderedByColumn(String name) {
            this.name = name;
        }

        public OrderedByColumn(String qualifier, String name) {
            this.qualifier = qualifier;
            this.name = name;
        }

        public OrderedByColumn(String qualifier, String name, OrderDirection direction) {
            this.qualifier = qualifier;
            this.name = name;
            this.direction = direction;
        }

        @Override
        public String toString() {
            return (this.qualifier == null ? "" : this.qualifier + ".") + this.name + (direction == null ? "" : " " + direction);
        }
    }

}
