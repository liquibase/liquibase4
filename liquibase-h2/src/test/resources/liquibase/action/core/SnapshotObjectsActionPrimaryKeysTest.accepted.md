**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column PKs correctly" #

- **connection:** h2[config:standard]

| Permutation | Verified | schemaName         | OPERATIONS
| :---------- | :------- | :----------------- | :------
| ffe0a3      | true     | LBSCHEMA2 (SCHEMA) | **plan**: getPrimaryKeys(null, LBSCHEMA2, TABLE_NAME)
| 31a9a7      | true     | PUBLIC (SCHEMA)    | **plan**: getPrimaryKeys(null, PUBLIC, TABLE_NAME)

# Test: "can find all primaryKeys in a fully qualified complex table name" #

- **connection:** h2[config:standard]

| Permutation | Verified | tableName                                       | OPERATIONS
| :---------- | :------- | :---------------------------------------------- | :------
| 1a7c3e      | true     | LBSCHEMA2.4TEST_table (TABLE)                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4TEST_table)
| 782dc1      | true     | LBSCHEMA2.4test_table (TABLE)                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4test_table)
| 76fd15      | true     | LBSCHEMA2.ANOTHERUPPERTABLE (TABLE)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| a32fc7      | true     | LBSCHEMA2.AnotherMixedTable (TABLE)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, AnotherMixedTable)
| 090453      | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE) | **plan**: getPrimaryKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 892700      | true     | LBSCHEMA2.MixedTable (TABLE)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, MixedTable)
| 80fd36      | true     | LBSCHEMA2.UPPERTABLE (TABLE)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, UPPERTABLE)
| 50f117      | true     | LBSCHEMA2.anotherlowertable (TABLE)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, anotherlowertable)
| c0f50e      | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table (TABLE) | **plan**: getPrimaryKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| 1a03ea      | true     | LBSCHEMA2.lowertable (TABLE)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, lowertable)
| de4ba2      | true     | LBSCHEMA2.only_in_LBSCHEMA2 (TABLE)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, only_in_LBSCHEMA2)
| 1fc527      | true     | PUBLIC.4TEST_table (TABLE)                      | **plan**: getPrimaryKeys(null, PUBLIC, 4TEST_table)
| d0ffe1      | true     | PUBLIC.4test_table (TABLE)                      | **plan**: getPrimaryKeys(null, PUBLIC, 4test_table)
| ae9df0      | true     | PUBLIC.ANOTHERUPPERTABLE (TABLE)                | **plan**: getPrimaryKeys(null, PUBLIC, ANOTHERUPPERTABLE)
| 825558      | true     | PUBLIC.AnotherMixedTable (TABLE)                | **plan**: getPrimaryKeys(null, PUBLIC, AnotherMixedTable)
| 0f856b      | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE)    | **plan**: getPrimaryKeys(null, PUBLIC, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| e8e0c1      | true     | PUBLIC.MixedTable (TABLE)                       | **plan**: getPrimaryKeys(null, PUBLIC, MixedTable)
| 8bfcdb      | true     | PUBLIC.UPPERTABLE (TABLE)                       | **plan**: getPrimaryKeys(null, PUBLIC, UPPERTABLE)
| a611b8      | true     | PUBLIC.anotherlowertable (TABLE)                | **plan**: getPrimaryKeys(null, PUBLIC, anotherlowertable)
| 8948bb      | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table (TABLE)    | **plan**: getPrimaryKeys(null, PUBLIC, crazy!@#\$%^&*()_+{}[]'"table)
| 38b9af      | true     | PUBLIC.lowertable (TABLE)                       | **plan**: getPrimaryKeys(null, PUBLIC, lowertable)
| 97fc83      | true     | PUBLIC.only_in_PUBLIC (TABLE)                   | **plan**: getPrimaryKeys(null, PUBLIC, only_in_PUBLIC)

# Test: "can find by PrimaryKeyReference with a table name but null primary key name" #

- **connection:** h2[config:standard]

| Permutation | Verified | pkName                                                       | OPERATIONS
| :---------- | :------- | :----------------------------------------------------------- | :------
| c78966      | true     | LBSCHEMA2.4TEST_table.UNNAMED (PRIMARYKEY)                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4TEST_table)
| 567737      | true     | LBSCHEMA2.4test_table.UNNAMED (PRIMARYKEY)                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4test_table)
| 74d1b4      | true     | LBSCHEMA2.ANOTHERUPPERTABLE.UNNAMED (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| d2e518      | true     | LBSCHEMA2.AnotherMixedTable.UNNAMED (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, AnotherMixedTable)
| bfea69      | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED (PRIMARYKEY) | **plan**: getPrimaryKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| a774c1      | true     | LBSCHEMA2.MixedTable.UNNAMED (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, MixedTable)
| c92495      | true     | LBSCHEMA2.UPPERTABLE.UNNAMED (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, UPPERTABLE)
| 09b3da      | true     | LBSCHEMA2.anotherlowertable.UNNAMED (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, anotherlowertable)
| 26feec      | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (PRIMARYKEY) | **plan**: getPrimaryKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| c76d5e      | true     | LBSCHEMA2.lowertable.UNNAMED (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, lowertable)
| f2fafd      | true     | LBSCHEMA2.only_in_LBSCHEMA2.UNNAMED (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, only_in_LBSCHEMA2)
| 6c63b0      | true     | PUBLIC.4TEST_table.UNNAMED (PRIMARYKEY)                      | **plan**: getPrimaryKeys(null, PUBLIC, 4TEST_table)
| 1be07f      | true     | PUBLIC.4test_table.UNNAMED (PRIMARYKEY)                      | **plan**: getPrimaryKeys(null, PUBLIC, 4test_table)
| 05848c      | true     | PUBLIC.ANOTHERUPPERTABLE.UNNAMED (PRIMARYKEY)                | **plan**: getPrimaryKeys(null, PUBLIC, ANOTHERUPPERTABLE)
| c0fdd1      | true     | PUBLIC.AnotherMixedTable.UNNAMED (PRIMARYKEY)                | **plan**: getPrimaryKeys(null, PUBLIC, AnotherMixedTable)
| 9cacd1      | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED (PRIMARYKEY)    | **plan**: getPrimaryKeys(null, PUBLIC, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| ba3a9c      | true     | PUBLIC.MixedTable.UNNAMED (PRIMARYKEY)                       | **plan**: getPrimaryKeys(null, PUBLIC, MixedTable)
| b6141e      | true     | PUBLIC.UPPERTABLE.UNNAMED (PRIMARYKEY)                       | **plan**: getPrimaryKeys(null, PUBLIC, UPPERTABLE)
| 9f2bb3      | true     | PUBLIC.anotherlowertable.UNNAMED (PRIMARYKEY)                | **plan**: getPrimaryKeys(null, PUBLIC, anotherlowertable)
| eb0376      | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (PRIMARYKEY)    | **plan**: getPrimaryKeys(null, PUBLIC, crazy!@#\$%^&*()_+{}[]'"table)
| 4a3e71      | true     | PUBLIC.lowertable.UNNAMED (PRIMARYKEY)                       | **plan**: getPrimaryKeys(null, PUBLIC, lowertable)
| 216764      | true     | PUBLIC.only_in_PUBLIC.UNNAMED (PRIMARYKEY)                   | **plan**: getPrimaryKeys(null, PUBLIC, only_in_PUBLIC)

# Test: "can find complex pk names with a table" #

- **connection:** h2[config:standard]

| Permutation | Verified | pkName                                                      | OPERATIONS
| :---------- | :------- | :---------------------------------------------------------- | :------
| 04d404      | true     | KNOWN_TABLE.4TEST_primarykey (PRIMARYKEY)                   | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| df4382      | true     | KNOWN_TABLE.4test_primarykey (PRIMARYKEY)                   | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| b98f97      | true     | KNOWN_TABLE.ANOTHERUPPERPRIMARYKEY (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| c7a70f      | true     | KNOWN_TABLE.AnotherMixedPrimaryKey (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 3c3463      | true     | KNOWN_TABLE.CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY (PRIMARYKEY) | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 21f20d      | true     | KNOWN_TABLE.MixedPrimaryKey (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| ea5cb5      | true     | KNOWN_TABLE.UPPERPRIMARYKEY (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 634d46      | true     | KNOWN_TABLE.anotherlowerprimarykey (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 98e891      | true     | KNOWN_TABLE.crazy!@#\$%^&*()_+{}[]'"primarykey (PRIMARYKEY) | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 0409e3      | true     | KNOWN_TABLE.lowerprimarykey (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| ea956e      | true     | KNOWN_TABLE.only_in_LBSCHEMA2 (PRIMARYKEY)                  | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 0ca391      | true     | KNOWN_TABLE.only_in_PUBLIC (PRIMARYKEY)                     | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)

# Test Version: "5ee9c8" #