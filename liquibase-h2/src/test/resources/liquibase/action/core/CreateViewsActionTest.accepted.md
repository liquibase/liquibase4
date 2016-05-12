**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can add columns with all permutations" #

- **connection:** h2 standard

| Permutation | Verified | completeDefinition | definition                         | name                         | schema    | OPERATIONS
| :---------- | :------- | :----------------- | :--------------------------------- | :--------------------------- | :-------- | :------
| a492f13     | true     | null               | select * from LBSCHEMA2.test_table | 4TEST_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4TEST_view" AS select * from LBSCHEMA2.test_table
| 8ca1374     | true     | null               | select * from LBSCHEMA2.test_table | 4test_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4test_view" AS select * from LBSCHEMA2.test_table
| e6b8300     | true     | null               | select * from LBSCHEMA2.test_table | ANOTHERUPPERVIEW             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."ANOTHERUPPERVIEW" AS select * from LBSCHEMA2.test_table
| 6aa29ed     | true     | null               | select * from LBSCHEMA2.test_table | AnotherMixedView             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."AnotherMixedView" AS select * from LBSCHEMA2.test_table
| 6af656d     | true     | null               | select * from LBSCHEMA2.test_table | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from LBSCHEMA2.test_table
| 4762fe5     | true     | null               | select * from LBSCHEMA2.test_table | MixedView                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."MixedView" AS select * from LBSCHEMA2.test_table
| 0ee881b     | true     | null               | select * from LBSCHEMA2.test_table | UPPERVIEW                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."UPPERVIEW" AS select * from LBSCHEMA2.test_table
| ff99534     | true     | null               | select * from LBSCHEMA2.test_table | anotherlowerview             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."anotherlowerview" AS select * from LBSCHEMA2.test_table
| c0ff408     | true     | null               | select * from LBSCHEMA2.test_table | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from LBSCHEMA2.test_table
| 1403691     | true     | null               | select * from LBSCHEMA2.test_table | lowerview                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."lowerview" AS select * from LBSCHEMA2.test_table
| 4897660     | true     | null               | select * from PUBLIC.test_table    | 4TEST_view                   | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."4TEST_view" AS select * from PUBLIC.test_table
| ee3dc35     | true     | null               | select * from PUBLIC.test_table    | 4test_view                   | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."4test_view" AS select * from PUBLIC.test_table
| a5f2ac5     | true     | null               | select * from PUBLIC.test_table    | ANOTHERUPPERVIEW             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."ANOTHERUPPERVIEW" AS select * from PUBLIC.test_table
| efd1c5a     | true     | null               | select * from PUBLIC.test_table    | AnotherMixedView             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."AnotherMixedView" AS select * from PUBLIC.test_table
| afbb8db     | true     | null               | select * from PUBLIC.test_table    | CRAZY!@#\$%^&*()_+{}[]'"VIEW | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from PUBLIC.test_table
| b758257     | true     | null               | select * from PUBLIC.test_table    | MixedView                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."MixedView" AS select * from PUBLIC.test_table
| 06739fa     | true     | null               | select * from PUBLIC.test_table    | UPPERVIEW                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."UPPERVIEW" AS select * from PUBLIC.test_table
| 6895b44     | true     | null               | select * from PUBLIC.test_table    | anotherlowerview             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."anotherlowerview" AS select * from PUBLIC.test_table
| e81e4d1     | true     | null               | select * from PUBLIC.test_table    | crazy!@#\$%^&*()_+{}[]'"view | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from PUBLIC.test_table
| 5be1f17     | true     | null               | select * from PUBLIC.test_table    | lowerview                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."lowerview" AS select * from PUBLIC.test_table
| adcd9cd     | true     | null               | select * from test_table           | 4TEST_view                   | null      | **plan**: CREATE VIEW "4TEST_view" AS select * from test_table
| 7ef7ae9     | true     | null               | select * from test_table           | 4test_view                   | null      | **plan**: CREATE VIEW "4test_view" AS select * from test_table
| ffb4cfe     | true     | null               | select * from test_table           | ANOTHERUPPERVIEW             | null      | **plan**: CREATE VIEW "ANOTHERUPPERVIEW" AS select * from test_table
| 32839e8     | true     | null               | select * from test_table           | AnotherMixedView             | null      | **plan**: CREATE VIEW "AnotherMixedView" AS select * from test_table
| 2454506     | true     | null               | select * from test_table           | CRAZY!@#\$%^&*()_+{}[]'"VIEW | null      | **plan**: CREATE VIEW "CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from test_table
| b9bf66d     | true     | null               | select * from test_table           | MixedView                    | null      | **plan**: CREATE VIEW "MixedView" AS select * from test_table
| 2e9cc1f     | true     | null               | select * from test_table           | UPPERVIEW                    | null      | **plan**: CREATE VIEW "UPPERVIEW" AS select * from test_table
| 585443e     | true     | null               | select * from test_table           | anotherlowerview             | null      | **plan**: CREATE VIEW "anotherlowerview" AS select * from test_table
| af62f16     | true     | null               | select * from test_table           | crazy!@#\$%^&*()_+{}[]'"view | null      | **plan**: CREATE VIEW "crazy!@#\$%^&*()_+{}[]'""view" AS select * from test_table
| b167800     | true     | null               | select * from test_table           | lowerview                    | null      | **plan**: CREATE VIEW "lowerview" AS select * from test_table

