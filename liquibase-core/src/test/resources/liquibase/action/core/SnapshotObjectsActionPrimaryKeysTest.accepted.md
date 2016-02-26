**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column PKs correctly" #

- **connection:** generic[config:standard]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 0f43a8      | Generic  | LBSCHEMA   | **plan**: getPrimaryKeys(null, LBSCHEMA, TABLE_NAME)
| eb7c69      | Generic  | LBSCHEMA2  | **plan**: getPrimaryKeys(null, LBSCHEMA2, TABLE_NAME)

# Test: "can find all primaryKeys in a fully qualified complex table name" #

- **connection:** generic[config:standard]

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| c9a7d3      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getPrimaryKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 014303      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getPrimaryKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 84ceec      | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: getPrimaryKeys(null, LBSCHEMA, ONLY_IN_LBSCHEMA)
| a214a1      | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: getPrimaryKeys(null, LBSCHEMA, UPPERTABLE)
| 787ea6      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| 35a817      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getPrimaryKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| f5e1de      | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: getPrimaryKeys(null, LBSCHEMA2, ONLY_IN_LBSCHEMA2)
| c90d39      | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: getPrimaryKeys(null, LBSCHEMA2, UPPERTABLE)

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

# Test Version: "65bf15" #