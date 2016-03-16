**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can set remarks from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | object                               | objectType | remarks                  | OPERATIONS
| :---------- | :------- | :----------------------------------- | :--------- | :----------------------- | :------
| 612990      | true     | lbcat.4test_table                    | Table      |                          | **plan**: ALTER TABLE `lbcat`.`4test_table` COMMENT ''
| 9ec4c9      | true     | lbcat.4test_table                    | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat`.`4test_table` COMMENT 'Simple remarks'
| afed77      | true     | lbcat.4test_table                    | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat`.`4test_table` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 7bd03b      | true     | lbcat.anotherlowertable              | Table      |                          | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` COMMENT ''
| 48f400      | true     | lbcat.anotherlowertable              | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` COMMENT 'Simple remarks'
| d98832      | true     | lbcat.anotherlowertable              | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 00467f      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | Table      |                          | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` COMMENT ''
| 1dfc29      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` COMMENT 'Simple remarks'
| bdc569      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 0e89cd      | true     | lbcat.lowertable                     | Table      |                          | **plan**: ALTER TABLE `lbcat`.`lowertable` COMMENT ''
| 0e11f9      | true     | lbcat.lowertable                     | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat`.`lowertable` COMMENT 'Simple remarks'
| 4047fa      | true     | lbcat.lowertable                     | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat`.`lowertable` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| d147f7      | true     | lbcat.only_in_lbcat                  | Table      |                          | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` COMMENT ''
| 2a9d54      | true     | lbcat.only_in_lbcat                  | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` COMMENT 'Simple remarks'
| f040b5      | true     | lbcat.only_in_lbcat                  | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 7f6cf2      | true     | lbcat2.4test_table                   | Table      |                          | **plan**: ALTER TABLE `lbcat2`.`4test_table` COMMENT ''
| 3b1e3e      | true     | lbcat2.4test_table                   | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat2`.`4test_table` COMMENT 'Simple remarks'
| f44e1f      | true     | lbcat2.4test_table                   | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat2`.`4test_table` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 63fdae      | true     | lbcat2.anotherlowertable             | Table      |                          | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` COMMENT ''
| bd55b0      | true     | lbcat2.anotherlowertable             | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` COMMENT 'Simple remarks'
| e89f31      | true     | lbcat2.anotherlowertable             | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 435447      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | Table      |                          | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` COMMENT ''
| bd2bef      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` COMMENT 'Simple remarks'
| c2d530      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| cb785d      | true     | lbcat2.lowertable                    | Table      |                          | **plan**: ALTER TABLE `lbcat2`.`lowertable` COMMENT ''
| 70c7e9      | true     | lbcat2.lowertable                    | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat2`.`lowertable` COMMENT 'Simple remarks'
| 7877c5      | true     | lbcat2.lowertable                    | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat2`.`lowertable` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 06e9d1      | true     | lbcat2.only_in_lbcat2                | Table      |                          | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` COMMENT ''
| 4a2c25      | true     | lbcat2.only_in_lbcat2                | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` COMMENT 'Simple remarks'
| 56da23      | true     | lbcat2.only_in_lbcat2                | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'

# Test Version: "939134" #