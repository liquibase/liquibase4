**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column FKs correctly" #

- **connection:** generic[config:standard]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 0f43a8      | Generic  | LBSCHEMA   | **plan**: getImportedKeys(null, LBSCHEMA, TABLE_NAME)
| eb7c69      | Generic  | LBSCHEMA2  | **plan**: getImportedKeys(null, LBSCHEMA2, TABLE_NAME)

# Test: "can find all foreignKeys in a fully qualified complex table name" #

- **connection:** generic[config:standard]

| Permutation | Verified | tableName                               | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 6f10b3      | Generic  | LBSCHEMA.4TEST_table                    | **plan**: getImportedKeys(null, LBSCHEMA, 4TEST_table)
| 447604      | Generic  | LBSCHEMA.4test_table                    | **plan**: getImportedKeys(null, LBSCHEMA, 4test_table)
| 7d8923      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getImportedKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 244240      | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: getImportedKeys(null, LBSCHEMA, AnotherMixedTable)
| cf6e70      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getImportedKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 1ab5be      | Generic  | LBSCHEMA.MixedTable                     | **plan**: getImportedKeys(null, LBSCHEMA, MixedTable)
| d62fef      | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: getImportedKeys(null, LBSCHEMA, UPPERTABLE)
| b7bd07      | Generic  | LBSCHEMA.anotherlowertable              | **plan**: getImportedKeys(null, LBSCHEMA, anotherlowertable)
| 8d1056      | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getImportedKeys(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table)
| f03884      | Generic  | LBSCHEMA.lowertable                     | **plan**: getImportedKeys(null, LBSCHEMA, lowertable)
| a69da2      | Generic  | LBSCHEMA.only_in_LBSCHEMA               | **plan**: getImportedKeys(null, LBSCHEMA, only_in_LBSCHEMA)
| a86d64      | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: getImportedKeys(null, LBSCHEMA2, 4TEST_table)
| 590a6e      | Generic  | LBSCHEMA2.4test_table                   | **plan**: getImportedKeys(null, LBSCHEMA2, 4test_table)
| 2927a8      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getImportedKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| ce1ca3      | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: getImportedKeys(null, LBSCHEMA2, AnotherMixedTable)
| 710fa9      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getImportedKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 315ef1      | Generic  | LBSCHEMA2.MixedTable                    | **plan**: getImportedKeys(null, LBSCHEMA2, MixedTable)
| 33d15e      | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: getImportedKeys(null, LBSCHEMA2, UPPERTABLE)
| 22dc5a      | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: getImportedKeys(null, LBSCHEMA2, anotherlowertable)
| fbf4a4      | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getImportedKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| 55094c      | Generic  | LBSCHEMA2.lowertable                    | **plan**: getImportedKeys(null, LBSCHEMA2, lowertable)
| f90500      | Generic  | LBSCHEMA2.only_in_LBSCHEMA2             | **plan**: getImportedKeys(null, LBSCHEMA2, only_in_LBSCHEMA2)

# Test: "can find fully qualified complex foreign key names" #

- **connection:** generic[config:standard]

| Permutation | Verified | fkName                             | schema    | OPERATIONS
| :---------- | :------- | :--------------------------------- | :-------- | :------
| 8ee244      | Generic  | 4TEST_foreignkey                   | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 7f0da0      | Generic  | 4TEST_foreignkey                   | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 7c57e8      | Generic  | 4test_foreignkey                   | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 005609      | Generic  | 4test_foreignkey                   | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| d4f218      | Generic  | ANOTHERUPPERFOREIGNKEY             | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| e56939      | Generic  | ANOTHERUPPERFOREIGNKEY             | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| c96e6c      | Generic  | AnotherMixedForeignKey             | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 242783      | Generic  | AnotherMixedForeignKey             | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 7a2347      | Generic  | CRAZY!@#\$%^&*()_+{}[]'"FOREIGNKEY | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 83b3af      | Generic  | CRAZY!@#\$%^&*()_+{}[]'"FOREIGNKEY | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| dabe84      | Generic  | MixedForeignKey                    | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 378a65      | Generic  | MixedForeignKey                    | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| a8492f      | Generic  | UPPERFOREIGNKEY                    | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 885cde      | Generic  | UPPERFOREIGNKEY                    | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 146115      | Generic  | anotherlowerforeignkey             | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 148ba1      | Generic  | anotherlowerforeignkey             | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 03a0cc      | Generic  | crazy!@#\$%^&*()_+{}[]'"foreignkey | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| e5992a      | Generic  | crazy!@#\$%^&*()_+{}[]'"foreignkey | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 7c6c7d      | Generic  | lowerforeignkey                    | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 5a69f0      | Generic  | lowerforeignkey                    | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| a5357d      | Generic  | only_in_LBSCHEMA                   | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 70ecde      | Generic  | only_in_LBSCHEMA                   | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 305ba2      | Generic  | only_in_LBSCHEMA2                  | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| f65bca      | Generic  | only_in_LBSCHEMA2                  | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)

# Test Version: "96ee62" #