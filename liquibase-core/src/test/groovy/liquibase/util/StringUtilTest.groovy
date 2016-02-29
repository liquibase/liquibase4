package liquibase.util

import spock.lang.Specification
import spock.lang.Unroll

class StringUtilTest extends Specification {

    def "join with map"() {
        expect:
        StringUtil.join((Map) map as Map, delimiter) == value

        where:
        map                               | value                    | delimiter
        new HashMap()                     | ""                       | ", "
        [key1: "a"]                       | "key1=a"                 | ", "
        [key1: "a", key2: "b"]            | "key1=a, key2=b"         | ", "
        [key1: "a", key2: "b"]            | "key1=aXkey2=b"          | "X"
        [key1: "a", key2: "b", key3: "c"] | "key1=a, key2=b, key3=c" | ", "
    }

    @Unroll
    def "join sorted"() {
        expect:
        StringUtil.join(array, ",", sorted) == expected

        where:
        array           | sorted | expected
        ["a", "c", "b"] | true   | "a,b,c"
        ["a", "c", "b"] | false  | "a,c,b"
    }

    @Unroll
    def "join with formatter sorted"() {
        expect:
        StringUtil.join(array, ",", new StringUtil.ToStringFormatter(), sorted) == expected

        where:
        array     | sorted | expected
        [1, 3, 2] | true   | "1,2,3"
        [1, 3, 2] | false  | "1,3,2"
    }


    @Unroll
    def "pad"() {
        expect:
        StringUtil.pad(input, pad) == output

        where:
        input   | pad | output
        null    | 0   | ""
        null    | 3   | "   "
        ""      | 0   | ""
        ""      | 3   | "   "
        " "     | 3   | "   "
        "abc"   | 2   | "abc"
        "abc"   | 3   | "abc"
        "abc  " | 3   | "abc"
        "abc"   | 5   | "abc  "
        "abc "  | 5   | "abc  "
    }

    @Unroll
    def "concatConsistentCase"() {
        expect:
        StringUtil.concatConsistentCase(base, addition) == expected

        where:
        base | addition | expected
        "abc" | "_xyz" | "abc_xyz"
        "abc" | "_XYZ" | "abc_xyz"
        "abc" | "_XyZ" | "abc_xyz"

        "ABC" | "_xyz" | "ABC_XYZ"
        "ABC" | "_XYZ" | "ABC_XYZ"
        "ABC" | "_XyZ" | "ABC_XYZ"

        "AbC" | "_xyz" | "AbC_xyz"
        "AbC" | "_XYZ" | "AbC_XYZ"
        "AbC" | "_XyZ" | "AbC_XyZ"

        "a1" | "_x" | "a1_x"
        "A1" | "_x" | "A1_X"
        "123" | "_x" | "123_x"
        "123" | "_X" | "123_X"
    }
}
