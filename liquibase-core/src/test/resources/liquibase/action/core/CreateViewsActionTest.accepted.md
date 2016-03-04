**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can add columns with all permutations" #

- **connection:** generic standard
- **fullDefinition:** false

| Permutation | Verified | definition                         | name                         | schema    | OPERATIONS
| :---------- | :------- | :--------------------------------- | :--------------------------- | :-------- | :------
| a4ad08      | Generic  | select * from LBSCHEMA.test_table  | 4TEST_view                   | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."4TEST_view" AS select * from LBSCHEMA.test_table
| 28ab2a      | Generic  | select * from LBSCHEMA.test_table  | 4test_view                   | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."4test_view" AS select * from LBSCHEMA.test_table
| 9f4e50      | Generic  | select * from LBSCHEMA.test_table  | ANOTHERUPPERVIEW             | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."ANOTHERUPPERVIEW" AS select * from LBSCHEMA.test_table
| bfd4e4      | Generic  | select * from LBSCHEMA.test_table  | AnotherMixedView             | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."AnotherMixedView" AS select * from LBSCHEMA.test_table
| e7b469      | Generic  | select * from LBSCHEMA.test_table  | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from LBSCHEMA.test_table
| 9c2382      | Generic  | select * from LBSCHEMA.test_table  | MixedView                    | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."MixedView" AS select * from LBSCHEMA.test_table
| 745894      | Generic  | select * from LBSCHEMA.test_table  | UPPERVIEW                    | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."UPPERVIEW" AS select * from LBSCHEMA.test_table
| 7b180e      | Generic  | select * from LBSCHEMA.test_table  | anotherlowerview             | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."anotherlowerview" AS select * from LBSCHEMA.test_table
| c1647b      | Generic  | select * from LBSCHEMA.test_table  | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from LBSCHEMA.test_table
| 3920d9      | Generic  | select * from LBSCHEMA.test_table  | lowerview                    | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."lowerview" AS select * from LBSCHEMA.test_table
| 92b4f1      | Generic  | select * from LBSCHEMA2.test_table | 4TEST_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4TEST_view" AS select * from LBSCHEMA2.test_table
| 52a279      | Generic  | select * from LBSCHEMA2.test_table | 4test_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4test_view" AS select * from LBSCHEMA2.test_table
| e4cf34      | Generic  | select * from LBSCHEMA2.test_table | ANOTHERUPPERVIEW             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."ANOTHERUPPERVIEW" AS select * from LBSCHEMA2.test_table
| 6db720      | Generic  | select * from LBSCHEMA2.test_table | AnotherMixedView             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."AnotherMixedView" AS select * from LBSCHEMA2.test_table
| 2936dd      | Generic  | select * from LBSCHEMA2.test_table | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from LBSCHEMA2.test_table
| 1a025e      | Generic  | select * from LBSCHEMA2.test_table | MixedView                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."MixedView" AS select * from LBSCHEMA2.test_table
| 5b4db9      | Generic  | select * from LBSCHEMA2.test_table | UPPERVIEW                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."UPPERVIEW" AS select * from LBSCHEMA2.test_table
| f6b5b0      | Generic  | select * from LBSCHEMA2.test_table | anotherlowerview             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."anotherlowerview" AS select * from LBSCHEMA2.test_table
| 450b83      | Generic  | select * from LBSCHEMA2.test_table | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from LBSCHEMA2.test_table
| c27b2a      | Generic  | select * from LBSCHEMA2.test_table | lowerview                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."lowerview" AS select * from LBSCHEMA2.test_table
| 6cb6c1      | Generic  | select * from test_table           | 4TEST_view                   | null      | **plan**: CREATE VIEW "4TEST_view" AS select * from test_table
| e8ddb4      | Generic  | select * from test_table           | 4test_view                   | null      | **plan**: CREATE VIEW "4test_view" AS select * from test_table
| 608de4      | Generic  | select * from test_table           | ANOTHERUPPERVIEW             | null      | **plan**: CREATE VIEW "ANOTHERUPPERVIEW" AS select * from test_table
| 35f75d      | Generic  | select * from test_table           | AnotherMixedView             | null      | **plan**: CREATE VIEW "AnotherMixedView" AS select * from test_table
| 7bad05      | Generic  | select * from test_table           | CRAZY!@#\$%^&*()_+{}[]'"VIEW | null      | **plan**: CREATE VIEW "CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from test_table
| 2a42e9      | Generic  | select * from test_table           | MixedView                    | null      | **plan**: CREATE VIEW "MixedView" AS select * from test_table
| 0d4871      | Generic  | select * from test_table           | UPPERVIEW                    | null      | **plan**: CREATE VIEW "UPPERVIEW" AS select * from test_table
| fa4cd1      | Generic  | select * from test_table           | anotherlowerview             | null      | **plan**: CREATE VIEW "anotherlowerview" AS select * from test_table
| 9519c8      | Generic  | select * from test_table           | crazy!@#\$%^&*()_+{}[]'"view | null      | **plan**: CREATE VIEW "crazy!@#\$%^&*()_+{}[]'""view" AS select * from test_table
| f7c0a7      | Generic  | select * from test_table           | lowerview                    | null      | **plan**: CREATE VIEW "lowerview" AS select * from test_table

