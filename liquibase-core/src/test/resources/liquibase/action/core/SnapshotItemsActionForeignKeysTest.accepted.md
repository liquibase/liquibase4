**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column FKs correctly" #

- **connection:** generic standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 6e63508     | Generic  | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TABLE_NAME)
| bbb8e61     | Generic  | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TABLE_NAME)

# Test: "can find all foreignKeys in a fully qualified complex table name" #

- **connection:** generic standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 82ca36a     | Generic  | LBSCHEMA.4TEST_table                    | **plan**: getImportedKeys(null, LBSCHEMA, 4TEST_table)
| a59cadd     | Generic  | LBSCHEMA.4test_table                    | **plan**: getImportedKeys(null, LBSCHEMA, 4test_table)
| 02109bf     | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: getImportedKeys(null, LBSCHEMA, ANOTHERUPPERTABLE)
| 0556eae     | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: getImportedKeys(null, LBSCHEMA, AnotherMixedTable)
| 2cbd26a     | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: getImportedKeys(null, LBSCHEMA, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| 6b38df5     | Generic  | LBSCHEMA.MixedTable                     | **plan**: getImportedKeys(null, LBSCHEMA, MixedTable)
| 7636549     | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: getImportedKeys(null, LBSCHEMA, ONLY_IN_LBSCHEMA)
| 8f18146     | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: getImportedKeys(null, LBSCHEMA, UPPERTABLE)
| 5c86256     | Generic  | LBSCHEMA.anotherlowertable              | **plan**: getImportedKeys(null, LBSCHEMA, anotherlowertable)
| 86a5d18     | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: getImportedKeys(null, LBSCHEMA, crazy!@#\$%^&*()_+{}[]'"table)
| c4dbeb3     | Generic  | LBSCHEMA.lowertable                     | **plan**: getImportedKeys(null, LBSCHEMA, lowertable)
| 31f94ac     | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: getImportedKeys(null, LBSCHEMA2, 4TEST_table)
| 13e76ef     | Generic  | LBSCHEMA2.4test_table                   | **plan**: getImportedKeys(null, LBSCHEMA2, 4test_table)
| cec2e1c     | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: getImportedKeys(null, LBSCHEMA2, ANOTHERUPPERTABLE)
| fe461f7     | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: getImportedKeys(null, LBSCHEMA2, AnotherMixedTable)
| dec400d     | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: getImportedKeys(null, LBSCHEMA2, CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| cf72a30     | Generic  | LBSCHEMA2.MixedTable                    | **plan**: getImportedKeys(null, LBSCHEMA2, MixedTable)
| 6e579ae     | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: getImportedKeys(null, LBSCHEMA2, ONLY_IN_LBSCHEMA2)
| d5eec30     | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: getImportedKeys(null, LBSCHEMA2, UPPERTABLE)
| 00b229a     | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: getImportedKeys(null, LBSCHEMA2, anotherlowertable)
| b875ac8     | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: getImportedKeys(null, LBSCHEMA2, crazy!@#\$%^&*()_+{}[]'"table)
| 5921e4d     | Generic  | LBSCHEMA2.lowertable                    | **plan**: getImportedKeys(null, LBSCHEMA2, lowertable)

# Test: "can find fully qualified complex foreign key names" #

- **connection:** generic standard

| Permutation | Verified | fk                                 | schema    | OPERATIONS
| :---------- | :------- | :--------------------------------- | :-------- | :------
| f6bfa35     | Generic  | 4TEST_foreignkey                   | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| dbfd00b     | Generic  | 4TEST_foreignkey                   | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 10c057a     | Generic  | 4test_foreignkey                   | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 740ea57     | Generic  | 4test_foreignkey                   | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 5f5585f     | Generic  | ANOTHERUPPERFOREIGNKEY             | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 4949cf3     | Generic  | ANOTHERUPPERFOREIGNKEY             | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| b1f4c52     | Generic  | AnotherMixedForeignKey             | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 3868859     | Generic  | AnotherMixedForeignKey             | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 9f375e8     | Generic  | CRAZY!@#\$%^&*()_+{}[]'"FOREIGNKEY | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| beb0f53     | Generic  | CRAZY!@#\$%^&*()_+{}[]'"FOREIGNKEY | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 381ea47     | Generic  | MixedForeignKey                    | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 0909f42     | Generic  | MixedForeignKey                    | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 268667a     | Generic  | UPPERFOREIGNKEY                    | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 229b19b     | Generic  | UPPERFOREIGNKEY                    | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| b603e83     | Generic  | anotherlowerforeignkey             | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| e5fa2c3     | Generic  | anotherlowerforeignkey             | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| a798358     | Generic  | crazy!@#\$%^&*()_+{}[]'"foreignkey | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 2b0a9b2     | Generic  | crazy!@#\$%^&*()_+{}[]'"foreignkey | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)
| 7401ea6     | Generic  | lowerforeignkey                    | LBSCHEMA  | **plan**: getImportedKeys(null, LBSCHEMA, TEST_TABLE)
| 2199448     | Generic  | lowerforeignkey                    | LBSCHEMA2 | **plan**: getImportedKeys(null, LBSCHEMA2, TEST_TABLE)

# Test Version: "a5dd2d" #