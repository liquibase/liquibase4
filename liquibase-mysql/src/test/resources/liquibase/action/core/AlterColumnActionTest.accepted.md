**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can alter columns" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                                                              | newDefinition | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------------ | :------
| ee1aa2      | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | int           | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `ANOTHERUPPERCOLUMN` int
| 3a56eb      | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | int           | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` int
| d421ed      | true     | lbcat.4test_table.UPPERCOLUMN                                       | int           | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `UPPERCOLUMN` int
| c78f65      | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | int           | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `ANOTHERUPPERCOLUMN` int
| 8d158c      | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | int           | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` int
| 7acb02      | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | int           | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `UPPERCOLUMN` int
| 46315a      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | int           | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `ANOTHERUPPERCOLUMN` int
| 438c01      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | int           | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` int
| 59add8      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | int           | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `UPPERCOLUMN` int
| 173f21      | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | int           | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `ANOTHERUPPERCOLUMN` int
| ebebae      | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | int           | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` int
| 4ea67e      | true     | lbcat.lowertable.UPPERCOLUMN                                        | int           | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `UPPERCOLUMN` int
| 68e013      | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | int           | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `ANOTHERUPPERCOLUMN` int
| d8ad69      | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | int           | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` int
| a9bcb8      | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | int           | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `UPPERCOLUMN` int
| 2f9176      | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | int           | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `ANOTHERUPPERCOLUMN` int
| 0cecdf      | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | int           | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` int
| f117cd      | true     | lbcat2.4test_table.UPPERCOLUMN                                      | int           | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `UPPERCOLUMN` int
| e5b581      | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | int           | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `ANOTHERUPPERCOLUMN` int
| eb5879      | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | int           | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` int
| 0d77fe      | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | int           | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `UPPERCOLUMN` int
| 0aef99      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | int           | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `ANOTHERUPPERCOLUMN` int
| 439199      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | int           | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` int
| b29a41      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | int           | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `UPPERCOLUMN` int
| 61d41a      | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | int           | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `ANOTHERUPPERCOLUMN` int
| 44241a      | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | int           | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` int
| 6f118c      | true     | lbcat2.lowertable.UPPERCOLUMN                                       | int           | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `UPPERCOLUMN` int
| 2a89e7      | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | int           | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `ANOTHERUPPERCOLUMN` int
| 837305      | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | int           | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` int
| 8b7ad6      | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | int           | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `UPPERCOLUMN` int

# Test Version: "15d028" #