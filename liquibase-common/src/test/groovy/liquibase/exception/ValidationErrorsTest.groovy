package liquibase.exception

import liquibase.ValidationErrors
import liquibase.action.core.AddAutoIncrementAction
import liquibase.action.core.AddColumnsAction
import liquibase.action.core.AddUniqueConstraintsAction
import liquibase.action.core.CreateTableAction
import liquibase.item.core.Column
import liquibase.item.core.ColumnReference
import liquibase.item.core.Table
import liquibase.item.core.UniqueConstraint
import liquibase.item.datatype.DataType
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
        field                                  | object                                                                                                | message
        "name"                                 | new Table("name")                                                                                     | "No errors"
        "age"                                  | new Table("name")                                                                                     | "Table.age is required"
        "schema"                               | new Table("name")                                                                                     | "Table.schema is required"
        "autoIncrementDetails"             | new Column()                                                                                          | "Column.autoIncrementDetails is required"
        "foreignKeys.table"                    | new AddColumnsAction()                                                                                | "No errors"
        "autoIncrementDetails.startWith"   | new Column()                                                                                          | "No errors"
        "autoIncrementDetails"             | new Column().set("autoIncrementDetails", new Column.AutoIncrementDetails())                   | "No errors"
        "autoIncrementDetails.startWith"   | new Column().set("autoIncrementDetails", new Column.AutoIncrementDetails(1, null))            | "No errors"
        "autoIncrementDetails.incrementBy" | new Column().set("autoIncrementDetails", new Column.AutoIncrementDetails(1, null))            | "Column.autoIncrementDetails.incrementBy is required"
        "uniqueConstraints.columns"            | new AddUniqueConstraintsAction(new UniqueConstraint())                                                | "AddUniqueConstraintsAction.uniqueConstraints.columns is required"
        "columns.type"                         | new AddColumnsAction(new Column("col1", null))                                                        | "AddColumnsAction.columns.type is required" //just one value and it's null
        "columns.type"                         | new AddColumnsAction(new Column("col1", null), new Column("col2", null, DataType.parse("int"), true)) | "AddColumnsAction.columns.type is required" //2 values, one of which is null
    }

    def "addAll"() {
        when:
        def rootErrors = new ValidationErrors(new CreateTableAction())
        def childErrors = new ValidationErrors(new AddAutoIncrementAction().set("column", new ColumnReference()))

        childErrors.checkRequiredFields("column.name")
        rootErrors.addAll(childErrors, "autoIncrementDetails")

        then:
        rootErrors.getErrorMessages() == ["CreateTableAction.autoIncrementDetails.column.name is required"]
    }
}