**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can update from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | columns                                                                    | relation          | where | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------------- | :---------------- | :---- | :------
| e859f90     | true     | UpdatedColumn{name=COLUMN1, value=1}                                       | lbcat.test_table  |       | **plan**: UPDATE `lbcat`.`test_table` SET `COLUMN1`=1
| 464cfa3     | true     | UpdatedColumn{name=COLUMN1, value=1}                                       | lbcat.test_table  | 1=1   | **plan**: UPDATE `lbcat`.`test_table` SET `COLUMN1`=1 WHERE 1=1
| 5f7759e     | true     | UpdatedColumn{name=COLUMN1, value=1}                                       | lbcat2.test_table |       | **plan**: UPDATE `lbcat2`.`test_table` SET `COLUMN1`=1
| 6885a14     | true     | UpdatedColumn{name=COLUMN1, value=1}                                       | lbcat2.test_table | 1=1   | **plan**: UPDATE `lbcat2`.`test_table` SET `COLUMN1`=1 WHERE 1=1
| 2e503fc     | true     | UpdatedColumn{name=COLUMN1, value=1}, UpdatedColumn{name=COLUMN2, value=2} | lbcat.test_table  |       | **plan**: UPDATE `lbcat`.`test_table` SET `COLUMN1`=1, `COLUMN2`=2
| c89601a     | true     | UpdatedColumn{name=COLUMN1, value=1}, UpdatedColumn{name=COLUMN2, value=2} | lbcat.test_table  | 1=1   | **plan**: UPDATE `lbcat`.`test_table` SET `COLUMN1`=1, `COLUMN2`=2 WHERE 1=1
| cf4e801     | true     | UpdatedColumn{name=COLUMN1, value=1}, UpdatedColumn{name=COLUMN2, value=2} | lbcat2.test_table |       | **plan**: UPDATE `lbcat2`.`test_table` SET `COLUMN1`=1, `COLUMN2`=2
| 9169fed     | true     | UpdatedColumn{name=COLUMN1, value=1}, UpdatedColumn{name=COLUMN2, value=2} | lbcat2.test_table | 1=1   | **plan**: UPDATE `lbcat2`.`test_table` SET `COLUMN1`=1, `COLUMN2`=2 WHERE 1=1
| a6654ee     | true     | UpdatedColumn{name=COLUMN1}                                                | lbcat.test_table  |       | **plan**: UPDATE `lbcat`.`test_table` SET `COLUMN1`=NULL
| f8bfe3a     | true     | UpdatedColumn{name=COLUMN1}                                                | lbcat.test_table  | 1=1   | **plan**: UPDATE `lbcat`.`test_table` SET `COLUMN1`=NULL WHERE 1=1
| 20bb83a     | true     | UpdatedColumn{name=COLUMN1}                                                | lbcat2.test_table |       | **plan**: UPDATE `lbcat2`.`test_table` SET `COLUMN1`=NULL
| 6c75b20     | true     | UpdatedColumn{name=COLUMN1}                                                | lbcat2.test_table | 1=1   | **plan**: UPDATE `lbcat2`.`test_table` SET `COLUMN1`=NULL WHERE 1=1

