**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple primary keys at once" #

- **connection:** generic standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 6e6350      | Generic  | LBSCHEMA  | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE_1" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA"."TEST_TABLE_2" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA"."TEST_TABLE_3" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| bbb8e6      | Generic  | LBSCHEMA2 | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE_1" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_2" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_3" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")

# Test: "Can apply primary key with with various settings" #

- **connection:** generic standard

| Permutation | Verified | clustered | columns  | direction | table                | tablespace | OPERATIONS
| :---------- | :------- | :-------- | :------- | :-------- | :------------------- | :--------- | :------
| c203b4      | Generic  | false     | COL_NAME | ASC       | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" ASC)
| f8879b      | Generic  | false     | COL_NAME | ASC       | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" ASC)
| 012f94      | Generic  | false     | COL_NAME | DESC      | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" DESC)
| 16a42c      | Generic  | false     | COL_NAME | DESC      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" DESC)
| 38a3c2      | Generic  | false     | COL_NAME | null      | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 1e6e4b      | Generic  | false     | COL_NAME | null      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 7df035      | Generic  | null      | COL_NAME | ASC       | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" ASC)
| 1cc9f5      | Generic  | null      | COL_NAME | ASC       | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" ASC)
| 75e0d1      | Generic  | null      | COL_NAME | DESC      | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" DESC)
| 10de00      | Generic  | null      | COL_NAME | DESC      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME" DESC)
| a369d7      | Generic  | null      | COL_NAME | null      | LBSCHEMA.TEST_TABLE  | null       | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| c3256f      | Generic  | null      | COL_NAME | null      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")

# Test: "Can apply single column with standard settings but complex PK names" #

- **connection:** generic standard

| Permutation | Verified | columns                         | pk                             | table                | OPERATIONS
| :---------- | :------- | :------------------------------ | :----------------------------- | :------------------- | :------
| 0a1040      | Generic  | PrimaryKeyColumn{name=COL_NAME} | 4TEST_column                   | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "4TEST_column" PRIMARY KEY ("COL_NAME")
| 4b78f5      | Generic  | PrimaryKeyColumn{name=COL_NAME} | 4TEST_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4TEST_column" PRIMARY KEY ("COL_NAME")
| 09c92a      | Generic  | PrimaryKeyColumn{name=COL_NAME} | 4test_column                   | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "4test_column" PRIMARY KEY ("COL_NAME")
| 5e7bc8      | Generic  | PrimaryKeyColumn{name=COL_NAME} | 4test_column                   | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "4test_column" PRIMARY KEY ("COL_NAME")
| 9fea3b      | Generic  | PrimaryKeyColumn{name=COL_NAME} | ANOTHERUPPERCOLUMN             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERCOLUMN" PRIMARY KEY ("COL_NAME")
| 5f1631      | Generic  | PrimaryKeyColumn{name=COL_NAME} | ANOTHERUPPERCOLUMN             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "ANOTHERUPPERCOLUMN" PRIMARY KEY ("COL_NAME")
| fa7085      | Generic  | PrimaryKeyColumn{name=COL_NAME} | AnotherMixedColumn             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedColumn" PRIMARY KEY ("COL_NAME")
| 0627b9      | Generic  | PrimaryKeyColumn{name=COL_NAME} | AnotherMixedColumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "AnotherMixedColumn" PRIMARY KEY ("COL_NAME")
| 8accd3      | Generic  | PrimaryKeyColumn{name=COL_NAME} | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""COLUMN" PRIMARY KEY ("COL_NAME")
| 1e9b93      | Generic  | PrimaryKeyColumn{name=COL_NAME} | CRAZY!@#\$%^&*()_+{}[]'"COLUMN | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "CRAZY!@#\$%^&*()_+{}[]'""COLUMN" PRIMARY KEY ("COL_NAME")
| 8480cf      | Generic  | PrimaryKeyColumn{name=COL_NAME} | MixedColumn                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "MixedColumn" PRIMARY KEY ("COL_NAME")
| 7e5f12      | Generic  | PrimaryKeyColumn{name=COL_NAME} | MixedColumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "MixedColumn" PRIMARY KEY ("COL_NAME")
| 757101      | Generic  | PrimaryKeyColumn{name=COL_NAME} | UPPERCOLUMN                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "UPPERCOLUMN" PRIMARY KEY ("COL_NAME")
| 6e9339      | Generic  | PrimaryKeyColumn{name=COL_NAME} | UPPERCOLUMN                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "UPPERCOLUMN" PRIMARY KEY ("COL_NAME")
| b6d129      | Generic  | PrimaryKeyColumn{name=COL_NAME} | anotherlowercolumn             | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "anotherlowercolumn" PRIMARY KEY ("COL_NAME")
| 29c346      | Generic  | PrimaryKeyColumn{name=COL_NAME} | anotherlowercolumn             | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "anotherlowercolumn" PRIMARY KEY ("COL_NAME")
| bb7364      | Generic  | PrimaryKeyColumn{name=COL_NAME} | crazy!@#\$%^&*()_+{}[]'"column | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""column" PRIMARY KEY ("COL_NAME")
| 65bcc7      | Generic  | PrimaryKeyColumn{name=COL_NAME} | crazy!@#\$%^&*()_+{}[]'"column | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "crazy!@#\$%^&*()_+{}[]'""column" PRIMARY KEY ("COL_NAME")
| 185386      | Generic  | PrimaryKeyColumn{name=COL_NAME} | lowercolumn                    | LBSCHEMA.TABLE_NAME  | **plan**: ALTER TABLE "LBSCHEMA"."TABLE_NAME" ADD CONSTRAINT "lowercolumn" PRIMARY KEY ("COL_NAME")
| 769daf      | Generic  | PrimaryKeyColumn{name=COL_NAME} | lowercolumn                    | LBSCHEMA2.TABLE_NAME | **plan**: ALTER TABLE "LBSCHEMA2"."TABLE_NAME" ADD CONSTRAINT "lowercolumn" PRIMARY KEY ("COL_NAME")

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** generic standard

