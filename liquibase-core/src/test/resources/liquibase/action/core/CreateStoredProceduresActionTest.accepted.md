**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can create from createAllActionPermutations" #

- **connection:** generic standard

| Permutation | Verified | body                                                                                                                   | container | name      | OPERATIONS
| :---------- | :------- | :--------------------------------------------------------------------------------------------------------------------- | :-------- | :-------- | :------
| 502ed73     | Generic  | <br>CREATE PROCEDURE "LBSCHEMA"."TEST_PROC" (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM TEST_TABLE;<br>END<br> | LBSCHEMA  | TEST_PROC | **plan**: CREATE PROCEDURE "LBSCHEMA"."TEST_PROC" (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM TEST_TABLE;<br>END
| 4c42302     | Generic  | <br>CREATE PROCEDURE "LBSCHEMA2"."TEST_PROC" (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM TEST_TABLE;<br>END<br> | LBSCHEMA2 | TEST_PROC | **plan**: CREATE PROCEDURE "LBSCHEMA2"."TEST_PROC" (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM TEST_TABLE;<br>END
| 8b293ee     | Generic  | <br>CREATE PROCEDURE "TEST_PROC" (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM TEST_TABLE;<br>END<br> | null      | TEST_PROC | **plan**: CREATE PROCEDURE "TEST_PROC" (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM TEST_TABLE;<br>END

# Test Version: "87b3a5" #