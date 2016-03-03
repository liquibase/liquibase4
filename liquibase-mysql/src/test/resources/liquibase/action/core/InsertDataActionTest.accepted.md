**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can add columns with various options" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | columns          | relation   | types                    | values                       | OPERATIONS
| :---------- | :------- | :--------------- | :--------- | :----------------------- | :--------------------------- | :------
| 01a082      | true     | COLUMN1          | test_table | BIGINT                   | 123142                       | **plan**: INSERT INTO `test_table` (`COLUMN1`) VALUES ('123142')
| d8128b      | true     | COLUMN1          | test_table | FLOAT                    | 12.5                         | **plan**: INSERT INTO `test_table` (`COLUMN1`) VALUES ('12.5')
| 38ede2      | true     | COLUMN1          | test_table | INTEGER                  | 42                           | **plan**: INSERT INTO `test_table` (`COLUMN1`) VALUES ('42')
| 042da4      | true     | COLUMN1          | test_table | INTEGER                  | null                         | **plan**: INSERT INTO `test_table` (`COLUMN1`) VALUES (NULL)
| 8f142d      | true     | COLUMN1          | test_table | VARCHAR(50)              | null                         | **plan**: INSERT INTO `test_table` (`COLUMN1`) VALUES (NULL)
| 449f2d      | true     | COLUMN1          | test_table | VARCHAR(50)              | test string                  | **plan**: INSERT INTO `test_table` (`COLUMN1`) VALUES ('test string')
| c136cc      | true     | COLUMN1, COLUMN2 | test_table | VARCHAR(50), INTEGER     | test string 1, 2362          | **plan**: INSERT INTO `test_table` (`COLUMN1`, `COLUMN2`) VALUES ('test string 1', '2362')
| 4673b1      | true     | COLUMN1, COLUMN2 | test_table | VARCHAR(50), VARCHAR(50) | test string 1, test string 2 | **plan**: INSERT INTO `test_table` (`COLUMN1`, `COLUMN2`) VALUES ('test string 1', 'test string 2')

