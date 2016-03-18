**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can delete from createAllActionPermutations" #

- **connection:** h2 standard

| Permutation | Verified | relation                                | where | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :---- | :------
| 6b10019     | true     | LBSCHEMA2.4TEST_table                   |       | **plan**: DELETE FROM "LBSCHEMA2"."4TEST_table"
| 8a42aa5     | true     | LBSCHEMA2.4TEST_table                   | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."4TEST_table" WHERE 1=1
| 0fdca27     | true     | LBSCHEMA2.4test_table                   |       | **plan**: DELETE FROM "LBSCHEMA2"."4test_table"
| cd95017     | true     | LBSCHEMA2.4test_table                   | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."4test_table" WHERE 1=1
| fc20c37     | true     | LBSCHEMA2.ANOTHERUPPERTABLE             |       | **plan**: DELETE FROM "LBSCHEMA2"."ANOTHERUPPERTABLE"
| c2c015e     | true     | LBSCHEMA2.ANOTHERUPPERTABLE             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."ANOTHERUPPERTABLE" WHERE 1=1
| b30fc62     | true     | LBSCHEMA2.AnotherMixedTable             |       | **plan**: DELETE FROM "LBSCHEMA2"."AnotherMixedTable"
| 6311e2e     | true     | LBSCHEMA2.AnotherMixedTable             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."AnotherMixedTable" WHERE 1=1
| 391762b     | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE |       | **plan**: DELETE FROM "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE"
| d247050     | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" WHERE 1=1
| 34103d0     | true     | LBSCHEMA2.MixedTable                    |       | **plan**: DELETE FROM "LBSCHEMA2"."MixedTable"
| 1d8d606     | true     | LBSCHEMA2.MixedTable                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."MixedTable" WHERE 1=1
| 92b82e5     | true     | LBSCHEMA2.ONLY_IN_LBSCHEMA2             |       | **plan**: DELETE FROM "LBSCHEMA2"."ONLY_IN_LBSCHEMA2"
| 977dd2f     | true     | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" WHERE 1=1
| 63f45fa     | true     | LBSCHEMA2.UPPERTABLE                    |       | **plan**: DELETE FROM "LBSCHEMA2"."UPPERTABLE"
| 909c885     | true     | LBSCHEMA2.UPPERTABLE                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."UPPERTABLE" WHERE 1=1
| bf749b9     | true     | LBSCHEMA2.anotherlowertable             |       | **plan**: DELETE FROM "LBSCHEMA2"."anotherlowertable"
| 7c0d485     | true     | LBSCHEMA2.anotherlowertable             | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."anotherlowertable" WHERE 1=1
| 03af806     | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table |       | **plan**: DELETE FROM "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table"
| 79c1fcd     | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" WHERE 1=1
| d67a6e8     | true     | LBSCHEMA2.lowertable                    |       | **plan**: DELETE FROM "LBSCHEMA2"."lowertable"
| 3c14979     | true     | LBSCHEMA2.lowertable                    | 1=1   | **plan**: DELETE FROM "LBSCHEMA2"."lowertable" WHERE 1=1
| ff1042a     | true     | PUBLIC.4TEST_table                      |       | **plan**: DELETE FROM "PUBLIC"."4TEST_table"
| a6963b1     | true     | PUBLIC.4TEST_table                      | 1=1   | **plan**: DELETE FROM "PUBLIC"."4TEST_table" WHERE 1=1
| ac10423     | true     | PUBLIC.4test_table                      |       | **plan**: DELETE FROM "PUBLIC"."4test_table"
| b275799     | true     | PUBLIC.4test_table                      | 1=1   | **plan**: DELETE FROM "PUBLIC"."4test_table" WHERE 1=1
| a200103     | true     | PUBLIC.ANOTHERUPPERTABLE                |       | **plan**: DELETE FROM "PUBLIC"."ANOTHERUPPERTABLE"
| a6abc8e     | true     | PUBLIC.ANOTHERUPPERTABLE                | 1=1   | **plan**: DELETE FROM "PUBLIC"."ANOTHERUPPERTABLE" WHERE 1=1
| 5824f9b     | true     | PUBLIC.AnotherMixedTable                |       | **plan**: DELETE FROM "PUBLIC"."AnotherMixedTable"
| 129e6e1     | true     | PUBLIC.AnotherMixedTable                | 1=1   | **plan**: DELETE FROM "PUBLIC"."AnotherMixedTable" WHERE 1=1
| 7390f91     | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE    |       | **plan**: DELETE FROM "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""TABLE"
| 0f0980d     | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE    | 1=1   | **plan**: DELETE FROM "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" WHERE 1=1
| 92240ac     | true     | PUBLIC.MixedTable                       |       | **plan**: DELETE FROM "PUBLIC"."MixedTable"
| d2f5962     | true     | PUBLIC.MixedTable                       | 1=1   | **plan**: DELETE FROM "PUBLIC"."MixedTable" WHERE 1=1
| 655ec41     | true     | PUBLIC.ONLY_IN_PUBLIC                   |       | **plan**: DELETE FROM "PUBLIC"."ONLY_IN_PUBLIC"
| 897cb19     | true     | PUBLIC.ONLY_IN_PUBLIC                   | 1=1   | **plan**: DELETE FROM "PUBLIC"."ONLY_IN_PUBLIC" WHERE 1=1
| 0d3664f     | true     | PUBLIC.UPPERTABLE                       |       | **plan**: DELETE FROM "PUBLIC"."UPPERTABLE"
| a7dc894     | true     | PUBLIC.UPPERTABLE                       | 1=1   | **plan**: DELETE FROM "PUBLIC"."UPPERTABLE" WHERE 1=1
| ba7f7da     | true     | PUBLIC.anotherlowertable                |       | **plan**: DELETE FROM "PUBLIC"."anotherlowertable"
| 9924fc5     | true     | PUBLIC.anotherlowertable                | 1=1   | **plan**: DELETE FROM "PUBLIC"."anotherlowertable" WHERE 1=1
| 8086b0d     | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table    |       | **plan**: DELETE FROM "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""table"
| 3824b21     | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table    | 1=1   | **plan**: DELETE FROM "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""table" WHERE 1=1
| 515b9a0     | true     | PUBLIC.lowertable                       |       | **plan**: DELETE FROM "PUBLIC"."lowertable"
| 3dfce93     | true     | PUBLIC.lowertable                       | 1=1   | **plan**: DELETE FROM "PUBLIC"."lowertable" WHERE 1=1

# Test Version: "864499" #