package liquibase.exception

import liquibase.ValidationErrors
import liquibase.action.core.AddAutoIncrementAction
import liquibase.action.core.AddColumnsAction
import liquibase.action.core.AddUniqueConstraintsAction
import liquibase.action.core.CreateTableAction
import liquibase.structure.core.Column
import liquibase.structure.core.Table
import liquibase.structure.core.UniqueConstraint
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
        "foreignKeys.table"                    | new AddColumnsAction()                                                                     | "No errors"
        "autoIncrementInformation.startWith"   | new Column()                                                                               | "No errors"
        "autoIncrementInformation"             | new Column().set("autoIncrementInformation", new Column.AutoIncrementInformation())        | "No errors"
        "autoIncrementInformation.startWith"   | new Column().set("autoIncrementInformation", new Column.AutoIncrementInformation(1, null)) | "No errors"
        "autoIncrementInformation.incrementBy" | new Column().set("autoIncrementInformation", new Column.AutoIncrementInformation(1, null)) | "Column.autoIncrementInformation.incrementBy is required"
        "uniqueConstraints.columns"            | new AddUniqueConstraintsAction(new UniqueConstraint())                                     | "AddUniqueConstraintsAction.uniqueConstraints.columns is required"
        "columns.type"                         | new AddColumnsAction(new Column(null, "col1"))                                             | "AddColumnsAction.columns.type is required" //just one value and it's null
        "columns.type"                         | new AddColumnsAction(new Column(null, "col1"), new Column(null, "col2", "int"))            | "AddColumnsAction.columns.type is required" //2 values, one of which is null
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