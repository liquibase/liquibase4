package liquibase.structure

import spock.lang.Specification
import spock.lang.Unroll

class ObjectReferenceTest extends Specification {

    @Unroll("#featureName: #expected")
    def "can construct with variable args"() {
        expect:
        objectName.toString() == expected

        where:
        objectName                                                   | expected
        new ObjectReference()                                        | "UNNAMED (NO TYPE)"
        new ObjectReference(null)                                    | "UNNAMED (NO TYPE)"
        new ObjectReference("a")                                     | "a (NO TYPE)"
        new ObjectReference("a", "b")                                | "a.b (NO TYPE)"
        new ObjectReference("a", "b", "c")                           | "a.b.c (NO TYPE)"
        new ObjectReference(new ObjectReference("a", "b"))           | "a.b (NO TYPE)"
        new ObjectReference(new ObjectReference("a", "b"), "c")      | "a.b.c (NO TYPE)"
        new ObjectReference(new ObjectReference("a", "b"), "c", "d") | "a.b.c.d (NO TYPE)"
    }

    @Unroll("#featureName: #expected")
    def "getNameList"() {
        expect:
        objectName.asList() == expected

        where:
        objectName                                      | expected
        new ObjectReference()                           | []
        new ObjectReference("a")                        | ["a"]
        new ObjectReference("a", "b")                   | ["a", "b"]
        new ObjectReference("a", "b", "c")              | ["a", "b", "c"]
        new ObjectReference(null, "b", "c")             | ["b", "c"]
        new ObjectReference("a", null, "c")             | ["a", null, "c"]
        new ObjectReference("a", "b", null)             | ["a", "b", null]
        new ObjectReference("a", null, "c")             | ["a", null, "c"]
        new ObjectReference(null, null, "a", "b")       | ["a", "b"]  //top level nulls do not count toward name
        new ObjectReference(null, "a", null, null, "c") | ["a", null, null, "c"]

    }

    @Unroll("#featureName: #expected")
    def "toString works"() {
        expect:
        objectName.toString() == expected

        where:
        objectName                              | expected
        new ObjectReference()                   | "UNNAMED (NO TYPE)"
        new ObjectReference("abc")              | "abc (NO TYPE)"
        new ObjectReference("ABC")              | "ABC (NO TYPE)"
        new ObjectReference("ABC", "xyz")       | "ABC.xyz (NO TYPE)"
        new ObjectReference("ABC", "XYZ", null) | "ABC.XYZ.UNNAMED (NO TYPE)"
        new ObjectReference(null, "XYZ")        | "XYZ (NO TYPE)"
        new ObjectReference("a", "b", "c")      | "a.b.c (NO TYPE)"
        new ObjectReference("a", null, "c")     | "a.UNNAMED.c (NO TYPE)"
    }

    @Unroll
    def depth() {
        expect:
        objectName.depth() == expected

        where:
        objectName                                      | expected
        new ObjectReference()                           | 0
        new ObjectReference("a")                        | 0
        new ObjectReference("a", "b")                   | 1
        new ObjectReference("a", "b", "c")              | 2
        new ObjectReference("a", null, "c")             | 2
        new ObjectReference(null, "a", "b")             | 1 //top level nulls do not count toward depth
        new ObjectReference(null, null, "a", "b")       | 1 //top level nulls do not count toward depth
        new ObjectReference(null, "a", null, "c")       | 2
        new ObjectReference(null, "a", null, null, "c") | 3
        new ObjectReference("a", "b", "c", "d")         | 3
    }

    @Unroll
    def parse() {
        expect:
        ObjectReference.parse(string).asList() == expected

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
        objectName                          | length | expected
        new ObjectReference()               | 2      | [null, null]
        new ObjectReference("a")            | 2      | [null, "a"]
        new ObjectReference("a", "b")       | 2      | ["a", "b"]
        new ObjectReference("a", "b", "c")  | 2      | ["b", "c"]
        new ObjectReference("a", null, "c") | 3      | ["a", null, "c"]
        new ObjectReference()               | 0      | []
        new ObjectReference("a")            | 0      | []
        new ObjectReference()               | 1      | [null]
        new ObjectReference("a")            | 1      | ["a"]
        new ObjectReference("a", "b")       | 1      | ["b"]
    }

