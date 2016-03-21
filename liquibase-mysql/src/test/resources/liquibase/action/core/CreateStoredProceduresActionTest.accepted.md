**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can create from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | body                                                                                                                | container | name      | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------------------------------------------------------ | :-------- | :-------- | :------
| bdb9aab     | true     | <br>CREATE PROCEDURE `lbcat2`.`test_proc` (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM test_table;<br>END<br> | lbcat2    | test_proc | **plan**: CREATE PROCEDURE `lbcat2`.`test_proc` (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM test_table;<br>END
| 4e30c34     | true     | <br>CREATE PROCEDURE `lbcat`.`test_proc` (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM test_table;<br>END<br> | lbcat     | test_proc | **plan**: CREATE PROCEDURE `lbcat`.`test_proc` (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM test_table;<br>END
| 39469ee     | true     | <br>CREATE PROCEDURE `test_proc` (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM test_table;<br>END<br> | null      | test_proc | **plan**: CREATE PROCEDURE `test_proc` (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM test_table;<br>END

# Test Version: "5b1c85" #