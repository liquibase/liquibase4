**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Finds multi-column PKs correctly" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | schema | OPERATIONS
| :---------- | :------- | :----- | :------
| 43a3ea      | true     | lbcat  | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat' AND TABLE_NAME='table_name'
| 06284b      | true     | lbcat2 | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat2' AND TABLE_NAME='table_name'

# Test: "can find all primaryKeys in a fully qualified complex table name" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | table                                | OPERATIONS
| :---------- | :------- | :----------------------------------- | :------
| c714c6      | true     | lbcat.4test_table                    | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat' AND TABLE_NAME='4test_table'
| 492248      | true     | lbcat.anotherlowertable              | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat' AND TABLE_NAME='anotherlowertable'
| 2f78b2      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat' AND TABLE_NAME='crazy!@#\\$%^&*()_+{}[]''"table'
| 7c583f      | true     | lbcat.lowertable                     | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat' AND TABLE_NAME='lowertable'
| d93a8c      | true     | lbcat.only_in_lbcat                  | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat' AND TABLE_NAME='only_in_lbcat'
| 352c7c      | true     | lbcat2.4test_table                   | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat2' AND TABLE_NAME='4test_table'
| f78fdd      | true     | lbcat2.anotherlowertable             | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat2' AND TABLE_NAME='anotherlowertable'
| 1a22ce      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat2' AND TABLE_NAME='crazy!@#\\$%^&*()_+{}[]''"table'
| ac925c      | true     | lbcat2.lowertable                    | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat2' AND TABLE_NAME='lowertable'
| cb2f55      | true     | lbcat2.only_in_lbcat2                | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat2' AND TABLE_NAME='only_in_lbcat2'

# Test: "can find by PrimaryKeyReference with a table name but null primary key name" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | pk                                              | OPERATIONS
| :---------- | :------- | :---------------------------------------------- | :------
| 4c6df7      | true     | UNNAMED on lbcat.4test_table                    | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat' AND TABLE_NAME='4test_table'
| 9842bb      | true     | UNNAMED on lbcat.anotherlowertable              | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat' AND TABLE_NAME='anotherlowertable'
| 79f4df      | true     | UNNAMED on lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat' AND TABLE_NAME='crazy!@#\\$%^&*()_+{}[]''"table'
| dffa31      | true     | UNNAMED on lbcat.lowertable                     | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat' AND TABLE_NAME='lowertable'
| 8030b6      | true     | UNNAMED on lbcat.only_in_lbcat                  | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat' AND TABLE_NAME='only_in_lbcat'
| ab73c0      | true     | UNNAMED on lbcat2.4test_table                   | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat2' AND TABLE_NAME='4test_table'
| 2b510c      | true     | UNNAMED on lbcat2.anotherlowertable             | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat2' AND TABLE_NAME='anotherlowertable'
| 3a6aaa      | true     | UNNAMED on lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat2' AND TABLE_NAME='crazy!@#\\$%^&*()_+{}[]''"table'
| ab3441      | true     | UNNAMED on lbcat2.lowertable                    | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat2' AND TABLE_NAME='lowertable'
| 92cfd9      | true     | UNNAMED on lbcat2.only_in_lbcat2                | **plan**: SELECT `TABLE_SCHEMA` AS TABLE_CAT, NULL AS TABLE_SCHEM, `TABLE_NAME`, `COLUMN_NAME`, `SEQ_IN_INDEX` AS KEY_SEQ, 'PRIMARY' AS PK_NAME FROM `INFORMATION_SCHEMA`.`STATISTICS` WHERE INDEX_NAME='PRIMARY' AND TABLE_SCHEMA='lbcat2' AND TABLE_NAME='only_in_lbcat2'

# Test Version: "2dd6e1" #