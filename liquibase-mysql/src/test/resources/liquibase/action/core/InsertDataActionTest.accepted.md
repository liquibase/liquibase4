**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can add columns with various options" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | columns          | columnsForUpdateCheck | relation   | types                    | values                       | OPERATIONS
| :---------- | :------- | :--------------- | :-------------------- | :--------- | :----------------------- | :--------------------------- | :------
| 01a0823     | true     | COLUMN1          |                       | test_table | BIGINT                   | 123142                       | **plan**: INSERT INTO `test_table` (`COLUMN1`) VALUES (123142)
| d8128ba     | true     | COLUMN1          |                       | test_table | FLOAT                    | 12.5                         | **plan**: INSERT INTO `test_table` (`COLUMN1`) VALUES (12.5)
| 38ede25     | true     | COLUMN1          |                       | test_table | INTEGER                  | 42                           | **plan**: INSERT INTO `test_table` (`COLUMN1`) VALUES (42)
| 042da47     | true     | COLUMN1          |                       | test_table | INTEGER                  | null                         | **plan**: INSERT INTO `test_table` (`COLUMN1`) VALUES (NULL)
| 8f142d4     | true     | COLUMN1          |                       | test_table | VARCHAR(50)              | null                         | **plan**: INSERT INTO `test_table` (`COLUMN1`) VALUES (NULL)
| 449f2dc     | true     | COLUMN1          |                       | test_table | VARCHAR(50)              | test string                  | **plan**: INSERT INTO `test_table` (`COLUMN1`) VALUES ('test string')
| c136cc5     | true     | COLUMN1, COLUMN2 |                       | test_table | VARCHAR(50), INTEGER     | test string 1, 2362          | **plan**: INSERT INTO `test_table` (`COLUMN1`, `COLUMN2`) VALUES ('test string 1', 2362)
| 4673b1e     | true     | COLUMN1, COLUMN2 |                       | test_table | VARCHAR(50), VARCHAR(50) | test string 1, test string 2 | **plan**: INSERT INTO `test_table` (`COLUMN1`, `COLUMN2`) VALUES ('test string 1', 'test string 2')
| e7c1547     | true     | COLUMN1, COLUMN2 | COLUMN1               | test_table | VARCHAR(50), INTEGER     | test string 1, 2362          | **plan**: INSERT INTO `test_table` (`COLUMN1`, `COLUMN2`) VALUES ('test string 1', 2362) ON DUPLICATE KEY UPDATE `COLUMN2`=2362
| 853d22a     | true     | COLUMN1, COLUMN2 | COLUMN1               | test_table | VARCHAR(50), VARCHAR(50) | test string 1, test string 2 | **plan**: INSERT INTO `test_table` (`COLUMN1`, `COLUMN2`) VALUES ('test string 1', 'test string 2') ON DUPLICATE KEY UPDATE `COLUMN2`='test string 2'

