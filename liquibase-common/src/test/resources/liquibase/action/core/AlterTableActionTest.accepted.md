**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can alter tables" #

- **connection:** generic standard

| Permutation | Verified | newDefinition | table                                   | OPERATIONS
| :---------- | :------- | :------------ | :-------------------------------------- | :------
| d8fbfce     | Generic  | add id int    | LBSCHEMA.4TEST_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4TEST_table" add id int
| c4f8a7c     | Generic  | add id int    | LBSCHEMA.4test_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4test_table" add id int
| bc93f14     | Generic  | add id int    | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: ALTER TABLE "LBSCHEMA"."ANOTHERUPPERTABLE" add id int
| 8836d06     | Generic  | add id int    | LBSCHEMA.AnotherMixedTable              | **plan**: ALTER TABLE "LBSCHEMA"."AnotherMixedTable" add id int
| e7fcf2b     | Generic  | add id int    | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" add id int
| 612a14d     | Generic  | add id int    | LBSCHEMA.MixedTable                     | **plan**: ALTER TABLE "LBSCHEMA"."MixedTable" add id int
| a8bd228     | Generic  | add id int    | LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: ALTER TABLE "LBSCHEMA"."ONLY_IN_LBSCHEMA" add id int
| 10d986b     | Generic  | add id int    | LBSCHEMA.UPPERTABLE                     | **plan**: ALTER TABLE "LBSCHEMA"."UPPERTABLE" add id int
| 8518fa3     | Generic  | add id int    | LBSCHEMA.anotherlowertable              | **plan**: ALTER TABLE "LBSCHEMA"."anotherlowertable" add id int
| 63c23cd     | Generic  | add id int    | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""table" add id int
| 56fbf43     | Generic  | add id int    | LBSCHEMA.lowertable                     | **plan**: ALTER TABLE "LBSCHEMA"."lowertable" add id int
| 3737417     | Generic  | add id int    | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" add id int
| 1f92584     | Generic  | add id int    | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" add id int
| 4603ae8     | Generic  | add id int    | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" add id int
| 321dfeb     | Generic  | add id int    | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" add id int
| 4dc0bf7     | Generic  | add id int    | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" add id int
| 00fa4c3     | Generic  | add id int    | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" add id int
| ca78c14     | Generic  | add id int    | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" add id int
| bcfa466     | Generic  | add id int    | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" add id int
| c6733d0     | Generic  | add id int    | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" add id int
| a24b530     | Generic  | add id int    | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" add id int
| d9ddcd3     | Generic  | add id int    | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" add id int

# Test Version: "54333e" #