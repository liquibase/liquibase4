**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can create from createAllActionPermutations" #

- **connection:** mysql caseInsensitive

| Permutation | Verified | body                                                                                                                | container | name      | OPERATIONS
| :---------- | :------- | :------------------------------------------------------------------------------------------------------------------ | :-------- | :-------- | :------
| efdeb57     | true     | <br>CREATE PROCEDURE `lbcat2`.`test_proc` (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM test_table;<br>END<br> | lbcat2    | test_proc | **plan**: CREATE PROCEDURE `lbcat2`.`test_proc` (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM test_table;<br>END
| 315df49     | true     | <br>CREATE PROCEDURE `lbcat`.`test_proc` (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM test_table;<br>END<br> | lbcat     | test_proc | **plan**: CREATE PROCEDURE `lbcat`.`test_proc` (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM test_table;<br>END
| 791c136     | true     | <br>CREATE PROCEDURE `test_proc` (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM test_table;<br>END<br> | null      | test_proc | **plan**: CREATE PROCEDURE `test_proc` (OUT param1 int)<br>BEGIN<br>    SELECT COUNT(*) INTO param1 FROM test_table;<br>END

# Test Version: "87b3a5" #