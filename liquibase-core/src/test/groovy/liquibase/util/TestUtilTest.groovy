package liquibase.util

import liquibase.JUnitScope
import liquibase.SingletonObject
import liquibase.action.core.AddAutoIncrementAction
import liquibase.action.core.DropTableAction
import liquibase.database.DatabaseFactory
import liquibase.item.core.ColumnReference
import liquibase.item.core.RelationReference
import liquibase.item.core.Table
import liquibase.item.datatype.DataType
import liquibase.plugin.AbstractPluginFactory
import spock.lang.Specification

class TestUtilTest extends Specification {

    def "getClasses should find subclasses as well"() {
        when:
        def classes = TestUtil.getClasses(SingletonObject)

        then:
        assert !classes.contains(AbstractPluginFactory)
        assert classes.contains(DatabaseFactory)
    }

    def "createAllPermutations"() {
        when:
        def permutations = TestUtil.createAllPermutations(DropTableAction, [
                table             : [new RelationReference(Table, "table1"), new RelationReference(Table, "table2"), new RelationReference(Table, "table3")],
                cascadeConstraints: [true, false],
        ])

        then:
        permutations.size() == 4 * 3 //3 + 1 null * 2 + 1 null
        assert permutations*.toString().contains("dropTable()") //empty permutation not included
        assert !permutations.contains(null) //no null value
        permutations*.toString().sort() == (permutations.collect { it.toString() }).unique().sort() //nothing is duplicated
    }

    def "createAllPermutationsWithoutNulls"() {
        when:
        def permutations = TestUtil.createAllPermutationsWithoutNulls(DropTableAction, [
                table             : [new RelationReference(Table, "table1"), new RelationReference(Table, "table2"), new RelationReference(Table, "table3")],
                cascadeConstraints: [true, false],
        ])

        then:
        permutations.size() == 3 * 2
        assert !permutations*.toString().contains("dropTable()") //empty permutation not included
        assert !permutations.contains(null) //no null value
        permutations*.toString().sort() == (permutations.collect { it.toString() }).unique().sort() //nothing is duplicated
    }

    def "createAllPermutations with nested fields"() {
        when:
        def permutations = TestUtil.createAllPermutations(AddAutoIncrementAction, [
                column  : TestUtil.createAllPermutations(ColumnReference, [
                        name     : ["a", "b", "c"],
                        container: [new RelationReference(Table, "x"), new RelationReference(Table, "y"), new RelationReference(Table, "z")],
                ]),
                dataType: [new DataType(DataType.StandardType.INTEGER)]
        ])

        then:
        permutations.size() == ((4 * 4) + 1) * 2 //column permutations plus null, times dataType
        assert permutations*.toString().contains("addAutoIncrement()") //empty permutation not included
        assert !permutations.contains(null) //no null value
        permutations*.toString().sort() == (permutations.collect { it.toString() }).unique().sort() //nothing is duplicated
    }
}
