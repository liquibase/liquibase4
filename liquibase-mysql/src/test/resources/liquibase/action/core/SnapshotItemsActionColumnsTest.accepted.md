**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "autoIncrement information set correctly" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | autoIncrement | column                                                              | OPERATIONS
| :---------- | :------- | :------------ | :------------------------------------------------------------------ | :------
| 8d348f      | true     | false         | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat, null, 4test\_table, ANOTHERUPPERCOLUMN)
| f0df23      | true     | false         | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 339648      | true     | false         | lbcat.4test_table.UPPERCOLUMN                                       | **plan**: getColumns(lbcat, null, 4test\_table, UPPERCOLUMN)
| 332f25      | true     | false         | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | **plan**: getColumns(lbcat, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| 762ae7      | true     | false         | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | **plan**: getColumns(lbcat, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| fc6dd5      | true     | false         | lbcat.anotherlowertable.UPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, anotherlowertable, UPPERCOLUMN)
| 95d2b8      | true     | false         | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| 575eb0      | true     | false         | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 59639a      | true     | false         | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| a31b2c      | true     | false         | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, lowertable, ANOTHERUPPERCOLUMN)
| 60d786      | true     | false         | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | **plan**: getColumns(lbcat, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 714365      | true     | false         | lbcat.lowertable.UPPERCOLUMN                                        | **plan**: getColumns(lbcat, null, lowertable, UPPERCOLUMN)
| 2feb49      | true     | false         | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | **plan**: getColumns(lbcat, null, only\_in\_lbcat, ANOTHERUPPERCOLUMN)
| 99a6ed      | true     | false         | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | **plan**: getColumns(lbcat, null, only\_in\_lbcat, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| b3b8a2      | true     | false         | lbcat.only_in_lbcat.UPPERCOLUMN                                     | **plan**: getColumns(lbcat, null, only\_in\_lbcat, UPPERCOLUMN)
| eb0bec      | true     | false         | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | **plan**: getColumns(lbcat2, null, 4test\_table, ANOTHERUPPERCOLUMN)
| 69f24d      | true     | false         | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | **plan**: getColumns(lbcat2, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| dc2aef      | true     | false         | lbcat2.4test_table.UPPERCOLUMN                                      | **plan**: getColumns(lbcat2, null, 4test\_table, UPPERCOLUMN)
| 049c8f      | true     | false         | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | **plan**: getColumns(lbcat2, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| e2d244      | true     | false         | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | **plan**: getColumns(lbcat2, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 0799fe      | true     | false         | lbcat2.anotherlowertable.UPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, anotherlowertable, UPPERCOLUMN)
| f4330c      | true     | false         | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| ef0c51      | true     | false         | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 47faa6      | true     | false         | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| 95277b      | true     | false         | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, lowertable, ANOTHERUPPERCOLUMN)
| 01b263      | true     | false         | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat2, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| f9f74d      | true     | false         | lbcat2.lowertable.UPPERCOLUMN                                       | **plan**: getColumns(lbcat2, null, lowertable, UPPERCOLUMN)
| ef98fe      | true     | false         | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, ANOTHERUPPERCOLUMN)
| 25e757      | true     | false         | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 57493b      | true     | false         | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, UPPERCOLUMN)
| 8c9888      | true     | true          | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat, null, 4test\_table, ANOTHERUPPERCOLUMN)
| b2ef5d      | true     | true          | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| b43db9      | true     | true          | lbcat.4test_table.UPPERCOLUMN                                       | **plan**: getColumns(lbcat, null, 4test\_table, UPPERCOLUMN)
| e5a478      | true     | true          | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | **plan**: getColumns(lbcat, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| 103da5      | true     | true          | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | **plan**: getColumns(lbcat, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 9e92d7      | true     | true          | lbcat.anotherlowertable.UPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, anotherlowertable, UPPERCOLUMN)
| 198b22      | true     | true          | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| fdae7a      | true     | true          | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 645c86      | true     | true          | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| 57e9ca      | true     | true          | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, lowertable, ANOTHERUPPERCOLUMN)
| ffba91      | true     | true          | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | **plan**: getColumns(lbcat, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 139f64      | true     | true          | lbcat.lowertable.UPPERCOLUMN                                        | **plan**: getColumns(lbcat, null, lowertable, UPPERCOLUMN)
| 192926      | true     | true          | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | **plan**: getColumns(lbcat, null, only\_in\_lbcat, ANOTHERUPPERCOLUMN)
| 936d4a      | true     | true          | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | **plan**: getColumns(lbcat, null, only\_in\_lbcat, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 451d5e      | true     | true          | lbcat.only_in_lbcat.UPPERCOLUMN                                     | **plan**: getColumns(lbcat, null, only\_in\_lbcat, UPPERCOLUMN)
| 4f740c      | true     | true          | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | **plan**: getColumns(lbcat2, null, 4test\_table, ANOTHERUPPERCOLUMN)
| 0f7718      | true     | true          | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | **plan**: getColumns(lbcat2, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 7b632c      | true     | true          | lbcat2.4test_table.UPPERCOLUMN                                      | **plan**: getColumns(lbcat2, null, 4test\_table, UPPERCOLUMN)
| 7ae5c5      | true     | true          | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | **plan**: getColumns(lbcat2, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| 42f2cd      | true     | true          | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | **plan**: getColumns(lbcat2, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 7e4172      | true     | true          | lbcat2.anotherlowertable.UPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, anotherlowertable, UPPERCOLUMN)
| 15ac91      | true     | true          | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| c57b59      | true     | true          | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 4ece8a      | true     | true          | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| ee5026      | true     | true          | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, lowertable, ANOTHERUPPERCOLUMN)
| c547c3      | true     | true          | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat2, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| b22e26      | true     | true          | lbcat2.lowertable.UPPERCOLUMN                                       | **plan**: getColumns(lbcat2, null, lowertable, UPPERCOLUMN)
| b4a6a8      | true     | true          | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, ANOTHERUPPERCOLUMN)
| 922722      | true     | true          | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| cf2dca      | true     | true          | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, UPPERCOLUMN)

# Test: "can find all columns in a fully qualified complex table name" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | table                                | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| c714c6      | true     | lbcat.4test_table                    | **plan**: getColumns(lbcat, null, 4test\_table, null)
| 492248      | true     | lbcat.anotherlowertable              | **plan**: getColumns(lbcat, null, anotherlowertable, null)
| 2f78b2      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, null)
| 7c583f      | true     | lbcat.lowertable                     | **plan**: getColumns(lbcat, null, lowertable, null)
| d93a8c      | true     | lbcat.only_in_lbcat                  | **plan**: getColumns(lbcat, null, only\_in\_lbcat, null)
| 352c7c      | true     | lbcat2.4test_table                   | **plan**: getColumns(lbcat2, null, 4test\_table, null)
| f78fdd      | true     | lbcat2.anotherlowertable             | **plan**: getColumns(lbcat2, null, anotherlowertable, null)
| 1a22ce      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, null)
| ac925c      | true     | lbcat2.lowertable                    | **plan**: getColumns(lbcat2, null, lowertable, null)
| cb2f55      | true     | lbcat2.only_in_lbcat2                | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, null)

