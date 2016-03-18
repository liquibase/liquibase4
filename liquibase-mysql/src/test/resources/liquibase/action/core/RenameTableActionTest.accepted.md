**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can rename from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | newName                              | oldName                              | OPERATIONS
| :---------- | :------- | :----------------------------------- | :----------------------------------- | :------
| 457bc28     | true     | lbcat.4test_table                    | lbcat.anotherlowertable              | **plan**: RENAME TABLE `lbcat`.`anotherlowertable` TO `lbcat`.`4test_table`
| 0b85e8b     | true     | lbcat.4test_table                    | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat`.`4test_table`
| 97d94b6     | true     | lbcat.4test_table                    | lbcat.lowertable                     | **plan**: RENAME TABLE `lbcat`.`lowertable` TO `lbcat`.`4test_table`
| 21a0ec2     | true     | lbcat.4test_table                    | lbcat.only_in_lbcat                  | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`4test_table`
| cc1d2a0     | true     | lbcat.anotherlowertable              | lbcat.4test_table                    | **plan**: RENAME TABLE `lbcat`.`4test_table` TO `lbcat`.`anotherlowertable`
| 350253d     | true     | lbcat.anotherlowertable              | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat`.`anotherlowertable`
| 0c499df     | true     | lbcat.anotherlowertable              | lbcat.lowertable                     | **plan**: RENAME TABLE `lbcat`.`lowertable` TO `lbcat`.`anotherlowertable`
| fec31a5     | true     | lbcat.anotherlowertable              | lbcat.only_in_lbcat                  | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`anotherlowertable`
| 11b8dde     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | lbcat.4test_table                    | **plan**: RENAME TABLE `lbcat`.`4test_table` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| f440c43     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | lbcat.anotherlowertable              | **plan**: RENAME TABLE `lbcat`.`anotherlowertable` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| 9ef9fee     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | lbcat.lowertable                     | **plan**: RENAME TABLE `lbcat`.`lowertable` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| 5fdea7e     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | lbcat.only_in_lbcat                  | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| 8d7d2bc     | true     | lbcat.lowertable                     | lbcat.4test_table                    | **plan**: RENAME TABLE `lbcat`.`4test_table` TO `lbcat`.`lowertable`
| 80201aa     | true     | lbcat.lowertable                     | lbcat.anotherlowertable              | **plan**: RENAME TABLE `lbcat`.`anotherlowertable` TO `lbcat`.`lowertable`
| 3858950     | true     | lbcat.lowertable                     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat`.`lowertable`
| be5acdf     | true     | lbcat.lowertable                     | lbcat.only_in_lbcat                  | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`lowertable`
| 8648a45     | true     | lbcat.only_in_lbcat                  | lbcat.4test_table                    | **plan**: RENAME TABLE `lbcat`.`4test_table` TO `lbcat`.`only_in_lbcat`
| 2fa3b82     | true     | lbcat.only_in_lbcat                  | lbcat.anotherlowertable              | **plan**: RENAME TABLE `lbcat`.`anotherlowertable` TO `lbcat`.`only_in_lbcat`
| 47fdb31     | true     | lbcat.only_in_lbcat                  | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat`.`only_in_lbcat`
| 1ff51a8     | true     | lbcat.only_in_lbcat                  | lbcat.lowertable                     | **plan**: RENAME TABLE `lbcat`.`lowertable` TO `lbcat`.`only_in_lbcat`
| c50316d     | true     | lbcat2.4test_table                   | lbcat2.anotherlowertable             | **plan**: RENAME TABLE `lbcat2`.`anotherlowertable` TO `lbcat2`.`4test_table`
| 8f5707f     | true     | lbcat2.4test_table                   | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat2`.`4test_table`
| 106e407     | true     | lbcat2.4test_table                   | lbcat2.lowertable                    | **plan**: RENAME TABLE `lbcat2`.`lowertable` TO `lbcat2`.`4test_table`
| d0b2833     | true     | lbcat2.4test_table                   | lbcat2.only_in_lbcat2                | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`4test_table`
| 68f6dd9     | true     | lbcat2.anotherlowertable             | lbcat2.4test_table                   | **plan**: RENAME TABLE `lbcat2`.`4test_table` TO `lbcat2`.`anotherlowertable`
| fab931e     | true     | lbcat2.anotherlowertable             | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat2`.`anotherlowertable`
| 443e918     | true     | lbcat2.anotherlowertable             | lbcat2.lowertable                    | **plan**: RENAME TABLE `lbcat2`.`lowertable` TO `lbcat2`.`anotherlowertable`
| 5a0bd50     | true     | lbcat2.anotherlowertable             | lbcat2.only_in_lbcat2                | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`anotherlowertable`
| 86c6004     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | lbcat2.4test_table                   | **plan**: RENAME TABLE `lbcat2`.`4test_table` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| 4908e3c     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | lbcat2.anotherlowertable             | **plan**: RENAME TABLE `lbcat2`.`anotherlowertable` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| 16fdf8c     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | lbcat2.lowertable                    | **plan**: RENAME TABLE `lbcat2`.`lowertable` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| 6f5b2ae     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | lbcat2.only_in_lbcat2                | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| 6072285     | true     | lbcat2.lowertable                    | lbcat2.4test_table                   | **plan**: RENAME TABLE `lbcat2`.`4test_table` TO `lbcat2`.`lowertable`
| ca34274     | true     | lbcat2.lowertable                    | lbcat2.anotherlowertable             | **plan**: RENAME TABLE `lbcat2`.`anotherlowertable` TO `lbcat2`.`lowertable`
| e6c943b     | true     | lbcat2.lowertable                    | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat2`.`lowertable`
| 972bd37     | true     | lbcat2.lowertable                    | lbcat2.only_in_lbcat2                | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`lowertable`
| a122175     | true     | lbcat2.only_in_lbcat2                | lbcat2.4test_table                   | **plan**: RENAME TABLE `lbcat2`.`4test_table` TO `lbcat2`.`only_in_lbcat2`
| 434dfbd     | true     | lbcat2.only_in_lbcat2                | lbcat2.anotherlowertable             | **plan**: RENAME TABLE `lbcat2`.`anotherlowertable` TO `lbcat2`.`only_in_lbcat2`
| 830e6b8     | true     | lbcat2.only_in_lbcat2                | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` TO `lbcat2`.`only_in_lbcat2`
| 800a4ff     | true     | lbcat2.only_in_lbcat2                | lbcat2.lowertable                    | **plan**: RENAME TABLE `lbcat2`.`lowertable` TO `lbcat2`.`only_in_lbcat2`

# Test Version: "5d5054" #