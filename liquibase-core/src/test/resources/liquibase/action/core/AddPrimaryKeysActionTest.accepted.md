**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple primary keys at once" #

- **connection:** generic[config:standard]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 0f43a8      | Generic  | LBSCHEMA   | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE_1" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA"."TEST_TABLE_2" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA"."TEST_TABLE_3" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| eb7c69      | Generic  | LBSCHEMA2  | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE_1" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_2" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_3" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")

# Test: "Can apply primary key with with various settings" #

- **connection:** generic[config:standard]

| Permutation | Verified | clustered | columns  | direction | table                | tablespace | OPERATIONS
| :---------- | :------- | :-------- | :------- | :-------- | :------------------- | :--------- | :------
| 6f262c      | Generic  | false     | COL_NAME | ASC       | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" ASC)
| 7711e4      | Generic  | false     | COL_NAME | ASC       | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" ASC)
| d99100      | Generic  | false     | COL_NAME | DESC      | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" DESC)
| 2e7da3      | Generic  | false     | COL_NAME | DESC      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" DESC)
| cfecd9      | Generic  | false     | COL_NAME | null      | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| e5429e      | Generic  | false     | COL_NAME | null      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| b7c546      | Generic  | null      | COL_NAME | ASC       | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" ASC)
| b50d17      | Generic  | null      | COL_NAME | ASC       | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" ASC)
| 2a8b4b      | Generic  | null      | COL_NAME | DESC      | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" DESC)
| 3d781b      | Generic  | null      | COL_NAME | DESC      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" DESC)
| ba9e54      | Generic  | null      | COL_NAME | null      | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| cbe50f      | Generic  | null      | COL_NAME | null      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")

# Test: "Can apply single column with standard settings but complex PK names" #

- **connection:** generic[config:standard]

| Permutation | Verified | columns                         | pkName                         | tableName  | OPERATIONS
| :---------- | :------- | :------------------------------ | :----------------------------- | :--------- | :------
| bcc0a1      | Generic  | PrimaryKeyColumn{name=COL_NAME} | 4TEST_column                   | TABLE_NAME | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT "4TEST_column" PRIMARY KEY ("COL_NAME")
| db5ccf      | Generic  | PrimaryKeyColumn{name=COL_NAME} | 4test_column                   | TABLE_NAME | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT "4test_column" PRIMARY KEY ("COL_NAME")
| c1ea88      | Generic  | PrimaryKeyColumn{name=COL_NAME} | ANOTHERUPPERCOLUMN             | TABLE_NAME | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERCOLUMN" PRIMARY KEY ("COL_NAME")
| 8b3574      | Generic  | PrimaryKeyColumn{name=COL_NAME} | AnotherMixedColumn             | TABLE_NAME | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT "AnotherMixedColumn" PRIMARY KEY ("COL_NAME")
| ee2690      | Generic  | PrimaryKeyColumn{name=COL_NAME} | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | TABLE_NAME | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""COLUMN" PRIMARY KEY ("COL_NAME")
| 158c8c      | Generic  | PrimaryKeyColumn{name=COL_NAME} | MixedColumn                    | TABLE_NAME | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT "MixedColumn" PRIMARY KEY ("COL_NAME")
| 30b9f6      | Generic  | PrimaryKeyColumn{name=COL_NAME} | UPPERCOLUMN                    | TABLE_NAME | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT "UPPERCOLUMN" PRIMARY KEY ("COL_NAME")
| 6d1574      | Generic  | PrimaryKeyColumn{name=COL_NAME} | anotherlowercolumn             | TABLE_NAME | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT "anotherlowercolumn" PRIMARY KEY ("COL_NAME")
| 3b8b71      | Generic  | PrimaryKeyColumn{name=COL_NAME} | crazy!@#\$%^&*()_+{}[]'"column | TABLE_NAME | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""column" PRIMARY KEY ("COL_NAME")
| 0f44f3      | Generic  | PrimaryKeyColumn{name=COL_NAME} | lowercolumn                    | TABLE_NAME | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT "lowercolumn" PRIMARY KEY ("COL_NAME")
| 6596f0      | Generic  | PrimaryKeyColumn{name=COL_NAME} | only_in_LBSCHEMA               | TABLE_NAME | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT "only_in_LBSCHEMA" PRIMARY KEY ("COL_NAME")
| e95bef      | Generic  | PrimaryKeyColumn{name=COL_NAME} | only_in_LBSCHEMA2              | TABLE_NAME | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT "only_in_LBSCHEMA2" PRIMARY KEY ("COL_NAME")

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** generic[config:standard]

| Permutation | Verified | columnName                         | OPERATIONS
| :---------- | :------- | :--------------------------------- | :------
| e5925e      | Generic  | 4TEST_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("4TEST_primarykey")
| de5730      | Generic  | 4test_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("4test_primarykey")
| 21c106      | Generic  | ANOTHERUPPERPRIMARYKEY             | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("ANOTHERUPPERPRIMARYKEY")
| 6586f9      | Generic  | AnotherMixedPrimaryKey             | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("AnotherMixedPrimaryKey")
| 6c7438      | Generic  | CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("CRAZY!@#\$%^&*()_+{}[]'""PRIMARYKEY")
| 117124      | Generic  | MixedPrimaryKey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("MixedPrimaryKey")
| 51b3ef      | Generic  | UPPERPRIMARYKEY                    | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("UPPERPRIMARYKEY")
| f93629      | Generic  | anotherlowerprimarykey             | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("anotherlowerprimarykey")
| 5605ae      | Generic  | crazy!@#\$%^&*()_+{}[]'"primarykey | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("crazy!@#\$%^&*()_+{}[]'""primarykey")
| e04b28      | Generic  | lowerprimarykey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("lowerprimarykey")
| 329839      | Generic  | only_in_LBSCHEMA                   | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("only_in_LBSCHEMA")
| 86015f      | Generic  | only_in_LBSCHEMA2                  | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("only_in_LBSCHEMA2")

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** generic[config:standard]

| Permutation | Verified | tableName                               | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 6f10b3      | Generic  | LBSCHEMA.4TEST_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4TEST_table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 447604      | Generic  | LBSCHEMA.4test_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4test_table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 7d8923      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: ALTER TABLE "LBSCHEMA"."ANOTHERUPPERTABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 244240      | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: ALTER TABLE "LBSCHEMA"."AnotherMixedTable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| cf6e70      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 1ab5be      | Generic  | LBSCHEMA.MixedTable                     | **plan**: ALTER TABLE "LBSCHEMA"."MixedTable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| d62fef      | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: ALTER TABLE "LBSCHEMA"."UPPERTABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| b7bd07      | Generic  | LBSCHEMA.anotherlowertable              | **plan**: ALTER TABLE "LBSCHEMA"."anotherlowertable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 8d1056      | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| f03884      | Generic  | LBSCHEMA.lowertable                     | **plan**: ALTER TABLE "LBSCHEMA"."lowertable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| a69da2      | Generic  | LBSCHEMA.only_in_LBSCHEMA               | **plan**: ALTER TABLE "LBSCHEMA"."only_in_LBSCHEMA" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| a86d64      | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 590a6e      | Generic  | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 2927a8      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| ce1ca3      | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 710fa9      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 315ef1      | Generic  | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 33d15e      | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 22dc5a      | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| fbf4a4      | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 55094c      | Generic  | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| f90500      | Generic  | LBSCHEMA2.only_in_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."only_in_LBSCHEMA2" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")

# Test Version: "3b619a" #