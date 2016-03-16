**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can rename from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | newName                              | oldName                              | OPERATIONS
| :---------- | :------- | :----------------------------------- | :----------------------------------- | :------
| 457bc2      | true     | lbcat.4test_table                    | lbcat.anotherlowertable              | **plan**: RENAME TABLE `lbcat`.`anotherlowertable` TO `lbcat`.`4test_table`
| 0b85e8      | true     | lbcat.4test_table                    | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat`.`4test_table`
| 97d94b      | true     | lbcat.4test_table                    | lbcat.lowertable                     | **plan**: RENAME TABLE `lbcat`.`lowertable` TO `lbcat`.`4test_table`
| 21a0ec      | true     | lbcat.4test_table                    | lbcat.only_in_lbcat                  | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`4test_table`
| cc1d2a      | true     | lbcat.anotherlowertable              | lbcat.4test_table                    | **plan**: RENAME TABLE `lbcat`.`4test_table` TO `lbcat`.`anotherlowertable`
| 350253      | true     | lbcat.anotherlowertable              | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat`.`anotherlowertable`
| 0c499d      | true     | lbcat.anotherlowertable              | lbcat.lowertable                     | **plan**: RENAME TABLE `lbcat`.`lowertable` TO `lbcat`.`anotherlowertable`
| fec31a      | true     | lbcat.anotherlowertable              | lbcat.only_in_lbcat                  | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`anotherlowertable`
| 11b8dd      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | lbcat.4test_table                    | **plan**: RENAME TABLE `lbcat`.`4test_table` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| f440c4      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | lbcat.anotherlowertable              | **plan**: RENAME TABLE `lbcat`.`anotherlowertable` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| 9ef9fe      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | lbcat.lowertable                     | **plan**: RENAME TABLE `lbcat`.`lowertable` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| 5fdea7      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | lbcat.only_in_lbcat                  | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| 8d7d2b      | true     | lbcat.lowertable                     | lbcat.4test_table                    | **plan**: RENAME TABLE `lbcat`.`4test_table` TO `lbcat`.`lowertable`
| 80201a      | true     | lbcat.lowertable                     | lbcat.anotherlowertable              | **plan**: RENAME TABLE `lbcat`.`anotherlowertable` TO `lbcat`.`lowertable`
| 385895      | true     | lbcat.lowertable                     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat`.`lowertable`
| be5acd      | true     | lbcat.lowertable                     | lbcat.only_in_lbcat                  | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`lowertable`
| 8648a4      | true     | lbcat.only_in_lbcat                  | lbcat.4test_table                    | **plan**: RENAME TABLE `lbcat`.`4test_table` TO `lbcat`.`only_in_lbcat`
| 2fa3b8      | true     | lbcat.only_in_lbcat                  | lbcat.anotherlowertable              | **plan**: RENAME TABLE `lbcat`.`anotherlowertable` TO `lbcat`.`only_in_lbcat`
| 47fdb3      | true     | lbcat.only_in_lbcat                  | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat`.`only_in_lbcat`
| 1ff51a      | true     | lbcat.only_in_lbcat                  | lbcat.lowertable                     | **plan**: RENAME TABLE `lbcat`.`lowertable` TO `lbcat`.`only_in_lbcat`
| c50316      | true     | lbcat2.4test_table                   | lbcat2.anotherlowertable             | **plan**: RENAME TABLE `lbcat2`.`anotherlowertable` TO `lbcat2`.`4test_table`
| 8f5707      | true     | lbcat2.4test_table                   | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat2`.`4test_table`
| 106e40      | true     | lbcat2.4test_table                   | lbcat2.lowertable                    | **plan**: RENAME TABLE `lbcat2`.`lowertable` TO `lbcat2`.`4test_table`
| d0b283      | true     | lbcat2.4test_table                   | lbcat2.only_in_lbcat2                | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`4test_table`
| 68f6dd      | true     | lbcat2.anotherlowertable             | lbcat2.4test_table                   | **plan**: RENAME TABLE `lbcat2`.`4test_table` TO `lbcat2`.`anotherlowertable`
| fab931      | true     | lbcat2.anotherlowertable             | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat2`.`anotherlowertable`
| 443e91      | true     | lbcat2.anotherlowertable             | lbcat2.lowertable                    | **plan**: RENAME TABLE `lbcat2`.`lowertable` TO `lbcat2`.`anotherlowertable`
| 5a0bd5      | true     | lbcat2.anotherlowertable             | lbcat2.only_in_lbcat2                | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`anotherlowertable`
| 86c600      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | lbcat2.4test_table                   | **plan**: RENAME TABLE `lbcat2`.`4test_table` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| 4908e3      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | lbcat2.anotherlowertable             | **plan**: RENAME TABLE `lbcat2`.`anotherlowertable` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| 16fdf8      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | lbcat2.lowertable                    | **plan**: RENAME TABLE `lbcat2`.`lowertable` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| 6f5b2a      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | lbcat2.only_in_lbcat2                | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| 607228      | true     | lbcat2.lowertable                    | lbcat2.4test_table                   | **plan**: RENAME TABLE `lbcat2`.`4test_table` TO `lbcat2`.`lowertable`
| ca3427      | true     | lbcat2.lowertable                    | lbcat2.anotherlowertable             | **plan**: RENAME TABLE `lbcat2`.`anotherlowertable` TO `lbcat2`.`lowertable`
| e6c943      | true     | lbcat2.lowertable                    | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat2`.`lowertable`
| 972bd3      | true     | lbcat2.lowertable                    | lbcat2.only_in_lbcat2                | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`lowertable`
| a12217      | true     | lbcat2.only_in_lbcat2                | lbcat2.4test_table                   | **plan**: RENAME TABLE `lbcat2`.`4test_table` TO `lbcat2`.`only_in_lbcat2`
| 434dfb      | true     | lbcat2.only_in_lbcat2                | lbcat2.anotherlowertable             | **plan**: RENAME TABLE `lbcat2`.`anotherlowertable` TO `lbcat2`.`only_in_lbcat2`
| 830e6b      | true     | lbcat2.only_in_lbcat2                | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat2`.`only_in_lbcat2`
| 800a4f      | true     | lbcat2.only_in_lbcat2                | lbcat2.lowertable                    | **plan**: RENAME TABLE `lbcat2`.`lowertable` TO `lbcat2`.`only_in_lbcat2`

# Test Version: "13cec8" #