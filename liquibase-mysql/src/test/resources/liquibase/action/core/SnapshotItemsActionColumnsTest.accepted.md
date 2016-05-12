**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "autoIncrement information set correctly" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | autoIncrement | column                                                              | OPERATIONS
| :---------- | :------- | :------------ | :------------------------------------------------------------------ | :------
| 8d348fc     | true     | false         | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat, null, 4test\_table, ANOTHERUPPERCOLUMN)
| f0df239     | true     | false         | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 3396481     | true     | false         | lbcat.4test_table.UPPERCOLUMN                                       | **plan**: getColumns(lbcat, null, 4test\_table, UPPERCOLUMN)
| 332f259     | true     | false         | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | **plan**: getColumns(lbcat, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| 762ae79     | true     | false         | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | **plan**: getColumns(lbcat, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| fc6dd52     | true     | false         | lbcat.anotherlowertable.UPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, anotherlowertable, UPPERCOLUMN)
| 95d2b82     | true     | false         | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| 575eb0d     | true     | false         | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 59639a9     | true     | false         | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| a31b2c2     | true     | false         | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, lowertable, ANOTHERUPPERCOLUMN)
| 60d7863     | true     | false         | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | **plan**: getColumns(lbcat, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 714365d     | true     | false         | lbcat.lowertable.UPPERCOLUMN                                        | **plan**: getColumns(lbcat, null, lowertable, UPPERCOLUMN)
| 2feb494     | true     | false         | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | **plan**: getColumns(lbcat, null, only\_in\_lbcat, ANOTHERUPPERCOLUMN)
| 99a6ed8     | true     | false         | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | **plan**: getColumns(lbcat, null, only\_in\_lbcat, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| b3b8a20     | true     | false         | lbcat.only_in_lbcat.UPPERCOLUMN                                     | **plan**: getColumns(lbcat, null, only\_in\_lbcat, UPPERCOLUMN)
| eb0bec3     | true     | false         | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | **plan**: getColumns(lbcat2, null, 4test\_table, ANOTHERUPPERCOLUMN)
| 69f24d9     | true     | false         | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | **plan**: getColumns(lbcat2, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| dc2aefc     | true     | false         | lbcat2.4test_table.UPPERCOLUMN                                      | **plan**: getColumns(lbcat2, null, 4test\_table, UPPERCOLUMN)
| 049c8f1     | true     | false         | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | **plan**: getColumns(lbcat2, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| e2d244d     | true     | false         | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | **plan**: getColumns(lbcat2, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 0799fec     | true     | false         | lbcat2.anotherlowertable.UPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, anotherlowertable, UPPERCOLUMN)
| f4330c7     | true     | false         | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| ef0c517     | true     | false         | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 47faa60     | true     | false         | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| 95277b4     | true     | false         | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, lowertable, ANOTHERUPPERCOLUMN)
| 01b2632     | true     | false         | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat2, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| f9f74de     | true     | false         | lbcat2.lowertable.UPPERCOLUMN                                       | **plan**: getColumns(lbcat2, null, lowertable, UPPERCOLUMN)
| ef98fee     | true     | false         | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, ANOTHERUPPERCOLUMN)
| 25e757b     | true     | false         | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 57493b9     | true     | false         | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, UPPERCOLUMN)
| 8c98886     | true     | true          | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat, null, 4test\_table, ANOTHERUPPERCOLUMN)
| b2ef5d7     | true     | true          | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| b43db93     | true     | true          | lbcat.4test_table.UPPERCOLUMN                                       | **plan**: getColumns(lbcat, null, 4test\_table, UPPERCOLUMN)
| e5a478b     | true     | true          | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | **plan**: getColumns(lbcat, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| 103da52     | true     | true          | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | **plan**: getColumns(lbcat, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 9e92d70     | true     | true          | lbcat.anotherlowertable.UPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, anotherlowertable, UPPERCOLUMN)
| 198b226     | true     | true          | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| fdae7ac     | true     | true          | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 645c86e     | true     | true          | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| 57e9ca4     | true     | true          | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, lowertable, ANOTHERUPPERCOLUMN)
| ffba91a     | true     | true          | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | **plan**: getColumns(lbcat, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 139f648     | true     | true          | lbcat.lowertable.UPPERCOLUMN                                        | **plan**: getColumns(lbcat, null, lowertable, UPPERCOLUMN)
| 1929265     | true     | true          | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | **plan**: getColumns(lbcat, null, only\_in\_lbcat, ANOTHERUPPERCOLUMN)
| 936d4ad     | true     | true          | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | **plan**: getColumns(lbcat, null, only\_in\_lbcat, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 451d5ed     | true     | true          | lbcat.only_in_lbcat.UPPERCOLUMN                                     | **plan**: getColumns(lbcat, null, only\_in\_lbcat, UPPERCOLUMN)
| 4f740c3     | true     | true          | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | **plan**: getColumns(lbcat2, null, 4test\_table, ANOTHERUPPERCOLUMN)
| 0f7718e     | true     | true          | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | **plan**: getColumns(lbcat2, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 7b632cd     | true     | true          | lbcat2.4test_table.UPPERCOLUMN                                      | **plan**: getColumns(lbcat2, null, 4test\_table, UPPERCOLUMN)
| 7ae5c52     | true     | true          | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | **plan**: getColumns(lbcat2, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| 42f2cd2     | true     | true          | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | **plan**: getColumns(lbcat2, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 7e4172d     | true     | true          | lbcat2.anotherlowertable.UPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, anotherlowertable, UPPERCOLUMN)
| 15ac910     | true     | true          | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| c57b599     | true     | true          | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 4ece8ac     | true     | true          | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| ee50260     | true     | true          | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, lowertable, ANOTHERUPPERCOLUMN)
| c547c3d     | true     | true          | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat2, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| b22e265     | true     | true          | lbcat2.lowertable.UPPERCOLUMN                                       | **plan**: getColumns(lbcat2, null, lowertable, UPPERCOLUMN)
| b4a6a87     | true     | true          | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, ANOTHERUPPERCOLUMN)
| 922722a     | true     | true          | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| cf2dca9     | true     | true          | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, UPPERCOLUMN)

# Test: "can find all columns in a fully qualified complex table name" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | table                                | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| c714c6a     | true     | lbcat.4test_table                    | **plan**: getColumns(lbcat, null, 4test\_table, null)
| 4922487     | true     | lbcat.anotherlowertable              | **plan**: getColumns(lbcat, null, anotherlowertable, null)
| 2f78b20     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, null)
| 7c583f0     | true     | lbcat.lowertable                     | **plan**: getColumns(lbcat, null, lowertable, null)
| d93a8c8     | true     | lbcat.only_in_lbcat                  | **plan**: getColumns(lbcat, null, only\_in\_lbcat, null)
| 352c7c3     | true     | lbcat2.4test_table                   | **plan**: getColumns(lbcat2, null, 4test\_table, null)
| f78fdd5     | true     | lbcat2.anotherlowertable             | **plan**: getColumns(lbcat2, null, anotherlowertable, null)
| 1a22ce6     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, null)
| ac925cd     | true     | lbcat2.lowertable                    | **plan**: getColumns(lbcat2, null, lowertable, null)
| cb2f558     | true     | lbcat2.only_in_lbcat2                | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, null)

