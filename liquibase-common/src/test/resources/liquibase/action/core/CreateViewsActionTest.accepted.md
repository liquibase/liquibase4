**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can add columns with all permutations" #

- **connection:** generic standard
- **fullDefinition:** null

| Permutation | Verified | definition                         | name                         | schema    | OPERATIONS
| :---------- | :------- | :--------------------------------- | :--------------------------- | :-------- | :------
| 58b4d02     | Generic  | select * from LBSCHEMA.test_table  | 4TEST_view                   | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."4TEST_view" AS select * from LBSCHEMA.test_table
| c275ffc     | Generic  | select * from LBSCHEMA.test_table  | 4test_view                   | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."4test_view" AS select * from LBSCHEMA.test_table
| a042564     | Generic  | select * from LBSCHEMA.test_table  | ANOTHERUPPERVIEW             | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."ANOTHERUPPERVIEW" AS select * from LBSCHEMA.test_table
| 82f5f2d     | Generic  | select * from LBSCHEMA.test_table  | AnotherMixedView             | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."AnotherMixedView" AS select * from LBSCHEMA.test_table
| 6dba525     | Generic  | select * from LBSCHEMA.test_table  | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from LBSCHEMA.test_table
| a959df9     | Generic  | select * from LBSCHEMA.test_table  | MixedView                    | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."MixedView" AS select * from LBSCHEMA.test_table
| a2ec7f4     | Generic  | select * from LBSCHEMA.test_table  | UPPERVIEW                    | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."UPPERVIEW" AS select * from LBSCHEMA.test_table
| 1712398     | Generic  | select * from LBSCHEMA.test_table  | anotherlowerview             | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."anotherlowerview" AS select * from LBSCHEMA.test_table
| cd63732     | Generic  | select * from LBSCHEMA.test_table  | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from LBSCHEMA.test_table
| 7f94053     | Generic  | select * from LBSCHEMA.test_table  | lowerview                    | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."lowerview" AS select * from LBSCHEMA.test_table
| bfb121e     | Generic  | select * from LBSCHEMA2.test_table | 4TEST_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4TEST_view" AS select * from LBSCHEMA2.test_table
| 890d30e     | Generic  | select * from LBSCHEMA2.test_table | 4test_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4test_view" AS select * from LBSCHEMA2.test_table
| 80834a8     | Generic  | select * from LBSCHEMA2.test_table | ANOTHERUPPERVIEW             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."ANOTHERUPPERVIEW" AS select * from LBSCHEMA2.test_table
| a2d7421     | Generic  | select * from LBSCHEMA2.test_table | AnotherMixedView             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."AnotherMixedView" AS select * from LBSCHEMA2.test_table
| 8804737     | Generic  | select * from LBSCHEMA2.test_table | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from LBSCHEMA2.test_table
| 3745684     | Generic  | select * from LBSCHEMA2.test_table | MixedView                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."MixedView" AS select * from LBSCHEMA2.test_table
| 85f19c9     | Generic  | select * from LBSCHEMA2.test_table | UPPERVIEW                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."UPPERVIEW" AS select * from LBSCHEMA2.test_table
| e546461     | Generic  | select * from LBSCHEMA2.test_table | anotherlowerview             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."anotherlowerview" AS select * from LBSCHEMA2.test_table
| 02b36ec     | Generic  | select * from LBSCHEMA2.test_table | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from LBSCHEMA2.test_table
| a8ca372     | Generic  | select * from LBSCHEMA2.test_table | lowerview                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."lowerview" AS select * from LBSCHEMA2.test_table
| 5e28d2b     | Generic  | select * from test_table           | 4TEST_view                   | null      | **plan**: CREATE VIEW "4TEST_view" AS select * from test_table
| f8157ad     | Generic  | select * from test_table           | 4test_view                   | null      | **plan**: CREATE VIEW "4test_view" AS select * from test_table
| ae0efd4     | Generic  | select * from test_table           | ANOTHERUPPERVIEW             | null      | **plan**: CREATE VIEW "ANOTHERUPPERVIEW" AS select * from test_table
| 8fd83b5     | Generic  | select * from test_table           | AnotherMixedView             | null      | **plan**: CREATE VIEW "AnotherMixedView" AS select * from test_table
| cb6bf71     | Generic  | select * from test_table           | CRAZY!@#\$%^&*()_+{}[]'"VIEW | null      | **plan**: CREATE VIEW "CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from test_table
| 120c11c     | Generic  | select * from test_table           | MixedView                    | null      | **plan**: CREATE VIEW "MixedView" AS select * from test_table
| 7a8d636     | Generic  | select * from test_table           | UPPERVIEW                    | null      | **plan**: CREATE VIEW "UPPERVIEW" AS select * from test_table
| 69c56e8     | Generic  | select * from test_table           | anotherlowerview             | null      | **plan**: CREATE VIEW "anotherlowerview" AS select * from test_table
| 50e20d8     | Generic  | select * from test_table           | crazy!@#\$%^&*()_+{}[]'"view | null      | **plan**: CREATE VIEW "crazy!@#\$%^&*()_+{}[]'""view" AS select * from test_table
| e026602     | Generic  | select * from test_table           | lowerview                    | null      | **plan**: CREATE VIEW "lowerview" AS select * from test_table

