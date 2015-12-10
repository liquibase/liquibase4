**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can find all primaryKeys in a fully qualified complex table name" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | tableName                                    | OPERATIONS
| :---------- | :------- | :------------------------------------------- | :------
| e83394      | true     | lbcat.4test_table (TABLE)                    | **plan**: getPrimaryKeys(lbcat, null, 4test_table)
| 56b06d      | true     | lbcat.anotherlowertable (TABLE)              | **plan**: getPrimaryKeys(lbcat, null, anotherlowertable)
| 45eba6      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table (TABLE)  | **plan**: getPrimaryKeys(lbcat, null, crazy!@#\$%^&*()_+{}[]'"table)
| e0e1e5      | true     | lbcat.lowertable (TABLE)                     | **plan**: getPrimaryKeys(lbcat, null, lowertable)
| 1217fc      | true     | lbcat.only_in_lbcat (TABLE)                  | **plan**: getPrimaryKeys(lbcat, null, only_in_lbcat)
| cdbe33      | true     | lbcat2.4test_table (TABLE)                   | **plan**: getPrimaryKeys(lbcat2, null, 4test_table)
| c51a94      | true     | lbcat2.anotherlowertable (TABLE)             | **plan**: getPrimaryKeys(lbcat2, null, anotherlowertable)
| 85fbd4      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table (TABLE) | **plan**: getPrimaryKeys(lbcat2, null, crazy!@#\$%^&*()_+{}[]'"table)
| 3cb8b0      | true     | lbcat2.lowertable (TABLE)                    | **plan**: getPrimaryKeys(lbcat2, null, lowertable)
| a1779f      | true     | lbcat2.only_in_lbcat2 (TABLE)                | **plan**: getPrimaryKeys(lbcat2, null, only_in_lbcat2)

# Test: "can find by PrimaryKeyReference with a table name but null primary key name" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | pkName                                                    | OPERATIONS
| :---------- | :------- | :-------------------------------------------------------- | :------
| 0a97c7      | true     | lbcat.4test_table.UNNAMED (PRIMARYKEY)                    | **plan**: getPrimaryKeys(lbcat, null, 4test_table)
| e32b23      | true     | lbcat.anotherlowertable.UNNAMED (PRIMARYKEY)              | **plan**: getPrimaryKeys(lbcat, null, anotherlowertable)
| 730a19      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (PRIMARYKEY)  | **plan**: getPrimaryKeys(lbcat, null, crazy!@#\$%^&*()_+{}[]'"table)
| a53b43      | true     | lbcat.lowertable.UNNAMED (PRIMARYKEY)                     | **plan**: getPrimaryKeys(lbcat, null, lowertable)
| a1ca94      | true     | lbcat.only_in_lbcat.UNNAMED (PRIMARYKEY)                  | **plan**: getPrimaryKeys(lbcat, null, only_in_lbcat)
| e0a233      | true     | lbcat2.4test_table.UNNAMED (PRIMARYKEY)                   | **plan**: getPrimaryKeys(lbcat2, null, 4test_table)
| cd45d5      | true     | lbcat2.anotherlowertable.UNNAMED (PRIMARYKEY)             | **plan**: getPrimaryKeys(lbcat2, null, anotherlowertable)
| 72b4c0      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED (PRIMARYKEY) | **plan**: getPrimaryKeys(lbcat2, null, crazy!@#\$%^&*()_+{}[]'"table)
| a0be3e      | true     | lbcat2.lowertable.UNNAMED (PRIMARYKEY)                    | **plan**: getPrimaryKeys(lbcat2, null, lowertable)
| 712cc9      | true     | lbcat2.only_in_lbcat2.UNNAMED (PRIMARYKEY)                | **plan**: getPrimaryKeys(lbcat2, null, only_in_lbcat2)

# Test Version: "e86ce9" #