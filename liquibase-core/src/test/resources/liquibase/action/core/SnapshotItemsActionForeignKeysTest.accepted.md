**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column FKs correctly" #

- **connection:** generic standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 6e6350      | Generic  | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TABLE_NAME)
| bbb8e6      | Generic  | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TABLE_NAME)

# Test: "can find all foreignKeys in a fully qualified complex table name" #

- **connection:** generic standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 82ca36      | Generic  | LBSCHEMA.4TEST_table                    | **plan**: getImportedKeys(null, LBSCHEMA, 4TEST_table)
| a59cad      | Generic  | LBSCHEMA.4test_table                    | **plan**: getImportedKeys(null, LBSCHEMA, 4test_table)
| 02109b      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getImportedKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 0556ea      | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: getImportedKeys(null, LBSCHEMA, AnotherMixedTable)
| 2cbd26      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getImportedKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 6b38df      | Generic  | LBSCHEMA.MixedTable                     | **plan**: getImportedKeys(null, LBSCHEMA, MixedTable)
| 763654      | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: getImportedKeys(null, LBSCHEMA, ONLY_IN_LBSCHEMA)
| 8f1814      | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: getImportedKeys(null, LBSCHEMA, UPPERTABLE)
| 5c8625      | Generic  | LBSCHEMA.anotherlowertable              | **plan**: getImportedKeys(null, LBSCHEMA, anotherlowertable)
| 86a5d1      | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getImportedKeys(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table)
| c4dbeb      | Generic  | LBSCHEMA.lowertable                     | **plan**: getImportedKeys(null, LBSCHEMA, lowertable)
| 31f94a      | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: getImportedKeys(null, LBSCHEMA2, 4TEST_table)
| 13e76e      | Generic  | LBSCHEMA2.4test_table                   | **plan**: getImportedKeys(null, LBSCHEMA2, 4test_table)
| cec2e1      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getImportedKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| fe461f      | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: getImportedKeys(null, LBSCHEMA2, AnotherMixedTable)
| dec400      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getImportedKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| cf72a3      | Generic  | LBSCHEMA2.MixedTable                    | **plan**: getImportedKeys(null, LBSCHEMA2, MixedTable)
| 6e579a      | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: getImportedKeys(null, LBSCHEMA2, ONLY_IN_LBSCHEMA2)
| d5eec3      | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: getImportedKeys(null, LBSCHEMA2, UPPERTABLE)
| 00b229      | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: getImportedKeys(null, LBSCHEMA2, anotherlowertable)
| b875ac      | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getImportedKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| 5921e4      | Generic  | LBSCHEMA2.lowertable                    | **plan**: getImportedKeys(null, LBSCHEMA2, lowertable)

# Test: "can find fully qualified complex foreign key names" #

- **connection:** generic standard

| Permutation | Verified | fk                                 | schema    | OPERATIONS
| :---------- | :------- | :--------------------------------- | :-------- | :------
| f6bfa3      | Generic  | 4TEST_foreignkey                   | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| dbfd00      | Generic  | 4TEST_foreignkey                   | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 10c057      | Generic  | 4test_foreignkey                   | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 740ea5      | Generic  | 4test_foreignkey                   | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 5f5585      | Generic  | ANOTHERUPPERFOREIGNKEY             | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 4949cf      | Generic  | ANOTHERUPPERFOREIGNKEY             | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| b1f4c5      | Generic  | AnotherMixedForeignKey             | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 386885      | Generic  | AnotherMixedForeignKey             | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 9f375e      | Generic  | CRAZY!@#\$%^&*()_+{}[]'"FOREIGNKEY | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| beb0f5      | Generic  | CRAZY!@#\$%^&*()_+{}[]'"FOREIGNKEY | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 381ea4      | Generic  | MixedForeignKey                    | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 0909f4      | Generic  | MixedForeignKey                    | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 268667      | Generic  | UPPERFOREIGNKEY                    | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 229b19      | Generic  | UPPERFOREIGNKEY                    | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| b603e8      | Generic  | anotherlowerforeignkey             | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| e5fa2c      | Generic  | anotherlowerforeignkey             | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| a79835      | Generic  | crazy!@#\$%^&*()_+{}[]'"foreignkey | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 2b0a9b      | Generic  | crazy!@#\$%^&*()_+{}[]'"foreignkey | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 7401ea      | Generic  | lowerforeignkey                    | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 219944      | Generic  | lowerforeignkey                    | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)

# Test Version: "5e96cc" #