**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can find all procedures in a schema" #

- **connection:** generic standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 6e63508     | Generic  | LBSCHEMA  | **plan**: getProcedures(null, LBSCHEMA, null)
| bbb8e61     | Generic  | LBSCHEMA2 | **plan**: getProcedures(null, LBSCHEMA2, null)

# Test: "can find fully qualified complex procedure names" #

- **connection:** generic standard

| Permutation | Verified | procedure                                         | OPERATIONS
| :---------- | :------- | :------------------------------------------------ | :------
| 30daf43     | Generic  | LBSCHEMA.4TEST_storedprocedure                    | **plan**: getProcedures(null, LBSCHEMA, 4TEST\_storedprocedure)
| 2e40cf1     | Generic  | LBSCHEMA.4test_storedprocedure                    | **plan**: getProcedures(null, LBSCHEMA, 4test\_storedprocedure)
| 1cb7c5b     | Generic  | LBSCHEMA.ANOTHERUPPERSTOREDPROCEDURE              | **plan**: getProcedures(null, LBSCHEMA, ANOTHERUPPERSTOREDPROCEDURE)
| d1541e1     | Generic  | LBSCHEMA.AnotherMixedStoredProcedure              | **plan**: getProcedures(null, LBSCHEMA, AnotherMixedStoredProcedure)
| 117b3c1     | Generic  | LBSCHEMA.CRAZY!@#\$%^&*()_+{}[]'"STOREDPROCEDURE  | **plan**: getProcedures(null, LBSCHEMA, CRAZY!@#\\$\%^&*()\_+{}[]'"STOREDPROCEDURE)
| ccc68bf     | Generic  | LBSCHEMA.MixedStoredProcedure                     | **plan**: getProcedures(null, LBSCHEMA, MixedStoredProcedure)
| 1b64fe2     | Generic  | LBSCHEMA.ONLY_IN_LBSCHEMA                         | **plan**: getProcedures(null, LBSCHEMA, ONLY\_IN\_LBSCHEMA)
| 72f9d95     | Generic  | LBSCHEMA.UPPERSTOREDPROCEDURE                     | **plan**: getProcedures(null, LBSCHEMA, UPPERSTOREDPROCEDURE)
| efc2dc6     | Generic  | LBSCHEMA.anotherlowerstoredprocedure              | **plan**: getProcedures(null, LBSCHEMA, anotherlowerstoredprocedure)
| 18c5e88     | Generic  | LBSCHEMA.crazy!@#\$%^&*()_+{}[]'"storedprocedure  | **plan**: getProcedures(null, LBSCHEMA, crazy!@#\\$\%^&*()\_+{}[]'"storedprocedure)
| 625e72e     | Generic  | LBSCHEMA.lowerstoredprocedure                     | **plan**: getProcedures(null, LBSCHEMA, lowerstoredprocedure)
| 0298f64     | Generic  | LBSCHEMA2.4TEST_storedprocedure                   | **plan**: getProcedures(null, LBSCHEMA2, 4TEST\_storedprocedure)
| 35ccaac     | Generic  | LBSCHEMA2.4test_storedprocedure                   | **plan**: getProcedures(null, LBSCHEMA2, 4test\_storedprocedure)
| 9ab1c4a     | Generic  | LBSCHEMA2.ANOTHERUPPERSTOREDPROCEDURE             | **plan**: getProcedures(null, LBSCHEMA2, ANOTHERUPPERSTOREDPROCEDURE)
| 02431f0     | Generic  | LBSCHEMA2.AnotherMixedStoredProcedure             | **plan**: getProcedures(null, LBSCHEMA2, AnotherMixedStoredProcedure)
| a1bdd58     | Generic  | LBSCHEMA2.CRAZY!@#\$%^&*()_+{}[]'"STOREDPROCEDURE | **plan**: getProcedures(null, LBSCHEMA2, CRAZY!@#\\$\%^&*()\_+{}[]'"STOREDPROCEDURE)
| fb21293     | Generic  | LBSCHEMA2.MixedStoredProcedure                    | **plan**: getProcedures(null, LBSCHEMA2, MixedStoredProcedure)
| 8aecafc     | Generic  | LBSCHEMA2.ONLY_IN_LBSCHEMA2                       | **plan**: getProcedures(null, LBSCHEMA2, ONLY\_IN\_LBSCHEMA2)
| 3538904     | Generic  | LBSCHEMA2.UPPERSTOREDPROCEDURE                    | **plan**: getProcedures(null, LBSCHEMA2, UPPERSTOREDPROCEDURE)
| 5056ac5     | Generic  | LBSCHEMA2.anotherlowerstoredprocedure             | **plan**: getProcedures(null, LBSCHEMA2, anotherlowerstoredprocedure)
| 9c9dc02     | Generic  | LBSCHEMA2.crazy!@#\$%^&*()_+{}[]'"storedprocedure | **plan**: getProcedures(null, LBSCHEMA2, crazy!@#\\$\%^&*()\_+{}[]'"storedprocedure)
| 7c0103e     | Generic  | LBSCHEMA2.lowerstoredprocedure                    | **plan**: getProcedures(null, LBSCHEMA2, lowerstoredprocedure)

# Test Version: "8b332b" #