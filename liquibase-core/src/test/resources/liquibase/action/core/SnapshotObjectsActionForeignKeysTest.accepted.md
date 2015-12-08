**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

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

# Test: "can find all foreignKeys in a schema" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | schemaName         | OPERATIONS
| :---------- | :------------------- | :----------------- | :------
| 5a2aea      | Unsupported Database | LBSCHEMA (SCHEMA)  | **plan**: getImportedKeys(null, LBSCHEMA, null)
| 2cb1f9      | Unsupported Database | LBSCHEMA2 (SCHEMA) | **plan**: getImportedKeys(null, LBSCHEMA2, null)

# Test: "can find fully qualified complex foreign key names" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | fkName                                                            | OPERATIONS
| :---------- | :------------------- | :---------------------------------------------------------------- | :------
| aed6c4      | Unsupported Database | LBSCHEMA.UNNAMED.4TEST_foreignkey (FOREIGNKEY)                    | **plan**: getImportedKeys(null, LBSCHEMA, null)
| 9eea48      | Unsupported Database | LBSCHEMA.UNNAMED.4test_foreignkey (FOREIGNKEY)                    | **plan**: getImportedKeys(null, LBSCHEMA, null)
| f256a4      | Unsupported Database | LBSCHEMA.UNNAMED.ANOTHERUPPERFOREIGNKEY (FOREIGNKEY)              | **plan**: getImportedKeys(null, LBSCHEMA, null)
| 6868cd      | Unsupported Database | LBSCHEMA.UNNAMED.AnotherMixedForeignKey (FOREIGNKEY)              | **plan**: getImportedKeys(null, LBSCHEMA, null)
| b5363a      | Unsupported Database | LBSCHEMA.UNNAMED.CRAZY!@#\$%^&*()_+{}[]'"FOREIGNKEY (FOREIGNKEY)  | **plan**: getImportedKeys(null, LBSCHEMA, null)
| fcbd86      | Unsupported Database | LBSCHEMA.UNNAMED.MixedForeignKey (FOREIGNKEY)                     | **plan**: getImportedKeys(null, LBSCHEMA, null)
| 46d7a0      | Unsupported Database | LBSCHEMA.UNNAMED.UPPERFOREIGNKEY (FOREIGNKEY)                     | **plan**: getImportedKeys(null, LBSCHEMA, null)
| fc5c51      | Unsupported Database | LBSCHEMA.UNNAMED.anotherlowerforeignkey (FOREIGNKEY)              | **plan**: getImportedKeys(null, LBSCHEMA, null)
| 7e9e77      | Unsupported Database | LBSCHEMA.UNNAMED.crazy!@#\$%^&*()_+{}[]'"foreignkey (FOREIGNKEY)  | **plan**: getImportedKeys(null, LBSCHEMA, null)
| 7a3c71      | Unsupported Database | LBSCHEMA.UNNAMED.lowerforeignkey (FOREIGNKEY)                     | **plan**: getImportedKeys(null, LBSCHEMA, null)
| b21b54      | Unsupported Database | LBSCHEMA.UNNAMED.only_in_LBSCHEMA (FOREIGNKEY)                    | **plan**: getImportedKeys(null, LBSCHEMA, null)
| cd9a11      | Unsupported Database | LBSCHEMA2.UNNAMED.4TEST_foreignkey (FOREIGNKEY)                   | **plan**: getImportedKeys(null, LBSCHEMA2, null)
| f5d590      | Unsupported Database | LBSCHEMA2.UNNAMED.4test_foreignkey (FOREIGNKEY)                   | **plan**: getImportedKeys(null, LBSCHEMA2, null)
| 7af7e2      | Unsupported Database | LBSCHEMA2.UNNAMED.ANOTHERUPPERFOREIGNKEY (FOREIGNKEY)             | **plan**: getImportedKeys(null, LBSCHEMA2, null)
| d02cb5      | Unsupported Database | LBSCHEMA2.UNNAMED.AnotherMixedForeignKey (FOREIGNKEY)             | **plan**: getImportedKeys(null, LBSCHEMA2, null)
| a708ec      | Unsupported Database | LBSCHEMA2.UNNAMED.CRAZY!@#\$%^&*()_+{}[]'"FOREIGNKEY (FOREIGNKEY) | **plan**: getImportedKeys(null, LBSCHEMA2, null)
| 6c10d4      | Unsupported Database | LBSCHEMA2.UNNAMED.MixedForeignKey (FOREIGNKEY)                    | **plan**: getImportedKeys(null, LBSCHEMA2, null)
| ef32d8      | Unsupported Database | LBSCHEMA2.UNNAMED.UPPERFOREIGNKEY (FOREIGNKEY)                    | **plan**: getImportedKeys(null, LBSCHEMA2, null)
| 998a6c      | Unsupported Database | LBSCHEMA2.UNNAMED.anotherlowerforeignkey (FOREIGNKEY)             | **plan**: getImportedKeys(null, LBSCHEMA2, null)
| 702f22      | Unsupported Database | LBSCHEMA2.UNNAMED.crazy!@#\$%^&*()_+{}[]'"foreignkey (FOREIGNKEY) | **plan**: getImportedKeys(null, LBSCHEMA2, null)
| 044214      | Unsupported Database | LBSCHEMA2.UNNAMED.lowerforeignkey (FOREIGNKEY)                    | **plan**: getImportedKeys(null, LBSCHEMA2, null)
| e4b27f      | Unsupported Database | LBSCHEMA2.UNNAMED.only_in_LBSCHEMA2 (FOREIGNKEY)                  | **plan**: getImportedKeys(null, LBSCHEMA2, null)

# Test Version: "f199e2" #