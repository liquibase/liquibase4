package liquibase

import liquibase.action.core.AddAutoIncrementAction
import liquibase.action.core.CreateTableAction
import liquibase.action.core.DropTableAction
import liquibase.structure.ObjectReference
import liquibase.structure.core.Column
import liquibase.structure.core.ForeignKey
import liquibase.structure.core.Schema
import liquibase.structure.core.Table
import liquibase.structure.core.UniqueConstraint
import spock.lang.Specification

import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.that

class AbstractExtensibleObjectTest extends Specification {

    def "add works"() {
        given:
        def obj = new AbstractExtensibleObject() {}
        obj.set("wasObj", "Value 1a")
        obj.set("wasEmpty", [])

        when:
        obj.add("wasObj", "Value 1b")
        obj.add("wasObj", "Value 1c")
        obj.add("wasEmpty", "Value 2a")
        obj.add("wasEmpty", "Value 2b")
        obj.add("wasEmpty", "Value 2c")
        obj.add("wasNull", "Value 3a")
        obj.add("wasNull", "Value 3b")
        obj.add("wasNull", "Value 3c")

        then:
        obj.get("wasObj", Collection) == ["Value 1a", "Value 1b", "Value 1c",]
        obj.get("wasEmpty", Collection) == ["Value 2a", "Value 2b", "Value 2c",]
        obj.get("wasNull", Collection) == ["Value 3a", "Value 3b", "Value 3c",]

    }

    def "getAttributeNames"() {
        when:
        def nonFieldObject = new AbstractExtensibleObject() {};
        nonFieldObject.set("value1", "One")
        nonFieldObject.set("value2", "Two")
        nonFieldObject.set("value3", 3)
        nonFieldObject.set("valueNull", null)

        def fieldObject = new AddAutoIncrementAction();
        fieldObject.set("value1", "One")
        fieldObject.set("value2", 2)
        fieldObject.set("startWith", 12)
        fieldObject.autoIncrementInformation = new Column.AutoIncrementInformation(null, new BigInteger(32))
        fieldObject.column = new Column.ColumnReference("x", "y")

        then:
        that nonFieldObject.getAttributeNames(), containsInAnyOrder(["value1", "value2", "value3"] as String[])
        that fieldObject.getAttributeNames(), containsInAnyOrder(["value1", "value2", "startWith", "autoIncrementInformation", "column"] as String[])
    }

    def "getStandardAttributeNames"() {
        expect:
        that new AddAutoIncrementAction().getStandardAttributeNames(), containsInAnyOrder(["dataType", "column", "autoIncrementInformation"] as String[])
        that new AddAutoIncrementAction().getStandardAttributeNames(), containsInAnyOrder(["dataType", "column", "autoIncrementInformation"] as String[]) //caching works

        that new DropTableAction().getStandardAttributeNames(), containsInAnyOrder(["table", "cascadeConstraints"] as String[])

        (new AbstractExtensibleObject() {}).getStandardAttributeNames().size() == 0
    }

    def "get/set works with non-field values"() {
        when:
        def obj = new AbstractExtensibleObject() {};
        obj.set("value1", "One")
        obj.set("value2", "Two")
        obj.set("value3", 3)
        obj.set("valueNull", null)

        then:
        obj.get("value1", String) == "One"
        obj.get("value2", String) == "Two"
        obj.get("value3", String) == "3"
        obj.get("value3", Integer) == 3
        obj.get("valueNull", Integer) == null
        obj.get("valueUndefined", Integer) == null
    }

    def "get/set works with field values"() {
        when:
        def obj = new AddAutoIncrementAction();
        obj.set("value1", "One")
        obj.set("value2", 2)
        obj.autoIncrementInformation = new Column.AutoIncrementInformation(null, new BigInteger(32))
        obj.autoIncrementInformation.set("startWith", 12)

        then:
        obj.get("value1", String) == "One"
        obj.get("value2", String) == "2"
        obj.get("value2", Integer) == 2

        obj.autoIncrementInformation.get("startWith", Integer) == 12
        obj.autoIncrementInformation.get("startWith", String) == "12"
        obj.autoIncrementInformation.startWith == new BigInteger("12")
        assert obj.autoIncrementInformation.has("startWith")

        obj.autoIncrementInformation.get("incrementBy", Integer) == 32
        obj.autoIncrementInformation.get("incrementBy", String) == "32"
        obj.autoIncrementInformation.incrementBy == new BigInteger("32")
        assert obj.autoIncrementInformation.has("incrementBy")

        obj.dataType == null;
        obj.get("dataType", String) == null
        assert !obj.has("dataType")
    }

    def "get/set works with field values on parent objects"() {
        when:
        def obj = new Table();
        obj.set("value1", "One")
        obj.set("value2", 2)
        obj.name = "testTable"
        obj.tablespace = "test_tablespace"

        then:
        obj.get("value2", String) == "2"
        obj.tablespace == "test_tablespace"
        obj.name == "testTable"
        obj.get("name", String) == "testTable"

        when:
        obj.set("tablespace", "other_tablespace")
        obj.set("name", "newTableName")

        then:
        obj.get("value2", String) == "2"
        obj.tablespace == "other_tablespace"
        obj.name.toString() == "newTableName"
        obj.get("name", String) == "newTableName"
    }

    def "nested properties work"() {
        when:
        def obj = new CreateTableAction()
        obj.table = new Table(new ObjectReference(Schema, "schema_name"), "test_table")
        obj.uniqueConstraints = [new UniqueConstraint(null, new ObjectReference(Table, "test_table"), "col1a", "col1b"), new UniqueConstraint(null, new ObjectReference(Table, "test_table"), "col2")]
        obj.foreignKeys = [
                new ForeignKey(new ObjectReference(Table, "base_table1"), new ObjectReference(Table, "ref_table1"), "fk1", ["base_1"], ["ref_1"]),
                new ForeignKey(new ObjectReference(Table, "base_table2"), new ObjectReference(Table, "ref_table2"), "fk2", ["base_2a", "base_2b"], ["ref_2a", "ref_2b"]),
        ]

        then:
        obj.get("table.name", String.class) == "test_table"
        obj.get("table.schema.name", String.class) == "schema_name"
        obj.get("table.schema.container", ObjectReference.class) == null
        obj.get("table.primaryKey.tablespace", String.class) == null //nulls in the chain return null
        obj.get("uniqueConstraints.name", List) == [null, null]
        obj.get("uniqueConstraints.columns", List) == ["col1a", "col1b", "col2"]
        obj.get("foreignKeys.name", List) == ["fk1", "fk2"]
        obj.get("foreignKeys.table", List) == [new ObjectReference(Table, "base_table1"), new ObjectReference(Table, "base_table2")]
        obj.get("foreignKeys.columnChecks.baseColumn", List) == ["base_1", "base_2a", "base_2b"]
        obj.get("foreignKeys.columnChecks.referencedColumn", List) == ["ref_1", "ref_2a", "ref_2b"]

    }

    def "collections with only null values count as null"() {
        when:
        def obj = new AbstractExtensibleObject([
                a: "a",
                b: ["b"],
                c: [null],
                d: [null, null],
                e: [new AbstractExtensibleObject()],
        ])

        then:
        obj.get("a", Object) == "a"
        obj.get("b", Object) == ["b"]
        obj.get("c", Object) == [null]
        obj.get("d", Object) == [null, null]
        obj.get("e.x", Object) == [null]
        obj.get("e.x.y", Object) == null

    }
}