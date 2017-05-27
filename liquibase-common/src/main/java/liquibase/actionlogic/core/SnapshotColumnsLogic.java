package liquibase.actionlogic.core;

import liquibase.Scope;
import liquibase.action.Action;
import liquibase.action.core.QueryJdbcMetaDataAction;
import liquibase.action.core.SnapshotItemsAction;
import liquibase.actionlogic.AbstractSnapshotDatabaseObjectsLogic;
import liquibase.actionlogic.RowBasedQueryResult;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.exception.ActionPerformException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.item.DatabaseObjectReference;
import liquibase.item.Item;
import liquibase.item.ItemReference;
import liquibase.item.core.*;
import liquibase.item.datatype.DataType;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;
import liquibase.util.Validate;
import org.slf4j.LoggerFactory;

import java.sql.DatabaseMetaData;
import java.util.List;

/**
 * Logic to snapshot database column(s). Delegates to {@link QueryJdbcMetaDataAction} getColumns().
 */
public class SnapshotColumnsLogic extends AbstractSnapshotDatabaseObjectsLogic<Column> {

    @Override
    protected Class<Column> getTypeToSnapshot() {
        return Column.class;
    }

    @Override
    protected Class<? extends Item>[] getSupportedRelatedTypes() {
        return new Class[]{
                Column.class,
                Relation.class,
                Schema.class,
                Catalog.class
        };
    }

    @Override
    /**
     * Creates an ObjectName with null values for "unknown" portions and calls {@link #createColumnSnapshotAction(ItemReference)}.
     */
    protected Action createSnapshotAction(DatabaseObjectReference relatedTo, SnapshotItemsAction action, Scope scope) throws ActionPerformException {
        ColumnReference columnName;

        if (relatedTo.instanceOf(Catalog.class)) {
            columnName = new ColumnReference(null, new RelationReference(Table.class, relatedTo.name, null, null));
        } else if (relatedTo instanceof SchemaReference) {
            columnName = new ColumnReference(null, new RelationReference(Table.class, null, (SchemaReference) relatedTo));
        } else if (relatedTo instanceof RelationReference) {
            columnName = new ColumnReference(null, (RelationReference) relatedTo);
        } else if (relatedTo instanceof ColumnReference) {
            columnName = new ColumnReference(relatedTo.name, ((ColumnReference) relatedTo).container);
        } else {
            throw Validate.failure("Unexpected type: " + relatedTo.getClass().getName());
        }

        return createColumnSnapshotAction(columnName, scope);
    }

    protected Action createColumnSnapshotAction(ColumnReference columnRef, Scope scope) {
        List<String> nameParts = columnRef.asList(4);

        AbstractJdbcDatabase database = (AbstractJdbcDatabase) scope.getDatabase();
        String tableName = database.escapeStringForLike(nameParts.get(2));
        String columnName = database.escapeStringForLike(nameParts.get(3));

        if (tableName == null) {
            tableName = "%";
        }
        if (nameParts.get(0) != null || database.supports(Catalog.class, scope)) {
            return new QueryJdbcMetaDataAction("getColumns", nameParts.get(0), nameParts.get(1), tableName, columnName);
        } else {
            if (database.metaDataCallsSchemasCatalogs()) {
                return new QueryJdbcMetaDataAction("getColumns", nameParts.get(1), null, tableName, columnName);
            } else {
                return new QueryJdbcMetaDataAction("getColumns", null, nameParts.get(1), tableName, columnName);
            }
        }
    }


