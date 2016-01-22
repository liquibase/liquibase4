**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column FKs correctly" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | schemaName         | OPERATIONS
| :---------- | :------------------- | :----------------- | :------
| 5a2aea      | Unsupported Database | LBSCHEMA (SCHEMA)  | **plan**: Execute getImportedKeys(null, LBSCHEMA, TABLE_NAME) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 2cb1f9      | Unsupported Database | LBSCHEMA2 (SCHEMA) | **plan**: Execute getImportedKeys(null, LBSCHEMA2, TABLE_NAME) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "can find all foreignKeys in a fully qualified complex table name" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | table                                       | OPERATIONS
| :---------- | :------------------- | :---------------------------------------------- | :------
| f15368      | Unsupported Database | LBSCHEMA.4TEST_table (TABLE)                    | **plan**: Execute getImportedKeys(null, LBSCHEMA, 4TEST_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 71dd23      | Unsupported Database | LBSCHEMA.4test_table (TABLE)                    | **plan**: Execute getImportedKeys(null, LBSCHEMA, 4test_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| db5ec7      | Unsupported Database | LBSCHEMA.ANOTHERUPPERTABLE (TABLE)              | **plan**: Execute getImportedKeys(null, LBSCHEMA, ANOTHERUPPERTABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 23217d      | Unsupported Database | LBSCHEMA.AnotherMixedTable (TABLE)              | **plan**: Execute getImportedKeys(null, LBSCHEMA, AnotherMixedTable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 3e74d8      | Unsupported Database | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE)  | **plan**: Execute getImportedKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| d6730a      | Unsupported Database | LBSCHEMA.MixedTable (TABLE)                     | **plan**: Execute getImportedKeys(null, LBSCHEMA, MixedTable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 47a1e8      | Unsupported Database | LBSCHEMA.UPPERTABLE (TABLE)                     | **plan**: Execute getImportedKeys(null, LBSCHEMA, UPPERTABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 95974f      | Unsupported Database | LBSCHEMA.anotherlowertable (TABLE)              | **plan**: Execute getImportedKeys(null, LBSCHEMA, anotherlowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| cdba92      | Unsupported Database | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table (TABLE)  | **plan**: Execute getImportedKeys(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 20611d      | Unsupported Database | LBSCHEMA.lowertable (TABLE)                     | **plan**: Execute getImportedKeys(null, LBSCHEMA, lowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| b79e72      | Unsupported Database | LBSCHEMA.only_in_LBSCHEMA (TABLE)               | **plan**: Execute getImportedKeys(null, LBSCHEMA, only_in_LBSCHEMA) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 46f60d      | Unsupported Database | LBSCHEMA2.4TEST_table (TABLE)                   | **plan**: Execute getImportedKeys(null, LBSCHEMA2, 4TEST_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| bfc36c      | Unsupported Database | LBSCHEMA2.4test_table (TABLE)                   | **plan**: Execute getImportedKeys(null, LBSCHEMA2, 4test_table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 7841f6      | Unsupported Database | LBSCHEMA2.ANOTHERUPPERTABLE (TABLE)             | **plan**: Execute getImportedKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 3e75c3      | Unsupported Database | LBSCHEMA2.AnotherMixedTable (TABLE)             | **plan**: Execute getImportedKeys(null, LBSCHEMA2, AnotherMixedTable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| b6d807      | Unsupported Database | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE) | **plan**: Execute getImportedKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 9c23bf      | Unsupported Database | LBSCHEMA2.MixedTable (TABLE)                    | **plan**: Execute getImportedKeys(null, LBSCHEMA2, MixedTable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 2267d3      | Unsupported Database | LBSCHEMA2.UPPERTABLE (TABLE)                    | **plan**: Execute getImportedKeys(null, LBSCHEMA2, UPPERTABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| a79e5b      | Unsupported Database | LBSCHEMA2.anotherlowertable (TABLE)             | **plan**: Execute getImportedKeys(null, LBSCHEMA2, anotherlowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 1ca256      | Unsupported Database | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table (TABLE) | **plan**: Execute getImportedKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| d06325      | Unsupported Database | LBSCHEMA2.lowertable (TABLE)                    | **plan**: Execute getImportedKeys(null, LBSCHEMA2, lowertable) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 6dc804      | Unsupported Database | LBSCHEMA2.only_in_LBSCHEMA2 (TABLE)             | **plan**: Execute getImportedKeys(null, LBSCHEMA2, only_in_LBSCHEMA2) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "can find fully qualified complex foreign key names" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | fkName                                                                  | OPERATIONS
| :---------- | :------------------- | :---------------------------------------------------------------------- | :------
| afd4ab      | Unsupported Database | 4TEST_foreignkey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                    | **plan**: Execute getImportedKeys(null, LBSCHEMA, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| f99a5a      | Unsupported Database | 4TEST_foreignkey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                   | **plan**: Execute getImportedKeys(null, LBSCHEMA2, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 6608d1      | Unsupported Database | 4test_foreignkey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                    | **plan**: Execute getImportedKeys(null, LBSCHEMA, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 944df7      | Unsupported Database | 4test_foreignkey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                   | **plan**: Execute getImportedKeys(null, LBSCHEMA2, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| ed3be1      | Unsupported Database | ANOTHERUPPERFOREIGNKEY on LBSCHEMA.TEST_TABLE (FOREIGNKEY)              | **plan**: Execute getImportedKeys(null, LBSCHEMA, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 971041      | Unsupported Database | ANOTHERUPPERFOREIGNKEY on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)             | **plan**: Execute getImportedKeys(null, LBSCHEMA2, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| f6f92a      | Unsupported Database | AnotherMixedForeignKey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)              | **plan**: Execute getImportedKeys(null, LBSCHEMA, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 8b7d12      | Unsupported Database | AnotherMixedForeignKey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)             | **plan**: Execute getImportedKeys(null, LBSCHEMA2, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| db1c0d      | Unsupported Database | CRAZY!@#\$%^&*()_+{}[]'"FOREIGNKEY on LBSCHEMA.TEST_TABLE (FOREIGNKEY)  | **plan**: Execute getImportedKeys(null, LBSCHEMA, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 71418b      | Unsupported Database | CRAZY!@#\$%^&*()_+{}[]'"FOREIGNKEY on LBSCHEMA2.TEST_TABLE (FOREIGNKEY) | **plan**: Execute getImportedKeys(null, LBSCHEMA2, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 81d4fa      | Unsupported Database | MixedForeignKey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                     | **plan**: Execute getImportedKeys(null, LBSCHEMA, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 3b8dfe      | Unsupported Database | MixedForeignKey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                    | **plan**: Execute getImportedKeys(null, LBSCHEMA2, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| e0c0ee      | Unsupported Database | UPPERFOREIGNKEY on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                     | **plan**: Execute getImportedKeys(null, LBSCHEMA, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 4685d7      | Unsupported Database | UPPERFOREIGNKEY on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                    | **plan**: Execute getImportedKeys(null, LBSCHEMA2, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 2eb86a      | Unsupported Database | anotherlowerforeignkey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)              | **plan**: Execute getImportedKeys(null, LBSCHEMA, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| a3129d      | Unsupported Database | anotherlowerforeignkey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)             | **plan**: Execute getImportedKeys(null, LBSCHEMA2, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 3dcc55      | Unsupported Database | crazy!@#\$%^&*()_+{}[]'"foreignkey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)  | **plan**: Execute getImportedKeys(null, LBSCHEMA, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 23cb64      | Unsupported Database | crazy!@#\$%^&*()_+{}[]'"foreignkey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY) | **plan**: Execute getImportedKeys(null, LBSCHEMA2, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 65ba38      | Unsupported Database | lowerforeignkey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                     | **plan**: Execute getImportedKeys(null, LBSCHEMA, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| b1949e      | Unsupported Database | lowerforeignkey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                    | **plan**: Execute getImportedKeys(null, LBSCHEMA2, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 3dc6dd      | Unsupported Database | only_in_LBSCHEMA on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                    | **plan**: Execute getImportedKeys(null, LBSCHEMA, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 15548e      | Unsupported Database | only_in_LBSCHEMA on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                   | **plan**: Execute getImportedKeys(null, LBSCHEMA2, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| c35f2a      | Unsupported Database | only_in_LBSCHEMA2 on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                   | **plan**: Execute getImportedKeys(null, LBSCHEMA, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic
| 9ee6b8      | Unsupported Database | only_in_LBSCHEMA2 on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                  | **plan**: Execute getImportedKeys(null, LBSCHEMA2, TEST_TABLE) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test Version: "143b7a" #