**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple constraints at once" #

- **connection:** generic standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 6e63508     | Generic  | LBSCHEMA  | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE_1" ADD CONSTRAINT UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA"."TEST_TABLE_2" ADD CONSTRAINT UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA"."TEST_TABLE_3" ADD CONSTRAINT UNIQUE ("COL_NAME")
| bbb8e61     | Generic  | LBSCHEMA2 | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE_1" ADD CONSTRAINT UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_2" ADD CONSTRAINT UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_3" ADD CONSTRAINT UNIQUE ("COL_NAME")

# Test: "Can apply multi-column with standard settings" #

- **connection:** generic standard

| Permutation | Verified | column       | table                | OPERATIONS
| :---------- | :------- | :----------- | :------------------- | :------
| 84ff3b3     | Generic  | COL_1, COL_2 | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COL_1", "COL_2")
| 296b775     | Generic  | COL_1, COL_2 | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COL_1", "COL_2")

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** generic standard

| Permutation | Verified | column                         | table                | OPERATIONS
| :---------- | :------- | :----------------------------- | :------------------- | :------
| 90fd685     | Generic  | 4TEST_column                   | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4TEST_column")
| f05d279     | Generic  | 4TEST_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4TEST_column")
| 14f6112     | Generic  | 4test_column                   | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4test_column")
| f8986c8     | Generic  | 4test_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4test_column")
| 8b05f49     | Generic  | ANOTHERUPPERCOLUMN             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("ANOTHERUPPERCOLUMN")
| 15014fc     | Generic  | ANOTHERUPPERCOLUMN             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("ANOTHERUPPERCOLUMN")
| bc0ba2f     | Generic  | AnotherMixedColumn             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("AnotherMixedColumn")
| c89a61b     | Generic  | AnotherMixedColumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("AnotherMixedColumn")
| 8c448ee     | Generic  | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("CRAZY!@#\$%^&*()_+{}[]'""COLUMN")
| 5157d44     | Generic  | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("CRAZY!@#\$%^&*()_+{}[]'""COLUMN")
| 8109147     | Generic  | MixedColumn                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("MixedColumn")
| 48d44db     | Generic  | MixedColumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("MixedColumn")
| 16ad6b9     | Generic  | UPPERCOLUMN                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("UPPERCOLUMN")
| 9406687     | Generic  | UPPERCOLUMN                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("UPPERCOLUMN")
| 79bdf46     | Generic  | anotherlowercolumn             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("anotherlowercolumn")
| ae0d562     | Generic  | anotherlowercolumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("anotherlowercolumn")
| c10beb8     | Generic  | crazy!@#\$%^&*()_+{}[]'"column | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("crazy!@#\$%^&*()_+{}[]'""column")
| 09c5335     | Generic  | crazy!@#\$%^&*()_+{}[]'"column | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("crazy!@#\$%^&*()_+{}[]'""column")
| f8b06b3     | Generic  | lowercolumn                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("lowercolumn")
| 6dffcc2     | Generic  | lowercolumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("lowercolumn")

# Test: "Can apply single column with standard settings but complex constraint names" #

- **connection:** generic standard

