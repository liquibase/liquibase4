**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can drop default values on complex names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                                                              | dataType | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------- | :------
| a0acc58     | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 94be334     | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| d4ced61     | true     | lbcat.4test_table.UPPERCOLUMN                                       | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| dcc7974     | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 5e69251     | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| b7484f2     | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 003d253     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| ed795c9     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 758157e     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 518dba9     | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 13b3f9e     | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| fdf6986     | true     | lbcat.lowertable.UPPERCOLUMN                                        | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 478dd97     | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| ec80260     | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 6a38fc1     | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 7407e8a     | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| c8aae69     | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| ff89699     | true     | lbcat2.4test_table.UPPERCOLUMN                                      | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| c88778d     | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 4042e51     | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| e8cfdda     | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| b7c1327     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 8a34e82     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| 7158712     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| b1dc9db     | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 298a0f6     | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| adc5b0c     | true     | lbcat2.lowertable.UPPERCOLUMN                                       | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT
| 444cf3c     | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `ANOTHERUPPERCOLUMN` DROP DEFAULT
| 4a56277     | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` DROP DEFAULT
| e6c9a92     | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `UPPERCOLUMN` DROP DEFAULT

# Test: "can set default values on complex names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                                                              | dataType | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------- | :------
| a0acc58     | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 94be334     | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| d4ced61     | true     | lbcat.4test_table.UPPERCOLUMN                                       | int      | **plan**: ALTER TABLE `lbcat`.`4test_table` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| dcc7974     | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 5e69251     | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| b7484f2     | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | int      | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| 003d253     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| ed795c9     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| 758157e     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | int      | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| 518dba9     | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 13b3f9e     | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| fdf6986     | true     | lbcat.lowertable.UPPERCOLUMN                                        | int      | **plan**: ALTER TABLE `lbcat`.`lowertable` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| 478dd97     | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| ec80260     | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| 6a38fc1     | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | int      | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| 7407e8a     | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| c8aae69     | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| ff89699     | true     | lbcat2.4test_table.UPPERCOLUMN                                      | int      | **plan**: ALTER TABLE `lbcat2`.`4test_table` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| c88778d     | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 4042e51     | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| e8cfdda     | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| b7c1327     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 8a34e82     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| 7158712     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | int      | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| b1dc9db     | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 298a0f6     | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| adc5b0c     | true     | lbcat2.lowertable.UPPERCOLUMN                                       | int      | **plan**: ALTER TABLE `lbcat2`.`lowertable` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50
| 444cf3c     | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `ANOTHERUPPERCOLUMN` SET DEFAULT 50
| 4a56277     | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `CRAZY!@#\$%^&*()_+{}[]'"COLUMN` SET DEFAULT 50
| e6c9a92     | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | int      | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` ALTER COLUMN `UPPERCOLUMN` SET DEFAULT 50

# Test: "check createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                        | dataType    | defaultValue | defaultValueType  | OPERATIONS
| :---------- | :------- | :---------------------------- | :---------- | :----------- | :---------------- | :------
| b64e55d     | true     | lbcat.test_table.TEST_COLUMN  | INTEGER     |              |                   | **plan**: ALTER TABLE `lbcat`.`test_table` ALTER COLUMN `TEST_COLUMN` DROP DEFAULT
| 1c88ad6     | true     | lbcat.test_table.TEST_COLUMN  | INTEGER     | 10           | java.lang.Integer | **plan**: ALTER TABLE `lbcat`.`test_table` ALTER COLUMN `TEST_COLUMN` SET DEFAULT 10
| fe33cc5     | true     | lbcat.test_table.TEST_COLUMN  | INTEGER     | 10           | java.lang.String  | **plan**: ALTER TABLE `lbcat`.`test_table` ALTER COLUMN `TEST_COLUMN` SET DEFAULT 10
| 0ba07bb     | true     | lbcat.test_table.TEST_COLUMN  | VARCHAR(50) |              |                   | **plan**: ALTER TABLE `lbcat`.`test_table` ALTER COLUMN `TEST_COLUMN` DROP DEFAULT
| 9f45834     | true     | lbcat.test_table.TEST_COLUMN  | VARCHAR(50) | test value   | java.lang.String  | **plan**: ALTER TABLE `lbcat`.`test_table` ALTER COLUMN `TEST_COLUMN` SET DEFAULT 'test value'
| d96676c     | true     | lbcat2.test_table.TEST_COLUMN | INTEGER     |              |                   | **plan**: ALTER TABLE `lbcat2`.`test_table` ALTER COLUMN `TEST_COLUMN` DROP DEFAULT
| d5eca93     | true     | lbcat2.test_table.TEST_COLUMN | INTEGER     | 10           | java.lang.Integer | **plan**: ALTER TABLE `lbcat2`.`test_table` ALTER COLUMN `TEST_COLUMN` SET DEFAULT 10
| 54f792b     | true     | lbcat2.test_table.TEST_COLUMN | INTEGER     | 10           | java.lang.String  | **plan**: ALTER TABLE `lbcat2`.`test_table` ALTER COLUMN `TEST_COLUMN` SET DEFAULT 10
| d0568f4     | true     | lbcat2.test_table.TEST_COLUMN | VARCHAR(50) |              |                   | **plan**: ALTER TABLE `lbcat2`.`test_table` ALTER COLUMN `TEST_COLUMN` DROP DEFAULT
| 006fbbd     | true     | lbcat2.test_table.TEST_COLUMN | VARCHAR(50) | test value   | java.lang.String  | **plan**: ALTER TABLE `lbcat2`.`test_table` ALTER COLUMN `TEST_COLUMN` SET DEFAULT 'test value'

# Test Version: "d2d5cf" #