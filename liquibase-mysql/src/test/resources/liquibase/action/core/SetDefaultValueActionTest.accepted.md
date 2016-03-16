**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can drop default values on complex names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                                                              | dataType | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------- | :------
| a0acc5      | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 94be33      | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| d4ced6      | true     | lbcat.4test_table.UPPERCOLUMN                                       | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| dcc797      | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 5e6925      | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| b7484f      | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 003d25      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| ed795c      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 758157      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 518dba      | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 13b3f9      | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| fdf698      | true     | lbcat.lowertable.UPPERCOLUMN                                        | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 478dd9      | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| ec8026      | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 6a38fc      | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 7407e8      | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| c8aae6      | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| ff8969      | true     | lbcat2.4test_table.UPPERCOLUMN                                      | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| c88778      | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 4042e5      | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| e8cfdd      | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| b7c132      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 8a34e8      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 715871      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| b1dc9d      | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 298a0f      | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| adc5b0      | true     | lbcat2.lowertable.UPPERCOLUMN                                       | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 444cf3      | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 4a5627      | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| e6c9a9      | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT

# Test: "can set default values on complex names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                                                              | dataType | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------- | :------
| a0acc5      | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 94be33      | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| d4ced6      | true     | lbcat.4test_table.UPPERCOLUMN                                       | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| dcc797      | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 5e6925      | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| b7484f      | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| 003d25      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| ed795c      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| 758157      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| 518dba      | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 13b3f9      | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| fdf698      | true     | lbcat.lowertable.UPPERCOLUMN                                        | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| 478dd9      | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| ec8026      | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| 6a38fc      | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| 7407e8      | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| c8aae6      | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| ff8969      | true     | lbcat2.4test_table.UPPERCOLUMN                                      | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| c88778      | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 4042e5      | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| e8cfdd      | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| b7c132      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 8a34e8      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| 715871      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| b1dc9d      | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 298a0f      | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| adc5b0      | true     | lbcat2.lowertable.UPPERCOLUMN                                       | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| 444cf3      | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 4a5627      | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| e6c9a9      | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50

# Test: "check createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                        | dataType    | defaultValue | defaultValueType  | OPERATIONS
| :---------- | :------- | :---------------------------- | :---------- | :----------- | :---------------- | :------
| b64e55      | true     | lbcat.test_table.TEST_COLUMN  | INTEGER     |              |                   | **plan**: ALTER TABLE `lbcat`.`test_table` ALTER COLUMN `TEST_COLUMN` DROP DEFAULT
| 1c88ad      | true     | lbcat.test_table.TEST_COLUMN  | INTEGER     | 10           | java.lang.Integer | **plan**: ALTER TABLE `lbcat`.`test_table` ALTER COLUMN `TEST_COLUMN` SET DEFAULT 10
| fe33cc      | true     | lbcat.test_table.TEST_COLUMN  | INTEGER     | 10           | java.lang.String  | **plan**: ALTER TABLE `lbcat`.`test_table` ALTER COLUMN `TEST_COLUMN` SET DEFAULT 10
| 0ba07b      | true     | lbcat.test_table.TEST_COLUMN  | VARCHAR(50) |              |                   | **plan**: ALTER TABLE `lbcat`.`test_table` ALTER COLUMN `TEST_COLUMN` DROP DEFAULT
| 9f4583      | true     | lbcat.test_table.TEST_COLUMN  | VARCHAR(50) | test value   | java.lang.String  | **plan**: ALTER TABLE `lbcat`.`test_table` ALTER COLUMN `TEST_COLUMN` SET DEFAULT 'test value'
| d96676      | true     | lbcat2.test_table.TEST_COLUMN | INTEGER     |              |                   | **plan**: ALTER TABLE `lbcat2`.`test_table` ALTER COLUMN `TEST_COLUMN` DROP DEFAULT
| d5eca9      | true     | lbcat2.test_table.TEST_COLUMN | INTEGER     | 10           | java.lang.Integer | **plan**: ALTER TABLE `lbcat2`.`test_table` ALTER COLUMN `TEST_COLUMN` SET DEFAULT 10
| 54f792      | true     | lbcat2.test_table.TEST_COLUMN | INTEGER     | 10           | java.lang.String  | **plan**: ALTER TABLE `lbcat2`.`test_table` ALTER COLUMN `TEST_COLUMN` SET DEFAULT 10
| d0568f      | true     | lbcat2.test_table.TEST_COLUMN | VARCHAR(50) |              |                   | **plan**: ALTER TABLE `lbcat2`.`test_table` ALTER COLUMN `TEST_COLUMN` DROP DEFAULT
| 006fbb      | true     | lbcat2.test_table.TEST_COLUMN | VARCHAR(50) | test value   | java.lang.String  | **plan**: ALTER TABLE `lbcat2`.`test_table` ALTER COLUMN `TEST_COLUMN` SET DEFAULT 'test value'

# Test Version: "e10d83" #