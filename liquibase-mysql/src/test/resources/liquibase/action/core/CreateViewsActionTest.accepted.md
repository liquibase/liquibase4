**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can add columns with all permutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | completeDefinition | definition                      | name                         | schema | OPERATIONS
| :---------- | :------- | :----------------- | :------------------------------ | :--------------------------- | :----- | :------
| 9a50dfd     | true     | null               | select * from lbcat.test_table  | 4test_view                   | lbcat  | **plan**: CREATE VIEW `lbcat`.`4test_view` AS select * from lbcat.test_table
| f1a6064     | true     | null               | select * from lbcat.test_table  | anotherlowerview             | lbcat  | **plan**: CREATE VIEW `lbcat`.`anotherlowerview` AS select * from lbcat.test_table
| 713ab1d     | true     | null               | select * from lbcat.test_table  | crazy!@#\$%^&*()_+{}[]'"view | lbcat  | **plan**: CREATE VIEW `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view` AS select * from lbcat.test_table
| 222fda1     | true     | null               | select * from lbcat.test_table  | lowerview                    | lbcat  | **plan**: CREATE VIEW `lbcat`.`lowerview` AS select * from lbcat.test_table
| 19b590f     | true     | null               | select * from lbcat2.test_table | 4test_view                   | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`4test_view` AS select * from lbcat2.test_table
| 0e863ec     | true     | null               | select * from lbcat2.test_table | anotherlowerview             | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`anotherlowerview` AS select * from lbcat2.test_table
| f1956ec     | true     | null               | select * from lbcat2.test_table | crazy!@#\$%^&*()_+{}[]'"view | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view` AS select * from lbcat2.test_table
| 36766d3     | true     | null               | select * from lbcat2.test_table | lowerview                    | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`lowerview` AS select * from lbcat2.test_table
| 4732f92     | true     | null               | select * from test_table        | 4test_view                   | null   | **plan**: CREATE VIEW `4test_view` AS select * from test_table
| 2256b05     | true     | null               | select * from test_table        | anotherlowerview             | null   | **plan**: CREATE VIEW `anotherlowerview` AS select * from test_table
| c9b97f1     | true     | null               | select * from test_table        | crazy!@#\$%^&*()_+{}[]'"view | null   | **plan**: CREATE VIEW `crazy!@#\$%^&*()_+{}[]'"view` AS select * from test_table
| e334429     | true     | null               | select * from test_table        | lowerview                    | null   | **plan**: CREATE VIEW `lowerview` AS select * from test_table

# Test: "can add create with complex names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | definition                      | name                         | schema | OPERATIONS
| :---------- | :------- | :------------------------------ | :--------------------------- | :----- | :------
| b2801b3     | true     | select * from lbcat.test_table  | 4test_view                   | lbcat  | **plan**: CREATE VIEW `lbcat`.`4test_view` AS select * from lbcat.test_table
| 1dafb46     | true     | select * from lbcat.test_table  | anotherlowerview             | lbcat  | **plan**: CREATE VIEW `lbcat`.`anotherlowerview` AS select * from lbcat.test_table
| d486b73     | true     | select * from lbcat.test_table  | crazy!@#\$%^&*()_+{}[]'"view | lbcat  | **plan**: CREATE VIEW `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view` AS select * from lbcat.test_table
| 8c61341     | true     | select * from lbcat.test_table  | lowerview                    | lbcat  | **plan**: CREATE VIEW `lbcat`.`lowerview` AS select * from lbcat.test_table
| b5e043f     | true     | select * from lbcat2.test_table | 4test_view                   | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`4test_view` AS select * from lbcat2.test_table
| a426863     | true     | select * from lbcat2.test_table | anotherlowerview             | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`anotherlowerview` AS select * from lbcat2.test_table
| 16916ba     | true     | select * from lbcat2.test_table | crazy!@#\$%^&*()_+{}[]'"view | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view` AS select * from lbcat2.test_table
| eda719d     | true     | select * from lbcat2.test_table | lowerview                    | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`lowerview` AS select * from lbcat2.test_table

# Test Version: "440a7e" #