# Test: "can find all columns in a schema" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | schema | OPERATIONS
| :---------- | :------- | :----- | :------
| 43a3eab     | true     | lbcat  | **plan**: getColumns(lbcat, null, %, null)
| 06284bc     | true     | lbcat2 | **plan**: getColumns(lbcat2, null, %, null)

# Test: "can find fully qualified complex column names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                                                              | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------
| 1530675     | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat, null, 4test\_table, ANOTHERUPPERCOLUMN)
| b636c88     | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 672f06a     | true     | lbcat.4test_table.UPPERCOLUMN                                       | **plan**: getColumns(lbcat, null, 4test\_table, UPPERCOLUMN)
| 888be33     | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | **plan**: getColumns(lbcat, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| 51cb8f8     | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | **plan**: getColumns(lbcat, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| dd60db9     | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, anotherlowertable, UPPERCOLUMN)
| f4ef516     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| 7871953     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 59c8cf3     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| f34cbc1     | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, lowertable, ANOTHERUPPERCOLUMN)
| e67310a     | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | **plan**: getColumns(lbcat, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 437de8b     | true     | lbcat.lowertable.UPPERCOLUMN                                        | **plan**: getColumns(lbcat, null, lowertable, UPPERCOLUMN)
| fe26032     | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | **plan**: getColumns(lbcat, null, only\_in\_lbcat, ANOTHERUPPERCOLUMN)
| ca5bd8d     | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | **plan**: getColumns(lbcat, null, only\_in\_lbcat, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 156320d     | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | **plan**: getColumns(lbcat, null, only\_in\_lbcat, UPPERCOLUMN)
| c9049c1     | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | **plan**: getColumns(lbcat2, null, 4test\_table, ANOTHERUPPERCOLUMN)
| 3857017     | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | **plan**: getColumns(lbcat2, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 49c17c9     | true     | lbcat2.4test_table.UPPERCOLUMN                                      | **plan**: getColumns(lbcat2, null, 4test\_table, UPPERCOLUMN)
| ae8523a     | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | **plan**: getColumns(lbcat2, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| c93c64f     | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | **plan**: getColumns(lbcat2, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| aca4ce5     | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, anotherlowertable, UPPERCOLUMN)
| 7775c73     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| 7e364d7     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| f633518     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| c52a7fc     | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, lowertable, ANOTHERUPPERCOLUMN)
| 524220f     | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat2, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| bb0d857     | true     | lbcat2.lowertable.UPPERCOLUMN                                       | **plan**: getColumns(lbcat2, null, lowertable, UPPERCOLUMN)
| a332625     | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, ANOTHERUPPERCOLUMN)
| 1b9ea13     | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| eadefc4     | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, UPPERCOLUMN)

# Test: "dataType comes through correctly" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column  | type        | OPERATIONS
| :---------- | :------- | :------ | :---------- | :------
| e06713c     | true     | TESTCOL | bigint      | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 7892340     | true     | TESTCOL | double      | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| f59d2dc     | true     | TESTCOL | float       | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 43c3097     | true     | TESTCOL | int         | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 978dfa5     | true     | TESTCOL | smallint    | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 8cca985     | true     | TESTCOL | varchar(10) | **plan**: getColumns(lbcat, null, testtable, TESTCOL)

# Test: "defaultValue comes through correctly" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | defaultValue | type    | OPERATIONS
| :---------- | :------- | :----------- | :------ | :------
| c13fb35     | true     | int          | TESTCOL | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 7b537bc     | true     | varchar(20)  | TESTCOL | **plan**: getColumns(lbcat, null, testtable, TESTCOL)

# Test Version: "c32774" #