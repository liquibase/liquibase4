**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column PKs correctly" #

- **connection:** generic[config:standard]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 0f43a8      | Generic  | LBSCHEMA   | **plan**: getPrimaryKeys(null, LBSCHEMA, TABLE_NAME)
| eb7c69      | Generic  | LBSCHEMA2  | **plan**: getPrimaryKeys(null, LBSCHEMA2, TABLE_NAME)

# Test: "can find all primaryKeys in a fully qualified complex table name" #

- **connection:** generic[config:standard]

| Permutation | Verified | tableName                               | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 6f10b3      | Generic  | LBSCHEMA.4TEST_table                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4TEST_table)
| 447604      | Generic  | LBSCHEMA.4test_table                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4test_table)
| 7d8923      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getPrimaryKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 244240      | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: getPrimaryKeys(null, LBSCHEMA, AnotherMixedTable)
| cf6e70      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getPrimaryKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 1ab5be      | Generic  | LBSCHEMA.MixedTable                     | **plan**: getPrimaryKeys(null, LBSCHEMA, MixedTable)
| d62fef      | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: getPrimaryKeys(null, LBSCHEMA, UPPERTABLE)
| b7bd07      | Generic  | LBSCHEMA.anotherlowertable              | **plan**: getPrimaryKeys(null, LBSCHEMA, anotherlowertable)
| 8d1056      | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getPrimaryKeys(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table)
| f03884      | Generic  | LBSCHEMA.lowertable                     | **plan**: getPrimaryKeys(null, LBSCHEMA, lowertable)
| a69da2      | Generic  | LBSCHEMA.only_in_LBSCHEMA               | **plan**: getPrimaryKeys(null, LBSCHEMA, only_in_LBSCHEMA)
| a86d64      | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4TEST_table)
| 590a6e      | Generic  | LBSCHEMA2.4test_table                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4test_table)
| 2927a8      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| ce1ca3      | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: getPrimaryKeys(null, LBSCHEMA2, AnotherMixedTable)
| 710fa9      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getPrimaryKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 315ef1      | Generic  | LBSCHEMA2.MixedTable                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, MixedTable)
| 33d15e      | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, UPPERTABLE)
| 22dc5a      | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: getPrimaryKeys(null, LBSCHEMA2, anotherlowertable)
| fbf4a4      | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getPrimaryKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| 55094c      | Generic  | LBSCHEMA2.lowertable                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, lowertable)
| f90500      | Generic  | LBSCHEMA2.only_in_LBSCHEMA2             | **plan**: getPrimaryKeys(null, LBSCHEMA2, only_in_LBSCHEMA2)

# Test: "can find by PrimaryKeyReference with a table name but null primary key name" #

- **connection:** generic[config:standard]

| Permutation | Verified | pkName                                          | OPERATIONS
| :---------- | :------- | :---------------------------------------------- | :------
| 516ac9      | Generic  | LBSCHEMA.4TEST_table.UNNAMED                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4TEST_table)
| bdc842      | Generic  | LBSCHEMA.4test_table.UNNAMED                    | **plan**: getPrimaryKeys(null, LBSCHEMA, 4test_table)
| 8acb9e      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE.UNNAMED              | **plan**: getPrimaryKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 375e0d      | Generic  | LBSCHEMA.AnotherMixedTable.UNNAMED              | **plan**: getPrimaryKeys(null, LBSCHEMA, AnotherMixedTable)
| 81495b      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED  | **plan**: getPrimaryKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 629fb7      | Generic  | LBSCHEMA.MixedTable.UNNAMED                     | **plan**: getPrimaryKeys(null, LBSCHEMA, MixedTable)
| 008c16      | Generic  | LBSCHEMA.UPPERTABLE.UNNAMED                     | **plan**: getPrimaryKeys(null, LBSCHEMA, UPPERTABLE)
| e1d038      | Generic  | LBSCHEMA.anotherlowertable.UNNAMED              | **plan**: getPrimaryKeys(null, LBSCHEMA, anotherlowertable)
| cf8831      | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED  | **plan**: getPrimaryKeys(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table)
| 9e8a70      | Generic  | LBSCHEMA.lowertable.UNNAMED                     | **plan**: getPrimaryKeys(null, LBSCHEMA, lowertable)
| c43512      | Generic  | LBSCHEMA.only_in_LBSCHEMA.UNNAMED               | **plan**: getPrimaryKeys(null, LBSCHEMA, only_in_LBSCHEMA)
| ec155e      | Generic  | LBSCHEMA2.4TEST_table.UNNAMED                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4TEST_table)
| 081a49      | Generic  | LBSCHEMA2.4test_table.UNNAMED                   | **plan**: getPrimaryKeys(null, LBSCHEMA2, 4test_table)
| 7081a1      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE.UNNAMED             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| 186c8d      | Generic  | LBSCHEMA2.AnotherMixedTable.UNNAMED             | **plan**: getPrimaryKeys(null, LBSCHEMA2, AnotherMixedTable)
| fb861f      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE.UNNAMED | **plan**: getPrimaryKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| c79780      | Generic  | LBSCHEMA2.MixedTable.UNNAMED                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, MixedTable)
| 42df0f      | Generic  | LBSCHEMA2.UPPERTABLE.UNNAMED                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, UPPERTABLE)
| dadc07      | Generic  | LBSCHEMA2.anotherlowertable.UNNAMED             | **plan**: getPrimaryKeys(null, LBSCHEMA2, anotherlowertable)
| 5387f7      | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table.UNNAMED | **plan**: getPrimaryKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| 01ad37      | Generic  | LBSCHEMA2.lowertable.UNNAMED                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, lowertable)
| 513430      | Generic  | LBSCHEMA2.only_in_LBSCHEMA2.UNNAMED             | **plan**: getPrimaryKeys(null, LBSCHEMA2, only_in_LBSCHEMA2)

# Test Version: "49d4b3" #