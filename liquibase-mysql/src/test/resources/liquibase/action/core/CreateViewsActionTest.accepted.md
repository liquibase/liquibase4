**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can add columns with all permutations" #

- **connection:** mysql caseInsensitive
- **fullDefinition:** false

| Permutation | Verified | definition                      | name                         | schema | OPERATIONS
| :---------- | :------- | :------------------------------ | :--------------------------- | :----- | :------
| 46a419      | true     | select * from lbcat.test_table  | 4test_view                   | lbcat  | **plan**: CREATE VIEW `lbcat`.`4test_view` AS select * from lbcat.test_table
| bb8dbb      | true     | select * from lbcat.test_table  | anotherlowerview             | lbcat  | **plan**: CREATE VIEW `lbcat`.`anotherlowerview` AS select * from lbcat.test_table
| 0697a3      | true     | select * from lbcat.test_table  | crazy!@#\$%^&*()_+{}[]'"view | lbcat  | **plan**: CREATE VIEW `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view` AS select * from lbcat.test_table
| a7b411      | true     | select * from lbcat.test_table  | lowerview                    | lbcat  | **plan**: CREATE VIEW `lbcat`.`lowerview` AS select * from lbcat.test_table
| c24a1d      | true     | select * from lbcat2.test_table | 4test_view                   | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`4test_view` AS select * from lbcat2.test_table
| ef627c      | true     | select * from lbcat2.test_table | anotherlowerview             | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`anotherlowerview` AS select * from lbcat2.test_table
| c68575      | true     | select * from lbcat2.test_table | crazy!@#\$%^&*()_+{}[]'"view | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view` AS select * from lbcat2.test_table
| 013881      | true     | select * from lbcat2.test_table | lowerview                    | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`lowerview` AS select * from lbcat2.test_table
| 6a2e43      | true     | select * from test_table        | 4test_view                   | null   | **plan**: CREATE VIEW `4test_view` AS select * from test_table
| 96bc76      | true     | select * from test_table        | anotherlowerview             | null   | **plan**: CREATE VIEW `anotherlowerview` AS select * from test_table
| 34f100      | true     | select * from test_table        | crazy!@#\$%^&*()_+{}[]'"view | null   | **plan**: CREATE VIEW `crazy!@#\$%^&*()_+{}[]'"view` AS select * from test_table
| 903904      | true     | select * from test_table        | lowerview                    | null   | **plan**: CREATE VIEW `lowerview` AS select * from test_table

# Test: "can add create with complex names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | definition                      | name                         | schema | OPERATIONS
| :---------- | :------- | :------------------------------ | :--------------------------- | :----- | :------
| b2801b      | true     | select * from lbcat.test_table  | 4test_view                   | lbcat  | **plan**: CREATE VIEW `lbcat`.`4test_view` AS select * from lbcat.test_table
| 1dafb4      | true     | select * from lbcat.test_table  | anotherlowerview             | lbcat  | **plan**: CREATE VIEW `lbcat`.`anotherlowerview` AS select * from lbcat.test_table
| d486b7      | true     | select * from lbcat.test_table  | crazy!@#\$%^&*()_+{}[]'"view | lbcat  | **plan**: CREATE VIEW `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view` AS select * from lbcat.test_table
| 8c6134      | true     | select * from lbcat.test_table  | lowerview                    | lbcat  | **plan**: CREATE VIEW `lbcat`.`lowerview` AS select * from lbcat.test_table
| b5e043      | true     | select * from lbcat2.test_table | 4test_view                   | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`4test_view` AS select * from lbcat2.test_table
| a42686      | true     | select * from lbcat2.test_table | anotherlowerview             | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`anotherlowerview` AS select * from lbcat2.test_table
| 16916b      | true     | select * from lbcat2.test_table | crazy!@#\$%^&*()_+{}[]'"view | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view` AS select * from lbcat2.test_table
| eda719      | true     | select * from lbcat2.test_table | lowerview                    | lbcat2 | **plan**: CREATE VIEW `lbcat2`.`lowerview` AS select * from lbcat2.test_table

# Test Version: "86bdbf" #