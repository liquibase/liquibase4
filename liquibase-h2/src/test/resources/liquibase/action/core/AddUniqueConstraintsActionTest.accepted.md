**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple constraints at once" #

- **connection:** h2 standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 8caa09      | true     | LBSCHEMA2 | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE_1" ADD UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_2" ADD UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_3" ADD UNIQUE ("COL_NAME")
| 63bb73      | true     | PUBLIC    | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE_1" ADD UNIQUE ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_2" ADD UNIQUE ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_3" ADD UNIQUE ("COL_NAME")

# Test: "Can apply multi-column with standard settings" #

- **connection:** h2 standard

| Permutation | Verified | column       | table                | OPERATIONS
| :---------- | :------- | :----------- | :------------------- | :------
| 6c4620      | true     | COL_1, COL_2 | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COL_1", "COL_2")
| 3742aa      | true     | COL_1, COL_2 | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COL_1", "COL_2")

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** h2 standard

| Permutation | Verified | column                         | table                | OPERATIONS
| :---------- | :------- | :----------------------------- | :------------------- | :------
| a37755      | true     | 4TEST_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4TEST_column")
| 491d75      | true     | 4TEST_column                   | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4TEST_column")
| 305323      | true     | 4test_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4test_column")
| 47d34c      | true     | 4test_column                   | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4test_column")
| 586905      | true     | ANOTHERUPPERCOLUMN             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("ANOTHERUPPERCOLUMN")
| 833d68      | true     | ANOTHERUPPERCOLUMN             | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("ANOTHERUPPERCOLUMN")
| 0dd17c      | true     | AnotherMixedColumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("AnotherMixedColumn")
| 6884e4      | true     | AnotherMixedColumn             | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("AnotherMixedColumn")
| 9c692f      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("CRAZY!@#\$%^&*()_+{}[]'""COLUMN")
| 2f763c      | true     | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("CRAZY!@#\$%^&*()_+{}[]'""COLUMN")
| 690e66      | true     | MixedColumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("MixedColumn")
| c6f538      | true     | MixedColumn                    | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("MixedColumn")
| 537e42      | true     | UPPERCOLUMN                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("UPPERCOLUMN")
| 95526b      | true     | UPPERCOLUMN                    | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("UPPERCOLUMN")
| 387e66      | true     | anotherlowercolumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("anotherlowercolumn")
| eb9ae2      | true     | anotherlowercolumn             | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("anotherlowercolumn")
| 93183f      | true     | crazy!@#\$%^&*()_+{}[]'"column | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("crazy!@#\$%^&*()_+{}[]'""column")
| 53ee5e      | true     | crazy!@#\$%^&*()_+{}[]'"column | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("crazy!@#\$%^&*()_+{}[]'""column")
| 3776e4      | true     | lowercolumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("lowercolumn")
| 914a4a      | true     | lowercolumn                    | PUBLIC.TABLE_NAME    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("lowercolumn")

# Test: "Can apply single column with standard settings but complex constraint names" #

- **connection:** h2 standard