# Test: "can add create with complex names" #

- **connection:** generic standard

| Permutation | Verified | definition                         | name                         | schema    | OPERATIONS
| :---------- | :------- | :--------------------------------- | :--------------------------- | :-------- | :------
| bea589      | Generic  | select * from LBSCHEMA.test_table  | 4TEST_view                   | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."4TEST_view" AS select * from LBSCHEMA.test_table
| 0bc720      | Generic  | select * from LBSCHEMA.test_table  | 4test_view                   | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."4test_view" AS select * from LBSCHEMA.test_table
| df026c      | Generic  | select * from LBSCHEMA.test_table  | ANOTHERUPPERVIEW             | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."ANOTHERUPPERVIEW" AS select * from LBSCHEMA.test_table
| 2cd44e      | Generic  | select * from LBSCHEMA.test_table  | AnotherMixedView             | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."AnotherMixedView" AS select * from LBSCHEMA.test_table
| f1624f      | Generic  | select * from LBSCHEMA.test_table  | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from LBSCHEMA.test_table
| f2dcda      | Generic  | select * from LBSCHEMA.test_table  | MixedView                    | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."MixedView" AS select * from LBSCHEMA.test_table
| 517a3e      | Generic  | select * from LBSCHEMA.test_table  | UPPERVIEW                    | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."UPPERVIEW" AS select * from LBSCHEMA.test_table
| 26eea4      | Generic  | select * from LBSCHEMA.test_table  | anotherlowerview             | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."anotherlowerview" AS select * from LBSCHEMA.test_table
| 3c34d4      | Generic  | select * from LBSCHEMA.test_table  | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from LBSCHEMA.test_table
| 1322aa      | Generic  | select * from LBSCHEMA.test_table  | lowerview                    | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."lowerview" AS select * from LBSCHEMA.test_table
| b07fa2      | Generic  | select * from LBSCHEMA2.test_table | 4TEST_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4TEST_view" AS select * from LBSCHEMA2.test_table
| b902b8      | Generic  | select * from LBSCHEMA2.test_table | 4test_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4test_view" AS select * from LBSCHEMA2.test_table
| c1a26a      | Generic  | select * from LBSCHEMA2.test_table | ANOTHERUPPERVIEW             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."ANOTHERUPPERVIEW" AS select * from LBSCHEMA2.test_table
| b1315f      | Generic  | select * from LBSCHEMA2.test_table | AnotherMixedView             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."AnotherMixedView" AS select * from LBSCHEMA2.test_table
| f83421      | Generic  | select * from LBSCHEMA2.test_table | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from LBSCHEMA2.test_table
| 0df9ab      | Generic  | select * from LBSCHEMA2.test_table | MixedView                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."MixedView" AS select * from LBSCHEMA2.test_table
| f4e897      | Generic  | select * from LBSCHEMA2.test_table | UPPERVIEW                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."UPPERVIEW" AS select * from LBSCHEMA2.test_table
| b798cb      | Generic  | select * from LBSCHEMA2.test_table | anotherlowerview             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."anotherlowerview" AS select * from LBSCHEMA2.test_table
| 0824cc      | Generic  | select * from LBSCHEMA2.test_table | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from LBSCHEMA2.test_table
| 9cdc45      | Generic  | select * from LBSCHEMA2.test_table | lowerview                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."lowerview" AS select * from LBSCHEMA2.test_table

# Test Version: "86bdbf" #