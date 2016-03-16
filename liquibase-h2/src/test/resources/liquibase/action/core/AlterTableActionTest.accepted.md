**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can alter tables" #

- **connection:** h2 standard

| Permutation | Verified | newDefinition | table                                   | OPERATIONS
| :---------- | :------- | :------------ | :-------------------------------------- | :------
| e95ae5      | true     | add id int    | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" add id int
| cbc44e      | true     | add id int    | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" add id int
| 86b0be      | true     | add id int    | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" add id int
| b86c5f      | true     | add id int    | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" add id int
| ce5aa0      | true     | add id int    | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" add id int
| 434ffd      | true     | add id int    | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" add id int
| 37fee4      | true     | add id int    | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" add id int
| 605e4b      | true     | add id int    | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" add id int
| 8ab978      | true     | add id int    | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" add id int
| baca9c      | true     | add id int    | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" add id int
| 65786b      | true     | add id int    | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" add id int
| d9051d      | true     | add id int    | PUBLIC.4TEST_table                      | **plan**: ALTER TABLE "PUBLIC"."4TEST_table" add id int
| 5a9796      | true     | add id int    | PUBLIC.4test_table                      | **plan**: ALTER TABLE "PUBLIC"."4test_table" add id int
| bd503b      | true     | add id int    | PUBLIC.ANOTHERUPPERTABLE                | **plan**: ALTER TABLE "PUBLIC"."ANOTHERUPPERTABLE" add id int
| a635df      | true     | add id int    | PUBLIC.AnotherMixedTable                | **plan**: ALTER TABLE "PUBLIC"."AnotherMixedTable" add id int
| 2fbac1      | true     | add id int    | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE    | **plan**: ALTER TABLE "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" add id int
| 632359      | true     | add id int    | PUBLIC.MixedTable                       | **plan**: ALTER TABLE "PUBLIC"."MixedTable" add id int
| e6c5d7      | true     | add id int    | PUBLIC.ONLY_IN_PUBLIC                   | **plan**: ALTER TABLE "PUBLIC"."ONLY_IN_PUBLIC" add id int
| d6464b      | true     | add id int    | PUBLIC.UPPERTABLE                       | **plan**: ALTER TABLE "PUBLIC"."UPPERTABLE" add id int
| b33c41      | true     | add id int    | PUBLIC.anotherlowertable                | **plan**: ALTER TABLE "PUBLIC"."anotherlowertable" add id int
| bd0efc      | true     | add id int    | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table    | **plan**: ALTER TABLE "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""table" add id int
| ee4f32      | true     | add id int    | PUBLIC.lowertable                       | **plan**: ALTER TABLE "PUBLIC"."lowertable" add id int

# Test Version: "16ec2a" #