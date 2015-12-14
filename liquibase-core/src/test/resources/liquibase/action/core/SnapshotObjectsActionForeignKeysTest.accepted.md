**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column FKs correctly" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | schemaName         | OPERATIONS
| :---------- | :------------------- | :----------------- | :------
| 5a2aea      | Unsupported Database | LBSCHEMA (SCHEMA)  | **plan**: getImportedKeys(null, LBSCHEMA, TABLE_NAME)
| 2cb1f9      | Unsupported Database | LBSCHEMA2 (SCHEMA) | **plan**: getImportedKeys(null, LBSCHEMA2, TABLE_NAME)

# Test: "can find all foreignKeys in a fully qualified complex table name" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | tableName                                       | OPERATIONS
| :---------- | :------------------- | :---------------------------------------------- | :------
| f15368      | Unsupported Database | LBSCHEMA.4TEST_table (TABLE)                    | **plan**: getImportedKeys(null, LBSCHEMA, 4TEST_table)
| 71dd23      | Unsupported Database | LBSCHEMA.4test_table (TABLE)                    | **plan**: getImportedKeys(null, LBSCHEMA, 4test_table)
| db5ec7      | Unsupported Database | LBSCHEMA.ANOTHERUPPERTABLE (TABLE)              | **plan**: getImportedKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 23217d      | Unsupported Database | LBSCHEMA.AnotherMixedTable (TABLE)              | **plan**: getImportedKeys(null, LBSCHEMA, AnotherMixedTable)
| 3e74d8      | Unsupported Database | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE)  | **plan**: getImportedKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| d6730a      | Unsupported Database | LBSCHEMA.MixedTable (TABLE)                     | **plan**: getImportedKeys(null, LBSCHEMA, MixedTable)
| 47a1e8      | Unsupported Database | LBSCHEMA.UPPERTABLE (TABLE)                     | **plan**: getImportedKeys(null, LBSCHEMA, UPPERTABLE)
| 95974f      | Unsupported Database | LBSCHEMA.anotherlowertable (TABLE)              | **plan**: getImportedKeys(null, LBSCHEMA, anotherlowertable)
| cdba92      | Unsupported Database | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table (TABLE)  | **plan**: getImportedKeys(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table)
| 20611d      | Unsupported Database | LBSCHEMA.lowertable (TABLE)                     | **plan**: getImportedKeys(null, LBSCHEMA, lowertable)
| b79e72      | Unsupported Database | LBSCHEMA.only_in_LBSCHEMA (TABLE)               | **plan**: getImportedKeys(null, LBSCHEMA, only_in_LBSCHEMA)
| 46f60d      | Unsupported Database | LBSCHEMA2.4TEST_table (TABLE)                   | **plan**: getImportedKeys(null, LBSCHEMA2, 4TEST_table)
| bfc36c      | Unsupported Database | LBSCHEMA2.4test_table (TABLE)                   | **plan**: getImportedKeys(null, LBSCHEMA2, 4test_table)
| 7841f6      | Unsupported Database | LBSCHEMA2.ANOTHERUPPERTABLE (TABLE)             | **plan**: getImportedKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| 3e75c3      | Unsupported Database | LBSCHEMA2.AnotherMixedTable (TABLE)             | **plan**: getImportedKeys(null, LBSCHEMA2, AnotherMixedTable)
| b6d807      | Unsupported Database | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE) | **plan**: getImportedKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 9c23bf      | Unsupported Database | LBSCHEMA2.MixedTable (TABLE)                    | **plan**: getImportedKeys(null, LBSCHEMA2, MixedTable)
| 2267d3      | Unsupported Database | LBSCHEMA2.UPPERTABLE (TABLE)                    | **plan**: getImportedKeys(null, LBSCHEMA2, UPPERTABLE)
| a79e5b      | Unsupported Database | LBSCHEMA2.anotherlowertable (TABLE)             | **plan**: getImportedKeys(null, LBSCHEMA2, anotherlowertable)
| 1ca256      | Unsupported Database | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table (TABLE) | **plan**: getImportedKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| d06325      | Unsupported Database | LBSCHEMA2.lowertable (TABLE)                    | **plan**: getImportedKeys(null, LBSCHEMA2, lowertable)
| 6dc804      | Unsupported Database | LBSCHEMA2.only_in_LBSCHEMA2 (TABLE)             | **plan**: getImportedKeys(null, LBSCHEMA2, only_in_LBSCHEMA2)

# Test: "can find fully qualified complex foreign key names" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | fkName                                                                  | OPERATIONS
| :---------- | :------------------- | :---------------------------------------------------------------------- | :------
| afd4ab      | Unsupported Database | 4TEST_foreignkey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                    | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| f99a5a      | Unsupported Database | 4TEST_foreignkey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                   | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 6608d1      | Unsupported Database | 4test_foreignkey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                    | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 944df7      | Unsupported Database | 4test_foreignkey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                   | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| ed3be1      | Unsupported Database | ANOTHERUPPERFOREIGNKEY on LBSCHEMA.TEST_TABLE (FOREIGNKEY)              | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 971041      | Unsupported Database | ANOTHERUPPERFOREIGNKEY on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)             | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| f6f92a      | Unsupported Database | AnotherMixedForeignKey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)              | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 8b7d12      | Unsupported Database | AnotherMixedForeignKey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)             | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| db1c0d      | Unsupported Database | CRAZY!@#\$%^&*()_+{}[]'"FOREIGNKEY on LBSCHEMA.TEST_TABLE (FOREIGNKEY)  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 71418b      | Unsupported Database | CRAZY!@#\$%^&*()_+{}[]'"FOREIGNKEY on LBSCHEMA2.TEST_TABLE (FOREIGNKEY) | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 81d4fa      | Unsupported Database | MixedForeignKey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                     | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 3b8dfe      | Unsupported Database | MixedForeignKey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                    | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| e0c0ee      | Unsupported Database | UPPERFOREIGNKEY on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                     | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 4685d7      | Unsupported Database | UPPERFOREIGNKEY on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                    | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 2eb86a      | Unsupported Database | anotherlowerforeignkey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)              | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| a3129d      | Unsupported Database | anotherlowerforeignkey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)             | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 3dcc55      | Unsupported Database | crazy!@#\$%^&*()_+{}[]'"foreignkey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 23cb64      | Unsupported Database | crazy!@#\$%^&*()_+{}[]'"foreignkey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY) | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 65ba38      | Unsupported Database | lowerforeignkey on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                     | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| b1949e      | Unsupported Database | lowerforeignkey on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                    | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 3dc6dd      | Unsupported Database | only_in_LBSCHEMA on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                    | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 15548e      | Unsupported Database | only_in_LBSCHEMA on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                   | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| c35f2a      | Unsupported Database | only_in_LBSCHEMA2 on LBSCHEMA.TEST_TABLE (FOREIGNKEY)                   | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 9ee6b8      | Unsupported Database | only_in_LBSCHEMA2 on LBSCHEMA2.TEST_TABLE (FOREIGNKEY)                  | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)

# Test Version: "a0a37" #