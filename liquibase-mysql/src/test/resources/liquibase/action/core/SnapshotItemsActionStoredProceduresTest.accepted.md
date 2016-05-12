**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can find all procedures in a schema" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | schema | OPERATIONS
| :---------- | :------- | :----- | :------
| 43a3eab     | true     | lbcat  | **plan**: getProcedures(lbcat, lbcat, null)
| 06284bc     | true     | lbcat2 | **plan**: getProcedures(lbcat2, lbcat2, null)

# Test: "can find fully qualified complex procedure names" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | procedure                                      | OPERATIONS
| :---------- | :------- | :--------------------------------------------- | :------
| fa74266     | true     | lbcat.4test_storedprocedure                    | **plan**: getProcedures(lbcat, lbcat, 4test\_storedprocedure)
| 3c33644     | true     | lbcat.anotherlowerstoredprocedure              | **plan**: getProcedures(lbcat, lbcat, anotherlowerstoredprocedure)
| 6bf07ab     | true     | lbcat.crazy!@#\$%^&*()_+{}[]'"storedprocedure  | **plan**: getProcedures(lbcat, lbcat, crazy!@#\\$\%^&*()\_+{}[]'"storedprocedure)
| 1e5f6db     | true     | lbcat.lowerstoredprocedure                     | **plan**: getProcedures(lbcat, lbcat, lowerstoredprocedure)
| 631947e     | true     | lbcat.only_in_lbcat                            | **plan**: getProcedures(lbcat, lbcat, only\_in\_lbcat)
| f035471     | true     | lbcat2.4test_storedprocedure                   | **plan**: getProcedures(lbcat2, lbcat2, 4test\_storedprocedure)
| ec99f9b     | true     | lbcat2.anotherlowerstoredprocedure             | **plan**: getProcedures(lbcat2, lbcat2, anotherlowerstoredprocedure)
| b7e9d85     | true     | lbcat2.crazy!@#\$%^&*()_+{}[]'"storedprocedure | **plan**: getProcedures(lbcat2, lbcat2, crazy!@#\\$\%^&*()\_+{}[]'"storedprocedure)
| 1cf545b     | true     | lbcat2.lowerstoredprocedure                    | **plan**: getProcedures(lbcat2, lbcat2, lowerstoredprocedure)
| 7affc03     | true     | lbcat2.only_in_lbcat2                          | **plan**: getProcedures(lbcat2, lbcat2, only\_in\_lbcat2)

# Test Version: "8846a1" #