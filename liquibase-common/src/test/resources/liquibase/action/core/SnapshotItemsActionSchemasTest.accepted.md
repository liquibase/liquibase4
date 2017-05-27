**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can find all schemas by specifying null name" #

## Permutation 29e1317 _NOT VERIFIED: Generic_ ##

- **connection:** generic standard

#### Results ####

- **plan:** getSchemas(null, null)

# Test: "can find each schema" #

- **connection:** generic standard

| Permutation | Verified | schema    | OPERATIONS
| :---------- | :------- | :-------- | :------
| 6e63508     | Generic  | LBSCHEMA  | **plan**: getSchemas(null, LBSCHEMA)
| bbb8e61     | Generic  | LBSCHEMA2 | **plan**: getSchemas(null, LBSCHEMA2)

# Test Version: "62de46" #