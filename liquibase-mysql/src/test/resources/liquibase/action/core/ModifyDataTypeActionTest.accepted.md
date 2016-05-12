**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can run from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                                                              | newDataType | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :---------- | :------
| a39a1ad     | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | BIGINT      | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `ANOTHERUPPERCOLUMN` BIGINT
| 831930c     | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | BIGINT      | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` BIGINT
| f206fd1     | true     | lbcat.4test_table.UPPERCOLUMN                                       | BIGINT      | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `UPPERCOLUMN` BIGINT
| 3fe302d     | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | BIGINT      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `ANOTHERUPPERCOLUMN` BIGINT
| afd24cc     | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | BIGINT      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` BIGINT
| 2835967     | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | BIGINT      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `UPPERCOLUMN` BIGINT
| 829961a     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | BIGINT      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `ANOTHERUPPERCOLUMN` BIGINT
| 5d0d22d     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | BIGINT      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` BIGINT
| f474a1a     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | BIGINT      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `UPPERCOLUMN` BIGINT
| 7bef468     | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | BIGINT      | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `ANOTHERUPPERCOLUMN` BIGINT
| dea669b     | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | BIGINT      | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` BIGINT
| 12f6189     | true     | lbcat.lowertable.UPPERCOLUMN                                        | BIGINT      | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `UPPERCOLUMN` BIGINT
| 45f7e86     | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | BIGINT      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `ANOTHERUPPERCOLUMN` BIGINT
| cdd9a2a     | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | BIGINT      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` BIGINT
| cc61592     | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | BIGINT      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `UPPERCOLUMN` BIGINT
| 17682b8     | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `ANOTHERUPPERCOLUMN` BIGINT
| fb75925     | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` BIGINT
| 504b191     | true     | lbcat2.4test_table.UPPERCOLUMN                                      | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `UPPERCOLUMN` BIGINT
| 52c884e     | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `ANOTHERUPPERCOLUMN` BIGINT
| 0f75573     | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` BIGINT
| 5cf6c86     | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `UPPERCOLUMN` BIGINT
| 800b52c     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `ANOTHERUPPERCOLUMN` BIGINT
| 1c30a59     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` BIGINT
| 937fb30     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `UPPERCOLUMN` BIGINT
| 5aaa36e     | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `ANOTHERUPPERCOLUMN` BIGINT
| e99f89d     | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` BIGINT
| 0d6201c     | true     | lbcat2.lowertable.UPPERCOLUMN                                       | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `UPPERCOLUMN` BIGINT
| d33179d     | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `ANOTHERUPPERCOLUMN` BIGINT
| db0fe77     | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` BIGINT
| 783502b     | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | BIGINT      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `UPPERCOLUMN` BIGINT

# Test Version: "c5a11f" #