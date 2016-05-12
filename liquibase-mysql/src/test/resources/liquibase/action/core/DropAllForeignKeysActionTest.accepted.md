**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can execute everything from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | table                                | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| c714c6a     | true     | lbcat.4test_table                    | **plan**: dropAllForeignKeys(table=lbcat.4test_table)
| 4922487     | true     | lbcat.anotherlowertable              | **plan**: dropAllForeignKeys(table=lbcat.anotherlowertable)
| 2f78b20     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: dropAllForeignKeys(table=lbcat.crazy!@#\$%^&*()_+{}[]'"table)
| 7c583f0     | true     | lbcat.lowertable                     | **plan**: dropAllForeignKeys(table=lbcat.lowertable)
| d93a8c8     | true     | lbcat.only_in_lbcat                  | **plan**: dropAllForeignKeys(table=lbcat.only_in_lbcat)
| 352c7c3     | true     | lbcat2.4test_table                   | **plan**: dropAllForeignKeys(table=lbcat2.4test_table)
| f78fdd5     | true     | lbcat2.anotherlowertable             | **plan**: dropAllForeignKeys(table=lbcat2.anotherlowertable)
| 1a22ce6     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: dropAllForeignKeys(table=lbcat2.crazy!@#\$%^&*()_+{}[]'"table)
| ac925cd     | true     | lbcat2.lowertable                    | **plan**: dropAllForeignKeys(table=lbcat2.lowertable)
| cb2f558     | true     | lbcat2.only_in_lbcat2                | **plan**: dropAllForeignKeys(table=lbcat2.only_in_lbcat2)

# Test Version: "b7d32a" #