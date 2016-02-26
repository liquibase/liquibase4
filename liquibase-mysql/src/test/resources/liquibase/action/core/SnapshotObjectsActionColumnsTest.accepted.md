**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "autoIncrement information set correctly" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | autoIncrement | column                                                              | OPERATIONS
| :---------- | :------- | :------------ | :------------------------------------------------------------------ | :------
| d1e66b      | true     | false         | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat, null, 4test\_table, ANOTHERUPPERCOLUMN)
| c23ae1      | true     | false         | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| ccab01      | true     | false         | lbcat.4test_table.UPPERCOLUMN                                       | **plan**: getColumns(lbcat, null, 4test\_table, UPPERCOLUMN)
| 31e795      | true     | false         | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | **plan**: getColumns(lbcat, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| 442e20      | true     | false         | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | **plan**: getColumns(lbcat, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 160103      | true     | false         | lbcat.anotherlowertable.UPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, anotherlowertable, UPPERCOLUMN)
| e6eed0      | true     | false         | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| 79d0d8      | true     | false         | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 3f3f01      | true     | false         | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| d55862      | true     | false         | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, lowertable, ANOTHERUPPERCOLUMN)
| 3013f5      | true     | false         | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | **plan**: getColumns(lbcat, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 595422      | true     | false         | lbcat.lowertable.UPPERCOLUMN                                        | **plan**: getColumns(lbcat, null, lowertable, UPPERCOLUMN)
| a105b9      | true     | false         | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | **plan**: getColumns(lbcat, null, only\_in\_lbcat, ANOTHERUPPERCOLUMN)
| 31270e      | true     | false         | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | **plan**: getColumns(lbcat, null, only\_in\_lbcat, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 7472e7      | true     | false         | lbcat.only_in_lbcat.UPPERCOLUMN                                     | **plan**: getColumns(lbcat, null, only\_in\_lbcat, UPPERCOLUMN)
| 925306      | true     | false         | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | **plan**: getColumns(lbcat2, null, 4test\_table, ANOTHERUPPERCOLUMN)
| acb163      | true     | false         | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | **plan**: getColumns(lbcat2, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 715403      | true     | false         | lbcat2.4test_table.UPPERCOLUMN                                      | **plan**: getColumns(lbcat2, null, 4test\_table, UPPERCOLUMN)
| 16fbef      | true     | false         | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | **plan**: getColumns(lbcat2, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| ea6ffa      | true     | false         | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | **plan**: getColumns(lbcat2, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 2391fb      | true     | false         | lbcat2.anotherlowertable.UPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, anotherlowertable, UPPERCOLUMN)
| d9210c      | true     | false         | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| 2ade97      | true     | false         | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| bf5697      | true     | false         | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| a6ce92      | true     | false         | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, lowertable, ANOTHERUPPERCOLUMN)
| 6afb30      | true     | false         | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat2, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| ffb4d5      | true     | false         | lbcat2.lowertable.UPPERCOLUMN                                       | **plan**: getColumns(lbcat2, null, lowertable, UPPERCOLUMN)
| a15f03      | true     | false         | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, ANOTHERUPPERCOLUMN)
| 92b689      | true     | false         | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 54edb1      | true     | false         | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, UPPERCOLUMN)
| 88d002      | true     | true          | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat, null, 4test\_table, ANOTHERUPPERCOLUMN)
| c45ace      | true     | true          | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 11a3ce      | true     | true          | lbcat.4test_table.UPPERCOLUMN                                       | **plan**: getColumns(lbcat, null, 4test\_table, UPPERCOLUMN)
| 15fd6e      | true     | true          | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | **plan**: getColumns(lbcat, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| 75a17a      | true     | true          | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | **plan**: getColumns(lbcat, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 518dbf      | true     | true          | lbcat.anotherlowertable.UPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, anotherlowertable, UPPERCOLUMN)
| a8e700      | true     | true          | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| 26420e      | true     | true          | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 07afda      | true     | true          | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| fd94df      | true     | true          | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, lowertable, ANOTHERUPPERCOLUMN)
| 01cc64      | true     | true          | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | **plan**: getColumns(lbcat, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 3ef30c      | true     | true          | lbcat.lowertable.UPPERCOLUMN                                        | **plan**: getColumns(lbcat, null, lowertable, UPPERCOLUMN)
| d4480d      | true     | true          | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | **plan**: getColumns(lbcat, null, only\_in\_lbcat, ANOTHERUPPERCOLUMN)
| 1b7f7e      | true     | true          | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | **plan**: getColumns(lbcat, null, only\_in\_lbcat, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| a35697      | true     | true          | lbcat.only_in_lbcat.UPPERCOLUMN                                     | **plan**: getColumns(lbcat, null, only\_in\_lbcat, UPPERCOLUMN)
| 47bde6      | true     | true          | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | **plan**: getColumns(lbcat2, null, 4test\_table, ANOTHERUPPERCOLUMN)
| 67a87c      | true     | true          | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | **plan**: getColumns(lbcat2, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 552745      | true     | true          | lbcat2.4test_table.UPPERCOLUMN                                      | **plan**: getColumns(lbcat2, null, 4test\_table, UPPERCOLUMN)
| 7f8159      | true     | true          | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | **plan**: getColumns(lbcat2, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| 8362bf      | true     | true          | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | **plan**: getColumns(lbcat2, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 5e1ff0      | true     | true          | lbcat2.anotherlowertable.UPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, anotherlowertable, UPPERCOLUMN)
| 231ad0      | true     | true          | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| fae7cb      | true     | true          | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 6cdea3      | true     | true          | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| d5b94d      | true     | true          | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, lowertable, ANOTHERUPPERCOLUMN)
| 3a9aaa      | true     | true          | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat2, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 9d46d9      | true     | true          | lbcat2.lowertable.UPPERCOLUMN                                       | **plan**: getColumns(lbcat2, null, lowertable, UPPERCOLUMN)
| 9592a3      | true     | true          | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, ANOTHERUPPERCOLUMN)
| b19717      | true     | true          | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| a894dc      | true     | true          | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, UPPERCOLUMN)

# Test: "can find all columns in a fully qualified complex table name" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | table                                | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| f75adb      | true     | lbcat.4test_table                    | **plan**: getColumns(lbcat, null, 4test\_table, null)
| 5e7754      | true     | lbcat.anotherlowertable              | **plan**: getColumns(lbcat, null, anotherlowertable, null)
| ca0403      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, null)
| ad19b9      | true     | lbcat.lowertable                     | **plan**: getColumns(lbcat, null, lowertable, null)
| cea6bc      | true     | lbcat.only_in_lbcat                  | **plan**: getColumns(lbcat, null, only\_in\_lbcat, null)
| bc878f      | true     | lbcat2.4test_table                   | **plan**: getColumns(lbcat2, null, 4test\_table, null)
| b48dc5      | true     | lbcat2.anotherlowertable             | **plan**: getColumns(lbcat2, null, anotherlowertable, null)
| b85e41      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, null)
| f95c81      | true     | lbcat2.lowertable                    | **plan**: getColumns(lbcat2, null, lowertable, null)
| d76757      | true     | lbcat2.only_in_lbcat2                | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, null)