| Permutation | Verified | table                | uq                                       | OPERATIONS
| :---------- | :------- | :------------------- | :--------------------------------------- | :------
| 8555ff      | true     | LBSCHEMA2.TABLE_NAME | 4TEST_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4TEST_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| f5146c      | true     | LBSCHEMA2.TABLE_NAME | 4test_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4test_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| d57f30      | true     | LBSCHEMA2.TABLE_NAME | ANOTHERUPPERUNIQUECONSTRAINT             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 928d90      | true     | LBSCHEMA2.TABLE_NAME | AnotherMixedUniqueConstraint             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| f9e233      | true     | LBSCHEMA2.TABLE_NAME | CRAZY!@#\$%^&*()_+{}[]'"UNIQUECONSTRAINT | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""UNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 79e383      | true     | LBSCHEMA2.TABLE_NAME | MixedUniqueConstraint                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "MixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| d5bebe      | true     | LBSCHEMA2.TABLE_NAME | UPPERUNIQUECONSTRAINT                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 645946      | true     | LBSCHEMA2.TABLE_NAME | anotherloweruniqueconstraint             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "anotherloweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 509520      | true     | LBSCHEMA2.TABLE_NAME | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 48b3ce      | true     | LBSCHEMA2.TABLE_NAME | loweruniqueconstraint                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "loweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 12e372      | true     | PUBLIC.TABLE_NAME    | 4TEST_uniqueconstraint                   | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "4TEST_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| c00fed      | true     | PUBLIC.TABLE_NAME    | 4test_uniqueconstraint                   | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "4test_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 127b12      | true     | PUBLIC.TABLE_NAME    | ANOTHERUPPERUNIQUECONSTRAINT             | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| acb6b1      | true     | PUBLIC.TABLE_NAME    | AnotherMixedUniqueConstraint             | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 5a0be1      | true     | PUBLIC.TABLE_NAME    | CRAZY!@#\$%^&*()_+{}[]'"UNIQUECONSTRAINT | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""UNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 36d48b      | true     | PUBLIC.TABLE_NAME    | MixedUniqueConstraint                    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "MixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 45ae42      | true     | PUBLIC.TABLE_NAME    | UPPERUNIQUECONSTRAINT                    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "UPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 0ded96      | true     | PUBLIC.TABLE_NAME    | anotherloweruniqueconstraint             | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "anotherloweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 0969e8      | true     | PUBLIC.TABLE_NAME    | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""uniqueconstraint" UNIQUE ("COLUMN_NAME")
| f62bdc      | true     | PUBLIC.TABLE_NAME    | loweruniqueconstraint                    | **plan**: ALTER TABLE "PUBLIC"."TABLE_NAME" ADD CONSTRAINT "loweruniqueconstraint" UNIQUE ("COLUMN_NAME")

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** h2 standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 001676      | true     | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| fe5689      | true     | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 791ecd      | true     | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 945d60      | true     | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 644d6e      | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| e731b7      | true     | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| e10b18      | true     | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 9dfc94      | true     | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 416359      | true     | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 28ea09      | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 993cd6      | true     | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 9581d7      | true     | PUBLIC.4TEST_table                      | **plan**: ALTER TABLE "PUBLIC"."4TEST_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| e34488      | true     | PUBLIC.4test_table                      | **plan**: ALTER TABLE "PUBLIC"."4test_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| b08716      | true     | PUBLIC.ANOTHERUPPERTABLE                | **plan**: ALTER TABLE "PUBLIC"."ANOTHERUPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 1d572d      | true     | PUBLIC.AnotherMixedTable                | **plan**: ALTER TABLE "PUBLIC"."AnotherMixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 69cf0e      | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE    | **plan**: ALTER TABLE "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| ec2093      | true     | PUBLIC.MixedTable                       | **plan**: ALTER TABLE "PUBLIC"."MixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 6e5bf8      | true     | PUBLIC.ONLY_IN_PUBLIC                   | **plan**: ALTER TABLE "PUBLIC"."ONLY_IN_PUBLIC" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 35a909      | true     | PUBLIC.UPPERTABLE                       | **plan**: ALTER TABLE "PUBLIC"."UPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| f79c86      | true     | PUBLIC.anotherlowertable                | **plan**: ALTER TABLE "PUBLIC"."anotherlowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| dc4e6f      | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table    | **plan**: ALTER TABLE "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| df2559      | true     | PUBLIC.lowertable                       | **plan**: ALTER TABLE "PUBLIC"."lowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")

# Test: "Can apply unique constraint with with various settings" #

- **connection:** h2 standard

| Permutation | Verified | columns              | name    | table                | OPERATIONS
| :---------- | :------- | :------------------- | :------ | :------------------- | :------
| b0c609      | true     | COL_NAME             |         | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD UNIQUE ("COL_NAME")
| c46f90      | true     | COL_NAME             |         | PUBLIC.TEST_TABLE    | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD UNIQUE ("COL_NAME")
| 208be0      | true     | COL_NAME             | UQ_TEST | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME")
| 1a43c1      | true     | COL_NAME             | UQ_TEST | PUBLIC.TEST_TABLE    | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME")
| 5105f1      | true     | COL_NAME1, COL_NAME2 |         | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD UNIQUE ("COL_NAME1", "COL_NAME2")
| 5b98ba      | true     | COL_NAME1, COL_NAME2 |         | PUBLIC.TEST_TABLE    | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD UNIQUE ("COL_NAME1", "COL_NAME2")
| 05c9d0      | true     | COL_NAME1, COL_NAME2 | UQ_TEST | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME1", "COL_NAME2")
| d76145      | true     | COL_NAME1, COL_NAME2 | UQ_TEST | PUBLIC.TEST_TABLE    | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME1", "COL_NAME2")

# Test Version: "0cbe5b" #