**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can load simple csv" #

- **connection:** generic standard

| Permutation | Verified | path                   | table                | OPERATIONS
| :---------- | :------- | :--------------------- | :------------------- | :------
| 32fd142     | Generic  | com/example/simple.csv | LBSCHEMA.TEST_TABLE  | **plan**: loadData(columns=[LoadDataColumn{dataType=VARCHAR, header=name}, LoadDataColumn{dataType=VARCHAR, header=username}, LoadDataColumn{dataType=INTEGER, header=age}], path=com/example/simple.csv, table=LBSCHEMA.TEST_TABLE)
| fb4dd88     | Generic  | com/example/simple.csv | LBSCHEMA2.TEST_TABLE | **plan**: loadData(columns=[LoadDataColumn{dataType=VARCHAR, header=name}, LoadDataColumn{dataType=VARCHAR, header=username}, LoadDataColumn{dataType=INTEGER, header=age}], path=com/example/simple.csv, table=LBSCHEMA2.TEST_TABLE)

# Test Version: "c1d234" #