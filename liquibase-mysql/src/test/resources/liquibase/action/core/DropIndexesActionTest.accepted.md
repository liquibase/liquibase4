**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can drop from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | indexName                     | indexRelation                        | indexSchema | OPERATIONS
| :---------- | :------- | :---------------------------- | :----------------------------------- | :---------- | :------
| ad01425     | true     | ANOTHERUPPERINDEX             | lbcat.4test_table                    | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat`.`4test_table`
| ade56aa     | true     | ANOTHERUPPERINDEX             | lbcat.anotherlowertable              | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat`.`anotherlowertable`
| 3580c63     | true     | ANOTHERUPPERINDEX             | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| 3fa9968     | true     | ANOTHERUPPERINDEX             | lbcat.lowertable                     | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat`.`lowertable`
| fea5e9b     | true     | ANOTHERUPPERINDEX             | lbcat.only_in_lbcat                  | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat`.`only_in_lbcat`
| c0988b6     | true     | ANOTHERUPPERINDEX             | lbcat2.4test_table                   | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat2`.`4test_table`
| e5e1bfa     | true     | ANOTHERUPPERINDEX             | lbcat2.anotherlowertable             | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat2`.`anotherlowertable`
| c73cf2c     | true     | ANOTHERUPPERINDEX             | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| c99bab2     | true     | ANOTHERUPPERINDEX             | lbcat2.lowertable                    | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat2`.`lowertable`
| 9b836ec     | true     | ANOTHERUPPERINDEX             | lbcat2.only_in_lbcat2                | null        | **plan**: DROP INDEX `ANOTHERUPPERINDEX` ON `lbcat2`.`only_in_lbcat2`
| 41024b2     | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat.4test_table                    | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat`.`4test_table`
| f37062f     | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat.anotherlowertable              | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat`.`anotherlowertable`
| 3058261     | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| fb00b77     | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat.lowertable                     | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat`.`lowertable`
| b0dc56c     | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat.only_in_lbcat                  | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat`.`only_in_lbcat`
| f22dcd3     | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat2.4test_table                   | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat2`.`4test_table`
| 7d0aa7e     | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat2.anotherlowertable             | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat2`.`anotherlowertable`
| f667400     | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| 5cad049     | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat2.lowertable                    | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat2`.`lowertable`
| 11eafaa     | true     | CRAZY!@#\$%^&*()_+{}[]'"INDEX | lbcat2.only_in_lbcat2                | null        | **plan**: DROP INDEX `CRAZY!@#\$%^&*()_+{}[]'"INDEX` ON `lbcat2`.`only_in_lbcat2`
| 86b3098     | true     | UPPERINDEX                    | lbcat.4test_table                    | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat`.`4test_table`
| 47d8eb4     | true     | UPPERINDEX                    | lbcat.anotherlowertable              | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat`.`anotherlowertable`
| 8afcd80     | true     | UPPERINDEX                    | lbcat.crazy!@#\$%^&*()_+{}[]'"table  | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat`.`crazy!@#\$%^&*()_+{}[]'"table`
| 8e9572f     | true     | UPPERINDEX                    | lbcat.lowertable                     | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat`.`lowertable`
| ecf1b0e     | true     | UPPERINDEX                    | lbcat.only_in_lbcat                  | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat`.`only_in_lbcat`
| f989482     | true     | UPPERINDEX                    | lbcat2.4test_table                   | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat2`.`4test_table`
| 9f2e3fb     | true     | UPPERINDEX                    | lbcat2.anotherlowertable             | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat2`.`anotherlowertable`
| ad8922e     | true     | UPPERINDEX                    | lbcat2.crazy!@#\$%^&*()_+{}[]'"table | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat2`.`crazy!@#\$%^&*()_+{}[]'"table`
| e04cc52     | true     | UPPERINDEX                    | lbcat2.lowertable                    | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat2`.`lowertable`
| 62dd12e     | true     | UPPERINDEX                    | lbcat2.only_in_lbcat2                | null        | **plan**: DROP INDEX `UPPERINDEX` ON `lbcat2`.`only_in_lbcat2`

# Test Version: "669052" #