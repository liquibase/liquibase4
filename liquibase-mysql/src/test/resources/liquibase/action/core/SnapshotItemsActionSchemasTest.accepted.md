**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can find all schemas by specifying null name" #

## Permutation dfad364 (verified) ##

- **connection:** mysql caseInsensitive

#### Results ####

- **plan:** getCatalogs()

# Test: "can find each schema" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | schema | OPERATIONS
| :---------- | :------- | :----- | :------
| 43a3eab     | true     | lbcat  | **plan**: getCatalogs()
| 06284bc     | true     | lbcat2 | **plan**: getCatalogs()

# Test Version: "6b1530" #