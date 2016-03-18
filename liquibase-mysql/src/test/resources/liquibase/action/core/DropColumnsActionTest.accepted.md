**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can drop from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                                                              | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------
| 1530675     | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | **plan**: ALTER TABLE `lbcat`.`4test_table` DROP COLUMN `ANOTHERUPPERCOLUMN`
| b636c88     | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: ALTER TABLE `lbcat`.`4test_table` DROP COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`
| 672f06a     | true     | lbcat.4test_table.UPPERCOLUMN                                       | **plan**: ALTER TABLE `lbcat`.`4test_table` DROP COLUMN `UPPERCOLUMN`
| 888be33     | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` DROP COLUMN `ANOTHERUPPERCOLUMN`
| 51cb8f8     | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` DROP COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`
| dd60db9     | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` DROP COLUMN `UPPERCOLUMN`
| f4ef516     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` DROP COLUMN `ANOTHERUPPERCOLUMN`
| 7871953     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` DROP COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`
| 59c8cf3     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` DROP COLUMN `UPPERCOLUMN`
| f34cbc1     | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | **plan**: ALTER TABLE `lbcat`.`lowertable` DROP COLUMN `ANOTHERUPPERCOLUMN`
| e67310a     | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | **plan**: ALTER TABLE `lbcat`.`lowertable` DROP COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`
| 437de8b     | true     | lbcat.lowertable.UPPERCOLUMN                                        | **plan**: ALTER TABLE `lbcat`.`lowertable` DROP COLUMN `UPPERCOLUMN`
| fe26032     | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` DROP COLUMN `ANOTHERUPPERCOLUMN`
| ca5bd8d     | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` DROP COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`
| 156320d     | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` DROP COLUMN `UPPERCOLUMN`
| c9049c1     | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | **plan**: ALTER TABLE `lbcat2`.`4test_table` DROP COLUMN `ANOTHERUPPERCOLUMN`
| 3857017     | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` DROP COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`
| 49c17c9     | true     | lbcat2.4test_table.UPPERCOLUMN                                      | **plan**: ALTER TABLE `lbcat2`.`4test_table` DROP COLUMN `UPPERCOLUMN`
| ae8523a     | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` DROP COLUMN `ANOTHERUPPERCOLUMN`
| c93c64f     | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` DROP COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`
| aca4ce5     | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` DROP COLUMN `UPPERCOLUMN`
| 7775c73     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` DROP COLUMN `ANOTHERUPPERCOLUMN`
| 7e364d7     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` DROP COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`
| f633518     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` DROP COLUMN `UPPERCOLUMN`
| c52a7fc     | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | **plan**: ALTER TABLE `lbcat2`.`lowertable` DROP COLUMN `ANOTHERUPPERCOLUMN`
| 524220f     | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` DROP COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`
| bb0d857     | true     | lbcat2.lowertable.UPPERCOLUMN                                       | **plan**: ALTER TABLE `lbcat2`.`lowertable` DROP COLUMN `UPPERCOLUMN`
| a332625     | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` DROP COLUMN `ANOTHERUPPERCOLUMN`
| 1b9ea13     | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` DROP COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`
| eadefc4     | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` DROP COLUMN `UPPERCOLUMN`

# Test Version: "5dbcb8" #