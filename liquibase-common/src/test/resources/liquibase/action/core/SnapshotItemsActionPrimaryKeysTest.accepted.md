**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column PKs correctly" #

- **connection:** generic standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 6e63508     | Generic  | LBSCHEMA  | **plan**: getPrimaryKeys(null, LBSCHEMA, TABLE_NAME)
| bbb8e61     | Generic  | LBSCHEMA2 | **plan**: getPrimaryKeys(null, LBSCHEMA2, TABLE_NAME)

# Test: "can find all primaryKeys in a fully qualified complex table name" #

- **connection:** generic standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 82ca36a     | Generic  | LBSCHEMA.4TEST_table                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4TEST_table)
| a59cadd     | Generic  | LBSCHEMA.4test_table                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4test_table)
| 02109bf     | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getPrimaryKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 0556eae     | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: getPrimaryKeys(null, LBSCHEMA, AnotherMixedTable)
| 2cbd26a     | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getPrimaryKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 6b38df5     | Generic  | LBSCHEMA.MixedTable                     | **plan**: getPrimaryKeys(null, LBSCHEMA, MixedTable)
| 7636549     | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: getPrimaryKeys(null, LBSCHEMA, ONLY_IN_LBSCHEMA)
| 8f18146     | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: getPrimaryKeys(null, LBSCHEMA, UPPERTABLE)
| 5c86256     | Generic  | LBSCHEMA.anotherlowertable              | **plan**: getPrimaryKeys(null, LBSCHEMA, anotherlowertable)
| 86a5d18     | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getPrimaryKeys(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table)
| c4dbeb3     | Generic  | LBSCHEMA.lowertable                     | **plan**: getPrimaryKeys(null, LBSCHEMA, lowertable)
| 31f94ac     | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4TEST_table)
| 13e76ef     | Generic  | LBSCHEMA2.4test_table                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4test_table)
| cec2e1c     | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| fe461f7     | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: getPrimaryKeys(null, LBSCHEMA2, AnotherMixedTable)
| dec400d     | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getPrimaryKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| cf72a30     | Generic  | LBSCHEMA2.MixedTable                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, MixedTable)
| 6e579ae     | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ONLY_IN_LBSCHEMA2)
| d5eec30     | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, UPPERTABLE)
| 00b229a     | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: getPrimaryKeys(null, LBSCHEMA2, anotherlowertable)
| b875ac8     | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getPrimaryKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| 5921e4d     | Generic  | LBSCHEMA2.lowertable                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, lowertable)

# Test: "can find by PrimaryKeyReference with a table name but null primary key name" #

- **connection:** generic standard

| Permutation | Verified | pk                                                 | OPERATIONS
| :---------- | :------- | :------------------------------------------------- | :------
| 46badb6     | Generic  | UNNAMED on LBSCHEMA.4TEST_table                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4TEST_table)
| 37bac95     | Generic  | UNNAMED on LBSCHEMA.4test_table                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4test_table)
| cb724ad     | Generic  | UNNAMED on LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getPrimaryKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 2e5e73e     | Generic  | UNNAMED on LBSCHEMA.AnotherMixedTable              | **plan**: getPrimaryKeys(null, LBSCHEMA, AnotherMixedTable)
| 775a848     | Generic  | UNNAMED on LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getPrimaryKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| b2d12d9     | Generic  | UNNAMED on LBSCHEMA.MixedTable                     | **plan**: getPrimaryKeys(null, LBSCHEMA, MixedTable)
| ccc9c33     | Generic  | UNNAMED on LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: getPrimaryKeys(null, LBSCHEMA, ONLY_IN_LBSCHEMA)
| 3c34beb     | Generic  | UNNAMED on LBSCHEMA.UPPERTABLE                     | **plan**: getPrimaryKeys(null, LBSCHEMA, UPPERTABLE)
| d1465fa     | Generic  | UNNAMED on LBSCHEMA.anotherlowertable              | **plan**: getPrimaryKeys(null, LBSCHEMA, anotherlowertable)
| b5cdc94     | Generic  | UNNAMED on LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getPrimaryKeys(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table)
| db48200     | Generic  | UNNAMED on LBSCHEMA.lowertable                     | **plan**: getPrimaryKeys(null, LBSCHEMA, lowertable)
| 6d40444     | Generic  | UNNAMED on LBSCHEMA2.4TEST_table                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4TEST_table)
| 1acae69     | Generic  | UNNAMED on LBSCHEMA2.4test_table                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4test_table)
| 4c1d7bc     | Generic  | UNNAMED on LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| 6859631     | Generic  | UNNAMED on LBSCHEMA2.AnotherMixedTable             | **plan**: getPrimaryKeys(null, LBSCHEMA2, AnotherMixedTable)
| 4f4d4ff     | Generic  | UNNAMED on LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getPrimaryKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| dc66343     | Generic  | UNNAMED on LBSCHEMA2.MixedTable                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, MixedTable)
| a1911fd     | Generic  | UNNAMED on LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ONLY_IN_LBSCHEMA2)
| 06740cb     | Generic  | UNNAMED on LBSCHEMA2.UPPERTABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, UPPERTABLE)
| e40e083     | Generic  | UNNAMED on LBSCHEMA2.anotherlowertable             | **plan**: getPrimaryKeys(null, LBSCHEMA2, anotherlowertable)
| fa1c7ec     | Generic  | UNNAMED on LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getPrimaryKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| 11bdab9     | Generic  | UNNAMED on LBSCHEMA2.lowertable                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, lowertable)

