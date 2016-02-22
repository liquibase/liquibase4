**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can apply standard settings to complex names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | columnName                                                          | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------
| 5d5e62      | true     | lbcat.4test_table.4test_column                                      | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| e4fbdf      | true     | lbcat.4test_table.anotherlowercolumn                                | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 2603bc      | true     | lbcat.4test_table.crazy!@#\$%^&*()_+{}[]'"column                    | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| 6bbe0d      | true     | lbcat.4test_table.lowercolumn                                       | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| da3d31      | true     | lbcat.4test_table.only_in_lbcat                                     | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 8c53c7      | true     | lbcat.4test_table.only_in_lbcat2                                    | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| 5e4c76      | true     | lbcat.anotherlowertable.4test_column                                | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| a0d2a5      | true     | lbcat.anotherlowertable.anotherlowercolumn                          | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 5aed99      | true     | lbcat.anotherlowertable.crazy!@#\$%^&*()_+{}[]'"column              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| c74f28      | true     | lbcat.anotherlowertable.lowercolumn                                 | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| b7c3b2      | true     | lbcat.anotherlowertable.only_in_lbcat                               | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 35aa45      | true     | lbcat.anotherlowertable.only_in_lbcat2                              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| 8cb25b      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.4test_column                    | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| 2e61aa      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.anotherlowercolumn              | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| e8f4f8      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.crazy!@#\$%^&*()_+{}[]'"column  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| 6e9126      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.lowercolumn                     | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| 6d6524      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.only_in_lbcat                   | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 343b5f      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.only_in_lbcat2                  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| 900f33      | true     | lbcat.lowertable.4test_column                                       | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| 2c555e      | true     | lbcat.lowertable.anotherlowercolumn                                 | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| a75a70      | true     | lbcat.lowertable.crazy!@#\$%^&*()_+{}[]'"column                     | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| e98236      | true     | lbcat.lowertable.lowercolumn                                        | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| e28069      | true     | lbcat.lowertable.only_in_lbcat                                      | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| fc65dc      | true     | lbcat.lowertable.only_in_lbcat2                                     | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| 3879d7      | true     | lbcat.only_in_lbcat.4test_column                                    | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| 685496      | true     | lbcat.only_in_lbcat.anotherlowercolumn                              | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 9473c9      | true     | lbcat.only_in_lbcat.crazy!@#\$%^&*()_+{}[]'"column                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| 5f9422      | true     | lbcat.only_in_lbcat.lowercolumn                                     | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| 263da9      | true     | lbcat.only_in_lbcat.only_in_lbcat                                   | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 3aff61      | true     | lbcat.only_in_lbcat.only_in_lbcat2                                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| 6a62da      | true     | lbcat2.4test_table.4test_column                                     | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| 4807f6      | true     | lbcat2.4test_table.anotherlowercolumn                               | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| f626f5      | true     | lbcat2.4test_table.crazy!@#\$%^&*()_+{}[]'"column                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| bb93e3      | true     | lbcat2.4test_table.lowercolumn                                      | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| 621c78      | true     | lbcat2.4test_table.only_in_lbcat                                    | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| b184d0      | true     | lbcat2.4test_table.only_in_lbcat2                                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| 1d2dc0      | true     | lbcat2.anotherlowertable.4test_column                               | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| ae2f5f      | true     | lbcat2.anotherlowertable.anotherlowercolumn                         | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 1cf264      | true     | lbcat2.anotherlowertable.crazy!@#\$%^&*()_+{}[]'"column             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| 5528b4      | true     | lbcat2.anotherlowertable.lowercolumn                                | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| 1fd387      | true     | lbcat2.anotherlowertable.only_in_lbcat                              | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 0d034b      | true     | lbcat2.anotherlowertable.only_in_lbcat2                             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| 66dafd      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.4test_column                   | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| 2ff1e0      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.anotherlowercolumn             | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 3afe72      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.crazy!@#\$%^&*()_+{}[]'"column | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| e030ce      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.lowercolumn                    | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| 10221a      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 9de9a2      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.only_in_lbcat2                 | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| cf631e      | true     | lbcat2.lowertable.4test_column                                      | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| 456321      | true     | lbcat2.lowertable.anotherlowercolumn                                | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| a37eed      | true     | lbcat2.lowertable.crazy!@#\$%^&*()_+{}[]'"column                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| 539a1b      | true     | lbcat2.lowertable.lowercolumn                                       | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| 16af61      | true     | lbcat2.lowertable.only_in_lbcat                                     | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 5f7151      | true     | lbcat2.lowertable.only_in_lbcat2                                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| dd2bdf      | true     | lbcat2.only_in_lbcat2.4test_column                                  | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| e257b2      | true     | lbcat2.only_in_lbcat2.anotherlowercolumn                            | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 9ef138      | true     | lbcat2.only_in_lbcat2.crazy!@#\$%^&*()_+{}[]'"column                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| 920af4      | true     | lbcat2.only_in_lbcat2.lowercolumn                                   | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| 3bc903      | true     | lbcat2.only_in_lbcat2.only_in_lbcat                                 | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| ef5fdd      | true     | lbcat2.only_in_lbcat2.only_in_lbcat2                                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT

# Test: "Valid parameter permutations work" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | column                 | dataType | infoObject | startWith | OPERATIONS
| :---------- | :------- | :--------------------- | :------- | :--------- | :-------- | :------
| b5ecd6      | true     | table_name.column_name | INTEGER  | false      |           | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT
| 48faf1      | true     | table_name.column_name | INTEGER  | true       |           | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT
| ba5eee      | true     | table_name.column_name | INTEGER  | true       | 1         | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT<br>ALTER TABLE `table_name` AUTO_INCREMENT=1
| 92cf3b      | true     | table_name.column_name | INTEGER  | true       | 10        | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT<br>ALTER TABLE `table_name` AUTO_INCREMENT=10
| 15f549      | true     | table_name.column_name | INTEGER  | true       | 2         | **plan**: ALTER TABLE `table_name` MODIFY `column_name` INTEGER AUTO_INCREMENT<br>ALTER TABLE `table_name` AUTO_INCREMENT=2

# Test Version: "99250f" #