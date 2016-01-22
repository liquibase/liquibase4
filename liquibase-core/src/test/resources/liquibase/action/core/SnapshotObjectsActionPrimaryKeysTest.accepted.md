**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column PKs correctly" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | schemaName         | OPERATIONS
| :---------- | :------------------- | :----------------- | :------
| 5a2aea      | Unsupported Database | LBSCHEMA (SCHEMA)  | **plan**: getPrimaryKeys(null, LBSCHEMA, TABLE_NAME)
| 2cb1f9      | Unsupported Database | LBSCHEMA2 (SCHEMA) | **plan**: getPrimaryKeys(null, LBSCHEMA2, TABLE_NAME)

# Test: "Snapshots column direction correctly" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | schemaName        | OPERATIONS
| :---------- | :------------------- | :---------------- | :------
| 5a2aea      | Unsupported Database | LBSCHEMA (SCHEMA) | **plan**: getPrimaryKeys(null, LBSCHEMA, TABLE1)

# Test: "can find all primaryKeys in a fully qualified complex table name" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | table                                       | OPERATIONS
| :---------- | :------------------- | :---------------------------------------------- | :------
| f15368      | Unsupported Database | LBSCHEMA.4TEST_table (TABLE)                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4TEST_table)
| 71dd23      | Unsupported Database | LBSCHEMA.4test_table (TABLE)                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4test_table)
| db5ec7      | Unsupported Database | LBSCHEMA.ANOTHERUPPERTABLE (TABLE)              | **plan**: getPrimaryKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 23217d      | Unsupported Database | LBSCHEMA.AnotherMixedTable (TABLE)              | **plan**: getPrimaryKeys(null, LBSCHEMA, AnotherMixedTable)
| 3e74d8      | Unsupported Database | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE)  | **plan**: getPrimaryKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| d6730a      | Unsupported Database | LBSCHEMA.MixedTable (TABLE)                     | **plan**: getPrimaryKeys(null, LBSCHEMA, MixedTable)
| 47a1e8      | Unsupported Database | LBSCHEMA.UPPERTABLE (TABLE)                     | **plan**: getPrimaryKeys(null, LBSCHEMA, UPPERTABLE)
| 95974f      | Unsupported Database | LBSCHEMA.anotherlowertable (TABLE)              | **plan**: getPrimaryKeys(null, LBSCHEMA, anotherlowertable)
| cdba92      | Unsupported Database | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table (TABLE)  | **plan**: getPrimaryKeys(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table)
| 20611d      | Unsupported Database | LBSCHEMA.lowertable (TABLE)                     | **plan**: getPrimaryKeys(null, LBSCHEMA, lowertable)
| b79e72      | Unsupported Database | LBSCHEMA.only_in_LBSCHEMA (TABLE)               | **plan**: getPrimaryKeys(null, LBSCHEMA, only_in_LBSCHEMA)
| 46f60d      | Unsupported Database | LBSCHEMA2.4TEST_table (TABLE)                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4TEST_table)
| bfc36c      | Unsupported Database | LBSCHEMA2.4test_table (TABLE)                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4test_table)
| 7841f6      | Unsupported Database | LBSCHEMA2.ANOTHERUPPERTABLE (TABLE)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| 3e75c3      | Unsupported Database | LBSCHEMA2.AnotherMixedTable (TABLE)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, AnotherMixedTable)
| b6d807      | Unsupported Database | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE) | **plan**: getPrimaryKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 9c23bf      | Unsupported Database | LBSCHEMA2.MixedTable (TABLE)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, MixedTable)
| 2267d3      | Unsupported Database | LBSCHEMA2.UPPERTABLE (TABLE)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, UPPERTABLE)
| a79e5b      | Unsupported Database | LBSCHEMA2.anotherlowertable (TABLE)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, anotherlowertable)
| 1ca256      | Unsupported Database | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table (TABLE) | **plan**: getPrimaryKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| d06325      | Unsupported Database | LBSCHEMA2.lowertable (TABLE)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, lowertable)
| 6dc804      | Unsupported Database | LBSCHEMA2.only_in_LBSCHEMA2 (TABLE)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, only_in_LBSCHEMA2)

