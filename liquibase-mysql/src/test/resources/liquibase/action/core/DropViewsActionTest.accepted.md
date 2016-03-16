**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can drop from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | view                                | OPERATIONS
| :---------- | :------- | :---------------------------------- | :------
| a2b840      | true     | 4test_view                          | **plan**: DROP VIEW `4test_view`
| 288ee4      | true     | anotherlowerview                    | **plan**: DROP VIEW `anotherlowerview`
| 17e9a4      | true     | crazy!@#\$%^&*()_+{}[]'"view        | **plan**: DROP VIEW `crazy!@#\$%^&*()_+{}[]'"view`
| 9690a6      | true     | lbcat.4test_view                    | **plan**: DROP VIEW `lbcat`.`4test_view`
| 69f9c4      | true     | lbcat.anotherlowerview              | **plan**: DROP VIEW `lbcat`.`anotherlowerview`
| 86d58a      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | **plan**: DROP VIEW `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view`
| 21cd30      | true     | lbcat.lowerview                     | **plan**: DROP VIEW `lbcat`.`lowerview`
| 150896      | true     | lbcat2.4test_view                   | **plan**: DROP VIEW `lbcat2`.`4test_view`
| d8d4be      | true     | lbcat2.anotherlowerview             | **plan**: DROP VIEW `lbcat2`.`anotherlowerview`
| 655b26      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | **plan**: DROP VIEW `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view`
| eea9d3      | true     | lbcat2.lowerview                    | **plan**: DROP VIEW `lbcat2`.`lowerview`
| b0fa01      | true     | lowerview                           | **plan**: DROP VIEW `lowerview`

# Test Version: "d18c1e" #