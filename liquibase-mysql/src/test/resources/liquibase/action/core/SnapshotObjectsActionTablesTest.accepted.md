**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can snapshot all tables in schema" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | schema | OPERATIONS
| :---------- | :------- | :----- | :------
| 4935d9      | true     | lbcat  | **plan**: getTables(lbcat, null, null, [TABLE])
| e78147      | true     | lbcat2 | **plan**: getTables(lbcat2, null, null, [TABLE])

# Test: "can snapshot all tables in schema using a null table name reference" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | schema | OPERATIONS
| :---------- | :------- | :----- | :------
| 4935d9      | true     | lbcat  | **plan**: getTables(lbcat, null, null, [TABLE])
| e78147      | true     | lbcat2 | **plan**: getTables(lbcat2, null, null, [TABLE])

# Test: "can snapshot fully qualified table" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | table                                | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| f75adb      | true     | lbcat.4test_table                    | **plan**: getTables(lbcat, null, 4test\_table, [TABLE])
| 5e7754      | true     | lbcat.anotherlowertable              | **plan**: getTables(lbcat, null, anotherlowertable, [TABLE])
| ca0403      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getTables(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, [TABLE])
| ad19b9      | true     | lbcat.lowertable                     | **plan**: getTables(lbcat, null, lowertable, [TABLE])
| cea6bc      | true     | lbcat.only_in_lbcat                  | **plan**: getTables(lbcat, null, only\_in\_lbcat, [TABLE])
| bc878f      | true     | lbcat2.4test_table                   | **plan**: getTables(lbcat2, null, 4test\_table, [TABLE])
| b48dc5      | true     | lbcat2.anotherlowertable             | **plan**: getTables(lbcat2, null, anotherlowertable, [TABLE])
| b85e41      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getTables(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, [TABLE])
| f95c81      | true     | lbcat2.lowertable                    | **plan**: getTables(lbcat2, null, lowertable, [TABLE])
| d76757      | true     | lbcat2.only_in_lbcat2                | **plan**: getTables(lbcat2, null, only\_in\_lbcat2, [TABLE])

# Test Version: "246a03" #