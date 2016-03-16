**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column indexes correctly" #

- **connection:** generic standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 6e6350      | Generic  | LBSCHEMA  | **plan**: getIndexInfo(null, LBSCHEMA, TABLE_NAME, false, true)
| bbb8e6      | Generic  | LBSCHEMA2 | **plan**: getIndexInfo(null, LBSCHEMA2, TABLE_NAME, false, true)

# Test: "Snapshots column direction correctly" #

- **connection:** generic standard

| Permutation | Verified | schema   | OPERATIONS
| :---------- | :------- | :------- | :------
| 6e6350      | Generic  | LBSCHEMA | **plan**: getIndexInfo(null, LBSCHEMA, TABLE1, false, true)

# Test: "Snapshots multi-column primary key correctly" #

- **connection:** generic standard

| Permutation | Verified | primaryKey                 | OPERATIONS
| :---------- | :------- | :------------------------- | :------
| dd31dc      | Generic  | UNNAMED on LBSCHEMA.TABLE2 | **plan**: getIndexInfo(null, LBSCHEMA, TABLE2, false, true)<br>getPrimaryKeys(null, LBSCHEMA, TABLE2)

# Test: "Snapshots single-column primary key correctly" #

- **connection:** generic standard

| Permutation | Verified | primaryKey                 | OPERATIONS
| :---------- | :------- | :------------------------- | :------
| a046d4      | Generic  | UNNAMED on LBSCHEMA.TABLE1 | **plan**: getIndexInfo(null, LBSCHEMA, TABLE1, false, true)<br>getPrimaryKeys(null, LBSCHEMA, TABLE1)

# Test: "can find all indexes in a fully qualified complex table name" #

