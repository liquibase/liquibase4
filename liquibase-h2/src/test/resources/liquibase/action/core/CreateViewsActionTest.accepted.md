**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can add columns with all permutations" #

- **connection:** h2 standard
- **fullDefinition:** false

| Permutation | Verified | definition                         | name                         | schema    | OPERATIONS
| :---------- | :------- | :--------------------------------- | :--------------------------- | :-------- | :------
| 085b4c      | true     | select * from LBSCHEMA2.test_table | 4TEST_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4TEST_view" AS select * from LBSCHEMA2.test_table
| d55ecf      | true     | select * from LBSCHEMA2.test_table | 4test_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4test_view" AS select * from LBSCHEMA2.test_table
| 4168d2      | true     | select * from LBSCHEMA2.test_table | ANOTHERUPPERVIEW             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."ANOTHERUPPERVIEW" AS select * from LBSCHEMA2.test_table
| 09bc27      | true     | select * from LBSCHEMA2.test_table | AnotherMixedView             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."AnotherMixedView" AS select * from LBSCHEMA2.test_table
| a88682      | true     | select * from LBSCHEMA2.test_table | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from LBSCHEMA2.test_table
| f558e4      | true     | select * from LBSCHEMA2.test_table | MixedView                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."MixedView" AS select * from LBSCHEMA2.test_table
| f72946      | true     | select * from LBSCHEMA2.test_table | UPPERVIEW                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."UPPERVIEW" AS select * from LBSCHEMA2.test_table
| 90a130      | true     | select * from LBSCHEMA2.test_table | anotherlowerview             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."anotherlowerview" AS select * from LBSCHEMA2.test_table
| 009702      | true     | select * from LBSCHEMA2.test_table | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from LBSCHEMA2.test_table
| 7dc3d2      | true     | select * from LBSCHEMA2.test_table | lowerview                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."lowerview" AS select * from LBSCHEMA2.test_table
| 263065      | true     | select * from PUBLIC.test_table    | 4TEST_view                   | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."4TEST_view" AS select * from PUBLIC.test_table
| 372b00      | true     | select * from PUBLIC.test_table    | 4test_view                   | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."4test_view" AS select * from PUBLIC.test_table
| 459531      | true     | select * from PUBLIC.test_table    | ANOTHERUPPERVIEW             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."ANOTHERUPPERVIEW" AS select * from PUBLIC.test_table
| c147e9      | true     | select * from PUBLIC.test_table    | AnotherMixedView             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."AnotherMixedView" AS select * from PUBLIC.test_table
| 144334      | true     | select * from PUBLIC.test_table    | CRAZY!@#\$%^&*()_+{}[]'"VIEW | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from PUBLIC.test_table
| 4e15b9      | true     | select * from PUBLIC.test_table    | MixedView                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."MixedView" AS select * from PUBLIC.test_table
| 114dfd      | true     | select * from PUBLIC.test_table    | UPPERVIEW                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."UPPERVIEW" AS select * from PUBLIC.test_table
| d17216      | true     | select * from PUBLIC.test_table    | anotherlowerview             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."anotherlowerview" AS select * from PUBLIC.test_table
| 1c86e5      | true     | select * from PUBLIC.test_table    | crazy!@#\$%^&*()_+{}[]'"view | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from PUBLIC.test_table
| 2819f4      | true     | select * from PUBLIC.test_table    | lowerview                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."lowerview" AS select * from PUBLIC.test_table
| 034387      | true     | select * from test_table           | 4TEST_view                   | null      | **plan**: CREATE VIEW "4TEST_view" AS select * from test_table
| 66eebd      | true     | select * from test_table           | 4test_view                   | null      | **plan**: CREATE VIEW "4test_view" AS select * from test_table
| 8d3001      | true     | select * from test_table           | ANOTHERUPPERVIEW             | null      | **plan**: CREATE VIEW "ANOTHERUPPERVIEW" AS select * from test_table
| 26c513      | true     | select * from test_table           | AnotherMixedView             | null      | **plan**: CREATE VIEW "AnotherMixedView" AS select * from test_table
| c71805      | true     | select * from test_table           | CRAZY!@#\$%^&*()_+{}[]'"VIEW | null      | **plan**: CREATE VIEW "CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from test_table
| 0fd2a4      | true     | select * from test_table           | MixedView                    | null      | **plan**: CREATE VIEW "MixedView" AS select * from test_table
| de3a14      | true     | select * from test_table           | UPPERVIEW                    | null      | **plan**: CREATE VIEW "UPPERVIEW" AS select * from test_table
| d2098a      | true     | select * from test_table           | anotherlowerview             | null      | **plan**: CREATE VIEW "anotherlowerview" AS select * from test_table
| b834e0      | true     | select * from test_table           | crazy!@#\$%^&*()_+{}[]'"view | null      | **plan**: CREATE VIEW "crazy!@#\$%^&*()_+{}[]'""view" AS select * from test_table
| a15e1b      | true     | select * from test_table           | lowerview                    | null      | **plan**: CREATE VIEW "lowerview" AS select * from test_table

