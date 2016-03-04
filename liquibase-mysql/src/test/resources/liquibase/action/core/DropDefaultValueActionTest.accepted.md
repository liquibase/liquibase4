**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can drop default values on complex names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                                                              | dataType | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------- | :------
| 153067      | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                |          | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| a0acc5      | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| b636c8      | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    |          | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 94be33      | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 672f06      | true     | lbcat.4test_table.UPPERCOLUMN                                       |          | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| d4ced6      | true     | lbcat.4test_table.UPPERCOLUMN                                       | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 888be3      | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          |          | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| dcc797      | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 51cb8f      | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              |          | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 5e6925      | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| dd60db      | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 |          | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| b7484f      | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| f4ef51      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              |          | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 003d25      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 787195      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  |          | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| ed795c      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 59c8cf      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     |          | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 758157      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| f34cbc      | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 |          | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 518dba      | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| e67310      | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     |          | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 13b3f9      | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 437de8      | true     | lbcat.lowertable.UPPERCOLUMN                                        |          | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| fdf698      | true     | lbcat.lowertable.UPPERCOLUMN                                        | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| fe2603      | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              |          | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 478dd9      | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| ca5bd8      | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  |          | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| ec8026      | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 156320      | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     |          | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 6a38fc      | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| c9049c      | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               |          | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 7407e8      | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 385701      | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   |          | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| c8aae6      | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 49c17c      | true     | lbcat2.4test_table.UPPERCOLUMN                                      |          | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| ff8969      | true     | lbcat2.4test_table.UPPERCOLUMN                                      | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| ae8523      | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         |          | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| c88778      | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| c93c64      | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             |          | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 4042e5      | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| aca4ce      | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                |          | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| e8cfdd      | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 7775c7      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             |          | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| b7c132      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 7e364d      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN |          | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 8a34e8      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| f63351      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    |          | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 715871      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| c52a7f      | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                |          | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| b1dc9d      | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 524220      | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    |          | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 298a0f      | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| bb0d85      | true     | lbcat2.lowertable.UPPERCOLUMN                                       |          | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| adc5b0      | true     | lbcat2.lowertable.UPPERCOLUMN                                       | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| a33262      | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            |          | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 444cf3      | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 1b9ea1      | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                |          | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 4a5627      | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| eadefc      | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   |          | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| e6c9a9      | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT

# Test Version: "c63cae" #