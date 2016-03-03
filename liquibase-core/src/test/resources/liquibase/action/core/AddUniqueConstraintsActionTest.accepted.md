**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple constraints at once" #

- **connection:** generic standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 6e6350      | Generic  | LBSCHEMA  | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE_1" ADD CONSTRAINT UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA"."TEST_TABLE_2" ADD CONSTRAINT UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA"."TEST_TABLE_3" ADD CONSTRAINT UNIQUE ("COL_NAME")
| bbb8e6      | Generic  | LBSCHEMA2 | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE_1" ADD CONSTRAINT UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_2" ADD CONSTRAINT UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_3" ADD CONSTRAINT UNIQUE ("COL_NAME")

# Test: "Can apply multi-column with standard settings" #

- **connection:** generic standard

| Permutation | Verified | column       | table                | OPERATIONS
| :---------- | :------- | :----------- | :------------------- | :------
| 84ff3b      | Generic  | COL_1, COL_2 | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COL_1", "COL_2")
| 296b77      | Generic  | COL_1, COL_2 | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COL_1", "COL_2")

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** generic standard

| Permutation | Verified | column                         | table                | OPERATIONS
| :---------- | :------- | :----------------------------- | :------------------- | :------
| 90fd68      | Generic  | 4TEST_column                   | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4TEST_column")
| f05d27      | Generic  | 4TEST_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4TEST_column")
| 14f611      | Generic  | 4test_column                   | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4test_column")
| f8986c      | Generic  | 4test_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4test_column")
| 8b05f4      | Generic  | ANOTHERUPPERCOLUMN             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("ANOTHERUPPERCOLUMN")
| 15014f      | Generic  | ANOTHERUPPERCOLUMN             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("ANOTHERUPPERCOLUMN")
| bc0ba2      | Generic  | AnotherMixedColumn             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("AnotherMixedColumn")
| c89a61      | Generic  | AnotherMixedColumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("AnotherMixedColumn")
| 8c448e      | Generic  | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("CRAZY!@#\$%^&*()_+{}[]'""COLUMN")
| 5157d4      | Generic  | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("CRAZY!@#\$%^&*()_+{}[]'""COLUMN")
| 810914      | Generic  | MixedColumn                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("MixedColumn")
| 48d44d      | Generic  | MixedColumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("MixedColumn")
| 16ad6b      | Generic  | UPPERCOLUMN                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("UPPERCOLUMN")
| 940668      | Generic  | UPPERCOLUMN                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("UPPERCOLUMN")
| 79bdf4      | Generic  | anotherlowercolumn             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("anotherlowercolumn")
| ae0d56      | Generic  | anotherlowercolumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("anotherlowercolumn")
| c10beb      | Generic  | crazy!@#\$%^&*()_+{}[]'"column | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("crazy!@#\$%^&*()_+{}[]'""column")
| 09c533      | Generic  | crazy!@#\$%^&*()_+{}[]'"column | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("crazy!@#\$%^&*()_+{}[]'""column")
| f8b06b      | Generic  | lowercolumn                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("lowercolumn")
| 6dffcc      | Generic  | lowercolumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("lowercolumn")

# Test: "Can apply single column with standard settings but complex constraint names" #

- **connection:** generic standard

