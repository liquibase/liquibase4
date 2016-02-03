package liquibase.exception

import liquibase.action.core.AddAutoIncrementAction
import liquibase.action.core.AddColumnsAction
import liquibase.action.core.CreateTableAction
import liquibase.structure.core.Column
import liquibase.structure.core.Table
import spock.lang.Specification
import spock.lang.Unroll

class ValidationErrorsTest extends Specification {

    @Unroll
    def "checkForRequiredField"() {
        when:
        def errors = new ValidationErrors(object).checkRequiredFields(field)

        then:
        errors.hasErrors() == (message != "No errors")
        errors.toString() == message

        where:
        field                                  | object                                                                                     | message
        "name"                                 | new Table("name")                                                                          | "No errors"
        "age"                                  | new Table("name")                                                                          | "Table.age is required"
        "schema"                               | new Table("name")                                                                          | "Table.schema is required"
        "autoIncrementInformation"             | new Column()                                                                               | "Column.autoIncrementInformation is required"
        "foreignKeys.table" | new AddColumnsAction() | "No errors"
        "autoIncrementInformation.startWith"   | new Column()                                                                               | "No errors"
        "autoIncrementInformation"             | new Column().set("autoIncrementInformation", new Column.AutoIncrementInformation())        | "No errors"
        "autoIncrementInformation.startWith"   | new Column().set("autoIncrementInformation", new Column.AutoIncrementInformation(1, null)) | "No errors"
        "autoIncrementInformation.incrementBy" | new Column().set("autoIncrementInformation", new Column.AutoIncrementInformation(1, null)) | "Column.autoIncrementInformation.incrementBy is required"
    }

    def "addAll"() {
        when:
        def rootErrors = new ValidationErrors(new CreateTableAction())
        def childErrors = new ValidationErrors(new AddAutoIncrementAction().set("column", new Column.ColumnReference()))

        childErrors.checkRequiredFields("column.name")
        rootErrors.addAll(childErrors, "autoIncrementInformation")

        then:
        rootErrors.getErrorMessages() == ["CreateTableAction.autoIncrementInformation.column.name is required"]
    }
}