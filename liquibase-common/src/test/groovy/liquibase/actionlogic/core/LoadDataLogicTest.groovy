package liquibase.actionlogic.core

import liquibase.JUnitScope
import liquibase.Scope
import liquibase.action.core.LoadDataAction
import liquibase.actionlogic.AbstractActionLogicTest
import liquibase.actionlogic.ActionExecutor
import liquibase.database.core.MockDatabase
import liquibase.exception.ActionPerformException
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.resource.MockResourceAccessor
import spock.lang.Unroll

class LoadDataLogicTest extends AbstractActionLogicTest {

    def resourceAccessor

    @Override
    protected String getExpectedValidationErrors() {
        return """
LoadDataAction.path is required
LoadDataAction.table is required
""".trim()
    }

    @Unroll
    def "configures parser based on file name and action param"() {
        when:
        def action = new LoadDataAction()
        action.commentLineStart = comment
        action.path = path
        def parser = new LoadDataLogic().createParser(action, JUnitScope.instance)

        then:
        parser.toString() == expected

        where:
        path                   | comment | expected
        "com/example/test.csv" | null    | "CSV Parser: delimiter=, commentMarker=# nullString=NULL escapeChar=\\ ignoreEmptyLines=true ignoreSurroundingSpaces=true quoteCharacter=\""
        "com/example/test.CSV" | null    | "CSV Parser: delimiter=, commentMarker=# nullString=NULL escapeChar=\\ ignoreEmptyLines=true ignoreSurroundingSpaces=true quoteCharacter=\""
        "com/example/test.txt" | null    | "CSV Parser: delimiter=, commentMarker=# nullString=NULL escapeChar=\\ ignoreEmptyLines=true ignoreSurroundingSpaces=true quoteCharacter=\""
        "com/example/test.tsv" | null    | "CSV Parser: delimiter=\t commentMarker=# nullString=NULL escapeChar=\\ ignoreEmptyLines=true ignoreSurroundingSpaces=true quoteCharacter=\""
        "com/example/test.TSV" | null    | "CSV Parser: delimiter=\t commentMarker=# nullString=NULL escapeChar=\\ ignoreEmptyLines=true ignoreSurroundingSpaces=true quoteCharacter=\""
        "com/example/test.csv" | "-"     | "CSV Parser: delimiter=, commentMarker=- nullString=NULL escapeChar=\\ ignoreEmptyLines=true ignoreSurroundingSpaces=true quoteCharacter=\""
    }