# Test: "can find by PrimaryKeyReference with a table name but null primary key name" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | pkName                                                       | OPERATIONS
| :---------- | :------------------- | :----------------------------------------------------------- | :------
| a33344      | Unsupported Database | LBSCHEMA.4TEST_table.UNNAMED (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4TEST_table)
| 9d28f6      | Unsupported Database | LBSCHEMA.4test_table.UNNAMED (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4test_table)
| e2d0f4      | Unsupported Database | LBSCHEMA.ANOTHERUPPERTABLE.UNNAMED (PRIMARYKEY)              | **plan**: getPrimaryKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 8fb775      | Unsupported Database | LBSCHEMA.AnotherMixedTable.UNNAMED (PRIMARYKEY)              | **plan**: getPrimaryKeys(null, LBSCHEMA, AnotherMixedTable)
| c7d37f      | Unsupported Database | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED (PRIMARYKEY)  | **plan**: getPrimaryKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 586d9c      | Unsupported Database | LBSCHEMA.MixedTable.UNNAMED (PRIMARYKEY)                     | **plan**: getPrimaryKeys(null, LBSCHEMA, MixedTable)
| 3ae49c      | Unsupported Database | LBSCHEMA.UPPERTABLE.UNNAMED (PRIMARYKEY)                     | **plan**: getPrimaryKeys(null, LBSCHEMA, UPPERTABLE)
| f88b9b      | Unsupported Database | LBSCHEMA.anotherlowertable.UNNAMED (PRIMARYKEY)              | **plan**: getPrimaryKeys(null, LBSCHEMA, anotherlowertable)
| 9f41ab      | Unsupported Database | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (PRIMARYKEY)  | **plan**: getPrimaryKeys(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table)
| 9ff6e1      | Unsupported Database | LBSCHEMA.lowertable.UNNAMED (PRIMARYKEY)                     | **plan**: getPrimaryKeys(null, LBSCHEMA, lowertable)
| cc155a      | Unsupported Database | LBSCHEMA.only_in_LBSCHEMA.UNNAMED (PRIMARYKEY)               | **plan**: getPrimaryKeys(null, LBSCHEMA, only_in_LBSCHEMA)
| 7663dc      | Unsupported Database | LBSCHEMA2.4TEST_table.UNNAMED (PRIMARYKEY)                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4TEST_table)
| 5a069d      | Unsupported Database | LBSCHEMA2.4test_table.UNNAMED (PRIMARYKEY)                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4test_table)
| 18fc76      | Unsupported Database | LBSCHEMA2.ANOTHERUPPERTABLE.UNNAMED (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| 7b02cc      | Unsupported Database | LBSCHEMA2.AnotherMixedTable.UNNAMED (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, AnotherMixedTable)
| 21278f      | Unsupported Database | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED (PRIMARYKEY) | **plan**: getPrimaryKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| b0c1c1      | Unsupported Database | LBSCHEMA2.MixedTable.UNNAMED (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, MixedTable)
| cdaff1      | Unsupported Database | LBSCHEMA2.UPPERTABLE.UNNAMED (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, UPPERTABLE)
| 1e9fd6      | Unsupported Database | LBSCHEMA2.anotherlowertable.UNNAMED (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, anotherlowertable)
| 7a74fb      | Unsupported Database | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (PRIMARYKEY) | **plan**: getPrimaryKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| 8c7e76      | Unsupported Database | LBSCHEMA2.lowertable.UNNAMED (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, lowertable)
| 408f74      | Unsupported Database | LBSCHEMA2.only_in_LBSCHEMA2.UNNAMED (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, LBSCHEMA2, only_in_LBSCHEMA2)

# Test: "can find complex pk names with a table" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | pkName                                                      | OPERATIONS
| :---------- | :------------------- | :---------------------------------------------------------- | :------
| 90f642      | Unsupported Database | KNOWN_TABLE.4TEST_primarykey (PRIMARYKEY)                   | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| ba3763      | Unsupported Database | KNOWN_TABLE.4test_primarykey (PRIMARYKEY)                   | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 70cd8b      | Unsupported Database | KNOWN_TABLE.ANOTHERUPPERPRIMARYKEY (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 808053      | Unsupported Database | KNOWN_TABLE.AnotherMixedPrimaryKey (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 49f8c1      | Unsupported Database | KNOWN_TABLE.CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY (PRIMARYKEY) | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 8d7a9d      | Unsupported Database | KNOWN_TABLE.MixedPrimaryKey (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 166ef9      | Unsupported Database | KNOWN_TABLE.UPPERPRIMARYKEY (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 567c02      | Unsupported Database | KNOWN_TABLE.anotherlowerprimarykey (PRIMARYKEY)             | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 00047c      | Unsupported Database | KNOWN_TABLE.crazy!@#\$%^&*()_+{}[]'"primarykey (PRIMARYKEY) | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| 22eb0e      | Unsupported Database | KNOWN_TABLE.lowerprimarykey (PRIMARYKEY)                    | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| ef0e48      | Unsupported Database | KNOWN_TABLE.only_in_LBSCHEMA (PRIMARYKEY)                   | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)
| b1ec2e      | Unsupported Database | KNOWN_TABLE.only_in_LBSCHEMA2 (PRIMARYKEY)                  | **plan**: getPrimaryKeys(null, null, KNOWN_TABLE)

# Test Version: "a7f54e" #