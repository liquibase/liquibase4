**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple primary keys at once" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | schema | OPERATIONS
| :---------- | :------- | :----- | :------
| 4935d9      | true     | lbcat  | **plan**: ALTER TABLE `lbcat`.`test_table_1` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)<br>ALTER TABLE `lbcat`.`test_table_2` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)<br>ALTER TABLE `lbcat`.`test_table_3` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| e78147      | true     | lbcat2 | **plan**: ALTER TABLE `lbcat2`.`test_table_1` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)<br>ALTER TABLE `lbcat2`.`test_table_2` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)<br>ALTER TABLE `lbcat2`.`test_table_3` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)

# Test: "Can apply primary key with with various settings" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | clustered | columns  | direction | table             | tablespace | OPERATIONS
| :---------- | :------- | :-------- | :------- | :-------- | :---------------- | :--------- | :------
| 2472be      | true     | false     | COL_NAME | ASC       | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME` ASC)
| d47a63      | true     | false     | COL_NAME | ASC       | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME` ASC)
| d82fc7      | true     | false     | COL_NAME | null      | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 8d1536      | true     | false     | COL_NAME | null      | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 4a6ab2      | true     | null      | COL_NAME | ASC       | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME` ASC)
| 34ee6c      | true     | null      | COL_NAME | ASC       | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME` ASC)
| 11543d      | true     | null      | COL_NAME | null      | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 45433a      | true     | null      | COL_NAME | null      | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)

# Test: "Can apply single column with standard settings but complex PK names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | columns                         | pk                             | table             | OPERATIONS
| :---------- | :------- | :------------------------------ | :----------------------------- | :---------------- | :------
| 210511      | true     | PrimaryKeyColumn{name=COL_NAME} | ANOTHERUPPERCOLUMN             | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `ANOTHERUPPERCOLUMN` PRIMARY KEY (`COL_NAME`)
| cb8f9f      | true     | PrimaryKeyColumn{name=COL_NAME} | ANOTHERUPPERCOLUMN             | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `ANOTHERUPPERCOLUMN` PRIMARY KEY (`COL_NAME`)
| be3527      | true     | PrimaryKeyColumn{name=COL_NAME} | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` PRIMARY KEY (`COL_NAME`)
| 47737e      | true     | PrimaryKeyColumn{name=COL_NAME} | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` PRIMARY KEY (`COL_NAME`)
| 747a9d      | true     | PrimaryKeyColumn{name=COL_NAME} | UPPERCOLUMN                    | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `UPPERCOLUMN` PRIMARY KEY (`COL_NAME`)
| a37d2b      | true     | PrimaryKeyColumn{name=COL_NAME} | UPPERCOLUMN                    | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `UPPERCOLUMN` PRIMARY KEY (`COL_NAME`)

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | column                             | OPERATIONS
| :---------- | :------- | :--------------------------------- | :------
| c8e9d5      | true     | 4test_primarykey                   | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`4test_primarykey`)
| 4b00e5      | true     | anotherlowerprimarykey             | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`anotherlowerprimarykey`)
| 285398      | true     | crazy!@#\$%^&*()_+{}[]'"primarykey | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`crazy!@#\$%^&*()_+{}[]'"primarykey`)
| e1ebc2      | true     | lowerprimarykey                    | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`lowerprimarykey`)

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | table                                | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| f75adb      | true     | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 5e7754      | true     | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| ca0403      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| ad19b9      | true     | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| cea6bc      | true     | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| bc878f      | true     | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| b48dc5      | true     | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| b85e41      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| f95c81      | true     | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| d76757      | true     | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)

# Test Version: "036c44" #