    @Unroll
    def "equals with fuzzy matching"() {
        expect:
        name1.equals(name2, fuzzy) == expected

        where:
        name1                                        | name2                                        | fuzzy | expected
        new ObjectReference("a")                     | new ObjectReference("a")                     | true   | true
        new ObjectReference("a")                     | new ObjectReference("a")                     | false  | true
        new ObjectReference("a")                     | new ObjectReference("b")                     | true   | false
        new ObjectReference("a")                     | new ObjectReference("b")                     | false  | false
        new ObjectReference("a")                     | new ObjectReference("b", "a")                | true   | true
        new ObjectReference("a")                     | new ObjectReference("b", "a")                | false  | false
        new ObjectReference("b", "a")                | new ObjectReference("a")                     | true   | true
        new ObjectReference("b", "a")                | new ObjectReference("a")                     | false  | false
        new ObjectReference("b", "a")                | new ObjectReference("b", "a")                | true   | true
        new ObjectReference("b", "a")                | new ObjectReference("b", "a")                | false  | true
        new ObjectReference("c", "b", "a")           | new ObjectReference("c", "b", "a")           | true   | true
        new ObjectReference("c", "b", "a")           | new ObjectReference("c", "b", "a")           | false  | true
        new ObjectReference("c", "x", "a")           | new ObjectReference("c", "b", "a")           | true   | false
        new ObjectReference("c", "b", "a")           | new ObjectReference("c", "x", "a")           | true   | false
        new ObjectReference("c", null, "a")          | new ObjectReference("c", "b", "a")           | true   | true
        new ObjectReference("c", "b", "a")           | new ObjectReference("c", null, "a")          | true   | true
        new ObjectReference(null, "c", "b", "a")     | new ObjectReference("c", "b", "a")           | true   | true
        new ObjectReference("c", "b", "a")           | new ObjectReference(null, "c", "b", "a")     | true   | true
        new ObjectReference(null, "c", "b", "a")     | new ObjectReference("c", "b", "a")           | false  | true
        new ObjectReference("a")                     | new ObjectReference((String) null, "a")      | true   | true
        new ObjectReference("a")                     | new ObjectReference((String) null, "b")      | true   | false
        new ObjectReference((String) null, "a")      | new ObjectReference((String) null, "a")      | true   | true
        new ObjectReference((String) null, "a")      | new ObjectReference((String) null, "b")      | true   | false
        new ObjectReference("a", (String) null, "c") | new ObjectReference("a", "b", "c")           | true   | true
        new ObjectReference("X", (String) null, "c") | new ObjectReference("a", "b", "c")           | true   | false
        new ObjectReference("a", (String) null, "X") | new ObjectReference("a", "b", "c")           | true   | false
        new ObjectReference("a", (String) null, "c") | new ObjectReference("X", "b", "c")           | true   | false
        new ObjectReference("a", (String) null, "c") | new ObjectReference("a", "b", "X")           | true   | false
        new ObjectReference("a", "b", "c")           | new ObjectReference("a", (String) null, "c") | true   | true
        new ObjectReference("a", "b", "c", "d")      | new ObjectReference("b", (String) null, "d") | true   | true
        new ObjectReference("b", (String) null, "d") | new ObjectReference("a", "b", "c", "d")      | true   | true
    }

    @Unroll
    def "truncate"() {
        expect:
        name.truncate(length).toString() == expected

        where:
        name                               | length | expected
        new ObjectReference()              | 1      | "UNNAMED (NO TYPE)"
        new ObjectReference()              | 2      | "UNNAMED (NO TYPE)"
        new ObjectReference("a")           | 1      | "a (NO TYPE)"
        new ObjectReference("a")           | 2      | "a (NO TYPE)"
        new ObjectReference("a")           | 3      | "a (NO TYPE)"
        new ObjectReference("a", "b")      | 1      | "b (NO TYPE)"
        new ObjectReference("a", "b")      | 2      | "a.b (NO TYPE)"
        new ObjectReference("a", "b")      | 3      | "a.b (NO TYPE)"
        new ObjectReference("a", "b", "c") | 1      | "c (NO TYPE)"
        new ObjectReference("a", "b", "c") | 2      | "b.c (NO TYPE)"
        new ObjectReference("a", "b", "c") | 3      | "a.b.c (NO TYPE)"
    }
}

