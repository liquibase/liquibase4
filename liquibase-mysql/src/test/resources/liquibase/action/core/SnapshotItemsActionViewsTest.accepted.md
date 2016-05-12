**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can find all views using a null name " #

- **connection:** mysql caseInsensitive

| Permutation | Verified | view           | OPERATIONS
| :---------- | :------- | :------------- | :------
| 3febd78     | true     | lbcat.UNNAMED  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_SCHEMA='lbcat'
| 3267d71     | true     | lbcat2.UNNAMED | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_SCHEMA='lbcat2'

# Test: "can find all views using schema reference" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | view   | OPERATIONS
| :---------- | :------- | :----- | :------
| fb317d2     | true     | lbcat  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_SCHEMA='lbcat'
| 5cdb101     | true     | lbcat2 | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_SCHEMA='lbcat2'

# Test: "can find complex view names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | view                                | OPERATIONS
| :---------- | :------- | :---------------------------------- | :------
| 9690a66     | true     | lbcat.4test_view                    | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='4test_view' AND TABLE_SCHEMA='lbcat'
| 69f9c4a     | true     | lbcat.anotherlowerview              | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='anotherlowerview' AND TABLE_SCHEMA='lbcat'
| 86d58ac     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='crazy!@#\\$%^&*()_+{}[]''"view' AND TABLE_SCHEMA='lbcat'
| 21cd302     | true     | lbcat.lowerview                     | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='lowerview' AND TABLE_SCHEMA='lbcat'
| b4a6e72     | true     | lbcat.only_in_lbcat                 | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='only_in_lbcat' AND TABLE_SCHEMA='lbcat'
| 1508961     | true     | lbcat2.4test_view                   | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='4test_view' AND TABLE_SCHEMA='lbcat2'
| d8d4beb     | true     | lbcat2.anotherlowerview             | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='anotherlowerview' AND TABLE_SCHEMA='lbcat2'
| 655b26a     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='crazy!@#\\$%^&*()_+{}[]''"view' AND TABLE_SCHEMA='lbcat2'
| eea9d3a     | true     | lbcat2.lowerview                    | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='lowerview' AND TABLE_SCHEMA='lbcat2'
| 31a3a97     | true     | lbcat2.only_in_lbcat2               | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='only_in_lbcat2' AND TABLE_SCHEMA='lbcat2'

# Test: "can find views with various options" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | definition                      | name                         | schema | OPERATIONS
| :---------- | :------- | :------------------------------ | :--------------------------- | :----- | :------
| b2801b3     | true     | select * from lbcat.test_table  | 4test_view                   | lbcat  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='4test_view' AND TABLE_SCHEMA='lbcat'
| 1dafb46     | true     | select * from lbcat.test_table  | anotherlowerview             | lbcat  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='anotherlowerview' AND TABLE_SCHEMA='lbcat'
| d486b73     | true     | select * from lbcat.test_table  | crazy!@#\$%^&*()_+{}[]'"view | lbcat  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='crazy!@#\\$%^&*()_+{}[]''"view' AND TABLE_SCHEMA='lbcat'
| 8c61341     | true     | select * from lbcat.test_table  | lowerview                    | lbcat  | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='lowerview' AND TABLE_SCHEMA='lbcat'
| b5e043f     | true     | select * from lbcat2.test_table | 4test_view                   | lbcat2 | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='4test_view' AND TABLE_SCHEMA='lbcat2'
| a426863     | true     | select * from lbcat2.test_table | anotherlowerview             | lbcat2 | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='anotherlowerview' AND TABLE_SCHEMA='lbcat2'
| 16916ba     | true     | select * from lbcat2.test_table | crazy!@#\$%^&*()_+{}[]'"view | lbcat2 | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='crazy!@#\\$%^&*()_+{}[]''"view' AND TABLE_SCHEMA='lbcat2'
| eda719d     | true     | select * from lbcat2.test_table | lowerview                    | lbcat2 | **plan**: SELECT `TABLE_NAME`, `VIEW_DEFINITION`, `TABLE_SCHEMA` FROM `INFORMATION_SCHEMA`.`VIEWS` WHERE TABLE_NAME='lowerview' AND TABLE_SCHEMA='lbcat2'

# Test Version: "8e10a2" #