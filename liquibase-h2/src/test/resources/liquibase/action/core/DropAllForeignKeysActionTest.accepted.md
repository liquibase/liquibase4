**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can execute everything from createAllActionPermutations" #

- **connection:** h2 standard

| Permutation | Verified | table                                   | OPERATIONS
| :---------- | :------- | :-------------------------------------- | :------
| 0016761     | true     | LBSCHEMA2.4TEST_table                   | **plan**: dropAllForeignKeys(table=LBSCHEMA2.4TEST_table)
| fe56897     | true     | LBSCHEMA2.4test_table                   | **plan**: dropAllForeignKeys(table=LBSCHEMA2.4test_table)
| 791ecdc     | true     | LBSCHEMA2.ANOTHERUPPERTABLE             | **plan**: dropAllForeignKeys(table=LBSCHEMA2.ANOTHERUPPERTABLE)
| 945d60b     | true     | LBSCHEMA2.AnotherMixedTable             | **plan**: dropAllForeignKeys(table=LBSCHEMA2.AnotherMixedTable)
| 644d6ec     | true     | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE | **plan**: dropAllForeignKeys(table=LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| e731b70     | true     | LBSCHEMA2.MixedTable                    | **plan**: dropAllForeignKeys(table=LBSCHEMA2.MixedTable)
| e10b18d     | true     | LBSCHEMA2.ONLY_IN_LBSCHEMA2             | **plan**: dropAllForeignKeys(table=LBSCHEMA2.ONLY_IN_LBSCHEMA2)
| 9dfc94b     | true     | LBSCHEMA2.UPPERTABLE                    | **plan**: dropAllForeignKeys(table=LBSCHEMA2.UPPERTABLE)
| 416359f     | true     | LBSCHEMA2.anotherlowertable             | **plan**: dropAllForeignKeys(table=LBSCHEMA2.anotherlowertable)
| 28ea09f     | true     | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: dropAllForeignKeys(table=LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"table)
| 993cd64     | true     | LBSCHEMA2.lowertable                    | **plan**: dropAllForeignKeys(table=LBSCHEMA2.lowertable)
| 9581d79     | true     | PUBLIC.4TEST_table                      | **plan**: dropAllForeignKeys(table=PUBLIC.4TEST_table)
| e344883     | true     | PUBLIC.4test_table                      | **plan**: dropAllForeignKeys(table=PUBLIC.4test_table)
| b087168     | true     | PUBLIC.ANOTHERUPPERTABLE                | **plan**: dropAllForeignKeys(table=PUBLIC.ANOTHERUPPERTABLE)
| 1d572dc     | true     | PUBLIC.AnotherMixedTable                | **plan**: dropAllForeignKeys(table=PUBLIC.AnotherMixedTable)
| 69cf0ea     | true     | PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE    | **plan**: dropAllForeignKeys(table=PUBLIC.CRAZY!@#\$%^&*()_+{}[]'"TABLE)
| ec2093e     | true     | PUBLIC.MixedTable                       | **plan**: dropAllForeignKeys(table=PUBLIC.MixedTable)
| 6e5bf84     | true     | PUBLIC.ONLY_IN_PUBLIC                   | **plan**: dropAllForeignKeys(table=PUBLIC.ONLY_IN_PUBLIC)
| 35a9095     | true     | PUBLIC.UPPERTABLE                       | **plan**: dropAllForeignKeys(table=PUBLIC.UPPERTABLE)
| f79c86b     | true     | PUBLIC.anotherlowertable                | **plan**: dropAllForeignKeys(table=PUBLIC.anotherlowertable)
| dc4e6fc     | true     | PUBLIC.crazy!@#\$%^&*()_+{}[]'"table    | **plan**: dropAllForeignKeys(table=PUBLIC.crazy!@#\$%^&*()_+{}[]'"table)
| df2559a     | true     | PUBLIC.lowertable                       | **plan**: dropAllForeignKeys(table=PUBLIC.lowertable)

# Test Version: "4d3e2f" #