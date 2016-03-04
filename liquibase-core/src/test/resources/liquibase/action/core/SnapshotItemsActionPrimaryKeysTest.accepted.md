**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column PKs correctly" #

- **connection:** generic standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 6e6350      | Generic  | LBSCHEMA  | **plan**: getPrimaryKeys(null, LBSCHEMA, TABLE_NAME)
| bbb8e6      | Generic  | LBSCHEMA2 | **plan**: getPrimaryKeys(null, LBSCHEMA2, TABLE_NAME)

# Test: "can find all primaryKeys in a fully qualified complex table name" #

- **connection:** generic standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 82ca36      | Generic  | LBSCHEMA.4TEST_table                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4TEST_table)
| a59cad      | Generic  | LBSCHEMA.4test_table                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4test_table)
| 02109b      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getPrimaryKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 0556ea      | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: getPrimaryKeys(null, LBSCHEMA, AnotherMixedTable)
| 2cbd26      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getPrimaryKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 6b38df      | Generic  | LBSCHEMA.MixedTable                     | **plan**: getPrimaryKeys(null, LBSCHEMA, MixedTable)
| 763654      | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: getPrimaryKeys(null, LBSCHEMA, ONLY_IN_LBSCHEMA)
| 8f1814      | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: getPrimaryKeys(null, LBSCHEMA, UPPERTABLE)
| 5c8625      | Generic  | LBSCHEMA.anotherlowertable              | **plan**: getPrimaryKeys(null, LBSCHEMA, anotherlowertable)
| 86a5d1      | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getPrimaryKeys(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table)
| c4dbeb      | Generic  | LBSCHEMA.lowertable                     | **plan**: getPrimaryKeys(null, LBSCHEMA, lowertable)
| 31f94a      | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4TEST_table)
| 13e76e      | Generic  | LBSCHEMA2.4test_table                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4test_table)
| cec2e1      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| fe461f      | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: getPrimaryKeys(null, LBSCHEMA2, AnotherMixedTable)
| dec400      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getPrimaryKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| cf72a3      | Generic  | LBSCHEMA2.MixedTable                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, MixedTable)
| 6e579a      | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ONLY_IN_LBSCHEMA2)
| d5eec3      | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, UPPERTABLE)
| 00b229      | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: getPrimaryKeys(null, LBSCHEMA2, anotherlowertable)
| b875ac      | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getPrimaryKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| 5921e4      | Generic  | LBSCHEMA2.lowertable                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, lowertable)

# Test: "can find by PrimaryKeyReference with a table name but null primary key name" #

- **connection:** generic standard

| Permutation | Verified | pk                                                 | OPERATIONS
| :---------- | :------- | :------------------------------------------------- | :------
| 46badb      | Generic  | UNNAMED on LBSCHEMA.4TEST_table                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4TEST_table)
| 37bac9      | Generic  | UNNAMED on LBSCHEMA.4test_table                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4test_table)
| cb724a      | Generic  | UNNAMED on LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getPrimaryKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 2e5e73      | Generic  | UNNAMED on LBSCHEMA.AnotherMixedTable              | **plan**: getPrimaryKeys(null, LBSCHEMA, AnotherMixedTable)
| 775a84      | Generic  | UNNAMED on LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getPrimaryKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| b2d12d      | Generic  | UNNAMED on LBSCHEMA.MixedTable                     | **plan**: getPrimaryKeys(null, LBSCHEMA, MixedTable)
| ccc9c3      | Generic  | UNNAMED on LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: getPrimaryKeys(null, LBSCHEMA, ONLY_IN_LBSCHEMA)
| 3c34be      | Generic  | UNNAMED on LBSCHEMA.UPPERTABLE                     | **plan**: getPrimaryKeys(null, LBSCHEMA, UPPERTABLE)
| d1465f      | Generic  | UNNAMED on LBSCHEMA.anotherlowertable              | **plan**: getPrimaryKeys(null, LBSCHEMA, anotherlowertable)
| b5cdc9      | Generic  | UNNAMED on LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getPrimaryKeys(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table)
| db4820      | Generic  | UNNAMED on LBSCHEMA.lowertable                     | **plan**: getPrimaryKeys(null, LBSCHEMA, lowertable)
| 6d4044      | Generic  | UNNAMED on LBSCHEMA2.4TEST_table                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4TEST_table)
| 1acae6      | Generic  | UNNAMED on LBSCHEMA2.4test_table                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4test_table)
| 4c1d7b      | Generic  | UNNAMED on LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| 685963      | Generic  | UNNAMED on LBSCHEMA2.AnotherMixedTable             | **plan**: getPrimaryKeys(null, LBSCHEMA2, AnotherMixedTable)
| 4f4d4f      | Generic  | UNNAMED on LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getPrimaryKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| dc6634      | Generic  | UNNAMED on LBSCHEMA2.MixedTable                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, MixedTable)
| a1911f      | Generic  | UNNAMED on LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ONLY_IN_LBSCHEMA2)
| 06740c      | Generic  | UNNAMED on LBSCHEMA2.UPPERTABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, UPPERTABLE)
| e40e08      | Generic  | UNNAMED on LBSCHEMA2.anotherlowertable             | **plan**: getPrimaryKeys(null, LBSCHEMA2, anotherlowertable)
| fa1c7e      | Generic  | UNNAMED on LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getPrimaryKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| 11bdab      | Generic  | UNNAMED on LBSCHEMA2.lowertable                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, lowertable)

