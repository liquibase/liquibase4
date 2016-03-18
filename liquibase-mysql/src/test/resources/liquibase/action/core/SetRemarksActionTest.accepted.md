**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can set remarks from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | object                               | objectType | remarks                  | OPERATIONS
| :---------- | :------- | :----------------------------------- | :--------- | :----------------------- | :------
| 6129901     | true     | lbcat.4test_table                    | Table      |                          | **plan**: ALTER TABLE `lbcat`.`4test_table` COMMENT ''
| 9ec4c94     | true     | lbcat.4test_table                    | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat`.`4test_table` COMMENT 'Simple remarks'
| afed778     | true     | lbcat.4test_table                    | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat`.`4test_table` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 7bd03b2     | true     | lbcat.anotherlowertable              | Table      |                          | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` COMMENT ''
| 48f4001     | true     | lbcat.anotherlowertable              | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` COMMENT 'Simple remarks'
| d988323     | true     | lbcat.anotherlowertable              | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 00467fd     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | Table      |                          | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` COMMENT ''
| 1dfc297     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` COMMENT 'Simple remarks'
| bdc5694     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 0e89cd1     | true     | lbcat.lowertable                     | Table      |                          | **plan**: ALTER TABLE `lbcat`.`lowertable` COMMENT ''
| 0e11f9e     | true     | lbcat.lowertable                     | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat`.`lowertable` COMMENT 'Simple remarks'
| 4047fac     | true     | lbcat.lowertable                     | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat`.`lowertable` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| d147f75     | true     | lbcat.only_in_lbcat                  | Table      |                          | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` COMMENT ''
| 2a9d548     | true     | lbcat.only_in_lbcat                  | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` COMMENT 'Simple remarks'
| f040b54     | true     | lbcat.only_in_lbcat                  | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 7f6cf27     | true     | lbcat2.4test_table                   | Table      |                          | **plan**: ALTER TABLE `lbcat2`.`4test_table` COMMENT ''
| 3b1e3ec     | true     | lbcat2.4test_table                   | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat2`.`4test_table` COMMENT 'Simple remarks'
| f44e1fe     | true     | lbcat2.4test_table                   | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat2`.`4test_table` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 63fdae3     | true     | lbcat2.anotherlowertable             | Table      |                          | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` COMMENT ''
| bd55b07     | true     | lbcat2.anotherlowertable             | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` COMMENT 'Simple remarks'
| e89f317     | true     | lbcat2.anotherlowertable             | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 4354479     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | Table      |                          | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` COMMENT ''
| bd2bef2     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` COMMENT 'Simple remarks'
| c2d5308     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| cb785d3     | true     | lbcat2.lowertable                    | Table      |                          | **plan**: ALTER TABLE `lbcat2`.`lowertable` COMMENT ''
| 70c7e98     | true     | lbcat2.lowertable                    | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat2`.`lowertable` COMMENT 'Simple remarks'
| 7877c5f     | true     | lbcat2.lowertable                    | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat2`.`lowertable` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'
| 06e9d16     | true     | lbcat2.only_in_lbcat2                | Table      |                          | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` COMMENT ''
| 4a2c255     | true     | lbcat2.only_in_lbcat2                | Table      | Simple remarks           | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` COMMENT 'Simple remarks'
| 56da23b     | true     | lbcat2.only_in_lbcat2                | Table      | crazy!@#\$%^&*()_+{}[]'" | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` COMMENT 'crazy!@#\\$%^&*()_+{}[]''"'

# Test Version: "c3058d" #