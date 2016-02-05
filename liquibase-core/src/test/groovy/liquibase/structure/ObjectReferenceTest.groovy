package liquibase.structure

import liquibase.structure.core.Schema
import liquibase.structure.core.Table
import spock.lang.Specification
import spock.lang.Unroll

class ObjectReferenceTest extends Specification {

    @Unroll("#featureName: #expected")
    def "can construct with variable args"() {
        expect:
        objectName.toString() == expected

        where:
        objectName                                                                  | expected
        new ObjectReference(Table)                                                  | "UNNAMED"
        new ObjectReference(Table, null)                                            | "UNNAMED"
        new ObjectReference(Table, "a")                                             | "a"
        new ObjectReference(Table, "a", "b")                                        | "a.b"
        new ObjectReference(Table, "a", "b", "c")                                   | "a.b.c"
        new ObjectReference(Table, new ObjectReference(Schema, "a", "b"))           | "a.b.UNNAMED"
        new ObjectReference(Table, new ObjectReference(Table, "a", "b"))           | "a.b"
        new ObjectReference(Table, new ObjectReference(Schema, "a", "b"), "c")      | "a.b.c"
        new ObjectReference(Table, new ObjectReference(Schema, "a", "b"), "c", "d") | "a.b.c.d"
        new ObjectReference(Table, new ObjectReference(Schema, "a", "b"), null)     | "a.b.UNNAMED"
    }

    @Unroll("#featureName: #expected")
    def "getNameList"() {
        expect:
        objectName.asList() == expected

        where:
        objectName                                             | expected
        new ObjectReference(Table,)                            | []
        new ObjectReference(Table, "a")                        | ["a"]
        new ObjectReference(Table, "a", "b")                   | ["a", "b"]
        new ObjectReference(Table, "a", "b", "c")              | ["a", "b", "c"]
        new ObjectReference(Table, null, "b", "c")             | ["b", "c"]
        new ObjectReference(Table, "a", null, "c")             | ["a", null, "c"]
        new ObjectReference(Table, "a", "b", null)             | ["a", "b", null]
        new ObjectReference(Table, "a", null, "c")             | ["a", null, "c"]
        new ObjectReference(Table, null, null, "a", "b")       | ["a", "b"]  //top level nulls do not count toward name
        new ObjectReference(Table, null, "a", null, null, "c") | ["a", null, null, "c"]

    }

    @Unroll("#featureName: #expected")
    def "toString works"() {
        expect:
        objectName.toString() == expected

        where:
        objectName                                     | expected
        new ObjectReference(Table,)                    | "UNNAMED"
        new ObjectReference(Table, "abc")              | "abc"
        new ObjectReference(Table, "ABC")              | "ABC"
        new ObjectReference(Table, "ABC", "xyz")       | "ABC.xyz"
        new ObjectReference(Table, "ABC", "XYZ", null) | "ABC.XYZ.UNNAMED"
        new ObjectReference(Table, null, "XYZ")        | "XYZ"
        new ObjectReference(Table, "a", "b", "c")      | "a.b.c"
        new ObjectReference(Table, "a", null, "c")     | "a.UNNAMED.c"
    }

    @Unroll
    def depth() {
        expect:
        objectName.depth() == expected

        where:
        objectName                                             | expected
        new ObjectReference(Table,)                            | 0
        new ObjectReference(Table, "a")                        | 0
        new ObjectReference(Table, "a", "b")                   | 1
        new ObjectReference(Table, "a", "b", "c")              | 2
        new ObjectReference(Table, "a", null, "c")             | 2
        new ObjectReference(Table, null, "a", "b")             | 1 //top level nulls do not count toward depth
        new ObjectReference(Table, null, null, "a", "b")       | 1 //top level nulls do not count toward depth
        new ObjectReference(Table, null, "a", null, "c")       | 2
        new ObjectReference(Table, null, "a", null, null, "c") | 3
        new ObjectReference(Table, "a", "b", "c", "d")         | 3
    }

    @Unroll
    def parse() {
        expect:
        ObjectReference.parse(Table, string).asList() == expected

        where:
        string  | expected
        null    | []
        "abc"   | ["abc"]
        "a.b"   | ["a", "b"]
        "a.b.c" | ["a", "b", "c"]
    }

    @Unroll
    def "asList with length"() {
        expect:
        objectName.asList(length) == expected

        where:
        objectName                                 | length | expected
        new ObjectReference(Table,)                | 2      | [null, null]
        new ObjectReference(Table, "a")            | 2      | [null, "a"]
        new ObjectReference(Table, "a", "b")       | 2      | ["a", "b"]
        new ObjectReference(Table, "a", "b", "c")  | 2      | ["b", "c"]
        new ObjectReference(Table, "a", null, "c") | 3      | ["a", null, "c"]
        new ObjectReference(Table,)                | 0      | []
        new ObjectReference(Table, "a")            | 0      | []
        new ObjectReference(Table,)                | 1      | [null]
        new ObjectReference(Table, "a")            | 1      | ["a"]
        new ObjectReference(Table, "a", "b")       | 1      | ["b"]
    }

