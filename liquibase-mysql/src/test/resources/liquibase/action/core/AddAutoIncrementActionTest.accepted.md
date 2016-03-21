**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can apply standard settings to complex names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                                                              | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------
| 1530675     | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| b636c88     | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 672f06a     | true     | lbcat.4test_table.UPPERCOLUMN                                       | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| 888be33     | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 51cb8f8     | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| dd60db9     | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| f4ef516     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 7871953     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 59c8cf3     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| f34cbc1     | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| e67310a     | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 437de8b     | true     | lbcat.lowertable.UPPERCOLUMN                                        | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| fe26032     | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| ca5bd8d     | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 156320d     | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| c9049c1     | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 3857017     | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 49c17c9     | true     | lbcat2.4test_table.UPPERCOLUMN                                      | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| ae8523a     | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| c93c64f     | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| aca4ce5     | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| 7775c73     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 7e364d7     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| f633518     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| c52a7fc     | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 524220f     | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| bb0d857     | true     | lbcat2.lowertable.UPPERCOLUMN                                       | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| a332625     | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 1b9ea13     | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| eadefc4     | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT

# Test: "Valid parameter permutations work" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                 | dataType | infoObject | startWith | OPERATIONS
| :---------- | :------- | :--------------------- | :------- | :--------- | :-------- | :------
| 0889dae     | true     | table_name.column_name | INTEGER  | false      |           | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT
| 7c8b150     | true     | table_name.column_name | INTEGER  | true       |           | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT
| 34257a4     | true     | table_name.column_name | INTEGER  | true       | 1         | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT<br>ALTER TABLE `table_name` AUTO_INCREMENT=1
| 5cdacd0     | true     | table_name.column_name | INTEGER  | true       | 10        | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT<br>ALTER TABLE `table_name` AUTO_INCREMENT=10
| f0d0a36     | true     | table_name.column_name | INTEGER  | true       | 2         | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT<br>ALTER TABLE `table_name` AUTO_INCREMENT=2

# Test Version: "192517" #