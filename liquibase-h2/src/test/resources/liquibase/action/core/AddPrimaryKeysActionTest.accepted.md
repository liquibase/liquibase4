**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple primary keys at once" #

- **connection:** h2 standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 8caa09      | true     | LBSCHEMA2 | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE_1" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_2" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_3" ADD PRIMARY KEY ("COL_NAME")
| 63bb73      | true     | PUBLIC    | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE_1" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_2" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_3" ADD PRIMARY KEY ("COL_NAME")

# Test: "Can apply primary key with with various settings" #

- **connection:** h2 standard

| Permutation | Verified | clustered | columns  | direction | table                | tablespace | OPERATIONS
| :---------- | :------- | :-------- | :------- | :-------- | :------------------- | :--------- | :------
| 37c2bd      | true     | false     | COL_NAME | ASC       | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" ASC)
| 1c668b      | true     | false     | COL_NAME | ASC       | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" ASC)
| 1d0e99      | true     | false     | COL_NAME | DESC      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" DESC)
| 9518f8      | true     | false     | COL_NAME | DESC      | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" DESC)
| b7e78c      | true     | false     | COL_NAME | null      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME")
| 5ac801      | true     | false     | COL_NAME | null      | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME")
| a87521      | true     | null      | COL_NAME | ASC       | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" ASC)
| b07539      | true     | null      | COL_NAME | ASC       | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" ASC)
| fc9840      | true     | null      | COL_NAME | DESC      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" DESC)
| 0594ec      | true     | null      | COL_NAME | DESC      | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" DESC)
| c63c17      | true     | null      | COL_NAME | null      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME")
| 81a9bd      | true     | null      | COL_NAME | null      | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME")

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** h2 standard

| Permutation | Verified | column                             | OPERATIONS
| :---------- | :------- | :--------------------------------- | :------
| d8b229      | true     | 4TEST_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("4TEST_primarykey")
| 65a2d6      | true     | 4test_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("4test_primarykey")
| 517275      | true     | ANOTHERUPPERPRIMARYKEY             | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("ANOTHERUPPERPRIMARYKEY")
| dfe99c      | true     | AnotherMixedPrimaryKey             | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("AnotherMixedPrimaryKey")
| 7d7baf      | true     | CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("CRAZY!@#\$%^&*()_+{}[]'""PRIMARYKEY")
| 7c45ed      | true     | MixedPrimaryKey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("MixedPrimaryKey")
| 938da3      | true     | UPPERPRIMARYKEY                    | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("UPPERPRIMARYKEY")
| 6f9241      | true     | anotherlowerprimarykey             | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("anotherlowerprimarykey")
| d671c2      | true     | crazy!@#\$%^&*()_+{}[]'"primarykey | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("crazy!@#\$%^&*()_+{}[]'""primarykey")
| 749441      | true     | lowerprimarykey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("lowerprimarykey")

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** h2 standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 001676      | true     | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" ADD PRIMARY KEY ("COL_NAME")
| fe5689      | true     | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" ADD PRIMARY KEY ("COL_NAME")
| 791ecd      | true     | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| 945d60      | true     | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" ADD PRIMARY KEY ("COL_NAME")
| 644d6e      | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD PRIMARY KEY ("COL_NAME")
| e731b7      | true     | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" ADD PRIMARY KEY ("COL_NAME")
| e10b18      | true     | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" ADD PRIMARY KEY ("COL_NAME")
| 9dfc94      | true     | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| 416359      | true     | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" ADD PRIMARY KEY ("COL_NAME")
| 28ea09      | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" ADD PRIMARY KEY ("COL_NAME")
| 993cd6      | true     | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" ADD PRIMARY KEY ("COL_NAME")
| 9581d7      | true     | PUBLIC.4TEST_table                      | **plan**: ALTER TABLE "PUBLIC"."4TEST_table" ADD PRIMARY KEY ("COL_NAME")
| e34488      | true     | PUBLIC.4test_table                      | **plan**: ALTER TABLE "PUBLIC"."4test_table" ADD PRIMARY KEY ("COL_NAME")
| b08716      | true     | PUBLIC.ANOTHERUPPERTABLE                | **plan**: ALTER TABLE "PUBLIC"."ANOTHERUPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| 1d572d      | true     | PUBLIC.AnotherMixedTable                | **plan**: ALTER TABLE "PUBLIC"."AnotherMixedTable" ADD PRIMARY KEY ("COL_NAME")
| 69cf0e      | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE    | **plan**: ALTER TABLE "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD PRIMARY KEY ("COL_NAME")
| ec2093      | true     | PUBLIC.MixedTable                       | **plan**: ALTER TABLE "PUBLIC"."MixedTable" ADD PRIMARY KEY ("COL_NAME")
| 6e5bf8      | true     | PUBLIC.ONLY_IN_PUBLIC                   | **plan**: ALTER TABLE "PUBLIC"."ONLY_IN_PUBLIC" ADD PRIMARY KEY ("COL_NAME")
| 35a909      | true     | PUBLIC.UPPERTABLE                       | **plan**: ALTER TABLE "PUBLIC"."UPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| f79c86      | true     | PUBLIC.anotherlowertable                | **plan**: ALTER TABLE "PUBLIC"."anotherlowertable" ADD PRIMARY KEY ("COL_NAME")
| dc4e6f      | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table    | **plan**: ALTER TABLE "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""table" ADD PRIMARY KEY ("COL_NAME")
| df2559      | true     | PUBLIC.lowertable                       | **plan**: ALTER TABLE "PUBLIC"."lowertable" ADD PRIMARY KEY ("COL_NAME")

# Test Version: "a72945" #