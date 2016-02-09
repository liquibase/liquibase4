**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple constraints at once" #

- **connection:** generic[config:standard]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 0f43a8      | Generic  | LBSCHEMA   | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE_1" ADD CONSTRAINT UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA"."TEST_TABLE_2" ADD CONSTRAINT UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA"."TEST_TABLE_3" ADD CONSTRAINT UNIQUE ("COL_NAME")
| eb7c69      | Generic  | LBSCHEMA2  | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE_1" ADD CONSTRAINT UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_2" ADD CONSTRAINT UNIQUE ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_3" ADD CONSTRAINT UNIQUE ("COL_NAME")

# Test: "Can apply multi-column with standard settings" #

- **connection:** generic[config:standard]

| Permutation | Verified | columnName   | tableName            | OPERATIONS
| :---------- | :------- | :----------- | :------------------- | :------
| 7d7084      | Generic  | COL_1, COL_2 | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COL_1", "COL_2")
| 105239      | Generic  | COL_1, COL_2 | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COL_1", "COL_2")

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** generic[config:standard]

| Permutation | Verified | columnName                     | tableName            | OPERATIONS
| :---------- | :------- | :----------------------------- | :------------------- | :------
| 0995a0      | Generic  | 4TEST_column                   | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4TEST_column")
| 5d6650      | Generic  | 4TEST_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4TEST_column")
| 6c1f1d      | Generic  | 4test_column                   | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4test_column")
| 379f6d      | Generic  | 4test_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("4test_column")
| cb7710      | Generic  | ANOTHERUPPERCOLUMN             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("ANOTHERUPPERCOLUMN")
| dca744      | Generic  | ANOTHERUPPERCOLUMN             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("ANOTHERUPPERCOLUMN")
| dd9917      | Generic  | AnotherMixedColumn             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("AnotherMixedColumn")
| 9ffabf      | Generic  | AnotherMixedColumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("AnotherMixedColumn")
| 2bb389      | Generic  | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("CRAZY!@#\$%^&*()_+{}[]'""COLUMN")
| f28b77      | Generic  | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("CRAZY!@#\$%^&*()_+{}[]'""COLUMN")
| d4b57b      | Generic  | MixedColumn                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("MixedColumn")
| 7df0bc      | Generic  | MixedColumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("MixedColumn")
| 14c465      | Generic  | UPPERCOLUMN                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("UPPERCOLUMN")
| 5d731e      | Generic  | UPPERCOLUMN                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("UPPERCOLUMN")
| 951b96      | Generic  | anotherlowercolumn             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("anotherlowercolumn")
| 7d5350      | Generic  | anotherlowercolumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("anotherlowercolumn")
| 4b4f7e      | Generic  | crazy!@#\$%^&*()_+{}[]'"column | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("crazy!@#\$%^&*()_+{}[]'""column")
| aa782b      | Generic  | crazy!@#\$%^&*()_+{}[]'"column | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("crazy!@#\$%^&*()_+{}[]'""column")
| 47285c      | Generic  | lowercolumn                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("lowercolumn")
| c029ce      | Generic  | lowercolumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("lowercolumn")
| dc165b      | Generic  | only_in_LBSCHEMA               | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("only_in_LBSCHEMA")
| a154f7      | Generic  | only_in_LBSCHEMA               | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("only_in_LBSCHEMA")
| 57e760      | Generic  | only_in_LBSCHEMA2              | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("only_in_LBSCHEMA2")
| 362cdd      | Generic  | only_in_LBSCHEMA2              | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UQ_NAME" UNIQUE ("only_in_LBSCHEMA2")

# Test: "Can apply single column with standard settings but complex constraint names" #

- **connection:** generic[config:standard]

