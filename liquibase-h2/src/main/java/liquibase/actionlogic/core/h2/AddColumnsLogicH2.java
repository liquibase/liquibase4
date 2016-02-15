package liquibase.actionlogic.core.h2;

import liquibase.Scope;
import liquibase.action.core.AddColumnsAction;
import liquibase.actionlogic.core.AddColumnsLogic;
import liquibase.database.Database;
import liquibase.database.core.h2.H2Database;
import liquibase.exception.ValidationErrors;
import liquibase.structure.core.Column;
import liquibase.structure.datatype.DataType;
import liquibase.util.StringClauses;
import liquibase.util.StringUtils;

public class AddColumnsLogicH2 extends AddColumnsLogic {

    @Override
    protected Class<? extends Database> getRequiredDatabase() {
        return H2Database.class;
    }

    @Override
    public ValidationErrors validate(AddColumnsAction action, Scope scope) {
        return super.validate(action, scope)
                .checkUnsupportedFields("primaryKey", "columns.autoIncrementInformation");
    }

    @Override
    protected boolean assertDataTypesCorrect(Column actionColumn, Column snapshotColumn, Scope scope) {
        DataType.StandardType snapshotType = snapshotColumn.type.standardType;

        if (snapshotColumn.isAutoIncrement()) { //auto-increment columns are always big_int
            return true;
        } else {
            return super.assertDataTypesCorrect(actionColumn, snapshotColumn, scope);
        }

    }

    @Override
    protected StringClauses getColumnClause(Column column, AddColumnsAction action, Scope scope) {
        StringClauses clauses = super.getColumnClause(column, action, scope);

        if (StringUtils.trimToNull(column.remarks) != null) {
            clauses.insertAfter(Clauses.primaryKey, "COMMENT "+scope.getDatabase().quoteString(column.remarks, scope));
        }

        return clauses;

    }
}
