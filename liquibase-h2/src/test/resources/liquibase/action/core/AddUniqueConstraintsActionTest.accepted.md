**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple constraints at once" #

- **connection:** h2[config:standard]

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| c0dc64      | true     | LBSCHEMA2 | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE_1" ADD UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_2" ADD UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_3" ADD UNIQUE ("COL_NAME")
| 99ef3c      | true     | PUBLIC    | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE_1" ADD UNIQUE ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_2" ADD UNIQUE ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_3" ADD UNIQUE ("COL_NAME")

# Test: "Can apply multi-column with standard settings" #

- **connection:** h2[config:standard]

| Permutation | Verified | column       | table                | OPERATIONS
| :---------- | :------- | :----------- | :------------------- | :------
| 582df9      | true     | COL_1, COL_2 | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COL_1", "COL_2")
| 3a1deb      | true     | COL_1, COL_2 | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COL_1", "COL_2")

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** h2[config:standard]

| Permutation | Verified | column                         | table                | OPERATIONS
| :---------- | :------- | :----------------------------- | :------------------- | :------
| 545894      | true     | 4TEST_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4TEST_column")
| 74228d      | true     | 4TEST_column                   | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4TEST_column")
| 8146cd      | true     | 4test_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4test_column")
| c490bf      | true     | 4test_column                   | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4test_column")
| efccaf      | true     | ANOTHERUPPERCOLUMN             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("ANOTHERUPPERCOLUMN")
| 95286a      | true     | ANOTHERUPPERCOLUMN             | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("ANOTHERUPPERCOLUMN")
| 3ba072      | true     | AnotherMixedColumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("AnotherMixedColumn")
| 318b8e      | true     | AnotherMixedColumn             | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("AnotherMixedColumn")
| e1f404      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("CRAZY!@#\$%^&*()_+{}[]'""COLUMN")
| 6627fa      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("CRAZY!@#\$%^&*()_+{}[]'""COLUMN")
| aab218      | true     | MixedColumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("MixedColumn")
| caddb8      | true     | MixedColumn                    | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("MixedColumn")
| a36b48      | true     | UPPERCOLUMN                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("UPPERCOLUMN")
| 32d061      | true     | UPPERCOLUMN                    | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("UPPERCOLUMN")
| e5cd72      | true     | anotherlowercolumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("anotherlowercolumn")
| c2cf1d      | true     | anotherlowercolumn             | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("anotherlowercolumn")
| 8a56e0      | true     | crazy!@#\$%^&*()_+{}[]'"column | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("crazy!@#\$%^&*()_+{}[]'""column")
| 812f61      | true     | crazy!@#\$%^&*()_+{}[]'"column | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("crazy!@#\$%^&*()_+{}[]'""column")
| 8b0c99      | true     | lowercolumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("lowercolumn")
| 55496a      | true     | lowercolumn                    | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("lowercolumn")

# Test: "Can apply single column with standard settings but complex constraint names" #

- **connection:** h2[config:standard]

| Permutation | Verified | table                | uq                                       | OPERATIONS
| :---------- | :------- | :------------------- | :--------------------------------------- | :------
| d9fedd      | true     | LBSCHEMA2.TABLE_NAME | 4TEST_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4TEST_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 252143      | true     | LBSCHEMA2.TABLE_NAME | 4test_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4test_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 2154a0      | true     | LBSCHEMA2.TABLE_NAME | ANOTHERUPPERUNIQUECONSTRAINT             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 399374      | true     | LBSCHEMA2.TABLE_NAME | AnotherMixedUniqueConstraint             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 17b049      | true     | LBSCHEMA2.TABLE_NAME | CRAZY!@#\$%^&*()_+{}[]'"UNIQUECONSTRAINT | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""UNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| edc67b      | true     | LBSCHEMA2.TABLE_NAME | MixedUniqueConstraint                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "MixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| c0955a      | true     | LBSCHEMA2.TABLE_NAME | UPPERUNIQUECONSTRAINT                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| ec8783      | true     | LBSCHEMA2.TABLE_NAME | anotherloweruniqueconstraint             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "anotherloweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| c6c265      | true     | LBSCHEMA2.TABLE_NAME | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""uniqueconstraint" UNIQUE ("COLUMN_NAME")
| bc5385      | true     | LBSCHEMA2.TABLE_NAME | loweruniqueconstraint                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "loweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 3038d3      | true     | PUBLIC.TABLE_NAME    | 4TEST_uniqueconstraint                   | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "4TEST_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 761226      | true     | PUBLIC.TABLE_NAME    | 4test_uniqueconstraint                   | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "4test_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 9c70c7      | true     | PUBLIC.TABLE_NAME    | ANOTHERUPPERUNIQUECONSTRAINT             | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 0b73c7      | true     | PUBLIC.TABLE_NAME    | AnotherMixedUniqueConstraint             | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| c2d577      | true     | PUBLIC.TABLE_NAME    | CRAZY!@#\$%^&*()_+{}[]'"UNIQUECONSTRAINT | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""UNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 369c2c      | true     | PUBLIC.TABLE_NAME    | MixedUniqueConstraint                    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "MixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 30126c      | true     | PUBLIC.TABLE_NAME    | UPPERUNIQUECONSTRAINT                    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 49c827      | true     | PUBLIC.TABLE_NAME    | anotherloweruniqueconstraint             | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "anotherloweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| ec7b47      | true     | PUBLIC.TABLE_NAME    | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 97359a      | true     | PUBLIC.TABLE_NAME    | loweruniqueconstraint                    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "loweruniqueconstraint" UNIQUE ("COLUMN_NAME")

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** h2[config:standard]

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 9ce697      | true     | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 05440c      | true     | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| e47136      | true     | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 25cd1a      | true     | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| d891cc      | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 3a440d      | true     | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| b5b886      | true     | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| b316c7      | true     | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 4649b2      | true     | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| e67874      | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 3847ea      | true     | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| f3d962      | true     | PUBLIC.4TEST_table                      | **plan**: ALTER TABLE "PUBLIC"."4TEST_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| dfc1d8      | true     | PUBLIC.4test_table                      | **plan**: ALTER TABLE "PUBLIC"."4test_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 27d4d5      | true     | PUBLIC.ANOTHERUPPERTABLE                | **plan**: ALTER TABLE "PUBLIC"."ANOTHERUPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 846368      | true     | PUBLIC.AnotherMixedTable                | **plan**: ALTER TABLE "PUBLIC"."AnotherMixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| bdcb41      | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE    | **plan**: ALTER TABLE "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 7bd544      | true     | PUBLIC.MixedTable                       | **plan**: ALTER TABLE "PUBLIC"."MixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 5e44e6      | true     | PUBLIC.ONLY_IN_PUBLIC                   | **plan**: ALTER TABLE "PUBLIC"."ONLY_IN_PUBLIC" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 09c0e6      | true     | PUBLIC.UPPERTABLE                       | **plan**: ALTER TABLE "PUBLIC"."UPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 7aafe6      | true     | PUBLIC.anotherlowertable                | **plan**: ALTER TABLE "PUBLIC"."anotherlowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 230483      | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table    | **plan**: ALTER TABLE "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| b8d606      | true     | PUBLIC.lowertable                       | **plan**: ALTER TABLE "PUBLIC"."lowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")

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

# Test Version: "278bd8" #