    @Unroll
    def "equals with fuzzy matching"() {
        expect:
        name1.equals(name2, fuzzy) == expected

        where:
        name1                                               | name2                                               | fuzzy | expected
        new ObjectReference(Table, "a")                     | new ObjectReference(Table, "a")                     | true  | true
        new ObjectReference(Table, "a")                     | new ObjectReference(Table, "a")                     | false | true
        new ObjectReference(Table, "a")                     | new ObjectReference(Table, "b")                     | true  | false
        new ObjectReference(Table, "a")                     | new ObjectReference(Table, "b")                     | false | false
        new ObjectReference(Table, "a")                     | new ObjectReference(Table, "b", "a")                | true  | true
        new ObjectReference(Table, "a")                     | new ObjectReference(Table, "b", "a")                | false | false
        new ObjectReference(Table, "b", "a")                | new ObjectReference(Table, "a")                     | true  | true
        new ObjectReference(Table, "b", "a")                | new ObjectReference(Table, "a")                     | false | false
        new ObjectReference(Table, "b", "a")                | new ObjectReference(Table, "b", "a")                | true  | true
        new ObjectReference(Table, "b", "a")                | new ObjectReference(Table, "b", "a")                | false | true
        new ObjectReference(Table, "c", "b", "a")           | new ObjectReference(Table, "c", "b", "a")           | true  | true
        new ObjectReference(Table, "c", "b", "a")           | new ObjectReference(Table, "c", "b", "a")           | false | true
        new ObjectReference(Table, "c", "x", "a")           | new ObjectReference(Table, "c", "b", "a")           | true  | false
        new ObjectReference(Table, "c", "b", "a")           | new ObjectReference(Table, "c", "x", "a")           | true  | false
        new ObjectReference(Table, "c", null, "a")          | new ObjectReference(Table, "c", "b", "a")           | true  | true
        new ObjectReference(Table, "c", "b", "a")           | new ObjectReference(Table, "c", null, "a")          | true  | true
        new ObjectReference(Table, null, "c", "b", "a")     | new ObjectReference(Table, "c", "b", "a")           | true  | true
        new ObjectReference(Table, "c", "b", "a")           | new ObjectReference(Table, null, "c", "b", "a")     | true  | true
        new ObjectReference(Table, null, "c", "b", "a")     | new ObjectReference(Table, "c", "b", "a")           | false | true
        new ObjectReference(Table, "a")                     | new ObjectReference(Table, (String) null, "a")      | true  | true
        new ObjectReference(Table, "a")                     | new ObjectReference(Table, (String) null, "b")      | true  | false
        new ObjectReference(Table, (String) null, "a")      | new ObjectReference(Table, (String) null, "a")      | true  | true
        new ObjectReference(Table, (String) null, "a")      | new ObjectReference(Table, (String) null, "b")      | true  | false
        new ObjectReference(Table, "a", (String) null, "c") | new ObjectReference(Table, "a", "b", "c")           | true  | true
        new ObjectReference(Table, "X", (String) null, "c") | new ObjectReference(Table, "a", "b", "c")           | true  | false
        new ObjectReference(Table, "a", (String) null, "X") | new ObjectReference(Table, "a", "b", "c")           | true  | false
        new ObjectReference(Table, "a", (String) null, "c") | new ObjectReference(Table, "X", "b", "c")           | true  | false
        new ObjectReference(Table, "a", (String) null, "c") | new ObjectReference(Table, "a", "b", "X")           | true  | false
        new ObjectReference(Table, "a", "b", "c")           | new ObjectReference(Table, "a", (String) null, "c") | true  | true
        new ObjectReference(Table, "a", "b", "c", "d")      | new ObjectReference(Table, "b", (String) null, "d") | true  | true
        new ObjectReference(Table, "b", (String) null, "d") | new ObjectReference(Table, "a", "b", "c", "d")      | true  | true
    }

    @Unroll
    def "truncate"() {
        expect:
        name.truncate(length).toString() == expected

        where:
        name                                      | length | expected
        new ObjectReference(Table,)               | 1      | "UNNAMED"
        new ObjectReference(Table,)               | 2      | "UNNAMED"
        new ObjectReference(Table, "a")           | 1      | "a"
        new ObjectReference(Table, "a")           | 2      | "a"
        new ObjectReference(Table, "a")           | 3      | "a"
        new ObjectReference(Table, "a", "b")      | 1      | "b"
        new ObjectReference(Table, "a", "b")      | 2      | "a.b"
        new ObjectReference(Table, "a", "b")      | 3      | "a.b"
        new ObjectReference(Table, "a", "b", "c") | 1      | "c"
        new ObjectReference(Table, "a", "b", "c") | 2      | "b.c"
        new ObjectReference(Table, "a", "b", "c") | 3      | "a.b.c"
    }
}

