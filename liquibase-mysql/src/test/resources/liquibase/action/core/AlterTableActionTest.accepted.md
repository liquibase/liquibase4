**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can alter tables" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | newDefinition | table                                | OPERATIONS
| :---------- | :------- | :------------ | :----------------------------------- | :------
| 5375d8      | true     | add id int    | lbcat.4test_table                    | **plan**: ALTER TABLE `lbcat`.`4test_table` add id int
| 40736a      | true     | add id int    | lbcat.anotherlowertable              | **plan**: ALTER TABLE `lbcat`.`anotherlowertable` add id int
| bfb112      | true     | add id int    | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | **plan**: ALTER TABLE `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` add id int
| 98d1e0      | true     | add id int    | lbcat.lowertable                     | **plan**: ALTER TABLE `lbcat`.`lowertable` add id int
| 8952e3      | true     | add id int    | lbcat.only_in_lbcat                  | **plan**: ALTER TABLE `lbcat`.`only_in_lbcat` add id int
| e10e64      | true     | add id int    | lbcat2.4test_table                   | **plan**: ALTER TABLE `lbcat2`.`4test_table` add id int
| 7a4e3f      | true     | add id int    | lbcat2.anotherlowertable             | **plan**: ALTER TABLE `lbcat2`.`anotherlowertable` add id int
| d584ac      | true     | add id int    | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | **plan**: ALTER TABLE `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` add id int
| 34526e      | true     | add id int    | lbcat2.lowertable                    | **plan**: ALTER TABLE `lbcat2`.`lowertable` add id int
| 58e26b      | true     | add id int    | lbcat2.only_in_lbcat2                | **plan**: ALTER TABLE `lbcat2`.`only_in_lbcat2` add id int

# Test Version: "57a643" #