    @Unroll
    def "parses CSV files correctly"() {
        when:
        def scope = JUnitScope.instance.child(Scope.Attr.resourceAccessor, resourceAccessor)

        def action = new LoadDataAction()
        action.table = new RelationReference(Table, "test_table")
        action.path = path
        action.columns = columns
        def result = new LoadDataLogic().execute(action, scope)

        then:
        result.actions.collect({
            return scope.getSingleton(ActionExecutor).createPlan(it, scope.child(Scope.Attr.database, new MockDatabase()))
        })*.describe(false).join("\n") == expected

        where:
        path                                 | columns | expected
        "com/example/simpleWithLineFeed.csv" | [new LoadDataAction.LoadDataColumn("name", new DataType(DataType.StandardType.VARCHAR, 10)), new LoadDataAction.LoadDataColumn("age", new DataType(DataType.StandardType.INTEGER))] | "INSERT INTO `test_table` (`name`, `age`) VALUES ('Bob Johnson', 42)\nINSERT INTO `test_table` (`name`, `age`) VALUES ('John Doe', 23)"
        "com/example/simpleWithCRLF.csv"     | [new LoadDataAction.LoadDataColumn("name", new DataType(DataType.StandardType.VARCHAR, 10)), new LoadDataAction.LoadDataColumn("age", new DataType(DataType.StandardType.INTEGER))] | "INSERT INTO `test_table` (`name`, `age`) VALUES ('Bob Johnson', 42)\nINSERT INTO `test_table` (`name`, `age`) VALUES ('John Doe', 23)"
        "com/example/withBlanks.csv"         | [new LoadDataAction.LoadDataColumn("name", new DataType(DataType.StandardType.VARCHAR, 10)), new LoadDataAction.LoadDataColumn("age", new DataType(DataType.StandardType.VARCHAR, 20))] | "INSERT INTO `test_table` (`name`, `age`) VALUES ('Bob Johnson', '42')\nINSERT INTO `test_table` (`name`, `age`) VALUES ('John Doe', '23')"
        "com/example/withBlanks.csv"         | [new LoadDataAction.LoadDataColumn("name", new DataType(DataType.StandardType.VARCHAR, 10)), new LoadDataAction.LoadDataColumn("age", new DataType(DataType.StandardType.VARCHAR, 20))] | "INSERT INTO `test_table` (`name`, `age`) VALUES ('Bob Johnson', '42')\nINSERT INTO `test_table` (`name`, `age`) VALUES ('John Doe', '23')"
        "com/example/comments.csv"           | [new LoadDataAction.LoadDataColumn("name", new DataType(DataType.StandardType.VARCHAR, 10)), new LoadDataAction.LoadDataColumn("age", new DataType(DataType.StandardType.VARCHAR, 20))] | "INSERT INTO `test_table` (`name`, `age`) VALUES ('No comment', '44')\nINSERT INTO `test_table` (`name`, `age`) VALUES ('Checking #twitter', '13')"
        "com/example/withQuotes.csv"         | [new LoadDataAction.LoadDataColumn("name", new DataType(DataType.StandardType.VARCHAR, 10)), new LoadDataAction.LoadDataColumn("age", new DataType(DataType.StandardType.INTEGER))] | "INSERT INTO `test_table` (`name`, `age`) VALUES ('Bob Johnson', 42)\nINSERT INTO `test_table` (`name`, `age`) VALUES ('John \"the fish\" Johnson', 23)"
        "com/example/simpleWithLineFeed.csv" | [new LoadDataAction.LoadDataColumn("name", new DataType(DataType.StandardType.VARCHAR, 10)), new LoadDataAction.LoadDataColumn("age", new DataType(DataType.StandardType.INTEGER)).set("skip", true)] | "INSERT INTO `test_table` (`name`) VALUES ('Bob Johnson')\nINSERT INTO `test_table` (`name`) VALUES ('John Doe')"
        "com/example/withNulls.csv"          | [new LoadDataAction.LoadDataColumn("name", new DataType(DataType.StandardType.VARCHAR, 10)), new LoadDataAction.LoadDataColumn("age", new DataType(DataType.StandardType.INTEGER))] | "INSERT INTO `test_table` (`name`, `age`) VALUES ('Bob Johnson', 42)\nINSERT INTO `test_table` (`name`, `age`) VALUES (NULL, 23)\nINSERT INTO `test_table` (`name`, `age`) VALUES ('Fred Smith', NULL)\nINSERT INTO `test_table` (`name`, `age`) VALUES (NULL, NULL)"
        "com/example/simpleWithLineFeed.csv" | [new LoadDataAction.LoadDataColumn("name", new DataType(DataType.StandardType.VARCHAR, 10))] | "INSERT INTO `test_table` (`name`, `age`) VALUES ('Bob Johnson', '42')\nINSERT INTO `test_table` (`name`, `age`) VALUES ('John Doe', '23')"
        "com/example/simpleWithLineFeed.csv" | [new LoadDataAction.LoadDataColumn("age", new DataType(DataType.StandardType.INTEGER))] | "INSERT INTO `test_table` (`name`, `age`) VALUES ('Bob Johnson', 42)\nINSERT INTO `test_table` (`name`, `age`) VALUES ('John Doe', 23)"
        "com/example/simpleWithLineFeed.csv" | [] | "INSERT INTO `test_table` (`name`, `age`) VALUES ('Bob Johnson', '42')\nINSERT INTO `test_table` (`name`, `age`) VALUES ('John Doe', '23')" //empty column configs
        "com/example/simpleWithLineFeed.csv" | null | "INSERT INTO `test_table` (`name`, `age`) VALUES ('Bob Johnson', '42')\nINSERT INTO `test_table` (`name`, `age`) VALUES ('John Doe', '23')" //null column configs
        "com/example/simpleWithLineFeed.csv" | [new LoadDataAction.LoadDataColumn(1, new DataType(DataType.StandardType.INTEGER)), new LoadDataAction.LoadDataColumn(0, new DataType(DataType.StandardType.VARCHAR, 10))] | "INSERT INTO `test_table` (`name`, `age`) VALUES ('Bob Johnson', 42)\nINSERT INTO `test_table` (`name`, `age`) VALUES ('John Doe', 23)" //column configs are indexed and out of order
        "com/example/simpleWithLineFeed.csv" | [new LoadDataAction.LoadDataColumn(1, new DataType(DataType.StandardType.INTEGER))] | "INSERT INTO `test_table` (`name`, `age`) VALUES ('Bob Johnson', 42)\nINSERT INTO `test_table` (`name`, `age`) VALUES ('John Doe', 23)"  //just one column config, by index

    }