# Test: "can add create with complex names" #

- **connection:** h2 standard

| Permutation | Verified | definition                         | name                         | schema    | OPERATIONS
| :---------- | :------- | :--------------------------------- | :--------------------------- | :-------- | :------
| c65344      | true     | select * from LBSCHEMA2.test_table | 4TEST_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4TEST_view" AS select * from LBSCHEMA2.test_table
| 46e132      | true     | select * from LBSCHEMA2.test_table | 4test_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4test_view" AS select * from LBSCHEMA2.test_table
| be3b9c      | true     | select * from LBSCHEMA2.test_table | ANOTHERUPPERVIEW             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."ANOTHERUPPERVIEW" AS select * from LBSCHEMA2.test_table
| 3f2ae5      | true     | select * from LBSCHEMA2.test_table | AnotherMixedView             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."AnotherMixedView" AS select * from LBSCHEMA2.test_table
| ea7be4      | true     | select * from LBSCHEMA2.test_table | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from LBSCHEMA2.test_table
| 33349c      | true     | select * from LBSCHEMA2.test_table | MixedView                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."MixedView" AS select * from LBSCHEMA2.test_table
| f6d796      | true     | select * from LBSCHEMA2.test_table | UPPERVIEW                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."UPPERVIEW" AS select * from LBSCHEMA2.test_table
| 1c8dd3      | true     | select * from LBSCHEMA2.test_table | anotherlowerview             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."anotherlowerview" AS select * from LBSCHEMA2.test_table
| e1e743      | true     | select * from LBSCHEMA2.test_table | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from LBSCHEMA2.test_table
| 74a17a      | true     | select * from LBSCHEMA2.test_table | lowerview                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."lowerview" AS select * from LBSCHEMA2.test_table
| 5548ba      | true     | select * from PUBLIC.test_table    | 4TEST_view                   | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."4TEST_view" AS select * from PUBLIC.test_table
| 3a4d09      | true     | select * from PUBLIC.test_table    | 4test_view                   | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."4test_view" AS select * from PUBLIC.test_table
| 380bd4      | true     | select * from PUBLIC.test_table    | ANOTHERUPPERVIEW             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."ANOTHERUPPERVIEW" AS select * from PUBLIC.test_table
| 5e6ba7      | true     | select * from PUBLIC.test_table    | AnotherMixedView             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."AnotherMixedView" AS select * from PUBLIC.test_table
| 1bdc88      | true     | select * from PUBLIC.test_table    | CRAZY!@#\$%^&*()_+{}[]'"VIEW | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from PUBLIC.test_table
| dc1729      | true     | select * from PUBLIC.test_table    | MixedView                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."MixedView" AS select * from PUBLIC.test_table
| fd86c7      | true     | select * from PUBLIC.test_table    | UPPERVIEW                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."UPPERVIEW" AS select * from PUBLIC.test_table
| 851819      | true     | select * from PUBLIC.test_table    | anotherlowerview             | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."anotherlowerview" AS select * from PUBLIC.test_table
| 8ab58e      | true     | select * from PUBLIC.test_table    | crazy!@#\$%^&*()_+{}[]'"view | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from PUBLIC.test_table
| c56ef3      | true     | select * from PUBLIC.test_table    | lowerview                    | PUBLIC    | **plan**: CREATE VIEW "PUBLIC"."lowerview" AS select * from PUBLIC.test_table

# Test Version: "6a77c7" #