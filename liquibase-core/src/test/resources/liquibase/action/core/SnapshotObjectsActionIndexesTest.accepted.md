**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column indexes correctly" #

- **connection:** generic[config:standard]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 0f43a8      | Generic  | LBSCHEMA   | **plan**: getIndexInfo(null, LBSCHEMA, TABLE_NAME, false, true)
| eb7c69      | Generic  | LBSCHEMA2  | **plan**: getIndexInfo(null, LBSCHEMA2, TABLE_NAME, false, true)

# Test: "Snapshots column direction correctly" #

- **connection:** generic[config:standard]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 0f43a8      | Generic  | LBSCHEMA   | **plan**: getIndexInfo(null, LBSCHEMA, TABLE1, false, true)

# Test: "Snapshots multi-column primary key correctly" #

- **connection:** generic[config:standard]

| Permutation | Verified | primaryKey              | OPERATIONS
| :---------- | :------- | :---------------------- | :------
| 4048bf      | Generic  | LBSCHEMA.TABLE2.UNNAMED | **plan**: getIndexInfo(null, LBSCHEMA, TABLE2, false, true)<br>getPrimaryKeys(null, LBSCHEMA, TABLE2)

# Test: "Snapshots single-column primary key correctly" #

- **connection:** generic[config:standard]

| Permutation | Verified | primaryKey              | OPERATIONS
| :---------- | :------- | :---------------------- | :------
| 2f4006      | Generic  | LBSCHEMA.TABLE1.UNNAMED | **plan**: getIndexInfo(null, LBSCHEMA, TABLE1, false, true)<br>getPrimaryKeys(null, LBSCHEMA, TABLE1)

# Test: "can find all indexes in a fully qualified complex table name" #

- **connection:** generic[config:standard]

