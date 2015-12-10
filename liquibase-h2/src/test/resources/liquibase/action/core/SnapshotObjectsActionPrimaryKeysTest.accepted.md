**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

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

| Permutation | Verified | pkName                                                                | OPERATIONS
| :---------- | :------- | :-------------------------------------------------------------------- | :------
| c64e36      | true     | LBSCHEMA2.KNOWN_TABLE.4TEST_primarykey (PRIMARYKEY)                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 8cd258      | true     | LBSCHEMA2.KNOWN_TABLE.4test_primarykey (PRIMARYKEY)                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 3fff04      | true     | LBSCHEMA2.KNOWN_TABLE.ANOTHERUPPERPRIMARYKEY (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 05f551      | true     | LBSCHEMA2.KNOWN_TABLE.AnotherMixedPrimaryKey (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 966ece      | true     | LBSCHEMA2.KNOWN_TABLE.CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY (PRIMARYKEY) | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 20e994      | true     | LBSCHEMA2.KNOWN_TABLE.MixedPrimaryKey (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| ec4a7b      | true     | LBSCHEMA2.KNOWN_TABLE.UPPERPRIMARYKEY (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 5fd99b      | true     | LBSCHEMA2.KNOWN_TABLE.anotherlowerprimarykey (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 943fa7      | true     | LBSCHEMA2.KNOWN_TABLE.crazy!@#\$%^&*()_+{}[]'"primarykey (PRIMARYKEY) | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| a48c18      | true     | LBSCHEMA2.KNOWN_TABLE.lowerprimarykey (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| c80a0e      | true     | LBSCHEMA2.KNOWN_TABLE.only_in_LBSCHEMA2 (PRIMARYKEY)                  | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 6364dd      | true     | PUBLIC.KNOWN_TABLE.4TEST_primarykey (PRIMARYKEY)                      | **plan**: getPrimaryKeys(null, PUBLIC, KNOWN_TABLE)
| 8d6e2c      | true     | PUBLIC.KNOWN_TABLE.4test_primarykey (PRIMARYKEY)                      | **plan**: getPrimaryKeys(null, PUBLIC, KNOWN_TABLE)
| 178b9f      | true     | PUBLIC.KNOWN_TABLE.ANOTHERUPPERPRIMARYKEY (PRIMARYKEY)                | **plan**: getPrimaryKeys(null, PUBLIC, KNOWN_TABLE)
| b68510      | true     | PUBLIC.KNOWN_TABLE.AnotherMixedPrimaryKey (PRIMARYKEY)                | **plan**: getPrimaryKeys(null, PUBLIC, KNOWN_TABLE)
| 3344ed      | true     | PUBLIC.KNOWN_TABLE.CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY (PRIMARYKEY)    | **plan**: getPrimaryKeys(null, PUBLIC, KNOWN_TABLE)
| 6990ac      | true     | PUBLIC.KNOWN_TABLE.MixedPrimaryKey (PRIMARYKEY)                       | **plan**: getPrimaryKeys(null, PUBLIC, KNOWN_TABLE)
| 14a862      | true     | PUBLIC.KNOWN_TABLE.UPPERPRIMARYKEY (PRIMARYKEY)                       | **plan**: getPrimaryKeys(null, PUBLIC, KNOWN_TABLE)
| 46178f      | true     | PUBLIC.KNOWN_TABLE.anotherlowerprimarykey (PRIMARYKEY)                | **plan**: getPrimaryKeys(null, PUBLIC, KNOWN_TABLE)
| a58468      | true     | PUBLIC.KNOWN_TABLE.crazy!@#\$%^&*()_+{}[]'"primarykey (PRIMARYKEY)    | **plan**: getPrimaryKeys(null, PUBLIC, KNOWN_TABLE)
| 489f23      | true     | PUBLIC.KNOWN_TABLE.lowerprimarykey (PRIMARYKEY)                       | **plan**: getPrimaryKeys(null, PUBLIC, KNOWN_TABLE)
| d8b4ed      | true     | PUBLIC.KNOWN_TABLE.only_in_PUBLIC (PRIMARYKEY)                        | **plan**: getPrimaryKeys(null, PUBLIC, KNOWN_TABLE)

# Test Version: "e86ce9" #