# Test: "can add create with complex names" #

- **connection:** generic standard

| Permutation | Verified | definition                         | name                         | schema    | OPERATIONS
| :---------- | :------- | :--------------------------------- | :--------------------------- | :-------- | :------
| bea5897     | Generic  | select * from LBSCHEMA.test_table  | 4TEST_view                   | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."4TEST_view" AS select * from LBSCHEMA.test_table
| 0bc720a     | Generic  | select * from LBSCHEMA.test_table  | 4test_view                   | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."4test_view" AS select * from LBSCHEMA.test_table
| df026cb     | Generic  | select * from LBSCHEMA.test_table  | ANOTHERUPPERVIEW             | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."ANOTHERUPPERVIEW" AS select * from LBSCHEMA.test_table
| 2cd44e7     | Generic  | select * from LBSCHEMA.test_table  | AnotherMixedView             | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."AnotherMixedView" AS select * from LBSCHEMA.test_table
| f1624ff     | Generic  | select * from LBSCHEMA.test_table  | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from LBSCHEMA.test_table
| f2dcda6     | Generic  | select * from LBSCHEMA.test_table  | MixedView                    | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."MixedView" AS select * from LBSCHEMA.test_table
| 517a3e0     | Generic  | select * from LBSCHEMA.test_table  | UPPERVIEW                    | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."UPPERVIEW" AS select * from LBSCHEMA.test_table
| 26eea4b     | Generic  | select * from LBSCHEMA.test_table  | anotherlowerview             | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."anotherlowerview" AS select * from LBSCHEMA.test_table
| 3c34d4b     | Generic  | select * from LBSCHEMA.test_table  | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from LBSCHEMA.test_table
| 1322aa7     | Generic  | select * from LBSCHEMA.test_table  | lowerview                    | LBSCHEMA  | **plan**: CREATE VIEW "LBSCHEMA"."lowerview" AS select * from LBSCHEMA.test_table
| b07fa2b     | Generic  | select * from LBSCHEMA2.test_table | 4TEST_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4TEST_view" AS select * from LBSCHEMA2.test_table
| b902b85     | Generic  | select * from LBSCHEMA2.test_table | 4test_view                   | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."4test_view" AS select * from LBSCHEMA2.test_table
| c1a26a0     | Generic  | select * from LBSCHEMA2.test_table | ANOTHERUPPERVIEW             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."ANOTHERUPPERVIEW" AS select * from LBSCHEMA2.test_table
| b1315fc     | Generic  | select * from LBSCHEMA2.test_table | AnotherMixedView             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."AnotherMixedView" AS select * from LBSCHEMA2.test_table
| f83421d     | Generic  | select * from LBSCHEMA2.test_table | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."CRAZY!@#\$%^&*()_+{}[]'""VIEW" AS select * from LBSCHEMA2.test_table
| 0df9ab2     | Generic  | select * from LBSCHEMA2.test_table | MixedView                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."MixedView" AS select * from LBSCHEMA2.test_table
| f4e8974     | Generic  | select * from LBSCHEMA2.test_table | UPPERVIEW                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."UPPERVIEW" AS select * from LBSCHEMA2.test_table
| b798cb9     | Generic  | select * from LBSCHEMA2.test_table | anotherlowerview             | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."anotherlowerview" AS select * from LBSCHEMA2.test_table
| 0824cce     | Generic  | select * from LBSCHEMA2.test_table | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."crazy!@#\$%^&*()_+{}[]'""view" AS select * from LBSCHEMA2.test_table
| 9cdc456     | Generic  | select * from LBSCHEMA2.test_table | lowerview                    | LBSCHEMA2 | **plan**: CREATE VIEW "LBSCHEMA2"."lowerview" AS select * from LBSCHEMA2.test_table

# Test Version: "4ae6e0" #