| Permutation | Verified | tableName                               | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 6f10b3      | Generic  | LBSCHEMA.4TEST_table                    | **plan**: getIndexInfo(null, LBSCHEMA, 4TEST_table, false, true)
| 447604      | Generic  | LBSCHEMA.4test_table                    | **plan**: getIndexInfo(null, LBSCHEMA, 4test_table, false, true)
| 7d8923      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getIndexInfo(null, LBSCHEMA, ANOTHERUPPERTABLE, false, true)
| 244240      | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: getIndexInfo(null, LBSCHEMA, AnotherMixedTable, false, true)
| cf6e70      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getIndexInfo(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| 1ab5be      | Generic  | LBSCHEMA.MixedTable                     | **plan**: getIndexInfo(null, LBSCHEMA, MixedTable, false, true)
| d62fef      | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: getIndexInfo(null, LBSCHEMA, UPPERTABLE, false, true)
| b7bd07      | Generic  | LBSCHEMA.anotherlowertable              | **plan**: getIndexInfo(null, LBSCHEMA, anotherlowertable, false, true)
| 8d1056      | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getIndexInfo(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| f03884      | Generic  | LBSCHEMA.lowertable                     | **plan**: getIndexInfo(null, LBSCHEMA, lowertable, false, true)
| a69da2      | Generic  | LBSCHEMA.only_in_LBSCHEMA               | **plan**: getIndexInfo(null, LBSCHEMA, only_in_LBSCHEMA, false, true)
| a86d64      | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4TEST_table, false, true)
| 590a6e      | Generic  | LBSCHEMA2.4test_table                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4test_table, false, true)
| 2927a8      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getIndexInfo(null, LBSCHEMA2, ANOTHERUPPERTABLE, false, true)
| ce1ca3      | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: getIndexInfo(null, LBSCHEMA2, AnotherMixedTable, false, true)
| 710fa9      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getIndexInfo(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| 315ef1      | Generic  | LBSCHEMA2.MixedTable                    | **plan**: getIndexInfo(null, LBSCHEMA2, MixedTable, false, true)
| 33d15e      | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: getIndexInfo(null, LBSCHEMA2, UPPERTABLE, false, true)
| 22dc5a      | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: getIndexInfo(null, LBSCHEMA2, anotherlowertable, false, true)
| fbf4a4      | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getIndexInfo(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| 55094c      | Generic  | LBSCHEMA2.lowertable                    | **plan**: getIndexInfo(null, LBSCHEMA2, lowertable, false, true)
| f90500      | Generic  | LBSCHEMA2.only_in_LBSCHEMA2             | **plan**: getIndexInfo(null, LBSCHEMA2, only_in_LBSCHEMA2, false, true)

# Test: "can find by IndexReference with a table name but null index name" #

- **connection:** generic[config:standard]

| Permutation | Verified | indexName                                       | OPERATIONS
| :---------- | :------- | :---------------------------------------------- | :------
| 619150      | Generic  | LBSCHEMA.4TEST_table.UNNAMED                    | **plan**: getIndexInfo(null, LBSCHEMA, 4TEST_table, false, true)
| 5d45ca      | Generic  | LBSCHEMA.4test_table.UNNAMED                    | **plan**: getIndexInfo(null, LBSCHEMA, 4test_table, false, true)
| 1914b3      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE.UNNAMED              | **plan**: getIndexInfo(null, LBSCHEMA, ANOTHERUPPERTABLE, false, true)
| c58e2d      | Generic  | LBSCHEMA.AnotherMixedTable.UNNAMED              | **plan**: getIndexInfo(null, LBSCHEMA, AnotherMixedTable, false, true)
| 3cdc4b      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED  | **plan**: getIndexInfo(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| d51ae8      | Generic  | LBSCHEMA.MixedTable.UNNAMED                     | **plan**: getIndexInfo(null, LBSCHEMA, MixedTable, false, true)
| 115251      | Generic  | LBSCHEMA.UPPERTABLE.UNNAMED                     | **plan**: getIndexInfo(null, LBSCHEMA, UPPERTABLE, false, true)
| 33d206      | Generic  | LBSCHEMA.anotherlowertable.UNNAMED              | **plan**: getIndexInfo(null, LBSCHEMA, anotherlowertable, false, true)
| f7538a      | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED  | **plan**: getIndexInfo(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| 222f07      | Generic  | LBSCHEMA.lowertable.UNNAMED                     | **plan**: getIndexInfo(null, LBSCHEMA, lowertable, false, true)
| 78ccfb      | Generic  | LBSCHEMA.only_in_LBSCHEMA.UNNAMED               | **plan**: getIndexInfo(null, LBSCHEMA, only_in_LBSCHEMA, false, true)
| 61b759      | Generic  | LBSCHEMA2.4TEST_table.UNNAMED                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4TEST_table, false, true)
| d4b8cb      | Generic  | LBSCHEMA2.4test_table.UNNAMED                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4test_table, false, true)
| af6db2      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE.UNNAMED             | **plan**: getIndexInfo(null, LBSCHEMA2, ANOTHERUPPERTABLE, false, true)
| 5d7b25      | Generic  | LBSCHEMA2.AnotherMixedTable.UNNAMED             | **plan**: getIndexInfo(null, LBSCHEMA2, AnotherMixedTable, false, true)
| 5c6233      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED | **plan**: getIndexInfo(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| 4364f5      | Generic  | LBSCHEMA2.MixedTable.UNNAMED                    | **plan**: getIndexInfo(null, LBSCHEMA2, MixedTable, false, true)
| 4a8d65      | Generic  | LBSCHEMA2.UPPERTABLE.UNNAMED                    | **plan**: getIndexInfo(null, LBSCHEMA2, UPPERTABLE, false, true)
| aa931b      | Generic  | LBSCHEMA2.anotherlowertable.UNNAMED             | **plan**: getIndexInfo(null, LBSCHEMA2, anotherlowertable, false, true)
| 47674b      | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED | **plan**: getIndexInfo(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| 9b7765      | Generic  | LBSCHEMA2.lowertable.UNNAMED                    | **plan**: getIndexInfo(null, LBSCHEMA2, lowertable, false, true)
| c7e7bc      | Generic  | LBSCHEMA2.only_in_LBSCHEMA2.UNNAMED             | **plan**: getIndexInfo(null, LBSCHEMA2, only_in_LBSCHEMA2, false, true)

# Test: "can find complex index names with a table" #

- **connection:** generic[config:standard]

| Permutation | Verified | indexName                                 | OPERATIONS
| :---------- | :------- | :---------------------------------------- | :------
| 3286dd      | Generic  | KNOWN_TABLE.4TEST_index                   | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 1b98d6      | Generic  | KNOWN_TABLE.4test_index                   | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 79462a      | Generic  | KNOWN_TABLE.ANOTHERUPPERINDEX             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| b9c7d9      | Generic  | KNOWN_TABLE.AnotherMixedIndex             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| e64834      | Generic  | KNOWN_TABLE.CRAZY!@#\$%^&*()_+{}[]'"INDEX | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 5bd445      | Generic  | KNOWN_TABLE.MixedIndex                    | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| e139ef      | Generic  | KNOWN_TABLE.UPPERINDEX                    | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 8180aa      | Generic  | KNOWN_TABLE.anotherlowerindex             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 73be1c      | Generic  | KNOWN_TABLE.crazy!@#\$%^&*()_+{}[]'"index | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| d40f14      | Generic  | KNOWN_TABLE.lowerindex                    | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| cfab30      | Generic  | KNOWN_TABLE.only_in_LBSCHEMA              | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| f7640e      | Generic  | KNOWN_TABLE.only_in_LBSCHEMA2             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)

# Test Version: "8cd43c" #