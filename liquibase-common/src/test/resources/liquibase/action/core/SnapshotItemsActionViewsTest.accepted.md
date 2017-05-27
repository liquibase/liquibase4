**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can find all views using a null name " #

- **connection:** generic standard

| Permutation | Verified | view              | OPERATIONS
| :---------- | :------- | :---------------- | :------
| a3346ec     | Generic  | LBSCHEMA.UNNAMED  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_SCHEMA='LBSCHEMA'
| 6077409     | Generic  | LBSCHEMA2.UNNAMED | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_SCHEMA='LBSCHEMA2'

# Test: "can find all views using schema reference" #

- **connection:** generic standard

| Permutation | Verified | view      | OPERATIONS
| :---------- | :------- | :-------- | :------
| 27f168d     | Generic  | LBSCHEMA  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_SCHEMA='LBSCHEMA'
| 81714ea     | Generic  | LBSCHEMA2 | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_SCHEMA='LBSCHEMA2'

# Test: "can find complex view names" #

- **connection:** generic standard

| Permutation | Verified | view                                   | OPERATIONS
| :---------- | :------- | :------------------------------------- | :------
| 2b681d8     | Generic  | LBSCHEMA.4TEST_view                    | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='4TEST_view' AND TABLE_SCHEMA='LBSCHEMA'
| 38a71f1     | Generic  | LBSCHEMA.4test_view                    | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='4test_view' AND TABLE_SCHEMA='LBSCHEMA'
| 3e3245c     | Generic  | LBSCHEMA.ANOTHERUPPERVIEW              | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='ANOTHERUPPERVIEW' AND TABLE_SCHEMA='LBSCHEMA'
| 2077c8e     | Generic  | LBSCHEMA.AnotherMixedView              | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='AnotherMixedView' AND TABLE_SCHEMA='LBSCHEMA'
| d2d0f08     | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"VIEW  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='CRAZY!@#\$%^&*()_+{}[]''"VIEW' AND TABLE_SCHEMA='LBSCHEMA'
| c81b27b     | Generic  | LBSCHEMA.MixedView                     | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='MixedView' AND TABLE_SCHEMA='LBSCHEMA'
| 8c52082     | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA              | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='ONLY_IN_LBSCHEMA' AND TABLE_SCHEMA='LBSCHEMA'
| eea8663     | Generic  | LBSCHEMA.UPPERVIEW                     | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='UPPERVIEW' AND TABLE_SCHEMA='LBSCHEMA'
| 2654bdb     | Generic  | LBSCHEMA.anotherlowerview              | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='anotherlowerview' AND TABLE_SCHEMA='LBSCHEMA'
| 7ba1d08     | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"view  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='crazy!@#\$%^&*()_+{}[]''"view' AND TABLE_SCHEMA='LBSCHEMA'
| b2a617b     | Generic  | LBSCHEMA.lowerview                     | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='lowerview' AND TABLE_SCHEMA='LBSCHEMA'
| b9a4c39     | Generic  | LBSCHEMA2.4TEST_view                   | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='4TEST_view' AND TABLE_SCHEMA='LBSCHEMA2'
| 688b7a3     | Generic  | LBSCHEMA2.4test_view                   | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='4test_view' AND TABLE_SCHEMA='LBSCHEMA2'
| e526c81     | Generic  | LBSCHEMA2.ANOTHERUPPERVIEW             | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='ANOTHERUPPERVIEW' AND TABLE_SCHEMA='LBSCHEMA2'
| 87a3627     | Generic  | LBSCHEMA2.AnotherMixedView             | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='AnotherMixedView' AND TABLE_SCHEMA='LBSCHEMA2'
| 9b87604     | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"VIEW | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='CRAZY!@#\$%^&*()_+{}[]''"VIEW' AND TABLE_SCHEMA='LBSCHEMA2'
| ddfa05d     | Generic  | LBSCHEMA2.MixedView                    | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='MixedView' AND TABLE_SCHEMA='LBSCHEMA2'
| e775803     | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2            | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='ONLY_IN_LBSCHEMA2' AND TABLE_SCHEMA='LBSCHEMA2'
| e2913fb     | Generic  | LBSCHEMA2.UPPERVIEW                    | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='UPPERVIEW' AND TABLE_SCHEMA='LBSCHEMA2'
| 415afa9     | Generic  | LBSCHEMA2.anotherlowerview             | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='anotherlowerview' AND TABLE_SCHEMA='LBSCHEMA2'
| 4b4cd97     | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"view | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='crazy!@#\$%^&*()_+{}[]''"view' AND TABLE_SCHEMA='LBSCHEMA2'
| 476a506     | Generic  | LBSCHEMA2.lowerview                    | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='lowerview' AND TABLE_SCHEMA='LBSCHEMA2'

# Test: "can find views with various options" #

- **connection:** generic standard

