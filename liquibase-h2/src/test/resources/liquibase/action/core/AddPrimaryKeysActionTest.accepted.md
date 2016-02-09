**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can add multiple primary keys at once" #

- **connection:** h2[config:standard]

| Permutation | Verified | schemaName | OPERATIONS
| :---------- | :------- | :--------- | :------
| 679812      | true     | LBSCHEMA2  | **plan**: ALTER TABLE "LBSCHEMA2"."TEST_TABLE_1" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_2" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "LBSCHEMA2"."TEST_TABLE_3" ADD PRIMARY KEY ("COL_NAME")
| bd13a9      | true     | PUBLIC     | **plan**: ALTER TABLE "PUBLIC"."TEST_TABLE_1" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_2" ADD PRIMARY KEY ("COL_NAME")<br>ALTER TABLE "PUBLIC"."TEST_TABLE_3" ADD PRIMARY KEY ("COL_NAME")

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

| Permutation | Verified | columnName                         | OPERATIONS
| :---------- | :------- | :--------------------------------- | :------
| 7b4fc7      | true     | 4TEST_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("4TEST_primarykey")
| 967da7      | true     | 4test_primarykey                   | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("4test_primarykey")
| 19a22b      | true     | ANOTHERUPPERPRIMARYKEY             | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("ANOTHERUPPERPRIMARYKEY")
| c765e3      | true     | AnotherMixedPrimaryKey             | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("AnotherMixedPrimaryKey")
| c8deb7      | true     | CRAZY!@#\$%^&*()_+{}[]'"PRIMARYKEY | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("CRAZY!@#\$%^&*()_+{}[]'""PRIMARYKEY")
| 751276      | true     | MixedPrimaryKey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("MixedPrimaryKey")
| 3b01a3      | true     | UPPERPRIMARYKEY                    | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("UPPERPRIMARYKEY")
| b49cde      | true     | anotherlowerprimarykey             | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("anotherlowerprimarykey")
| 443ee7      | true     | crazy!@#\$%^&*()_+{}[]'"primarykey | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("crazy!@#\$%^&*()_+{}[]'""primarykey")
| cb1693      | true     | lowerprimarykey                    | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("lowerprimarykey")
| a29cbb      | true     | only_in_LBSCHEMA2                  | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("only_in_LBSCHEMA2")
| be3365      | true     | only_in_PUBLIC                     | **plan**: ALTER TABLE "TABLE_NAME" ADD PRIMARY KEY ("only_in_PUBLIC")

# Test: "Can apply single column with standard settings but complex table names" #

- **connection:** h2[config:standard]

| Permutation | Verified | tableName                               | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 040a4c      | true     | LBSCHEMA2.4TEST_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4TEST_table" ADD PRIMARY KEY ("COL_NAME")
| 64232c      | true     | LBSCHEMA2.4test_table                   | **plan**: ALTER TABLE "LBSCHEMA2"."4test_table" ADD PRIMARY KEY ("COL_NAME")
| 7235ec      | true     | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: ALTER TABLE "LBSCHEMA2"."ANOTHERUPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| f90bc6      | true     | LBSCHEMA2.AnotherMixedTable             | **plan**: ALTER TABLE "LBSCHEMA2"."AnotherMixedTable" ADD PRIMARY KEY ("COL_NAME")
| 0d533f      | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: ALTER TABLE "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD PRIMARY KEY ("COL_NAME")
| 6dbf8b      | true     | LBSCHEMA2.MixedTable                    | **plan**: ALTER TABLE "LBSCHEMA2"."MixedTable" ADD PRIMARY KEY ("COL_NAME")
| a92dd5      | true     | LBSCHEMA2.UPPERTABLE                    | **plan**: ALTER TABLE "LBSCHEMA2"."UPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| 6d7bef      | true     | LBSCHEMA2.anotherlowertable             | **plan**: ALTER TABLE "LBSCHEMA2"."anotherlowertable" ADD PRIMARY KEY ("COL_NAME")
| 62cf87      | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""table" ADD PRIMARY KEY ("COL_NAME")
| e77a61      | true     | LBSCHEMA2.lowertable                    | **plan**: ALTER TABLE "LBSCHEMA2"."lowertable" ADD PRIMARY KEY ("COL_NAME")
| 730360      | true     | LBSCHEMA2.only_in_LBSCHEMA2             | **plan**: ALTER TABLE "LBSCHEMA2"."only_in_LBSCHEMA2" ADD PRIMARY KEY ("COL_NAME")
| 99f863      | true     | PUBLIC.4TEST_table                      | **plan**: ALTER TABLE "PUBLIC"."4TEST_table" ADD PRIMARY KEY ("COL_NAME")
| 4486fd      | true     | PUBLIC.4test_table                      | **plan**: ALTER TABLE "PUBLIC"."4test_table" ADD PRIMARY KEY ("COL_NAME")
| 443117      | true     | PUBLIC.ANOTHERUPPERTABLE                | **plan**: ALTER TABLE "PUBLIC"."ANOTHERUPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| 78e225      | true     | PUBLIC.AnotherMixedTable                | **plan**: ALTER TABLE "PUBLIC"."AnotherMixedTable" ADD PRIMARY KEY ("COL_NAME")
| a17aa4      | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE    | **plan**: ALTER TABLE "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""TABLE" ADD PRIMARY KEY ("COL_NAME")
| 6a8c10      | true     | PUBLIC.MixedTable                       | **plan**: ALTER TABLE "PUBLIC"."MixedTable" ADD PRIMARY KEY ("COL_NAME")
| 42126b      | true     | PUBLIC.UPPERTABLE                       | **plan**: ALTER TABLE "PUBLIC"."UPPERTABLE" ADD PRIMARY KEY ("COL_NAME")
| 840c9d      | true     | PUBLIC.anotherlowertable                | **plan**: ALTER TABLE "PUBLIC"."anotherlowertable" ADD PRIMARY KEY ("COL_NAME")
| 861913      | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table    | **plan**: ALTER TABLE "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""table" ADD PRIMARY KEY ("COL_NAME")
| b68a2e      | true     | PUBLIC.lowertable                       | **plan**: ALTER TABLE "PUBLIC"."lowertable" ADD PRIMARY KEY ("COL_NAME")
| f6a54f      | true     | PUBLIC.only_in_PUBLIC                   | **plan**: ALTER TABLE "PUBLIC"."only_in_PUBLIC" ADD PRIMARY KEY ("COL_NAME")

# Test Version: "5c46cc" #