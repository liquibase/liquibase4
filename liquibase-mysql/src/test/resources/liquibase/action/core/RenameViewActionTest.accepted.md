**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can rename from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | newName                             | oldName                             | OPERATIONS
| :---------- | :------- | :---------------------------------- | :---------------------------------- | :------
| 7e81bbe     | true     | lbcat.4test_view                    | lbcat.anotherlowerview              | **plan**: RENAME TABLE `lbcat`.`anotherlowerview` TO `lbcat`.`4test_view`
| 4b278d3     | true     | lbcat.4test_view                    | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat`.`4test_view`
| 9604b97     | true     | lbcat.4test_view                    | lbcat.lowerview                     | **plan**: RENAME TABLE `lbcat`.`lowerview` TO `lbcat`.`4test_view`
| a1b25ab     | true     | lbcat.4test_view                    | lbcat.only_in_lbcat                 | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`4test_view`
| b45c08b     | true     | lbcat.anotherlowerview              | lbcat.4test_view                    | **plan**: RENAME TABLE `lbcat`.`4test_view` TO `lbcat`.`anotherlowerview`
| 362a68a     | true     | lbcat.anotherlowerview              | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat`.`anotherlowerview`
| 3b411fe     | true     | lbcat.anotherlowerview              | lbcat.lowerview                     | **plan**: RENAME TABLE `lbcat`.`lowerview` TO `lbcat`.`anotherlowerview`
| 2ea6a8d     | true     | lbcat.anotherlowerview              | lbcat.only_in_lbcat                 | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`anotherlowerview`
| 3b4ef00     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | lbcat.4test_view                    | **plan**: RENAME TABLE `lbcat`.`4test_view` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view`
| be85ce0     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | lbcat.anotherlowerview              | **plan**: RENAME TABLE `lbcat`.`anotherlowerview` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view`
| 287dd03     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | lbcat.lowerview                     | **plan**: RENAME TABLE `lbcat`.`lowerview` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view`
| 34edbdf     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | lbcat.only_in_lbcat                 | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view`
| 7b05b78     | true     | lbcat.lowerview                     | lbcat.4test_view                    | **plan**: RENAME TABLE `lbcat`.`4test_view` TO `lbcat`.`lowerview`
| 62edbc2     | true     | lbcat.lowerview                     | lbcat.anotherlowerview              | **plan**: RENAME TABLE `lbcat`.`anotherlowerview` TO `lbcat`.`lowerview`
| 737a090     | true     | lbcat.lowerview                     | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat`.`lowerview`
| ac67ee1     | true     | lbcat.lowerview                     | lbcat.only_in_lbcat                 | **plan**: RENAME TABLE `lbcat`.`only_in_lbcat` TO `lbcat`.`lowerview`
| cbd0350     | true     | lbcat.only_in_lbcat                 | lbcat.4test_view                    | **plan**: RENAME TABLE `lbcat`.`4test_view` TO `lbcat`.`only_in_lbcat`
| cdfe9de     | true     | lbcat.only_in_lbcat                 | lbcat.anotherlowerview              | **plan**: RENAME TABLE `lbcat`.`anotherlowerview` TO `lbcat`.`only_in_lbcat`
| 8325892     | true     | lbcat.only_in_lbcat                 | lbcat.crazy!@#\$%^&*()_+{}[]'"view  | **plan**: RENAME TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat`.`only_in_lbcat`
| 7d6072c     | true     | lbcat.only_in_lbcat                 | lbcat.lowerview                     | **plan**: RENAME TABLE `lbcat`.`lowerview` TO `lbcat`.`only_in_lbcat`
| 16d429d     | true     | lbcat2.4test_view                   | lbcat2.anotherlowerview             | **plan**: RENAME TABLE `lbcat2`.`anotherlowerview` TO `lbcat2`.`4test_view`
| 7d2cfe1     | true     | lbcat2.4test_view                   | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat2`.`4test_view`
| 0067ea7     | true     | lbcat2.4test_view                   | lbcat2.lowerview                    | **plan**: RENAME TABLE `lbcat2`.`lowerview` TO `lbcat2`.`4test_view`
| 24933aa     | true     | lbcat2.4test_view                   | lbcat2.only_in_lbcat2               | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`4test_view`
| da65611     | true     | lbcat2.anotherlowerview             | lbcat2.4test_view                   | **plan**: RENAME TABLE `lbcat2`.`4test_view` TO `lbcat2`.`anotherlowerview`
| d23da76     | true     | lbcat2.anotherlowerview             | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat2`.`anotherlowerview`
| e943e51     | true     | lbcat2.anotherlowerview             | lbcat2.lowerview                    | **plan**: RENAME TABLE `lbcat2`.`lowerview` TO `lbcat2`.`anotherlowerview`
| 2e72047     | true     | lbcat2.anotherlowerview             | lbcat2.only_in_lbcat2               | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`anotherlowerview`
| 0e1c111     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | lbcat2.4test_view                   | **plan**: RENAME TABLE `lbcat2`.`4test_view` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view`
| b6c6442     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | lbcat2.anotherlowerview             | **plan**: RENAME TABLE `lbcat2`.`anotherlowerview` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view`
| ac9c019     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | lbcat2.lowerview                    | **plan**: RENAME TABLE `lbcat2`.`lowerview` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view`
| e5e36fd     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | lbcat2.only_in_lbcat2               | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view`
| a042bd5     | true     | lbcat2.lowerview                    | lbcat2.4test_view                   | **plan**: RENAME TABLE `lbcat2`.`4test_view` TO `lbcat2`.`lowerview`
| b224807     | true     | lbcat2.lowerview                    | lbcat2.anotherlowerview             | **plan**: RENAME TABLE `lbcat2`.`anotherlowerview` TO `lbcat2`.`lowerview`
| ad16035     | true     | lbcat2.lowerview                    | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat2`.`lowerview`
| a9ffada     | true     | lbcat2.lowerview                    | lbcat2.only_in_lbcat2               | **plan**: RENAME TABLE `lbcat2`.`only_in_lbcat2` TO `lbcat2`.`lowerview`
| 20ffa17     | true     | lbcat2.only_in_lbcat2               | lbcat2.4test_view                   | **plan**: RENAME TABLE `lbcat2`.`4test_view` TO `lbcat2`.`only_in_lbcat2`
| 2b09238     | true     | lbcat2.only_in_lbcat2               | lbcat2.anotherlowerview             | **plan**: RENAME TABLE `lbcat2`.`anotherlowerview` TO `lbcat2`.`only_in_lbcat2`
| bb4e31f     | true     | lbcat2.only_in_lbcat2               | lbcat2.crazy!@#\$%^&*()_+{}[]'"view | **plan**: RENAME TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"view` TO `lbcat2`.`only_in_lbcat2`
| 8418099     | true     | lbcat2.only_in_lbcat2               | lbcat2.lowerview                    | **plan**: RENAME TABLE `lbcat2`.`lowerview` TO `lbcat2`.`only_in_lbcat2`

# Test Version: "9ce0a5" #