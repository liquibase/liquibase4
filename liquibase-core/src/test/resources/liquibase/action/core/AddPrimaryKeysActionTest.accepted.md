**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "Can apply primary key with with various settings" #

- **connection:** unsupported[config:standard]

| Permutation | Verified             | colDef        | pkDef                                      | schemaRef         | OPERATIONS
| :---------- | :------------------- | :------------ | :----------------------------------------- | :---------------- | :------
| 3d31a1      | Unsupported Database | COL_NAME      | null on LBSCHEMA.TEST_TABLE(COL_NAME)      | LBSCHEMA (SCHEMA) | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")
| ade29c      | Unsupported Database | COL_NAME DESC | null on LBSCHEMA.TEST_TABLE(COL_NAME DESC) | LBSCHEMA (SCHEMA) | **plan**: ALTER TABLE "LBSCHEMA"."TEST_TABLE" ADD CONSTRAINT PRIMARY KEY ("COL_NAME")

# Test Version: "d17504" #