- **connection:** generic standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 82ca36      | Generic  | LBSCHEMA.4TEST_table                    | **plan**: getIndexInfo(null, LBSCHEMA, 4TEST_table, false, true)
| a59cad      | Generic  | LBSCHEMA.4test_table                    | **plan**: getIndexInfo(null, LBSCHEMA, 4test_table, false, true)
| 02109b      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getIndexInfo(null, LBSCHEMA, ANOTHERUPPERTABLE, false, true)
| 0556ea      | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: getIndexInfo(null, LBSCHEMA, AnotherMixedTable, false, true)
| 2cbd26      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getIndexInfo(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| 6b38df      | Generic  | LBSCHEMA.MixedTable                     | **plan**: getIndexInfo(null, LBSCHEMA, MixedTable, false, true)
| 763654      | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: getIndexInfo(null, LBSCHEMA, ONLY_IN_LBSCHEMA, false, true)
| 8f1814      | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: getIndexInfo(null, LBSCHEMA, UPPERTABLE, false, true)
| 5c8625      | Generic  | LBSCHEMA.anotherlowertable              | **plan**: getIndexInfo(null, LBSCHEMA, anotherlowertable, false, true)
| 86a5d1      | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getIndexInfo(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| c4dbeb      | Generic  | LBSCHEMA.lowertable                     | **plan**: getIndexInfo(null, LBSCHEMA, lowertable, false, true)
| 31f94a      | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4TEST_table, false, true)
| 13e76e      | Generic  | LBSCHEMA2.4test_table                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4test_table, false, true)
| cec2e1      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getIndexInfo(null, LBSCHEMA2, ANOTHERUPPERTABLE, false, true)
| fe461f      | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: getIndexInfo(null, LBSCHEMA2, AnotherMixedTable, false, true)
| dec400      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getIndexInfo(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| cf72a3      | Generic  | LBSCHEMA2.MixedTable                    | **plan**: getIndexInfo(null, LBSCHEMA2, MixedTable, false, true)
| 6e579a      | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: getIndexInfo(null, LBSCHEMA2, ONLY_IN_LBSCHEMA2, false, true)
| d5eec3      | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: getIndexInfo(null, LBSCHEMA2, UPPERTABLE, false, true)
| 00b229      | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: getIndexInfo(null, LBSCHEMA2, anotherlowertable, false, true)
| b875ac      | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getIndexInfo(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| 5921e4      | Generic  | LBSCHEMA2.lowertable                    | **plan**: getIndexInfo(null, LBSCHEMA2, lowertable, false, true)

# Test: "can find by IndexReference with a table name but null index name" #

- **connection:** generic standard

| Permutation | Verified | index                                              | OPERATIONS
| :---------- | :------- | :------------------------------------------------- | :------
| d97175      | Generic  | UNNAMED on LBSCHEMA.4TEST_table                    | **plan**: getIndexInfo(null, LBSCHEMA, 4TEST_table, false, true)
| 8a7d9b      | Generic  | UNNAMED on LBSCHEMA.4test_table                    | **plan**: getIndexInfo(null, LBSCHEMA, 4test_table, false, true)
| ca704d      | Generic  | UNNAMED on LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getIndexInfo(null, LBSCHEMA, ANOTHERUPPERTABLE, false, true)
| bbe7df      | Generic  | UNNAMED on LBSCHEMA.AnotherMixedTable              | **plan**: getIndexInfo(null, LBSCHEMA, AnotherMixedTable, false, true)
| 5b7150      | Generic  | UNNAMED on LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getIndexInfo(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| 376daf      | Generic  | UNNAMED on LBSCHEMA.MixedTable                     | **plan**: getIndexInfo(null, LBSCHEMA, MixedTable, false, true)
| 33590d      | Generic  | UNNAMED on LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: getIndexInfo(null, LBSCHEMA, ONLY_IN_LBSCHEMA, false, true)
| aed4e5      | Generic  | UNNAMED on LBSCHEMA.UPPERTABLE                     | **plan**: getIndexInfo(null, LBSCHEMA, UPPERTABLE, false, true)
| 851a72      | Generic  | UNNAMED on LBSCHEMA.anotherlowertable              | **plan**: getIndexInfo(null, LBSCHEMA, anotherlowertable, false, true)
| cadd54      | Generic  | UNNAMED on LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getIndexInfo(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| 402768      | Generic  | UNNAMED on LBSCHEMA.lowertable                     | **plan**: getIndexInfo(null, LBSCHEMA, lowertable, false, true)
| a96072      | Generic  | UNNAMED on LBSCHEMA2.4TEST_table                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4TEST_table, false, true)
| f22a9e      | Generic  | UNNAMED on LBSCHEMA2.4test_table                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4test_table, false, true)
| 4bbe88      | Generic  | UNNAMED on LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getIndexInfo(null, LBSCHEMA2, ANOTHERUPPERTABLE, false, true)
| c65d3b      | Generic  | UNNAMED on LBSCHEMA2.AnotherMixedTable             | **plan**: getIndexInfo(null, LBSCHEMA2, AnotherMixedTable, false, true)
| 3217bb      | Generic  | UNNAMED on LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getIndexInfo(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| ee3842      | Generic  | UNNAMED on LBSCHEMA2.MixedTable                    | **plan**: getIndexInfo(null, LBSCHEMA2, MixedTable, false, true)
| c7fc5e      | Generic  | UNNAMED on LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: getIndexInfo(null, LBSCHEMA2, ONLY_IN_LBSCHEMA2, false, true)
| b3f8bd      | Generic  | UNNAMED on LBSCHEMA2.UPPERTABLE                    | **plan**: getIndexInfo(null, LBSCHEMA2, UPPERTABLE, false, true)
| ac1343      | Generic  | UNNAMED on LBSCHEMA2.anotherlowertable             | **plan**: getIndexInfo(null, LBSCHEMA2, anotherlowertable, false, true)
| ed692f      | Generic  | UNNAMED on LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getIndexInfo(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| c2cce1      | Generic  | UNNAMED on LBSCHEMA2.lowertable                    | **plan**: getIndexInfo(null, LBSCHEMA2, lowertable, false, true)

# Test: "can find complex index names with a table" #

- **connection:** generic standard

| Permutation | Verified | index                                        | OPERATIONS
| :---------- | :------- | :------------------------------------------- | :------
| dd420c      | Generic  | 4TEST_index on KNOWN_TABLE                   | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 3d182b      | Generic  | 4test_index on KNOWN_TABLE                   | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| a95d31      | Generic  | ANOTHERUPPERINDEX on KNOWN_TABLE             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 9db7c2      | Generic  | AnotherMixedIndex on KNOWN_TABLE             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 745ff2      | Generic  | CRAZY!@#\$%^&*()_+{}[]'"INDEX on KNOWN_TABLE | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| d84b03      | Generic  | MixedIndex on KNOWN_TABLE                    | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 97ddb7      | Generic  | UPPERINDEX on KNOWN_TABLE                    | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| ad8067      | Generic  | anotherlowerindex on KNOWN_TABLE             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| d03897      | Generic  | crazy!@#\$%^&*()_+{}[]'"index on KNOWN_TABLE | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 764513      | Generic  | lowerindex on KNOWN_TABLE                    | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)

# Test Version: "fed3ec" #