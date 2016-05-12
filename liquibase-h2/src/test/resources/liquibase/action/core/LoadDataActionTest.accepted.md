**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can load simple csv" #

- **connection:** h2 standard

| Permutation | Verified | path                   | table                | OPERATIONS
| :---------- | :------- | :--------------------- | :------------------- | :------
| c0fda97     | true     | com/example/simple.csv | LBSCHEMA2.TEST_TABLE | **plan**: loadData(columns=[LoadDataColumn{dataType=VARCHAR, header=name}, LoadDataColumn{dataType=VARCHAR, header=username}, LoadDataColumn{dataType=INTEGER, header=age}], path=com/example/simple.csv, table=LBSCHEMA2.TEST_TABLE)
| 0c9e821     | true     | com/example/simple.csv | PUBLIC.TEST_TABLE    | **plan**: loadData(columns=[LoadDataColumn{dataType=VARCHAR, header=name}, LoadDataColumn{dataType=VARCHAR, header=username}, LoadDataColumn{dataType=INTEGER, header=age}], path=com/example/simple.csv, table=PUBLIC.TEST_TABLE)

# Test Version: "ba07fb" #