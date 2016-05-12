**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can delete from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | relation                             | where | OPERATIONS
| :---------- | :------- | :----------------------------------- | :---- | :------
| ea119db     | true     | lbcat.4test_table                    |       | **plan**: DELETE FROM `lbcat`.`4test_table`
| d8a3b88     | true     | lbcat.4test_table                    | 1=1   | **plan**: DELETE FROM `lbcat`.`4test_table` WHERE 1=1
| 98b0c32     | true     | lbcat.anotherlowertable              |       | **plan**: DELETE FROM `lbcat`.`anotherlowertable`
| f705047     | true     | lbcat.anotherlowertable              | 1=1   | **plan**: DELETE FROM `lbcat`.`anotherlowertable` WHERE 1=1
| 873363f     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  |       | **plan**: DELETE FROM `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| 4747120     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | 1=1   | **plan**: DELETE FROM `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table` WHERE 1=1
| 86ee260     | true     | lbcat.lowertable                     |       | **plan**: DELETE FROM `lbcat`.`lowertable`
| 6afae3a     | true     | lbcat.lowertable                     | 1=1   | **plan**: DELETE FROM `lbcat`.`lowertable` WHERE 1=1
| 02fbb5a     | true     | lbcat.only_in_lbcat                  |       | **plan**: DELETE FROM `lbcat`.`only_in_lbcat`
| 99db744     | true     | lbcat.only_in_lbcat                  | 1=1   | **plan**: DELETE FROM `lbcat`.`only_in_lbcat` WHERE 1=1
| bcce77a     | true     | lbcat2.4test_table                   |       | **plan**: DELETE FROM `lbcat2`.`4test_table`
| 1cac956     | true     | lbcat2.4test_table                   | 1=1   | **plan**: DELETE FROM `lbcat2`.`4test_table` WHERE 1=1
| e2d1876     | true     | lbcat2.anotherlowertable             |       | **plan**: DELETE FROM `lbcat2`.`anotherlowertable`
| 6856ae1     | true     | lbcat2.anotherlowertable             | 1=1   | **plan**: DELETE FROM `lbcat2`.`anotherlowertable` WHERE 1=1
| 95908e3     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table |       | **plan**: DELETE FROM `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| 9619ad7     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | 1=1   | **plan**: DELETE FROM `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table` WHERE 1=1
| 0090300     | true     | lbcat2.lowertable                    |       | **plan**: DELETE FROM `lbcat2`.`lowertable`
| 309a4f7     | true     | lbcat2.lowertable                    | 1=1   | **plan**: DELETE FROM `lbcat2`.`lowertable` WHERE 1=1
| fffce03     | true     | lbcat2.only_in_lbcat2                |       | **plan**: DELETE FROM `lbcat2`.`only_in_lbcat2`
| a1c4b13     | true     | lbcat2.only_in_lbcat2                | 1=1   | **plan**: DELETE FROM `lbcat2`.`only_in_lbcat2` WHERE 1=1

# Test Version: "bb6047" #