**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple constraints at once" #

- **connection:** h2[config:standard]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 679812      | true     | LBSCHEMA2  | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE_1" ADD UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_2" ADD UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_3" ADD UNIQUE ("COL_NAME")
| bd13a9      | true     | PUBLIC     | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE_1" ADD UNIQUE ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_2" ADD UNIQUE ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_3" ADD UNIQUE ("COL_NAME")

# Test: "Can apply multi-column with standard settings" #

- **connection:** h2[config:standard]

| Permutation | Verified | columnName   | tableName            | OPERATIONS
| :---------- | :------- | :----------- | :------------------- | :------
| 1a6f91      | true     | COL_1, COL_2 | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COL_1", "COL_2")
| b316ef      | true     | COL_1, COL_2 | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COL_1", "COL_2")

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** h2[config:standard]

| Permutation | Verified | columnName                     | tableName            | OPERATIONS
| :---------- | :------- | :----------------------------- | :------------------- | :------
| dd026f      | true     | 4TEST_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4TEST_column")
| d4894d      | true     | 4TEST_column                   | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4TEST_column")
| 0fd8c0      | true     | 4test_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4test_column")
| d80f94      | true     | 4test_column                   | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4test_column")
| 2a11a1      | true     | ANOTHERUPPERCOLUMN             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("ANOTHERUPPERCOLUMN")
| c14422      | true     | ANOTHERUPPERCOLUMN             | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("ANOTHERUPPERCOLUMN")
| 23d958      | true     | AnotherMixedColumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("AnotherMixedColumn")
| 735e3c      | true     | AnotherMixedColumn             | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("AnotherMixedColumn")
| c39dbb      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("CRAZY!@#\$%^&*()_+{}[]'""COLUMN")
| aa2586      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("CRAZY!@#\$%^&*()_+{}[]'""COLUMN")
| 33e1e8      | true     | MixedColumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("MixedColumn")
| a4085a      | true     | MixedColumn                    | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("MixedColumn")
| de6c76      | true     | UPPERCOLUMN                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("UPPERCOLUMN")
| 8039cc      | true     | UPPERCOLUMN                    | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("UPPERCOLUMN")
| 7f0aea      | true     | anotherlowercolumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("anotherlowercolumn")
| efadc7      | true     | anotherlowercolumn             | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("anotherlowercolumn")
| 3277a4      | true     | crazy!@#\$%^&*()_+{}[]'"column | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("crazy!@#\$%^&*()_+{}[]'""column")
| 679dde      | true     | crazy!@#\$%^&*()_+{}[]'"column | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("crazy!@#\$%^&*()_+{}[]'""column")
| af19db      | true     | lowercolumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("lowercolumn")
| 37400d      | true     | lowercolumn                    | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("lowercolumn")
| 07481c      | true     | only_in_LBSCHEMA2              | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("only_in_LBSCHEMA2")
| 8554e6      | true     | only_in_LBSCHEMA2              | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("only_in_LBSCHEMA2")
| 2df3dc      | true     | only_in_PUBLIC                 | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("only_in_PUBLIC")
| e26cf3      | true     | only_in_PUBLIC                 | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("only_in_PUBLIC")

# Test: "Can apply single column with standard settings but complex constraint names" #

- **connection:** h2[config:standard]