# Test: "can insert with just numbers but complex names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | columns                        | relation                             | OPERATIONS
| :---------- | :------- | :----------------------------- | :----------------------------------- | :------
| 779037c     | true     | ANOTHERUPPERCOLUMN             | lbcat.4test_table                    | **plan**: INSERT INTO `lbcat`.`4test_table` (`ANOTHERUPPERCOLUMN`) VALUES (42)
| 1b07ae3     | true     | ANOTHERUPPERCOLUMN             | lbcat.anotherlowertable              | **plan**: INSERT INTO `lbcat`.`anotherlowertable` (`ANOTHERUPPERCOLUMN`) VALUES (42)
| b5a55e1     | true     | ANOTHERUPPERCOLUMN             | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: INSERT INTO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` (`ANOTHERUPPERCOLUMN`) VALUES (42)
| 0a81542     | true     | ANOTHERUPPERCOLUMN             | lbcat.lowertable                     | **plan**: INSERT INTO `lbcat`.`lowertable` (`ANOTHERUPPERCOLUMN`) VALUES (42)
| 209c382     | true     | ANOTHERUPPERCOLUMN             | lbcat.only_in_lbcat                  | **plan**: INSERT INTO `lbcat`.`only_in_lbcat` (`ANOTHERUPPERCOLUMN`) VALUES (42)
| 9964b47     | true     | ANOTHERUPPERCOLUMN             | lbcat2.4test_table                   | **plan**: INSERT INTO `lbcat2`.`4test_table` (`ANOTHERUPPERCOLUMN`) VALUES (42)
| 56d064a     | true     | ANOTHERUPPERCOLUMN             | lbcat2.anotherlowertable             | **plan**: INSERT INTO `lbcat2`.`anotherlowertable` (`ANOTHERUPPERCOLUMN`) VALUES (42)
| 77e84ac     | true     | ANOTHERUPPERCOLUMN             | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: INSERT INTO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` (`ANOTHERUPPERCOLUMN`) VALUES (42)
| e275966     | true     | ANOTHERUPPERCOLUMN             | lbcat2.lowertable                    | **plan**: INSERT INTO `lbcat2`.`lowertable` (`ANOTHERUPPERCOLUMN`) VALUES (42)
| 58a8654     | true     | ANOTHERUPPERCOLUMN             | lbcat2.only_in_lbcat2                | **plan**: INSERT INTO `lbcat2`.`only_in_lbcat2` (`ANOTHERUPPERCOLUMN`) VALUES (42)
| 0d5c48d     | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.4test_table                    | **plan**: INSERT INTO `lbcat`.`4test_table` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES (42)
| c91dcdf     | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.anotherlowertable              | **plan**: INSERT INTO `lbcat`.`anotherlowertable` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES (42)
| a1e5c79     | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: INSERT INTO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES (42)
| e6e7017     | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.lowertable                     | **plan**: INSERT INTO `lbcat`.`lowertable` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES (42)
| 9e72294     | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.only_in_lbcat                  | **plan**: INSERT INTO `lbcat`.`only_in_lbcat` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES (42)
| b8d53b8     | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.4test_table                   | **plan**: INSERT INTO `lbcat2`.`4test_table` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES (42)
| 3fc0ce5     | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.anotherlowertable             | **plan**: INSERT INTO `lbcat2`.`anotherlowertable` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES (42)
| e1f50a8     | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: INSERT INTO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES (42)
| b4976be     | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.lowertable                    | **plan**: INSERT INTO `lbcat2`.`lowertable` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES (42)
| ab9d2d2     | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.only_in_lbcat2                | **plan**: INSERT INTO `lbcat2`.`only_in_lbcat2` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES (42)
| 2a5545b     | true     | UPPERCOLUMN                    | lbcat.4test_table                    | **plan**: INSERT INTO `lbcat`.`4test_table` (`UPPERCOLUMN`) VALUES (42)
| 7db5b6a     | true     | UPPERCOLUMN                    | lbcat.anotherlowertable              | **plan**: INSERT INTO `lbcat`.`anotherlowertable` (`UPPERCOLUMN`) VALUES (42)
| ef70df8     | true     | UPPERCOLUMN                    | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: INSERT INTO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` (`UPPERCOLUMN`) VALUES (42)
| bada725     | true     | UPPERCOLUMN                    | lbcat.lowertable                     | **plan**: INSERT INTO `lbcat`.`lowertable` (`UPPERCOLUMN`) VALUES (42)
| 965729c     | true     | UPPERCOLUMN                    | lbcat.only_in_lbcat                  | **plan**: INSERT INTO `lbcat`.`only_in_lbcat` (`UPPERCOLUMN`) VALUES (42)
| d0266df     | true     | UPPERCOLUMN                    | lbcat2.4test_table                   | **plan**: INSERT INTO `lbcat2`.`4test_table` (`UPPERCOLUMN`) VALUES (42)
| 3cb0fc5     | true     | UPPERCOLUMN                    | lbcat2.anotherlowertable             | **plan**: INSERT INTO `lbcat2`.`anotherlowertable` (`UPPERCOLUMN`) VALUES (42)
| 063f73f     | true     | UPPERCOLUMN                    | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: INSERT INTO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` (`UPPERCOLUMN`) VALUES (42)
| 0a17ee3     | true     | UPPERCOLUMN                    | lbcat2.lowertable                    | **plan**: INSERT INTO `lbcat2`.`lowertable` (`UPPERCOLUMN`) VALUES (42)
| 3f791d2     | true     | UPPERCOLUMN                    | lbcat2.only_in_lbcat2                | **plan**: INSERT INTO `lbcat2`.`only_in_lbcat2` (`UPPERCOLUMN`) VALUES (42)

# Test: "merge statement works" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | columns            | columnsForUpdateCheck | relation                             | OPERATIONS
| :---------- | :------- | :----------------- | :-------------------- | :----------------------------------- | :------
| 07ac780     | true     | ID, NAME, ID, NAME | ID                    | lbcat.test_table, lbcat.test_table   | **plan**: INSERT INTO `lbcat`.`test_table` (`ID`, `NAME`) VALUES (1, 'user 1 - new') ON DUPLICATE KEY UPDATE `NAME`='user 1 - new'<br>INSERT INTO `lbcat`.`test_table` (`ID`, `NAME`) VALUES (3, 'user 3 - new') ON DUPLICATE KEY UPDATE `NAME`='user 3 - new'
| 02f993a     | true     | ID, NAME, ID, NAME | ID                    | lbcat2.test_table, lbcat2.test_table | **plan**: INSERT INTO `lbcat2`.`test_table` (`ID`, `NAME`) VALUES (1, 'user 1 - new') ON DUPLICATE KEY UPDATE `NAME`='user 1 - new'<br>INSERT INTO `lbcat2`.`test_table` (`ID`, `NAME`) VALUES (3, 'user 3 - new') ON DUPLICATE KEY UPDATE `NAME`='user 3 - new'

# Test Version: "8f1f01" #