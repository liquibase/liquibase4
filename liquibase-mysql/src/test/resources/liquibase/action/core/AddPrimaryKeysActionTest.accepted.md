**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple primary keys at once" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | schema | OPERATIONS
| :---------- | :------- | :----- | :------
| 43a3ea      | true     | lbcat  | **plan**: ALTER TABLE `lbcat`.`test_table_1` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)<br>ALTER TABLE `lbcat`.`test_table_2` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)<br>ALTER TABLE `lbcat`.`test_table_3` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 06284b      | true     | lbcat2 | **plan**: ALTER TABLE `lbcat2`.`test_table_1` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)<br>ALTER TABLE `lbcat2`.`test_table_2` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)<br>ALTER TABLE `lbcat2`.`test_table_3` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)

# Test: "Can apply primary key with with various settings" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | clustered | columns  | direction | table             | tablespace | OPERATIONS
| :---------- | :------- | :-------- | :------- | :-------- | :---------------- | :--------- | :------
| d5082b      | true     | false     | COL_NAME | ASC       | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME` ASC)
| 484112      | true     | false     | COL_NAME | ASC       | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME` ASC)
| 8e974d      | true     | false     | COL_NAME | null      | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 6e44ea      | true     | false     | COL_NAME | null      | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| e4265f      | true     | null      | COL_NAME | ASC       | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME` ASC)
| dc1972      | true     | null      | COL_NAME | ASC       | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME` ASC)
| 479beb      | true     | null      | COL_NAME | null      | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| d63746      | true     | null      | COL_NAME | null      | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)

# Test: "Can apply single column with standard settings but complex PK names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | columns                         | pk                             | table             | OPERATIONS
| :---------- | :------- | :------------------------------ | :----------------------------- | :---------------- | :------
| 9d982f      | true     | PrimaryKeyColumn{name=COL_NAME} | ANOTHERUPPERCOLUMN             | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `ANOTHERUPPERCOLUMN` PRIMARY KEY (`COL_NAME`)
| efe257      | true     | PrimaryKeyColumn{name=COL_NAME} | ANOTHERUPPERCOLUMN             | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `ANOTHERUPPERCOLUMN` PRIMARY KEY (`COL_NAME`)
| 9988e9      | true     | PrimaryKeyColumn{name=COL_NAME} | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` PRIMARY KEY (`COL_NAME`)
| 3bc4a3      | true     | PrimaryKeyColumn{name=COL_NAME} | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` PRIMARY KEY (`COL_NAME`)
| faed9d      | true     | PrimaryKeyColumn{name=COL_NAME} | UPPERCOLUMN                    | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `UPPERCOLUMN` PRIMARY KEY (`COL_NAME`)
| d2d44e      | true     | PrimaryKeyColumn{name=COL_NAME} | UPPERCOLUMN                    | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `UPPERCOLUMN` PRIMARY KEY (`COL_NAME`)

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                             | OPERATIONS
| :---------- | :------- | :--------------------------------- | :------
| 13cdc2      | true     | 4test_primarykey                   | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`4test_primarykey`)
| 3949c9      | true     | anotherlowerprimarykey             | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`anotherlowerprimarykey`)
| 34f668      | true     | crazy!@#\$%^&*()_+{}[]'"primarykey | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`crazy!@#\$%^&*()_+{}[]'"primarykey`)
| 18436d      | true     | lowerprimarykey                    | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`lowerprimarykey`)

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | table                                | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| c714c6      | true     | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 492248      | true     | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 2f78b2      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 7c583f      | true     | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| d93a8c      | true     | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 352c7c      | true     | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| f78fdd      | true     | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 1a22ce      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| ac925c      | true     | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| cb2f55      | true     | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)

# Test Version: "cf1d58" #