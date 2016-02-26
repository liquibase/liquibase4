**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple primary keys at once" #

- **connection:** h2[config:standard]

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| c0dc64      | true     | LBSCHEMA2 | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE_1" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_2" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_3" ADD PRIMARY KEY ("COL_NAME")
| 99ef3c      | true     | PUBLIC    | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE_1" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_2" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_3" ADD PRIMARY KEY ("COL_NAME")

# Test: "Can apply primary key with with various settings" #

- **connection:** h2[config:standard]

| Permutation | Verified | clustered | columns  | direction | table                | tablespace | OPERATIONS
| :---------- | :------- | :-------- | :------- | :-------- | :------------------- | :--------- | :------
| 16df11      | true     | false     | COL_NAME | ASC       | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" ASC)
| 82cc83      | true     | false     | COL_NAME | ASC       | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" ASC)
| 5b0096      | true     | false     | COL_NAME | DESC      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" DESC)
| 6f263a      | true     | false     | COL_NAME | DESC      | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" DESC)
| 43f678      | true     | false     | COL_NAME | null      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME")
| b55bb2      | true     | false     | COL_NAME | null      | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME")
| 2cccda      | true     | null      | COL_NAME | ASC       | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" ASC)
| 77e5ea      | true     | null      | COL_NAME | ASC       | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" ASC)
| 6e5192      | true     | null      | COL_NAME | DESC      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" DESC)
| 0850e5      | true     | null      | COL_NAME | DESC      | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME" DESC)
| dbb99c      | true     | null      | COL_NAME | null      | LBSCHEMA2.TEST_TABLE | null       | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME")
| 5b7de2      | true     | null      | COL_NAME | null      | PUBLIC.TEST_TABLE    | null       | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE" ADD PRIMARY KEY ("COL_NAME")

# Test: "Can apply single column with standard settings but complex column names" #

- **connection:** h2[config:standard]

| Permutation | Verified | column                             | OPERATIONS
| :---------- | :------- | :--------------------------------- | :------
| 5be077      | true     | 4TEST_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("4TEST_primarykey")
| 9e74e6      | true     | 4test_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("4test_primarykey")
| 29efa5      | true     | ANOTHERUPPERPRIMARYKEY             | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("ANOTHERUPPERPRIMARYKEY")
| e6cb3f      | true     | AnotherMixedPrimaryKey             | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("AnotherMixedPrimaryKey")
| 897cf7      | true     | CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("CRAZY!@#\$%^&*()_+{}[]'""PRIMARYKEY")
| 62927f      | true     | MixedPrimaryKey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("MixedPrimaryKey")
| 19963d      | true     | UPPERPRIMARYKEY                    | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("UPPERPRIMARYKEY")
| f0150a      | true     | anotherlowerprimarykey             | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("anotherlowerprimarykey")
| a5f871      | true     | crazy!@#\$%^&*()_+{}[]'"primarykey | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("crazy!@#\$%^&*()_+{}[]'""primarykey")
| 16aeb7      | true     | lowerprimarykey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("lowerprimarykey")

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** h2[config:standard]

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 9ce697      | true     | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" ADD PRIMARY KEY ("COL_NAME")
| 05440c      | true     | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" ADD PRIMARY KEY ("COL_NAME")
| e47136      | true     | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| 25cd1a      | true     | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" ADD PRIMARY KEY ("COL_NAME")
| d891cc      | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD PRIMARY KEY ("COL_NAME")
| 3a440d      | true     | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" ADD PRIMARY KEY ("COL_NAME")
| b5b886      | true     | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."ONLY_IN_LBSCHEMA2" ADD PRIMARY KEY ("COL_NAME")
| b316c7      | true     | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| 4649b2      | true     | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" ADD PRIMARY KEY ("COL_NAME")
| e67874      | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" ADD PRIMARY KEY ("COL_NAME")
| 3847ea      | true     | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" ADD PRIMARY KEY ("COL_NAME")
| f3d962      | true     | PUBLIC.4TEST_table                      | **plan**: ALTER TABLE "PUBLIC"."4TEST_table" ADD PRIMARY KEY ("COL_NAME")
| dfc1d8      | true     | PUBLIC.4test_table                      | **plan**: ALTER TABLE "PUBLIC"."4test_table" ADD PRIMARY KEY ("COL_NAME")
| 27d4d5      | true     | PUBLIC.ANOTHERUPPERTABLE                | **plan**: ALTER TABLE "PUBLIC"."ANOTHERUPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| 846368      | true     | PUBLIC.AnotherMixedTable                | **plan**: ALTER TABLE "PUBLIC"."AnotherMixedTable" ADD PRIMARY KEY ("COL_NAME")
| bdcb41      | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE    | **plan**: ALTER TABLE "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD PRIMARY KEY ("COL_NAME")
| 7bd544      | true     | PUBLIC.MixedTable                       | **plan**: ALTER TABLE "PUBLIC"."MixedTable" ADD PRIMARY KEY ("COL_NAME")
| 5e44e6      | true     | PUBLIC.ONLY_IN_PUBLIC                   | **plan**: ALTER TABLE "PUBLIC"."ONLY_IN_PUBLIC" ADD PRIMARY KEY ("COL_NAME")
| 09c0e6      | true     | PUBLIC.UPPERTABLE                       | **plan**: ALTER TABLE "PUBLIC"."UPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| 7aafe6      | true     | PUBLIC.anotherlowertable                | **plan**: ALTER TABLE "PUBLIC"."anotherlowertable" ADD PRIMARY KEY ("COL_NAME")
| 230483      | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table    | **plan**: ALTER TABLE "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""table" ADD PRIMARY KEY ("COL_NAME")
| b8d606      | true     | PUBLIC.lowertable                       | **plan**: ALTER TABLE "PUBLIC"."lowertable" ADD PRIMARY KEY ("COL_NAME")

# Test Version: "036c44" #