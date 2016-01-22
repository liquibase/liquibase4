**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column indexes correctly" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | schemaName         | OPERATIONS
| :---------- | :------------------- | :----------------- | :------
| 5a2aea      | Unsupported Database | LBSCHEMA (SCHEMA)  | **plan**: getIndexInfo(null, LBSCHEMA, TABLE_NAME, false, true)
| 2cb1f9      | Unsupported Database | LBSCHEMA2 (SCHEMA) | **plan**: getIndexInfo(null, LBSCHEMA2, TABLE_NAME, false, true)

# Test: "Snapshots column direction correctly" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | schemaName        | OPERATIONS
| :---------- | :------------------- | :---------------- | :------
| 5a2aea      | Unsupported Database | LBSCHEMA (SCHEMA) | **plan**: getIndexInfo(null, LBSCHEMA, TABLE1, false, true)

# Test: "Snapshots multi-column primary key correctly" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | primaryKey                           | OPERATIONS
| :---------- | :------------------- | :----------------------------------- | :------
| 208026      | Unsupported Database | LBSCHEMA.TABLE2.UNNAMED (PRIMARYKEY) | **plan**: Execute getIndexInfo(null, LBSCHEMA, TABLE2, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic<br>Execute getPrimaryKeys(null, LBSCHEMA, TABLE2) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "Snapshots single-column primary key correctly" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | primaryKey                           | OPERATIONS
| :---------- | :------------------- | :----------------------------------- | :------
| 96b72d      | Unsupported Database | LBSCHEMA.TABLE1.UNNAMED (PRIMARYKEY) | **plan**: Execute getIndexInfo(null, LBSCHEMA, TABLE1, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "can find all indexes in a fully qualified complex table name" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | table                                       | OPERATIONS
| :---------- | :------------------- | :---------------------------------------------- | :------
| f15368      | Unsupported Database | LBSCHEMA.4TEST_table (TABLE)                    | **plan**: getIndexInfo(null, LBSCHEMA, 4TEST_table, false, true)
| 71dd23      | Unsupported Database | LBSCHEMA.4test_table (TABLE)                    | **plan**: getIndexInfo(null, LBSCHEMA, 4test_table, false, true)
| db5ec7      | Unsupported Database | LBSCHEMA.ANOTHERUPPERTABLE (TABLE)              | **plan**: getIndexInfo(null, LBSCHEMA, ANOTHERUPPERTABLE, false, true)
| 23217d      | Unsupported Database | LBSCHEMA.AnotherMixedTable (TABLE)              | **plan**: getIndexInfo(null, LBSCHEMA, AnotherMixedTable, false, true)
| 3e74d8      | Unsupported Database | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE)  | **plan**: getIndexInfo(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| d6730a      | Unsupported Database | LBSCHEMA.MixedTable (TABLE)                     | **plan**: getIndexInfo(null, LBSCHEMA, MixedTable, false, true)
| 47a1e8      | Unsupported Database | LBSCHEMA.UPPERTABLE (TABLE)                     | **plan**: getIndexInfo(null, LBSCHEMA, UPPERTABLE, false, true)
| 95974f      | Unsupported Database | LBSCHEMA.anotherlowertable (TABLE)              | **plan**: getIndexInfo(null, LBSCHEMA, anotherlowertable, false, true)
| cdba92      | Unsupported Database | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table (TABLE)  | **plan**: getIndexInfo(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| 20611d      | Unsupported Database | LBSCHEMA.lowertable (TABLE)                     | **plan**: getIndexInfo(null, LBSCHEMA, lowertable, false, true)
| b79e72      | Unsupported Database | LBSCHEMA.only_in_LBSCHEMA (TABLE)               | **plan**: getIndexInfo(null, LBSCHEMA, only_in_LBSCHEMA, false, true)
| 46f60d      | Unsupported Database | LBSCHEMA2.4TEST_table (TABLE)                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4TEST_table, false, true)
| bfc36c      | Unsupported Database | LBSCHEMA2.4test_table (TABLE)                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4test_table, false, true)
| 7841f6      | Unsupported Database | LBSCHEMA2.ANOTHERUPPERTABLE (TABLE)             | **plan**: getIndexInfo(null, LBSCHEMA2, ANOTHERUPPERTABLE, false, true)
| 3e75c3      | Unsupported Database | LBSCHEMA2.AnotherMixedTable (TABLE)             | **plan**: getIndexInfo(null, LBSCHEMA2, AnotherMixedTable, false, true)
| b6d807      | Unsupported Database | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE (TABLE) | **plan**: getIndexInfo(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| 9c23bf      | Unsupported Database | LBSCHEMA2.MixedTable (TABLE)                    | **plan**: getIndexInfo(null, LBSCHEMA2, MixedTable, false, true)
| 2267d3      | Unsupported Database | LBSCHEMA2.UPPERTABLE (TABLE)                    | **plan**: getIndexInfo(null, LBSCHEMA2, UPPERTABLE, false, true)
| a79e5b      | Unsupported Database | LBSCHEMA2.anotherlowertable (TABLE)             | **plan**: getIndexInfo(null, LBSCHEMA2, anotherlowertable, false, true)
| 1ca256      | Unsupported Database | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table (TABLE) | **plan**: getIndexInfo(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| d06325      | Unsupported Database | LBSCHEMA2.lowertable (TABLE)                    | **plan**: getIndexInfo(null, LBSCHEMA2, lowertable, false, true)
| 6dc804      | Unsupported Database | LBSCHEMA2.only_in_LBSCHEMA2 (TABLE)             | **plan**: getIndexInfo(null, LBSCHEMA2, only_in_LBSCHEMA2, false, true)

# Test: "can find by IndexReference with a table name but null index name" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | indexName                                               | OPERATIONS
| :---------- | :------------------- | :------------------------------------------------------ | :------
| 4d7ef0      | Unsupported Database | LBSCHEMA.4TEST_table.UNNAMED (INDEX)                    | **plan**: getIndexInfo(null, LBSCHEMA, 4TEST_table, false, true)
| 1c31f3      | Unsupported Database | LBSCHEMA.4test_table.UNNAMED (INDEX)                    | **plan**: getIndexInfo(null, LBSCHEMA, 4test_table, false, true)
| 5913eb      | Unsupported Database | LBSCHEMA.ANOTHERUPPERTABLE.UNNAMED (INDEX)              | **plan**: getIndexInfo(null, LBSCHEMA, ANOTHERUPPERTABLE, false, true)
| cbfaae      | Unsupported Database | LBSCHEMA.AnotherMixedTable.UNNAMED (INDEX)              | **plan**: getIndexInfo(null, LBSCHEMA, AnotherMixedTable, false, true)
| eb69ed      | Unsupported Database | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED (INDEX)  | **plan**: getIndexInfo(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| b605e2      | Unsupported Database | LBSCHEMA.MixedTable.UNNAMED (INDEX)                     | **plan**: getIndexInfo(null, LBSCHEMA, MixedTable, false, true)
| da9889      | Unsupported Database | LBSCHEMA.UPPERTABLE.UNNAMED (INDEX)                     | **plan**: getIndexInfo(null, LBSCHEMA, UPPERTABLE, false, true)
| a3ca5e      | Unsupported Database | LBSCHEMA.anotherlowertable.UNNAMED (INDEX)              | **plan**: getIndexInfo(null, LBSCHEMA, anotherlowertable, false, true)
| 83338a      | Unsupported Database | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (INDEX)  | **plan**: getIndexInfo(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| a213f1      | Unsupported Database | LBSCHEMA.lowertable.UNNAMED (INDEX)                     | **plan**: getIndexInfo(null, LBSCHEMA, lowertable, false, true)
| c2b90a      | Unsupported Database | LBSCHEMA.only_in_LBSCHEMA.UNNAMED (INDEX)               | **plan**: getIndexInfo(null, LBSCHEMA, only_in_LBSCHEMA, false, true)
| 14eda5      | Unsupported Database | LBSCHEMA2.4TEST_table.UNNAMED (INDEX)                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4TEST_table, false, true)
| 0b387a      | Unsupported Database | LBSCHEMA2.4test_table.UNNAMED (INDEX)                   | **plan**: getIndexInfo(null, LBSCHEMA2, 4test_table, false, true)
| 066c91      | Unsupported Database | LBSCHEMA2.ANOTHERUPPERTABLE.UNNAMED (INDEX)             | **plan**: getIndexInfo(null, LBSCHEMA2, ANOTHERUPPERTABLE, false, true)
| 040da2      | Unsupported Database | LBSCHEMA2.AnotherMixedTable.UNNAMED (INDEX)             | **plan**: getIndexInfo(null, LBSCHEMA2, AnotherMixedTable, false, true)
| 125285      | Unsupported Database | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED (INDEX) | **plan**: getIndexInfo(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE, false, true)
| 77b45c      | Unsupported Database | LBSCHEMA2.MixedTable.UNNAMED (INDEX)                    | **plan**: getIndexInfo(null, LBSCHEMA2, MixedTable, false, true)
| bdd103      | Unsupported Database | LBSCHEMA2.UPPERTABLE.UNNAMED (INDEX)                    | **plan**: getIndexInfo(null, LBSCHEMA2, UPPERTABLE, false, true)
| 5958e6      | Unsupported Database | LBSCHEMA2.anotherlowertable.UNNAMED (INDEX)             | **plan**: getIndexInfo(null, LBSCHEMA2, anotherlowertable, false, true)
| beb016      | Unsupported Database | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (INDEX) | **plan**: getIndexInfo(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| 7c3312      | Unsupported Database | LBSCHEMA2.lowertable.UNNAMED (INDEX)                    | **plan**: getIndexInfo(null, LBSCHEMA2, lowertable, false, true)
| f72b7d      | Unsupported Database | LBSCHEMA2.only_in_LBSCHEMA2.UNNAMED (INDEX)             | **plan**: getIndexInfo(null, LBSCHEMA2, only_in_LBSCHEMA2, false, true)

# Test: "can find complex index names with a table" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | indexName                                         | OPERATIONS
| :---------- | :------------------- | :------------------------------------------------ | :------
| 888723      | Unsupported Database | KNOWN_TABLE.4TEST_index (INDEX)                   | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| ad8d6c      | Unsupported Database | KNOWN_TABLE.4test_index (INDEX)                   | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 957667      | Unsupported Database | KNOWN_TABLE.ANOTHERUPPERINDEX (INDEX)             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| ea72bd      | Unsupported Database | KNOWN_TABLE.AnotherMixedIndex (INDEX)             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| db1d53      | Unsupported Database | KNOWN_TABLE.CRAZY!@#\$%^&*()_+{}[]'"INDEX (INDEX) | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| e20c74      | Unsupported Database | KNOWN_TABLE.MixedIndex (INDEX)                    | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 525666      | Unsupported Database | KNOWN_TABLE.UPPERINDEX (INDEX)                    | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| acb023      | Unsupported Database | KNOWN_TABLE.anotherlowerindex (INDEX)             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 45a082      | Unsupported Database | KNOWN_TABLE.crazy!@#\$%^&*()_+{}[]'"index (INDEX) | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 3e4106      | Unsupported Database | KNOWN_TABLE.lowerindex (INDEX)                    | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| 62a614      | Unsupported Database | KNOWN_TABLE.only_in_LBSCHEMA (INDEX)              | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)
| ad6f83      | Unsupported Database | KNOWN_TABLE.only_in_LBSCHEMA2 (INDEX)             | **plan**: getIndexInfo(null, null, KNOWN_TABLE, false, true)

# Test Version: "98b3e8" #