**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can find all views using a null name " #

- **connection:** mysql caseInsensitive

| Permutation | Verified | view           | OPERATIONS
| :---------- | :------- | :------------- | :------
| 3febd7      | true     | lbcat.UNNAMED  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_SCHEMA='lbcat'
| 3267d7      | true     | lbcat2.UNNAMED | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_SCHEMA='lbcat2'

# Test: "can find all views using schema reference" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | view   | OPERATIONS
| :---------- | :------- | :----- | :------
| fb317d      | true     | lbcat  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_SCHEMA='lbcat'
| 5cdb10      | true     | lbcat2 | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_SCHEMA='lbcat2'

# Test: "can find complex view names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | view                                | OPERATIONS
| :---------- | :------- | :---------------------------------- | :------
| 9690a6      | true     | lbcat.4test_view                    | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='4test_view' AND TABLE_SCHEMA='lbcat'
| 69f9c4      | true     | lbcat.anotherlowerview              | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='anotherlowerview' AND TABLE_SCHEMA='lbcat'
| 86d58a      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='crazy!@#\\$%^&*()_+{}[]''"view' AND TABLE_SCHEMA='lbcat'
| 21cd30      | true     | lbcat.lowerview                     | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='lowerview' AND TABLE_SCHEMA='lbcat'
| b4a6e7      | true     | lbcat.only_in_lbcat                 | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='only_in_lbcat' AND TABLE_SCHEMA='lbcat'
| 150896      | true     | lbcat2.4test_view                   | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='4test_view' AND TABLE_SCHEMA='lbcat2'
| d8d4be      | true     | lbcat2.anotherlowerview             | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='anotherlowerview' AND TABLE_SCHEMA='lbcat2'
| 655b26      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='crazy!@#\\$%^&*()_+{}[]''"view' AND TABLE_SCHEMA='lbcat2'
| eea9d3      | true     | lbcat2.lowerview                    | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='lowerview' AND TABLE_SCHEMA='lbcat2'
| 31a3a9      | true     | lbcat2.only_in_lbcat2               | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='only_in_lbcat2' AND TABLE_SCHEMA='lbcat2'

# Test: "can find views with various options" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | completeDefinition | definition                      | name                         | schema | OPERATIONS
| :---------- | :------- | :----------------- | :------------------------------ | :--------------------------- | :----- | :------
| 9b800a      | true     | false              | select * from lbcat.test_table  | 4test_view                   | lbcat  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='4test_view' AND TABLE_SCHEMA='lbcat'
| c412fd      | true     | false              | select * from lbcat.test_table  | anotherlowerview             | lbcat  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='anotherlowerview' AND TABLE_SCHEMA='lbcat'
| 66f81f      | true     | false              | select * from lbcat.test_table  | crazy!@#\$%^&*()_+{}[]'"view | lbcat  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='crazy!@#\\$%^&*()_+{}[]''"view' AND TABLE_SCHEMA='lbcat'
| 12736a      | true     | false              | select * from lbcat.test_table  | lowerview                    | lbcat  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='lowerview' AND TABLE_SCHEMA='lbcat'
| 13848d      | true     | false              | select * from lbcat2.test_table | 4test_view                   | lbcat2 | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='4test_view' AND TABLE_SCHEMA='lbcat2'
| b59bdf      | true     | false              | select * from lbcat2.test_table | anotherlowerview             | lbcat2 | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='anotherlowerview' AND TABLE_SCHEMA='lbcat2'
| 51aa18      | true     | false              | select * from lbcat2.test_table | crazy!@#\$%^&*()_+{}[]'"view | lbcat2 | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='crazy!@#\\$%^&*()_+{}[]''"view' AND TABLE_SCHEMA='lbcat2'
| 27e193      | true     | false              | select * from lbcat2.test_table | lowerview                    | lbcat2 | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='lowerview' AND TABLE_SCHEMA='lbcat2'

# Test Version: "5b6153" #