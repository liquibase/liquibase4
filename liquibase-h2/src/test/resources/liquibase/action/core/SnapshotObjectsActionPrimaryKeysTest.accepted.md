**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column PKs correctly" #

- **connection:** h2[config:standard]

| Permutation | Verified | schemaName         | OPERATIONS
| :---------- | :------- | :----------------- | :------
| ffe0a3      | true     | LBSCHEMA2 (SCHEMA) | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, TABLE_NAME) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 31a9a7      | true     | PUBLIC (SCHEMA)    | **plan**: Execute getPrimaryKeys(null, PUBLIC, TABLE_NAME) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "can find all primaryKeys in a fully qualified complex table name" #

- **connection:** h2[config:standard]

| Permutation | Verified | table                                       | OPERATIONS
| :---------- | :------- | :---------------------------------------------- | :------
| 1a7c3e      | true     | LBSCHEMA2.4TEST_table (TABLE)                   | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, 4TEST_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 782dc1      | true     | LBSCHEMA2.4test_table (TABLE)                   | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, 4test_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 76fd15      | true     | LBSCHEMA2.ANOTHERUPPERTABLE (TABLE)             | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| a32fc7      | true     | LBSCHEMA2.AnotherMixedTable (TABLE)             | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, AnotherMixedTable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 090453      | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE) | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 892700      | true     | LBSCHEMA2.MixedTable (TABLE)                    | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, MixedTable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 80fd36      | true     | LBSCHEMA2.UPPERTABLE (TABLE)                    | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, UPPERTABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 50f117      | true     | LBSCHEMA2.anotherlowertable (TABLE)             | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, anotherlowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| c0f50e      | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table (TABLE) | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 1a03ea      | true     | LBSCHEMA2.lowertable (TABLE)                    | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, lowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| de4ba2      | true     | LBSCHEMA2.only_in_LBSCHEMA2 (TABLE)             | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, only_in_LBSCHEMA2) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 1fc527      | true     | PUBLIC.4TEST_table (TABLE)                      | **plan**: Execute getPrimaryKeys(null, PUBLIC, 4TEST_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| d0ffe1      | true     | PUBLIC.4test_table (TABLE)                      | **plan**: Execute getPrimaryKeys(null, PUBLIC, 4test_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| ae9df0      | true     | PUBLIC.ANOTHERUPPERTABLE (TABLE)                | **plan**: Execute getPrimaryKeys(null, PUBLIC, ANOTHERUPPERTABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 825558      | true     | PUBLIC.AnotherMixedTable (TABLE)                | **plan**: Execute getPrimaryKeys(null, PUBLIC, AnotherMixedTable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 0f856b      | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE)    | **plan**: Execute getPrimaryKeys(null, PUBLIC, CRAZY!@#\$%^&*()_+{}[]'"TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| e8e0c1      | true     | PUBLIC.MixedTable (TABLE)                       | **plan**: Execute getPrimaryKeys(null, PUBLIC, MixedTable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 8bfcdb      | true     | PUBLIC.UPPERTABLE (TABLE)                       | **plan**: Execute getPrimaryKeys(null, PUBLIC, UPPERTABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| a611b8      | true     | PUBLIC.anotherlowertable (TABLE)                | **plan**: Execute getPrimaryKeys(null, PUBLIC, anotherlowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 8948bb      | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table (TABLE)    | **plan**: Execute getPrimaryKeys(null, PUBLIC, crazy!@#\$%^&*()_+{}[]'"table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 38b9af      | true     | PUBLIC.lowertable (TABLE)                       | **plan**: Execute getPrimaryKeys(null, PUBLIC, lowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 97fc83      | true     | PUBLIC.only_in_PUBLIC (TABLE)                   | **plan**: Execute getPrimaryKeys(null, PUBLIC, only_in_PUBLIC) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "can find by PrimaryKeyReference with a table name but null primary key name" #

- **connection:** h2[config:standard]

| Permutation | Verified | pkName                                                       | OPERATIONS
| :---------- | :------- | :----------------------------------------------------------- | :------
| c78966      | true     | LBSCHEMA2.4TEST_table.UNNAMED (PRIMARYKEY)                   | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, 4TEST_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 567737      | true     | LBSCHEMA2.4test_table.UNNAMED (PRIMARYKEY)                   | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, 4test_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 74d1b4      | true     | LBSCHEMA2.ANOTHERUPPERTABLE.UNNAMED (PRIMARYKEY)             | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| d2e518      | true     | LBSCHEMA2.AnotherMixedTable.UNNAMED (PRIMARYKEY)             | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, AnotherMixedTable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| bfea69      | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED (PRIMARYKEY) | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| a774c1      | true     | LBSCHEMA2.MixedTable.UNNAMED (PRIMARYKEY)                    | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, MixedTable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| c92495      | true     | LBSCHEMA2.UPPERTABLE.UNNAMED (PRIMARYKEY)                    | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, UPPERTABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 09b3da      | true     | LBSCHEMA2.anotherlowertable.UNNAMED (PRIMARYKEY)             | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, anotherlowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 26feec      | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (PRIMARYKEY) | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| c76d5e      | true     | LBSCHEMA2.lowertable.UNNAMED (PRIMARYKEY)                    | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, lowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| f2fafd      | true     | LBSCHEMA2.only_in_LBSCHEMA2.UNNAMED (PRIMARYKEY)             | **plan**: Execute getPrimaryKeys(null, LBSCHEMA2, only_in_LBSCHEMA2) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 6c63b0      | true     | PUBLIC.4TEST_table.UNNAMED (PRIMARYKEY)                      | **plan**: Execute getPrimaryKeys(null, PUBLIC, 4TEST_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 1be07f      | true     | PUBLIC.4test_table.UNNAMED (PRIMARYKEY)                      | **plan**: Execute getPrimaryKeys(null, PUBLIC, 4test_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 05848c      | true     | PUBLIC.ANOTHERUPPERTABLE.UNNAMED (PRIMARYKEY)                | **plan**: Execute getPrimaryKeys(null, PUBLIC, ANOTHERUPPERTABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| c0fdd1      | true     | PUBLIC.AnotherMixedTable.UNNAMED (PRIMARYKEY)                | **plan**: Execute getPrimaryKeys(null, PUBLIC, AnotherMixedTable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 9cacd1      | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED (PRIMARYKEY)    | **plan**: Execute getPrimaryKeys(null, PUBLIC, CRAZY!@#\$%^&*()_+{}[]'"TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| ba3a9c      | true     | PUBLIC.MixedTable.UNNAMED (PRIMARYKEY)                       | **plan**: Execute getPrimaryKeys(null, PUBLIC, MixedTable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| b6141e      | true     | PUBLIC.UPPERTABLE.UNNAMED (PRIMARYKEY)                       | **plan**: Execute getPrimaryKeys(null, PUBLIC, UPPERTABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 9f2bb3      | true     | PUBLIC.anotherlowertable.UNNAMED (PRIMARYKEY)                | **plan**: Execute getPrimaryKeys(null, PUBLIC, anotherlowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| eb0376      | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (PRIMARYKEY)    | **plan**: Execute getPrimaryKeys(null, PUBLIC, crazy!@#\$%^&*()_+{}[]'"table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 4a3e71      | true     | PUBLIC.lowertable.UNNAMED (PRIMARYKEY)                       | **plan**: Execute getPrimaryKeys(null, PUBLIC, lowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 216764      | true     | PUBLIC.only_in_PUBLIC.UNNAMED (PRIMARYKEY)                   | **plan**: Execute getPrimaryKeys(null, PUBLIC, only_in_PUBLIC) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "can find complex pk names with a table" #

- **connection:** h2[config:standard]

| Permutation | Verified | pkName                                                      | OPERATIONS
| :---------- | :------- | :---------------------------------------------------------- | :------
| 04d404      | true     | KNOWN_TABLE.4TEST_primarykey (PRIMARYKEY)                   | **plan**: Execute getPrimaryKeys(null, null, KNOWN_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| df4382      | true     | KNOWN_TABLE.4test_primarykey (PRIMARYKEY)                   | **plan**: Execute getPrimaryKeys(null, null, KNOWN_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| b98f97      | true     | KNOWN_TABLE.ANOTHERUPPERPRIMARYKEY (PRIMARYKEY)             | **plan**: Execute getPrimaryKeys(null, null, KNOWN_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| c7a70f      | true     | KNOWN_TABLE.AnotherMixedPrimaryKey (PRIMARYKEY)             | **plan**: Execute getPrimaryKeys(null, null, KNOWN_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 3c3463      | true     | KNOWN_TABLE.CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY (PRIMARYKEY) | **plan**: Execute getPrimaryKeys(null, null, KNOWN_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 21f20d      | true     | KNOWN_TABLE.MixedPrimaryKey (PRIMARYKEY)                    | **plan**: Execute getPrimaryKeys(null, null, KNOWN_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| ea5cb5      | true     | KNOWN_TABLE.UPPERPRIMARYKEY (PRIMARYKEY)                    | **plan**: Execute getPrimaryKeys(null, null, KNOWN_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 634d46      | true     | KNOWN_TABLE.anotherlowerprimarykey (PRIMARYKEY)             | **plan**: Execute getPrimaryKeys(null, null, KNOWN_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 98e891      | true     | KNOWN_TABLE.crazy!@#\$%^&*()_+{}[]'"primarykey (PRIMARYKEY) | **plan**: Execute getPrimaryKeys(null, null, KNOWN_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 0409e3      | true     | KNOWN_TABLE.lowerprimarykey (PRIMARYKEY)                    | **plan**: Execute getPrimaryKeys(null, null, KNOWN_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| ea956e      | true     | KNOWN_TABLE.only_in_LBSCHEMA2 (PRIMARYKEY)                  | **plan**: Execute getPrimaryKeys(null, null, KNOWN_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 0ca391      | true     | KNOWN_TABLE.only_in_PUBLIC (PRIMARYKEY)                     | **plan**: Execute getPrimaryKeys(null, null, KNOWN_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test Version: "0cd8ca" #