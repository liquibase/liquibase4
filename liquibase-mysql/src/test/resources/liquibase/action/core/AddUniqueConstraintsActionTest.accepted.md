**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple constraints at once" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 2a5ee3      | true     | lbcat      | **plan**: ALTER TABLE `lbcat`.`test_table_1` ADD CONSTRAINT UNIQUE (`col_name`)<br>ALTER TABLE `lbcat`.`test_table_2` ADD CONSTRAINT UNIQUE (`col_name`)<br>ALTER TABLE `lbcat`.`test_table_3` ADD CONSTRAINT UNIQUE (`col_name`)
| 9f6418      | true     | lbcat2     | **plan**: ALTER TABLE `lbcat2`.`test_table_1` ADD CONSTRAINT UNIQUE (`col_name`)<br>ALTER TABLE `lbcat2`.`test_table_2` ADD CONSTRAINT UNIQUE (`col_name`)<br>ALTER TABLE `lbcat2`.`test_table_3` ADD CONSTRAINT UNIQUE (`col_name`)

# Test: "Can apply multi-column with standard settings" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | columnName   | tableName         | OPERATIONS
| :---------- | :------- | :----------- | :---------------- | :------
| ffc5fe      | true     | col_1, col_2 | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`col_1`, `col_2`)
| 7fd924      | true     | col_1, col_2 | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`col_1`, `col_2`)

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | columnName                     | tableName         | OPERATIONS
| :---------- | :------- | :----------------------------- | :---------------- | :------
| f7c80b      | true     | 4test_column                   | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`4test_column`)
| 545c5e      | true     | 4test_column                   | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`4test_column`)
| b24e49      | true     | anotherlowercolumn             | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`anotherlowercolumn`)
| 3f4f42      | true     | anotherlowercolumn             | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`anotherlowercolumn`)
| 883353      | true     | crazy!@#\$%^&*()_+{}[]'"column | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`crazy!@#\$%^&*()_+{}[]'"column`)
| bc8e39      | true     | crazy!@#\$%^&*()_+{}[]'"column | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`crazy!@#\$%^&*()_+{}[]'"column`)
| 380500      | true     | lowercolumn                    | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`lowercolumn`)
| d156a0      | true     | lowercolumn                    | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`lowercolumn`)
| fa8d8f      | true     | only_in_lbcat                  | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`only_in_lbcat`)
| 41257a      | true     | only_in_lbcat                  | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`only_in_lbcat`)
| 9ae3ac      | true     | only_in_lbcat2                 | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`only_in_lbcat2`)
| 049b85      | true     | only_in_lbcat2                 | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`only_in_lbcat2`)

# Test: "Can apply single column with standard settings but complex constraint names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | tableName         | uqName                                   | OPERATIONS
| :---------- | :------- | :---------------- | :--------------------------------------- | :------
| d31ca1      | true     | lbcat.table_name  | 4test_uniqueconstraint                   | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `4test_uniqueconstraint` UNIQUE (`column_name`)
| 989603      | true     | lbcat.table_name  | anotherloweruniqueconstraint             | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `anotherloweruniqueconstraint` UNIQUE (`column_name`)
| 414ac2      | true     | lbcat.table_name  | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint` UNIQUE (`column_name`)
| db8dc8      | true     | lbcat.table_name  | loweruniqueconstraint                    | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `loweruniqueconstraint` UNIQUE (`column_name`)
| b0f8c2      | true     | lbcat.table_name  | only_in_lbcat                            | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `only_in_lbcat` UNIQUE (`column_name`)
| 71ad27      | true     | lbcat.table_name  | only_in_lbcat2                           | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `only_in_lbcat2` UNIQUE (`column_name`)
| 2f8fdc      | true     | lbcat2.table_name | 4test_uniqueconstraint                   | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `4test_uniqueconstraint` UNIQUE (`column_name`)
| f12589      | true     | lbcat2.table_name | anotherloweruniqueconstraint             | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `anotherloweruniqueconstraint` UNIQUE (`column_name`)
| 0d17d3      | true     | lbcat2.table_name | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint` UNIQUE (`column_name`)
| e0c7f8      | true     | lbcat2.table_name | loweruniqueconstraint                    | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `loweruniqueconstraint` UNIQUE (`column_name`)
| 9930e7      | true     | lbcat2.table_name | only_in_lbcat                            | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `only_in_lbcat` UNIQUE (`column_name`)
| 05a77e      | true     | lbcat2.table_name | only_in_lbcat2                           | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `only_in_lbcat2` UNIQUE (`column_name`)

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | tableName                            | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| 6db8bb      | true     | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` ADD CONSTRAINT `uq_name` UNIQUE (`column_name`)
| b2e45f      | true     | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ADD CONSTRAINT `uq_name` UNIQUE (`column_name`)
| 5f0ea5      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT `uq_name` UNIQUE (`column_name`)
| 572208      | true     | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` ADD CONSTRAINT `uq_name` UNIQUE (`column_name`)
| 58d58f      | true     | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ADD CONSTRAINT `uq_name` UNIQUE (`column_name`)
| be2547      | true     | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` ADD CONSTRAINT `uq_name` UNIQUE (`column_name`)
| c493e5      | true     | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ADD CONSTRAINT `uq_name` UNIQUE (`column_name`)
| 94369b      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT `uq_name` UNIQUE (`column_name`)
| 2c35e6      | true     | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` ADD CONSTRAINT `uq_name` UNIQUE (`column_name`)
| 4e636c      | true     | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ADD CONSTRAINT `uq_name` UNIQUE (`column_name`)

# Test: "Can apply unique constraint with with various settings" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | columns              | name    | table  | OPERATIONS
| :---------- | :------- | :------------------- | :------ | :----- | :------
| 27c7dc      | true     | col_name             |         | lbcat  | **plan**: ALTER TABLE `lbcat` ADD CONSTRAINT UNIQUE (`col_name`)
| 0d78d5      | true     | col_name             |         | lbcat2 | **plan**: ALTER TABLE `lbcat2` ADD CONSTRAINT UNIQUE (`col_name`)
| 0d4e0f      | true     | col_name             | uq_test | lbcat  | **plan**: ALTER TABLE `lbcat` ADD CONSTRAINT `uq_test` UNIQUE (`col_name`)
| 61fb0c      | true     | col_name             | uq_test | lbcat2 | **plan**: ALTER TABLE `lbcat2` ADD CONSTRAINT `uq_test` UNIQUE (`col_name`)
| 199789      | true     | col_name1, col_name2 |         | lbcat  | **plan**: ALTER TABLE `lbcat` ADD CONSTRAINT UNIQUE (`col_name1`, `col_name2`)
| e84fb7      | true     | col_name1, col_name2 |         | lbcat2 | **plan**: ALTER TABLE `lbcat2` ADD CONSTRAINT UNIQUE (`col_name1`, `col_name2`)
| b9448c      | true     | col_name1, col_name2 | uq_test | lbcat  | **plan**: ALTER TABLE `lbcat` ADD CONSTRAINT `uq_test` UNIQUE (`col_name1`, `col_name2`)
| 07ee83      | true     | col_name1, col_name2 | uq_test | lbcat2 | **plan**: ALTER TABLE `lbcat2` ADD CONSTRAINT `uq_test` UNIQUE (`col_name1`, `col_name2`)

# Test Version: "a39b39" #