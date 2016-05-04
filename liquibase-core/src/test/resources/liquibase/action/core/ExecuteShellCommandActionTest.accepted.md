**NOTE: This output is generated and parsed by TestMD. Please read it, but DO NOT EDIT MANUALLY**

# Test: "can run from createAllActionPermutations" #

- **connection:** null

| Permutation | Verified | args        | executable                 | osFilters  | OPERATIONS
| :---------- | :------- | :---------- | :------------------------- | :--------- | :------
| 2273dd7     | true     |             | \windows\system32\tree.com | windows    | **plan**: executeShellCommand(executable=\windows\system32\tree.com, osFilters=[windows])
| cf39557     | true     |             | echo                       | linux      | **plan**: executeShellCommand(executable=echo, osFilters=[linux])
| 0c8e2a9     | true     |             | echo                       | linux, mac | **plan**: executeShellCommand(executable=echo, osFilters=[linux, mac])
| 2d25dc9     | true     |             | echo                       | mac        | **plan**: executeShellCommand(executable=echo, osFilters=[mac])
| 00e0e23     | true     |             | tree.com                   | windows    | **plan**: executeShellCommand(executable=tree.com, osFilters=[windows])
| 9b6ee7b     | true     | /F          | \windows\system32\tree.com | windows    | **plan**: executeShellCommand(args=[/F], executable=\windows\system32\tree.com, osFilters=[windows])
| 11a5e1d     | true     | /F          | tree.com                   | windows    | **plan**: executeShellCommand(args=[/F], executable=tree.com, osFilters=[windows])
| 0ec0937     | true     | /F, /A      | \windows\system32\tree.com | windows    | **plan**: executeShellCommand(args=[/F, /A], executable=\windows\system32\tree.com, osFilters=[windows])
| 58315db     | true     | /F, /A      | tree.com                   | windows    | **plan**: executeShellCommand(args=[/F, /A], executable=tree.com, osFilters=[windows])
| 8f1b176     | true     | hello world | echo                       | linux      | **plan**: executeShellCommand(args=[hello world], executable=echo, osFilters=[linux])
| 0f6c8bc     | true     | hello world | echo                       | linux, mac | **plan**: executeShellCommand(args=[hello world], executable=echo, osFilters=[linux, mac])
| ebfe26f     | true     | hello world | echo                       | mac        | **plan**: executeShellCommand(args=[hello world], executable=echo, osFilters=[mac])

# Test Version: "74f1e8" #