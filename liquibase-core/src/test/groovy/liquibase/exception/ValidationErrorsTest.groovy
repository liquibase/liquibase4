package liquibase.exception

import liquibase.action.AbstractAction
import liquibase.structure.ObjectReference
import liquibase.structure.core.Column
import liquibase.structure.core.Table
import spock.lang.Specification
import spock.lang.Unroll

class ValidationErrorsTest extends Specification {

    @Unroll("#featureName #field")
    def "checkForRequiredContainer"() {
        when:
        def action = new AbstractAction() {}
        action.set(field, objectName)

        then:
        new ValidationErrors().checkForRequiredContainer("a message", field, action).hasErrors() == expectError

        where:
        field            | objectName                                 | expectError
        "noContainer"    | new ObjectReference(Table, "a")            | true
        "oneContainer"   | new ObjectReference(Table, "a", "b")       | false
        "twoContainer"   | new ObjectReference(Table, "a", "b", "c")  | false
        "splitContainer" | new ObjectReference(Table, "a", null, "c") | true
        "nullContainer"  | new ObjectReference(Table, null, "c")      | true
        "emptyObject"    | new ObjectReference(Table)                 | true
        "nullObject"     | new ObjectReference(Table, (String) null)  | true
        "null"           | null                                       | false
    }

    @Unroll
    def "checkForRequiredField"() {
        when:
        def errors = new ValidationErrors().checkRequiredFields(object, field)

        then:
        errors.hasErrors() == (message != "No errors")
        errors.toString() == message

        where:
        field                                  | object                                                                                     | message
        "name"                                 | new Table("name")                                                                          | "No errors"
        "age"                                  | new Table("name")                                                                          | "Table.age is required"
        "schema"                               | new Table("name")                                                                          | "Table.schema is required"
        "name"                                 | [new Table("name1"), new Table("name2")]                                                   | "No errors"
        "name"                                 | [new Table(), new Table()]                                                                 | "Table.name is required" //only the first error is found
        "name"                                 | [new Table("name1"), new Table()]                                                          | "Table.name is required"
        "name"                                 | [new Table(), new Table("name1")]                                                          | "Table.name is required"
        "autoIncrementInformation"             | new Column()                                                                               | "Column.autoIncrementInformation is required"
        "autoIncrementInformation"             | new Column().set("autoIncrementInformation", new Column.AutoIncrementInformation())        | "No errors"
        "autoIncrementInformation.startWith"   | new Column().set("autoIncrementInformation", new Column.AutoIncrementInformation(1, null)) | "No errors"
        "autoIncrementInformation.incrementBy" | new Column().set("autoIncrementInformation", new Column.AutoIncrementInformation(1, null)) | "Column.autoIncrementInformation.incrementBy is required"
    }
}