    @Override
    protected Column convertToObject(Object result, DatabaseObjectReference relatedTo, SnapshotItemsAction originalAction, Scope scope) throws ActionPerformException {
        RowBasedQueryResult.Row row = (RowBasedQueryResult.Row) result;
        Database database = scope.getDatabase();

        String rawTableName = StringUtil.trimToNull(row.get("TABLE_NAME", String.class));
        String rawColumnName = row.get("COLUMN_NAME", String.class);
        String rawSchemaName = StringUtil.trimToNull(row.get("TABLE_SCHEM", String.class));
        String rawCatalogName = StringUtil.trimToNull(row.get("TABLE_CAT", String.class));
        String remarks = StringUtil.trimToNull(row.get("REMARKS", String.class));
        if (remarks != null) {
            remarks = remarks.replace("''", "'"); //come back escaped sometimes
        }


        Column column = new Column();
        if (rawSchemaName == null) {
            column.relation = new RelationReference(Table.class, rawCatalogName, rawTableName);
            column.name = rawColumnName;
        } else {
            if (database.supports(Catalog.class, scope)) {
                column.relation = new RelationReference(Table.class, rawCatalogName, rawSchemaName, rawTableName);
            } else {
                column.relation = new RelationReference(Table.class, rawSchemaName, rawTableName);
            }
            column.name = rawColumnName;

        }

        column.remarks = remarks;

//        if (database instanceof OracleDatabase) {
//            String nullable = row.getString("NULLABLE");
//            if (nullable.equals("Y")) {
//                column.setNullable(true);
//            } else {
//                column.setNullable(false);
//            }
//        } else {
        int nullable = row.get("NULLABLE", Integer.class);
        if (nullable == DatabaseMetaData.columnNoNulls) {
            column.nullable = false;
        } else if (nullable == DatabaseMetaData.columnNullable) {
            column.nullable = true;
        } else if (nullable == DatabaseMetaData.columnNullableUnknown) {
            LoggerFactory.getLogger(getClass()).info("Unknown nullable state for column " + column.toString() + ". Assuming nullable");
            column.nullable = true;
        }
//        }

        if (database.supports(Database.Feature.AUTO_INCREMENT, scope)) {
//            if (table instanceof Table) {
            if (row.get("IS_AUTOINCREMENT", Object.class) != null) {
                String isAutoincrement = row.get("IS_AUTOINCREMENT", String.class);
                isAutoincrement = StringUtil.trimToNull(isAutoincrement);
                if (isAutoincrement == null) {
                    column.autoIncrementDetails = null;
                } else if (isAutoincrement.equals("YES")) {
                    column.autoIncrementDetails = new Column.AutoIncrementDetails();
                } else if (isAutoincrement.equals("NO")) {
                    column.autoIncrementDetails = null;
                } else if (isAutoincrement.equals("")) {
                    LoggerFactory.getLogger(getClass()).info("Unknown auto increment state for column " + column.toString() + ". Assuming not auto increment");
                    column.autoIncrementDetails = null;
                } else {
                    throw new UnexpectedLiquibaseException("Unknown is_autoincrement value: '" + isAutoincrement + "'");
                }
//                } else {
//                    //probably older version of java, need to select from the column to find out if it is auto-increment
//                    String selectStatement;
//                    if (database.getDatabaseProductName().startsWith("DB2 UDB for AS/400")) {
//                        selectStatement = "select " + database.escapeColumnName(rawCatalogName, rawSchemaName, rawTableName, rawColumnName) + " from " + rawSchemaName + "." + rawTableName + " where 0=1";
//                        LoggerFactory.getLogger(getClass()).debug("rawCatalogName : <" + rawCatalogName + ">");
//                        LoggerFactory.getLogger(getClass()).debug("rawSchemaName : <" + rawSchemaName + ">");
//                        LoggerFactory.getLogger(getClass()).debug("rawTableName : <" + rawTableName + ">");
//                        LoggerFactory.getLogger(getClass()).debug("raw selectStatement : <" + selectStatement + ">");
//
//
//                    }
//                    else{
//                        selectStatement = "select " + database.escapeColumnName(rawCatalogName, rawSchemaName, rawTableName, rawColumnName) + " from " + database.escapeTableName(rawCatalogName, rawSchemaName, rawTableName) + " where 0=1";
//                    }
//                    LoggerFactory.getLogger(getClass()).debug("Checking "+rawTableName+"."+rawCatalogName+" for auto-increment with SQL: '"+selectStatement+"'");
//                    Connection underlyingConnection = ((JdbcConnection) database.getConnection()).getUnderlyingConnection();
//                    Statement statement = null;
//                    ResultSet columnSelectRS = null;
//
//                    try {
//                        statement = underlyingConnection.createStatement();
//                        columnSelectRS = statement.executeQuery(selectStatement);
//                        if (columnSelectRS.getMetaData().isAutoIncrement(1)) {
//                            column.setautoIncrementDetails(new Column.autoIncrementDetails());
//                        } else {
//                            column.setautoIncrementDetails(null);
//                        }
//                    } finally {
//                        try {
//                            if (statement != null) {
//                                statement.close();
//                            }
//                        } catch (SQLException ignore) {
//                        }
//                        if (columnSelectRS != null) {
//                            columnSelectRS.close();
//                        }
//                    }
////                }
            }
        }

        column.type = readDataType(row, column, scope);
        column.defaultValue = readDefaultValue(row, column, scope);

        return column;
    }

