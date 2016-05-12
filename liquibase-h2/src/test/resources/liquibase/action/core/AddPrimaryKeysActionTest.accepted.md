**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple primary keys at once" #

- **connection:** h2 standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 8caa091     | true     | LBSCHEMA2 | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE_1" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_2" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_3" ADD PRIMARY KEY ("COL_NAME")
| 63bb730     | true     | PUBLIC    | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE_1" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_2" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_3" ADD PRIMARY KEY ("COL_NAME")

# Test: "Can apply primary key with with various settings" #

- **connection:** h2 standard

| Permutation | Verified | clustered | columns  | direction | table                | tablespace | OPERATIONS
| :---------- | :------- | :-------- | :------- | :-------- | :------------------- | :--------- | :------
| 37c2bd2     | true     | false     | COL_NAME | ASC       | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" ASC)
| 1c668bf     | true     | false     | COL_NAME | ASC       | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" ASC)
| 1d0e99a     | true     | false     | COL_NAME | DESC      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" DESC)
| 9518f82     | true     | false     | COL_NAME | DESC      | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" DESC)
| b7e78cc     | true     | false     | COL_NAME | null      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME")
| 5ac801a     | true     | false     | COL_NAME | null      | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME")
| a875216     | true     | null      | COL_NAME | ASC       | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" ASC)
| b075397     | true     | null      | COL_NAME | ASC       | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" ASC)
| fc98408     | true     | null      | COL_NAME | DESC      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" DESC)
| 0594ecc     | true     | null      | COL_NAME | DESC      | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" DESC)
| c63c17e     | true     | null      | COL_NAME | null      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME")
| 81a9bd3     | true     | null      | COL_NAME | null      | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME")

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** h2 standard

| Permutation | Verified | column                             | OPERATIONS
| :---------- | :------- | :--------------------------------- | :------
| d8b2293     | true     | 4TEST_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("4TEST_primarykey")
| 65a2d64     | true     | 4test_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("4test_primarykey")
| 5172752     | true     | ANOTHERUPPERPRIMARYKEY             | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("ANOTHERUPPERPRIMARYKEY")
| dfe99cb     | true     | AnotherMixedPrimaryKey             | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("AnotherMixedPrimaryKey")
| 7d7bafe     | true     | CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("CRAZY!@#\$%^&*()_+{}[]'""PRIMARYKEY")
| 7c45ed5     | true     | MixedPrimaryKey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("MixedPrimaryKey")
| 938da3a     | true     | UPPERPRIMARYKEY                    | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("UPPERPRIMARYKEY")
| 6f9241e     | true     | anotherlowerprimarykey             | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("anotherlowerprimarykey")
| d671c2a     | true     | crazy!@#\$%^&*()_+{}[]'"primarykey | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("crazy!@#\$%^&*()_+{}[]'""primarykey")
| 7494411     | true     | lowerprimarykey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("lowerprimarykey")

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** h2 standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 0016761     | true     | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" ADD PRIMARY KEY ("COL_NAME")
| fe56897     | true     | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" ADD PRIMARY KEY ("COL_NAME")
| 791ecdc     | true     | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| 945d60b     | true     | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" ADD PRIMARY KEY ("COL_NAME")
| 644d6ec     | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD PRIMARY KEY ("COL_NAME")
| e731b70     | true     | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" ADD PRIMARY KEY ("COL_NAME")
| e10b18d     | true     | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" ADD PRIMARY KEY ("COL_NAME")
| 9dfc94b     | true     | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| 416359f     | true     | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" ADD PRIMARY KEY ("COL_NAME")
| 28ea09f     | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" ADD PRIMARY KEY ("COL_NAME")
| 993cd64     | true     | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" ADD PRIMARY KEY ("COL_NAME")
| 9581d79     | true     | PUBLIC.4TEST_table                      | **plan**: ALTER TABLE "PUBLIC"."4TEST_table" ADD PRIMARY KEY ("COL_NAME")
| e344883     | true     | PUBLIC.4test_table                      | **plan**: ALTER TABLE "PUBLIC"."4test_table" ADD PRIMARY KEY ("COL_NAME")
| b087168     | true     | PUBLIC.ANOTHERUPPERTABLE                | **plan**: ALTER TABLE "PUBLIC"."ANOTHERUPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| 1d572dc     | true     | PUBLIC.AnotherMixedTable                | **plan**: ALTER TABLE "PUBLIC"."AnotherMixedTable" ADD PRIMARY KEY ("COL_NAME")
| 69cf0ea     | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE    | **plan**: ALTER TABLE "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD PRIMARY KEY ("COL_NAME")
| ec2093e     | true     | PUBLIC.MixedTable                       | **plan**: ALTER TABLE "PUBLIC"."MixedTable" ADD PRIMARY KEY ("COL_NAME")
| 6e5bf84     | true     | PUBLIC.ONLY_IN_PUBLIC                   | **plan**: ALTER TABLE "PUBLIC"."ONLY_IN_PUBLIC" ADD PRIMARY KEY ("COL_NAME")
| 35a9095     | true     | PUBLIC.UPPERTABLE                       | **plan**: ALTER TABLE "PUBLIC"."UPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| f79c86b     | true     | PUBLIC.anotherlowertable                | **plan**: ALTER TABLE "PUBLIC"."anotherlowertable" ADD PRIMARY KEY ("COL_NAME")
| dc4e6fc     | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table    | **plan**: ALTER TABLE "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""table" ADD PRIMARY KEY ("COL_NAME")
| df2559a     | true     | PUBLIC.lowertable                       | **plan**: ALTER TABLE "PUBLIC"."lowertable" ADD PRIMARY KEY ("COL_NAME")

# Test Version: "c9fb43" #