**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple constraints at once" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | schema | OPERATIONS
| :---------- | :------- | :----- | :------
| 4935d9      | true     | lbcat  | **plan**: ALTER TABLE `lbcat`.`test_table_1` ADD CONSTRAINT UNIQUE (`COL_NAME`)<br>ALTER TABLE `lbcat`.`test_table_2` ADD CONSTRAINT UNIQUE (`COL_NAME`)<br>ALTER TABLE `lbcat`.`test_table_3` ADD CONSTRAINT UNIQUE (`COL_NAME`)
| e78147      | true     | lbcat2 | **plan**: ALTER TABLE `lbcat2`.`test_table_1` ADD CONSTRAINT UNIQUE (`COL_NAME`)<br>ALTER TABLE `lbcat2`.`test_table_2` ADD CONSTRAINT UNIQUE (`COL_NAME`)<br>ALTER TABLE `lbcat2`.`test_table_3` ADD CONSTRAINT UNIQUE (`COL_NAME`)

# Test: "Can apply multi-column with standard settings" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | column       | table             | OPERATIONS
| :---------- | :------- | :----------- | :---------------- | :------
| 050402      | true     | COL_1, COL_2 | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`COL_1`, `COL_2`)
| e29cb5      | true     | COL_1, COL_2 | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`COL_1`, `COL_2`)

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | column                         | table             | OPERATIONS
| :---------- | :------- | :----------------------------- | :---------------- | :------
| 1ab753      | true     | ANOTHERUPPERCOLUMN             | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`ANOTHERUPPERCOLUMN`)
| 19df9c      | true     | ANOTHERUPPERCOLUMN             | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`ANOTHERUPPERCOLUMN`)
| b2810f      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`)
| 46ab16      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`)
| ec721d      | true     | UPPERCOLUMN                    | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`UPPERCOLUMN`)
| 8e172b      | true     | UPPERCOLUMN                    | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`UPPERCOLUMN`)

# Test: "Can apply single column with standard settings but complex constraint names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | table             | uq                                       | OPERATIONS
| :---------- | :------- | :---------------- | :--------------------------------------- | :------
| 7924d5      | true     | lbcat.table_name  | 4test_uniqueconstraint                   | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `4test_uniqueconstraint` UNIQUE (`COLUMN_NAME`)
| 0ef673      | true     | lbcat.table_name  | anotherloweruniqueconstraint             | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `anotherloweruniqueconstraint` UNIQUE (`COLUMN_NAME`)
| 03b6e6      | true     | lbcat.table_name  | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint` UNIQUE (`COLUMN_NAME`)
| 6d9d5a      | true     | lbcat.table_name  | loweruniqueconstraint                    | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `loweruniqueconstraint` UNIQUE (`COLUMN_NAME`)
| ffffec      | true     | lbcat2.table_name | 4test_uniqueconstraint                   | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `4test_uniqueconstraint` UNIQUE (`COLUMN_NAME`)
| 8fbad1      | true     | lbcat2.table_name | anotherloweruniqueconstraint             | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `anotherloweruniqueconstraint` UNIQUE (`COLUMN_NAME`)
| bb52dd      | true     | lbcat2.table_name | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint` UNIQUE (`COLUMN_NAME`)
| b14807      | true     | lbcat2.table_name | loweruniqueconstraint                    | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `loweruniqueconstraint` UNIQUE (`COLUMN_NAME`)

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | table                                | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| f75adb      | true     | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| 5e7754      | true     | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| ca0403      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| ad19b9      | true     | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| cea6bc      | true     | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| bc878f      | true     | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| b48dc5      | true     | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| b85e41      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| f95c81      | true     | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| d76757      | true     | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)

# Test: "Can apply unique constraint with with various settings" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | columns              | name    | table             | OPERATIONS
| :---------- | :------- | :------------------- | :------ | :---------------- | :------
| 9224d3      | true     | COL_NAME             |         | lbcat.test_table  | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT UNIQUE (`COL_NAME`)
| 30a711      | true     | COL_NAME             |         | lbcat2.test_table | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT UNIQUE (`COL_NAME`)
| 056b8c      | true     | COL_NAME             | uq_test | lbcat.test_table  | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT `uq_test` UNIQUE (`COL_NAME`)
| bd3e6c      | true     | COL_NAME             | uq_test | lbcat2.test_table | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT `uq_test` UNIQUE (`COL_NAME`)
| 30b041      | true     | COL_NAME1, COL_NAME2 |         | lbcat.test_table  | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT UNIQUE (`COL_NAME1`, `COL_NAME2`)
| 0c43aa      | true     | COL_NAME1, COL_NAME2 |         | lbcat2.test_table | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT UNIQUE (`COL_NAME1`, `COL_NAME2`)
| 2df6a7      | true     | COL_NAME1, COL_NAME2 | uq_test | lbcat.test_table  | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT `uq_test` UNIQUE (`COL_NAME1`, `COL_NAME2`)
| f7e3e8      | true     | COL_NAME1, COL_NAME2 | uq_test | lbcat2.test_table | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT `uq_test` UNIQUE (`COL_NAME1`, `COL_NAME2`)

# Test Version: "278bd8" #