    protected DataType readDataType(RowBasedQueryResult.Row row, Column column, Scope scope) {
        DataType dataType = new DataType(row.get("TYPE_NAME", String.class));

        dataType.origin = scope.getDatabase().getShortName();
        setDataTypeStandardType(dataType, row, column, scope);
        if (dataType.standardType != null) {
            dataType.valueType = dataType.standardType.valueType;
        }
        setDataTypeParameters(dataType, row, column, scope);

        return dataType;
//        if (database instanceof OracleDatabase) {
//            String dataType = columnMetadataResultSet.getString("DATA_TYPE");
//            dataType = dataType.replace("VARCHAR2", "VARCHAR");
//            dataType = dataType.replace("NVARCHAR2", "NVARCHAR");
//
//            DataType type = new DataType(dataType);
////            type.setDataTypeId(dataType);
//            if (dataType.equalsIgnoreCase("NUMBER")) {
//                type.setColumnSize(columnMetadataResultSet.getInt("DATA_PRECISION"));
////                if (type.getColumnSize() == null) {
////                    type.setColumnSize(38);
////                }
//                type.setDecimalDigits(columnMetadataResultSet.getInt("DATA_SCALE"));
////                if (type.getDecimalDigits() == null) {
////                    type.setDecimalDigits(0);
////                }
////            type.setRadix(10);
//            } else {
//                type.setColumnSize(columnMetadataResultSet.getInt("DATA_LENGTH"));
//
//                if (dataType.equalsIgnoreCase("NCLOB") || dataType.equalsIgnoreCase("BLOB") || dataType.equalsIgnoreCase("CLOB")) {
//                    type.setColumnSize(null);
//                } else if (dataType.equalsIgnoreCase("NVARCHAR") || dataType.equalsIgnoreCase("NCHAR")) {
//                    type.setColumnSize(columnMetadataResultSet.getInt("CHAR_LENGTH"));
//                    type.setColumnSizeUnit(DataType.ColumnSizeUnit.CHAR);
//                } else {
//                    String charUsed = columnMetadataResultSet.getString("CHAR_USED");
//                    DataType.ColumnSizeUnit unit = null;
//                    if ("C".equals(charUsed)) {
//                        unit = DataType.ColumnSizeUnit.CHAR;
//                        type.setColumnSize(type.getColumnSize());
//                    }
//                    type.setColumnSizeUnit(unit);
//                }
//            }
//
//
//            return type;
//        }

//        String columnTypeName = row.get("TYPE_NAME", String.class);

//        if (database instanceof FirebirdDatabase) {
//            if (columnTypeName.equals("BLOB SUB_TYPE 0")) {
//                columnTypeName = "BLOB";
//            }
//            if (columnTypeName.equals("BLOB SUB_TYPE 1")) {
//                columnTypeName = "CLOB";
//            }
//        }

//        if (database instanceof MySQLDatabase && (columnTypeName.equalsIgnoreCase("ENUM") || columnTypeName.equalsIgnoreCase("SET"))) {
//            try {
//                String boilerLength;
//                if (columnTypeName.equalsIgnoreCase("ENUM"))
//                    boilerLength = "7";
//                else // SET
//                    boilerLength = "6";
//                List<String> enumValues = ExecutorService.getInstance().getExecutor(database).queryForList(new RawSqlStatement("SELECT DISTINCT SUBSTRING_INDEX(SUBSTRING_INDEX(SUBSTRING(COLUMN_TYPE, " + boilerLength + ", LENGTH(COLUMN_TYPE) - " + boilerLength + " - 1 ), \"','\", 1 + units.i + tens.i * 10) , \"','\", -1)\n" +
//                        "FROM INFORMATION_SCHEMA.COLUMNS\n" +
//                        "CROSS JOIN (SELECT 0 AS i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) units\n" +
//                        "CROSS JOIN (SELECT 0 AS i UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) tens\n" +
//                        "WHERE TABLE_NAME = '"+column.getRelation().getName()+"' \n" +
//                        "AND COLUMN_NAME = '"+column.getName()+"'"), String.class);
//                String enumClause = "";
//                for (String enumValue : enumValues) {
//                    enumClause += "'"+enumValue+"', ";
//                }
//                enumClause = enumClause.replaceFirst(", $", "");
//                return new DataType(columnTypeName + "("+enumClause+")");
//            } catch (DatabaseException e) {
//                LoggerFactory.getLogger(getClass()).warn("Error fetching enum values", e);
//            }
//        }
//        OldDataType.ColumnSizeUnit columnSizeUnit = OldDataType.ColumnSizeUnit.BYTE;
//
//        int dataType = row.get("DATA_TYPE", Integer.class);
//        Integer columnSize = null;
//        Integer decimalDigits = null;
//        if (!database.dataTypeIsNotModifiable(columnTypeName)) { // don't set size for types like int4, int8 etc
//            columnSize = row.get("COLUMN_SIZE", Integer.class);
//            decimalDigits = row.get("DECIMAL_DIGITS", Integer.class);
//            if (decimalDigits != null && decimalDigits.equals(0)) {
//                decimalDigits = null;
//            }
//        }
//
//        Integer radix = row.get("NUM_PREC_RADIX", Integer.class);
//
//        Integer characterOctetLength = row.get("CHAR_OCTET_LENGTH", Integer.class);

//TODO: refactor action        if (database instanceof DB2Database) {
//            String typeName = row.get("TYPE_NAME", String.class);
//            if (typeName.equalsIgnoreCase("DBCLOB") || typeName.equalsIgnoreCase("GRAPHIC") || typeName.equalsIgnoreCase("VARGRAPHIC")) {
//                if (columnSize != null) {
//                    columnSize = columnSize / 2; //Stored as double length chars
//                }
//            }
//        }


//        DataType type = new DataType(columnTypeName);
//        type.setDataTypeId(dataType);
//        type.setColumnSize(columnSize);
//        type.setDecimalDigits(decimalDigits);
//        type.setRadix(radix);
//        type.setCharacterOctetLength(characterOctetLength);
//        type.setColumnSizeUnit(columnSizeUnit);

//        return type;
    }

