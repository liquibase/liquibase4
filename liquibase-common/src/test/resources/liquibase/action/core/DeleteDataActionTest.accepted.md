**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can delete from createAllActionPermutations" #

- **connection:** generic standard

| Permutation | Verified | relation                                | where | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :---- | :------
| 231db6f     | Generic  | LBSCHEMA.4TEST_table                    |       | **plan**: DELETE FROM "LBSCHEMA"."4TEST_table"
| b57289f     | Generic  | LBSCHEMA.4TEST_table                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."4TEST_table" WHERE 1=1
| 6de22ec     | Generic  | LBSCHEMA.4test_table                    |       | **plan**: DELETE FROM "LBSCHEMA"."4test_table"
| 5407eef     | Generic  | LBSCHEMA.4test_table                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."4test_table" WHERE 1=1
| 578994a     | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              |       | **plan**: DELETE FROM "LBSCHEMA"."ANOTHERUPPERTABLE"
| fc6f02c     | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."ANOTHERUPPERTABLE" WHERE 1=1
| 67b044c     | Generic  | LBSCHEMA.AnotherMixedTable              |       | **plan**: DELETE FROM "LBSCHEMA"."AnotherMixedTable"
| f13c92d     | Generic  | LBSCHEMA.AnotherMixedTable              | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."AnotherMixedTable" WHERE 1=1
| d2628bb     | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  |       | **plan**: DELETE FROM "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""TABLE"
| 2c5448a     | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" WHERE 1=1
| 62bb2ec     | Generic  | LBSCHEMA.MixedTable                     |       | **plan**: DELETE FROM "LBSCHEMA"."MixedTable"
| 022154e     | Generic  | LBSCHEMA.MixedTable                     | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."MixedTable" WHERE 1=1
| 30d04e5     | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               |       | **plan**: DELETE FROM "LBSCHEMA"."ONLY_IN_LBSCHEMA"
| fb0f397     | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."ONLY_IN_LBSCHEMA" WHERE 1=1
| 2db0806     | Generic  | LBSCHEMA.UPPERTABLE                     |       | **plan**: DELETE FROM "LBSCHEMA"."UPPERTABLE"
| 46d49dc     | Generic  | LBSCHEMA.UPPERTABLE                     | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."UPPERTABLE" WHERE 1=1
| 57951bf     | Generic  | LBSCHEMA.anotherlowertable              |       | **plan**: DELETE FROM "LBSCHEMA"."anotherlowertable"
| 4d56db6     | Generic  | LBSCHEMA.anotherlowertable              | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."anotherlowertable" WHERE 1=1
| ae668b9     | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  |       | **plan**: DELETE FROM "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""table"
| dc2caae     | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""table" WHERE 1=1
| 77827a7     | Generic  | LBSCHEMA.lowertable                     |       | **plan**: DELETE FROM "LBSCHEMA"."lowertable"
| 25f5c1a     | Generic  | LBSCHEMA.lowertable                     | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."lowertable" WHERE 1=1
| 9477ea1     | Generic  | LBSCHEMA2.4TEST_table                   |       | **plan**: DELETE FROM "LBSCHEMA2"."4TEST_table"
| dbd8a6c     | Generic  | LBSCHEMA2.4TEST_table                   | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."4TEST_table" WHERE 1=1
| cdd5562     | Generic  | LBSCHEMA2.4test_table                   |       | **plan**: DELETE FROM "LBSCHEMA2"."4test_table"
| 5cf8cd7     | Generic  | LBSCHEMA2.4test_table                   | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."4test_table" WHERE 1=1
| 2a1936c     | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             |       | **plan**: DELETE FROM "LBSCHEMA2"."ANOTHERUPPERTABLE"
| 675341b     | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."ANOTHERUPPERTABLE" WHERE 1=1
| bfe4823     | Generic  | LBSCHEMA2.AnotherMixedTable             |       | **plan**: DELETE FROM "LBSCHEMA2"."AnotherMixedTable"
| 44f5075     | Generic  | LBSCHEMA2.AnotherMixedTable             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."AnotherMixedTable" WHERE 1=1
| 01755a7     | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE |       | **plan**: DELETE FROM "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE"
| 5fdc74f     | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" WHERE 1=1
| 4aa305f     | Generic  | LBSCHEMA2.MixedTable                    |       | **plan**: DELETE FROM "LBSCHEMA2"."MixedTable"
| 833f7f1     | Generic  | LBSCHEMA2.MixedTable                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."MixedTable" WHERE 1=1
| d1048a6     | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             |       | **plan**: DELETE FROM "LBSCHEMA2"."ONLY_IN_LBSCHEMA2"
| 81b98f9     | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" WHERE 1=1
| 226b6c5     | Generic  | LBSCHEMA2.UPPERTABLE                    |       | **plan**: DELETE FROM "LBSCHEMA2"."UPPERTABLE"
| c9d41dc     | Generic  | LBSCHEMA2.UPPERTABLE                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."UPPERTABLE" WHERE 1=1
| e90e49c     | Generic  | LBSCHEMA2.anotherlowertable             |       | **plan**: DELETE FROM "LBSCHEMA2"."anotherlowertable"
| a38ead0     | Generic  | LBSCHEMA2.anotherlowertable             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."anotherlowertable" WHERE 1=1
| 37e044c     | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table |       | **plan**: DELETE FROM "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table"
| 5c4efd9     | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" WHERE 1=1
| 58567ee     | Generic  | LBSCHEMA2.lowertable                    |       | **plan**: DELETE FROM "LBSCHEMA2"."lowertable"
| edcea15     | Generic  | LBSCHEMA2.lowertable                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."lowertable" WHERE 1=1

# Test Version: "684615" #