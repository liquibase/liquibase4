**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can alter tables" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | newDefinition | table                                | OPERATIONS
| :---------- | :------- | :------------ | :----------------------------------- | :------
| 5375d8d     | true     | add id int    | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` add id int
| 40736a0     | true     | add id int    | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` add id int
| bfb1122     | true     | add id int    | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` add id int
| 98d1e07     | true     | add id int    | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` add id int
| 8952e32     | true     | add id int    | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` add id int
| e10e642     | true     | add id int    | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` add id int
| 7a4e3fb     | true     | add id int    | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` add id int
| d584ac9     | true     | add id int    | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` add id int
| 34526ed     | true     | add id int    | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` add id int
| 58e26bd     | true     | add id int    | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` add id int

# Test Version: "be4a91" #