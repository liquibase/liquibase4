**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can drop from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | name                                     | relation                             | OPERATIONS
| :---------- | :------- | :--------------------------------------- | :----------------------------------- | :------
| 896feb9     | true     | 4test_uniqueconstraint                   | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` DROP KEY `4test_uniqueconstraint`
| 7336a3c     | true     | 4test_uniqueconstraint                   | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` DROP KEY `4test_uniqueconstraint`
| bca7dcd     | true     | 4test_uniqueconstraint                   | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` DROP KEY `4test_uniqueconstraint`
| 950208c     | true     | 4test_uniqueconstraint                   | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` DROP KEY `4test_uniqueconstraint`
| d00bb2d     | true     | 4test_uniqueconstraint                   | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` DROP KEY `4test_uniqueconstraint`
| 2d97c0b     | true     | 4test_uniqueconstraint                   | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` DROP KEY `4test_uniqueconstraint`
| aaff54e     | true     | 4test_uniqueconstraint                   | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` DROP KEY `4test_uniqueconstraint`
| 9807675     | true     | 4test_uniqueconstraint                   | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` DROP KEY `4test_uniqueconstraint`
| 8413925     | true     | 4test_uniqueconstraint                   | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` DROP KEY `4test_uniqueconstraint`
| 3e39df8     | true     | 4test_uniqueconstraint                   | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` DROP KEY `4test_uniqueconstraint`
| 1d8bd7d     | true     | anotherloweruniqueconstraint             | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` DROP KEY `anotherloweruniqueconstraint`
| 2aabc44     | true     | anotherloweruniqueconstraint             | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` DROP KEY `anotherloweruniqueconstraint`
| d174145     | true     | anotherloweruniqueconstraint             | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` DROP KEY `anotherloweruniqueconstraint`
| 4f55531     | true     | anotherloweruniqueconstraint             | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` DROP KEY `anotherloweruniqueconstraint`
| b9c72f1     | true     | anotherloweruniqueconstraint             | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` DROP KEY `anotherloweruniqueconstraint`
| 56ad5ce     | true     | anotherloweruniqueconstraint             | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` DROP KEY `anotherloweruniqueconstraint`
| 701abc8     | true     | anotherloweruniqueconstraint             | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` DROP KEY `anotherloweruniqueconstraint`
| 8f05b50     | true     | anotherloweruniqueconstraint             | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` DROP KEY `anotherloweruniqueconstraint`
| 411a890     | true     | anotherloweruniqueconstraint             | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` DROP KEY `anotherloweruniqueconstraint`
| 6f57198     | true     | anotherloweruniqueconstraint             | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` DROP KEY `anotherloweruniqueconstraint`
| f7f5e9f     | true     | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` DROP KEY `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint`
| 0f43889     | true     | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` DROP KEY `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint`
| 82be960     | true     | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` DROP KEY `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint`
| 6956c66     | true     | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` DROP KEY `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint`
| ebb035e     | true     | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` DROP KEY `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint`
| 3773866     | true     | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` DROP KEY `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint`
| 0c3968a     | true     | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` DROP KEY `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint`
| 95859e6     | true     | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` DROP KEY `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint`
| 8631e53     | true     | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` DROP KEY `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint`
| 62a014c     | true     | crazy!@#\$%^&*()_+{}[]'"uniqueconstraint | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` DROP KEY `crazy!@#\$%^&*()_+{}[]'"uniqueconstraint`
| 8b3779a     | true     | loweruniqueconstraint                    | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` DROP KEY `loweruniqueconstraint`
| d78763c     | true     | loweruniqueconstraint                    | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` DROP KEY `loweruniqueconstraint`
| 070d873     | true     | loweruniqueconstraint                    | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` DROP KEY `loweruniqueconstraint`
| 28b8b3e     | true     | loweruniqueconstraint                    | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` DROP KEY `loweruniqueconstraint`
| f9c8dd5     | true     | loweruniqueconstraint                    | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` DROP KEY `loweruniqueconstraint`
| b9a3d38     | true     | loweruniqueconstraint                    | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` DROP KEY `loweruniqueconstraint`
| 9c35383     | true     | loweruniqueconstraint                    | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` DROP KEY `loweruniqueconstraint`
| 1fff7dd     | true     | loweruniqueconstraint                    | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` DROP KEY `loweruniqueconstraint`
| 5a730bd     | true     | loweruniqueconstraint                    | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` DROP KEY `loweruniqueconstraint`
| 8871bed     | true     | loweruniqueconstraint                    | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` DROP KEY `loweruniqueconstraint`

# Test Version: "7c6f2f" #