| Permutation | Verified | table                | uq                                       | OPERATIONS
| :---------- | :------- | :------------------- | :--------------------------------------- | :------
| 1cd2867     | Generic  | LBSCHEMA.TABLE_NAME  | 4TEST_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "4TEST_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| a11f8fc     | Generic  | LBSCHEMA.TABLE_NAME  | 4test_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "4test_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 249ba55     | Generic  | LBSCHEMA.TABLE_NAME  | ANOTHERUPPERUNIQUECONSTRAINT             | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| e4a828d     | Generic  | LBSCHEMA.TABLE_NAME  | AnotherMixedUniqueConstraint             | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 5fe9445     | Generic  | LBSCHEMA.TABLE_NAME  | CRAZY!@#\$%^&*()_+{}[]'"UNIQUECONSTRAINT | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""UNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 1e96d7a     | Generic  | LBSCHEMA.TABLE_NAME  | MixedUniqueConstraint                    | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "MixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 7a2bfe7     | Generic  | LBSCHEMA.TABLE_NAME  | UPPERUNIQUECONSTRAINT                    | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| cfd90b7     | Generic  | LBSCHEMA.TABLE_NAME  | anotherloweruniqueconstraint             | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "anotherloweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 74f2d7c     | Generic  | LBSCHEMA.TABLE_NAME  | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 59ad887     | Generic  | LBSCHEMA.TABLE_NAME  | loweruniqueconstraint                    | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "loweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 73b0d73     | Generic  | LBSCHEMA2.TABLE_NAME | 4TEST_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4TEST_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| f110b1e     | Generic  | LBSCHEMA2.TABLE_NAME | 4test_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4test_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 7370961     | Generic  | LBSCHEMA2.TABLE_NAME | ANOTHERUPPERUNIQUECONSTRAINT             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| d93d1fd     | Generic  | LBSCHEMA2.TABLE_NAME | AnotherMixedUniqueConstraint             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 06d9d83     | Generic  | LBSCHEMA2.TABLE_NAME | CRAZY!@#\$%^&*()_+{}[]'"UNIQUECONSTRAINT | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""UNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| cb27d51     | Generic  | LBSCHEMA2.TABLE_NAME | MixedUniqueConstraint                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "MixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 30b02fb     | Generic  | LBSCHEMA2.TABLE_NAME | UPPERUNIQUECONSTRAINT                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| fd02ccc     | Generic  | LBSCHEMA2.TABLE_NAME | anotherloweruniqueconstraint             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "anotherloweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 3dfc31a     | Generic  | LBSCHEMA2.TABLE_NAME | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 2228b62     | Generic  | LBSCHEMA2.TABLE_NAME | loweruniqueconstraint                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "loweruniqueconstraint" UNIQUE ("COLUMN_NAME")

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** generic standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 82ca36a     | Generic  | LBSCHEMA.4TEST_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4TEST_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| a59cadd     | Generic  | LBSCHEMA.4test_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4test_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 02109bf     | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: ALTER TABLE "LBSCHEMA"."ANOTHERUPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 0556eae     | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: ALTER TABLE "LBSCHEMA"."AnotherMixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 2cbd26a     | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 6b38df5     | Generic  | LBSCHEMA.MixedTable                     | **plan**: ALTER TABLE "LBSCHEMA"."MixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 7636549     | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: ALTER TABLE "LBSCHEMA"."ONLY_IN_LBSCHEMA" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 8f18146     | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: ALTER TABLE "LBSCHEMA"."UPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 5c86256     | Generic  | LBSCHEMA.anotherlowertable              | **plan**: ALTER TABLE "LBSCHEMA"."anotherlowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 86a5d18     | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| c4dbeb3     | Generic  | LBSCHEMA.lowertable                     | **plan**: ALTER TABLE "LBSCHEMA"."lowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 31f94ac     | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 13e76ef     | Generic  | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| cec2e1c     | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| fe461f7     | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| dec400d     | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| cf72a30     | Generic  | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 6e579ae     | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| d5eec30     | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 00b229a     | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| b875ac8     | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 5921e4d     | Generic  | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")

# Test: "Can apply unique constraint with with various settings" #

- **connection:** generic standard

| Permutation | Verified | columns              | name    | table                | OPERATIONS
| :---------- | :------- | :------------------- | :------ | :------------------- | :------
| dd66f1a     | Generic  | COL_NAME             |         | LBSCHEMA.TEST_TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT UNIQUE ("COL_NAME")
| 1eddbbf     | Generic  | COL_NAME             |         | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT UNIQUE ("COL_NAME")
| 87dcf12     | Generic  | COL_NAME             | UQ_TEST | LBSCHEMA.TEST_TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME")
| 404bbc3     | Generic  | COL_NAME             | UQ_TEST | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME")
| af1395c     | Generic  | COL_NAME1, COL_NAME2 |         | LBSCHEMA.TEST_TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT UNIQUE ("COL_NAME1", "COL_NAME2")
| c70d2c9     | Generic  | COL_NAME1, COL_NAME2 |         | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT UNIQUE ("COL_NAME1", "COL_NAME2")
| 0ed37fc     | Generic  | COL_NAME1, COL_NAME2 | UQ_TEST | LBSCHEMA.TEST_TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME1", "COL_NAME2")
| 1043bd4     | Generic  | COL_NAME1, COL_NAME2 | UQ_TEST | LBSCHEMA2.TEST_TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME1", "COL_NAME2")

# Test Version: "43b318" #