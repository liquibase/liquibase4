**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can apply standard settings to complex names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | columnName                                                                   | OPERATIONS
| :---------- | :------- | :--------------------------------------------------------------------------- | :------
| 8d0275      | true     | lbcat.4test_table.4test_column (COLUMN)                                      | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| b47683      | true     | lbcat.4test_table.anotherlowercolumn (COLUMN)                                | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 761b6a      | true     | lbcat.4test_table.crazy!@#\$%^&*()_+{}[]'"column (COLUMN)                    | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| 3aaa74      | true     | lbcat.4test_table.lowercolumn (COLUMN)                                       | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| e401b7      | true     | lbcat.4test_table.only_in_lbcat (COLUMN)                                     | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| abc6cc      | true     | lbcat.4test_table.only_in_lbcat2 (COLUMN)                                    | **plan**: ALTER TABLE `lbcat`.`4test_table` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| c83ad0      | true     | lbcat.anotherlowertable.4test_column (COLUMN)                                | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| e0a592      | true     | lbcat.anotherlowertable.anotherlowercolumn (COLUMN)                          | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 66bc14      | true     | lbcat.anotherlowertable.crazy!@#\$%^&*()_+{}[]'"column (COLUMN)              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| de5862      | true     | lbcat.anotherlowertable.lowercolumn (COLUMN)                                 | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| d2ffcb      | true     | lbcat.anotherlowertable.only_in_lbcat (COLUMN)                               | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 49bab2      | true     | lbcat.anotherlowertable.only_in_lbcat2 (COLUMN)                              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| a2021b      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.4test_column (COLUMN)                    | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| d63e4d      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.anotherlowercolumn (COLUMN)              | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 9f3d93      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.crazy!@#\$%^&*()_+{}[]'"column (COLUMN)  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| 23c595      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.lowercolumn (COLUMN)                     | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| 51c588      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.only_in_lbcat (COLUMN)                   | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 554ec0      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.only_in_lbcat2 (COLUMN)                  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| 3dca93      | true     | lbcat.lowertable.4test_column (COLUMN)                                       | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| 138a09      | true     | lbcat.lowertable.anotherlowercolumn (COLUMN)                                 | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 6505f7      | true     | lbcat.lowertable.crazy!@#\$%^&*()_+{}[]'"column (COLUMN)                     | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| 97ccee      | true     | lbcat.lowertable.lowercolumn (COLUMN)                                        | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| ee79e0      | true     | lbcat.lowertable.only_in_lbcat (COLUMN)                                      | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 5903a8      | true     | lbcat.lowertable.only_in_lbcat2 (COLUMN)                                     | **plan**: ALTER TABLE `lbcat`.`lowertable` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| d49164      | true     | lbcat.only_in_lbcat.4test_column (COLUMN)                                    | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| 25833e      | true     | lbcat.only_in_lbcat.anotherlowercolumn (COLUMN)                              | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 738609      | true     | lbcat.only_in_lbcat.crazy!@#\$%^&*()_+{}[]'"column (COLUMN)                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| d1036e      | true     | lbcat.only_in_lbcat.lowercolumn (COLUMN)                                     | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| 0ecbf6      | true     | lbcat.only_in_lbcat.only_in_lbcat (COLUMN)                                   | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 8ae252      | true     | lbcat.only_in_lbcat.only_in_lbcat2 (COLUMN)                                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| bbb234      | true     | lbcat2.4test_table.4test_column (COLUMN)                                     | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| 45016a      | true     | lbcat2.4test_table.anotherlowercolumn (COLUMN)                               | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 3aa852      | true     | lbcat2.4test_table.crazy!@#\$%^&*()_+{}[]'"column (COLUMN)                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| 2f34da      | true     | lbcat2.4test_table.lowercolumn (COLUMN)                                      | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| 16df81      | true     | lbcat2.4test_table.only_in_lbcat (COLUMN)                                    | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| e81721      | true     | lbcat2.4test_table.only_in_lbcat2 (COLUMN)                                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| 1a9c9e      | true     | lbcat2.anotherlowertable.4test_column (COLUMN)                               | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| 0a492d      | true     | lbcat2.anotherlowertable.anotherlowercolumn (COLUMN)                         | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 66457c      | true     | lbcat2.anotherlowertable.crazy!@#\$%^&*()_+{}[]'"column (COLUMN)             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| a89496      | true     | lbcat2.anotherlowertable.lowercolumn (COLUMN)                                | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| 5a1710      | true     | lbcat2.anotherlowertable.only_in_lbcat (COLUMN)                              | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 587fc3      | true     | lbcat2.anotherlowertable.only_in_lbcat2 (COLUMN)                             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| 24642d      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.4test_column (COLUMN)                   | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| 2aaefa      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.anotherlowercolumn (COLUMN)             | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 57b417      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.crazy!@#\$%^&*()_+{}[]'"column (COLUMN) | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| 10f398      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.lowercolumn (COLUMN)                    | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| e5101b      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.only_in_lbcat (COLUMN)                  | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 8682da      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.only_in_lbcat2 (COLUMN)                 | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| 268e53      | true     | lbcat2.lowertable.4test_column (COLUMN)                                      | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| 08114a      | true     | lbcat2.lowertable.anotherlowercolumn (COLUMN)                                | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| 6e55c2      | true     | lbcat2.lowertable.crazy!@#\$%^&*()_+{}[]'"column (COLUMN)                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| 53f19e      | true     | lbcat2.lowertable.lowercolumn (COLUMN)                                       | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| 27277d      | true     | lbcat2.lowertable.only_in_lbcat (COLUMN)                                     | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 8d9671      | true     | lbcat2.lowertable.only_in_lbcat2 (COLUMN)                                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT
| d052c8      | true     | lbcat2.only_in_lbcat2.4test_column (COLUMN)                                  | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `4test_column` INTEGER AUTO_INCREMENT
| fe71e5      | true     | lbcat2.only_in_lbcat2.anotherlowercolumn (COLUMN)                            | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `anotherlowercolumn` INTEGER AUTO_INCREMENT
| be97e2      | true     | lbcat2.only_in_lbcat2.crazy!@#\$%^&*()_+{}[]'"column (COLUMN)                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `crazy!@#\$%^&*()_+{}[]'"column` INTEGER AUTO_INCREMENT
| e58c3c      | true     | lbcat2.only_in_lbcat2.lowercolumn (COLUMN)                                   | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `lowercolumn` INTEGER AUTO_INCREMENT
| dfd19c      | true     | lbcat2.only_in_lbcat2.only_in_lbcat (COLUMN)                                 | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `only_in_lbcat` INTEGER AUTO_INCREMENT
| 235027      | true     | lbcat2.only_in_lbcat2.only_in_lbcat2 (COLUMN)                                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` MODIFY `only_in_lbcat2` INTEGER AUTO_INCREMENT

# Test: "Valid parameter permutations work" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | columnName                    | startWith | OPERATIONS
| :---------- | :------- | :---------------------------- | :-------- | :------
| 15d8ef      | true     | lbcat.table1.column1 (COLUMN) |           | **plan**: ALTER TABLE `lbcat`.`table1` MODIFY `column1` INTEGER AUTO_INCREMENT
| 99a040      | true     | lbcat.table1.column1 (COLUMN) | 1         | **plan**: ALTER TABLE `lbcat`.`table1` MODIFY `column1` INTEGER AUTO_INCREMENT<br>AND THEN: ALTER TABLE `lbcat`.`table1` AUTO_INCREMENT=1
| 8b1fdf      | true     | lbcat.table1.column1 (COLUMN) | 10        | **plan**: ALTER TABLE `lbcat`.`table1` MODIFY `column1` INTEGER AUTO_INCREMENT<br>AND THEN: ALTER TABLE `lbcat`.`table1` AUTO_INCREMENT=10
| 8296e8      | true     | lbcat.table1.column1 (COLUMN) | 2         | **plan**: ALTER TABLE `lbcat`.`table1` MODIFY `column1` INTEGER AUTO_INCREMENT<br>AND THEN: ALTER TABLE `lbcat`.`table1` AUTO_INCREMENT=2

# Test Version: "d0b898" #