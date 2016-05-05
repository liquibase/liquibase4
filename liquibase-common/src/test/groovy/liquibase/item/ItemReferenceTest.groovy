package liquibase.item

import liquibase.item.core.Relation
import liquibase.item.core.RelationReference
import liquibase.item.core.SchemaReference
import liquibase.item.core.Table
import liquibase.item.core.View
import spock.lang.Specification
import spock.lang.Unroll

class ItemReferenceTest extends Specification {

    @Unroll("#featureName: #expected")
    def "can construct with variable args"() {
        expect:
        reference.toString() == expected

        where:
        reference                                                        | expected
        new RelationReference(Table)                                      | "UNNAMED"
        new RelationReference(Table, null)                                | "UNNAMED"
        new RelationReference(Table, "a")                                 | "a"
        new RelationReference(Table, "a", "b")                            | "a.b"
        new RelationReference(Table, "a", "b", "c")                       | "a.b.c"
        new RelationReference(Table, "c", new SchemaReference("a", "b"))  | "a.b.c"
        new RelationReference(Table, null, new SchemaReference("a", "b")) | "a.b.UNNAMED"
    }

    @Unroll()
    def "varriable arg constructor creates the correct containers"() {
        expect:
        ref.asReferenceList()*.type.simpleName.join(",") == expected

        where:
        ref                                                          | expected
        new RelationReference(Table, "schema", "table")              | "Schema,Table"
        new RelationReference(Table, "cat", "schema", "table")       | "Catalog,Schema,Table"
        new RelationReference(Table, "db", "cat", "schema", "table") | "Item,Catalog,Schema,Table"
    }

    @Unroll("#featureName: #expected")
    def "asList"() {
        expect:
        reference.asList() == expected

        where:
        reference                                               | expected
        new RelationReference(Table,)                            | []
        new RelationReference(Table, "a")                        | ["a"]
        new RelationReference(Table, "a", "b")                   | ["a", "b"]
        new RelationReference(Table, "a", "b", "c")              | ["a", "b", "c"]
        new RelationReference(Table, null, "b", "c")             | ["b", "c"]
        new RelationReference(Table, "a", null, "c")             | ["a", null, "c"]
        new RelationReference(Table, "a", "b", null)             | ["a", "b", null]
        new RelationReference(Table, "a", null, "c")             | ["a", null, "c"]
        new RelationReference(Table, null, null, "a", "b")       | ["a", "b"]  //top level nulls do not count toward name
        new RelationReference(Table, null, "a", null, null, "c") | ["a", null, null, "c"]

    }

    @Unroll("#featureName: #expected")
    def "toString works"() {
        expect:
        reference.toString() == expected

        where:
        reference                                       | expected
        new RelationReference(Table,)                    | "UNNAMED"
        new RelationReference(Table, "abc")              | "abc"
        new RelationReference(Table, "ABC")              | "ABC"
        new RelationReference(Table, "ABC", "xyz")       | "ABC.xyz"
        new RelationReference(Table, "ABC", "XYZ", null) | "ABC.XYZ.UNNAMED"
        new RelationReference(Table, "XYZ", null)        | "XYZ"
        new RelationReference(Table, "a", "b", "c")      | "a.b.c"
        new RelationReference(Table, "a", null, "c")     | "a.UNNAMED.c"
    }

    @Unroll
    def "asList with length"() {
        expect:
        reference.asList(length) == expected

        where:
        reference                                   | length | expected
        new RelationReference(Table,)                | 2      | [null, null]
        new RelationReference(Table, "a")            | 2      | [null, "a"]
        new RelationReference(Table, "a", "b")       | 2      | ["a", "b"]
        new RelationReference(Table, "a", "b", "c")  | 2      | ["b", "c"]
        new RelationReference(Table, "a", null, "c") | 3      | ["a", null, "c"]
        new RelationReference(Table,)                | 0      | []
        new RelationReference(Table, "a")            | 0      | []
        new RelationReference(Table,)                | 1      | [null]
        new RelationReference(Table, "a")            | 1      | ["a"]
        new RelationReference(Table, "a", "b")       | 1      | ["b"]
    }

