**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column indexes correctly" #

- **connection:** generic standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 6e63508     | Generic  | LBSCHEMA  | **plan**: getIndexInfo(null, LBSCHEMA, TABLE_NAME, false, true)
| bbb8e61     | Generic  | LBSCHEMA2 | **plan**: getIndexInfo(null, LBSCHEMA2, TABLE_NAME, false, true)

# Test: "Snapshots column direction correctly" #

- **connection:** generic standard

| Permutation | Verified | schema   | OPERATIONS
| :---------- | :------- | :------- | :------
| 6e63508     | Generic  | LBSCHEMA | **plan**: getIndexInfo(null, LBSCHEMA, TABLE1, false, true)

# Test: "Snapshots multi-column primary key correctly" #

- **connection:** generic standard

| Permutation | Verified | primaryKey                 | OPERATIONS
| :---------- | :------- | :------------------------- | :------
| dd31dc5     | Generic  | UNNAMED on LBSCHEMA.TABLE2 | **plan**: getIndexInfo(null, LBSCHEMA, TABLE2, false, true)<br>getPrimaryKeys(null, LBSCHEMA, TABLE2)

# Test: "Snapshots single-column primary key correctly" #

- **connection:** generic standard

| Permutation | Verified | primaryKey                 | OPERATIONS
| :---------- | :------- | :------------------------- | :------
| a046d46     | Generic  | UNNAMED on LBSCHEMA.TABLE1 | **plan**: getIndexInfo(null, LBSCHEMA, TABLE1, false, true)<br>getPrimaryKeys(null, LBSCHEMA, TABLE1)

# Test: "can find all indexes in a fully qualified complex table name" #