    protected void setDataTypeStandardType(DataType dataType, RowBasedQueryResult.Row row, Column column, Scope scope) {
        dataType.standardType = DataType.standardType(dataType.name);
    }

    protected void setDataTypeParameters(DataType dataType, RowBasedQueryResult.Row row, Column column, Scope scope) {
        if (dataType.standardType == DataType.StandardType.VARCHAR || dataType.standardType == DataType.StandardType.NVARCHAR) {
            Long columnSize = row.get("COLUMN_SIZE", Long.class);
            if (columnSize != null) {
                dataType.parameters.add(columnSize.toString());
            }
        }
    }

    protected Object readDefaultValue(RowBasedQueryResult.Row row, Column column, Scope scope) {
        String defaultValueAsString = row.get("COLUMN_DEF", String.class);
        if (defaultValueAsString == null) {
            return null;
        }

        Class valueType = ObjectUtil.defaultIfNull(column.type.valueType, Object.class);
        if (defaultValueAsString.startsWith("(") && defaultValueAsString.endsWith(")")) {
            return new Column.FunctionDefaultValue(defaultValueAsString.substring(1, defaultValueAsString.length()-1));
        } else if (defaultValueAsString.startsWith("'") && defaultValueAsString.endsWith("'")) {
            return ObjectUtil.convert(defaultValueAsString.substring(1, defaultValueAsString.length() - 1), valueType);
        } else {
            return row.get("COLUMN_DEF", valueType);
        }

//TODO: refactor action        if (database instanceof MSSQLDatabase) {
//            Object defaultValue = row.get("COLUMN_DEF", Object.class);
//
//            if (defaultValue != null && defaultValue instanceof String) {
//                if (defaultValue.equals("(NULL)")) {
//                    row.set("COLUMN_DEF", null);
//                }
//            }
//        }
//
//        if (database instanceof OracleDatabase) {
//            if (row.get("COLUMN_DEF", Object.class) == null) {
//                row.set("COLUMN_DEF", row.get("DATA_DEFAULT", Object.class));
//
//                if (row.get("COLUMN_DEF", Object.class) != null && ((String) row.get("COLUMN_DEF", String.class)).equalsIgnoreCase("NULL")) {
//                    row.set("COLUMN_DEF", null);
//                }
//
//                if (row.get("VIRTUAL_COLUMN", String.class).equals("YES")) {
//                    row.set("COLUMN_DEF", "GENERATED ALWAYS AS ("+row.get("COLUMN_DEF", String.class)+")");
//                }
//            }
//
//        }

        //SqlUtil.parseValue(database, row.get("COLUMN_DEF", String.class), columnInfo.type);
    }
}