# Test: "can find all columns in a schema" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | schema | OPERATIONS
| :---------- | :------- | :----- | :------
| 4935d9      | true     | lbcat  | **plan**: getColumns(lbcat, null, %, null)
| e78147      | true     | lbcat2 | **plan**: getColumns(lbcat2, null, %, null)

# Test: "can find fully qualified complex column names" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | column                                                              | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------
| 7baeba      | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat, null, 4test\_table, ANOTHERUPPERCOLUMN)
| 32862a      | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 4e4b15      | true     | lbcat.4test_table.UPPERCOLUMN                                       | **plan**: getColumns(lbcat, null, 4test\_table, UPPERCOLUMN)
| 42f816      | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | **plan**: getColumns(lbcat, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| d412fa      | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | **plan**: getColumns(lbcat, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 4c651d      | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, anotherlowertable, UPPERCOLUMN)
| 43db30      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| a814fc      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| d4ade5      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| edf518      | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, lowertable, ANOTHERUPPERCOLUMN)
| fc2242      | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | **plan**: getColumns(lbcat, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| a0f9b1      | true     | lbcat.lowertable.UPPERCOLUMN                                        | **plan**: getColumns(lbcat, null, lowertable, UPPERCOLUMN)
| 7d7666      | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | **plan**: getColumns(lbcat, null, only\_in\_lbcat, ANOTHERUPPERCOLUMN)
| 5f1036      | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | **plan**: getColumns(lbcat, null, only\_in\_lbcat, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 795b5d      | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | **plan**: getColumns(lbcat, null, only\_in\_lbcat, UPPERCOLUMN)
| ed78ab      | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | **plan**: getColumns(lbcat2, null, 4test\_table, ANOTHERUPPERCOLUMN)
| 6a3196      | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | **plan**: getColumns(lbcat2, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 2f1058      | true     | lbcat2.4test_table.UPPERCOLUMN                                      | **plan**: getColumns(lbcat2, null, 4test\_table, UPPERCOLUMN)
| f218f9      | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | **plan**: getColumns(lbcat2, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| 16813a      | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | **plan**: getColumns(lbcat2, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| ecf614      | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, anotherlowertable, UPPERCOLUMN)
| 7a8c33      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| d6dfd5      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| b4bfdd      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| ee34ab      | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, lowertable, ANOTHERUPPERCOLUMN)
| 00e79a      | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat2, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| de281d      | true     | lbcat2.lowertable.UPPERCOLUMN                                       | **plan**: getColumns(lbcat2, null, lowertable, UPPERCOLUMN)
| a717ae      | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, ANOTHERUPPERCOLUMN)
| d17a9d      | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| dafb30      | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, UPPERCOLUMN)

# Test: "dataType comes through correctly" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | column  | type        | OPERATIONS
| :---------- | :------- | :------ | :---------- | :------
| 3e7b0a      | true     | TESTCOL | bigint      | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 2fb154      | true     | TESTCOL | double      | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 49b22e      | true     | TESTCOL | float       | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| a6671e      | true     | TESTCOL | int         | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 474fe8      | true     | TESTCOL | smallint    | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| ccfd8c      | true     | TESTCOL | varchar(10) | **plan**: getColumns(lbcat, null, testtable, TESTCOL)

# Test: "defaultValue comes through correctly" #

- **connection:** mysql[config:caseInsensitive]

| Permutation | Verified | defaultValue | type    | OPERATIONS
| :---------- | :------- | :----------- | :------ | :------
| e420b1      | true     | int          | TESTCOL | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 93208e      | true     | varchar(20)  | TESTCOL | **plan**: getColumns(lbcat, null, testtable, TESTCOL)

# Test Version: "8f9c60" #