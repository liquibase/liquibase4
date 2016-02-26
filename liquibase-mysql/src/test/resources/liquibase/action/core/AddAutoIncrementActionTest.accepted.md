**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can apply standard settings to complex names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | column                                                              | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------
| 7baeba      | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 32862a      | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 4e4b15      | true     | lbcat.4test_table.UPPERCOLUMN                                       | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| 42f816      | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| d412fa      | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 4c651d      | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| 43db30      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| a814fc      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| d4ade5      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| edf518      | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| fc2242      | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| a0f9b1      | true     | lbcat.lowertable.UPPERCOLUMN                                        | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| 7d7666      | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 5f1036      | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 795b5d      | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| ed78ab      | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 6a3196      | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 2f1058      | true     | lbcat2.4test_table.UPPERCOLUMN                                      | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| f218f9      | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 16813a      | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| ecf614      | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| 7a8c33      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| d6dfd5      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| b4bfdd      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| ee34ab      | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 00e79a      | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| de281d      | true     | lbcat2.lowertable.UPPERCOLUMN                                       | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| a717ae      | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| d17a9d      | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| dafb30      | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT

# Test: "Valid parameter permutations work" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | column                 | dataType | infoObject | startWith | OPERATIONS
| :---------- | :------- | :--------------------- | :------- | :--------- | :-------- | :------
| b5ecd6      | true     | table_name.column_name | INTEGER  | false      |           | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT
| 48faf1      | true     | table_name.column_name | INTEGER  | true       |           | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT
| ba5eee      | true     | table_name.column_name | INTEGER  | true       | 1         | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT<br>ALTER TABLE `table_name` AUTO_INCREMENT=1
| 92cf3b      | true     | table_name.column_name | INTEGER  | true       | 10        | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT<br>ALTER TABLE `table_name` AUTO_INCREMENT=10
| 15f549      | true     | table_name.column_name | INTEGER  | true       | 2         | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT<br>ALTER TABLE `table_name` AUTO_INCREMENT=2

# Test Version: "d397b9" #