# Test: "can find complex pk names with a table" #

- **connection:** generic standard

| Permutation | Verified | pk                                                          | OPERATIONS
| :---------- | :------- | :---------------------------------------------------------- | :------
| 1f522c      | Generic  | 4TEST_primarykey on LBSCHEMA.KNOWN_TABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 99af2a      | Generic  | 4TEST_primarykey on LBSCHEMA2.KNOWN_TABLE                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| e35650      | Generic  | 4test_primarykey on LBSCHEMA.KNOWN_TABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 8c4e33      | Generic  | 4test_primarykey on LBSCHEMA2.KNOWN_TABLE                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| e215ad      | Generic  | ANOTHERUPPERPRIMARYKEY on LBSCHEMA.KNOWN_TABLE              | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| a0e684      | Generic  | ANOTHERUPPERPRIMARYKEY on LBSCHEMA2.KNOWN_TABLE             | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 96e561      | Generic  | AnotherMixedPrimaryKey on LBSCHEMA.KNOWN_TABLE              | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 8d975e      | Generic  | AnotherMixedPrimaryKey on LBSCHEMA2.KNOWN_TABLE             | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| bbccab      | Generic  | CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY on LBSCHEMA.KNOWN_TABLE  | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 1ff47b      | Generic  | CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY on LBSCHEMA2.KNOWN_TABLE | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| ccfe55      | Generic  | MixedPrimaryKey on LBSCHEMA.KNOWN_TABLE                     | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| e0f6a8      | Generic  | MixedPrimaryKey on LBSCHEMA2.KNOWN_TABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 4ee846      | Generic  | UPPERPRIMARYKEY on LBSCHEMA.KNOWN_TABLE                     | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| cb0427      | Generic  | UPPERPRIMARYKEY on LBSCHEMA2.KNOWN_TABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 0a9ccc      | Generic  | anotherlowerprimarykey on LBSCHEMA.KNOWN_TABLE              | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 29d917      | Generic  | anotherlowerprimarykey on LBSCHEMA2.KNOWN_TABLE             | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| f5cf37      | Generic  | crazy!@#\$%^&*()_+{}[]'"primarykey on LBSCHEMA.KNOWN_TABLE  | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 671ed8      | Generic  | crazy!@#\$%^&*()_+{}[]'"primarykey on LBSCHEMA2.KNOWN_TABLE | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 0b9382      | Generic  | lowerprimarykey on LBSCHEMA.KNOWN_TABLE                     | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 56122e      | Generic  | lowerprimarykey on LBSCHEMA2.KNOWN_TABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)

# Test Version: "2dd6e1" #