    def "uses columnsForUpdateCheck"() {
        when:
        def scope = JUnitScope.instance.child(Scope.Attr.resourceAccessor, resourceAccessor)

        def action = new LoadDataAction()
        action.table = new RelationReference(Table, "test_table")
        action.path = "com/example/simpleWithLineFeed.csv"
        action.columns = [new LoadDataAction.LoadDataColumn("name", new DataType(DataType.StandardType.VARCHAR, 10)), new LoadDataAction.LoadDataColumn("age", new DataType(DataType.StandardType.INTEGER))]
        action.columnsForUpdateCheck = ["name"]
        def result = new LoadDataLogic().execute(action, scope)

        then:
        result.actions.collect({
            return scope.getSingleton(ActionExecutor).createPlan(it, scope.child(Scope.Attr.database, new MockDatabase()))
        })*.describe(false).join("\n") == "MERGE INTO `test_table` dst USING ( SELECT 'Bob Johnson' as `name`, 42 as `age` ) src ON (dst.`name`=src.`name`) WHEN MATCHED THEN UPDATE SET `age`=42 WHEN NOT MATCHED THEN INSERT (`name`, `age`) VALUES ('Bob Johnson', 42)\n" +
                "MERGE INTO `test_table` dst USING ( SELECT 'John Doe' as `name`, 23 as `age` ) src ON (dst.`name`=src.`name`) WHEN MATCHED THEN UPDATE SET `age`=23 WHEN NOT MATCHED THEN INSERT (`name`, `age`) VALUES ('John Doe', 23)"
    }

    def "throws an error if the file doesn't exist"() {
        when:
        def action = new LoadDataAction()
        action.table = new RelationReference(Table, "test_table")
        action.path = "com/example/invalid.csv"

        new LoadDataLogic().execute(action, JUnitScope.instance)

        then:
        ActionPerformException ex = thrown()
        ex.message == "Could not find data at path com/example/invalid.csv"
    }

    def "throws an error if the file is missing a cell"() {
        when:
        def action = new LoadDataAction()
        action.table = new RelationReference(Table, "test_table")
        action.path = "com/example/missingCell.csv"

        new LoadDataLogic().execute(action, JUnitScope.instance.child(Scope.Attr.resourceAccessor, resourceAccessor))

        then:
        ActionPerformException ex = thrown()
        ex.message == "Index for header 'age' is 1 but CSVRecord only has 1 values!"
    }

    def setup() {
        resourceAccessor = new MockResourceAccessor()
        resourceAccessor.addData("com/example/simpleWithLineFeed.csv", """
name, age
Bob Johnson, 42
John Doe, 23
""".trim().replaceAll("\r\n", "\n"))

        resourceAccessor.addData("com/example/simpleWithCRLF.csv", """
name, age
Bob Johnson, 42
John Doe, 23
""".trim().replaceAll("\r\n", "\n").replaceAll("\n", "\r\n"))

        resourceAccessor.addData("com/example/withBlanks.csv", """

name, age

Bob Johnson, 42


John Doe, 23


""")

        resourceAccessor.addData("com/example/comments.csv", """
## some comments before the header
name, age
# Bob Johnson, 42
#John Doe, 23
No comment, 44
Checking #twitter, 13
""".trim().replaceAll("\r\n", "\n"))

        resourceAccessor.addData("com/example/withQuotes.csv", """
name, age
"Bob Johnson", 42
John "the fish" Johnson, 23
""".trim().replaceAll("\r\n", "\n"))

        resourceAccessor.addData("com/example/withNulls.csv", """
name, age
"Bob Johnson", 42
null, 23
"Fred Smith", NULL
null, null
""".trim().replaceAll("\r\n", "\n"))

        resourceAccessor.addData("com/example/missingCell.csv", """
name, age
Bob Johnson, 42
John Doe
""")

    }
}
