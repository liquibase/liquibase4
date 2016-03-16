**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can rename from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | newName                             | oldName                             | OPERATIONS
| :---------- | :------- | :---------------------------------- | :---------------------------------- | :------
| 7e81bb      | true     | lbcat.4test_view                    | lbcat.anotherlowerview              | **plan**: RENAME TABLE `lbcat`.`anotherlowerview` TO `lbcat`.`4test_view`
| 4b278d      | true     | lbcat.4test_view                    | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat`.`4test_view`
| 9604b9      | true     | lbcat.4test_view                    | lbcat.lowerview                     | **plan**: RENAME TABLE `lbcat`.`lowerview` TO `lbcat`.`4test_view`
| a1b25a      | true     | lbcat.4test_view                    | lbcat.only_in_lbcat                 | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`4test_view`
| b45c08      | true     | lbcat.anotherlowerview              | lbcat.4test_view                    | **plan**: RENAME TABLE `lbcat`.`4test_view` TO `lbcat`.`anotherlowerview`
| 362a68      | true     | lbcat.anotherlowerview              | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat`.`anotherlowerview`
| 3b411f      | true     | lbcat.anotherlowerview              | lbcat.lowerview                     | **plan**: RENAME TABLE `lbcat`.`lowerview` TO `lbcat`.`anotherlowerview`
| 2ea6a8      | true     | lbcat.anotherlowerview              | lbcat.only_in_lbcat                 | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`anotherlowerview`
| 3b4ef0      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | lbcat.4test_view                    | **plan**: RENAME TABLE `lbcat`.`4test_view` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view`
| be85ce      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | lbcat.anotherlowerview              | **plan**: RENAME TABLE `lbcat`.`anotherlowerview` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view`
| 287dd0      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | lbcat.lowerview                     | **plan**: RENAME TABLE `lbcat`.`lowerview` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view`
| 34edbd      | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | lbcat.only_in_lbcat                 | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view`
| 7b05b7      | true     | lbcat.lowerview                     | lbcat.4test_view                    | **plan**: RENAME TABLE `lbcat`.`4test_view` TO `lbcat`.`lowerview`
| 62edbc      | true     | lbcat.lowerview                     | lbcat.anotherlowerview              | **plan**: RENAME TABLE `lbcat`.`anotherlowerview` TO `lbcat`.`lowerview`
| 737a09      | true     | lbcat.lowerview                     | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat`.`lowerview`
| ac67ee      | true     | lbcat.lowerview                     | lbcat.only_in_lbcat                 | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`lowerview`
| cbd035      | true     | lbcat.only_in_lbcat                 | lbcat.4test_view                    | **plan**: RENAME TABLE `lbcat`.`4test_view` TO `lbcat`.`only_in_lbcat`
| cdfe9d      | true     | lbcat.only_in_lbcat                 | lbcat.anotherlowerview              | **plan**: RENAME TABLE `lbcat`.`anotherlowerview` TO `lbcat`.`only_in_lbcat`
| 832589      | true     | lbcat.only_in_lbcat                 | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat`.`only_in_lbcat`
| 7d6072      | true     | lbcat.only_in_lbcat                 | lbcat.lowerview                     | **plan**: RENAME TABLE `lbcat`.`lowerview` TO `lbcat`.`only_in_lbcat`
| 16d429      | true     | lbcat2.4test_view                   | lbcat2.anotherlowerview             | **plan**: RENAME TABLE `lbcat2`.`anotherlowerview` TO `lbcat2`.`4test_view`
| 7d2cfe      | true     | lbcat2.4test_view                   | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat2`.`4test_view`
| 0067ea      | true     | lbcat2.4test_view                   | lbcat2.lowerview                    | **plan**: RENAME TABLE `lbcat2`.`lowerview` TO `lbcat2`.`4test_view`
| 24933a      | true     | lbcat2.4test_view                   | lbcat2.only_in_lbcat2               | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`4test_view`
| da6561      | true     | lbcat2.anotherlowerview             | lbcat2.4test_view                   | **plan**: RENAME TABLE `lbcat2`.`4test_view` TO `lbcat2`.`anotherlowerview`
| d23da7      | true     | lbcat2.anotherlowerview             | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat2`.`anotherlowerview`
| e943e5      | true     | lbcat2.anotherlowerview             | lbcat2.lowerview                    | **plan**: RENAME TABLE `lbcat2`.`lowerview` TO `lbcat2`.`anotherlowerview`
| 2e7204      | true     | lbcat2.anotherlowerview             | lbcat2.only_in_lbcat2               | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`anotherlowerview`
| 0e1c11      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | lbcat2.4test_view                   | **plan**: RENAME TABLE `lbcat2`.`4test_view` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view`
| b6c644      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | lbcat2.anotherlowerview             | **plan**: RENAME TABLE `lbcat2`.`anotherlowerview` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view`
| ac9c01      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | lbcat2.lowerview                    | **plan**: RENAME TABLE `lbcat2`.`lowerview` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view`
| e5e36f      | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | lbcat2.only_in_lbcat2               | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view`
| a042bd      | true     | lbcat2.lowerview                    | lbcat2.4test_view                   | **plan**: RENAME TABLE `lbcat2`.`4test_view` TO `lbcat2`.`lowerview`
| b22480      | true     | lbcat2.lowerview                    | lbcat2.anotherlowerview             | **plan**: RENAME TABLE `lbcat2`.`anotherlowerview` TO `lbcat2`.`lowerview`
| ad1603      | true     | lbcat2.lowerview                    | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat2`.`lowerview`
| a9ffad      | true     | lbcat2.lowerview                    | lbcat2.only_in_lbcat2               | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`lowerview`
| 20ffa1      | true     | lbcat2.only_in_lbcat2               | lbcat2.4test_view                   | **plan**: RENAME TABLE `lbcat2`.`4test_view` TO `lbcat2`.`only_in_lbcat2`
| 2b0923      | true     | lbcat2.only_in_lbcat2               | lbcat2.anotherlowerview             | **plan**: RENAME TABLE `lbcat2`.`anotherlowerview` TO `lbcat2`.`only_in_lbcat2`
| bb4e31      | true     | lbcat2.only_in_lbcat2               | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat2`.`only_in_lbcat2`
| 841809      | true     | lbcat2.only_in_lbcat2               | lbcat2.lowerview                    | **plan**: RENAME TABLE `lbcat2`.`lowerview` TO `lbcat2`.`only_in_lbcat2`

# Test Version: "81a5e7" #