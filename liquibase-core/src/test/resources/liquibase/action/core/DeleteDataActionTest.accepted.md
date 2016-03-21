**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can delete from createAllActionPermutations" #

- **connection:** generic standard

| Permutation | Verified | relation                                | where | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :---- | :------
| 67843a1     | Generic  | LBSCHEMA.4TEST_table                    |       | **plan**: DELETE FROM "LBSCHEMA"."4TEST_table"
| b57289f     | Generic  | LBSCHEMA.4TEST_table                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."4TEST_table" WHERE 1=1
| ab5155a     | Generic  | LBSCHEMA.4test_table                    |       | **plan**: DELETE FROM "LBSCHEMA"."4test_table"
| 5407eef     | Generic  | LBSCHEMA.4test_table                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."4test_table" WHERE 1=1
| 0101732     | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              |       | **plan**: DELETE FROM "LBSCHEMA"."ANOTHERUPPERTABLE"
| fc6f02c     | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."ANOTHERUPPERTABLE" WHERE 1=1
| 2f7f438     | Generic  | LBSCHEMA.AnotherMixedTable              |       | **plan**: DELETE FROM "LBSCHEMA"."AnotherMixedTable"
| f13c92d     | Generic  | LBSCHEMA.AnotherMixedTable              | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."AnotherMixedTable" WHERE 1=1
| e2eca17     | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  |       | **plan**: DELETE FROM "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""TABLE"
| 2c5448a     | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" WHERE 1=1
| 42a96a8     | Generic  | LBSCHEMA.MixedTable                     |       | **plan**: DELETE FROM "LBSCHEMA"."MixedTable"
| 022154e     | Generic  | LBSCHEMA.MixedTable                     | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."MixedTable" WHERE 1=1
| 947ef0b     | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               |       | **plan**: DELETE FROM "LBSCHEMA"."ONLY_IN_LBSCHEMA"
| fb0f397     | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."ONLY_IN_LBSCHEMA" WHERE 1=1
| 8f120e2     | Generic  | LBSCHEMA.UPPERTABLE                     |       | **plan**: DELETE FROM "LBSCHEMA"."UPPERTABLE"
| 46d49dc     | Generic  | LBSCHEMA.UPPERTABLE                     | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."UPPERTABLE" WHERE 1=1
| 55feedd     | Generic  | LBSCHEMA.anotherlowertable              |       | **plan**: DELETE FROM "LBSCHEMA"."anotherlowertable"
| 4d56db6     | Generic  | LBSCHEMA.anotherlowertable              | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."anotherlowertable" WHERE 1=1
| da73b41     | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  |       | **plan**: DELETE FROM "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""table"
| dc2caae     | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""table" WHERE 1=1
| 8c25b5b     | Generic  | LBSCHEMA.lowertable                     |       | **plan**: DELETE FROM "LBSCHEMA"."lowertable"
| 25f5c1a     | Generic  | LBSCHEMA.lowertable                     | 1=1   | **plan**: DELETE FROM "LBSCHEMA"."lowertable" WHERE 1=1
| adc7fc5     | Generic  | LBSCHEMA2.4TEST_table                   |       | **plan**: DELETE FROM "LBSCHEMA2"."4TEST_table"
| dbd8a6c     | Generic  | LBSCHEMA2.4TEST_table                   | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."4TEST_table" WHERE 1=1
| f710b6c     | Generic  | LBSCHEMA2.4test_table                   |       | **plan**: DELETE FROM "LBSCHEMA2"."4test_table"
| 5cf8cd7     | Generic  | LBSCHEMA2.4test_table                   | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."4test_table" WHERE 1=1
| 10704f1     | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             |       | **plan**: DELETE FROM "LBSCHEMA2"."ANOTHERUPPERTABLE"
| 675341b     | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."ANOTHERUPPERTABLE" WHERE 1=1
| 3cb4f58     | Generic  | LBSCHEMA2.AnotherMixedTable             |       | **plan**: DELETE FROM "LBSCHEMA2"."AnotherMixedTable"
| 44f5075     | Generic  | LBSCHEMA2.AnotherMixedTable             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."AnotherMixedTable" WHERE 1=1
| 790c37d     | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE |       | **plan**: DELETE FROM "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE"
| 5fdc74f     | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" WHERE 1=1
| f890029     | Generic  | LBSCHEMA2.MixedTable                    |       | **plan**: DELETE FROM "LBSCHEMA2"."MixedTable"
| 833f7f1     | Generic  | LBSCHEMA2.MixedTable                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."MixedTable" WHERE 1=1
| 550cb2a     | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             |       | **plan**: DELETE FROM "LBSCHEMA2"."ONLY_IN_LBSCHEMA2"
| 81b98f9     | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" WHERE 1=1
| 4beab16     | Generic  | LBSCHEMA2.UPPERTABLE                    |       | **plan**: DELETE FROM "LBSCHEMA2"."UPPERTABLE"
| c9d41dc     | Generic  | LBSCHEMA2.UPPERTABLE                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."UPPERTABLE" WHERE 1=1
| 8741ad3     | Generic  | LBSCHEMA2.anotherlowertable             |       | **plan**: DELETE FROM "LBSCHEMA2"."anotherlowertable"
| a38ead0     | Generic  | LBSCHEMA2.anotherlowertable             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."anotherlowertable" WHERE 1=1
| cfbc1b8     | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table |       | **plan**: DELETE FROM "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table"
| 5c4efd9     | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" WHERE 1=1
| bee74e3     | Generic  | LBSCHEMA2.lowertable                    |       | **plan**: DELETE FROM "LBSCHEMA2"."lowertable"
| edcea15     | Generic  | LBSCHEMA2.lowertable                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."lowertable" WHERE 1=1

# Test Version: "8da7df" #