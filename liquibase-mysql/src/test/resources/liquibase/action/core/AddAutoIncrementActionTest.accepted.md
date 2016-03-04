**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can apply standard settings to complex names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                                                              | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------
| 153067      | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| b636c8      | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 672f06      | true     | lbcat.4test_table.UPPERCOLUMN                                       | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| 888be3      | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 51cb8f      | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| dd60db      | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| f4ef51      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 787195      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 59c8cf      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| f34cbc      | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| e67310      | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 437de8      | true     | lbcat.lowertable.UPPERCOLUMN                                        | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| fe2603      | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| ca5bd8      | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 156320      | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| c9049c      | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 385701      | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| 49c17c      | true     | lbcat2.4test_table.UPPERCOLUMN                                      | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| ae8523      | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| c93c64      | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| aca4ce      | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| 7775c7      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 7e364d      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| f63351      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| c52a7f      | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 524220      | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| bb0d85      | true     | lbcat2.lowertable.UPPERCOLUMN                                       | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT
| a33262      | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `ANOTHERUPPERCOLUMN` INTEGER AUTO_INCREMENT
| 1b9ea1      | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` INTEGER AUTO_INCREMENT
| eadefc      | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `UPPERCOLUMN` INTEGER AUTO_INCREMENT

# Test: "Valid parameter permutations work" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                 | dataType | infoObject | startWith | OPERATIONS
| :---------- | :------- | :--------------------- | :------- | :--------- | :-------- | :------
| 0889da      | true     | table_name.column_name | INTEGER  | false      |           | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT
| 7c8b15      | true     | table_name.column_name | INTEGER  | true       |           | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT
| 34257a      | true     | table_name.column_name | INTEGER  | true       | 1         | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT<br>ALTER TABLE `table_name` AUTO_INCREMENT=1
| 5cdacd      | true     | table_name.column_name | INTEGER  | true       | 10        | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT<br>ALTER TABLE `table_name` AUTO_INCREMENT=10
| f0d0a3      | true     | table_name.column_name | INTEGER  | true       | 2         | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT<br>ALTER TABLE `table_name` AUTO_INCREMENT=2

# Test Version: "9932ac" #