**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can drop from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | name | relation                             | OPERATIONS
| :---------- | :------- | :--- | :----------------------------------- | :------
| 5383ccd     | true     | null | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` DROP PRIMARY KEY
| 8d8d365     | true     | null | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` DROP PRIMARY KEY
| d2c8ffc     | true     | null | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` DROP PRIMARY KEY
| 12b942b     | true     | null | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` DROP PRIMARY KEY
| af7ec2e     | true     | null | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` DROP PRIMARY KEY
| d6fae84     | true     | null | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` DROP PRIMARY KEY
| e3c90d3     | true     | null | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` DROP PRIMARY KEY
| 4d3866d     | true     | null | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` DROP PRIMARY KEY
| b9b3a95     | true     | null | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` DROP PRIMARY KEY
| 0d7e95c     | true     | null | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` DROP PRIMARY KEY

# Test Version: "482672" #