    @Unroll
    def "equals with fuzzy matching"() {
        expect:
        name1.equals(name2, fuzzy) == expected

        where:
        name1                                                 | name2                                                 | fuzzy | expected
        new RelationReference(Table, "a")                     | new RelationReference(Table, "a")                     | true  | true
        new RelationReference(Table, "a")                     | new RelationReference(Table, "a")                     | false | true
        new RelationReference(Table, "a")                     | new RelationReference(Table, "b")                     | true  | false
        new RelationReference(Table, "a")                     | new RelationReference(Table, "b")                     | false | false
        new RelationReference(Table, "a")                     | new RelationReference(Table, "b", "a")                | true  | true
        new RelationReference(Table, "a")                     | new RelationReference(Table, "b", "a")                | false | false
        new RelationReference(Table, "b", "a")                | new RelationReference(Table, "a")                     | true  | true
        new RelationReference(Table, "b", "a")                | new RelationReference(Table, "a")                     | false | false
        new RelationReference(Table, "b", "a")                | new RelationReference(Table, "b", "a")                | true  | true
        new RelationReference(Table, "b", "a")                | new RelationReference(Table, "b", "a")                | false | true
        new RelationReference(Table, "c", "b", "a")           | new RelationReference(Table, "c", "b", "a")           | true  | true
        new RelationReference(Table, "c", "b", "a")           | new RelationReference(Table, "c", "b", "a")           | false | true
        new RelationReference(Table, "c", "x", "a")           | new RelationReference(Table, "c", "b", "a")           | true  | false
        new RelationReference(Table, "c", "b", "a")           | new RelationReference(Table, "c", "x", "a")           | true  | false
        new RelationReference(Table, "c", null, "a")          | new RelationReference(Table, "c", "b", "a")           | true  | true
        new RelationReference(Table, "c", "b", "a")           | new RelationReference(Table, "c", null, "a")          | true  | true
        new RelationReference(Table, null, "c", "b", "a")     | new RelationReference(Table, "c", "b", "a")           | true  | true
        new RelationReference(Table, "c", "b", "a")           | new RelationReference(Table, null, "c", "b", "a")     | true  | true
        new RelationReference(Table, null, "c", "b", "a")     | new RelationReference(Table, "c", "b", "a")           | false | true
        new RelationReference(Table, "a")                     | new RelationReference(Table, (String) null, "a")      | true  | true
        new RelationReference(Table, "a")                     | new RelationReference(Table, (String) null, "b")      | true  | false
        new RelationReference(Table, (String) null, "a")      | new RelationReference(Table, (String) null, "a")      | true  | true
        new RelationReference(Table, (String) null, "a")      | new RelationReference(Table, (String) null, "b")      | true  | false
        new RelationReference(Table, "a", (String) null, "c") | new RelationReference(Table, "a", "b", "c")           | true  | true
        new RelationReference(Table, "X", (String) null, "c") | new RelationReference(Table, "a", "b", "c")           | true  | false
        new RelationReference(Table, "a", (String) null, "X") | new RelationReference(Table, "a", "b", "c")           | true  | false
        new RelationReference(Table, "a", (String) null, "c") | new RelationReference(Table, "X", "b", "c")           | true  | false
        new RelationReference(Table, "a", (String) null, "c") | new RelationReference(Table, "a", "b", "X")           | true  | false
        new RelationReference(Table, "a", "b", "c")           | new RelationReference(Table, "a", (String) null, "c") | true  | true
        new RelationReference(Table, "a", "b", "c", "d")      | new RelationReference(Table, "b", (String) null, "d") | true  | true
        new RelationReference(Table, "b", (String) null, "d") | new RelationReference(Table, "a", "b", "c", "d")      | true  | true
    }

    @Unroll
    def "truncate"() {
        when:
        def originalName = name.toString();

        then:
        name.truncate(length).toString() == expected
        name.toString() == originalName //original is not affected

        where:
        name                                        | length | expected
        new RelationReference(Table)                | 1      | "UNNAMED"
        new RelationReference(Table)                | 2      | "UNNAMED"
        new RelationReference(Table, "a")           | 1      | "a"
        new RelationReference(Table, "a")           | 2      | "a"
        new RelationReference(Table, "a")           | 3      | "a"
        new RelationReference(Table, "a", "b")      | 1      | "b"
        new RelationReference(Table, "a", "b")      | 2      | "a.b"
        new RelationReference(Table, "a", "b")      | 3      | "a.b"
        new RelationReference(Table, "a", "b", "c") | 1      | "c"
        new RelationReference(Table, "a", "b", "c") | 2      | "b.c"
        new RelationReference(Table, "a", "b", "c") | 3      | "a.b.c"
    }

    @Unroll
    def "instanceOf"() {
        expect:
        reference.instanceOf(type) == expected

        where:
        reference                   | type     | expected
        new ItemReference(Table)    | Table    | true
        new ItemReference(Table)    | View     | false
        new ItemReference(Table)    | Relation | true
        new ItemReference(Relation) | Table    | false
        new ItemReference(Item)     | Table    | false
        new ItemReference(Item)     | Item     | true
        new ItemReference(Table)    | Item     | true
        new ItemReference()         | Item     | true
        new ItemReference()         | Table    | false
        new ItemReference()         | null     | true
        new ItemReference(Table)    | null     | false
    }
}

