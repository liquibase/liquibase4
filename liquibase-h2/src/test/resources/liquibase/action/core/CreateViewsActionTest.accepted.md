**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can add columns with all permutations" #

- **connection:** h2 standard
- **fullDefinition:** false

| Permutation | Verified | definition                         | name                         | schema    | OPERATIONS
| :---------- | :------- | :--------------------------------- | :--------------------------- | :-------- | :------
| 085b4c9     | true     | select * from LBSCHEMA2.test_table | 4TEST_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4TEST_view" AS select * from LBSCHEMA2.test_table
| d55ecf3     | true     | select * from LBSCHEMA2.test_table | 4test_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4test_view" AS select * from LBSCHEMA2.test_table
| 4168d2f     | true     | select * from LBSCHEMA2.test_table | ANOTHERUPPERVIEW             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."ANOTHERUPPERVIEW" AS select * from LBSCHEMA2.test_table
| 09bc272     | true     | select * from LBSCHEMA2.test_table | AnotherMixedView             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."AnotherMixedView" AS select * from LBSCHEMA2.test_table
| a886824     | true     | select * from LBSCHEMA2.test_table | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from LBSCHEMA2.test_table
| f558e49     | true     | select * from LBSCHEMA2.test_table | MixedView                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."MixedView" AS select * from LBSCHEMA2.test_table
| f729461     | true     | select * from LBSCHEMA2.test_table | UPPERVIEW                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."UPPERVIEW" AS select * from LBSCHEMA2.test_table
| 90a130c     | true     | select * from LBSCHEMA2.test_table | anotherlowerview             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."anotherlowerview" AS select * from LBSCHEMA2.test_table
| 009702b     | true     | select * from LBSCHEMA2.test_table | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from LBSCHEMA2.test_table
| 7dc3d22     | true     | select * from LBSCHEMA2.test_table | lowerview                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."lowerview" AS select * from LBSCHEMA2.test_table
| 263065e     | true     | select * from PUBLIC.test_table    | 4TEST_view                   | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."4TEST_view" AS select * from PUBLIC.test_table
| 372b007     | true     | select * from PUBLIC.test_table    | 4test_view                   | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."4test_view" AS select * from PUBLIC.test_table
| 4595314     | true     | select * from PUBLIC.test_table    | ANOTHERUPPERVIEW             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."ANOTHERUPPERVIEW" AS select * from PUBLIC.test_table
| c147e9a     | true     | select * from PUBLIC.test_table    | AnotherMixedView             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."AnotherMixedView" AS select * from PUBLIC.test_table
| 1443344     | true     | select * from PUBLIC.test_table    | CRAZY!@#\$%^&*()_+{}[]'"VIEW | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from PUBLIC.test_table
| 4e15b91     | true     | select * from PUBLIC.test_table    | MixedView                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."MixedView" AS select * from PUBLIC.test_table
| 114dfd1     | true     | select * from PUBLIC.test_table    | UPPERVIEW                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."UPPERVIEW" AS select * from PUBLIC.test_table
| d172162     | true     | select * from PUBLIC.test_table    | anotherlowerview             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."anotherlowerview" AS select * from PUBLIC.test_table
| 1c86e5f     | true     | select * from PUBLIC.test_table    | crazy!@#\$%^&*()_+{}[]'"view | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from PUBLIC.test_table
| 2819f4b     | true     | select * from PUBLIC.test_table    | lowerview                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."lowerview" AS select * from PUBLIC.test_table
| 034387d     | true     | select * from test_table           | 4TEST_view                   | null      | **plan**: CREATE VIEW "4TEST_view" AS select * from test_table
| 66eebda     | true     | select * from test_table           | 4test_view                   | null      | **plan**: CREATE VIEW "4test_view" AS select * from test_table
| 8d3001b     | true     | select * from test_table           | ANOTHERUPPERVIEW             | null      | **plan**: CREATE VIEW "ANOTHERUPPERVIEW" AS select * from test_table
| 26c5132     | true     | select * from test_table           | AnotherMixedView             | null      | **plan**: CREATE VIEW "AnotherMixedView" AS select * from test_table
| c718053     | true     | select * from test_table           | CRAZY!@#\$%^&*()_+{}[]'"VIEW | null      | **plan**: CREATE VIEW "CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from test_table
| 0fd2a4c     | true     | select * from test_table           | MixedView                    | null      | **plan**: CREATE VIEW "MixedView" AS select * from test_table
| de3a142     | true     | select * from test_table           | UPPERVIEW                    | null      | **plan**: CREATE VIEW "UPPERVIEW" AS select * from test_table
| d2098ae     | true     | select * from test_table           | anotherlowerview             | null      | **plan**: CREATE VIEW "anotherlowerview" AS select * from test_table
| b834e02     | true     | select * from test_table           | crazy!@#\$%^&*()_+{}[]'"view | null      | **plan**: CREATE VIEW "crazy!@#\$%^&*()_+{}[]'""view" AS select * from test_table
| a15e1b6     | true     | select * from test_table           | lowerview                    | null      | **plan**: CREATE VIEW "lowerview" AS select * from test_table

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

# Test Version: "81e033" #