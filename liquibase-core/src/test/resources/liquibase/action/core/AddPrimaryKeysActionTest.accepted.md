**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple primary keys at once" #

- **connection:** generic standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 6e63508     | Generic  | LBSCHEMA  | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE_1" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA"."TEST_TABLE_2" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA"."TEST_TABLE_3" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| bbb8e61     | Generic  | LBSCHEMA2 | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE_1" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_2" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_3" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")

# Test: "Can apply primary key with with various settings" #

- **connection:** generic standard

| Permutation | Verified | clustered | columns  | direction | table                | tablespace | OPERATIONS
| :---------- | :------- | :-------- | :------- | :-------- | :------------------- | :--------- | :------
| c203b49     | Generic  | false     | COL_NAME | ASC       | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" ASC)
| f8879b5     | Generic  | false     | COL_NAME | ASC       | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" ASC)
| 012f944     | Generic  | false     | COL_NAME | DESC      | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" DESC)
| 16a42c8     | Generic  | false     | COL_NAME | DESC      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" DESC)
| 38a3c2f     | Generic  | false     | COL_NAME | null      | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 1e6e4bf     | Generic  | false     | COL_NAME | null      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 7df0350     | Generic  | null      | COL_NAME | ASC       | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" ASC)
| 1cc9f5a     | Generic  | null      | COL_NAME | ASC       | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" ASC)
| 75e0d14     | Generic  | null      | COL_NAME | DESC      | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" DESC)
| 10de001     | Generic  | null      | COL_NAME | DESC      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" DESC)
| a369d7b     | Generic  | null      | COL_NAME | null      | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| c3256f3     | Generic  | null      | COL_NAME | null      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")

# Test: "Can apply single column with standard settings but complex PK names" #

- **connection:** generic standard

| Permutation | Verified | columns                         | pk                             | table                | OPERATIONS
| :---------- | :------- | :------------------------------ | :----------------------------- | :------------------- | :------
| 0a1040c     | Generic  | PrimaryKeyColumn{name=COL_NAME} | 4TEST_column                   | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "4TEST_column" PRIMARY KEY ("COL_NAME")
| 4b78f5e     | Generic  | PrimaryKeyColumn{name=COL_NAME} | 4TEST_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4TEST_column" PRIMARY KEY ("COL_NAME")
| 09c92ac     | Generic  | PrimaryKeyColumn{name=COL_NAME} | 4test_column                   | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "4test_column" PRIMARY KEY ("COL_NAME")
| 5e7bc8e     | Generic  | PrimaryKeyColumn{name=COL_NAME} | 4test_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4test_column" PRIMARY KEY ("COL_NAME")
| 9fea3b9     | Generic  | PrimaryKeyColumn{name=COL_NAME} | ANOTHERUPPERCOLUMN             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERCOLUMN" PRIMARY KEY ("COL_NAME")
| 5f1631d     | Generic  | PrimaryKeyColumn{name=COL_NAME} | ANOTHERUPPERCOLUMN             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERCOLUMN" PRIMARY KEY ("COL_NAME")
| fa70859     | Generic  | PrimaryKeyColumn{name=COL_NAME} | AnotherMixedColumn             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedColumn" PRIMARY KEY ("COL_NAME")
| 0627b95     | Generic  | PrimaryKeyColumn{name=COL_NAME} | AnotherMixedColumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedColumn" PRIMARY KEY ("COL_NAME")
| 8accd36     | Generic  | PrimaryKeyColumn{name=COL_NAME} | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""COLUMN" PRIMARY KEY ("COL_NAME")
| 1e9b93d     | Generic  | PrimaryKeyColumn{name=COL_NAME} | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""COLUMN" PRIMARY KEY ("COL_NAME")
| 8480cfe     | Generic  | PrimaryKeyColumn{name=COL_NAME} | MixedColumn                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "MixedColumn" PRIMARY KEY ("COL_NAME")
| 7e5f12c     | Generic  | PrimaryKeyColumn{name=COL_NAME} | MixedColumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "MixedColumn" PRIMARY KEY ("COL_NAME")
| 757101b     | Generic  | PrimaryKeyColumn{name=COL_NAME} | UPPERCOLUMN                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UPPERCOLUMN" PRIMARY KEY ("COL_NAME")
| 6e93398     | Generic  | PrimaryKeyColumn{name=COL_NAME} | UPPERCOLUMN                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UPPERCOLUMN" PRIMARY KEY ("COL_NAME")
| b6d129a     | Generic  | PrimaryKeyColumn{name=COL_NAME} | anotherlowercolumn             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "anotherlowercolumn" PRIMARY KEY ("COL_NAME")
| 29c346c     | Generic  | PrimaryKeyColumn{name=COL_NAME} | anotherlowercolumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "anotherlowercolumn" PRIMARY KEY ("COL_NAME")
| bb73640     | Generic  | PrimaryKeyColumn{name=COL_NAME} | crazy!@#\$%^&*()_+{}[]'"column | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""column" PRIMARY KEY ("COL_NAME")
| 65bcc7d     | Generic  | PrimaryKeyColumn{name=COL_NAME} | crazy!@#\$%^&*()_+{}[]'"column | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""column" PRIMARY KEY ("COL_NAME")
| 1853866     | Generic  | PrimaryKeyColumn{name=COL_NAME} | lowercolumn                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "lowercolumn" PRIMARY KEY ("COL_NAME")
| 769daff     | Generic  | PrimaryKeyColumn{name=COL_NAME} | lowercolumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "lowercolumn" PRIMARY KEY ("COL_NAME")

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** generic standard

