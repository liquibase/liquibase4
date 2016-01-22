**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column indexes correctly" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | schemaName      | OPERATIONS
| :---------- | :------- | :-------------- | :------
| 3cd221      | true     | lbcat (SCHEMA)  | **plan**: getIndexInfo(lbcat, null, table_name, false, true)
| 3193e6      | true     | lbcat2 (SCHEMA) | **plan**: getIndexInfo(lbcat2, null, table_name, false, true)

# Test: "Snapshots multi-column primary key correctly" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | primaryKey                        | OPERATIONS
| :---------- | :------- | :-------------------------------- | :------
| 8ef588      | true     | lbcat.table2.UNNAMED (PRIMARYKEY) | **plan**: Execute getIndexInfo(lbcat, null, table2, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic<br>Execute getPrimaryKeys(lbcat, null, table2) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "Snapshots single-column primary key correctly" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | primaryKey                        | OPERATIONS
| :---------- | :------- | :-------------------------------- | :------
| de26f7      | true     | lbcat.table1.UNNAMED (PRIMARYKEY) | **plan**: Execute getIndexInfo(lbcat, null, table1, false, true) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic<br>Execute getPrimaryKeys(lbcat, null, table1) with liquibase.actionlogic.core.QueryJdbcMetaDataLogic

# Test: "can find all indexes in a fully qualified complex table name" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | table                                    | OPERATIONS
| :---------- | :------- | :------------------------------------------- | :------
| e83394      | true     | lbcat.4test_table (TABLE)                    | **plan**: getIndexInfo(lbcat, null, 4test_table, false, true)
| 56b06d      | true     | lbcat.anotherlowertable (TABLE)              | **plan**: getIndexInfo(lbcat, null, anotherlowertable, false, true)
| 45eba6      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table (TABLE)  | **plan**: getIndexInfo(lbcat, null, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| e0e1e5      | true     | lbcat.lowertable (TABLE)                     | **plan**: getIndexInfo(lbcat, null, lowertable, false, true)
| 1217fc      | true     | lbcat.only_in_lbcat (TABLE)                  | **plan**: getIndexInfo(lbcat, null, only_in_lbcat, false, true)
| cdbe33      | true     | lbcat2.4test_table (TABLE)                   | **plan**: getIndexInfo(lbcat2, null, 4test_table, false, true)
| c51a94      | true     | lbcat2.anotherlowertable (TABLE)             | **plan**: getIndexInfo(lbcat2, null, anotherlowertable, false, true)
| 85fbd4      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table (TABLE) | **plan**: getIndexInfo(lbcat2, null, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| 3cb8b0      | true     | lbcat2.lowertable (TABLE)                    | **plan**: getIndexInfo(lbcat2, null, lowertable, false, true)
| a1779f      | true     | lbcat2.only_in_lbcat2 (TABLE)                | **plan**: getIndexInfo(lbcat2, null, only_in_lbcat2, false, true)

# Test: "can find by IndexReference with a table name but null index name" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | indexName                                            | OPERATIONS
| :---------- | :------- | :--------------------------------------------------- | :------
| 769be6      | true     | lbcat.4test_table.UNNAMED (INDEX)                    | **plan**: getIndexInfo(lbcat, null, 4test_table, false, true)
| 352487      | true     | lbcat.anotherlowertable.UNNAMED (INDEX)              | **plan**: getIndexInfo(lbcat, null, anotherlowertable, false, true)
| c65324      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (INDEX)  | **plan**: getIndexInfo(lbcat, null, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| 6563e7      | true     | lbcat.lowertable.UNNAMED (INDEX)                     | **plan**: getIndexInfo(lbcat, null, lowertable, false, true)
| 4da986      | true     | lbcat.only_in_lbcat.UNNAMED (INDEX)                  | **plan**: getIndexInfo(lbcat, null, only_in_lbcat, false, true)
| 30e384      | true     | lbcat2.4test_table.UNNAMED (INDEX)                   | **plan**: getIndexInfo(lbcat2, null, 4test_table, false, true)
| fa8f20      | true     | lbcat2.anotherlowertable.UNNAMED (INDEX)             | **plan**: getIndexInfo(lbcat2, null, anotherlowertable, false, true)
| 1105ef      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (INDEX) | **plan**: getIndexInfo(lbcat2, null, crazy!@#\$%^&*()_+{}[]'"table, false, true)
| 866d73      | true     | lbcat2.lowertable.UNNAMED (INDEX)                    | **plan**: getIndexInfo(lbcat2, null, lowertable, false, true)
| d318ec      | true     | lbcat2.only_in_lbcat2.UNNAMED (INDEX)                | **plan**: getIndexInfo(lbcat2, null, only_in_lbcat2, false, true)

# Test: "can find complex index names with a table" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | indexName                                         | OPERATIONS
| :---------- | :------- | :------------------------------------------------ | :------
| 00cdd4      | true     | known_table.4test_index (INDEX)                   | **plan**: getIndexInfo(null, null, known_table, false, true)
| 6b02d3      | true     | known_table.anotherlowerindex (INDEX)             | **plan**: getIndexInfo(null, null, known_table, false, true)
| 05d9a9      | true     | known_table.crazy!@#\$%^&*()_+{}[]'"index (INDEX) | **plan**: getIndexInfo(null, null, known_table, false, true)
| 7812ac      | true     | known_table.lowerindex (INDEX)                    | **plan**: getIndexInfo(null, null, known_table, false, true)
| e8a018      | true     | known_table.only_in_lbcat (INDEX)                 | **plan**: getIndexInfo(null, null, known_table, false, true)
| 0fa075      | true     | known_table.only_in_lbcat2 (INDEX)                | **plan**: getIndexInfo(null, null, known_table, false, true)

# Test Version: "dc9421" #