# Test: "can insert with just numbers but complex names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | columns                        | relation                             | OPERATIONS
| :---------- | :------- | :----------------------------- | :----------------------------------- | :------
| 779037      | true     | ANOTHERUPPERCOLUMN             | lbcat.4test_table                    | **plan**: INSERT INTO `lbcat`.`4test_table` (`ANOTHERUPPERCOLUMN`) VALUES ('42')
| 1b07ae      | true     | ANOTHERUPPERCOLUMN             | lbcat.anotherlowertable              | **plan**: INSERT INTO `lbcat`.`anotherlowertable` (`ANOTHERUPPERCOLUMN`) VALUES ('42')
| b5a55e      | true     | ANOTHERUPPERCOLUMN             | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: INSERT INTO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` (`ANOTHERUPPERCOLUMN`) VALUES ('42')
| 0a8154      | true     | ANOTHERUPPERCOLUMN             | lbcat.lowertable                     | **plan**: INSERT INTO `lbcat`.`lowertable` (`ANOTHERUPPERCOLUMN`) VALUES ('42')
| 209c38      | true     | ANOTHERUPPERCOLUMN             | lbcat.only_in_lbcat                  | **plan**: INSERT INTO `lbcat`.`only_in_lbcat` (`ANOTHERUPPERCOLUMN`) VALUES ('42')
| 9964b4      | true     | ANOTHERUPPERCOLUMN             | lbcat2.4test_table                   | **plan**: INSERT INTO `lbcat2`.`4test_table` (`ANOTHERUPPERCOLUMN`) VALUES ('42')
| 56d064      | true     | ANOTHERUPPERCOLUMN             | lbcat2.anotherlowertable             | **plan**: INSERT INTO `lbcat2`.`anotherlowertable` (`ANOTHERUPPERCOLUMN`) VALUES ('42')
| 77e84a      | true     | ANOTHERUPPERCOLUMN             | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: INSERT INTO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` (`ANOTHERUPPERCOLUMN`) VALUES ('42')
| e27596      | true     | ANOTHERUPPERCOLUMN             | lbcat2.lowertable                    | **plan**: INSERT INTO `lbcat2`.`lowertable` (`ANOTHERUPPERCOLUMN`) VALUES ('42')
| 58a865      | true     | ANOTHERUPPERCOLUMN             | lbcat2.only_in_lbcat2                | **plan**: INSERT INTO `lbcat2`.`only_in_lbcat2` (`ANOTHERUPPERCOLUMN`) VALUES ('42')
| 0d5c48      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.4test_table                    | **plan**: INSERT INTO `lbcat`.`4test_table` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES ('42')
| c91dcd      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.anotherlowertable              | **plan**: INSERT INTO `lbcat`.`anotherlowertable` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES ('42')
| a1e5c7      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: INSERT INTO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES ('42')
| e6e701      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.lowertable                     | **plan**: INSERT INTO `lbcat`.`lowertable` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES ('42')
| 9e7229      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat.only_in_lbcat                  | **plan**: INSERT INTO `lbcat`.`only_in_lbcat` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES ('42')
| b8d53b      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.4test_table                   | **plan**: INSERT INTO `lbcat2`.`4test_table` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES ('42')
| 3fc0ce      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.anotherlowertable             | **plan**: INSERT INTO `lbcat2`.`anotherlowertable` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES ('42')
| e1f50a      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: INSERT INTO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES ('42')
| b4976b      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.lowertable                    | **plan**: INSERT INTO `lbcat2`.`lowertable` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES ('42')
| ab9d2d      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | lbcat2.only_in_lbcat2                | **plan**: INSERT INTO `lbcat2`.`only_in_lbcat2` (`CRAZY!@#\$%^&*()_+{}[]'"COLUMN`) VALUES ('42')
| 2a5545      | true     | UPPERCOLUMN                    | lbcat.4test_table                    | **plan**: INSERT INTO `lbcat`.`4test_table` (`UPPERCOLUMN`) VALUES ('42')
| 7db5b6      | true     | UPPERCOLUMN                    | lbcat.anotherlowertable              | **plan**: INSERT INTO `lbcat`.`anotherlowertable` (`UPPERCOLUMN`) VALUES ('42')
| ef70df      | true     | UPPERCOLUMN                    | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: INSERT INTO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` (`UPPERCOLUMN`) VALUES ('42')
| bada72      | true     | UPPERCOLUMN                    | lbcat.lowertable                     | **plan**: INSERT INTO `lbcat`.`lowertable` (`UPPERCOLUMN`) VALUES ('42')
| 965729      | true     | UPPERCOLUMN                    | lbcat.only_in_lbcat                  | **plan**: INSERT INTO `lbcat`.`only_in_lbcat` (`UPPERCOLUMN`) VALUES ('42')
| d0266d      | true     | UPPERCOLUMN                    | lbcat2.4test_table                   | **plan**: INSERT INTO `lbcat2`.`4test_table` (`UPPERCOLUMN`) VALUES ('42')
| 3cb0fc      | true     | UPPERCOLUMN                    | lbcat2.anotherlowertable             | **plan**: INSERT INTO `lbcat2`.`anotherlowertable` (`UPPERCOLUMN`) VALUES ('42')
| 063f73      | true     | UPPERCOLUMN                    | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: INSERT INTO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` (`UPPERCOLUMN`) VALUES ('42')
| 0a17ee      | true     | UPPERCOLUMN                    | lbcat2.lowertable                    | **plan**: INSERT INTO `lbcat2`.`lowertable` (`UPPERCOLUMN`) VALUES ('42')
| 3f791d      | true     | UPPERCOLUMN                    | lbcat2.only_in_lbcat2                | **plan**: INSERT INTO `lbcat2`.`only_in_lbcat2` (`UPPERCOLUMN`) VALUES ('42')

# Test Version: "0bd1ac" #