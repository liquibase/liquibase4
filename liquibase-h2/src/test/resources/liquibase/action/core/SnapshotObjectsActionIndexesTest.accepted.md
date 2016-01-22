**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column indexes correctly" #

- **connection:** h2[config:standard]

| Permutation | Verified | schemaName         | OPERATIONS
| :---------- | :------- | :----------------- | :------
| ffe0a3      | true     | LBSCHEMA2 (SCHEMA) | **plan**: Execute getIndexInfo(null, LBSCHEMA2, TABLE_NAME, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 31a9a7      | true     | PUBLIC (SCHEMA)    | **plan**: Execute getIndexInfo(null, PUBLIC, TABLE_NAME, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "Snapshots column direction correctly" #

- **connection:** h2[config:standard]

| Permutation | Verified | schemaName      | OPERATIONS
| :---------- | :------- | :-------------- | :------
| 31a9a7      | true     | PUBLIC (SCHEMA) | **plan**: Execute getIndexInfo(null, PUBLIC, TABLE1, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "Snapshots multi-column primary key correctly" #

- **connection:** h2[config:standard]

| Permutation | Verified | primaryKey                         | OPERATIONS
| :---------- | :------- | :--------------------------------- | :------
| 6ebb3b      | true     | PUBLIC.TABLE2.UNNAMED (PRIMARYKEY) | **plan**: Execute getIndexInfo(null, PUBLIC, TABLE2, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic<br>Execute getPrimaryKeys(null, PUBLIC, TABLE2) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "Snapshots single-column primary key correctly" #

- **connection:** h2[config:standard]

| Permutation | Verified | primaryKey                         | OPERATIONS
| :---------- | :------- | :--------------------------------- | :------
| 556c73      | true     | PUBLIC.TABLE1.UNNAMED (PRIMARYKEY) | **plan**: Execute getIndexInfo(null, PUBLIC, TABLE1, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic<br>Execute getPrimaryKeys(null, PUBLIC, TABLE1) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "can find all indexes in a fully qualified complex table name" #

- **connection:** h2[config:standard]

| Permutation | Verified | table                                       | OPERATIONS
| :---------- | :------- | :---------------------------------------------- | :------
| 1a7c3e      | true     | LBSCHEMA2.4TEST_table (TABLE)                   | **plan**: Execute getIndexInfo(null, LBSCHEMA2, 4TEST_table, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 782dc1      | true     | LBSCHEMA2.4test_table (TABLE)                   | **plan**: Execute getIndexInfo(null, LBSCHEMA2, 4test_table, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 76fd15      | true     | LBSCHEMA2.ANOTHERUPPERTABLE (TABLE)             | **plan**: Execute getIndexInfo(null, LBSCHEMA2, ANOTHERUPPERTABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| a32fc7      | true     | LBSCHEMA2.AnotherMixedTable (TABLE)             | **plan**: Execute getIndexInfo(null, LBSCHEMA2, AnotherMixedTable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 090453      | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE) | **plan**: Execute getIndexInfo(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 892700      | true     | LBSCHEMA2.MixedTable (TABLE)                    | **plan**: Execute getIndexInfo(null, LBSCHEMA2, MixedTable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 80fd36      | true     | LBSCHEMA2.UPPERTABLE (TABLE)                    | **plan**: Execute getIndexInfo(null, LBSCHEMA2, UPPERTABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 50f117      | true     | LBSCHEMA2.anotherlowertable (TABLE)             | **plan**: Execute getIndexInfo(null, LBSCHEMA2, anotherlowertable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| c0f50e      | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table (TABLE) | **plan**: Execute getIndexInfo(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 1a03ea      | true     | LBSCHEMA2.lowertable (TABLE)                    | **plan**: Execute getIndexInfo(null, LBSCHEMA2, lowertable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| de4ba2      | true     | LBSCHEMA2.only_in_LBSCHEMA2 (TABLE)             | **plan**: Execute getIndexInfo(null, LBSCHEMA2, only_in_LBSCHEMA2, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 1fc527      | true     | PUBLIC.4TEST_table (TABLE)                      | **plan**: Execute getIndexInfo(null, PUBLIC, 4TEST_table, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| d0ffe1      | true     | PUBLIC.4test_table (TABLE)                      | **plan**: Execute getIndexInfo(null, PUBLIC, 4test_table, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| ae9df0      | true     | PUBLIC.ANOTHERUPPERTABLE (TABLE)                | **plan**: Execute getIndexInfo(null, PUBLIC, ANOTHERUPPERTABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 825558      | true     | PUBLIC.AnotherMixedTable (TABLE)                | **plan**: Execute getIndexInfo(null, PUBLIC, AnotherMixedTable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 0f856b      | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE)    | **plan**: Execute getIndexInfo(null, PUBLIC, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| e8e0c1      | true     | PUBLIC.MixedTable (TABLE)                       | **plan**: Execute getIndexInfo(null, PUBLIC, MixedTable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 8bfcdb      | true     | PUBLIC.UPPERTABLE (TABLE)                       | **plan**: Execute getIndexInfo(null, PUBLIC, UPPERTABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| a611b8      | true     | PUBLIC.anotherlowertable (TABLE)                | **plan**: Execute getIndexInfo(null, PUBLIC, anotherlowertable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 8948bb      | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table (TABLE)    | **plan**: Execute getIndexInfo(null, PUBLIC, crazy!@#\$%^&*()_+{}[]'"table, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 38b9af      | true     | PUBLIC.lowertable (TABLE)                       | **plan**: Execute getIndexInfo(null, PUBLIC, lowertable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 97fc83      | true     | PUBLIC.only_in_PUBLIC (TABLE)                   | **plan**: Execute getIndexInfo(null, PUBLIC, only_in_PUBLIC, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "can find by IndexReference with a table name but null index name" #

- **connection:** h2[config:standard]

| Permutation | Verified | indexName                                               | OPERATIONS
| :---------- | :------- | :------------------------------------------------------ | :------
| 1432d6      | true     | LBSCHEMA2.4TEST_table.UNNAMED (INDEX)                   | **plan**: Execute getIndexInfo(null, LBSCHEMA2, 4TEST_table, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 03cee9      | true     | LBSCHEMA2.4test_table.UNNAMED (INDEX)                   | **plan**: Execute getIndexInfo(null, LBSCHEMA2, 4test_table, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 00b79b      | true     | LBSCHEMA2.ANOTHERUPPERTABLE.UNNAMED (INDEX)             | **plan**: Execute getIndexInfo(null, LBSCHEMA2, ANOTHERUPPERTABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 52c1a4      | true     | LBSCHEMA2.AnotherMixedTable.UNNAMED (INDEX)             | **plan**: Execute getIndexInfo(null, LBSCHEMA2, AnotherMixedTable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 33b666      | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED (INDEX) | **plan**: Execute getIndexInfo(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| b5f830      | true     | LBSCHEMA2.MixedTable.UNNAMED (INDEX)                    | **plan**: Execute getIndexInfo(null, LBSCHEMA2, MixedTable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 96c9e3      | true     | LBSCHEMA2.UPPERTABLE.UNNAMED (INDEX)                    | **plan**: Execute getIndexInfo(null, LBSCHEMA2, UPPERTABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| d63c9c      | true     | LBSCHEMA2.anotherlowertable.UNNAMED (INDEX)             | **plan**: Execute getIndexInfo(null, LBSCHEMA2, anotherlowertable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| f3d080      | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (INDEX) | **plan**: Execute getIndexInfo(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| f5f199      | true     | LBSCHEMA2.lowertable.UNNAMED (INDEX)                    | **plan**: Execute getIndexInfo(null, LBSCHEMA2, lowertable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 1721b3      | true     | LBSCHEMA2.only_in_LBSCHEMA2.UNNAMED (INDEX)             | **plan**: Execute getIndexInfo(null, LBSCHEMA2, only_in_LBSCHEMA2, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| ce1eaf      | true     | PUBLIC.4TEST_table.UNNAMED (INDEX)                      | **plan**: Execute getIndexInfo(null, PUBLIC, 4TEST_table, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 0db5d2      | true     | PUBLIC.4test_table.UNNAMED (INDEX)                      | **plan**: Execute getIndexInfo(null, PUBLIC, 4test_table, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| f420eb      | true     | PUBLIC.ANOTHERUPPERTABLE.UNNAMED (INDEX)                | **plan**: Execute getIndexInfo(null, PUBLIC, ANOTHERUPPERTABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 4f2352      | true     | PUBLIC.AnotherMixedTable.UNNAMED (INDEX)                | **plan**: Execute getIndexInfo(null, PUBLIC, AnotherMixedTable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| f527f1      | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED (INDEX)    | **plan**: Execute getIndexInfo(null, PUBLIC, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| a78756      | true     | PUBLIC.MixedTable.UNNAMED (INDEX)                       | **plan**: Execute getIndexInfo(null, PUBLIC, MixedTable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 1eb1f9      | true     | PUBLIC.UPPERTABLE.UNNAMED (INDEX)                       | **plan**: Execute getIndexInfo(null, PUBLIC, UPPERTABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| fe22ce      | true     | PUBLIC.anotherlowertable.UNNAMED (INDEX)                | **plan**: Execute getIndexInfo(null, PUBLIC, anotherlowertable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 047da9      | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (INDEX)    | **plan**: Execute getIndexInfo(null, PUBLIC, crazy!@#\$%^&*()_+{}[]'"table, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| fa5d40      | true     | PUBLIC.lowertable.UNNAMED (INDEX)                       | **plan**: Execute getIndexInfo(null, PUBLIC, lowertable, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| f0e836      | true     | PUBLIC.only_in_PUBLIC.UNNAMED (INDEX)                   | **plan**: Execute getIndexInfo(null, PUBLIC, only_in_PUBLIC, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "can find complex index names with a table" #

- **connection:** h2[config:standard]

| Permutation | Verified | indexName                                         | OPERATIONS
| :---------- | :------- | :------------------------------------------------ | :------
| 5d73d2      | true     | KNOWN_TABLE.4TEST_index (INDEX)                   | **plan**: Execute getIndexInfo(null, null, KNOWN_TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| d7e782      | true     | KNOWN_TABLE.4test_index (INDEX)                   | **plan**: Execute getIndexInfo(null, null, KNOWN_TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 44fed9      | true     | KNOWN_TABLE.ANOTHERUPPERINDEX (INDEX)             | **plan**: Execute getIndexInfo(null, null, KNOWN_TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 83e29f      | true     | KNOWN_TABLE.AnotherMixedIndex (INDEX)             | **plan**: Execute getIndexInfo(null, null, KNOWN_TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 4f9f42      | true     | KNOWN_TABLE.CRAZY!@#\$%^&*()_+{}[]'"INDEX (INDEX) | **plan**: Execute getIndexInfo(null, null, KNOWN_TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 022136      | true     | KNOWN_TABLE.MixedIndex (INDEX)                    | **plan**: Execute getIndexInfo(null, null, KNOWN_TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 67f5d6      | true     | KNOWN_TABLE.UPPERINDEX (INDEX)                    | **plan**: Execute getIndexInfo(null, null, KNOWN_TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| cc6331      | true     | KNOWN_TABLE.anotherlowerindex (INDEX)             | **plan**: Execute getIndexInfo(null, null, KNOWN_TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 16adfe      | true     | KNOWN_TABLE.crazy!@#\$%^&*()_+{}[]'"index (INDEX) | **plan**: Execute getIndexInfo(null, null, KNOWN_TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 982905      | true     | KNOWN_TABLE.lowerindex (INDEX)                    | **plan**: Execute getIndexInfo(null, null, KNOWN_TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| c2a9e9      | true     | KNOWN_TABLE.only_in_LBSCHEMA2 (INDEX)             | **plan**: Execute getIndexInfo(null, null, KNOWN_TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 9ef795      | true     | KNOWN_TABLE.only_in_PUBLIC (INDEX)                | **plan**: Execute getIndexInfo(null, null, KNOWN_TABLE, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test Version: "dc9421" #