# Test: "can add create with complex names" #

- **connection:** h2 standard

| Permutation | Verified | definition                         | name                         | schema    | OPERATIONS
| :---------- | :------- | :--------------------------------- | :--------------------------- | :-------- | :------
| c65344e     | true     | select * from LBSCHEMA2.test_table | 4TEST_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4TEST_view" AS select * from LBSCHEMA2.test_table
| 46e1323     | true     | select * from LBSCHEMA2.test_table | 4test_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4test_view" AS select * from LBSCHEMA2.test_table
| be3b9c1     | true     | select * from LBSCHEMA2.test_table | ANOTHERUPPERVIEW             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."ANOTHERUPPERVIEW" AS select * from LBSCHEMA2.test_table
| 3f2ae5d     | true     | select * from LBSCHEMA2.test_table | AnotherMixedView             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."AnotherMixedView" AS select * from LBSCHEMA2.test_table
| ea7be42     | true     | select * from LBSCHEMA2.test_table | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from LBSCHEMA2.test_table
| 33349c9     | true     | select * from LBSCHEMA2.test_table | MixedView                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."MixedView" AS select * from LBSCHEMA2.test_table
| f6d7969     | true     | select * from LBSCHEMA2.test_table | UPPERVIEW                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."UPPERVIEW" AS select * from LBSCHEMA2.test_table
| 1c8dd33     | true     | select * from LBSCHEMA2.test_table | anotherlowerview             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."anotherlowerview" AS select * from LBSCHEMA2.test_table
| e1e7432     | true     | select * from LBSCHEMA2.test_table | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from LBSCHEMA2.test_table
| 74a17af     | true     | select * from LBSCHEMA2.test_table | lowerview                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."lowerview" AS select * from LBSCHEMA2.test_table
| 5548ba0     | true     | select * from PUBLIC.test_table    | 4TEST_view                   | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."4TEST_view" AS select * from PUBLIC.test_table
| 3a4d095     | true     | select * from PUBLIC.test_table    | 4test_view                   | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."4test_view" AS select * from PUBLIC.test_table
| 380bd41     | true     | select * from PUBLIC.test_table    | ANOTHERUPPERVIEW             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."ANOTHERUPPERVIEW" AS select * from PUBLIC.test_table
| 5e6ba70     | true     | select * from PUBLIC.test_table    | AnotherMixedView             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."AnotherMixedView" AS select * from PUBLIC.test_table
| 1bdc887     | true     | select * from PUBLIC.test_table    | CRAZY!@#\$%^&*()_+{}[]'"VIEW | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from PUBLIC.test_table
| dc1729c     | true     | select * from PUBLIC.test_table    | MixedView                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."MixedView" AS select * from PUBLIC.test_table
| fd86c74     | true     | select * from PUBLIC.test_table    | UPPERVIEW                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."UPPERVIEW" AS select * from PUBLIC.test_table
| 8518197     | true     | select * from PUBLIC.test_table    | anotherlowerview             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."anotherlowerview" AS select * from PUBLIC.test_table
| 8ab58e5     | true     | select * from PUBLIC.test_table    | crazy!@#\$%^&*()_+{}[]'"view | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from PUBLIC.test_table
| c56ef3f     | true     | select * from PUBLIC.test_table    | lowerview                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."lowerview" AS select * from PUBLIC.test_table

# Test Version: "440a7e" #