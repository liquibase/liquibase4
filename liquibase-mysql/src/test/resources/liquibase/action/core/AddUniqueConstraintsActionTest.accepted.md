**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple constraints at once" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | schema | OPERATIONS
| :---------- | :------- | :----- | :------
| 43a3ea      | true     | lbcat  | **plan**: ALTER TABLE `lbcat`.`test_table_1` ADD CONSTRAINT UNIQUE (`COL_NAME`)<br>ALTER TABLE `lbcat`.`test_table_2` ADD CONSTRAINT UNIQUE (`COL_NAME`)<br>ALTER TABLE `lbcat`.`test_table_3` ADD CONSTRAINT UNIQUE (`COL_NAME`)
| 06284b      | true     | lbcat2 | **plan**: ALTER TABLE `lbcat2`.`test_table_1` ADD CONSTRAINT UNIQUE (`COL_NAME`)<br>ALTER TABLE `lbcat2`.`test_table_2` ADD CONSTRAINT UNIQUE (`COL_NAME`)<br>ALTER TABLE `lbcat2`.`test_table_3` ADD CONSTRAINT UNIQUE (`COL_NAME`)

# Test: "Can apply multi-column with standard settings" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column       | table             | OPERATIONS
| :---------- | :------- | :----------- | :---------------- | :------
| 496ec8      | true     | COL_1, COL_2 | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`COL_1`, `COL_2`)
| 997257      | true     | COL_1, COL_2 | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`COL_1`, `COL_2`)

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                         | table             | OPERATIONS
| :---------- | :------- | :----------------------------- | :---------------- | :------
| e553d9      | true     | ANOTHERUPPERCOLUMN             | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`ANOTHERUPPERCOLUMN`)
| 981c22      | true     | ANOTHERUPPERCOLUMN             | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`ANOTHERUPPERCOLUMN`)
| 156c98      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`)
| e95fe4      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`)
| e60575      | true     | UPPERCOLUMN                    | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`UPPERCOLUMN`)
| b36f9b      | true     | UPPERCOLUMN                    | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `uq_name` UNIQUE (`UPPERCOLUMN`)

# Test: "Can apply single column with standard settings but complex constraint names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | table             | uq                                       | OPERATIONS
| :---------- | :------- | :---------------- | :--------------------------------------- | :------
| 65997b      | true     | lbcat.table_name  | 4test_uniqueconstraint                   | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `4test_uniqueconstraint` UNIQUE (`COLUMN_NAME`)
| 5e7d6d      | true     | lbcat.table_name  | anotherloweruniqueconstraint             | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `anotherloweruniqueconstraint` UNIQUE (`COLUMN_NAME`)
| 6ffc00      | true     | lbcat.table_name  | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint` UNIQUE (`COLUMN_NAME`)
| 415853      | true     | lbcat.table_name  | loweruniqueconstraint                    | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `loweruniqueconstraint` UNIQUE (`COLUMN_NAME`)
| 50c236      | true     | lbcat2.table_name | 4test_uniqueconstraint                   | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `4test_uniqueconstraint` UNIQUE (`COLUMN_NAME`)
| 59608b      | true     | lbcat2.table_name | anotherloweruniqueconstraint             | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `anotherloweruniqueconstraint` UNIQUE (`COLUMN_NAME`)
| f2fed5      | true     | lbcat2.table_name | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint` UNIQUE (`COLUMN_NAME`)
| 7e2eb4      | true     | lbcat2.table_name | loweruniqueconstraint                    | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `loweruniqueconstraint` UNIQUE (`COLUMN_NAME`)

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | table                                | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| c714c6      | true     | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| 492248      | true     | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| 2f78b2      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| 7c583f      | true     | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| d93a8c      | true     | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| 352c7c      | true     | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| f78fdd      | true     | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| 1a22ce      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| ac925c      | true     | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)
| cb2f55      | true     | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ADD CONSTRAINT `uq_name` UNIQUE (`COLUMN_NAME`)

# Test: "Can apply unique constraint with with various settings" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | columns              | name    | table             | OPERATIONS
| :---------- | :------- | :------------------- | :------ | :---------------- | :------
| 55aa72      | true     | COL_NAME             |         | lbcat.test_table  | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT UNIQUE (`COL_NAME`)
| 5fdd27      | true     | COL_NAME             |         | lbcat2.test_table | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT UNIQUE (`COL_NAME`)
| c35e53      | true     | COL_NAME             | uq_test | lbcat.test_table  | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT `uq_test` UNIQUE (`COL_NAME`)
| 2f0b9b      | true     | COL_NAME             | uq_test | lbcat2.test_table | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT `uq_test` UNIQUE (`COL_NAME`)
| b64ee8      | true     | COL_NAME1, COL_NAME2 |         | lbcat.test_table  | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT UNIQUE (`COL_NAME1`, `COL_NAME2`)
| b0c70a      | true     | COL_NAME1, COL_NAME2 |         | lbcat2.test_table | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT UNIQUE (`COL_NAME1`, `COL_NAME2`)
| 50a4ed      | true     | COL_NAME1, COL_NAME2 | uq_test | lbcat.test_table  | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT `uq_test` UNIQUE (`COL_NAME1`, `COL_NAME2`)
| 42fd7e      | true     | COL_NAME1, COL_NAME2 | uq_test | lbcat2.test_table | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT `uq_test` UNIQUE (`COL_NAME1`, `COL_NAME2`)

# Test Version: "a74fb8" #