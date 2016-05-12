**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can load simple csv" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | path                   | table             | OPERATIONS
| :---------- | :------- | :--------------------- | :---------------- | :------
| 77cd1ef     | true     | com/example/simple.csv | lbcat.test_table  | **plan**: loadData(columns=[LoadDataColumn{dataType=VARCHAR, header=name}, LoadDataColumn{dataType=VARCHAR, header=username}, LoadDataColumn{dataType=INTEGER, header=age}], path=com/example/simple.csv, table=lbcat.test_table)
| 669ab60     | true     | com/example/simple.csv | lbcat2.test_table | **plan**: loadData(columns=[LoadDataColumn{dataType=VARCHAR, header=name}, LoadDataColumn{dataType=VARCHAR, header=username}, LoadDataColumn{dataType=INTEGER, header=age}], path=com/example/simple.csv, table=lbcat2.test_table)

# Test Version: "ba07fb" #