| Permutation | Verified | tableName            | uqName                                   | OPERATIONS
| :---------- | :------- | :------------------- | :--------------------------------------- | :------
| 520721      | true     | LBSCHEMA2.TABLE_NAME | 4TEST_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4TEST_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 097ced      | true     | LBSCHEMA2.TABLE_NAME | 4test_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4test_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 0ffce6      | true     | LBSCHEMA2.TABLE_NAME | ANOTHERUPPERUNIQUECONSTRAINT             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| cd4fd7      | true     | LBSCHEMA2.TABLE_NAME | AnotherMixedUniqueConstraint             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 94eed1      | true     | LBSCHEMA2.TABLE_NAME | CRAZY!@#\$%^&*()_+{}[]'"UNIQUECONSTRAINT | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""UNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 8cf249      | true     | LBSCHEMA2.TABLE_NAME | MixedUniqueConstraint                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "MixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 10ff2f      | true     | LBSCHEMA2.TABLE_NAME | UPPERUNIQUECONSTRAINT                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| e1bb94      | true     | LBSCHEMA2.TABLE_NAME | anotherloweruniqueconstraint             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "anotherloweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 41d863      | true     | LBSCHEMA2.TABLE_NAME | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 89f64c      | true     | LBSCHEMA2.TABLE_NAME | loweruniqueconstraint                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "loweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 8c1382      | true     | LBSCHEMA2.TABLE_NAME | only_in_LBSCHEMA2                        | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "only_in_LBSCHEMA2" UNIQUE ("COLUMN_NAME")
| 4954fd      | true     | LBSCHEMA2.TABLE_NAME | only_in_PUBLIC                           | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "only_in_PUBLIC" UNIQUE ("COLUMN_NAME")
| 48888c      | true     | PUBLIC.TABLE_NAME    | 4TEST_uniqueconstraint                   | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "4TEST_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 9d22c7      | true     | PUBLIC.TABLE_NAME    | 4test_uniqueconstraint                   | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "4test_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 822e5b      | true     | PUBLIC.TABLE_NAME    | ANOTHERUPPERUNIQUECONSTRAINT             | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| ff4377      | true     | PUBLIC.TABLE_NAME    | AnotherMixedUniqueConstraint             | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 083d26      | true     | PUBLIC.TABLE_NAME    | CRAZY!@#\$%^&*()_+{}[]'"UNIQUECONSTRAINT | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""UNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 82feee      | true     | PUBLIC.TABLE_NAME    | MixedUniqueConstraint                    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "MixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| b51762      | true     | PUBLIC.TABLE_NAME    | UPPERUNIQUECONSTRAINT                    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 7d2fe5      | true     | PUBLIC.TABLE_NAME    | anotherloweruniqueconstraint             | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "anotherloweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 162b04      | true     | PUBLIC.TABLE_NAME    | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""uniqueconstraint" UNIQUE ("COLUMN_NAME")
| df268c      | true     | PUBLIC.TABLE_NAME    | loweruniqueconstraint                    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "loweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 5a3d01      | true     | PUBLIC.TABLE_NAME    | only_in_LBSCHEMA2                        | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "only_in_LBSCHEMA2" UNIQUE ("COLUMN_NAME")
| f9550b      | true     | PUBLIC.TABLE_NAME    | only_in_PUBLIC                           | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "only_in_PUBLIC" UNIQUE ("COLUMN_NAME")

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** h2[config:standard]

| Permutation | Verified | tableName                               | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 040a4c      | true     | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 64232c      | true     | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 7235ec      | true     | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| f90bc6      | true     | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 0d533f      | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 6dbf8b      | true     | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| a92dd5      | true     | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 6d7bef      | true     | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 62cf87      | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| e77a61      | true     | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 730360      | true     | LBSCHEMA2.only_in_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."only_in_LBSCHEMA2" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 99f863      | true     | PUBLIC.4TEST_table                      | **plan**: ALTER TABLE "PUBLIC"."4TEST_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 4486fd      | true     | PUBLIC.4test_table                      | **plan**: ALTER TABLE "PUBLIC"."4test_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 443117      | true     | PUBLIC.ANOTHERUPPERTABLE                | **plan**: ALTER TABLE "PUBLIC"."ANOTHERUPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 78e225      | true     | PUBLIC.AnotherMixedTable                | **plan**: ALTER TABLE "PUBLIC"."AnotherMixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| a17aa4      | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE    | **plan**: ALTER TABLE "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 6a8c10      | true     | PUBLIC.MixedTable                       | **plan**: ALTER TABLE "PUBLIC"."MixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 42126b      | true     | PUBLIC.UPPERTABLE                       | **plan**: ALTER TABLE "PUBLIC"."UPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 840c9d      | true     | PUBLIC.anotherlowertable                | **plan**: ALTER TABLE "PUBLIC"."anotherlowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 861913      | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table    | **plan**: ALTER TABLE "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| b68a2e      | true     | PUBLIC.lowertable                       | **plan**: ALTER TABLE "PUBLIC"."lowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| f6a54f      | true     | PUBLIC.only_in_PUBLIC                   | **plan**: ALTER TABLE "PUBLIC"."only_in_PUBLIC" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")

# Test: "Can apply unique constraint with with various settings" #

- **connection:** h2[config:standard]

| Permutation | Verified | columns              | name    | table                | OPERATIONS
| :---------- | :------- | :------------------- | :------ | :------------------- | :------
| 839c9f      | true     | COL_NAME             |         | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD UNIQUE ("COL_NAME")
| 81b512      | true     | COL_NAME             |         | PUBLIC.TEST_TABLE    | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD UNIQUE ("COL_NAME")
| 7bdf90      | true     | COL_NAME             | UQ_TEST | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME")
| de7221      | true     | COL_NAME             | UQ_TEST | PUBLIC.TEST_TABLE    | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME")
| eff9f4      | true     | COL_NAME1, COL_NAME2 |         | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD UNIQUE ("COL_NAME1", "COL_NAME2")
| 963023      | true     | COL_NAME1, COL_NAME2 |         | PUBLIC.TEST_TABLE    | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD UNIQUE ("COL_NAME1", "COL_NAME2")
| f381d9      | true     | COL_NAME1, COL_NAME2 | UQ_TEST | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME1", "COL_NAME2")
| 549e7a      | true     | COL_NAME1, COL_NAME2 | UQ_TEST | PUBLIC.TEST_TABLE    | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME1", "COL_NAME2")

# Test Version: "49ee98" #