| Permutation | Verified | tableName            | uqName                                   | OPERATIONS
| :---------- | :------- | :------------------- | :--------------------------------------- | :------
| bed3c4      | Generic  | LBSCHEMA.TABLE_NAME  | 4TEST_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "4TEST_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 377f81      | Generic  | LBSCHEMA.TABLE_NAME  | 4test_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "4test_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 2a8fd0      | Generic  | LBSCHEMA.TABLE_NAME  | ANOTHERUPPERUNIQUECONSTRAINT             | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 2f1b86      | Generic  | LBSCHEMA.TABLE_NAME  | AnotherMixedUniqueConstraint             | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 1427f9      | Generic  | LBSCHEMA.TABLE_NAME  | CRAZY!@#\$%^&*()_+{}[]'"UNIQUECONSTRAINT | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""UNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| e949e5      | Generic  | LBSCHEMA.TABLE_NAME  | MixedUniqueConstraint                    | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "MixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| fb1f31      | Generic  | LBSCHEMA.TABLE_NAME  | UPPERUNIQUECONSTRAINT                    | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 3f563e      | Generic  | LBSCHEMA.TABLE_NAME  | anotherloweruniqueconstraint             | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "anotherloweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 2fb3df      | Generic  | LBSCHEMA.TABLE_NAME  | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""uniqueconstraint" UNIQUE ("COLUMN_NAME")
| d609ca      | Generic  | LBSCHEMA.TABLE_NAME  | loweruniqueconstraint                    | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "loweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 1aec87      | Generic  | LBSCHEMA.TABLE_NAME  | only_in_LBSCHEMA                         | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "only_in_LBSCHEMA" UNIQUE ("COLUMN_NAME")
| 7201a0      | Generic  | LBSCHEMA.TABLE_NAME  | only_in_LBSCHEMA2                        | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "only_in_LBSCHEMA2" UNIQUE ("COLUMN_NAME")
| 384ac3      | Generic  | LBSCHEMA2.TABLE_NAME | 4TEST_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4TEST_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 641d96      | Generic  | LBSCHEMA2.TABLE_NAME | 4test_uniqueconstraint                   | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4test_uniqueconstraint" UNIQUE ("COLUMN_NAME")
| 9ee251      | Generic  | LBSCHEMA2.TABLE_NAME | ANOTHERUPPERUNIQUECONSTRAINT             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| c7ea40      | Generic  | LBSCHEMA2.TABLE_NAME | AnotherMixedUniqueConstraint             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 224a97      | Generic  | LBSCHEMA2.TABLE_NAME | CRAZY!@#\$%^&*()_+{}[]'"UNIQUECONSTRAINT | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""UNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| c964b7      | Generic  | LBSCHEMA2.TABLE_NAME | MixedUniqueConstraint                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "MixedUniqueConstraint" UNIQUE ("COLUMN_NAME")
| 776d31      | Generic  | LBSCHEMA2.TABLE_NAME | UPPERUNIQUECONSTRAINT                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UPPERUNIQUECONSTRAINT" UNIQUE ("COLUMN_NAME")
| 8f5f7b      | Generic  | LBSCHEMA2.TABLE_NAME | anotherloweruniqueconstraint             | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "anotherloweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| ab66c8      | Generic  | LBSCHEMA2.TABLE_NAME | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""uniqueconstraint" UNIQUE ("COLUMN_NAME")
| ca4880      | Generic  | LBSCHEMA2.TABLE_NAME | loweruniqueconstraint                    | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "loweruniqueconstraint" UNIQUE ("COLUMN_NAME")
| 049660      | Generic  | LBSCHEMA2.TABLE_NAME | only_in_LBSCHEMA                         | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "only_in_LBSCHEMA" UNIQUE ("COLUMN_NAME")
| 86dddd      | Generic  | LBSCHEMA2.TABLE_NAME | only_in_LBSCHEMA2                        | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "only_in_LBSCHEMA2" UNIQUE ("COLUMN_NAME")

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** generic[config:standard]

| Permutation | Verified | tableName                               | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 6f10b3      | Generic  | LBSCHEMA.4TEST_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4TEST_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 447604      | Generic  | LBSCHEMA.4test_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4test_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 7d8923      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: ALTER TABLE "LBSCHEMA"."ANOTHERUPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 244240      | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: ALTER TABLE "LBSCHEMA"."AnotherMixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| cf6e70      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 1ab5be      | Generic  | LBSCHEMA.MixedTable                     | **plan**: ALTER TABLE "LBSCHEMA"."MixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| d62fef      | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: ALTER TABLE "LBSCHEMA"."UPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| b7bd07      | Generic  | LBSCHEMA.anotherlowertable              | **plan**: ALTER TABLE "LBSCHEMA"."anotherlowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 8d1056      | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| f03884      | Generic  | LBSCHEMA.lowertable                     | **plan**: ALTER TABLE "LBSCHEMA"."lowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| a69da2      | Generic  | LBSCHEMA.only_in_LBSCHEMA               | **plan**: ALTER TABLE "LBSCHEMA"."only_in_LBSCHEMA" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| a86d64      | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 590a6e      | Generic  | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 2927a8      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| ce1ca3      | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 710fa9      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 315ef1      | Generic  | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 33d15e      | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 22dc5a      | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| fbf4a4      | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| 55094c      | Generic  | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")
| f90500      | Generic  | LBSCHEMA2.only_in_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."only_in_LBSCHEMA2" ADD CONSTRAINT "UQ_NAME" UNIQUE ("COLUMN_NAME")

# Test: "Can apply unique constraint with with various settings" #

- **connection:** generic[config:standard]

| Permutation | Verified | columns              | name    | table     | OPERATIONS
| :---------- | :------- | :------------------- | :------ | :-------- | :------
| 617950      | Generic  | COL_NAME             |         | LBSCHEMA  | **plan**: ALTER TABLE "LBSCHEMA" ADD CONSTRAINT UNIQUE ("COL_NAME")
| 066957      | Generic  | COL_NAME             |         | LBSCHEMA2 | **plan**: ALTER TABLE "LBSCHEMA2" ADD CONSTRAINT UNIQUE ("COL_NAME")
| 5b1afe      | Generic  | COL_NAME             | UQ_TEST | LBSCHEMA  | **plan**: ALTER TABLE "LBSCHEMA" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME")
| 3ba497      | Generic  | COL_NAME             | UQ_TEST | LBSCHEMA2 | **plan**: ALTER TABLE "LBSCHEMA2" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME")
| f43d4c      | Generic  | COL_NAME1, COL_NAME2 |         | LBSCHEMA  | **plan**: ALTER TABLE "LBSCHEMA" ADD CONSTRAINT UNIQUE ("COL_NAME1", "COL_NAME2")
| 7c779c      | Generic  | COL_NAME1, COL_NAME2 |         | LBSCHEMA2 | **plan**: ALTER TABLE "LBSCHEMA2" ADD CONSTRAINT UNIQUE ("COL_NAME1", "COL_NAME2")
| 2dc87c      | Generic  | COL_NAME1, COL_NAME2 | UQ_TEST | LBSCHEMA  | **plan**: ALTER TABLE "LBSCHEMA" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME1", "COL_NAME2")
| 189c17      | Generic  | COL_NAME1, COL_NAME2 | UQ_TEST | LBSCHEMA2 | **plan**: ALTER TABLE "LBSCHEMA2" ADD CONSTRAINT "UQ_TEST" UNIQUE ("COL_NAME1", "COL_NAME2")

# Test Version: "cd0cc8" #