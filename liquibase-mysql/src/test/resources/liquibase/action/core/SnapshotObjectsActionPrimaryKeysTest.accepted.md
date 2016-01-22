**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column PKs correctly" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | schemaName      | OPERATIONS
| :---------- | :------- | :-------------- | :------
| 3cd221      | true     | lbcat (SCHEMA)  | **plan**: Execute getPrimaryKeys(lbcat, null, table_name) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 3193e6      | true     | lbcat2 (SCHEMA) | **plan**: Execute getPrimaryKeys(lbcat2, null, table_name) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "can find all primaryKeys in a fully qualified complex table name" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | table                                    | OPERATIONS
| :---------- | :------- | :------------------------------------------- | :------
| e83394      | true     | lbcat.4test_table (TABLE)                    | **plan**: Execute getPrimaryKeys(lbcat, null, 4test_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 56b06d      | true     | lbcat.anotherlowertable (TABLE)              | **plan**: Execute getPrimaryKeys(lbcat, null, anotherlowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 45eba6      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table (TABLE)  | **plan**: Execute getPrimaryKeys(lbcat, null, crazy!@#\$%^&*()_+{}[]'"table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| e0e1e5      | true     | lbcat.lowertable (TABLE)                     | **plan**: Execute getPrimaryKeys(lbcat, null, lowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 1217fc      | true     | lbcat.only_in_lbcat (TABLE)                  | **plan**: Execute getPrimaryKeys(lbcat, null, only_in_lbcat) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| cdbe33      | true     | lbcat2.4test_table (TABLE)                   | **plan**: Execute getPrimaryKeys(lbcat2, null, 4test_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| c51a94      | true     | lbcat2.anotherlowertable (TABLE)             | **plan**: Execute getPrimaryKeys(lbcat2, null, anotherlowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 85fbd4      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table (TABLE) | **plan**: Execute getPrimaryKeys(lbcat2, null, crazy!@#\$%^&*()_+{}[]'"table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 3cb8b0      | true     | lbcat2.lowertable (TABLE)                    | **plan**: Execute getPrimaryKeys(lbcat2, null, lowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| a1779f      | true     | lbcat2.only_in_lbcat2 (TABLE)                | **plan**: Execute getPrimaryKeys(lbcat2, null, only_in_lbcat2) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "can find by PrimaryKeyReference with a table name but null primary key name" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | pkName                                                    | OPERATIONS
| :---------- | :------- | :-------------------------------------------------------- | :------
| 0a97c7      | true     | lbcat.4test_table.UNNAMED (PRIMARYKEY)                    | **plan**: Execute getPrimaryKeys(lbcat, null, 4test_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| e32b23      | true     | lbcat.anotherlowertable.UNNAMED (PRIMARYKEY)              | **plan**: Execute getPrimaryKeys(lbcat, null, anotherlowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 730a19      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (PRIMARYKEY)  | **plan**: Execute getPrimaryKeys(lbcat, null, crazy!@#\$%^&*()_+{}[]'"table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| a53b43      | true     | lbcat.lowertable.UNNAMED (PRIMARYKEY)                     | **plan**: Execute getPrimaryKeys(lbcat, null, lowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| a1ca94      | true     | lbcat.only_in_lbcat.UNNAMED (PRIMARYKEY)                  | **plan**: Execute getPrimaryKeys(lbcat, null, only_in_lbcat) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| e0a233      | true     | lbcat2.4test_table.UNNAMED (PRIMARYKEY)                   | **plan**: Execute getPrimaryKeys(lbcat2, null, 4test_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| cd45d5      | true     | lbcat2.anotherlowertable.UNNAMED (PRIMARYKEY)             | **plan**: Execute getPrimaryKeys(lbcat2, null, anotherlowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 72b4c0      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (PRIMARYKEY) | **plan**: Execute getPrimaryKeys(lbcat2, null, crazy!@#\$%^&*()_+{}[]'"table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| a0be3e      | true     | lbcat2.lowertable.UNNAMED (PRIMARYKEY)                    | **plan**: Execute getPrimaryKeys(lbcat2, null, lowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 712cc9      | true     | lbcat2.only_in_lbcat2.UNNAMED (PRIMARYKEY)                | **plan**: Execute getPrimaryKeys(lbcat2, null, only_in_lbcat2) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test Version: "0cd8ca" #