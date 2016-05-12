**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can delete from createAllActionPermutations" #

- **connection:** h2 standard

| Permutation | Verified | relation                                | where | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :---- | :------
| 0431dc3     | true     | LBSCHEMA2.4TEST_table                   |       | **plan**: DELETE FROM "LBSCHEMA2"."4TEST_table"
| 8a42aa5     | true     | LBSCHEMA2.4TEST_table                   | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."4TEST_table" WHERE 1=1
| 7e4b92a     | true     | LBSCHEMA2.4test_table                   |       | **plan**: DELETE FROM "LBSCHEMA2"."4test_table"
| cd95017     | true     | LBSCHEMA2.4test_table                   | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."4test_table" WHERE 1=1
| 6a375e2     | true     | LBSCHEMA2.ANOTHERUPPERTABLE             |       | **plan**: DELETE FROM "LBSCHEMA2"."ANOTHERUPPERTABLE"
| c2c015e     | true     | LBSCHEMA2.ANOTHERUPPERTABLE             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."ANOTHERUPPERTABLE" WHERE 1=1
| 18558a6     | true     | LBSCHEMA2.AnotherMixedTable             |       | **plan**: DELETE FROM "LBSCHEMA2"."AnotherMixedTable"
| 6311e2e     | true     | LBSCHEMA2.AnotherMixedTable             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."AnotherMixedTable" WHERE 1=1
| 32c5cb3     | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE |       | **plan**: DELETE FROM "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE"
| d247050     | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" WHERE 1=1
| 007d329     | true     | LBSCHEMA2.MixedTable                    |       | **plan**: DELETE FROM "LBSCHEMA2"."MixedTable"
| 1d8d606     | true     | LBSCHEMA2.MixedTable                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."MixedTable" WHERE 1=1
| a073e16     | true     | LBSCHEMA2.ONLY_IN_LBSCHEMA2             |       | **plan**: DELETE FROM "LBSCHEMA2"."ONLY_IN_LBSCHEMA2"
| 977dd2f     | true     | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" WHERE 1=1
| a55e5ee     | true     | LBSCHEMA2.UPPERTABLE                    |       | **plan**: DELETE FROM "LBSCHEMA2"."UPPERTABLE"
| 909c885     | true     | LBSCHEMA2.UPPERTABLE                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."UPPERTABLE" WHERE 1=1
| 4904e98     | true     | LBSCHEMA2.anotherlowertable             |       | **plan**: DELETE FROM "LBSCHEMA2"."anotherlowertable"
| 7c0d485     | true     | LBSCHEMA2.anotherlowertable             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."anotherlowertable" WHERE 1=1
| 1575f6f     | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table |       | **plan**: DELETE FROM "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table"
| 79c1fcd     | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" WHERE 1=1
| 515df4a     | true     | LBSCHEMA2.lowertable                    |       | **plan**: DELETE FROM "LBSCHEMA2"."lowertable"
| 3c14979     | true     | LBSCHEMA2.lowertable                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."lowertable" WHERE 1=1
| 956be3d     | true     | PUBLIC.4TEST_table                      |       | **plan**: DELETE FROM "PUBLIC"."4TEST_table"
| a6963b1     | true     | PUBLIC.4TEST_table                      | 1=1   | **plan**: DELETE FROM "PUBLIC"."4TEST_table" WHERE 1=1
| d977ceb     | true     | PUBLIC.4test_table                      |       | **plan**: DELETE FROM "PUBLIC"."4test_table"
| b275799     | true     | PUBLIC.4test_table                      | 1=1   | **plan**: DELETE FROM "PUBLIC"."4test_table" WHERE 1=1
| 6efb108     | true     | PUBLIC.ANOTHERUPPERTABLE                |       | **plan**: DELETE FROM "PUBLIC"."ANOTHERUPPERTABLE"
| a6abc8e     | true     | PUBLIC.ANOTHERUPPERTABLE                | 1=1   | **plan**: DELETE FROM "PUBLIC"."ANOTHERUPPERTABLE" WHERE 1=1
| 5ac762a     | true     | PUBLIC.AnotherMixedTable                |       | **plan**: DELETE FROM "PUBLIC"."AnotherMixedTable"
| 129e6e1     | true     | PUBLIC.AnotherMixedTable                | 1=1   | **plan**: DELETE FROM "PUBLIC"."AnotherMixedTable" WHERE 1=1
| fdeefe4     | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE    |       | **plan**: DELETE FROM "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""TABLE"
| 0f0980d     | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE    | 1=1   | **plan**: DELETE FROM "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" WHERE 1=1
| b325a75     | true     | PUBLIC.MixedTable                       |       | **plan**: DELETE FROM "PUBLIC"."MixedTable"
| d2f5962     | true     | PUBLIC.MixedTable                       | 1=1   | **plan**: DELETE FROM "PUBLIC"."MixedTable" WHERE 1=1
| ad28a94     | true     | PUBLIC.ONLY_IN_PUBLIC                   |       | **plan**: DELETE FROM "PUBLIC"."ONLY_IN_PUBLIC"
| 897cb19     | true     | PUBLIC.ONLY_IN_PUBLIC                   | 1=1   | **plan**: DELETE FROM "PUBLIC"."ONLY_IN_PUBLIC" WHERE 1=1
| decd59b     | true     | PUBLIC.UPPERTABLE                       |       | **plan**: DELETE FROM "PUBLIC"."UPPERTABLE"
| a7dc894     | true     | PUBLIC.UPPERTABLE                       | 1=1   | **plan**: DELETE FROM "PUBLIC"."UPPERTABLE" WHERE 1=1
| 7113b3e     | true     | PUBLIC.anotherlowertable                |       | **plan**: DELETE FROM "PUBLIC"."anotherlowertable"
| 9924fc5     | true     | PUBLIC.anotherlowertable                | 1=1   | **plan**: DELETE FROM "PUBLIC"."anotherlowertable" WHERE 1=1
| f59a6bd     | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table    |       | **plan**: DELETE FROM "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""table"
| 3824b21     | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table    | 1=1   | **plan**: DELETE FROM "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""table" WHERE 1=1
| 0e08927     | true     | PUBLIC.lowertable                       |       | **plan**: DELETE FROM "PUBLIC"."lowertable"
| 3dfce93     | true     | PUBLIC.lowertable                       | 1=1   | **plan**: DELETE FROM "PUBLIC"."lowertable" WHERE 1=1

# Test Version: "bb6047" #