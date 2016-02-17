**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can snapshot all tables in schema" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 2a5ee3      | true     | lbcat      | **plan**: getTables(lbcat, null, null, [TABLE])
| 9f6418      | true     | lbcat2     | **plan**: getTables(lbcat2, null, null, [TABLE])

# Test: "can snapshot all tables in schema using a null table name reference" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 2a5ee3      | true     | lbcat      | **plan**: getTables(lbcat, null, null, [TABLE])
| 9f6418      | true     | lbcat2     | **plan**: getTables(lbcat2, null, null, [TABLE])

# Test: "can snapshot fully qualified table" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | tableName                            | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| 6db8bb      | true     | lbcat.4test_table                    | **plan**: getTables(lbcat, null, 4test\_table, [TABLE])
| b2e45f      | true     | lbcat.anotherlowertable              | **plan**: getTables(lbcat, null, anotherlowertable, [TABLE])
| 5f0ea5      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getTables(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, [TABLE])
| 572208      | true     | lbcat.lowertable                     | **plan**: getTables(lbcat, null, lowertable, [TABLE])
| 58d58f      | true     | lbcat.only_in_lbcat                  | **plan**: getTables(lbcat, null, only\_in\_lbcat, [TABLE])
| be2547      | true     | lbcat2.4test_table                   | **plan**: getTables(lbcat2, null, 4test\_table, [TABLE])
| c493e5      | true     | lbcat2.anotherlowertable             | **plan**: getTables(lbcat2, null, anotherlowertable, [TABLE])
| 94369b      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getTables(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, [TABLE])
| 2c35e6      | true     | lbcat2.lowertable                    | **plan**: getTables(lbcat2, null, lowertable, [TABLE])
| 4e636c      | true     | lbcat2.only_in_lbcat2                | **plan**: getTables(lbcat2, null, only\_in\_lbcat2, [TABLE])

# Test Version: "30b626" #