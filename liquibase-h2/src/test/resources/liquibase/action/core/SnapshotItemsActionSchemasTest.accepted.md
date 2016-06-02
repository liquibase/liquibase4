**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can find all schemas by specifying null name" #

## Permutation bf4851c (verified) ##

- **connection:** h2 standard

#### Results ####

- **plan:** getSchemas(null, null)

# Test: "can find each schema" #

- **connection:** h2 standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 8caa091     | true     | LBSCHEMA2 | **plan**: getSchemas(null, LBSCHEMA2)
| 63bb730     | true     | PUBLIC    | **plan**: getSchemas(null, PUBLIC)

# Test Version: "6b1530" #