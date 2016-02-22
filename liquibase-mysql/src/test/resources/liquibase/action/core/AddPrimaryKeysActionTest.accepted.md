**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple primary keys at once" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 2a5ee3      | true     | lbcat      | **plan**: ALTER TABLE `lbcat`.`test_table_1` ADD CONSTRAINT PRIMARY KEY (`col_name`)<br>ALTER TABLE `lbcat`.`test_table_2` ADD CONSTRAINT PRIMARY KEY (`col_name`)<br>ALTER TABLE `lbcat`.`test_table_3` ADD CONSTRAINT PRIMARY KEY (`col_name`)
| 9f6418      | true     | lbcat2     | **plan**: ALTER TABLE `lbcat2`.`test_table_1` ADD CONSTRAINT PRIMARY KEY (`col_name`)<br>ALTER TABLE `lbcat2`.`test_table_2` ADD CONSTRAINT PRIMARY KEY (`col_name`)<br>ALTER TABLE `lbcat2`.`test_table_3` ADD CONSTRAINT PRIMARY KEY (`col_name`)

# Test: "Can apply primary key with with various settings" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | clustered | columns  | direction | table             | tablespace | OPERATIONS
| :---------- | :------- | :-------- | :------- | :-------- | :---------------- | :--------- | :------
| 0a9d9a      | true     | false     | col_name | ASC       | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`col_name` ASC)
| 0a06e6      | true     | false     | col_name | ASC       | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`col_name` ASC)
| 414d07      | true     | false     | col_name | null      | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`col_name`)
| 1637b4      | true     | false     | col_name | null      | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`col_name`)
| c5885b      | true     | null      | col_name | ASC       | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`col_name` ASC)
| 344619      | true     | null      | col_name | ASC       | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`col_name` ASC)
| df9b20      | true     | null      | col_name | null      | lbcat.test_table  | null       | **plan**: ALTER TABLE `lbcat`.`test_table` ADD CONSTRAINT PRIMARY KEY (`col_name`)
| 42de96      | true     | null      | col_name | null      | lbcat2.test_table | null       | **plan**: ALTER TABLE `lbcat2`.`test_table` ADD CONSTRAINT PRIMARY KEY (`col_name`)

# Test: "Can apply single column with standard settings but complex PK names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | columns                         | pkName                         | tableName  | OPERATIONS
| :---------- | :------- | :------------------------------ | :----------------------------- | :--------- | :------
| eca29f      | true     | PrimaryKeyColumn{name=col_name} | 4test_column                   | table_name | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT `4test_column` PRIMARY KEY (`col_name`)
| c7ef40      | true     | PrimaryKeyColumn{name=col_name} | anotherlowercolumn             | table_name | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT `anotherlowercolumn` PRIMARY KEY (`col_name`)
| 277ab9      | true     | PrimaryKeyColumn{name=col_name} | crazy!@#\$%^&*()_+{}[]'"column | table_name | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT `crazy!@#\$%^&*()_+{}[]'"column` PRIMARY KEY (`col_name`)
| ad114b      | true     | PrimaryKeyColumn{name=col_name} | lowercolumn                    | table_name | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT `lowercolumn` PRIMARY KEY (`col_name`)
| 4f8409      | true     | PrimaryKeyColumn{name=col_name} | only_in_lbcat                  | table_name | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT `only_in_lbcat` PRIMARY KEY (`col_name`)
| e83465      | true     | PrimaryKeyColumn{name=col_name} | only_in_lbcat2                 | table_name | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT `only_in_lbcat2` PRIMARY KEY (`col_name`)

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | columnName                         | OPERATIONS
| :---------- | :------- | :--------------------------------- | :------
| d2b612      | true     | 4test_primarykey                   | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`4test_primarykey`)
| 97b999      | true     | anotherlowerprimarykey             | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`anotherlowerprimarykey`)
| dfbb4d      | true     | crazy!@#\$%^&*()_+{}[]'"primarykey | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`crazy!@#\$%^&*()_+{}[]'"primarykey`)
| 3c5340      | true     | lowerprimarykey                    | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`lowerprimarykey`)
| 043873      | true     | only_in_lbcat                      | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`only_in_lbcat`)
| 74c98a      | true     | only_in_lbcat2                     | **plan**: ALTER TABLE `table_name` ADD CONSTRAINT PRIMARY KEY (`only_in_lbcat2`)

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | tableName                            | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| 6db8bb      | true     | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` ADD CONSTRAINT PRIMARY KEY (`col_name`)
| b2e45f      | true     | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ADD CONSTRAINT PRIMARY KEY (`col_name`)
| 5f0ea5      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT PRIMARY KEY (`col_name`)
| 572208      | true     | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` ADD CONSTRAINT PRIMARY KEY (`col_name`)
| 58d58f      | true     | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ADD CONSTRAINT PRIMARY KEY (`col_name`)
| be2547      | true     | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` ADD CONSTRAINT PRIMARY KEY (`col_name`)
| c493e5      | true     | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ADD CONSTRAINT PRIMARY KEY (`col_name`)
| 94369b      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ADD CONSTRAINT PRIMARY KEY (`col_name`)
| 2c35e6      | true     | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` ADD CONSTRAINT PRIMARY KEY (`col_name`)
| 4e636c      | true     | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ADD CONSTRAINT PRIMARY KEY (`col_name`)

# Test Version: "880114" #