| Permutation | Verified | definition                         | name                         | schema    | OPERATIONS
| :---------- | :------- | :--------------------------------- | :--------------------------- | :-------- | :------
| bea5897     | Generic  | select * from LBSCHEMA.test_table  | 4TEST_view                   | LBSCHEMA  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='4TEST_view' AND TABLE_SCHEMA='LBSCHEMA'
| 0bc720a     | Generic  | select * from LBSCHEMA.test_table  | 4test_view                   | LBSCHEMA  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='4test_view' AND TABLE_SCHEMA='LBSCHEMA'
| df026cb     | Generic  | select * from LBSCHEMA.test_table  | ANOTHERUPPERVIEW             | LBSCHEMA  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='ANOTHERUPPERVIEW' AND TABLE_SCHEMA='LBSCHEMA'
| 2cd44e7     | Generic  | select * from LBSCHEMA.test_table  | AnotherMixedView             | LBSCHEMA  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='AnotherMixedView' AND TABLE_SCHEMA='LBSCHEMA'
| f1624ff     | Generic  | select * from LBSCHEMA.test_table  | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='CRAZY!@#\$%^&*()_+{}[]''"VIEW' AND TABLE_SCHEMA='LBSCHEMA'
| f2dcda6     | Generic  | select * from LBSCHEMA.test_table  | MixedView                    | LBSCHEMA  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='MixedView' AND TABLE_SCHEMA='LBSCHEMA'
| 517a3e0     | Generic  | select * from LBSCHEMA.test_table  | UPPERVIEW                    | LBSCHEMA  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='UPPERVIEW' AND TABLE_SCHEMA='LBSCHEMA'
| 26eea4b     | Generic  | select * from LBSCHEMA.test_table  | anotherlowerview             | LBSCHEMA  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='anotherlowerview' AND TABLE_SCHEMA='LBSCHEMA'
| 3c34d4b     | Generic  | select * from LBSCHEMA.test_table  | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='crazy!@#\$%^&*()_+{}[]''"view' AND TABLE_SCHEMA='LBSCHEMA'
| 1322aa7     | Generic  | select * from LBSCHEMA.test_table  | lowerview                    | LBSCHEMA  | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='lowerview' AND TABLE_SCHEMA='LBSCHEMA'
| b07fa2b     | Generic  | select * from LBSCHEMA2.test_table | 4TEST_view                   | LBSCHEMA2 | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='4TEST_view' AND TABLE_SCHEMA='LBSCHEMA2'
| b902b85     | Generic  | select * from LBSCHEMA2.test_table | 4test_view                   | LBSCHEMA2 | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='4test_view' AND TABLE_SCHEMA='LBSCHEMA2'
| c1a26a0     | Generic  | select * from LBSCHEMA2.test_table | ANOTHERUPPERVIEW             | LBSCHEMA2 | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='ANOTHERUPPERVIEW' AND TABLE_SCHEMA='LBSCHEMA2'
| b1315fc     | Generic  | select * from LBSCHEMA2.test_table | AnotherMixedView             | LBSCHEMA2 | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='AnotherMixedView' AND TABLE_SCHEMA='LBSCHEMA2'
| f83421d     | Generic  | select * from LBSCHEMA2.test_table | CRAZY!@#\$%^&*()_+{}[]'"VIEW | LBSCHEMA2 | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='CRAZY!@#\$%^&*()_+{}[]''"VIEW' AND TABLE_SCHEMA='LBSCHEMA2'
| 0df9ab2     | Generic  | select * from LBSCHEMA2.test_table | MixedView                    | LBSCHEMA2 | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='MixedView' AND TABLE_SCHEMA='LBSCHEMA2'
| f4e8974     | Generic  | select * from LBSCHEMA2.test_table | UPPERVIEW                    | LBSCHEMA2 | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='UPPERVIEW' AND TABLE_SCHEMA='LBSCHEMA2'
| b798cb9     | Generic  | select * from LBSCHEMA2.test_table | anotherlowerview             | LBSCHEMA2 | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='anotherlowerview' AND TABLE_SCHEMA='LBSCHEMA2'
| 0824cce     | Generic  | select * from LBSCHEMA2.test_table | crazy!@#\$%^&*()_+{}[]'"view | LBSCHEMA2 | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='crazy!@#\$%^&*()_+{}[]''"view' AND TABLE_SCHEMA='LBSCHEMA2'
| 9cdc456     | Generic  | select * from LBSCHEMA2.test_table | lowerview                    | LBSCHEMA2 | **plan**: SELECT "TABLE_NAME", "VIEW_DEFINITION", "TABLE_SCHEMA" FROM "INFORMATION_SCHEMA"."VIEWS" WHERE TABLE_NAME='lowerview' AND TABLE_SCHEMA='LBSCHEMA2'

# Test Version: "617629" #