| Permutation | Verified | table                | uq                                       | OPERATIONS
| :---------- | :------- | :------------------- | :--------------------------------------- | :------
| 1cd286      | Generic  | LBSCHEMA.TABLE_NAME  | 4TEST_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "4TEST_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| a11f8f      | Generic  | LBSCHEMA.TABLE_NAME  | 4test_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "4test_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 249ba5      | Generic  | LBSCHEMA.TABLE_NAME  | ANOTHERUPPERUNIQUECONSTRAINT             | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| e4a828      | Generic  | LBSCHEMA.TABLE_NAME  | AnotherMixedUniqueConstraint             | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 5fe944      | Generic  | LBSCHEMA.TABLE_NAME  | CRAZY!@#\$%^&*()_+{}[]'"UNIQUECONSTRAINT | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""UNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 1e96d7      | Generic  | LBSCHEMA.TABLE_NAME  | MixedUniqueConstraint                    | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "MixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 7a2bfe      | Generic  | LBSCHEMA.TABLE_NAME  | UPPERUNIQUECONSTRAINT                    | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| cfd90b      | Generic  | LBSCHEMA.TABLE_NAME  | anotherloweruniqueconstraint             | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "anotherloweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 74f2d7      | Generic  | LBSCHEMA.TABLE_NAME  | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 59ad88      | Generic  | LBSCHEMA.TABLE_NAME  | loweruniqueconstraint                    | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "loweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 73b0d7      | Generic  | LBSCHEMA2.TABLE_NAME | 4TEST_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4TEST_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| f110b1      | Generic  | LBSCHEMA2.TABLE_NAME | 4test_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4test_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 737096      | Generic  | LBSCHEMA2.TABLE_NAME | ANOTHERUPPERUNIQUECONSTRAINT             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| d93d1f      | Generic  | LBSCHEMA2.TABLE_NAME | AnotherMixedUniqueConstraint             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 06d9d8      | Generic  | LBSCHEMA2.TABLE_NAME | CRAZY!@#\$%^&*()_+{}[]'"UNIQUECONSTRAINT | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""UNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| cb27d5      | Generic  | LBSCHEMA2.TABLE_NAME | MixedUniqueConstraint                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "MixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 30b02f      | Generic  | LBSCHEMA2.TABLE_NAME | UPPERUNIQUECONSTRAINT                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| fd02cc      | Generic  | LBSCHEMA2.TABLE_NAME | anotherloweruniqueconstraint             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "anotherloweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 3dfc31      | Generic  | LBSCHEMA2.TABLE_NAME | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 2228b6      | Generic  | LBSCHEMA2.TABLE_NAME | loweruniqueconstraint                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "loweruniqueconstraint" UNIQUE ("COLUMN_NAME")

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** generic standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 82ca36      | Generic  | LBSCHEMA.4TEST_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4TEST_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| a59cad      | Generic  | LBSCHEMA.4test_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4test_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 02109b      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: ALTER TABLE "LBSCHEMA"."ANOTHERUPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 0556ea      | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: ALTER TABLE "LBSCHEMA"."AnotherMixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 2cbd26      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 6b38df      | Generic  | LBSCHEMA.MixedTable                     | **plan**: ALTER TABLE "LBSCHEMA"."MixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 763654      | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: ALTER TABLE "LBSCHEMA"."ONLY_IN_LBSCHEMA" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 8f1814      | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: ALTER TABLE "LBSCHEMA"."UPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 5c8625      | Generic  | LBSCHEMA.anotherlowertable              | **plan**: ALTER TABLE "LBSCHEMA"."anotherlowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 86a5d1      | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| c4dbeb      | Generic  | LBSCHEMA.lowertable                     | **plan**: ALTER TABLE "LBSCHEMA"."lowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 31f94a      | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 13e76e      | Generic  | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| cec2e1      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| fe461f      | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| dec400      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| cf72a3      | Generic  | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 6e579a      | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| d5eec3      | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 00b229      | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| b875ac      | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 5921e4      | Generic  | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")

# Test: "Can apply unique constraint with with various settings" #

- **connection:** generic standard

| Permutation | Verified | columns              | name    | table                | OPERATIONS
| :---------- | :------- | :------------------- | :------ | :------------------- | :------
| dd66f1      | Generic  | COL_NAME             |         | LBSCHEMA.TEST_TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT UNIQUE ("COL_NAME")
| 1eddbb      | Generic  | COL_NAME             |         | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT UNIQUE ("COL_NAME")
| 87dcf1      | Generic  | COL_NAME             | UQ_TEST | LBSCHEMA.TEST_TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME")
| 404bbc      | Generic  | COL_NAME             | UQ_TEST | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME")
| af1395      | Generic  | COL_NAME1, COL_NAME2 |         | LBSCHEMA.TEST_TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT UNIQUE ("COL_NAME1", "COL_NAME2")
| c70d2c      | Generic  | COL_NAME1, COL_NAME2 |         | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT UNIQUE ("COL_NAME1", "COL_NAME2")
| 0ed37f      | Generic  | COL_NAME1, COL_NAME2 | UQ_TEST | LBSCHEMA.TEST_TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME1", "COL_NAME2")
| 1043bd      | Generic  | COL_NAME1, COL_NAME2 | UQ_TEST | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME1", "COL_NAME2")

# Test Version: "9fa363" #