| Permutation | Verified | column                             | OPERATIONS
| :---------- | :------- | :--------------------------------- | :------
| f5368ba     | Generic  | 4TEST_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("4TEST_primarykey")
| 2c8cd01     | Generic  | 4test_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("4test_primarykey")
| a45b37d     | Generic  | ANOTHERUPPERPRIMARYKEY             | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("ANOTHERUPPERPRIMARYKEY")
| 48d937f     | Generic  | AnotherMixedPrimaryKey             | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("AnotherMixedPrimaryKey")
| 6f2926e     | Generic  | CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("CRAZY!@#\$%^&*()_+{}[]'""PRIMARYKEY")
| b9b4184     | Generic  | MixedPrimaryKey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("MixedPrimaryKey")
| e68311c     | Generic  | UPPERPRIMARYKEY                    | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("UPPERPRIMARYKEY")
| 6f451ac     | Generic  | anotherlowerprimarykey             | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("anotherlowerprimarykey")
| cca71b1     | Generic  | crazy!@#\$%^&*()_+{}[]'"primarykey | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("crazy!@#\$%^&*()_+{}[]'""primarykey")
| 8e6a23b     | Generic  | lowerprimarykey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("lowerprimarykey")

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** generic standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 82ca36a     | Generic  | LBSCHEMA.4TEST_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4TEST_table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| a59cadd     | Generic  | LBSCHEMA.4test_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4test_table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 02109bf     | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: ALTER TABLE "LBSCHEMA"."ANOTHERUPPERTABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 0556eae     | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: ALTER TABLE "LBSCHEMA"."AnotherMixedTable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 2cbd26a     | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 6b38df5     | Generic  | LBSCHEMA.MixedTable                     | **plan**: ALTER TABLE "LBSCHEMA"."MixedTable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 7636549     | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: ALTER TABLE "LBSCHEMA"."ONLY_IN_LBSCHEMA" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 8f18146     | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: ALTER TABLE "LBSCHEMA"."UPPERTABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 5c86256     | Generic  | LBSCHEMA.anotherlowertable              | **plan**: ALTER TABLE "LBSCHEMA"."anotherlowertable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 86a5d18     | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| c4dbeb3     | Generic  | LBSCHEMA.lowertable                     | **plan**: ALTER TABLE "LBSCHEMA"."lowertable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 31f94ac     | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 13e76ef     | Generic  | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| cec2e1c     | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| fe461f7     | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| dec400d     | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| cf72a30     | Generic  | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 6e579ae     | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| d5eec30     | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 00b229a     | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| b875ac8     | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 5921e4d     | Generic  | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")

# Test Version: "083525" #