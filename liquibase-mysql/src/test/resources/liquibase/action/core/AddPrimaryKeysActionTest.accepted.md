**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple primary keys at once" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | schema | OPERATIONS
| :---------- | :------- | :----- | :------
| 43a3eab     | true     | lbcat  | **plan**: ALTER TABLE `lbcat`.`test_table_1` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)<br>ALTER TABLE `lbcat`.`test_table_2` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)<br>ALTER TABLE `lbcat`.`test_table_3` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 06284bc     | true     | lbcat2 | **plan**: ALTER TABLE `lbcat2`.`test_table_1` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)<br>ALTER TABLE `lbcat2`.`test_table_2` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)<br>ALTER TABLE `lbcat2`.`test_table_3` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)

# Test: "Can apply primary key with with various settings" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | clustered | columns  | direction | table             | tablespace | OPERATIONS
| :---------- | :------- | :-------- | :------- | :-------- | :---------------- | :--------- | :------
| d5082be     | true     | false     | COL_NAME | ASC       | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME` ASC)
| 484112a     | true     | false     | COL_NAME | ASC       | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME` ASC)
| 8e974dd     | true     | false     | COL_NAME | null      | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 6e44ea0     | true     | false     | COL_NAME | null      | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| e4265fa     | true     | null      | COL_NAME | ASC       | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME` ASC)
| dc19725     | true     | null      | COL_NAME | ASC       | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME` ASC)
| 479bebf     | true     | null      | COL_NAME | null      | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| d637466     | true     | null      | COL_NAME | null      | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)

# Test: "Can apply single column with standard settings but complex PK names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | columns                         | pk                             | table             | OPERATIONS
| :---------- | :------- | :------------------------------ | :----------------------------- | :---------------- | :------
| 9d982fb     | true     | PrimaryKeyColumn{name=COL_NAME} | ANOTHERUPPERCOLUMN             | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `ANOTHERUPPERCOLUMN` PRIMARY KEY (`COL_NAME`)
| efe2573     | true     | PrimaryKeyColumn{name=COL_NAME} | ANOTHERUPPERCOLUMN             | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `ANOTHERUPPERCOLUMN` PRIMARY KEY (`COL_NAME`)
| 9988e9e     | true     | PrimaryKeyColumn{name=COL_NAME} | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` PRIMARY KEY (`COL_NAME`)
| 3bc4a30     | true     | PrimaryKeyColumn{name=COL_NAME} | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` PRIMARY KEY (`COL_NAME`)
| faed9d8     | true     | PrimaryKeyColumn{name=COL_NAME} | UPPERCOLUMN                    | lbcat.table_name  | **plan**: ALTER TABLE `lbcat`.`table_name` ADD CONSTRAINT `UPPERCOLUMN` PRIMARY KEY (`COL_NAME`)
| d2d44e3     | true     | PrimaryKeyColumn{name=COL_NAME} | UPPERCOLUMN                    | lbcat2.table_name | **plan**: ALTER TABLE `lbcat2`.`table_name` ADD CONSTRAINT `UPPERCOLUMN` PRIMARY KEY (`COL_NAME`)

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                             | OPERATIONS
| :---------- | :------- | :--------------------------------- | :------
| 13cdc22     | true     | 4test_primarykey                   | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`4test_primarykey`)
| 3949c9a     | true     | anotherlowerprimarykey             | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`anotherlowerprimarykey`)
| 34f668b     | true     | crazy!@#\$%^&*()_+{}[]'"primarykey | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`crazy!@#\$%^&*()_+{}[]'"primarykey`)
| 18436db     | true     | lowerprimarykey                    | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`lowerprimarykey`)

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | table                                | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| c714c6a     | true     | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 4922487     | true     | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 2f78b20     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 7c583f0     | true     | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| d93a8c8     | true     | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 352c7c3     | true     | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| f78fdd5     | true     | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| 1a22ce6     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| ac925cd     | true     | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)
| cb2f558     | true     | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ADD CONSTRAINT PRIMARY KEY (`COL_NAME`)

# Test Version: "15edbf" #