# Test: "can update with complex names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | columns                                                      | relation                             | OPERATIONS
| :---------- | :------- | :----------------------------------------------------------- | :----------------------------------- | :------
| bab503e     | true     | UpdatedColumn{name=ANOTHERUPPERCOLUMN, value=42}             | lbcat.4test_table                    | **plan**: UPDATE `lbcat`.`4test_table` SET `ANOTHERUPPERCOLUMN`=42
| e041f4c     | true     | UpdatedColumn{name=ANOTHERUPPERCOLUMN, value=42}             | lbcat.anotherlowertable              | **plan**: UPDATE `lbcat`.`anotherlowertable` SET `ANOTHERUPPERCOLUMN`=42
| 127e2bd     | true     | UpdatedColumn{name=ANOTHERUPPERCOLUMN, value=42}             | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: UPDATE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` SET `ANOTHERUPPERCOLUMN`=42
| ec70144     | true     | UpdatedColumn{name=ANOTHERUPPERCOLUMN, value=42}             | lbcat.lowertable                     | **plan**: UPDATE `lbcat`.`lowertable` SET `ANOTHERUPPERCOLUMN`=42
| 104db62     | true     | UpdatedColumn{name=ANOTHERUPPERCOLUMN, value=42}             | lbcat.only_in_lbcat                  | **plan**: UPDATE `lbcat`.`only_in_lbcat` SET `ANOTHERUPPERCOLUMN`=42
| 5fa5af6     | true     | UpdatedColumn{name=ANOTHERUPPERCOLUMN, value=42}             | lbcat2.4test_table                   | **plan**: UPDATE `lbcat2`.`4test_table` SET `ANOTHERUPPERCOLUMN`=42
| 2f256dc     | true     | UpdatedColumn{name=ANOTHERUPPERCOLUMN, value=42}             | lbcat2.anotherlowertable             | **plan**: UPDATE `lbcat2`.`anotherlowertable` SET `ANOTHERUPPERCOLUMN`=42
| 460c5ce     | true     | UpdatedColumn{name=ANOTHERUPPERCOLUMN, value=42}             | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: UPDATE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` SET `ANOTHERUPPERCOLUMN`=42
| 7aedc9a     | true     | UpdatedColumn{name=ANOTHERUPPERCOLUMN, value=42}             | lbcat2.lowertable                    | **plan**: UPDATE `lbcat2`.`lowertable` SET `ANOTHERUPPERCOLUMN`=42
| a8fb0da     | true     | UpdatedColumn{name=ANOTHERUPPERCOLUMN, value=42}             | lbcat2.only_in_lbcat2                | **plan**: UPDATE `lbcat2`.`only_in_lbcat2` SET `ANOTHERUPPERCOLUMN`=42
| 4d5a50c     | true     | UpdatedColumn{name=CRAZY!@#\$%^&*()_+{}[]'"COLUMN, value=42} | lbcat.4test_table                    | **plan**: UPDATE `lbcat`.`4test_table` SET `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`=42
| f30835e     | true     | UpdatedColumn{name=CRAZY!@#\$%^&*()_+{}[]'"COLUMN, value=42} | lbcat.anotherlowertable              | **plan**: UPDATE `lbcat`.`anotherlowertable` SET `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`=42
| b83add1     | true     | UpdatedColumn{name=CRAZY!@#\$%^&*()_+{}[]'"COLUMN, value=42} | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: UPDATE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` SET `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`=42
| 38654dd     | true     | UpdatedColumn{name=CRAZY!@#\$%^&*()_+{}[]'"COLUMN, value=42} | lbcat.lowertable                     | **plan**: UPDATE `lbcat`.`lowertable` SET `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`=42
| d831541     | true     | UpdatedColumn{name=CRAZY!@#\$%^&*()_+{}[]'"COLUMN, value=42} | lbcat.only_in_lbcat                  | **plan**: UPDATE `lbcat`.`only_in_lbcat` SET `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`=42
| 0be56e1     | true     | UpdatedColumn{name=CRAZY!@#\$%^&*()_+{}[]'"COLUMN, value=42} | lbcat2.4test_table                   | **plan**: UPDATE `lbcat2`.`4test_table` SET `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`=42
| 351ccb4     | true     | UpdatedColumn{name=CRAZY!@#\$%^&*()_+{}[]'"COLUMN, value=42} | lbcat2.anotherlowertable             | **plan**: UPDATE `lbcat2`.`anotherlowertable` SET `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`=42
| 7adc129     | true     | UpdatedColumn{name=CRAZY!@#\$%^&*()_+{}[]'"COLUMN, value=42} | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: UPDATE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` SET `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`=42
| 70fbfb4     | true     | UpdatedColumn{name=CRAZY!@#\$%^&*()_+{}[]'"COLUMN, value=42} | lbcat2.lowertable                    | **plan**: UPDATE `lbcat2`.`lowertable` SET `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`=42
| 9468853     | true     | UpdatedColumn{name=CRAZY!@#\$%^&*()_+{}[]'"COLUMN, value=42} | lbcat2.only_in_lbcat2                | **plan**: UPDATE `lbcat2`.`only_in_lbcat2` SET `CRAZY!@#\$%^&*()_+{}[]'"COLUMN`=42
| c5eecab     | true     | UpdatedColumn{name=UPPERCOLUMN, value=42}                    | lbcat.4test_table                    | **plan**: UPDATE `lbcat`.`4test_table` SET `UPPERCOLUMN`=42
| d31c545     | true     | UpdatedColumn{name=UPPERCOLUMN, value=42}                    | lbcat.anotherlowertable              | **plan**: UPDATE `lbcat`.`anotherlowertable` SET `UPPERCOLUMN`=42
| 7770d38     | true     | UpdatedColumn{name=UPPERCOLUMN, value=42}                    | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: UPDATE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` SET `UPPERCOLUMN`=42
| 9ddc66d     | true     | UpdatedColumn{name=UPPERCOLUMN, value=42}                    | lbcat.lowertable                     | **plan**: UPDATE `lbcat`.`lowertable` SET `UPPERCOLUMN`=42
| 70391f1     | true     | UpdatedColumn{name=UPPERCOLUMN, value=42}                    | lbcat.only_in_lbcat                  | **plan**: UPDATE `lbcat`.`only_in_lbcat` SET `UPPERCOLUMN`=42
| 2893fa4     | true     | UpdatedColumn{name=UPPERCOLUMN, value=42}                    | lbcat2.4test_table                   | **plan**: UPDATE `lbcat2`.`4test_table` SET `UPPERCOLUMN`=42
| cfd3cec     | true     | UpdatedColumn{name=UPPERCOLUMN, value=42}                    | lbcat2.anotherlowertable             | **plan**: UPDATE `lbcat2`.`anotherlowertable` SET `UPPERCOLUMN`=42
| 0914336     | true     | UpdatedColumn{name=UPPERCOLUMN, value=42}                    | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: UPDATE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` SET `UPPERCOLUMN`=42
| e3d1e6d     | true     | UpdatedColumn{name=UPPERCOLUMN, value=42}                    | lbcat2.lowertable                    | **plan**: UPDATE `lbcat2`.`lowertable` SET `UPPERCOLUMN`=42
| b56b1b7     | true     | UpdatedColumn{name=UPPERCOLUMN, value=42}                    | lbcat2.only_in_lbcat2                | **plan**: UPDATE `lbcat2`.`only_in_lbcat2` SET `UPPERCOLUMN`=42

# Test Version: "d08fee" #