| Permutation | Verified | column                             | OPERATIONS
| :---------- | :------- | :--------------------------------- | :------
| f5368b      | Generic  | 4TEST_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("4TEST_primarykey")
| 2c8cd0      | Generic  | 4test_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("4test_primarykey")
| a45b37      | Generic  | ANOTHERUPPERPRIMARYKEY             | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("ANOTHERUPPERPRIMARYKEY")
| 48d937      | Generic  | AnotherMixedPrimaryKey             | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("AnotherMixedPrimaryKey")
| 6f2926      | Generic  | CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("CRAZY!@#\$%^&*()_+{}[]'""PRIMARYKEY")
| b9b418      | Generic  | MixedPrimaryKey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("MixedPrimaryKey")
| e68311      | Generic  | UPPERPRIMARYKEY                    | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("UPPERPRIMARYKEY")
| 6f451a      | Generic  | anotherlowerprimarykey             | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("anotherlowerprimarykey")
| cca71b      | Generic  | crazy!@#\$%^&*()_+{}[]'"primarykey | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("crazy!@#\$%^&*()_+{}[]'""primarykey")
| 8e6a23      | Generic  | lowerprimarykey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD CONSTRAINT PRIMARY KEY ("lowerprimarykey")

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** generic standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 82ca36      | Generic  | LBSCHEMA.4TEST_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4TEST_table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| a59cad      | Generic  | LBSCHEMA.4test_table                    | **plan**: ALTER TABLE "LBSCHEMA"."4test_table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 02109b      | Generic  | LBSCHEMA.ANOTHERUPPERTABLE              | **plan**: ALTER TABLE "LBSCHEMA"."ANOTHERUPPERTABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 0556ea      | Generic  | LBSCHEMA.AnotherMixedTable              | **plan**: ALTER TABLE "LBSCHEMA"."AnotherMixedTable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 2cbd26      | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"TABLE  | **plan**: ALTER TABLE "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 6b38df      | Generic  | LBSCHEMA.MixedTable                     | **plan**: ALTER TABLE "LBSCHEMA"."MixedTable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 763654      | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA               | **plan**: ALTER TABLE "LBSCHEMA"."ONLY_IN_LBSCHEMA" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 8f1814      | Generic  | LBSCHEMA.UPPERTABLE                     | **plan**: ALTER TABLE "LBSCHEMA"."UPPERTABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 5c8625      | Generic  | LBSCHEMA.anotherlowertable              | **plan**: ALTER TABLE "LBSCHEMA"."anotherlowertable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 86a5d1      | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| c4dbeb      | Generic  | LBSCHEMA.lowertable                     | **plan**: ALTER TABLE "LBSCHEMA"."lowertable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 31f94a      | Generic  | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 13e76e      | Generic  | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| cec2e1      | Generic  | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| fe461f      | Generic  | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| dec400      | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| cf72a3      | Generic  | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 6e579a      | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| d5eec3      | Generic  | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 00b229      | Generic  | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| b875ac      | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| 5921e4      | Generic  | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")

# Test Version: "04f042" #