# Test: "can find all columns in a schema" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | schema | OPERATIONS
| :---------- | :------- | :----- | :------
| 43a3ea      | true     | lbcat  | **plan**: getColumns(lbcat, null, %, null)
| 06284b      | true     | lbcat2 | **plan**: getColumns(lbcat2, null, %, null)

# Test: "can find fully qualified complex column names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column                                                              | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------ | :------
| 153067      | true     | lbcat.4test_table.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat, null, 4test\_table, ANOTHERUPPERCOLUMN)
| b636c8      | true     | lbcat.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 672f06      | true     | lbcat.4test_table.UPPERCOLUMN                                       | **plan**: getColumns(lbcat, null, 4test\_table, UPPERCOLUMN)
| 888be3      | true     | lbcat.anotherlowertable.ANOTHERUPPERCOLUMN                          | **plan**: getColumns(lbcat, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| 51cb8f      | true     | lbcat.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN              | **plan**: getColumns(lbcat, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| dd60db      | true     | lbcat.anotherlowertable.UPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, anotherlowertable, UPPERCOLUMN)
| f4ef51      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN              | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| 787195      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN  | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 59c8cf      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                     | **plan**: getColumns(lbcat, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| f34cbc      | true     | lbcat.lowertable.ANOTHERUPPERCOLUMN                                 | **plan**: getColumns(lbcat, null, lowertable, ANOTHERUPPERCOLUMN)
| e67310      | true     | lbcat.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                     | **plan**: getColumns(lbcat, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 437de8      | true     | lbcat.lowertable.UPPERCOLUMN                                        | **plan**: getColumns(lbcat, null, lowertable, UPPERCOLUMN)
| fe2603      | true     | lbcat.only_in_lbcat.ANOTHERUPPERCOLUMN                              | **plan**: getColumns(lbcat, null, only\_in\_lbcat, ANOTHERUPPERCOLUMN)
| ca5bd8      | true     | lbcat.only_in_lbcat.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                  | **plan**: getColumns(lbcat, null, only\_in\_lbcat, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 156320      | true     | lbcat.only_in_lbcat.UPPERCOLUMN                                     | **plan**: getColumns(lbcat, null, only\_in\_lbcat, UPPERCOLUMN)
| c9049c      | true     | lbcat2.4test_table.ANOTHERUPPERCOLUMN                               | **plan**: getColumns(lbcat2, null, 4test\_table, ANOTHERUPPERCOLUMN)
| 385701      | true     | lbcat2.4test_table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                   | **plan**: getColumns(lbcat2, null, 4test\_table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| 49c17c      | true     | lbcat2.4test_table.UPPERCOLUMN                                      | **plan**: getColumns(lbcat2, null, 4test\_table, UPPERCOLUMN)
| ae8523      | true     | lbcat2.anotherlowertable.ANOTHERUPPERCOLUMN                         | **plan**: getColumns(lbcat2, null, anotherlowertable, ANOTHERUPPERCOLUMN)
| c93c64      | true     | lbcat2.anotherlowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN             | **plan**: getColumns(lbcat2, null, anotherlowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| aca4ce      | true     | lbcat2.anotherlowertable.UPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, anotherlowertable, UPPERCOLUMN)
| 7775c7      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.ANOTHERUPPERCOLUMN             | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, ANOTHERUPPERCOLUMN)
| 7e364d      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.CRAZY!@#\$%^&*()_+{}[]'"COLUMN | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| f63351      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table.UPPERCOLUMN                    | **plan**: getColumns(lbcat2, null, crazy!@#\\$\%^&*()\_+{}[]'"table, UPPERCOLUMN)
| c52a7f      | true     | lbcat2.lowertable.ANOTHERUPPERCOLUMN                                | **plan**: getColumns(lbcat2, null, lowertable, ANOTHERUPPERCOLUMN)
| 524220      | true     | lbcat2.lowertable.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                    | **plan**: getColumns(lbcat2, null, lowertable, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| bb0d85      | true     | lbcat2.lowertable.UPPERCOLUMN                                       | **plan**: getColumns(lbcat2, null, lowertable, UPPERCOLUMN)
| a33262      | true     | lbcat2.only_in_lbcat2.ANOTHERUPPERCOLUMN                            | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, ANOTHERUPPERCOLUMN)
| 1b9ea1      | true     | lbcat2.only_in_lbcat2.CRAZY!@#\$%^&*()_+{}[]'"COLUMN                | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, CRAZY!@#\\$\%^&*()\_+{}[]'"COLUMN)
| eadefc      | true     | lbcat2.only_in_lbcat2.UPPERCOLUMN                                   | **plan**: getColumns(lbcat2, null, only\_in\_lbcat2, UPPERCOLUMN)

# Test: "dataType comes through correctly" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | column  | type        | OPERATIONS
| :---------- | :------- | :------ | :---------- | :------
| e06713      | true     | TESTCOL | bigint      | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 789234      | true     | TESTCOL | double      | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| f59d2d      | true     | TESTCOL | float       | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 43c309      | true     | TESTCOL | int         | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 978dfa      | true     | TESTCOL | smallint    | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 8cca98      | true     | TESTCOL | varchar(10) | **plan**: getColumns(lbcat, null, testtable, TESTCOL)

# Test: "defaultValue comes through correctly" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | defaultValue | type    | OPERATIONS
| :---------- | :------- | :----------- | :------ | :------
| c13fb3      | true     | int          | TESTCOL | **plan**: getColumns(lbcat, null, testtable, TESTCOL)
| 7b537b      | true     | varchar(20)  | TESTCOL | **plan**: getColumns(lbcat, null, testtable, TESTCOL)

# Test Version: "c69ae6" #