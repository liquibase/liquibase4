**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column FKs correctly" #

- **connection:** generic[config:standard]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 0f43a8      | Generic  | LBSCHEMA   | **plan**: getImportedKeys(null, LBSCHEMA, TABLE_NAME)
| eb7c69      | Generic  | LBSCHEMA2  | **plan**: getImportedKeys(null, LBSCHEMA2, TABLE_NAME)

# Test: "can find all foreignKeys in a fully qualified complex table name" #

- **connection:** generic[config:standard]

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| c9a7d3      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getImportedKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 014303      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getImportedKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 84ceec      | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: getImportedKeys(null, LBSCHEMA, ONLY_IN_LBSCHEMA)
| a214a1      | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: getImportedKeys(null, LBSCHEMA, UPPERTABLE)
| 787ea6      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getImportedKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| 35a817      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getImportedKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| f5e1de      | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: getImportedKeys(null, LBSCHEMA2, ONLY_IN_LBSCHEMA2)
| c90d39      | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: getImportedKeys(null, LBSCHEMA2, UPPERTABLE)

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

# Test Version: "571199" #