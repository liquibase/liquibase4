**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can drop from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | indexName                     | indexRelation                        | indexSchema | OPERATIONS
| :---------- | :------- | :---------------------------- | :----------------------------------- | :---------- | :------
| ad0142      | true     | ANOTHERUPPERINDEX             | lbcat.4test_table                    | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat`.`4test_table`
| ade56a      | true     | ANOTHERUPPERINDEX             | lbcat.anotherlowertable              | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat`.`anotherlowertable`
| 3580c6      | true     | ANOTHERUPPERINDEX             | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| 3fa996      | true     | ANOTHERUPPERINDEX             | lbcat.lowertable                     | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat`.`lowertable`
| fea5e9      | true     | ANOTHERUPPERINDEX             | lbcat.only_in_lbcat                  | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat`.`only_in_lbcat`
| c0988b      | true     | ANOTHERUPPERINDEX             | lbcat2.4test_table                   | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat2`.`4test_table`
| e5e1bf      | true     | ANOTHERUPPERINDEX             | lbcat2.anotherlowertable             | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat2`.`anotherlowertable`
| c73cf2      | true     | ANOTHERUPPERINDEX             | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| c99bab      | true     | ANOTHERUPPERINDEX             | lbcat2.lowertable                    | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat2`.`lowertable`
| 9b836e      | true     | ANOTHERUPPERINDEX             | lbcat2.only_in_lbcat2                | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat2`.`only_in_lbcat2`
| 41024b      | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat.4test_table                    | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat`.`4test_table`
| f37062      | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat.anotherlowertable              | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat`.`anotherlowertable`
| 305826      | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| fb00b7      | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat.lowertable                     | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat`.`lowertable`
| b0dc56      | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat.only_in_lbcat                  | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat`.`only_in_lbcat`
| f22dcd      | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat2.4test_table                   | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat2`.`4test_table`
| 7d0aa7      | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat2.anotherlowertable             | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat2`.`anotherlowertable`
| f66740      | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| 5cad04      | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat2.lowertable                    | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat2`.`lowertable`
| 11eafa      | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat2.only_in_lbcat2                | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat2`.`only_in_lbcat2`
| 86b309      | true     | UPPERINDEX                    | lbcat.4test_table                    | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat`.`4test_table`
| 47d8eb      | true     | UPPERINDEX                    | lbcat.anotherlowertable              | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat`.`anotherlowertable`
| 8afcd8      | true     | UPPERINDEX                    | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| 8e9572      | true     | UPPERINDEX                    | lbcat.lowertable                     | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat`.`lowertable`
| ecf1b0      | true     | UPPERINDEX                    | lbcat.only_in_lbcat                  | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat`.`only_in_lbcat`
| f98948      | true     | UPPERINDEX                    | lbcat2.4test_table                   | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat2`.`4test_table`
| 9f2e3f      | true     | UPPERINDEX                    | lbcat2.anotherlowertable             | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat2`.`anotherlowertable`
| ad8922      | true     | UPPERINDEX                    | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| e04cc5      | true     | UPPERINDEX                    | lbcat2.lowertable                    | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat2`.`lowertable`
| 62dd12      | true     | UPPERINDEX                    | lbcat2.only_in_lbcat2                | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat2`.`only_in_lbcat2`

# Test Version: "cd648d" #