- **connection:** generic standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 82ca36a     | Generic  | LBSCHEMA.4TEST_table                    | **plan**: getIndexInfo(null, LBSCHEMA, 4TEST_table, false, true)
| a59cadd     | Generic  | LBSCHEMA.4test_table                    | **plan**: getIndexInfo(null, LBSCHEMA, 4test_table, false, true)
| 02109bf     | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getIndexInfo(null, LBSCHEMA, ANOTHERUPPERTABLE, false, true)
| 0556eae     | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: getIndexInfo(null, LBSCHEMA, AnotherMixedTable, false, true)
| 2cbd26a     | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getIndexInfo(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| 6b38df5     | Generic  | LBSCHEMA.MixedTable                     | **plan**: getIndexInfo(null, LBSCHEMA, MixedTable, false, true)
| 7636549     | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: getIndexInfo(null, LBSCHEMA, ONLY_IN_LBSCHEMA, false, true)
| 8f18146     | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: getIndexInfo(null, LBSCHEMA, UPPERTABLE, false, true)
| 5c86256     | Generic  | LBSCHEMA.anotherlowertable              | **plan**: getIndexInfo(null, LBSCHEMA, anotherlowertable, false, true)
| 86a5d18     | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getIndexInfo(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| c4dbeb3     | Generic  | LBSCHEMA.lowertable                     | **plan**: getIndexInfo(null, LBSCHEMA, lowertable, false, true)
| 31f94ac     | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4TEST_table, false, true)
| 13e76ef     | Generic  | LBSCHEMA2.4test_table                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4test_table, false, true)
| cec2e1c     | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getIndexInfo(null, LBSCHEMA2, ANOTHERUPPERTABLE, false, true)
| fe461f7     | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: getIndexInfo(null, LBSCHEMA2, AnotherMixedTable, false, true)
| dec400d     | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getIndexInfo(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| cf72a30     | Generic  | LBSCHEMA2.MixedTable                    | **plan**: getIndexInfo(null, LBSCHEMA2, MixedTable, false, true)
| 6e579ae     | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: getIndexInfo(null, LBSCHEMA2, ONLY_IN_LBSCHEMA2, false, true)
| d5eec30     | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: getIndexInfo(null, LBSCHEMA2, UPPERTABLE, false, true)
| 00b229a     | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: getIndexInfo(null, LBSCHEMA2, anotherlowertable, false, true)
| b875ac8     | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getIndexInfo(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| 5921e4d     | Generic  | LBSCHEMA2.lowertable                    | **plan**: getIndexInfo(null, LBSCHEMA2, lowertable, false, true)

# Test: "can find by IndexReference with a table name but null index name" #

- **connection:** generic standard

| Permutation | Verified | index                                              | OPERATIONS
| :---------- | :------- | :------------------------------------------------- | :------
| d97175b     | Generic  | UNNAMED on LBSCHEMA.4TEST_table                    | **plan**: getIndexInfo(null, LBSCHEMA, 4TEST_table, false, true)
| 8a7d9b0     | Generic  | UNNAMED on LBSCHEMA.4test_table                    | **plan**: getIndexInfo(null, LBSCHEMA, 4test_table, false, true)
| ca704d1     | Generic  | UNNAMED on LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getIndexInfo(null, LBSCHEMA, ANOTHERUPPERTABLE, false, true)
| bbe7df5     | Generic  | UNNAMED on LBSCHEMA.AnotherMixedTable              | **plan**: getIndexInfo(null, LBSCHEMA, AnotherMixedTable, false, true)
| 5b71504     | Generic  | UNNAMED on LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getIndexInfo(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| 376daf6     | Generic  | UNNAMED on LBSCHEMA.MixedTable                     | **plan**: getIndexInfo(null, LBSCHEMA, MixedTable, false, true)
| 33590d7     | Generic  | UNNAMED on LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: getIndexInfo(null, LBSCHEMA, ONLY_IN_LBSCHEMA, false, true)
| aed4e50     | Generic  | UNNAMED on LBSCHEMA.UPPERTABLE                     | **plan**: getIndexInfo(null, LBSCHEMA, UPPERTABLE, false, true)
| 851a72d     | Generic  | UNNAMED on LBSCHEMA.anotherlowertable              | **plan**: getIndexInfo(null, LBSCHEMA, anotherlowertable, false, true)
| cadd547     | Generic  | UNNAMED on LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getIndexInfo(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| 4027685     | Generic  | UNNAMED on LBSCHEMA.lowertable                     | **plan**: getIndexInfo(null, LBSCHEMA, lowertable, false, true)
| a96072a     | Generic  | UNNAMED on LBSCHEMA2.4TEST_table                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4TEST_table, false, true)
| f22a9e8     | Generic  | UNNAMED on LBSCHEMA2.4test_table                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4test_table, false, true)
| 4bbe888     | Generic  | UNNAMED on LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getIndexInfo(null, LBSCHEMA2, ANOTHERUPPERTABLE, false, true)
| c65d3b5     | Generic  | UNNAMED on LBSCHEMA2.AnotherMixedTable             | **plan**: getIndexInfo(null, LBSCHEMA2, AnotherMixedTable, false, true)
| 3217bb2     | Generic  | UNNAMED on LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getIndexInfo(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| ee38425     | Generic  | UNNAMED on LBSCHEMA2.MixedTable                    | **plan**: getIndexInfo(null, LBSCHEMA2, MixedTable, false, true)
| c7fc5e0     | Generic  | UNNAMED on LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: getIndexInfo(null, LBSCHEMA2, ONLY_IN_LBSCHEMA2, false, true)
| b3f8bdd     | Generic  | UNNAMED on LBSCHEMA2.UPPERTABLE                    | **plan**: getIndexInfo(null, LBSCHEMA2, UPPERTABLE, false, true)
| ac13432     | Generic  | UNNAMED on LBSCHEMA2.anotherlowertable             | **plan**: getIndexInfo(null, LBSCHEMA2, anotherlowertable, false, true)
| ed692fb     | Generic  | UNNAMED on LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getIndexInfo(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| c2cce1a     | Generic  | UNNAMED on LBSCHEMA2.lowertable                    | **plan**: getIndexInfo(null, LBSCHEMA2, lowertable, false, true)

# Test: "can find complex index names with a table" #

- **connection:** generic standard

| Permutation | Verified | index                                        | OPERATIONS
| :---------- | :------- | :------------------------------------------- | :------
| dd420c3     | Generic  | 4TEST_index on KNOWN_TABLE                   | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 3d182b8     | Generic  | 4test_index on KNOWN_TABLE                   | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| a95d31e     | Generic  | ANOTHERUPPERINDEX on KNOWN_TABLE             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 9db7c24     | Generic  | AnotherMixedIndex on KNOWN_TABLE             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 745ff2f     | Generic  | CRAZY!@#\$%^&*()_+{}[]'"INDEX on KNOWN_TABLE | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| d84b03f     | Generic  | MixedIndex on KNOWN_TABLE                    | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 97ddb7d     | Generic  | UPPERINDEX on KNOWN_TABLE                    | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| ad80673     | Generic  | anotherlowerindex on KNOWN_TABLE             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| d038976     | Generic  | crazy!@#\$%^&*()_+{}[]'"index on KNOWN_TABLE | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 7645134     | Generic  | lowerindex on KNOWN_TABLE                    | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)

# Test Version: "1182ae" #