# Test: "can find complex pk names with a table" #

- **connection:** generic standard

| Permutation | Verified | pk                                                          | OPERATIONS
| :---------- | :------- | :---------------------------------------------------------- | :------
| 1f522c6     | Generic  | 4TEST_primarykey on LBSCHEMA.KNOWN_TABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 99af2a7     | Generic  | 4TEST_primarykey on LBSCHEMA2.KNOWN_TABLE                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| e35650d     | Generic  | 4test_primarykey on LBSCHEMA.KNOWN_TABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 8c4e33b     | Generic  | 4test_primarykey on LBSCHEMA2.KNOWN_TABLE                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| e215ad8     | Generic  | ANOTHERUPPERPRIMARYKEY on LBSCHEMA.KNOWN_TABLE              | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| a0e6846     | Generic  | ANOTHERUPPERPRIMARYKEY on LBSCHEMA2.KNOWN_TABLE             | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 96e5618     | Generic  | AnotherMixedPrimaryKey on LBSCHEMA.KNOWN_TABLE              | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 8d975e9     | Generic  | AnotherMixedPrimaryKey on LBSCHEMA2.KNOWN_TABLE             | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| bbccab7     | Generic  | CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY on LBSCHEMA.KNOWN_TABLE  | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 1ff47bb     | Generic  | CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY on LBSCHEMA2.KNOWN_TABLE | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| ccfe55a     | Generic  | MixedPrimaryKey on LBSCHEMA.KNOWN_TABLE                     | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| e0f6a8c     | Generic  | MixedPrimaryKey on LBSCHEMA2.KNOWN_TABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 4ee8460     | Generic  | UPPERPRIMARYKEY on LBSCHEMA.KNOWN_TABLE                     | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| cb04277     | Generic  | UPPERPRIMARYKEY on LBSCHEMA2.KNOWN_TABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 0a9ccc6     | Generic  | anotherlowerprimarykey on LBSCHEMA.KNOWN_TABLE              | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 29d917f     | Generic  | anotherlowerprimarykey on LBSCHEMA2.KNOWN_TABLE             | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| f5cf37c     | Generic  | crazy!@#\$%^&*()_+{}[]'"primarykey on LBSCHEMA.KNOWN_TABLE  | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 671ed82     | Generic  | crazy!@#\$%^&*()_+{}[]'"primarykey on LBSCHEMA2.KNOWN_TABLE | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)
| 0b9382e     | Generic  | lowerprimarykey on LBSCHEMA.KNOWN_TABLE                     | **plan**: getPrimaryKeys(null, LBSCHEMA, KNOWN_TABLE)
| 56122e3     | Generic  | lowerprimarykey on LBSCHEMA2.KNOWN_TABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, KNOWN_TABLE)

# Test Version: "f39632" #